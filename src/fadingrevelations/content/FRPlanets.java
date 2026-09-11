package fadingrevelations.content;

import arc.graphics.Color;
import arc.math.geom.Mat3D;
import arc.math.geom.Vec3;
import mindustry.content.Blocks;
import mindustry.content.Planets;
import mindustry.type.Planet;
import mindustry.type.Sector;
import mindustry.graphics.g3d.GenericMesh;
import mindustry.graphics.g3d.HexMesh;
import mindustry.graphics.g3d.HexSkyMesh;
import mindustry.graphics.g3d.PlanetParams;
import fadingrevelations.graphics.FROrbitalRingMesh;
import static fadingrevelations.content.FRPlanetGenerators.*;

public class FRPlanets {
    public static Planet cerbero, cangirus, hathor;

    public static void load() {
        cerbero = new Planet("cerbero", Planets.sun, 1f, 2) {{
            localizedName = "Cerbero";
            description = "A barren planet where tragedy struck long ago.";
            alwaysUnlocked = true;
            visible = true;
            accessible = true;
            
            meshLoader = () -> new HexMesh(this, 6);
            generator = new CerberoGenerator() {{ seed = 69; }};
            
            radius = 1f;
            minZoom = 1.8f;
            drawOrbit = true;
            orbitRadius = 8f;
            position = new Vec3(8f, 0f, 0f);
            atmosphereColor = Color.valueOf("1a1518");
            iconColor = Color.valueOf("29302b");
            hasAtmosphere = true;
            atmosphereRadIn = 0.15f;
            atmosphereRadOut = 0.4f;
            defaultCore = Blocks.coreShard;
            parent = Planets.sun;
            solarSystem = Planets.sun;
        }};

        hathor = new Planet("hathor", cerbero, 0.4f, 2) {{
            localizedName = "Hathor";
            description = "A small moon of Cerbero.";
            alwaysUnlocked = true;
            visible = true;
            accessible = false;
            
            meshLoader = () -> new HexMesh(this, 4);
            generator = new HathorGenerator() {{ seed = 69420; }};
            
            orbitSpacing = 12;
            radius = 0.4f;
            minZoom = 1.8f;
            drawOrbit = true;
            orbitRadius = 2f;
            position = new Vec3(2f, 0f, 0f);
            atmosphereColor = Color.valueOf("0f0d0e");
            iconColor = Color.valueOf("222020");
            hasAtmosphere = false;
            atmosphereRadIn = 0.1f;
            atmosphereRadOut = 0.3f;
            defaultCore = Blocks.coreShard;
            parent = cerbero;
            solarSystem = Planets.sun;
        }};

        cangirus = new Planet("cangirus", Planets.sun, 1f, 2) {{
            localizedName = "Cangirus";
            description = "A lush planet with land and water.";
            alwaysUnlocked = true;
            visible = true;
            accessible = true;

            meshLoader = () -> new HexMesh(this, 5);
            //clouds + the Orbital Ring megastructure, which appears quadrant by
            //quadrant as the Orbital Ring project progresses (FROrbitalRing)
            cloudMeshLoader = () -> {
                GenericMesh clouds = new HexSkyMesh(this, 15, 0.32f, 0.08f, 6, Color.valueOf("eafffd7e"), 3, 0.7f, 1f, 0.6f);
                GenericMesh ring = new FROrbitalRingMesh(this);
                return new GenericMesh() {
                    @Override
                    public void render(PlanetParams params, Mat3D projection, Mat3D transform) {
                        clouds.render(params, projection, transform);
                        ring.render(params, projection, transform);
                    }

                    @Override
                    public void dispose() {
                        clouds.dispose();
                        ring.dispose();
                    }
                };
            };
            generator = new CangirusGenerator() {{ seed = 69; }};

            radius = 1f;
            minZoom = 1.5f;
            drawOrbit = true;
            orbitRadius = 12f;
            position = new Vec3(12f, 0f, 0f);
            atmosphereColor = Color.valueOf("5992af");
            iconColor = Color.valueOf("4b64a9");
            hasAtmosphere = true;
            atmosphereRadIn = 0.12f;
            atmosphereRadOut = 0.45f;
            //keep the ring visible when the planet itself is off-screen
            clipRadius = 2.2f;
            defaultCore = Blocks.coreShard;
            parent = Planets.sun;
            solarSystem = Planets.sun;
            allowSectorInvasion = true;
        }};
    }
}