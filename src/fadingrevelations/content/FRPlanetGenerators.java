package fadingrevelations.content;

import arc.math.Mathf;
import arc.util.noise.Ridged;
import arc.util.noise.Simplex;
import mindustry.content.Blocks;
import mindustry.maps.generators.PlanetGenerator;
import mindustry.world.Block;
import mindustry.world.TileGen;
import arc.math.geom.Vec3;
import arc.graphics.Color;

public class FRPlanetGenerators {
    public static class CerberoGenerator extends PlanetGenerator {
        {
            seed = 69;
        }

        public float getNoise(Vec3 position) {
            return Simplex.noise3d(seed, 4, 0.5f, 10f, position.x, position.y, position.z);
        }

        @Override
        public float getHeight(Vec3 position) {
            float h = getNoise(position);
            return 0.5f + h * 0.3f;
        }

        @Override
        public float getSizeScl() {
            return 2200;
        }

        @Override
        public void getColor(Vec3 position, Color out) {
            float h = getNoise(position);
            
            if (h < -0.2f) {
                out.set(60, 50, 45, 255);
            } else if (h < 0.1f) {
                out.set(90, 75, 65, 255);
            } else if (h < 0.4f) {
                out.set(120, 100, 85, 255);
            } else {
                out.set(70, 60, 55, 255);
            }
        }

        @Override
        protected void genTile(Vec3 position, TileGen tile) {
            float h = getNoise(position);
            
            if (h < -0.2f) {
                tile.floor = Blocks.basalt;
            } else if (h < 0.1f) {
                tile.floor = Blocks.yellowStone;
            } else if (h < 0.4f) {
                tile.floor = Blocks.stone;
            } else {
                tile.floor = Blocks.craters;
            }
            
            tile.block = tile.floor.asFloor().wall;
        }

        @Override
        protected void generate() {
            distort(6, 10);
            median(3);
            scatter(Blocks.basalt, Blocks.yellowStone, 0.3f);
            decoration(0.02f);
        }
    }

    public static class HathorGenerator extends PlanetGenerator {
        {
            seed = 69420;
        }

        @Override
        public float getHeight(Vec3 position) {
            float h = Simplex.noise3d(seed, 3, 0.6f, 8f, position.x, position.y, position.z);
            return 0.5f + h * 0.2f;
        }

        @Override
        public float getSizeScl() {
            return 2200;
        }

        @Override
        public void getColor(Vec3 position, Color out) {
            float h = Simplex.noise3d(seed, 4, 0.5f, 10f, position.x, position.y, position.z);
            
            if (h > 0.1f) {
                out.set(100, 95, 90, 255);
            } else {
                out.set(50, 45, 42, 255);
            }
        }

        @Override
        protected void genTile(Vec3 position, TileGen tile) {
            float h = Simplex.noise3d(seed, 4, 0.5f, 10f, position.x, position.y, position.z);
            tile.floor = h > 0.1f ? Blocks.stone : Blocks.basalt;
            tile.block = tile.floor.asFloor().wall;
        }

        @Override
        protected void generate() {
            distort(4, 6);
            median(2);
        }
    }

    public static class CangirusGenerator extends PlanetGenerator {
        {
            seed = 69;
        }

        public float getNoise(Vec3 position) {
            return Simplex.noise3d(seed, 4, 0.5f, 10f, position.x, position.y, position.z);
        }

        @Override
        public float getHeight(Vec3 position) {
            float h = getNoise(position);
            return 0.5f + h * 0.35f;
        }

        @Override
        public float getSizeScl() {
            return 2200;
        }

        @Override
        public void getColor(Vec3 position, Color out) {
            float h = getNoise(position);
            
            if (h < -0.4f) {
                out.set(20, 60, 120, 255);
            } else if (h < -0.1f) {
                out.set(30, 100, 160, 255);
            } else if (h < 0.1f) {
                out.set(230, 210, 150, 255);
            } else if (h < 0.3f) {
                out.set(80, 140, 60, 255);
            } else if (h < 0.5f) {
                out.set(80, 75, 70, 255);
            } else {
                out.set(90, 85, 80, 255);
            }
        }

        @Override
        protected void genTile(Vec3 position, TileGen tile) {
            float h = getNoise(position);
            
            if (h < -0.4f) {
                tile.floor = Blocks.darksand;
            } else if (h < -0.1f) {
                tile.floor = Blocks.water;
            } else if (h < 0.1f) {
                tile.floor = Blocks.sand;
            } else if (h < 0.3f) {
                tile.floor = Blocks.grass;
            } else if (h < 0.5f) {
                tile.floor = Blocks.craters;
            } else {
                tile.floor = Blocks.stone;
            }
            
            tile.block = tile.floor.asFloor().wall;
        }

        @Override
        protected void generate() {
            distort(8, 12);
            median(4);
            scatter(Blocks.sand, Blocks.grass, 0.3f);
            decoration(0.03f);
        }
    }
}