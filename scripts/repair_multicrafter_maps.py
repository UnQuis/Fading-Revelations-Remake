#!/usr/bin/env python3
"""
Repair campaign maps whose `living-steel-complex` entity data predates the
block's conversion from GenericCrafter to MultiCrafter.

WHY THIS IS NEEDED
------------------
Mindustry stores each building's state inline in the map chunk's tile RLE
stream. `ShortChunkSaveVersion.readMap` (used by save version 7, which is what
these maps are) reads it like this:

    >h   block id            (resolved BY NAME through the map's own content
    >B   packedCheck          header, so unknown mods/blocks are harmless)
         bit0 -> hadEntity
         bit1 -> hadDataOld  (1 byte)
         bit2 -> hadDataNew  (7 bytes)
    if hadEntity:  >B isCenter
       if isCenter: readLegacyShortChunk ->  >H length, then `length` bytes
                     consumed by  byte revision + build.readAll(...)
    elif hadDataOld/New: ...
    else:               >B consecutives   (RLE run)

The critical detail: `readLegacyShortChunk` does NOT enforce the chunk length.
It hands the stream to `readAll` and trusts it to consume exactly
`length - 1` bytes. If a building's `read()` consumes more or fewer bytes than
were written, the stream silently desynchronises and the tile RLE run walks off
the end of the tile array:

    java.lang.ArrayIndexOutOfBoundsException: Index 262144 out of bounds for length 262144

262144 == 512*512 == the tile count of etnatic-isles, i.e. `context.tile(j)`
was called with j == width*height.

WHAT WENT WRONG
---------------
`living-steel-complex` used to be a plain GenericCrafter, whose build state is
8 bytes (progress f32 + warmup f32) at revision 0. It is now a MultiCrafter,
whose `read()` consumes 29 bytes. The maps still carry the 8-byte form, so the
loader under-reads by 21 bytes per building and desyncs.

    etnatic-isles.msav      1 occurrence  (tile 98725)
    carbonic-downpour.msav  3 occurrences (tiles 371243, 585274, 589370)

Every other building in every shipped map was verified to match its current
class exactly - see `verify()` below.

WHAT THIS DOES
--------------
Rewrites only those entity chunks:

  * keeps the revision byte and the whole `readBase` payload verbatim
    (health / rotation / team / enabled / module bits / items / power /
    efficiency), so nothing else about the building changes;
  * patches health to a value >= any block health, which `readBase` clamps via
    `Math.min(read.f(), block.health)` - i.e. the building loads at full health,
    which is what the map author intended (285 was *full* health for the old
    GenericCrafter version of this block);
  * replaces the 8 legacy bytes with the 29 bytes MultiCrafter.read() expects,
    all zeroed: no payload, zeroed crafting time / warmup / heat, recipe 0.

The file is re-emitted as MSAV version 7 with the same chunk layout and the
same zlib level, so the diff is confined to the map chunk.

Usage:
    python3 scripts/repair_multicrafter_maps.py           # dry run, report only
    python3 scripts/repair_multicrafter_maps.py --write   # rewrite the maps
"""

import os
import re
import struct
import subprocess
import sys
import zlib

MAP_DIR = os.path.join(os.path.dirname(os.path.dirname(os.path.abspath(__file__))), "assets", "maps")
SRC_DIR = os.path.join(os.path.dirname(os.path.dirname(os.path.abspath(__file__))), "src")

MOD_PREFIX = "fading-revelations-remake-"
MAGIC = b"MSAV"
SAVE_VERSION = 7
CHUNK_NAMES = ["meta", "content", "map", "entities", "custom"]

# MultiCrafterBuild.read(), bytes consumed after readBase():
#   super.read (PayloadBlockBuild): payVector.x f(4) + payVector.y f(4)
#                                 + payRotation f(4) + Payload.read bool(1) = 13
#   craftingTime f(4) + warmup f(4) + curRecipeIndex i(4) + heat f(4)        = 16
#   payloads.read()        only if the active recipe consumes payloads
#   readVecNullable (8)    only if revision >= 1 and the active recipe outputs payloads
# No MultiCrafter recipe in this mod uses payloads (asserted in main()), so the
# size is a constant. Bump this if that ever changes.
MULTICRAFTER_SIZE = 13 + 16

# GenericCrafterBuild.read(): progress f(4) + warmup f(4). This is what the
# stale chunks in the shipped maps contain.
LEGACY_GENERIC_CRAFTER_SIZE = 8

# "large enough to be clamped to full health by Math.min(read.f(), block.health)"
FULL_HEALTH = 1.0e6

# Expected block-specific byte counts for the mod's block classes, keyed by the
# Java class the block is instantiated with. Derived from the Mindustry v160.5
# sources; `rev` is the revision byte stored in the chunk. Used by verify() to
# prove a map can be loaded without desyncing.
#
# Classes that extend Building directly and add no state are 0.
def _turret(rev, tail, **_):          # Turret: reloadCounter f + rotation f when rev >= 1
    return 8 if rev >= 1 else 0

def _item_turret(rev, tail, **_):  # Turret + ammo count byte + entries
    n = 8 if rev >= 1 else 0
    if len(tail) < n + 1:
        return None
    count = tail[n]
    entry = 4 if rev < 2 else 2 + 2   # legacy 1-byte item id, else short id + short amount
    return n + 1 + count * entry

def _unit_build(rev, tail, **_):      # UnitBuild extends PayloadBlockBuild: 13 bytes of super
    n = 13
    return n

def _reconstructor(rev, tail, **_):   # UnitBuild + progress + commandPos + command
    n = 13
    if rev >= 1: n += 4
    if rev >= 2: n += 8
    if rev >= 3: n += 1
    return n

def _unit_factory(rev, tail, **_):    # UnitBuild + progress + currentPlan + commandPos + command
    n = 13 + 4 + 2
    if rev >= 2: n += 8
    if rev >= 3: n += 1
    return n

def _generator(rev, tail, **_):       # GeneratorBuild: productionEfficiency + generateTime
    n = 4
    if rev >= 1: n += 4
    return n

CLASS_SIZE = {
    "MultiCrafter":        lambda rev, tail, **_: MULTICRAFTER_SIZE,
    "GenericCrafter":      lambda rev, tail, **_: 8,
    "AttributeCrafter":    lambda rev, tail, **_: 8,
    "Drill":               lambda rev, tail, **_: 8 if rev >= 1 else 0,
    "ItemTurret":          _item_turret,
    "PowerTurret":         _turret,
    "LiquidTurret":        _turret,
    "TractorBeamTurret":   lambda rev, tail, **_: 4,   # BaseTurretBuild + rotation f
    "BuildTurret":         lambda rev, tail, **_: 6,
    "MassDriver":          lambda rev, tail, **_: 9,   # link i + rotation f + state b
    "Junction":            lambda rev, tail, **_: 200,  # 4 * (2 + 6 longs), capacity 6
    "ItemBridge":          lambda rev, tail, **_: 10,
    "Reconstructor":       _reconstructor,
    "UnitFactory":         _unit_factory,
    "SolarGenerator":      _generator,
    "ConsumeGenerator":    _generator,
    "ThermalGenerator":    _generator,
    "MendProjector":       lambda rev, tail, **_: 8,   # heat f + phaseHeat f
    "OverdriveProjector":  lambda rev, tail, **_: 8,
    "Unloader":            lambda rev, tail, **_: 2,
    "PowerNode":           lambda rev, tail, **_: 0,
    "Router":              lambda rev, tail, **_: 0,
    "LiquidRouter":        lambda rev, tail, **_: 0,
    "SolidPump":           lambda rev, tail, **_: 0,
}


# --------------------------------------------------------------------------- #
# container parsing
# --------------------------------------------------------------------------- #

def split_container(raw):
    """MSAV header + top level chunks. Returns (version, [(name, bytes)])."""
    if raw[:4] != MAGIC:
        raise ValueError("not an MSAV file: bad magic %r" % (raw[:4],))
    version = struct.unpack(">i", raw[4:8])[0]
    pos = 8
    chunks = []
    names = list(CHUNK_NAMES)
    if version >= 12:
        names = ["meta", "patches", "content", "map", "entities", "markers", "custom"]
    elif version >= 8:
        names = ["meta", "content", "map", "entities", "markers", "custom"]
    for name in names:
        if pos + 4 > len(raw):
            raise ValueError("truncated container: expected chunk %r" % name)
        length = struct.unpack(">i", raw[pos:pos + 4])[0]
        if length < 0 or pos + 4 + length > len(raw):
            raise ValueError("chunk %r length %d out of range at %d" % (name, length, pos))
        chunks.append((name, raw[pos + 4:pos + 4 + length]))
        pos += 4 + length
    if pos != len(raw):
        raise ValueError("%d trailing bytes after the last chunk" % (len(raw) - pos))
    return version, chunks


def build_container(version, chunks):
    out = bytearray(MAGIC)
    out += struct.pack(">i", version)
    for _, data in chunks:
        out += struct.pack(">i", len(data))
        out += data
    return bytes(out)


def block_names(content_chunk):
    """Content header -> {ContentType ordinal: [names]}, ids are positional."""
    p = 0
    ntypes = content_chunk[p]; p += 1
    names = {}
    for _ in range(ntypes):
        ctype = content_chunk[p]; p += 1
        count = struct.unpack(">h", content_chunk[p:p + 2])[0]; p += 2
        lst = []
        for _ in range(count):
            ln = struct.unpack(">H", content_chunk[p:p + 2])[0]; p += 2
            lst.append(content_chunk[p:p + ln].decode("utf-8", "replace"))
            p += ln
        names[ctype] = lst
    if p != len(content_chunk):
        raise ValueError("content header left %d bytes unread" % (len(content_chunk) - p))
    return names


# --------------------------------------------------------------------------- #
# map chunk walking
# --------------------------------------------------------------------------- #

def readbase_size(data):
    """Size of Building.readBase() for this chunk, including the revision byte.

    Returns (revision, consumed) or None if the chunk is in the pre-`rot|0x80`
    legacy layout (then module bits come from the live block, not the file, and
    the size cannot be modelled statically).
    """
    p = 0
    rev = data[p]; p += 1
    p += 4                                   # health f32
    rot = data[p]; p += 1
    p += 1                                   # team byte
    bits = None
    ver = 0
    legacy = True
    if rot & 0x80:
        ver = data[p]; p += 1
        if ver >= 1:
            p += 1                           # enabled
        if ver >= 2:
            bits = data[p]; p += 1
            legacy = False
    if bits is None:
        return None
    if bits & 1:                             # ItemModule
        if legacy:
            count = data[p]; p += 1
            p += count * (1 + 4)             # ub item id + i amount
        else:
            count = struct.unpack(">h", data[p:p + 2])[0]; p += 2
            p += count * (2 + 4)             # s item id + i amount
    if bits & 2:                             # PowerModule
        n = struct.unpack(">h", data[p:p + 2])[0]; p += 2
        p += n * 4 + 4                       # link positions + status f32
    if bits & 4:                             # LiquidModule
        if legacy:
            count = data[p]; p += 1
            p += count * (1 + 4)
        else:
            count = struct.unpack(">h", data[p:p + 2])[0]; p += 2
            p += count * (2 + 4)
    if bits & 16: p += 8                     # timeScale + timeScaleDuration
    if bits & 32: p += 4                     # lastDisabler position
    if ver <= 2: p += 1                      # old consume module bool
    if ver >= 3: p += 2                      # efficiency + optionalEfficiency
    if ver == 4: p += 8                      # visibleFlags
    return rev, p


def walk_blocks(mapdata, blocks):
    """Yield (tile_index, block_name, packed, is_center, chunk_off, chunk_len)
    for every block entry, and return the end offset.

    Mirrors ShortChunkSaveVersion.readMap exactly.
    """
    width, height = struct.unpack(">HH", mapdata[:4])
    total = width * height

    p = 4
    tiles = 0
    while tiles < total:                     # floor RLE
        p += 5                               # >h floor, >h ore, >B consecutives
        tiles += mapdata[p - 1] + 1
    if tiles != total:
        raise ValueError("floor RLE covered %d tiles, expected %d" % (tiles, total))

    entries = []
    i = 0
    while i < total:
        if p + 3 > len(mapdata):
            raise ValueError("block RLE ran out of bytes at tile %d" % i)
        bid = struct.unpack(">h", mapdata[p:p + 2])[0]; p += 2
        packed = mapdata[p]; p += 1
        name = blocks[bid] if 0 <= bid < len(blocks) else None

        if packed & 4:                       # hadDataNew
            p += 7

        is_center = True
        chunk_off = chunk_len = None
        if packed & 1:                       # hadEntity
            is_center = mapdata[p]; p += 1
            if is_center:
                chunk_len = struct.unpack(">H", mapdata[p:p + 2])[0]; p += 2
                chunk_off = p
                p += chunk_len
            i += 1
        elif packed & 2:                     # hadDataOld
            p += 1
            i += 1
        else:
            cons = mapdata[p]; p += 1
            # this is the exact expression that crashes the game when it runs long
            if i + cons + 1 > total:
                raise ValueError("tile RLE overruns the map at tile %d (consecutives=%d, total=%d)"
                                 % (i, cons, total))
            i += cons + 1

        entries.append((i - 1, name, packed, is_center, chunk_off, chunk_len))

    if i != total:
        raise ValueError("block RLE covered %d tiles, expected %d" % (i, total))
    if p != len(mapdata):
        raise ValueError("map chunk left %d bytes unread" % (len(mapdata) - p))
    return width, height, entries, p


def java_classes():
    """block name -> Java class, parsed out of the mod sources."""
    out = subprocess.run(
        ["grep", "-rhoE", r'new [A-Za-z]+\("[a-z0-9-]+"\)', SRC_DIR],
        capture_output=True, text=True).stdout
    mapping = {}
    for m in re.finditer(r'new ([A-Za-z]+)\("([a-z0-9-]+)"\)', out):
        mapping[m.group(2)] = m.group(1)
    return mapping


# --------------------------------------------------------------------------- #
# verify / repair
# --------------------------------------------------------------------------- #

def verify(mapdata, blocks, classes, path):
    """Structural + per-building size check. Returns (problems, entity_count)."""
    problems = []
    width, height, entries, _ = walk_blocks(mapdata, blocks)
    count = 0
    for _, name, packed, is_center, off, clen in entries:
        if not (packed & 1) or not is_center:
            continue
        count += 1
        if not name or not name.startswith(MOD_PREFIX):
            continue                         # vanilla blocks round-trip by construction
        short = name[len(MOD_PREFIX):]
        cls = classes.get(short)
        if cls is None:
            problems.append("%s: block %r is not in the mod sources (renamed?)" % (path, short))
            continue
        spec = CLASS_SIZE.get(cls)
        if spec is None:
            problems.append("%s: no size model for class %s (block %r)" % (path, cls, short))
            continue
        data = mapdata[off:off + clen]
        rb = readbase_size(data)
        if rb is None:
            problems.append("%s: %r uses the pre-160 entity layout, cannot model" % (path, short))
            continue
        rev, base = rb
        tail = data[base:]
        expected = spec(rev, tail)
        if expected is None:
            problems.append("%s: %r chunk too short to model" % (path, short))
        elif len(tail) != expected:
            problems.append("%s: %r (%s) stores %d bytes but read() consumes %d (rev %d)"
                            % (path, short, cls, len(tail), expected, rev))
    return problems, count


def repair(mapdata, blocks, classes):
    """Rewrite stale MultiCrafter entity chunks. Returns (new_mapdata, repaired)."""
    width, height, entries, _ = walk_blocks(mapdata, blocks)

    targets = []
    for idx, (tile, name, packed, is_center, off, clen) in enumerate(entries):
        if not (packed & 1) or not is_center or not name:
            continue
        if not name.startswith(MOD_PREFIX):
            continue
        if classes.get(name[len(MOD_PREFIX):]) != "MultiCrafter":
            continue
        data = mapdata[off:off + clen]
        rev, base = readbase_size(data)
        if len(data) - base == MULTICRAFTER_SIZE:
            continue                         # already current format, nothing to do
        if len(data) - base != LEGACY_GENERIC_CRAFTER_SIZE:
            raise ValueError("unexpected legacy payload of %d bytes at tile %d; refusing to guess"
                             % (len(data) - base, tile))
        targets.append((tile, off, clen, rev, base))

    if not targets:
        return mapdata, []

    out = bytearray()
    cursor = 0
    repaired = []
    for tile, off, clen, rev, base in targets:
        # off points at the chunk payload; the ushort length sits right before it
        len_off = off - 2
        out += mapdata[cursor:len_off]

        old = mapdata[off:off + clen]
        new = bytearray()
        new += bytes([rev])
        new += struct.pack(">f", FULL_HEALTH)          # clamped to block.health by readBase
        new += old[5:base]                             # rotation/team/version/enabled/modules/...
        new += bytes(MULTICRAFTER_SIZE)                # MultiCrafter state, all default
        assert len(new) == 1 + (base - 1) + MULTICRAFTER_SIZE, len(new)

        out += struct.pack(">H", len(new))
        out += new
        cursor = off + clen
        repaired.append((tile, clen, len(new)))

    out += mapdata[cursor:]
    return bytes(out), repaired


# --------------------------------------------------------------------------- #

def main():
    write = "--write" in sys.argv

    # The fixed 29-byte MultiCrafter payload is only correct while no recipe uses
    # payloads; fail loudly instead of silently corrupting maps if that changes.
    payloads = subprocess.run(["grep", "-rl", "PayloadStack", os.path.join(SRC_DIR, "fadingrevelations", "content")],
                              capture_output=True, text=True).stdout.strip()
    if payloads:
        sys.exit("MultiCrafter recipes now use PayloadStack (%s); MULTICRAFTER_SIZE must be recomputed."
                 % payloads.replace("\n", ", "))

    classes = java_classes()
    multicrafters = sorted(k for k, v in classes.items() if v == "MultiCrafter")
    print("MultiCrafter blocks in the mod: %s" % ", ".join(multicrafters))
    print()

    failures = 0
    for fn in sorted(os.listdir(MAP_DIR)):
        if not fn.endswith(".msav"):
            continue
        path = os.path.join(MAP_DIR, fn)
        blob = open(path, "rb").read()
        # preserve the original compression level so the diff stays inside the map chunk
        level = 9 if blob[:2] == b"\x78\xda" else 6
        raw = zlib.decompress(blob)

        try:
            version, chunks = split_container(raw)
            cmap = dict(chunks)
            blocks = block_names(cmap["content"])[1]     # ContentType.block ordinal
            problems, nent = verify(cmap["map"], blocks, classes, fn)
        except ValueError as e:
            print("%-28s STRUCTURAL FAILURE: %s" % (fn, e))
            failures += 1
            continue

        if version != SAVE_VERSION:
            print("%-28s save version %d (expected %d) - skipping" % (fn, version, SAVE_VERSION))
            continue

        newmap, repaired = repair(cmap["map"], blocks, classes)

        wrote = None
        if repaired and write:
            newchunks = [(n, newmap if n == "map" else d) for n, d in chunks]
            out = zlib.compress(build_container(version, newchunks), level)

            # the repaired file must still pass every check before it touches disk
            rversion, rchunks = split_container(zlib.decompress(out))
            assert rversion == version
            rcmap = dict(rchunks)
            rblocks = block_names(rcmap["content"])[1]
            rproblems, rent = verify(rcmap["map"], rblocks, classes, fn)
            _, rrep = repair(rcmap["map"], rblocks, classes)
            if rproblems or rrep:
                sys.exit("post-repair verification failed for %s: problems=%s still_to_repair=%s"
                         % (fn, rproblems, rrep))
            assert rent == nent, (rent, nent)
            # every chunk except `map` must be byte-identical
            for (on, od), (nn, nd) in zip(chunks, rchunks):
                assert on == nn and (od == nd or on == "map"), on

            open(path, "wb").write(out)
            wrote = (len(blob), len(out), rent)
            # report the post-repair state, not the pre-repair one
            problems = []

        if problems:
            failures += 1
        print("%-28s v%d  %dx%d  entities=%-6d %s" % (
            fn, version,
            struct.unpack(">H", newmap[:2])[0], struct.unpack(">H", newmap[2:4])[0],
            nent, "OK" if not problems else "%d PROBLEM(S)" % len(problems)))
        for p in problems:
            print("      ! %s" % p)
        for tile, old, new in repaired:
            print("      * repaired living-steel-complex at tile %d: entity chunk %d -> %d bytes"
                  % (tile, old, new))
        if wrote:
            print("      -> wrote %s (%d -> %d bytes, %d entities re-verified)"
                  % (fn, wrote[0], wrote[1], wrote[2]))

    print()
    if failures:
        print("FAILED: %d map(s) still inconsistent." % failures)
        return 1
    print("All maps consistent." + ("" if write else "  (dry run - pass --write to apply)"))
    return 0


if __name__ == "__main__":
    sys.exit(main())
