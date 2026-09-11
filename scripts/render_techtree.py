#!/usr/bin/env python3
"""Parse FRFullTechTree.java node structure and render a radial-layout preview."""
import re, sys

src = open('src/fadingrevelations/content/FRFullTechTree.java').read()

# Extract the rootNode = nodeRoot(...) { ... } body
start = src.index('rootNode = nodeRoot(')
# find matching closing brace of the runnable
i = src.index('{', start)
depth = 0
for j in range(i, len(src)):
    if src[j] == '{': depth += 1
    elif src[j] == '}':
        depth -= 1
        if depth == 0:
            end = j + 1
            break
root_body = src[i:end]

# tokenize: names inside node(...) / nodeProduce(...)
# Build a proper AST via recursive descent on calls
call_re = re.compile(r'(nodeProduce|addToNode|node)\s*\(')

class Node:
    def __init__(self, name, kind):
        self.name = name
        self.kind = kind
        self.children = []
    def dump(self, ind=0, out=None):
        out.append('  '*ind + self.name)
        for c in self.children:
            c.dump(ind+1, out)

def parse_calls(text):
    """Parse a block of code, returning list of Node at this level (calls executed in order)."""
    results = []
    pos = 0
    for m in call_re.finditer(text):
        # ensure not inside a previous child block: check by scanning sequentially instead
        pass
    # sequential scan
    pos = 0
    while True:
        m = call_re.search(text, pos)
        if not m:
            break
        kind = m.group(1)
        # read until matching paren
        j = m.end()
        pd = 1
        while pd > 0:
            if text[j] == '(': pd += 1
            elif text[j] == ')': pd -= 1
            j += 1
        inner = text[m.end():j-1]
        # first argument = content name up to first comma at depth 0
        k = 0; d = 0; first_end = len(inner)
        while k < len(inner):
            if inner[k] in '([': d += 1
            elif inner[k] in ')]': d -= 1
            elif inner[k] == ',' and d == 0:
                first_end = k; break
            k += 1
        first = inner[:first_end].strip()
        # find lambda { ... } inside inner
        lb = inner.find('{')
        node = Node(first, kind)
        if lb != -1:
            # matching brace for lambda
            d2 = 0; e = lb
            while e < len(inner):
                if inner[e] == '{': d2 += 1
                elif inner[e] == '}':
                    d2 -= 1
                    if d2 == 0: break
                e += 1
            node.children = parse_calls(inner[lb+1:e])
        results.append(node)
        pos = j
    return results

roots = parse_calls(root_body)
addtos = parse_calls(src[end:])  # addToNode calls after rootNode block

def collect(n, lst):
    lst.append(n)
    for c in n.children: collect(c, lst)

all_nodes = []
for r in roots: collect(r, all_nodes)

# Depth stats
def maxdepth(n):
    return 1 + (max((maxdepth(c) for c in n.children), default=0))

print("=== TOP-LEVEL BRANCHES (order = angular order in radial layout) ===")
for r in roots:
    cnt = []
    collect(r, cnt)
    print(f"  {r.name:55s} depth={maxdepth(r):2d} subtree={len(cnt):3d}")
print(f"\nTotal nodes in FR tree: {len(all_nodes)}")

print("\n=== DEEPEST CHAINS ===")
def chains(n, path):
    p = path + [n.name]
    if not n.children:
        yield p
    else:
        for c in n.children:
            yield from chains(c, p)
allch = []
for r in roots:
    allch += list(chains(r, []))
allch.sort(key=len, reverse=True)
for c in allch[:8]:
    print(f"  len={len(c)}: {' -> '.join(c)}")

# width at each depth (radial: how many nodes share the same ring)
from collections import Counter
ring = Counter()
def walk(n, d):
    ring[d] += 1
    for c in n.children: walk(c, d+1)
for r in roots: walk(r, 0)
print("\n=== NODES PER RING (depth) ===")
for d in sorted(ring):
    print(f"  ring {d}: {ring[d]:3d} nodes")

print("\n=== addToNode (nodes grafted onto VANILLA trees!) ===")
for a in addtos:
    print(f"  {a.name}: children={[c.name for c in a.children]}")

if '--tree' in sys.argv:
    print("\n=== FULL TREE ===")
    out = []
    for r in roots: r.dump(0, out)
    print('\n'.join(out))
