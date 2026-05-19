package fadingrevelations.content;

import arc.math.Mathf;
import arc.util.noise.Simplex;
import mindustry.content.Blocks;
import mindustry.maps.generators.PlanetGenerator;
import mindustry.world.Block;
import mindustry.world.TileGen;
import arc.math.geom.Vec3;
import arc.graphics.Color;
import mindustry.game.Schematics;

import static mindustry.Vars.*;

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
            if (h < -0.1f) out.set(Blocks.basalt.mapColor);
            else if (h < 0.2f) out.set(Blocks.yellowStone.mapColor);
            else out.set(Blocks.stone.mapColor);
        }

        @Override
        protected void genTile(Vec3 position, TileGen tile) {
            float h = getNoise(position);
            if (h < -0.1f) tile.floor = Blocks.basalt;
            else if (h < 0.2f) tile.floor = Blocks.yellowStone;
            else tile.floor = Blocks.stone;
            tile.block = tile.floor.asFloor().wall;
        }

        @Override
        protected void generate() {
            distort(6, 10);
            median(3);
            pass((x, y) -> {
                if (noise(x, y, 20f, 0.6f) > 0.85f && floor.asFloor().hasSurface()) {
                    block = floor.asFloor().wall;
                }
            });
            Schematics.placeLaunchLoadout(width / 2, height / 2);
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
            out.set(h > 0.1f ? Blocks.stone.mapColor : Blocks.basalt.mapColor);
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
            Schematics.placeLaunchLoadout(width / 2, height / 2);
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
            if (h < -0.3f) out.set(Blocks.darksand.mapColor);
            else if (h < 0f) out.set(Blocks.water.mapColor);
            else if (h < 0.2f) out.set(Blocks.sand.mapColor);
            else if (h < 0.4f) out.set(Blocks.grass.mapColor);
            else out.set(Blocks.stone.mapColor);
        }

        @Override
        protected void genTile(Vec3 position, TileGen tile) {
            float h = getNoise(position);
            if (h < -0.3f) tile.floor = Blocks.darksand;
            else if (h < 0f) tile.floor = Blocks.water;
            else if (h < 0.2f) tile.floor = Blocks.sand;
            else if (h < 0.4f) tile.floor = Blocks.grass;
            else tile.floor = Blocks.stone;
            tile.block = tile.floor.asFloor().wall;
        }

        @Override
        protected void generate() {
            distort(8, 12);
            median(4);
            pass((x, y) -> {
                if (noise(x, y, 25f, 0.5f) > 0.87f && floor.asFloor().hasSurface()) {
                    block = floor.asFloor().wall;
                }
            });
            Schematics.placeLaunchLoadout(width / 2, height / 2);
        }
    }
}