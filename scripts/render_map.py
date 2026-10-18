#!/usr/bin/env python3
# -*- coding: utf-8 -*-
"""Renders a .msav to a PNG so the terrain can actually be looked at.

Wall ratios and floor histograms tell you nothing about whether a map reads as a
place. This paints the parsed map with roughly Mindustry's floor colours, shaded
by slope, so the shapes - ridges, beaches, basins, the arc of a spine - are
visible at a glance.

Two rules that the first version got wrong and that make a preview actively
misleading:
  * a wall is drawn as its floor darkened hard, otherwise a wall and its floor
    are the same colour and the wall network is invisible;
  * a wall is *anything* named `*-wall`, not a hardcoded list, or a modded wall
    is drawn as if it were a tree.

    python3 scripts/render_map.py assets/maps/*.msav --out build/preview
    python3 scripts/render_map.py assets/maps/etnatic-isles.msav --scale 1
"""
import argparse
import os
import sys

sys.path.insert(0, os.path.dirname(os.path.abspath(__file__)))
import msav  # noqa: E402
from msav import block_names, load_map  # noqa: E402

from PIL import Image  # noqa: E402

# close enough to the vanilla floor colours to judge the palette
FLOOR = {
    'air': (0, 0, 0), 'deep-water': (28, 62, 122), 'shallow-water': (52, 108, 168),
    'darksand-water': (60, 58, 44), 'salt-water': (110, 140, 150),
    'sand-floor': (198, 176, 116), 'darksand': (96, 84, 60), 'stone': (108, 108, 118),
    'basalt': (62, 62, 70), 'carbon-stone': (48, 46, 50), 'yellow-stone': (156, 140, 96),
    'char': (40, 38, 40), 'moss': (58, 84, 52), 'grass': (86, 128, 62),
    'dirt': (124, 96, 64), 'shale': (78, 78, 86), 'snow': (216, 220, 228),
    'ice': (150, 180, 210), 'salt': (206, 200, 168), 'spore-moss': (96, 72, 116),
    'darksand-tinted-water': (70, 62, 48), 'deep-tainted-water': (48, 40, 68),
    'darksand-tainted-water': (58, 52, 44), 'tainted-water': (72, 66, 92),
    'fading-revelations-remake-tile-acid': (150, 200, 60),
    'fading-revelations-remake-deep-tile-acid': (96, 150, 30),
    'metal-floor': (96, 100, 110), 'metal-floor-damaged': (110, 100, 88),
}
BUILDING = (30, 90, 40)      # trees, boulders, machines: green, so walls stay readable
CORE = (250, 230, 90)
ORE = {
    'ore-copper': (198, 130, 66), 'ore-lead': (140, 150, 178),
    'ore-coal': (56, 56, 60), 'ore-titanium': (196, 176, 108),
    'ore-thorium': (108, 196, 168), 'ore-scrap': (150, 140, 130),
    'fading-revelations-remake-ore-graphite': (72, 72, 78),
    'fading-revelations-remake-steel-sedimentation': (200, 210, 220),
    'fading-revelations-remake-tile-acid': (168, 208, 80),
    'spawn': (240, 60, 60),
}
WATER_SUFFIX = 'water'


def is_water_floor(name):
    return name == 'air' or name.endswith(WATER_SUFFIX)


def is_wall_block(name):
    return name.endswith('-wall') or name in ('dark-metal', 'metal-floor', 'core-zone')


def render(path, out, scale):
    names = block_names(path)
    mr = msav.read_map_region(load_map(path)['map'])
    w, h = mr.width, mr.height
    img = Image.new('RGB', (w, h))
    px = img.load()

    centres = set()
    for k, _bid, centre, _p in mr.records:
        if centre:
            centres.add(k)

    for y in range(h):
        for x in range(w):
            i = y * w + x
            fl = names[mr.floors[i]]
            bl = names[mr.blocks[i]]
            ov = names[mr.overlays[i]] if mr.overlays[i] else None
            wall = is_wall_block(bl)

            if i in centres and bl.startswith('core-'):
                c = CORE
            elif bl not in ('air',) and not is_water_floor(bl) and not wall:
                c = BUILDING
            else:
                c = FLOOR.get(fl, (100, 100, 100))
                if ov in ORE:
                    c = ORE[ov]
                up = names[mr.floors[(y - 1) * w + x]] if y > 0 else fl
                if up != fl:
                    c = tuple(min(255, v + 24) for v in c)
                elif y + 1 < h and names[mr.floors[(y + 1) * w + x]] != fl:
                    c = tuple(max(0, v - 20) for v in c)
                if wall:
                    c = tuple(int(v * 0.42) for v in c)
            px[x, y] = c

    if scale != 1:
        img = img.resize((w * scale, h * scale), Image.NEAREST)
    os.makedirs(os.path.dirname(out) or '.', exist_ok=True)
    img.save(out)
    return w, h, os.path.getsize(out)


def main():
    ap = argparse.ArgumentParser()
    ap.add_argument('maps', nargs='+')
    ap.add_argument('--out', default='build/preview')
    ap.add_argument('--scale', type=int, default=1)
    args = ap.parse_args()
    for m in args.maps:
        name = os.path.splitext(os.path.basename(m))[0]
        out = os.path.join(args.out, name + '.png')
        w, h, size = render(m, out, args.scale)
        print('  %-28s %4dx%-4d -> %s (%dB)' % (name + '.msav', w, h, out, size))
    return 0


if __name__ == '__main__':
    sys.exit(main())
