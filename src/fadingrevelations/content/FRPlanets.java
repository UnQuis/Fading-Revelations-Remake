package fadingrevelations.content;

import arc.graphics.Color;
import mindustry.content.Blocks;
import mindustry.content.Planets;
import mindustry.type.Planet;
import mindustry.graphics.g3d.HexMesh;
import mindustry.graphics.g3d.HexSkyMesh;
import mindustry.graphics.g3d.MultiMesh;
import static fadingrevelations.content.FRPlanetGenerators.*;

public class FRPlanets {
    public static Planet cerbero, cangirus, hathor;

    public static void load() {
        cerbero = new Planet("cerbero", Planets.sun, 1.5f, 1) {{
            localizedName = "Cerbero";
            description = "A barren planet where tragedy struck long ago. Nothing is left here besides destruction and death.";
            alwaysUnlocked = true;
            visible = true;
            accessible = true;
            
            meshLoader = () -> new HexMesh(this, 6);
            
            generator = new CerberoGenerator() {{
                seed = 69;
            }};
            
            radius = 1.5f;
            minZoom = 1.8f;
            drawOrbit = true;
            atmosphereColor = Color.valueOf("1a1518");
            iconColor = Color.valueOf("29302b");
            hasAtmosphere = true;
            atmosphereRadIn = 0.15f;
            atmosphereRadOut = 0.4f;
            defaultCore = Blocks.coreShard;
            parent = Planets.sun;
            solarSystem = Planets.sun;
            allowLaunchSchematics = false;
            allowLaunchLoadout = false;
        }};

        hathor = new Planet("hathor", cerbero, 0.3f, 1) {{
            localizedName = "Hathor";
            description = "A small piece of Cerbero created by the impact of a meteor.";
            alwaysUnlocked = true;
            visible = true;
            accessible = false;
            
            meshLoader = () -> new HexMesh(this, 4);
            
            generator = new HathorGenerator() {{
                seed = 69420;
            }};
            
            orbitSpacing = 12;
            radius = 0.3f;
            minZoom = 1.8f;
            drawOrbit = true;
            atmosphereColor = Color.valueOf("0f0d0e");
            iconColor = Color.valueOf("222020");
            hasAtmosphere = false;
            atmosphereRadIn = 0.1f;
            atmosphereRadOut = 0.3f;
            defaultCore = Blocks.coreShard;
            parent = cerbero;
            solarSystem = Planets.sun;
        }};

        cangirus = new Planet("cangirus", Planets.sun, 1.2f, 1) {{
            localizedName = "Cangirus";
            description = "A lush planet that has land and water.";
            alwaysUnlocked = true;
            visible = true;
            accessible = false;
            
            meshLoader = () -> new HexMesh(this, 5);
            cloudMeshLoader = () -> new HexSkyMesh(this, 15, 0.32f, 0.08f, 6, Color.valueOf("eafffd7e"), 3, 0.7f, 1f, 0.6f);
            
            generator = new CangirusGenerator() {{
                seed = 69;
            }};
            
            orbitSpacing = 4;
            radius = 1.2f;
            minZoom = 1.5f;
            drawOrbit = true;
            orbitRadius = 24;
            atmosphereColor = Color.valueOf("5992af");
            iconColor = Color.valueOf("4b64a9");
            hasAtmosphere = true;
            atmosphereRadIn = 0.12f;
            atmosphereRadOut = 0.45f;
            defaultCore = Blocks.coreShard;
            parent = Planets.sun;
            solarSystem = Planets.sun;
            allowLaunchSchematics = false;
            allowLaunchLoadout = false;
            allowSectorInvasion = true;
        }};
    }
}