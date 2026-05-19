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
    public float octaves = 4f;
    public float persistence = 0.7f;
    public float scale = 24f;
    public float mag = 1.4f;
    public float thresh = 1.1f;
    public float min = 100f;
    public float max = 100f;
    public float radMin = 300f;
    public float radMax = 700f;
    public float waterOffset = 0.6f;

    public static class CerberoGenerator extends FRPlanetGenerators {
        {
            octaves = 3f;
            persistence = 0.52f;
            scale = 22f;
            mag = 0.721f;
            thresh = 1.152f;
            min = 220f;
            max = 600f;
            radMin = 220f;
            radMax = 600f;
            waterOffset = 0f;
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

        public float getRawHeight(Vec3 position) {
            return Simplex.noise3d(seed, octaves, persistence, scale, position.x, position.y, position.z) * mag;
        }

        public float getTerrainNoise(Vec3 position) {
            return Simplex.noise3d(seed + 3, 5, 0.8f, 3.1f, position.x, position.y, position.z) * 1.2f;
        }

        public Block getFloor(Vec3 position) {
            float terr = Math.abs(getTerrainNoise(position));
            int size = terrains.length;
            int y = Mathf.clamp(Mathf.round(terr * size), 0, size - 1);
            return terrains[0][y];
        }

        @Override
        public float getHeight(Vec3 position) {
            float height = getRawHeight(position);
            return Math.max(radMin, Math.min(radMax, min + height));
        }

        @Override
        public void getColor(Vec3 position, Color out) {
            float height = getRawHeight(position);
            out.set(getFloor(position).mapColor);
            if(height > thresh + 0.3f) {
                out.lerp(Color.valueOf("1a1a1a"), Mathf.clamp((height - thresh - 0.3f) * 0.5f));
            }
        }

        @Override
        protected void genTile(Vec3 position, TileGen tile) {
            float height = getRawHeight(position);
            tile.floor = getFloor(position);
            tile.block = tile.floor.asFloor().wall;
            if(Ridged.noise3d(seed + 5, position.x, position.y, position.z, 3, 12f) > -0.35f) {
                tile.block = Blocks.air;
            }
        }

        @Override
        protected void generate() {
            distort(8, 12);
            median(4);
            scatter(Blocks.basalt, Blocks.yellowStone, 0.35f);
            decoration(0.02f);
            distort(4, 5);
        }
    }

    public static class HathorGenerator extends FRPlanetGenerators {
        {
            octaves = 3f;
            persistence = 0.6f;
            scale = 30f;
            mag = 0.5f;
            thresh = 0.5f;
            min = 420f;
            max = 420f;
            radMin = 420f;
            radMax = 420f;
            waterOffset = 0f;
        }

        @Override
        public float getHeight(Vec3 position) {
            return 420f + Simplex.noise3d(seed, octaves, persistence, scale, position.x, position.y, position.z) * 50f;
        }

        @Override
        public void getColor(Vec3 position, Color out) {
            out.set(Blocks.stone.mapColor);
        }

        @Override
        protected void genTile(Vec3 position, TileGen tile) {
            float noise = Simplex.noise3d(seed, 4, 0.5f, 10f, position.x, position.y, position.z);
            tile.floor = noise > 0.2f ? Blocks.stone : Blocks.basalt;
            tile.block = tile.floor.asFloor().wall;
        }

        @Override
        protected void generate() {
            distort(4, 6);
            median(2);
        }
    }

    public static class CangirusGenerator extends FRPlanetGenerators {
        {
            octaves = 4f;
            persistence = 0.55f;
            scale = 25f;
            mag = 1.3f;
            thresh = 0.8f;
            min = 250f;
            max = 550f;
            radMin = 250f;
            radMax = 550f;
            waterOffset = 0.5f;
        }

        Block dw = Blocks.darksand;
        Block w = Blocks.water;
        Block sd = Blocks.sand;
        Block gr = Blocks.grass;
        Block cr = Blocks.craters;
        Block st = Blocks.stone;
        Block bs = Blocks.basalt;

        Block[][] terrains = {
            {dw, dw, w, w, sd},
            {dw, w, w, sd, sd},
            {w, sd, sd, gr, gr},
            {sd, gr, gr, cr, cr},
            {gr, cr, cr, st, st},
            {cr, cr, st, st, st}
        };

        public float getRawHeight(Vec3 position) {
            return Simplex.noise3d(seed, octaves, persistence, scale, position.x, position.y, position.z) * mag;
        }

        public float getTerrainNoise(Vec3 position) {
            return Simplex.noise3d(seed + 3, 5, 0.8f, 3.1f, position.x, position.y, position.z);
        }

        public Block getFloor(Vec3 position) {
            float raw = Math.abs(getRawHeight(position));
            float terr = Math.abs(getTerrainNoise(position));

            int size = terrains.length;
            int x = Mathf.clamp(Mathf.round(raw * size * 0.5f), 0, size - 1);
            int tSize = terrains[x].length;
            int y = Mathf.clamp(Mathf.round(terr * tSize), 0, tSize - 1);

            return terrains[x][y];
        }

        @Override
        public float getHeight(Vec3 position) {
            float height = getRawHeight(position);
            float clamped = Math.max(height, waterOffset);
            return Math.max(radMin, Math.min(radMax, min + clamped));
        }

        @Override
        public void getColor(Vec3 position, Color out) {
            float height = getRawHeight(position);
            out.set(getFloor(position).mapColor);
            if(height > thresh + 0.5f) {
                out.lerp(Color.valueOf("4a6741"), Mathf.clamp((height - thresh - 0.5f) * 0.3f));
            }
        }

        @Override
        protected void genTile(Vec3 position, TileGen tile) {
            float height = getRawHeight(position);
            tile.floor = getFloor(position);
            tile.block = tile.floor.asFloor().wall;
            if(Ridged.noise3d(seed + 5, position.x, position.y, position.z, 3, 12f) > -0.4f) {
                tile.block = Blocks.air;
            }
        }

        @Override
        protected void generate() {
            distort(8, 12);
            median(4);
            scatter(Blocks.sand, Blocks.grass, 0.3f);
            scatter(Blocks.grass, Blocks.craters, 0.4f);
            decoration(0.03f);
            distort(4, 5);
        }
    }
}