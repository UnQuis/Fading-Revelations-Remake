<div align = center>

# Fading Revelations: Remake

*Community continuation of the discontinued **[Fading Revelations]** mod.* <br>
*Rebuilt for modern Mindustry - now with a campaign, a story, and megastructures.*

<br>

[![Badge Build]][Build] 
[![Badge Commit]][Commits] 
[![Badge Stars]][Stargazers] 
[![Badge Issues]][Issues] 
[![Badge PRs]][Pulls] 
[![Badge License]][License] 
[![Badge Mindustry]][Mindustry] 

<br>
<br>

![Logo]

<br>

</div>

## The Story

Long ago a single **Precursor** faction ruled this system - a civilization far
beyond anything the **Shards** (you) have ever built. Then it split apart.
Nobody knows why. The Precursors themselves are gone; only their ruins,
their technologies and their automated war machines remain, still following
orders nobody can revoke.

The expedition lands on three worlds, and each tells a different story:

| Planet | What you find |
| --- | --- |
| **Cangirus** | The only world where Precursor systems still run - and still fight. Its records claim the split was a **treason**. |
| **Hathor** | Ruins holding the *other* side's records. No treason - one side was trying to **prevent a catastrophe**. |
| **Cerbero** | A dead world with the oldest records of all, written *before* the split: the conflict grew from a **technology** one side wanted destroyed and the other wanted to control. |

All three versions are convincing. All three contradict each other. Recover
every archive entry from the **Precursor Terminals** hidden across the
sectors, and the truth - about the war, and about what you are really here
to finish - finally converges.

## Features

**Campaign & world**
- `3` planets - Cangirus, Cerbero and its dead moon Hathor - with `7` campaign sectors
- A **lore system**: ancient terminals restore archive entries per planet,
  with a finale that unlocks when all three chains are complete
- An **asteroid belt** of Precursor debris around Cerbero

**Megastructures**
- The **Orbital Ring**: a 4-stage, planet-wide Cangirus project. Feed
  Orbital Ring Stations, watch the ring grow quadrant by quadrant around the
  planet, and earn permanent campaign bonuses - mining speed, build speed,
  health, damage, unit cap. Finish it, and every block on Cangirus gets
  **Orbital Boost: +200%**

**Late-game logistics & QoL**
- **Jump Gates**: pair-linked unit teleporters with phase-fatigue cooldowns
- **Grid Monitor**: a power node with a Satisfactory-style live chart -
  hover it to watch production, consumption, max output and battery charge
  over the last minutes
- Rebuilt **tech tree** with category gates, deep chains untangled

**Content**
- `200+` blocks, `59` units, custom items and liquids
- Endgame drills, overdrives, cores and turret reworks - strictly better
  than their vanilla counterparts, balanced around them
- New Horizon-inspired extras: categorized mod settings, spin-up turrets,
  ramp-up unit abilities

<br>

## Installing

Java mod - works on Desktop and Android (not iOS):

1. **In-game**: Mods → Browse Mods → search `Fading Revelations: Remake` → Install → restart
2. **From releases**: download the latest `FadingRevelations.jar` from
   [releases], drop it into your mods folder:
   - Windows: `%APPDATA%\Mindustry\mods\`
   - Linux: `~/.local/share/Mindustry/mods/`
   - Mac: `~/Library/Application Support/Mindustry/mods/`

## Building

Requires JDK 17+.

```bash
./gradlew build
# -> build/libs/FadingRevelations.jar
```

<br>

## Acknowledgment

- Based on the original **[Fading Revelations]** by Fresh791 and contributors
- *Software used for the project*: sprites made with **[Piskel]**, badges by **[Shield.io]**,
  sound effects mostly from **[Pixabay]**
- Jump gate & ring concepts adapted from the **[New Horizon]** mod (GPL-3);
  asteroid belt mesh structure adapted from **[Omaloon]** (GPL-3);
  power-chart idea inspired by **MI2-Utilities**

<!------------------------------------------------------------------------->

[Mindustry]: https://github.com/Anuken/Mindustry
[New Horizon]: https://github.com/Yuria-Shikibe/NewHorizonMod
[Omaloon]: https://github.com/stabu-dev/Omaloon
[Shield.io]: https://shields.io
[Piskel]: https://www.piskelapp.com/
[Pixabay]: https://pixabay.com
[Fading Revelations]: https://github.com/Fresh791/Fading-Revelations
[releases]: https://github.com/UnQuis/Fading-Revelations-Remake/releases
[Fading Revelations Wiki!]: https://fadingrevelations.miraheze.org/wiki/Main_Page

[Stargazers]: https://github.com/UnQuis/Fading-Revelations-Remake/stargazers
[Commits]: https://github.com/UnQuis/Fading-Revelations-Remake/commits
[Issues]: https://github.com/UnQuis/Fading-Revelations-Remake/issues
[Pulls]: https://github.com/UnQuis/Fading-Revelations-Remake/pulls
[Build]: https://github.com/UnQuis/Fading-Revelations-Remake/actions/workflows/build.yml
[License]: LICENSE
[Logo]: github/Banner.png

<!----------------------------------[ Badges ]------------------------------->

[Badge Build]: https://img.shields.io/github/actions/workflow/status/UnQuis/Fading-Revelations-Remake/build.yml?style=for-the-badge&label=Build&color=2ea043&logo=githubactions&logoColor=white
[Badge Commit]: https://img.shields.io/github/last-commit/UnQuis/Fading-Revelations-Remake?style=for-the-badge&label=Last%20Commit&color=42a5f5&logo=git&logoColor=white
[Badge Stars]: https://img.shields.io/github/stars/UnQuis/Fading-Revelations-Remake?style=for-the-badge&label=Stars&color=ffd37f&logo=apachespark&logoColor=black
[Badge Issues]: https://img.shields.io/github/issues/UnQuis/Fading-Revelations-Remake?style=for-the-badge&label=Issues&color=e05f5f&logo=github&logoColor=white
[Badge PRs]: https://img.shields.io/github/issues-pr/UnQuis/Fading-Revelations-Remake?style=for-the-badge&label=Pull%20Requests&color=8a6fd1&logo=github&logoColor=white
[Badge License]: https://img.shields.io/github/license/UnQuis/Fading-Revelations-Remake?style=for-the-badge&label=License&color=0369a3&logo=gnu&logoColor=white
[Badge Mindustry]: https://img.shields.io/badge/Mindustry-v160.5-0369a3?style=for-the-badge&labelColor=2f6fdb&logo=mindustry&logoColor=white
