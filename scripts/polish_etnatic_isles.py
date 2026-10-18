#!/usr/bin/env python3
# -*- coding: utf-8 -*-
"""
Polishes assets/maps/etnatic_isles in place, and deliberately keeps the
hand-authored map.

WHY THIS EXISTS
---------------
The first attempt replaced the whole map with fresh procedural terrain, which
was the wrong call: the hand-authored version already has a good island layout,
its cores and a working base, and replacing it threw all of that away. What it
lacked was *variety*, not a new shape. Measured on the original:

    floors    deep-water 40.4%  sand-floor 26.4%  shale 21.5%  shallow-water 11.6%
    walls     sand-wall 121,069   shale-wall 38,492   (~2,300 more)
    decor     none at all outside the base
    buildings 11,501 records, 6,074 of them team 0, 5 cores

Two floors and two walls across a whole planet. Every island is the same tan
patch with the same tan wall, and the only relief is the base. So this changes
*what the blocks are*, not where they are:

  1. FLOORS.  Land tiles get a patchwork drawn from a palette per source floor,
     at two noise scales so it reads as organic patches rather than static. The
     beach ring - tiles within 2 of water - is left alone, because the
     coastlines are the part of the hand-authored map that is actually good.
  2. WALLS.   The author's walled/unwalled mask is *preserved*, since that shape
     is theirs; only the wall block changes, to something that suits the new
     floor. The walls standing in the sea (60,334 of them) are the map's most
     distinctive feature, so they stay - they just become reef and ice instead of
     one uniform sand wall. A few new walls go on open land to break up the flats.
  3. DECOR.   There is none outside the base, which is why the islands read as
     bare. Boulders and dead trees are scattered on unwalled land.
  4. BASES.   Five cores means five bases, and the original has one sprawling
     carpet instead. Each core gets a square compound - metal pad, wall perimeter
     with a gate, power row, belt spine, turrets - and player buildings outside
     every compound are cleared. **All five cores are kept, in place.** Enemy
     buildings are kept too.

HOW BUILDINGS ARE PLACED WITHOUT DECODING PAYLOADS
--------------------------------------------------
A payload is position independent but must belong to the right block and the
right *size*, so nothing is synthesised: every placed building reuses a payload
harvested from a real record of the same block in the same map. Whether a block
is safe to copy is decided from the map itself rather than a hand-written list -
a block is copyable when *every* record of it in this map carries a payload.
A 2x2 always produces three non-centre records and a 3x3 produces eight, so the
rule excludes every multiblock automatically. In this map that leaves
`titanium-wall`, `copper-wall`, `titanium-conveyor`, `conduit`, `pulse-conduit`,
`power-node`, `battery`, `mender`, `scorch`, `hail`, `fast-unloader` and
friends, and rules out the drills, `titanium-wall-large`, `lancer`,
`steam-generator` and every other multiblock. The old base's drills and cores are
simply kept rather than re-placed.

Belt spines are re-laid from harvested *contiguous straight runs*: links are
stored relative to the block, so a straight run keeps working when it is moved.

ONLY THE `map` REGION IS REWRITTEN. `meta` (including `rules`, whose length is
coupled to the framing in a way that makes shortening it fatal) and the content
header are copied through byte for byte - which also means the polished map
inherits whatever `meta` its source has, so the source is never renamed.

A map is keyed in Mindustry by its *filename*, not by its `name` tag, so
`etnatic-isles-original.msav` needs no renaming to avoid shadowing
`etnatic-isles.msav`: their keys are `Etnatic_Isles_Original` and
`Etnatic_Isles`.

    python3 scripts/polish_etnatic_isles.py            # rewrite the map
    python3 scripts/polish_etnatic_isles.py --check    # report, write nothing
"""
import argparse
import os
import random
import struct
import sys
import zlib
from collections import Counter

sys.path.insert(0, os.path.dirname(os.path.abspath(__file__)))

import msav  # noqa: E402
import shutil  # noqa: E402
from msav import (MAPS, Perlin, chunk, load_map, parse_content,  # noqa: E402
                   parse_tags, quantile_threshold, rescale)

TARGET = os.path.join(MAPS, 'etnatic-isles.msav')
# The polish always runs from the pristine hand-authored map, never from its own
# output. Repainting an already-repainted map is not idempotent - the patchwork
# would be applied on top of itself - and it would also mean the committed file
# is no longer reproducible from anything in the repository.
SOURCE = os.path.join(MAPS, 'etnatic-isles-original.msav')

# ---------------------------------------------------------------- palettes --

WATER = ('deep-water', 'shallow-water', 'darksand-water')

# source floor -> patchwork. None in the list means "not a patch target".
FLOORS = {
    'sand-floor':    [('sand-floor', 0.30), ('darksand', 0.18), ('salt', 0.10),
                      ('grass', 0.16), ('moss', 0.14), ('spore-moss', 0.12)],
    'darksand':      [('darksand', 0.36), ('sand-floor', 0.30), ('char', 0.18), ('salt', 0.16)],
    'shale':         [('shale', 0.30), ('stone', 0.14), ('basalt', 0.12), ('carbon-stone', 0.11),
                      ('yellow-stone', 0.09), ('dirt', 0.12), ('moss', 0.12)],
    'stone':         [('stone', 0.38), ('shale', 0.24), ('basalt', 0.18), ('dirt', 0.10), ('moss', 0.10)],
    'basalt':        [('basalt', 0.42), ('shale', 0.24), ('carbon-stone', 0.20), ('char', 0.14)],
    'carbon-stone':  [('carbon-stone', 0.44), ('char', 0.24), ('shale', 0.20), ('stone', 0.12)],
    'yellow-stone':  [('yellow-stone', 0.42), ('shale', 0.26), ('dirt', 0.18), ('stone', 0.14)],
    'dirt':          [('dirt', 0.40), ('grass', 0.28), ('moss', 0.20), ('shale', 0.12)],
    'moss':          [('moss', 0.42), ('grass', 0.28), ('dirt', 0.18), ('spore-moss', 0.12)],
    'spore-moss':    [('spore-moss', 0.42), ('moss', 0.30), ('dirt', 0.18), ('grass', 0.10)],
    'char':          [('char', 0.38), ('carbon-stone', 0.26), ('basalt', 0.22), ('darksand', 0.14)],
    'salt':          [('salt', 0.50), ('shallow-water', 0.22), ('sand-floor', 0.18), ('ice', 0.10)],
    'snow':          [('snow', 0.48), ('ice', 0.24), ('shale', 0.16), ('stone', 0.12)],
    'ice':           [('ice', 0.42), ('snow', 0.30), ('shale', 0.18), ('deep-water', 0.10)],
    'grass':         [('grass', 0.44), ('moss', 0.26), ('dirt', 0.20), ('sand-floor', 0.10)],
}

# A wall that belongs on a given floor. `None` means that floor takes no wall.
WALL_FOR_FLOOR = {
    'sand-floor':    ['dune-wall', 'sand-wall', 'regolith-wall', 'spore-wall'],
    'darksand':      ['dune-wall', 'sand-wall', 'shale-wall'],
    'salt':          ['dune-wall', 'sand-wall', 'ice-wall'],
    'grass':         ['dirt-wall', 'spore-wall'],
    'moss':          ['spore-wall', 'dirt-wall'],
    'spore-moss':    ['spore-wall', 'dirt-wall'],
    'dirt':          ['dirt-wall', 'dune-wall', 'shale-wall'],
    'shale':         ['shale-wall', 'stone-wall', 'dacite-wall'],
    'stone':         ['stone-wall', 'shale-wall', 'dacite-wall'],
    'basalt':        ['stone-wall', 'dacite-wall', 'shale-wall'],
    'carbon-stone':  ['carbon-wall', 'stone-wall', 'shale-wall'],
    'char':          ['stone-wall', 'carbon-wall', 'shale-wall'],
    'yellow-stone':  ['stone-wall', 'dacite-wall', 'shale-wall'],
    'snow':          ['snow-wall', 'ice-wall', 'red-ice-wall'],
    'ice':           ['ice-wall', 'snow-wall', 'red-ice-wall'],
    'deep-water':    ['shale-wall', 'ice-wall', 'red-ice-wall'],
    'shallow-water': ['shale-wall', 'ice-wall', 'dune-wall'],
    'darksand-water': ['shale-wall', 'dune-wall', 'ice-wall'],
    'metal-floor':   ['titanium-wall', 'dark-metal', 'scrap-wall'],
}

# The reef round the sand shelves, and the ice at the cold edges.
WALL_SEA = ['shale-wall', 'shale-wall', 'shale-wall', 'dacite-wall',
            'regolith-wall', 'spore-wall', 'ice-wall']


def cumulative(weighted):
    total = sum(w for _n, w in weighted) or 1.0
    acc, edges = 0.0, []
    for _n, w in weighted:
        acc += w / total
        edges.append(acc)
    return edges


def pick(weighted, value, edges):
    for (name, _w), edge in zip(weighted, edges):
        if value < edge:
            return name
    return weighted[-1][0]


# ------------------------------------------------------------------ noise ---
def field(perlin, w, h, scale, xoff=0.0, yoff=0.0):
    out = [[perlin.noise(x * scale + xoff, y * scale + yoff) for x in range(w)] for y in range(h)]
    rescale(out, 0.0, 1.0)
    return out


def patch_field(perlin, w, h, scale_a, scale_b, xoff, yoff):
    """Two noise scales blended into one 0..1 field.

    One scale gives either blobs or static depending on the value; the blend
    gives patches with ragged edges, which is what a patchwork of floor types
    has to look like.
    """
    out = [[0.0] * w for _ in range(h)]
    for y in range(h):
        for x in range(w):
            a = perlin.noise(x * scale_a + xoff, y * scale_a + yoff)
            b = perlin.noise(x * scale_b + xoff * 0.5, y * scale_b + yoff * 0.5)
            out[y][x] = 0.62 * a + 0.38 * b
    rescale(out, 0.0, 1.0)
    return out


# ------------------------------------------------------------------- dist ---
def water_distance(is_water, cap=6):
    """Chebyshev distance to the nearest water tile, capped. Two sweeps."""
    h = len(is_water)
    w = len(is_water[0]) if h else 0
    big = cap + 1
    d = [[0 if is_water[y][x] else big for x in range(w)] for y in range(h)]
    for y in range(h):
        row, prev = d[y], (d[y - 1] if y > 0 else None)
        for x in range(w):
            if row[x] == 0:
                continue
            best = row[x]
            if x > 0:
                best = min(best, row[x - 1] + 1)
            if prev is not None:
                best = min(best, prev[x] + 1)
                if x > 0:
                    best = min(best, prev[x - 1] + 1)
            row[x] = best
    for y in range(h - 1, -1, -1):
        row, nxt = d[y], (d[y + 1] if y + 1 < h else None)
        for x in range(w - 1, -1, -1):
            if row[x] == 0:
                continue
            best = row[x]
            if x + 1 < w:
                best = min(best, row[x + 1] + 1)
            if nxt is not None:
                best = min(best, nxt[x] + 1)
                if x + 1 < w:
                    best = min(best, nxt[x + 1] + 1)
            row[x] = best
    return d


# ------------------------------------------------------------------ floors ---
def repaint_floors(mr, ids, beach):
    w, h = mr.width, mr.height
    water_ids = set(ids[n] for n in WATER)
    is_water = [[ids[names_of(mr)[mr.floors[y * w + x]]] in water_ids for x in range(w)] for y in range(h)]
    dist = water_distance(is_water)

    perlin = Perlin(4242)
    all_names = names_of(mr)
    fields, edges = {}, {}
    for i, name in enumerate(all_names):
        if name in FLOORS:
            fields[name] = patch_field(perlin, w, h, 0.0085, 0.038, i * 137.0, i * 61.0)
            edges[name] = cumulative(FLOORS[name])

    changed = 0
    for y in range(h):
        for x in range(w):
            k = y * w + x
            src = all_names[mr.floors[k]]
            if src not in FLOORS or is_water[y][x] or dist[y][x] <= beach:
                continue
            nid = ids.get(pick(FLOORS[src], fields[src][y][x], edges[src]))
            if nid is not None and nid != mr.floors[k]:
                mr.floors[k] = nid
                changed += 1
    return changed


_NAMES = {}


def names_of(mr):
    return _NAMES


# ------------------------------------------------------------------- walls ---
def retype_walls(mr, ids):
    w, h = mr.width, mr.height
    names = _NAMES
    fld = field(Perlin(9091), w, h, 0.022)
    sea = field(Perlin(3131), w, h, 0.013, 500.0, 500.0)
    sea_edges = cumulative([(n, 1.0) for n in WALL_SEA])

    changed = 0
    for y in range(h):
        for x in range(w):
            k = y * w + x
            if mr.blocks[k] == 0:
                continue
            floor = names[mr.floors[k]]
            if floor in WATER:
                options = WALL_SEA
            else:
                options = WALL_FOR_FLOOR.get(floor)
                if not options:
                    continue
                options = [o for o in options if o]
                if not options:
                    mr.blocks[k] = 0
                    changed += 1
                    continue
            value = sea[y][x] if floor in WATER else fld[y][x]
            nid = ids.get(pick([(o, 1.0) for o in options], value, cumulative([(o, 1.0) for o in options])))
            if nid is not None and nid != mr.blocks[k]:
                mr.blocks[k] = nid
                changed += 1
    return changed


def add_walls(mr, ids, keep, coverage):
    """A little new rock on open land, so the flats are not blank."""
    w, h = mr.width, mr.height
    names = _NAMES
    water_ids = set(ids[n] for n in WATER)
    is_water = [[ids[names[mr.floors[y * w + x]]] in water_ids for x in range(w)] for y in range(h)]
    dist = water_distance(is_water)
    ridge = field(Perlin(7777), w, h, 0.030)
    tilt = field(Perlin(5151), w, h, 0.011, 220.0, 40.0)

    eligible = []
    for y in range(2, h - 2):
        for x in range(2, w - 2):
            k = y * w + x
            if mr.blocks[k] != 0 or k in keep or dist[y][x] <= 2:
                continue
            eligible.append((k, 0.7 * ridge[y][x] + 0.3 * tilt[y][x]))
    if not eligible:
        return 0
    cut = quantile_threshold([v for _k, v in eligible], coverage)

    added = 0
    for k, v in eligible:
        if v < cut:
            continue
        options = [o for o in WALL_FOR_FLOOR.get(names[mr.floors[k]], []) if o]
        if not options:
            continue
        nid = ids[options[0 if v > (cut + 1.0) / 2 else -1]]
        if nid is not None:
            mr.blocks[k] = nid
            added += 1
    return added


# ------------------------------------------------------------------- decor ---
DECOR = [
    # block, per-100k-tiles, max value of the density field it accepts
    ('boulder', 55, 0.62),
    ('shale-boulder', 40, 0.80),
    ('snow-boulder', 18, 0.88),
    ('white-tree-dead', 75, 0.70),
    ('fading-revelations-remake-gnarled-tree', 60, 0.66),
    ('fading-revelations-remake-charred-tree', 50, 0.70),
    ('spore-pine', 65, 0.62),
]


def scatter_decor(mr, ids, free_of, rnd):
    w, h = mr.width, mr.height
    dens = field(Perlin(2468), w, h, 0.020)
    patch = field(Perlin(1357), w, h, 0.055, 90.0, 90.0)
    area = w * h / 100000.0

    placed = 0
    for block, per100k, ceiling in DECOR:
        nid = ids.get(block)
        if nid is None:
            continue
        budget = int(per100k * area)
        done = 0
        for _ in range(budget * 4):
            if done >= budget:
                break
            x, y = rnd.randrange(2, w - 2), rnd.randrange(2, h - 2)
            k = y * w + x
            if mr.blocks[k] != 0 or k in free_of:
                continue
            # the decor follows the same field as the floor patchwork, so trees
            # stand where a leafy floor ended up rather than at random
            if dens[y][x] > ceiling or patch[y][x] < 0.45:
                continue
            mr.blocks[k] = nid
            done += 1
            placed += 1
    return placed


# ---------------------------------------------------------------- analysis ---
def analyse(mr, names):
    """Which blocks are safe to copy, and which tiles belong to which building."""
    total, with_payload = Counter(), Counter()
    for _k, bid, centre, _p in mr.records:
        total[names[bid]] += 1
        if centre:
            with_payload[names[bid]] += 1
    # A 2x2 always writes three non-centre records and a 3x3 eight, so "every
    # record of this block carries a payload" is a self-contained 1x1 test that
    # needs no size table and cannot be wrong about a modded block.
    copyable = {}
    for _k, bid, centre, payload in mr.records:
        name = names[bid]
        if centre and payload and total[name] == with_payload[name] and payload[0] == 0:
            copyable.setdefault(name, payload)

    # every tile of a multiblock, found by walking the neighbours that carry the
    # same block and are not their own centre
    owner = {}
    for k, bid, centre, _p in mr.records:
        if not centre:
            continue
        owner[k] = k
        if names[bid] == 'air':
            continue
        if total[names[bid]] == with_payload[names[bid]]:
            continue                      # 1x1, nothing to gather
        for dy in (-1, 0, 1):
            for dx in (-1, 0, 1):
                x, y = mr.xy(k)[0] + dx, mr.xy(k)[1] + dy
                if not (0 <= x < mr.width and 0 <= y < mr.height):
                    continue
                j = y * mr.width + x
                if mr.blocks[j] == bid and j not in owner:
                    owner[j] = k
    return copyable, owner


def core_groups(mr, names):
    """The cores, grouped so all five survive with all of their tiles."""
    centres = sorted(k for k, bid, centre, _p in mr.records
                     if centre and names[bid].startswith('core-'))
    groups = []
    for k in centres:
        x, y = mr.xy(k)
        for g in groups:
            if abs(x - g['x']) <= 4 and abs(y - g['y']) <= 4:
                g['centres'].append(k)
                break
        else:
            groups.append({'x': x, 'y': y, 'centres': [k], 'name': names[mr.blocks[k]]})
    return groups


def belt_runs(mr, names, min_len=6):
    """Contiguous straight runs of belts, longest first."""
    names_ok = ('titanium-conveyor', 'conveyor')
    runs, taken = [], set()
    for horizontal in (True, False):
        for y in range(mr.height):
            for x in range(mr.width):
                k = y * mr.width + x
                if k in taken or names[mr.blocks[k]] not in names_ok:
                    continue
                length = 1
                while True:
                    if horizontal:
                        nx, ny = x + length, y
                    else:
                        nx, ny = x, y + length
                    if nx >= mr.width or ny >= mr.height:
                        break
                    j = ny * mr.width + nx
                    if j in taken or names[mr.blocks[j]] not in names_ok:
                        break
                    length += 1
                if length >= min_len:
                    for i2 in range(length):
                        taken.add((y * mr.width + x + i2) if horizontal else ((y + i2) * mr.width + x))
                    runs.append((horizontal, y, x, length))
    runs.sort(key=lambda r: -r[3])
    return runs


# ------------------------------------------------------------------- bases ---
WALL = 'titanium-wall'
WALL_ALT = 'copper-wall'
BELT = 'titanium-conveyor'
WIRE = 'pulse-conduit'
WIRE2 = 'conduit'
POWER = 'power-node'
STORE = 'battery'
GUNS = ('mender', 'scorch', 'hail', 'duo')
UNLOAD = 'fading-revelations-remake-fast-unloader'
DRILLS = ('fading-revelations-remake-mini-od', 'fading-revelations-remake-tiny-md')


def place(out, mr, ids, payload_of, x, y, block, taken):
    if not (0 <= x < mr.width and 0 <= y < mr.height):
        return False
    k = y * mr.width + x
    if k in taken or k in out:
        return False
    bid, payload = ids.get(block), payload_of.get(block)
    if bid is None or payload is None:
        return False
    out[k] = (bid, True, payload)
    taken.add(k)
    return True


def build_compound(mr, ids, payload_of, core, taken, runs, half, rnd):
    w, h = mr.width, mr.height
    cx, cy = core['x'], core['y']
    out = {}
    claimed = set()

    x0, x1 = max(1, cx - half), min(w - 2, cx + half)
    y0, y1 = max(1, cy - half), min(h - 2, cy + half)

    water_ids = set(ids[n] for n in WATER)
    pad = ids.get('metal-floor')
    for y in range(y0 + 1, y1):
        for x in range(x0 + 1, x1):
            k = y * w + x
            # the whole square is the compound, not just the part that gets a
            # metal pad, so everything the author built inside it survives
            claimed.add(k)
            if ids[_NAMES[mr.floors[k]]] in water_ids or k in taken:
                continue
            if pad is not None:
                mr.floors[k] = pad

    # Two-thick perimeter. A one-tile ring of titanium reads as a wireframe
    # rectangle rather than a rampart, and the whole point of the compound is
    # that it looks like a base. The outer layer is the other wall type so the
    # two courses are distinguishable.
    gate_y = y1 if cy < h // 2 else y0
    for layer, wall in ((0, WALL_ALT), (1, WALL)):
        edge_x0, edge_x1 = x0 + layer, x1 - layer
        edge_y0, edge_y1 = y0 + layer, y1 - layer
        for x in range(edge_x0, edge_x1 + 1):
            for y in (edge_y0, edge_y1):
                if y == gate_y and abs(x - cx) <= 1:
                    continue
                k = y * w + x
                if k in taken:
                    continue
                place(out, mr, ids, payload_of, x, y, wall, taken)
        for y in range(edge_y0 + 1, edge_y1):
            for x in (edge_x0, edge_x1):
                k = y * w + x
                if k in taken or (y == cy and abs(x - cx) <= 1):
                    continue
                place(out, mr, ids, payload_of, x, y, wall, taken)
    claimed |= {y * w + x for y in range(y0, y1 + 1) for x in range(x0, x1 + 1)}

    # turrets in the corners and at the middle of each side, one block in
    guns = [g for g in GUNS if g in payload_of] or [WALL]
    posts = ((x0 + 3, y0 + 3), (x1 - 3, y0 + 3), (x0 + 3, y1 - 3), (x1 - 3, y1 - 3),
             ((x0 + x1) // 2, y0 + 3), ((x0 + x1) // 2, y1 - 3),
             (x0 + 3, (y0 + y1) // 2), (x1 - 3, (y0 + y1) // 2))
    for i, (tx, ty) in enumerate(posts):
        place(out, mr, ids, payload_of, tx, ty, guns[i % len(guns)], taken)
        place(out, mr, ids, payload_of, tx, ty + 1, 'mender' if 'mender' in payload_of else WALL, taken)

    # power row along the top, with a small store
    for i, x in enumerate(range(cx - 7, cx + 8)):
        block = STORE if i % 3 == 0 else WIRE
        place(out, mr, ids, payload_of, x, y0 + 1, block, taken)
    for x in range(cx - 7, cx + 8, 3):
        place(out, mr, ids, payload_of, x, y0 + 2, WIRE2, taken)
    place(out, mr, ids, payload_of, cx, y0 + 2, POWER, taken)

    # belt spine: two harvested straight runs, laid as parallel lines
    for run in runs[:2]:
        horizontal, _ry, _rx, length = run
        span = min(length, (x1 - x0) - 4)
        for i2 in range(span):
            if horizontal:
                place(out, mr, ids, payload_of, cx - span // 2 + i2, cy + 2, BELT, taken)
            else:
                place(out, mr, ids, payload_of, cx - 9, cy - span // 2 + i2, BELT, taken)
    for i2 in range(2):
        place(out, mr, ids, payload_of, cx - i2, cy + 3, UNLOAD, taken)

    # A drill row on whatever ore is actually under the compound. The map's real
    # drills are 2x2 and 3x3 and so cannot be copied, but these two are 1x1.
    drills = [d for d in DRILLS if d in payload_of]
    if drills:
        for y in range(y0 + 4, y1 - 4, 2):
            for x in range(x0 + 4, x1 - 3):
                k = y * w + x
                if mr.overlays[k] == 0 or k in taken:
                    continue
                place(out, mr, ids, payload_of, x, y, drills[0], taken)
                place(out, mr, ids, payload_of, x + 1, y, BELT, taken)
                place(out, mr, ids, payload_of, x + 2, y, BELT, taken)
                if x + 3 <= x1 - 2:
                    place(out, mr, ids, payload_of, x + 3, y, 'fading-revelations-remake-titanium-junction', taken)

    # a junction where the spine meets the drill rows
    place(out, mr, ids, payload_of, cx, cy + 1, 'fading-revelations-remake-titanium-junction', taken)
    place(out, mr, ids, payload_of, cx, cy, 'fading-revelations-remake-titanium-router', taken)

    return out, claimed


# -------------------------------------------------------------------- main ---
def keep_original():
    """Preserves the hand-authored map next to the polished one, byte for byte.

    Two things that are easy to get wrong here, both of which happened:

    * Rewriting the backup's `name` tag is unnecessary and harmful. Mindustry
      keys a map by its *filename* (`etnatic-isles-original.msav` becomes
      `Etnatic_Isles_Original`), not by the tag, so the two files already have
      different keys. Renaming the tag does nothing useful - and because the
      polish copies `meta` through from its source, the renamed tag leaked into
      the polished map and both files ended up displaying the same name.
    * Rewriting the file at all is unnecessary and harmful. `regions()` in the
      generator once walked a fixed list of five region names, so a copy written
      through it silently dropped any region it did not know about and the
      backup analysed as an empty world.

    So: a plain file copy, and the region splitter keeps every region.
    """
    backup = SOURCE
    if os.path.exists(backup):
        return
    shutil.copyfile(TARGET, backup)
    print('saved the hand-authored map as %s' % os.path.basename(backup))


def main():
    global _NAMES

    ap = argparse.ArgumentParser()
    ap.add_argument('--check', action='store_true', help='report, write nothing')
    ap.add_argument('--half', type=int, default=28, help='compound half-size in tiles')
    ap.add_argument('--beach', type=int, default=2, help='tiles of beach left unpainted')
    ap.add_argument('--wall-coverage', type=float, default=0.05)
    ap.add_argument('--keep-outside', action='store_true',
                    help='keep the existing base instead of clearing it')
    args = ap.parse_args()

    if not args.check:
        keep_original()

    source = SOURCE if os.path.exists(SOURCE) else TARGET
    print('source: %s' % os.path.basename(source))
    reg = load_map(source)
    # The polish copies `meta` through verbatim, so a mutated source silently
    # poisons the output. Refuse to run from a backup that has been tampered with.
    name = parse_tags(reg['meta']).get('name', '')
    assert not name.endswith('(original)'), 'source map is named %r' % name
    names = parse_content(reg['content'])[0][1]
    _NAMES = names
    ids = {n: i for i, n in enumerate(names)}
    mr = msav.read_map_region(reg['map'])
    w, h, n = mr.width, mr.height, mr.count()
    rnd = random.Random(20260926)

    copyable, owner = analyse(mr, names)
    cores = core_groups(mr, names)
    runs = belt_runs(mr, names)
    print('etnatic-isles  %dx%d  %d building records  %d cores  %d copyable blocks  %d belt runs'
          % (w, h, len(mr.records), len(cores), len(copyable), len(runs)))
    for c in cores:
        print('   core %-16s at (%3d,%3d)' % (c['name'], c['x'], c['y']))

    print('\ncopyable (1x1, team 0): %s' % ', '.join(sorted(copyable)))

    need = [WALL, WALL_ALT, BELT, WIRE, WIRE2, POWER, STORE]
    missing = [b for b in need if b not in copyable]
    if missing:
        print('! cannot build the compounds, no payload for: %s' % ', '.join(missing))
        return 1

    repaint_floors(mr, ids, args.beach)
    print('\nfloors repainted')
    retype_walls(mr, ids)
    print('walls re-typed')

    # ---- bases ------------------------------------------------------------
    taken = set()
    for c in cores:
        for k, bid, centre, payload in mr.records:
            if centre and names[bid].startswith('core-') \
                    and abs(mr.xy(k)[0] - c['x']) <= 4 and abs(mr.xy(k)[1] - c['y']) <= 4:
                for tile in [k] + [t for t, o in owner.items() if o == k]:
                    taken.add(tile)

    compounds, claimed = {}, set()
    if not args.keep_outside:
        for c in cores:
            b, cl = build_compound(mr, ids, copyable, c, taken, runs, args.half, rnd)
            compounds.update(b)
            claimed |= cl
            print('compound at (%3d,%3d): %4d new buildings, %5d tiles claimed'
                  % (c['x'], c['y'], len(b), len(cl)))

    # what survives: the cores, everything inside a compound, the enemy's stuff
    inside = {k for k in owner if k in claimed}
    keep = set(taken)
    for k, bid, centre, payload in mr.records:
        if not centre:
            continue
        team = payload[0] if payload else 0
        if team != 0 or k in inside:
            for tile in [k] + [t for t, o in owner.items() if o == k]:
                keep.add(tile)

    surviving = {k: (bid, centre, payload) for k, bid, centre, payload in mr.records if k in keep}
    player_before = len([1 for _k, _b, c, p in mr.records if c and p and p[0] == 0])
    player_after = len([1 for _k, (bid, c, p) in surviving.items() if c and p and p[0] == 0])
    surviving.update(compounds)
    print('player building records: %d before, %d kept, %d added by the compounds'
          % (player_before, player_after, len(compounds)))
    print('total building records:  %d before, %d after'
          % (len(mr.records), len(surviving)))

    # A multiblock that lost any of its tiles would desync the whole structure,
    # so prove every kept centre still owns all of its tiles.
    orphan = 0
    for k, (bid, centre, payload) in surviving.items():
        if not centre:
            continue
        for tile, o in owner.items():
            if o == k and tile not in surviving:
                orphan += 1
    assert orphan == 0, '%d multiblock tiles lost their building' % orphan
    cores_left = len([1 for _k, (bid, c, _p) in surviving.items()
                      if c and _NAMES[bid].startswith('core-')])
    assert cores_left == len(cores), 'kept %d of %d cores' % (cores_left, len(cores))
    print('all %d cores kept, no multiblock cut in half' % cores_left)

    for k, (bid, _c, _p) in surviving.items():
        mr.blocks[k] = bid
    for k in taken - set(surviving):
        mr.blocks[k] = ids['metal-floor']

    print('new walls on open land: %d'
          % add_walls(mr, ids, taken | set(surviving) | claimed, args.wall_coverage))
    print('decor scattered:       %d' % scatter_decor(mr, ids, taken | set(surviving), rnd))

    # ---- write ------------------------------------------------------------
    new_map = msav.write_map_region(w, h, mr.floors, mr.overlays, mr.blocks, surviving)
    if args.check:
        back = msav.read_map_region(new_map)
        print('\n--check: %dx%d, %d building records'
              % (back.width, back.height, len(back.records)))
        if source != TARGET:
            committed = load_map(TARGET)['map']
            if committed == new_map:
                print('--check: the committed etnatic-isles.msav matches this script exactly')
                return 0
            same_size = len(committed) == len(new_map)
            print('--check: MISMATCH - %s is not what this script produces'
                  % os.path.basename(TARGET))
            print('         committed %d bytes, rebuilt %d bytes%s'
                  % (len(committed), len(new_map), '' if same_size else ' (different length)'))
            print('         re-run without --check to regenerate it')
            return 1
        print('--check: no %s yet, so there is nothing to compare against - run without --check'
              % os.path.basename(SOURCE))
    else:
        blob = b'MSAV' + struct.pack('>i', 7)
        blob += chunk(reg['meta'])
        blob += chunk(reg['content'])
        blob += chunk(new_map)
        blob += chunk(reg['entities'])
        blob += chunk(reg['custom'])
        with open(TARGET, 'wb') as f:
            f.write(zlib.compress(blob, 9))
        print('wrote %s (%d bytes)' % (os.path.basename(TARGET), os.path.getsize(TARGET)))

    print('\nfloor mix:')
    for name, c in Counter(names[mr.floors[i]] for i in range(n)).most_common(16):
        print('   %-42s %7d  %5.1f%%' % (name, c, 100.0 * c / n))
    print('wall mix:')
    for name, c in Counter(names[mr.blocks[i]] for i in range(n)
                           if names[mr.blocks[i]] != 'air').most_common(16):
        print('   %-42s %7d  %5.1f%%' % (name, c, 100.0 * c / n))
    return 0


if __name__ == '__main__':
    sys.exit(main())
