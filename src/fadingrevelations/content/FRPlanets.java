package fadingrevelations.content;
import arc.graphics.Color;
import mindustry.content.*;
import mindustry.type.*;
import mindustry.graphics.g3d.NoiseMesh;
import mindustry.maps.generators.PlanetGenerator;

public class FRPlanets {
    public static Planet cerbero, cangirus, hathor;

    public static void load() {
        cerbero = new Planet("cerbero", Planets.sun, 1.5f, 4) {{
            localizedName = "Cerbero";
            description = "A barren planet where tragedy struck long ago. Nothing is left here besides destruction and death.";
            alwaysUnlocked = true;
            mesh = new NoiseMesh(this, 69, 6, Color.valueOf("2b2930"), 1.2f, 3, 0.8f, 1f, 1.5f);
            startSector = 0;
            generator = new PlanetGenerator() {{
                baseSeed = 0;
                seed = 0;
            }};
            orbitSpacing = 4;
            minZoom = 1.8f;
            allowLaunchSchematics = false;
            allowLaunchLoadout = false;
            drawOrbit = true;
            atmosphereRadIn = 0;
            atmosphereRadOut = 0.3f;
            prebuildBase = false;
            accessible = true;
            updateLighting = true;
            sectorSeed = 6;
            bloom = false;
            visible = true;
            atmosphereColor = Color.valueOf("000000");
            iconColor = Color.valueOf("29302b");
            hasAtmosphere = true;
            clearSectorOnLose = true;
            allowWaves = true;
            defaultCore = Blocks.coreShard;
            allowLaunchToNumbered = false;
        }};

        hathor = new Planet("hathor", cerbero, 0.3f, 4) {{
            localizedName = "Hathor";
            description = "A small piece of Cerbero created by the impact of a meteor. Nothing lasts here except an ancient Cerberian base built here before everything collapsed...";
            alwaysUnlocked = true;
            mesh = new NoiseMesh(this, 0, 4, Color.valueOf("29302b"), 0.1f, 3, 0.8f, 1f, 1.5f);
            generator = new PlanetGenerator() {{
                baseSeed = 69420;
                seed = 69420;
            }};
            radius = 0.3f;
            orbitSpacing = 12;
            minZoom = 1.8f;
            drawOrbit = true;
            atmosphereRadIn = 0;
            atmosphereRadOut = 0.3f;
            accessible = false;
            updateLighting = true;
            sectorSeed = 0;
            bloom = false;
            visible = true;
            atmosphereColor = Color.valueOf("000000");
            iconColor = Color.valueOf("29302b");
            hasAtmosphere = false;
            clearSectorOnLose = true;
            allowWaves = true;
            defaultCore = Blocks.coreShard;
        }};

        cangirus = new Planet("cangirus", Planets.sun, 1.2f, 4) {{
            localizedName = "Cangirus";
            description = "A lush planet that has land and water.";
            alwaysUnlocked = true;
            mesh = new NoiseMesh(this, 0, 5, Color.valueOf("4b64a9"), 1.2f, 4, 1f, 0.75f, 1f);
            generator = new PlanetGenerator() {{
                baseSeed = 0;
                seed = 0;
            }};
            allowLaunchSchematics = false;
            allowLaunchLoadout = false;
            allowSectorInvasion = true;
            startSector = 0;
            orbitSpacing = 4;
            minZoom = 1.5f;
            drawOrbit = true;
            atmosphereRadIn = 0;
            atmosphereRadOut = 0.3f;
            accessible = false;
            orbitRadius = 24;
            updateLighting = false;
            sectorSeed = 69420;
            bloom = false;
            visible = true;
            atmosphereColor = Color.valueOf("5992af");
            iconColor = Color.valueOf("4b64a9");
            hasAtmosphere = true;
            clearSectorOnLose = true;
            allowWaves = true;
            prebuildBase = false;
            defaultCore = Blocks.coreShard;
            allowLaunchToNumbered = false;
        }};
    }
}
