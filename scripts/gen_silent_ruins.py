#!/usr/bin/env python3
"""Generates assets/maps/silent-ruins.msav - the Hathor lore sector map.

Uses the same legacy Save7 map format as the mod's other maps (exordium.msav
as the donor: its content-header, entities and custom chunks are copied
verbatim; the meta and map regions are rebuilt).

Run: python3 scripts/gen_silent_ruins.py
"""
import json, random, struct, zlib, io, os

ROOT = os.path.join(os.path.dirname(__file__), '..')
DONOR = os.path.join(ROOT, 'assets/maps/exordium.msav')
OUT = os.path.join(ROOT, 'assets/maps/silent-ruins.msav')

W, H = 150, 200
SEED = 69421

# block ids from the mod's content header (see exordium.msav content region)
STONE, BASALT, DARKSAND = 31, 34, 38
AIR = 0
STONE_WALL, BOULDER = 76, 107
ORE_COPPER, ORE_LEAD, ORE_TITANIUM = 141, 142, 145

def rutf(b, p):
    ln = struct.unpack('>H', b[p:p+2])[0]; p += 2
    return b[p:p+ln].decode('utf-8','replace'), p+ln

def wutf(s):
    b = s.encode('utf-8')
    return struct.pack('>H', len(b)) + b

def chunks(raw, names):
    pos, out = 8, {}
    for name in names:
        ln = struct.unpack('>i', raw[pos:pos+4])[0]; pos += 4
        out[name] = raw[pos:pos+ln]; pos += ln
    assert pos == len(raw), (pos, len(raw))
    return out

def chunk(data):
    return struct.pack('>i', len(data)) + data

# ---------------------------------------------------------------- parse donor
raw = zlib.decompress(open(DONOR, 'rb').read())
assert raw[:4] == b'MSAV' and struct.unpack('>i', raw[4:8])[0] == 7
reg = chunks(raw, ['meta', 'content', 'map', 'entities', 'custom'])

m = reg['meta']; p = 0
cnt = struct.unpack('>h', m[p:p+2])[0]; p += 2
tags = {}
for _ in range(cnt):
    k, p = rutf(m, p); v, p = rutf(m, p)
    tags[k] = v

# ---------------------------------------------------------------- new map
rnd = random.Random(SEED)

# smooth value noise for terrain
def noise_grid(w, h, scale):
    gw, gh = w // scale + 2, h // scale + 2
    grid = [[rnd.random() for _ in range(gw)] for _ in range(gh)]
    out = [[0.0] * w for _ in range(h)]
    for y in range(h):
        for x in range(w):
            gx, gy = x / scale, y / scale
            x0, y0 = int(gx), int(gy)
            fx, fy = gx - x0, gy - y0
            fx = fx * fx * (3 - 2 * fx)
            fy = fy * fy * (3 - 2 * fy)
            a = grid[y0][x0] * (1 - fx) + grid[y0][x0 + 1] * fx
            b = grid[y0 + 1][x0] * (1 - fx) + grid[y0 + 1][x0 + 1] * fx
            out[y][x] = a * (1 - fy) + b * fy
    return out

base = noise_grid(W, H, 24)
detail = noise_grid(W, H, 7)

floors = [[0] * W for _ in range(H)]
overlays = [[0] * W for _ in range(H)]
blocks = [[AIR] * W for _ in range(H)]

for y in range(H):
    for x in range(W):
        v = base[y][x] * 0.85 + detail[y][x] * 0.15
        if v < 0.38:
            floors[y][x] = BASALT
        elif v < 0.55:
            floors[y][x] = STONE
        else:
            floors[y][x] = DARKSAND if detail[y][x] > 0.72 else STONE

# ore clusters (a quiet moon - just enough to build with)
def cluster(cx, cy, r, overlay):
    for y in range(max(0, cy - r), min(H, cy + r + 1)):
        for x in range(max(0, cx - r), min(W, cx + r + 1)):
            if (x - cx) ** 2 + (y - cy) ** 2 <= r * r and rnd.random() < 0.75:
                if blocks[y][x] == AIR:
                    overlays[y][x] = overlay

for cx, cy, r, ore in [
    (40, 60, 5, ORE_COPPER), (105, 48, 4, ORE_LEAD), (78, 130, 5, ORE_COPPER),
    (118, 152, 4, ORE_LEAD), (30, 160, 4, ORE_TITANIUM), (68, 20, 3, ORE_COPPER),
]:
    cluster(cx, cy, r, ore)

# ruins: broken rectangles and arcs of stone walls + scattered boulders
def ruin_rect(cx, cy, w, h):
    for x in range(cx - w // 2, cx + w // 2 + 1):
        for y in (cy - h // 2, cy + h // 2):
            if 1 <= x < W - 1 and 1 <= y < H - 1 and rnd.random() < 0.78:
                blocks[y][x] = STONE_WALL
    for y in range(cy - h // 2, cy + h // 2 + 1):
        for x in (cx - w // 2, cx + w // 2):
            if 1 <= x < W - 1 and 1 <= y < H - 1 and rnd.random() < 0.78:
                blocks[y][x] = STONE_WALL

def ruin_arc(cx, cy, r, a0, a1):
    import math
    steps = int((a1 - a0) * r)
    for i in range(steps):
        a = math.radians(a0 + (a1 - a0) * i / max(1, steps - 1))
        x, y = int(cx + math.cos(a) * r), int(cy + math.sin(a) * r)
        if 1 <= x < W - 1 and 1 <= y < H - 1 and rnd.random() < 0.8:
            blocks[y][x] = STONE_WALL

# a fallen structure near the center (the terminal lands near here)
ruin_rect(75, 96, 17, 11)
ruin_rect(75, 96, 9, 5)
ruin_arc(75, 96, 23, 20, 160)
# scattered remote ruins
ruin_rect(38, 40, 9, 7)
ruin_rect(112, 72, 7, 5)
ruin_rect(52, 158, 11, 6)
ruin_rect(102, 140, 8, 8)
ruin_arc(30, 110, 12, 200, 340)
ruin_arc(120, 175, 10, 30, 170)

for _ in range(90):
    x, y = rnd.randrange(2, W - 2), rnd.randrange(2, H - 2)
    if blocks[y][x] == AIR and overlays[y][x] == 0:
        blocks[y][x] = BOULDER

# ------------------------------------------------------------- write regions
def wutf_pad(s):
    b = s.encode('utf-8')
    return struct.pack('>H', len(b)) + b

# map region: floors RLE then blocks RLE
bio = io.BytesIO()
bio.write(struct.pack('>HH', W, H))
i = 0
n = W * H
flat_f, flat_o = [], []
for y in range(H):
    flat_f += floors[y]
    flat_o += overlays[y]
while i < n:
    cons = 0
    while i + cons + 1 < n and cons < 255 and flat_f[i + cons + 1] == flat_f[i] and flat_o[i + cons + 1] == flat_o[i]:
        cons += 1
    bio.write(struct.pack('>hhB', flat_f[i], flat_o[i], cons))
    i += cons + 1

flat_b = []
for y in range(H):
    flat_b += blocks[y]
i = 0
while i < n:
    cons = 0
    while i + cons + 1 < n and cons < 255 and flat_b[i + cons + 1] == flat_b[i]:
        cons += 1
    bio.write(struct.pack('>hB', flat_b[i], 0))  # block id + packed(no build, no data)
    bio.write(struct.pack('>B', cons))
    i += cons + 1
map_region = bio.getvalue()

# meta region: tags copied from donor, with replacements
# arc's JsonIO writes minimal JSON (unquoted keys) - patch with brace balancing
rules = tags['rules']
def replace_key(src, key, value):
    idx = src.find(key + ':[')
    assert idx != -1, key
    depth, pos = 0, idx + len(key) + 1
    while True:
        if src[pos] == '[':
            depth += 1
        elif src[pos] == ']':
            depth -= 1
            if depth == 0:
                break
        pos += 1
    return src[:idx + len(key) + 1] + value + src[pos:]

# a quiet moon: no enemy waves
rules = replace_key(rules, 'spawns', '')
rules = replace_key(rules, 'objectives', '')

new_tags = dict(tags)
new_tags.update({
    'name': 'Silent Ruins',
    'author': 'UnQuis',
    'description': 'Nothing fights back on Hathor - nothing is left to fight. Search the ruins for the other side of the story.',
    'width': str(W),
    'height': str(H),
    'rules': rules,
    'saved': '0',
    'playtime': '0',
    'tick': '0.0',
    'wave': '0',
    'wavetime': '3600.0',
    'stats': '{}',
})

bio = io.BytesIO()
bio.write(struct.pack('>h', len(new_tags)))
for k, v in new_tags.items():
    bio.write(wutf_pad(k))
    bio.write(wutf_pad(v))
meta_region = bio.getvalue()

# assemble: zlib(MSAV + int 7 + [meta][content][map][entities][custom])
out = b'MSAV' + struct.pack('>i', 7)
out += chunk(meta_region)
out += chunk(reg['content'])
out += chunk(map_region)
out += chunk(reg['entities'])
out += chunk(reg['custom'])
open(OUT, 'wb').write(zlib.compress(out, 9))
print('written', OUT, os.path.getsize(OUT), 'bytes')

# ---------------------------------------------------------------- self-check
raw2 = zlib.decompress(open(OUT, 'rb').read())
assert raw2[:8] == out[:8]
reg2 = chunks(raw2, ['meta', 'content', 'map', 'entities', 'custom'])
m2 = reg2['meta']; p = 0
cnt = struct.unpack('>h', m2[p:p+2])[0]; p += 2
t2 = {}
for _ in range(cnt):
    k, p = rutf(m2, p); v, p = rutf(m2, p)
    t2[k] = v
assert int(t2['width']) == W and int(t2['height']) == H

# walk the map region, count tiles
mm = reg2['map']
w2, h2 = struct.unpack('>HH', mm[:4])
pos, tiles = 4, 0
while tiles < w2 * h2:
    f, o, cons = struct.unpack('>hhB', mm[pos:pos+5]); pos += 5
    tiles += cons + 1
assert tiles == w2 * h2, tiles
pos2, bcount = pos, 0
while bcount < w2 * h2:
    b, packed = struct.unpack('>hB', mm[pos2:pos2+3]); pos2 += 3
    assert packed == 0
    cons = mm[pos2]; pos2 += 1
    bcount += cons + 1
assert bcount == w2 * h2, bcount
print('self-check OK: %dx%d, tiles=%d, blocks=%d, consumed %d/%d' % (w2, h2, tiles, bcount, pos2, len(mm)))
