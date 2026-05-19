package fadingrevelations.content;

import arc.graphics.Color;
import mindustry.content.Blocks;
import mindustry.content.Planets;
import mindustry.type.Planet;
import mindustry.graphics.g3d.NoiseMesh;
import mindustry.graphics.g3d.HexSkyMesh;

public class FRPlanets {
    public static Planet cerbero, cangirus, hathor;

    public static void load() {
        cerbero = new Planet("cerbero", Planets.sun, 1f, 1) {{
            localizedName = "Cerbero";
            description = "A barren planet where tragedy struck long ago.";
            alwaysUnlocked = true;
            visible = true;
            accessible = true;
            
            mesh = new NoiseMesh(this, 69, 6, Color.valueOf("2b2930"), 1.2f, 3, 0.8f, 1f, 1.5f);
            
            radius = 1f;
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

        hathor = new Planet("hathor", cerbero, 0.4f, 1) {{
            localizedName = "Hathor";
            description = "A small moon of Cerbero.";
            alwaysUnlocked = true;
            visible = true;
            accessible = false;
            
            mesh = new NoiseMesh(this, 69420, 4, Color.valueOf("29302b"), 0.1f, 3, 0.8f, 1f, 1.5f);
            
            orbitSpacing = 12;
            radius = 0.4f;
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

        cangirus = new Planet("cangirus", Planets.sun, 1f, 1) {{
            localizedName = "Cangirus";
            description = "A lush planet with land and water.";
            alwaysUnlocked = true;
            visible = true;
            accessible = false;
            
            mesh = new NoiseMesh(this, 69, 5, Color.valueOf("4b64a9"), 1.2f, 4, 1f, 0.75f, 1f);
            cloudMesh = new HexSkyMesh(this, 15, 0.32f, 0.08f, 6, Color.valueOf("eafffd7e"), 3, 0.7f, 1f, 0.6f);
            
            radius = 1f;
            minZoom = 1.5f;
            drawOrbit = true;
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