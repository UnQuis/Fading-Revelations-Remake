"""Single authoritative reader/writer for the Save7 `map` region.

Every tool in this directory used to carry its own copy of the block-section
walker, and two of them were wrong: they read the `is centre` flag and, when it
was false, failed to consume the length-prefixed payload that still followed.
That desynchronised the walk, and because the reader never checked that it had
consumed the whole region, it just quietly produced an empty-looking map. The
symptom was an etnatic-isles dump that reported 0 buildings and 36 walls for a
map that actually has 11,501 building records and ~3,700 walls.

So: one implementation, and it asserts that it landed exactly on the end of the
region. If the format changes, that assert fires instead of the tool lying.

Format (verified against the v160.5 jars and this map):

    short width, short height
    RLE  (short floor, short overlay, byte run) x n        run counts *following*
    tiles, so a record covers run+1 tiles
    tile (short block id, byte flags) x n                   flags: bit0 building,
        bit1 legacy 1-byte data (only without a building), bit2 7-byte data
        bit0 set:  byte is centre; then, if centre, a 2-byte length and that
                   many bytes of `build.writeAll()`
        no building and no data:  a run byte follows instead
"""
import math
import os
import random
import struct
import zlib

BUILDING = 1
LEGACY_DATA = 2
NEW_DATA = 4


class MapRegion:
    def __init__(self, width, height, floors, overlays, blocks, records):
        self.width = width
        self.height = height
        self.floors = floors          # list[int] of block id
        self.overlays = overlays
        self.blocks = blocks
        # records: list of (index, block id, is centre, payload bytes)
        # index is the flat tile index the record was written for
        self.records = records

    def count(self):
        return self.width * self.height

    def idx(self, x, y):
        return y * self.width + x

    def xy(self, i):
        return i % self.width, i // self.width


def read_map_region(region):
    width, height = struct.unpack('>HH', region[:4])
    n = width * height

    pos = 4
    floors, overlays = [], []
    while len(floors) < n:
        floor, overlay, run = struct.unpack('>hhB', region[pos:pos + 5])
        pos += 5
        floors += [floor] * (run + 1)
        overlays += [overlay] * (run + 1)
    if len(floors) != n:
        raise ValueError('floor RLE covered %d tiles, expected %d' % (len(floors), n))

    blocks = [0] * n
    records = []
    i = 0
    while i < n:
        start = i
        bid, flags = struct.unpack('>hB', region[pos:pos + 3])
        pos += 3
        blocks[i] = bid
        i += 1

        if flags & NEW_DATA:
            pos += 7
        if flags & BUILDING:
            centre = region[pos]
            pos += 1
            payload = b''
            if centre:
                ln = struct.unpack('>H', region[pos:pos + 2])[0]
                pos += 2
                payload = region[pos:pos + ln]
                pos += ln
            records.append((start, bid, bool(centre), payload))
        elif flags & LEGACY_DATA:
            pos += 1
        else:
            run = region[pos]
            pos += 1
            for _ in range(run):
                blocks[i] = bid
                i += 1

    if i != n:
        raise ValueError('block walk ended at %d, expected %d' % (i, n))
    if pos != len(region):
        raise ValueError('block walk ended at %d of %d bytes - format mismatch'
                         % (pos, len(region)))
    return MapRegion(width, height, floors, overlays, blocks, records)


def write_map_region(width, height, floors, overlays, blocks, buildings):
    """buildings: {flat index: (block id, is centre, payload)}."""
    n = width * height
    out = bytearray()
    out += struct.pack('>HH', width, height)

    i = 0
    while i < n:
        run = 0
        while i + run + 1 < n and run < 255 \
                and floors[i + run + 1] == floors[i] and overlays[i + run + 1] == overlays[i]:
            run += 1
        out += struct.pack('>hhB', floors[i], overlays[i], run)
        i += run + 1

    i = 0
    while i < n:
        if i in buildings:
            bid, centre, payload = buildings[i]
            out += struct.pack('>hB', bid, BUILDING)
            out += struct.pack('>B', 1 if centre else 0)
            if centre:
                out += struct.pack('>H', len(payload)) + payload
            i += 1
            continue
        run = 0
        while i + run + 1 < n and run < 255 \
                and blocks[i + run + 1] == blocks[i] and (i + run + 1) not in buildings:
            run += 1
        out += struct.pack('>hB', blocks[i], 0)
        out += struct.pack('>B', run)
        i += run + 1
    return bytes(out)


# ------------------------------------------------------------ whole file ---

def split_regions(raw):
    """All length-prefixed regions, in order, as [(length, bytes)].

    Written with a fixed five-name list, this silently dropped any region it
    did not know about - which is how a backup of a map came out corrupted and
    then analysed as an empty world.
    """
    pos, out = 8, []
    while pos + 4 <= len(raw):
        ln = struct.unpack('>i', raw[pos:pos + 4])[0]
        pos += 4
        out.append((ln, raw[pos:pos + ln]))
        pos += ln
    if pos != len(raw):
        raise ValueError('region walk ended at %d of %d bytes' % (pos, len(raw)))
    return out


def join_regions(raw, regions):
    out = bytearray(raw[:8])
    for data in regions:
        out += struct.pack('>i', len(data)) + data
    return bytes(out)


# ============================================================== file level ==
#
# The format-level and noise helpers used to live in gen_sector_maps.py, which
# was a sector-map *generator*. Deleting the generator therefore took the only
# working reader of a .msav with it, while three tools that generate nothing
# still need to read one. A reader is not a generator's job.

MAPS = os.path.join(os.path.dirname(os.path.dirname(os.path.abspath(__file__))),
                    'assets', 'maps')

# A map is written as meta, content, map, entities, custom. A world save has more
# regions; only the first three are ever unambiguous by position, so that is all
# the named accessors promise.
REGION_NAMES = ('meta', 'content', 'map', 'entities', 'markers', 'custom')


def load_map(path):
    raw = zlib.decompress(open(path, 'rb').read())
    assert raw[:4] == b'MSAV', 'not a map file: %s' % path
    version = struct.unpack('>i', raw[4:8])[0]
    assert version == 7, 'unexpected save version %d in %s' % (version, path)
    chunks = [data for _ln, data in split_regions(raw)]
    out = {'raw': chunks}
    for i, name in enumerate(REGION_NAMES):
        if i < len(chunks):
            out[name] = chunks[i]
    return out


def rutf(b, p):
    ln = struct.unpack('>H', b[p:p + 2])[0]
    p += 2
    return b[p:p + ln].decode('utf-8', 'replace'), p + ln


def wutf(value):
    b = value.encode('utf-8')
    return struct.pack('>H', len(b)) + b


def chunk(data):
    return struct.pack('>i', len(data)) + data


def parse_tags(region):
    p = 0
    cnt = struct.unpack('>h', region[p:p + 2])[0]
    p += 2
    tags = {}
    for _ in range(cnt):
        key, p = rutf(region, p)
        value, p = rutf(region, p)
        tags[key] = value
    return tags


def build_tags(tags):
    out = struct.pack('>h', len(tags))
    for key, value in tags.items():
        out += wutf(key) + wutf(value)
    return out


def parse_content(region):
    """-> ({type ordinal: [names]}, [ordinals in file order]).

    Ids are positional *within* a type, and the game resolves each name against
    the current content - so a name in a header that no longer exists does not
    fail, it resolves to nothing. See the header-generation notes in AGENTS.md.
    """
    p = 0
    count = region[p]
    p += 1
    tables, order = {}, []
    for _ in range(count):
        ordinal = region[p]
        p += 1
        total = struct.unpack('>h', region[p:p + 2])[0]
        p += 2
        names = []
        for _ in range(total):
            name, p = rutf(region, p)
            names.append(name)
        tables[ordinal] = names
        order.append(ordinal)
    return tables, order


def build_content(tables, order):
    out = struct.pack('>B', len(order))
    for ordinal in order:
        names = tables[ordinal]
        out += struct.pack('>Bh', ordinal, len(names))
        for name in names:
            out += wutf(name)
    return out


def block_names(path):
    """Block-name table of a .msav, as a list indexed by block id."""
    return parse_content(load_map(path)['content'])[0][1]


# ------------------------------------------------------------------ noise ---
class Perlin:
    """Seeded gradient noise, enough for terrain that reads like the game's own
    planet generators (which are simplex + distort + median)."""

    def __init__(self, seed):
        rnd = random.Random(seed)
        self.perm = list(range(256))
        rnd.shuffle(self.perm)
        self.perm *= 2

    def _grad(self, h, x, y):
        h &= 7
        u = x if h < 4 else y
        v = y if h < 4 else x
        return ((u if (h & 1) == 0 else -u) + (v if (h & 2) == 0 else -v))

    def noise(self, x, y):
        xi, yi = math.floor(x), math.floor(y)
        xf, yf = x - xi, y - yi
        xi, yi = int(xi) & 255, int(yi) & 255
        u = xf * xf * xf * (xf * (xf * 6 - 15) + 10)
        v = yf * yf * yf * (yf * (yf * 6 - 15) + 10)
        p = self.perm
        aa = p[p[xi] + yi]
        ab = p[p[xi] + yi + 1]
        ba = p[p[xi + 1] + yi]
        bb = p[p[xi + 1] + yi + 1]
        x1 = self._grad(aa, xf, yf) + u * (self._grad(ba, xf - 1, yf) - self._grad(aa, xf, yf))
        x2 = self._grad(ab, xf, yf - 1) + u * (self._grad(bb, xf - 1, yf - 1)
                                               - self._grad(ab, xf, yf - 1))
        return (x1 + v * (x2 - x1)) * 0.7


def rescale(field, low, high):
    """Stretch a field so its min/max land exactly on low/high."""
    lo = min(min(row) for row in field)
    hi = max(max(row) for row in field)
    span = (hi - lo) or 1.0
    k = (high - low) / span
    for row in field:
        for i in range(len(row)):
            row[i] = (row[i] - lo) * k + low


def quantile_threshold(values, keep):
    """Threshold with exactly `keep` of the samples above it.

    A fixed cut-off on a normalised noise field does not mean the same thing on
    every map, so a coverage setting has to be a quantile to be a real fraction
    rather than a magic number.
    """
    if not values:
        return 1.0
    ordered = sorted(values)
    k = int(round(len(ordered) * (1.0 - keep)))
    return ordered[max(0, min(len(ordered) - 1, k))]
