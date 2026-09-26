#!/usr/bin/env python3
"""
Repair legacy `me-*` content names in the shipped campaign maps.

BACKGROUND
----------
The maps were authored against the original mod whose id was `me`, so their MSAV
content headers contain `me-<name>` entries. This mod's id is now
`fading-revelations-remake`, so Mindustry resolves those names as
`<currentMod>-<name>` and a raw `me-...` string matches nothing:

  * a block that does not resolve is replaced by `Blocks.air` (a hole);
  * the only legacy-name fallback in the loader is block-only
    (`SaveVersion.readContentHeader` applies `SaveFileReader.fallback` for
    ContentType.block only), so units cannot be fixed from code at all.

WHAT IS ACTUALLY BROKEN
-----------------------
The header also *declares* many `me-*` entries that nothing references, so the
count of dead names badly overstates the damage. This script reports, per map:

  * names that are declared AND placed  -> these are the ones that matter;
  * names that are declared but unused   -> cosmetic header noise.

`--check` prints that inventory and the effect of the repair table without
touching anything, so the real blast radius can be seen before committing to it.

THE REPAIR
----------
Names are rewritten **in place** inside the content header. Content ids are
positional, so replacing a name in slot N keeps every tile and building pointing
at the same slot - the `map` chunk is never touched, which is what makes this
safe. Only blocks whose replacement has the **same size** are substituted: a 2x2
placement whose block becomes 3x3 would break `Placement2x2` and the stored
entity chunks.

Anything not in the table is left alone on purpose. A dead name already loads as
`Blocks.air`, so deleting it would gain nothing functionally while forcing an
index remap across the whole map; it is simply reported so a human can decide
whether to re-place it in the editor.

Usage:
    python3 scripts/fix_legacy_map_names.py --check
    python3 scripts/fix_legacy_map_names.py --write
"""

import argparse
import collections
import glob
import os
import re
import struct
import sys
import zlib

ROOT = os.path.dirname(os.path.dirname(os.path.abspath(__file__)))
MAP_DIR = os.path.join(ROOT, "assets", "maps")
SRC_DIR = os.path.join(ROOT, "src")

MAGIC = b"MSAV"
CHUNK_NAMES = ["meta", "content", "map", "entities", "custom"]

# ContentType ordinals in v160.5 (verified against mindustry/ctype/ContentType)
CT_ITEM, CT_BLOCK, CT_LIQUID, CT_STATUS, CT_UNIT, CT_SECTOR, CT_PLANET = 0, 1, 4, 5, 6, 9, 13

# --------------------------------------------------------------------------- #
# Repair table
# --------------------------------------------------------------------------- #
# key: legacy name as stored in the maps -> (new name, size of new block)
# Only entries whose replacement is the same size AND the same functional role
# are listed. Verified against the original mod's JSON in FROriginal/:
#
#   me-weave              LiquidTurret  size 2  range 160  (liquid turret)
#   me-zephyr             LiquidTurret  size 3  range 200  (steam knockback)
#   me-sprunkler          LiquidTurret  size 3  range 200  (water, firefighting)
#   me-pneumatic-sieve    GenericCrafter size 2  production
#   me-bc-trading-station GenericCrafter size 2 effect
#   me-ammo-crafter-1     GenericCrafter size 2  crafting
#
# `batter` is the mod's LiquidTurret replacement: size 3, range 200 - an exact
# match for zephyr and sprunkler. There is no size-2 liquid turret, so `weave`
# has no safe substitute and is left as-is (reported instead). The three
# size-2 crafters have no equivalent block at all, so they are left too.

BLOCK_SUBSTITUTIONS = {
    "me-zephyr": "batter",      # size 3 -> 3
    "me-sprunkler": "batter",   # size 3 -> 3
}

# Unit renames taken verbatim from the author's own table in the (now removed)
# scripts/update_bundles.py. Every target is a real current unit.
UNIT_RENAMES = {
    # the map author used a shorthand here; the content is `cerberian-auctus`
    "me-auctus": "cerberian-auctus",
    "me-cerberian-antumbra": "cerberian-penumbra",
    "me-cerberian-atrax": "cerberian-hexathelid",
    "me-cerberian-arkyid": "cerberian-curtulus",
    "me-cerberian-crawler": "cerberian-straggle",
    "me-cerberian-dagger": "cerberian-bayonet",
    "me-cerberian-eclipse": "cerberian-veil",
    "me-cerberian-flare": "cerberian-spark",
    "me-cerberian-fortress": "cerberian-citadel",
    "me-cerberian-horizon": "cerberian-vista",
    "me-cerberian-mace": "cerberian-cudgel",
    "me-cerberian-reign": "cerberian-kaiser",
    "me-cerberian-scepter": "cerberian-baton",
    "me-cerberian-spiroct": "cerberian-nephila",
    "me-cerberian-toxopid": "cerberian-setosus",
    "me-cerberian-zenith": "cerberian-summit",
    "me-test": "lycosid",
    "me-cryo-crawler": "toruct",
    "me-fire-crawler": "reduct",
    "me-sps": "sps-strahl",
}

MOD_ID = "fading-revelations-remake"


# --------------------------------------------------------------------------- #
# container / content-header handling
# --------------------------------------------------------------------------- #

def split_container(raw):
    if raw[:4] != MAGIC:
        raise ValueError("not an MSAV file: bad magic %r" % (raw[:4],))
    version = struct.unpack(">i", raw[4:8])[0]
    pos, chunks = 8, []
    for name in CHUNK_NAMES:
        if pos + 4 > len(raw):
            raise ValueError("truncated container at %r" % name)
        length = struct.unpack(">i", raw[pos:pos + 4])[0]
        if length < 0 or pos + 4 + length > len(raw):
            raise ValueError("chunk %r length %d out of range" % (name, length))
        chunks.append((name, raw[pos + 4:pos + 4 + length]))
        pos += 4 + length
    if pos != len(raw):
        raise ValueError("%d trailing bytes" % (len(raw) - pos))
    return version, chunks


def build_container(version, chunks):
    out = bytearray(MAGIC)
    out += struct.pack(">i", version)
    for _, data in chunks:
        out += struct.pack(">i", len(data))
        out += data
    return bytes(out)


def parse_content_header(chunk):
    """-> (ordinal, [names]) in file order, plus the trailing bytes (if any)."""
    p = 0
    ntypes = chunk[p]; p += 1
    types = []
    for _ in range(ntypes):
        ctype = chunk[p]; p += 1
        count = struct.unpack(">h", chunk[p:p + 2])[0]; p += 2
        names = []
        for _ in range(count):
            ln = struct.unpack(">H", chunk[p:p + 2])[0]; p += 2
            names.append(chunk[p:p + ln].decode("utf-8", "replace"))
            p += ln
        types.append((ctype, names))
    return types, chunk[p:]


def build_content_header(types, tail):
    out = bytearray([len(types)])
    for ctype, names in types:
        out += struct.pack(">B h", ctype, len(names))
        for n in names:
            b = n.encode("utf-8")
            out += struct.pack(">H", len(b)) + b
    return bytes(out) + tail


def rename_in_content_header(chunk):
    """Apply the repair tables in place. -> (new_chunk, changes)"""
    types, tail = parse_content_header(chunk)
    changes = []
    for idx, (ctype, names) in enumerate(types):
        for j, name in enumerate(names):
            new = None
            if name.startswith("me-"):
                if ctype == CT_BLOCK and name in BLOCK_SUBSTITUTIONS:
                    new = MOD_ID + "-" + BLOCK_SUBSTITUTIONS[name]
                elif ctype == CT_UNIT:
                    target = resolve_legacy(name)
                    if target:
                        new = MOD_ID + "-" + target
            if new:
                changes.append((ctype, name, new))
                names[j] = new
    return build_content_header(types, tail), changes


# --------------------------------------------------------------------------- #
# meta region: the `rules` tag carries the wave definitions
# --------------------------------------------------------------------------- #
# Mindustry stores map metadata as a flat list of length-prefixed UTF-8
# key/value pairs:
#     >h  count
#     (>H len key)(>H len value) * count
# `rules` is a minimal-JSON string, and its `spawns` array is where enemy waves
# live - it references units by name (`{type:me-cerberian-dagger,...}`).
# These references are NOT covered by the content header rename: the loader
# resolves them separately, and an unresolvable name silently falls back to
# vanilla UnitTypes.dagger (SpawnGroup.read), so a campaign wave would end up
# spawning Crux daggers.
#
# Only lengthening edits are safe here. Shortening the `rules` value produces a
# meta region Mindustry refuses to read
# ("SerializationException ... near: ...spawns:*ERROR*]..."), even when the result
# is valid JSON, so the table below is only ever applied in the growing direction
# (every replacement gains the `fading-revelations-remake-` prefix).

def parse_tags(chunk):
    cnt = struct.unpack(">h", chunk[:2])[0]
    p, out = 2, []
    for _ in range(cnt):
        kl = struct.unpack(">H", chunk[p:p + 2])[0]; p += 2
        k = chunk[p:p + kl].decode("utf-8"); p += kl
        vl = struct.unpack(">H", chunk[p:p + 2])[0]; p += 2
        v = chunk[p:p + vl].decode("utf-8"); p += vl
        out.append((k, v))
    if p != len(chunk):
        raise ValueError("meta tag walk left %d bytes" % (len(chunk) - p))
    return out


def build_tags(tags):
    out = bytearray(struct.pack(">h", len(tags)))
    for k, v in tags:
        for s in (k, v):
            b = s.encode("utf-8")
            out += struct.pack(">H", len(b)) + b
    return bytes(out)


_unit_ref = re.compile(r"type:(me-[a-z0-9\-]+)")


def current_names():
    """Every content name the mod actually defines, from the Java sources."""
    names = set()
    ctor = re.compile(r'new\s+[\w.]+\s*\(\s*"([a-z][a-z0-9\-]*)"')
    for path in glob.glob(os.path.join(SRC_DIR, "**", "*.java"), recursive=True):
        src = open(path, encoding="utf-8").read()
        names.update(ctor.findall(src))
    return names


CURRENT = current_names()


def resolve_legacy(name):
    """me-<x> -> current content name, or None if nothing matches.

    Handles both the renamed units (via UNIT_RENAMES) and the plain case where the
    name never changed and only the `me-` prefix is stale.
    """
    bare = name[3:]
    if bare in UNIT_RENAMES.values() or bare in CURRENT:
        return bare
    if name in UNIT_RENAMES:
        return UNIT_RENAMES[name]
    return None


def rename_spawn_refs(meta_chunk):
    """Rewrite `type:me-*` in the rules tag. -> (new_chunk, [(old, new)], unresolved)"""
    tags = parse_tags(meta_chunk)
    changes, unresolved = [], []
    out = []
    for k, v in tags:
        if k != "rules":
            out.append((k, v))
            continue

        def sub(m):
            old = m.group(1)
            target = resolve_legacy(old)
            if not target:
                unresolved.append(old)
                return m.group(0)
            new = "%s-%s" % (MOD_ID, target)
            changes.append((old, new))
            return "type:" + new

        out.append((k, _unit_ref.sub(sub, v)))
    return build_tags(out), changes, sorted(set(unresolved))


# --------------------------------------------------------------------------- #
# tile-stream walk (only to count which names are actually placed)
# --------------------------------------------------------------------------- #

def walk_block_ids(mapdata, block_names):
    """Yield the block name used for every tile. Mirrors ShortChunkSaveVersion.readMap."""
    width, height = struct.unpack(">HH", mapdata[:4])
    total = width * height
    p = 4
    tiles = 0
    while tiles < total:
        p += 5
        tiles += mapdata[p - 1] + 1
    if tiles != total:
        raise ValueError("floor RLE mismatch")

    i = 0
    while i < total:
        bid = struct.unpack(">h", mapdata[p:p + 2])[0]; p += 2
        packed = mapdata[p]; p += 1
        name = block_names[bid] if 0 <= bid < len(block_names) else None
        if packed & 4:
            p += 7
        if packed & 1:
            is_center = mapdata[p]; p += 1
            if is_center:
                clen = struct.unpack(">H", mapdata[p:p + 2])[0]; p += 2
                p += clen
        else:
            cons = mapdata[p]; p += 1
            i += cons
        i += 1
        yield name
    if i != total:
        raise ValueError("block RLE mismatch (%d vs %d)" % (i, total))
    if p != len(mapdata):
        raise ValueError("map chunk left %d bytes unread" % (len(mapdata) - p))


# --------------------------------------------------------------------------- #

def main():
    ap = argparse.ArgumentParser()
    ap.add_argument("--write", action="store_true")
    args = ap.parse_args()

    maps = sorted(glob.glob(os.path.join(MAP_DIR, "*.msav")))
    if not maps:
        print("no maps found in %s" % MAP_DIR)
        return 2

    total_substituted = collections.Counter()
    total_still_dead = collections.Counter()
    header_noise = set()
    spawn_fixed = collections.Counter()
    spawn_unresolved = collections.Counter()
    changed_files = 0

    for path in maps:
        name = os.path.basename(path)
        blob = open(path, "rb").read()
        version, chunks = split_container(zlib.decompress(blob))
        new_chunks = list(chunks)
        ci = [i for i, (n, _) in enumerate(chunks) if n == "content"][0]
        mi = [i for i, (n, _) in enumerate(chunks) if n == "map"][0]
        xi = [i for i, (n, _) in enumerate(chunks) if n == "meta"][0]

        types, _ = parse_content_header(chunks[ci][1])
        block_names = dict(types).get(CT_BLOCK, [])

        used = collections.Counter()
        used.update(n for n in walk_block_ids(chunks[mi][1], block_names) if n)

        new_header, changes = rename_in_content_header(chunks[ci][1])
        for ctype, old, new in changes:
            total_substituted[old] += used.get(old, 0)

        new_meta, spawn_changes, unresolved = rename_spawn_refs(chunks[xi][1])
        for old, new in spawn_changes:
            spawn_fixed[old] += 1
        for u in unresolved:
            spawn_unresolved[u] += 1

        # what stays dead after the repair
        after = dict(parse_content_header(new_header)[0])
        for ctype in (CT_BLOCK, CT_UNIT, CT_ITEM, CT_LIQUID, CT_STATUS):
            for n in after.get(ctype, []):
                if not n.startswith("me-"):
                    continue
                if ctype == CT_BLOCK and n in used:
                    total_still_dead[n] += used[n]
                else:
                    header_noise.add(n)        # declared but never placed

        touched = False
        if new_header != chunks[ci][1]:
            new_chunks[ci] = ("content", new_header)
            touched = True
        if new_meta != chunks[xi][1]:
            new_chunks[xi] = ("meta", new_meta)
            touched = True
        if touched:
            changed_files += 1
            if args.write:
                out = zlib.compress(build_container(version, new_chunks), 9)
                # re-verify the emitted bytes before touching disk
                v2, c2 = split_container(zlib.decompress(out))
                assert v2 == version and [n for n, _ in c2] == [n for n, _ in chunks]
                assert dict(c2)["map"] == chunks[mi][1]
                # and that the meta tag walk still round-trips exactly
                assert build_tags(parse_tags(dict(c2)["meta"])) == dict(c2)["meta"]
                open(path, "wb").write(out)

        print("%-28s %d header, %d spawn-ref rewrite(s)"
              % (name, len(changes), len(spawn_changes)))

    print()
    print("substituted (a working block now stands where the removed one was):")
    for k, v in sorted(total_substituted.items(), key=lambda x: -x[1]):
        print("   %-28s %6d tile(s) -> %s" % (k, v, BLOCK_SUBSTITUTIONS.get(k, UNIT_RENAMES.get(k, "?"))))
    if not total_substituted:
        print("   (none)")

    print()
    print("STILL DEAD and actually placed - these load as Blocks.air and need an editor pass:")
    if total_still_dead:
        for k, v in sorted(total_still_dead.items(), key=lambda x: -x[1]):
            print("   %-28s %6d tile(s)" % (k, v))
    else:
        print("   (none)")

    print()
    print("enemy waves (rules.spawns) re-pointed at surviving units:")
    if spawn_fixed:
        for k, v in sorted(spawn_fixed.items()):
            print("   %-34s %d wave(s) -> %s" % (k, v, UNIT_RENAMES.get(k, "?")))
    else:
        print("   (none)")
    if spawn_unresolved:
        print()
        print("waves still pointing at removed units - they will spawn vanilla Crux dagger:")
        for k, v in sorted(spawn_unresolved.items(), key=lambda x: -x[1]):
            print("   %-34s %d map(s)" % (k, v))

    print()
    print("Also %d distinct legacy names are declared in headers but never placed (cosmetic -"
          % len(header_noise))
    print("they resolve to nothing but nothing references them either).")
    print()
    if args.write:
        print("rewrote %d map file(s)." % changed_files)
    else:
        print("read-only; pass --write to apply (%d file(s) would change)" % changed_files)
    return 0


if __name__ == "__main__":
    sys.exit(main())
