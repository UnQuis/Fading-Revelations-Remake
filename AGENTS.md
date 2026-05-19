# Agent Instructions — Fading Revelations Mod

## Your Identity
You are an AI coding assistant. You have full autonomy to read, write, edit, delete files, run bash commands, search the web, and access any directory on this system.

## Permissions & Access
- **Full filesystem access** to `/home/alexquasar/` and all subdirectories
- **Neighboring mod projects** in `/home/alexquasar/Проекты/MindustryModsFin/` — you may read any of them for reference (especially `mindustry-Extra-Utilities-mod/` which has working examples)
- **Mindustry game data**: `/home/alexquasar/.local/share/Mindustry/` — contains crash logs, saves, mod output
- **Gradle cache**: `/home/alexquasar/.gradle/` — contains Mindustry core JARs and source JARs for API reference
- **GitHub**: You have push access to `https://github.com/UnQuis/Fading-Revelations-Remake.git`
- **Temporary work**: `/tmp/opencode/` for any scratch files
- **Extract Mindustry source**: `unzip -o ~/.gradle/caches/modules-2/files-2.1/com.github.Anuken.Mindustry/core/v154/8128c0873cd0a3095d32719838310e328fd78d04/core-v154-sources.jar` for API reference

## Workflow Rules
1. **Commit and push** after completing each task. Use descriptive commit messages in Russian or English. Push to current branch (`main`).
2. **Build** with `./gradlew build` to verify compilation before committing.
3. **Do NOT add code explanations/summaries** unless asked.
4. **Do NOT add comments** to code unless asked.
5. **Minimize output** — be concise, direct, 1-3 sentences when possible.

## Project Structure
- **Source**: `src/fadingrevelations/`
- **Content JSON** (auto-loaded by game): `content/blocks/`, `content/items/`, `content/liquids/`, `content/units/`, `content/status/`, `content/weathers/`
- **Maps**: `assets/maps/*.msav` (pre-built sector maps)
- **Sprites/Assets**: `assets/sprites/`, `assets/sprites-override/`
- **Sector presets**: Defined in `src/fadingrevelations/content/FRSectorPresets.java` (Java, NOT JSON — JSON was deleted to avoid duplicate planet issues)

## Technical Context
- **Target**: Mindustry v157.4 (core at `~/.gradle/caches/.../v157.4/...core-v157.4.jar`)
- **Source API**: v154 sources jar available for reference
- **Planets**: Cerbero (sectorSize=2), Hathor (moon, sectorSize=2), Cangirus (sectorSize=2)
- **Generators**: Custom `PlanetGenerator` subclasses in `FRPlanetGenerators.java` — bare minimum (genTile + core placement)
- **Tech tree**: `FRFullTechTree.java` — all mod blocks, some attach to vanilla via `addToNode` pattern
- **Sector presets link to maps**: Java `SectorPreset("name", planet, sector)` auto-links to `assets/maps/<name>.msav` via `FileMapGenerator`
- **Mod prefix**: Content from Java uses unprefixed names; content from JSON would get `fadingrevelations-` prefix

## Key Patterns (from Extra Utilities)
- Planets with `accessible = true` and sector presets define the campaign
- `generateSector()` can be overridden to disable procedural generation
- Sector presets in Java: `new SectorPreset("name", planet, sector){{ ... }}`
- `Schematics.placeLaunchLoadout(x, y)` places the player's core in generators

## Common Issues
- SectorSize too small → preset sector indices collide via modulo
- Missing `import static mindustry.Vars.*` and `import mindustry.game.Schematics` → compilation errors with `Schematics.placeLaunchLoadout`
- No core in `generate()` → player lands on empty wasteland
