package fadingrevelations.content;

import mindustry.type.SectorPreset;

public class FRSectorPresets {
    public static SectorPreset
        exordium, dree, sporeDunes, abandonedBattlefield,
        etnaticIsles, carbonicDownpour;

    public static void load() {
        exordium = new SectorPreset("exordium", FRPlanets.cerbero, 0) {{
            localizedName = "Exordium";
            description = "You arrive on Cerbero. This is the sector that the computer calculated the least danger for. Start your journey. Set up basic operations. Prepare for more difficult sectors.";
            alwaysUnlocked = true;
            addStartingItems = true;
            captureWave = 11;
            difficulty = 2;
            allowLaunchLoadout = true;
            overrideLaunchDefaults = true;
            startWaveTimeMultiplier = 3f;
        }};

        dree = new SectorPreset("dree", FRPlanets.cerbero, 12) {{
            localizedName = "Dree";
            description = "The next step on your journey through Cerbero. This sector has coal and water. Use coal to create primitive power. Upgrade your resources.";
            difficulty = 2;
            captureWave = 20;
            allowLaunchLoadout = true;
            addStartingItems = true;
        }};

        sporeDunes = new SectorPreset("spore-dunes", FRPlanets.cerbero, 13) {{
            localizedName = "Spore Dunes";
            description = "The next challenging sector on Cerbero. Has Titanium. Harder waves.";
            difficulty = 3;
            captureWave = 20;
            allowLaunchLoadout = true;
            addStartingItems = true;
        }};

        abandonedBattlefield = new SectorPreset("abandoned-battlefield", FRPlanets.cerbero, 14) {{
            localizedName = "Abandoned Battlefield";
            description = "The last sector before you conquer Cerbero. The instruments register many powerful foes here. Waves will be sent relentlessly. Try to survive!";
            difficulty = 10;
            captureWave = 40;
            isLastSector = true;
            allowLaunchLoadout = true;
            addStartingItems = true;
        }};

        etnaticIsles = new SectorPreset("etnatic-isles", FRPlanets.cangirus, 0) {{
            localizedName = "Etnatic Isles";
            description = "The Crux's primary manufacturing plant for flammables and circuitry. Eliminate the outposts, bust through the main gate and secure valuable resources.";
            alwaysUnlocked = true;
            addStartingItems = true;
            difficulty = 2;
            allowLaunchLoadout = true;
            noLighting = true;
        }};

        carbonicDownpour = new SectorPreset("carbonic-downpour", FRPlanets.cangirus, 14) {{
            localizedName = "Carbonic Downpour";
            description = "The enemy has entrenched in an acidic, barren wasteland. Take advantage of the abundant natural resources, research acid processing, and invade the enemy position.";
            difficulty = 4;
            allowLaunchLoadout = true;
            addStartingItems = true;
            noLighting = true;
        }};
    }
}
