#!/usr/bin/env python3
# -*- coding: utf-8 -*-
"""Read-only dump of a .msav: name, size, floor/wall/overlay/building histograms.

    python3 scripts/inspect_map.py assets/maps/*.msav --top 12

Uses scripts/msav.py, which is the single authoritative reader for the Save7 map
region. The three copies of the block-section walker this script used to keep in
step with each other had already drifted, and two of them desynchronised on a
building record and reported a 11,501-building map as having none.
"""
import argparse
import os
import sys
from collections import Counter

sys.path.insert(0, os.path.dirname(os.path.abspath(__file__)))
import msav  # noqa: E402
from msav import load_map, parse_content, parse_tags  # noqa: E402


def dump(path, top):
    reg = load_map(path)
    tags = parse_tags(reg['meta'])
    names = parse_content(reg['content'])[0][1]
    mr = msav.read_map_region(reg['map'])
    n = mr.count()

    print('== %s  %dx%d' % (os.path.basename(path), mr.width, mr.height))
    for key in ('name', 'author', 'description', 'text', 'saved', 'tick'):
        if key in tags:
            value = tags[key]
            if len(value) > 110:
                value = value[:110] + '...'
            print('   %-11s %s' % (key, value))
    print('   %-11s %d bytes' % ('rules', len(tags.get('rules', ''))))

    for label, seq in (('floor', mr.floors), ('overlay', mr.overlays)):
        print('   -- %s --' % label)
        for bid, c in Counter(seq).most_common(top):
            if bid == 0 and label == 'overlay':
                continue
            print('      %-52s %7d  (%4.1f%%)' % (names[bid], c, 100.0 * c / n))

    print('   -- block --')
    for bid, c in Counter(mr.blocks).most_common(top):
        print('      %-52s %7d  (%4.1f%%)' % (names[bid], c, 100.0 * c / n))

    centres = [names[bid] for _k, bid, centre, _p in mr.records if centre]
    cores = sorted({c for c in centres if c.startswith('core-')})
    spawns = sum(1 for o in mr.overlays if names[o] == 'spawn')
    print('   cores:   %s' % (', '.join(cores) if cores else 'NONE - not hostable'))
    print('   spawns:  %d spawn markers' % spawns)
    print('   records: %d building records, %d of them a multiblock centre'
          % (len(mr.records), sum(1 for _k, _b, c, _p in mr.records if c)))
    print()


def main():
    ap = argparse.ArgumentParser()
    ap.add_argument('maps', nargs='+')
    ap.add_argument('--top', type=int, default=12)
    args = ap.parse_args()
    for m in args.maps:
        dump(m, args.top)
    return 0


if __name__ == '__main__':
    sys.exit(main())
