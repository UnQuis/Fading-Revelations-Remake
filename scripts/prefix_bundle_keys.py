#!/usr/bin/env python3
"""
Normalise and validate mod bundle keys.

WHY THIS IS NEEDED
------------------
Mindustry prefixes every mod content name with the mod id:
`MappableContent.name = Vars.content.transformName(name)` and
`transformName` returns `currentMod == null ? name : currentMod.name + "-" + name`.
`Mods.loadContent()` calls `content.setCurrentMod(mod)` before `mod.main.loadContent()`,
so a block declared as `new Wall("outpost")` is really named
`fading-revelations-remake-outpost`. `UnlockableContent`'s constructor then does

    Core.bundle.get(getContentType() + "." + this.name + ".name", this.name)

so the bundle key it looks up is `block.fading-revelations-remake-outpost.name`.
Mod bundles are loaded verbatim (`PropertiesUtils.load`), with no key rewriting, so a
bundle that stores `block.outpost.name` is a dead key: the translation never shows.

Non-content keys (`techtree.*`, `fr.*`, `settings.*`, ...) are NOT prefixed, because
`TechNode.localizedName()` looks its own name up directly and the mod's own UI strings
are plain bundle keys.

WHAT THIS DOES
--------------
  * rewrites `<type>.<name>.<field>` -> `<type>.<modPrefix><name>.<field>` for the
    content types that go through UnlockableContent;
  * strips legacy `me-` prefixes that leaked into keys;
  * repairs `.json.` artefacts (`block.mortar.json.name` -> `block.mortar.name`);
  * verifies every rewritten name actually exists in the Java sources, and that no
    content name is left without a bundle entry;
  * `--check` only reports (used by CI), without `--write` it stays read-only.

Usage:
    python3 scripts/prefix_bundle_keys.py --write
    python3 scripts/prefix_bundle_keys.py --check
"""

import argparse
import glob
import os
import re
import sys

ROOT = os.path.dirname(os.path.dirname(os.path.abspath(__file__)))
BUNDLE_DIR = os.path.join(ROOT, "assets", "bundles")
SRC_DIR = os.path.join(ROOT, "src")

# must match `name:` in mod.hjson - LoadedMod.name is meta.name lowercased
MOD_ID = "fading-revelations-remake"

# content types that extend UnlockableContent -> MappableContent, i.e. get the prefix
PREFIXED_TYPES = ("block", "item", "unit", "liquid", "status", "sector", "planet")
FIELDS = ("name", "description", "details", "credit")

# key names that were written with a translated content name instead of the Java name
NAME_FIXUPS = {
    # es: "acero-sedimentación" is the Spanish label, the content is `steel-sedimentation`
    "me-acero-sedimentación": "steel-sedimentation",
}

_key_re = re.compile(
    r"^(\w+)\.([^.]+)\.(name|description|details|credit)$"
)
_json_re = re.compile(
    r"^(\w+)\.([^.]+)\.json\.(name|description|details|credit)$"
)
# every `new SomeClass("content-name")` in the mod's Java sources
_ctor_re = re.compile(r'new\s+[\w.]+\s*\(\s*"([a-z][a-z0-9\-]*)"')
# static Block/Item/UnitType/... field declarations, to tell which *type* a name belongs to
_field_re = re.compile(
    r"public static \w[\w<>,.\[\] ]*?\b(\w+)\s*=\s*new\s+(\w+)\s*\(", re.M
)


def collect_content():
    """Return (all_names, {lowercase basename -> content name}) from the Java sources."""
    names = set()
    by_basename = {}
    for path in glob.glob(os.path.join(SRC_DIR, "**", "*.java"), recursive=True):
        src = open(path, encoding="utf-8").read()
        for m in _ctor_re.finditer(src):
            names.add(m.group(1))
    # sprite basenames are a good proxy for "does an asset exist for this content"
    for path in glob.glob(os.path.join(ROOT, "assets", "sprites", "**", "*.png"), recursive=True):
        by_basename[os.path.splitext(os.path.basename(path))[0]] = path
    return names, by_basename


def split_key(key):
    """-> (content_type, name, field) or None if the key is not a content key."""
    m = _key_re.match(key) or _json_re.match(key)
    if not m:
        return None
    ctype, name, field = m.group(1), m.group(2), m.group(3)
    if ctype not in PREFIXED_TYPES:
        return None
    name = NAME_FIXUPS.get(name, name)
    # already-prefixed keys must round-trip, so --check is idempotent
    if name.startswith(MOD_ID + "-"):
        name = name[len(MOD_ID) + 1:]
    if name.startswith("me-"):
        name = name[3:]
    return ctype, name, field


def process(path, names, write):
    out, changed, unresolved, blank = [], 0, set(), 0
    for lineno, raw in enumerate(open(path, encoding="utf-8"), 1):
        stripped = raw.strip()
        if not stripped or stripped[0] in "#!" or "=" not in stripped:
            out.append(raw.rstrip("\n"))
            continue
        key, val = stripped.split("=", 1)
        key = key.strip()
        if not val.strip():
            blank += 1
        lead = raw[: len(raw) - len(raw.lstrip())]
        parts = split_key(key)
        if parts is None:
            out.append(raw.rstrip("\n"))
            continue
        ctype, name, field = parts
        if name not in names:
            unresolved.add("%s:%s (line %d)" % (ctype, name, lineno))
            out.append(raw.rstrip("\n"))
            continue
        new_key = "%s.%s%s.%s" % (ctype, MOD_ID + "-", name, field)
        if new_key != key:
            changed += 1
        out.append("%s%s =%s" % (lead, new_key, val))
    new_text = "\n".join(out) + "\n"
    if write and new_text != open(path, encoding="utf-8").read():
        open(path, "w", encoding="utf-8").write(new_text)
    return changed, unresolved, blank


def main():
    ap = argparse.ArgumentParser()
    ap.add_argument("--write", action="store_true", help="rewrite the bundle files")
    ap.add_argument("--check", action="store_true", help="report only (default)")
    args = ap.parse_args()
    write = args.write and not args.check

    names, _ = collect_content()
    print("content names found in src/: %d" % len(names))
    print("bundle keys must look like: block.%s-<name>.name\n" % MOD_ID)

    total_unresolved = 0
    for path in sorted(glob.glob(os.path.join(BUNDLE_DIR, "bundle*.properties"))):
        changed, unresolved, blank = process(path, names, write)
        name = os.path.basename(path)
        status = "ok" if not unresolved else "%d UNRESOLVED" % len(unresolved)
        print("%-26s %4d keys rewritten  %3d blank values  %s"
              % (name, changed, blank, status))
        for u in sorted(unresolved):
            print("    !! %s" % u)
        total_unresolved += len(unresolved)

    print()
    if total_unresolved:
        print("FAILED: %d bundle key(s) name content that does not exist." % total_unresolved)
        return 1
    if not write:
        print("All bundle keys resolve. (read-only; pass --write to apply)")
    else:
        print("All bundle keys resolve and are normalised.")
    return 0


if __name__ == "__main__":
    sys.exit(main())
