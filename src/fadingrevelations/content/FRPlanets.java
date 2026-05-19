package fadingrevelations.content;

import arc.graphics.Color;
import mindustry.content.Blocks;
import mindustry.content.Planets;
import mindustry.type.Planet;
import mindustry.graphics.g3d.NoiseMesh;
import mindustry.graphics.g3d.HexSkyMesh;
import mindustry.maps.generators.PlanetGenerator;
import static fadingrevelations.content.FRPlanetGenerators.*;

public class FRPlanets {
    public static Planet cerbero, cangirus, hathor;

    public static void load() {
        // CERBERO - main planet
        cerbero = new Planet("cerbero", Planets.sun, 1.5f, 1) {{
            localizedName = "Cerbero";
            description = "A barren planet where tragedy struck long ago. Nothing is left here besides destruction and death.";
            alwaysUnlocked = true;
            
            // Mesh
            mesh = new NoiseMesh(this, 69, 6, Color.valueOf("2b2930"), 1.2f, 3, 0.8f, 1f, 1.5f);
            
            generator = new CerberoGenerator() {{
                seed = 69;
            }};
            
            startSector = 0;
            orbitSpacing = 4;
            radius = 1.5f;
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
            parent = Planets.sun;
            solarSystem = Planets.sun;
            allowLaunchToNumbered = false;
        }};

        // HATHOR - moon of Cerbero  
        hathor = new Planet("hathor", cerbero, 0.3f, 1) {{
            localizedName = "Hathor";
            description = "A small piece of Cerbero created by the impact of a meteor. Nothing lasts here except an ancient Cerberian base built here before everything collapsed...";
            alwaysUnlocked = true;
            
            mesh = new NoiseMesh(this, 69, 4, Color.valueOf("29302b"), 0.1f, 3, 0.8f, 1f, 1.5f);
            
            generator = new HathorGenerator() {{
                seed = 69420;
            }};
            
            orbitSpacing = 12;
            radius = 0.3f;
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
            parent = cerbero;
            solarSystem = Planets.sun;
        }};

        // CANGIRUS - second planet from sun
        cangirus = new Planet("cangirus", Planets.sun, 1.2f, 1) {{
            localizedName = "Cangirus";
            description = "A lush planet that has land and water.";
            alwaysUnlocked = true;
            
            // Mesh
            mesh = new NoiseMesh(this, 69, 5, Color.valueOf("4b64a9"), 1.2f, 4, 1f, 0.75f, 1f);
            
            // Cloud mesh for Cangirus
            cloudMesh = new HexSkyMesh(this, 15, 0.32f, 0.08f, 6, Color.valueOf("eafffd7e"), 3, 0.7f, 1f, 0.6f);
            
            generator = new CangirusGenerator() {{
                seed = 69;
            }};
            
            allowLaunchSchematics = false;
            allowLaunchLoadout = false;
            allowSectorInvasion = true;
            startSector = 0;
            orbitSpacing = 4;
            radius = 1.2f;
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
            parent = Planets.sun;
            solarSystem = Planets.sun;
            allowLaunchToNumbered = false;
        }};
    }
}