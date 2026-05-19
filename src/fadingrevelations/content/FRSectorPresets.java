package fadingrevelations.content;

import mindustry.type.SectorPreset;

public class FRSectorPresets {
    public static SectorPreset
        exordium, dree, sporeDunes, abandonedBattlefield,
        etnaticIsles, carbonicDownpour;

    public static void load() {
        exordium = new SectorPreset("exordium", FRPlanets.cerbero, 0) {{
            alwaysUnlocked = true;
            addStartingItems = true;
            captureWave = 11;
            difficulty = 2;
            allowLaunchLoadout = true;
            overrideLaunchDefaults = true;
            startWaveTimeMultiplier = 3f;
        }};

        dree = new SectorPreset("dree", FRPlanets.cerbero, 12) {{
            difficulty = 2;
            captureWave = 20;
            allowLaunchLoadout = true;
            addStartingItems = true;
        }};

        sporeDunes = new SectorPreset("spore-dunes", FRPlanets.cerbero, 13) {{
            difficulty = 3;
            captureWave = 20;
            allowLaunchLoadout = true;
            addStartingItems = true;
        }};

        abandonedBattlefield = new SectorPreset("abandoned-battlefield", FRPlanets.cerbero, 14) {{
            difficulty = 10;
            captureWave = 40;
            isLastSector = true;
            allowLaunchLoadout = true;
            addStartingItems = true;
        }};

        etnaticIsles = new SectorPreset("etnatic-isles", FRPlanets.cangirus, 0) {{
            alwaysUnlocked = true;
            addStartingItems = true;
            difficulty = 2;
            allowLaunchLoadout = true;
            noLighting = true;
        }};

        carbonicDownpour = new SectorPreset("carbonic-downpour", FRPlanets.cangirus, 14) {{
            difficulty = 4;
            allowLaunchLoadout = true;
            addStartingItems = true;
            noLighting = true;
        }};
    }
}
