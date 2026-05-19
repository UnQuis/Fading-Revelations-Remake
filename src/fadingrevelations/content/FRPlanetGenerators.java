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

public class FRPlanetGenerators extends PlanetGenerator {
    public static class CerberoGenerator extends PlanetGenerator {
        {
            seed = 69;
        }

        Block st = Blocks.stone;
        Block bs = Blocks.basalt;
        Block ys = Blocks.yellowStone;
        Block cr = Blocks.craters;

        Block[][] terrains = {
            {bs, bs, ys, ys, st},
            {bs, ys, ys, st, st},
            {ys, ys, st, st, cr},
            {ys, st, st, cr, cr},
            {st, st, cr, cr, cr}
        };

        public float getNoise(Vec3 position) {
            return Simplex.noise3d(seed, 4, 0.5f, 10f, position.x, position.y, position.z);
        }

        public Block getFloor(Vec3 position) {
            float terr = Math.abs(getNoise(position));
            int size = terrains.length;
            int y = Mathf.clamp(Mathf.round(terr * size * 0.8f), 0, size - 1);
            return terrains[0][y];
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
            out.set(getFloor(position).mapColor);
        }

        @Override
        protected void genTile(Vec3 position, TileGen tile) {
            tile.floor = getFloor(position);
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
            out.set(Blocks.stone.mapColor);
        }

        @Override
        protected void genTile(Vec3 position, TileGen tile) {
            float noise = Simplex.noise3d(seed, 4, 0.5f, 10f, position.x, position.y, position.z);
            tile.floor = noise > 0.1f ? Blocks.stone : Blocks.basalt;
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

        Block dw = Blocks.darksand;
        Block w = Blocks.water;
        Block sd = Blocks.sand;
        Block gr = Blocks.grass;
        Block cr = Blocks.craters;
        Block st = Blocks.stone;

        Block[][] terrains = {
            {dw, dw, w, w, sd},
            {dw, w, w, sd, sd},
            {w, sd, sd, gr, gr},
            {sd, gr, gr, cr, cr},
            {gr, cr, cr, st, st},
            {cr, cr, st, st, st}
        };

        public float getNoise(Vec3 position) {
            return Simplex.noise3d(seed, 4, 0.5f, 10f, position.x, position.y, position.z);
        }

        public Block getFloor(Vec3 position) {
            float raw = Math.abs(getNoise(position));
            float terr = Math.abs(Simplex.noise3d(seed + 3, 5, 0.8f, 5f, position.x, position.y, position.z));

            int size = terrains.length;
            int x = Mathf.clamp(Mathf.round(raw * size * 0.6f), 0, size - 1);
            int tSize = terrains[x].length;
            int y = Mathf.clamp(Mathf.round(terr * tSize), 0, tSize - 1);

            return terrains[x][y];
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
            out.set(getFloor(position).mapColor);
        }

        @Override
        protected void genTile(Vec3 position, TileGen tile) {
            tile.floor = getFloor(position);
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