#!/usr/bin/env bash
# Load every shipped campaign map in a *headless* Mindustry dedicated server and
# report anything that breaks.
#
# Why a server and not the client: `host <mapname>` goes through exactly the
# MapIO.readMap -> ShortChunkSaveVersion.readMap path that used to throw
#   ArrayIndexOutOfBoundsException: Index N out of bounds for length N
# on sector load, and the server sets Vars.headless, so no window is ever opened
# and the user's display is not touched.
#
# The mod under test is rebuilt and re-installed on every run, so this always
# exercises the current code.
#
# Usage: scripts/check_maps_live.sh [map ...]
set -uo pipefail

cd "$(dirname "$0")/.."
ROOT="$PWD"
RUN="$ROOT/run"
SERVER_JAR="$RUN/server-v160.5.jar"
SERVER_DIR="$RUN/servercheck"
MOD_JAR="$ROOT/build/libs/FadingRevelations.jar"
STDIN="$SERVER_DIR/stdin.txt"

MV=$(sed -n 's/^mindustryVersion[[:space:]]*=[[:space:]]*//p' gradle.properties | tr -d '[:space:]')
if [ ! -f "$SERVER_JAR" ]; then
    echo "!! $SERVER_JAR missing. Fetch it with:" >&2
    echo "   curl -sL -o run/server-\$MV.jar https://github.com/Anuken/Mindustry/releases/download/\$MV/server-release.jar" >&2
    exit 2
fi

./gradlew jar -q || exit 2
mkdir -p "$SERVER_DIR/mods" "$SERVER_DIR/config/maps"
rm -f "$SERVER_DIR/mods"/*.jar
cp "$MOD_JAR" "$SERVER_DIR/mods/"

# The server reads custom maps from config/maps, so the maps must be copied there.
# Always clear it first: a stale same-named file shadows the one we just built and
# you end up debugging a map that was replaced hours ago.
rm -f "${SERVER_DIR:?}"/config/maps/*.msav
for m in "$ROOT"/assets/maps/*.msav; do cp "$m" "$SERVER_DIR/config/maps/"; done

# Mindustry normalises map names to Title_Case with underscores (exordium -> Exordium,
# carbonic-downpour -> Carbonic_Downpour), and `host` only accepts the normalised form.
normalise() {
    python3 -c 'import sys;print("_".join(w[:1].upper()+w[1:] for w in sys.argv[1].replace("-","_").split("_")))' "$1"
}

maps=()
if [ $# -gt 0 ]; then
    for a in "$@"; do maps+=("$(normalise "$a")"); done
else
    for m in "$ROOT"/assets/maps/*.msav; do
        maps+=("$(normalise "$(basename "$m" .msav)")")
    done
fi

echo "Mindustry ${MV} | headless server | mod: $(basename "$MOD_JAR")"
echo "maps: ${maps[*]}"
echo

fail=0
for name in "${maps[@]}"; do
    # the server needs a moment to finish booting before it reads stdin, and blank
    # lines on stdin are consumed as (invalid) commands
    { sleep 15; echo "host $name survival"; sleep 35; echo exit; } > "$STDIN"
    out=$(cd "$SERVER_DIR" && MINDUSTRY_DATA_DIR="$SERVER_DIR" \
        timeout 240 java -Xmx2g -Dfile.encoding=UTF-8 -jar "$SERVER_JAR" < "$STDIN" 2>&1)
    out=$(printf '%s' "$out" | sed 's/\x1b\[[0-9;]*m//g')

    if printf '%s' "$out" | grep -qE "ArrayIndexOutOfBounds|Exception in thread|error occurred loading|Failed to (load|read)|Caused by|content error|Error reading region"; then
        echo "FAIL  $name  (loader error)"
        printf '%s\n' "$out" | grep -E "ArrayIndexOutOfBounds|Exception|error occurred|Failed|Caused by|Error reading" | head -5 | sed 's/^/        /'
        fail=1
    elif printf '%s' "$out" | grep -qi "No map with name"; then
        # a missing map produces no loader error at all, so it must be treated as a
        # failure explicitly - otherwise this check passes vacuously
        echo "FAIL  $name  (server does not know this map name - not actually tested)"
        fail=1
    elif printf '%s' "$out" | grep -qi "Map has no cores"; then
        # deserialised fine; the map is deliberately coreless (a lore map, its meta
        # has nocores=true) so survival hosting legitimately refuses it
        echo "ok    $name  (parsed; coreless by design, not hostable in survival)"
    elif printf '%s' "$out" | grep -qiE "Map loaded|Saved [0-9]+x[0-9]+|hosting|Loaded [0-9]+x[0-9]+|Loading map"; then
        echo "ok    $name  $(printf '%s' "$out" | grep -oiE '[0-9]+x[0-9]+' | head -1)"
    else
        echo "WARN  $name (no crash, but no positive confirmation either - inspect manually)"
        printf '%s\n' "$out" | grep -vE '^\s*$' | tail -6 | sed 's/^/        /'
    fi
done

echo
[ $fail -eq 0 ] && echo "PASS: all maps loaded without crashing the loader" \
                 || echo "FAIL: see above"
exit $fail
