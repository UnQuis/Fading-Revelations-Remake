package fadingrevelations.content;

import arc.math.Mathf;
import arc.struct.FloatSeq;
import arc.struct.Seq;
import arc.util.Tmp;
import arc.util.noise.Ridged;
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
    /** Smoothstep, used for polar caps and terrain blending. */
    private static float smooth(float from, float to, float value) {
        float t = Mathf.clamp((value - from) / (to - from));
        return t * t * (3f - 2f * t);
    }

    /** Samples a colour ramp and writes the interpolated result into {@code out}. */
    private static Color gradient(Color[] colors, float t, Color out) {
        t = Mathf.clamp(t) * (colors.length - 1);
        int i = (int) t;
        int j = Math.min(i + 1, colors.length - 1);
        return out.set(colors[i]).lerp(colors[j], t - i);
    }

    public static class CerberoGenerator extends PlanetGenerator {
        {
            seed = 69;
        }

        /** Visual seed, independent of the per-sector generation seed. */
        private static final int VIS_SEED = 6901;
        private static final Color[] surface = {
            Color.valueOf("171418"), // deep ash basins
            Color.valueOf("28221f"), // shaded lowlands
            Color.valueOf("3d3229"), // dust plains
            Color.valueOf("6d5838"), // yellow-stone flats
            Color.valueOf("a08a68"), // highlands
            Color.valueOf("d8ccb2")  // pale peaks
        };
        private static final Color polarFrost = Color.valueOf("c9c6bf");
        private static final Color peakColor = Color.valueOf("c2b393");
        private static final Color lava = Color.valueOf("d4551f");

        public float getNoise(Vec3 position) {
            return Simplex.noise3d(seed, 4, 0.5f, 10f, position.x, position.y, position.z);
        }

        /** Large, low-frequency landmasses rather than high-frequency noise blobs. */
        private static final float BASIN = 0.36f;

        private float continent(Vec3 position) {
            // Two octaves at a low frequency: one broad continental mass with a few
            // large neighbours, not the dozens of small islands the old 4-octave
            // scale-1.6 noise produced.
            return Simplex.noise3d(VIS_SEED, 2, 0.5f, 0.6f, position.x, position.y, position.z)
                + Simplex.noise3d(VIS_SEED + 6, 3, 0.5f, 4.5f, position.x, position.y, position.z) * 0.02f;
        }

        private float ridges(Vec3 position) {
            return Ridged.noise3d(VIS_SEED + 1, position.x, position.y, position.z, 4, 2.2f);
        }


        @Override
        public float getHeight(Vec3 position) {
            float c = continent(position);
            float base = 0.16f;
            // Dry basins stay flat, which gives the planet readable continental shelves.
            if (c <= BASIN) return base;

            float t = Mathf.clamp((c - BASIN) / (1f - BASIN));
            float mountains = Math.max(0f, ridges(position) - 0.15f) * 0.62f * t;
            float hills = t * 0.3f;
            float detail = Simplex.noise3d(VIS_SEED + 2, 4, 0.55f, 6f,
                position.x, position.y, position.z) * 0.035f * t;
            // Stretch land relief away from the flat basin level so peaks read from orbit.
            return Math.min(1f, base + (hills + mountains + detail) * 1.3f);
        }

        @Override
        public float getSizeScl() {
            return 2200;
        }

        @Override
        public void getColor(Vec3 position, Color out) {
            float c = continent(position);
            float h = getHeight(position);
            float detail = Simplex.noise3d(VIS_SEED + 3, 3, 0.55f, 8f,
                position.x, position.y, position.z);

            if (c <= BASIN) {
                gradient(surface, Mathf.clamp((BASIN - c) / 0.35f) * 0.18f, out);
            } else {
                float t = Mathf.clamp((h - 0.16f) / 0.84f);
                gradient(surface, 0.24f + t * 0.76f + detail * 0.05f, out);

                float rock = smooth(0.3f, 0.9f, ridges(position)) * t;
                if (rock > 0f) out.lerp(peakColor, rock * 0.35f);
            }

            float cracks = Ridged.noise3d(VIS_SEED + 9, position.x * 2.2f, position.y * 2.2f, position.z * 2.2f, 4, 2.6f);
            if (cracks > 0.4f) out.mul(1f - (cracks - 0.4f) * 0.55f);

            float fine = Ridged.noise3d(VIS_SEED + 10, position.x * 4.5f, position.y * 4.5f, position.z * 4.5f, 3, 2.2f);
            out.mul(0.9f + fine * 0.1f);

            float ice = smooth(0.86f, 0.97f, Math.abs(position.y) + detail * 0.03f);
            if (ice > 0f) out.lerp(polarFrost, ice);
            out.mul(0.95f + detail * 0.05f);
        }

        @Override
        public boolean isEmissive() {
            return true;
        }

        /** Faint lava fissures in the deepest basins. */
        @Override
        public void getEmissiveColor(Vec3 position, Color out) {
            if (continent(position) > BASIN - 0.12f) return;
            float crack = Ridged.noise3d(VIS_SEED + 4, position.x, position.y, position.z, 5, 2.6f);
            if (crack > 0.8f) {
                out.set(lava).a(Mathf.clamp((crack - 0.8f) / 0.2f) * 0.7f);
            }
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
            genOres();
            // sparse dead trees for flavor
            pass((x, y) -> {
                if (block == Blocks.air && floor.asFloor().hasSurface() && rand.chance(0.006)) {
                    block = rand.chance(0.5f) ? FREnvironment.gnarledTree : FREnvironment.charredTree;
                }
            });
            Schematics.placeLaunchLoadout(width / 2, height / 2);
        }

        /** Ore distribution adapted from the vanilla Serpulo generator, plus mod ores. */
        private void genOres() {
            // Base ores are unconditional: graphite (-> silicon) and steel sedimentation
            // (-> living steel) are hard requirements for the whole mid/late tree (silicon forge,
            // basic multismelter, mainCore/corePrime, overdriveBeacon, all nano optic walls), so
            // gating them behind noise made a large share of sectors unwinnable.
            Seq<Block> ores = Seq.with(Blocks.oreCopper, Blocks.oreLead, FREnvironment.oreGraphite,
                FREnvironment.steelSedimentation);
            float poles = Math.abs(sector.tile.v.y);
            float nmag = 0.5f;
            float scl = 1f;
            float addscl = 1.3f;

            if (Simplex.noise3d(seed, 2, 0.5f, scl, sector.tile.v.x, sector.tile.v.y, sector.tile.v.z) * nmag + poles > 0.25f * addscl) {
                ores.add(Blocks.oreCoal);
            }
            if (Simplex.noise3d(seed, 2, 0.5f, scl, sector.tile.v.x + 1, sector.tile.v.y, sector.tile.v.z) * nmag + poles > 0.5f * addscl) {
                ores.add(Blocks.oreTitanium);
            }
            if (Simplex.noise3d(seed, 2, 0.5f, scl, sector.tile.v.x + 2, sector.tile.v.y, sector.tile.v.z) * nmag + poles > 0.7f * addscl) {
                ores.add(Blocks.oreThorium);
            }
            if (rand.chance(0.25)) {
                ores.add(Blocks.oreScrap);
            }

            FloatSeq frequencies = new FloatSeq();
            for (int i = 0; i < ores.size; i++) {
                frequencies.add(rand.random(-0.1f, 0.01f) - i * 0.01f + poles * 0.04f);
            }

            pass((x, y) -> {
                if (!floor.asFloor().hasSurface()) return;

                int offsetX = x - 4, offsetY = y + 23;
                for (int i = ores.size - 1; i >= 0; i--) {
                    Block entry = ores.get(i);
                    float freq = frequencies.get(i);
                    if (Math.abs(0.5f - noise(offsetX, offsetY + i * 999, 2, 0.7f, (40 + i * 2))) > 0.22f + i * 0.01 &&
                        Math.abs(0.5f - noise(offsetX, offsetY - i * 999, 1, 1, (30 + i * 4))) > 0.37f + freq) {
                        ore = entry;
                        break;
                    }
                }
            });
        }
    }

    public static class HathorGenerator extends PlanetGenerator {
        {
            seed = 69420;
        }

        /** Visual seed, independent of the per-sector generation seed. */
        private static final int VIS_SEED = 6903;
        private static final Color[] surface = {
            Color.valueOf("232127"), // maria
            Color.valueOf("35323a"), // dark regolith
            Color.valueOf("4a474d"), // lowlands
            Color.valueOf("6b676d"), // highlands
            Color.valueOf("928d93"), // crater rims
            Color.valueOf("c2bdc3")  // peaks
        };
        private static final Color polarIce = Color.valueOf("c8d2d8");
        private static final Color peakColor = Color.valueOf("b8b3ba");

        private static final float BASIN = 0.38f;

        private float continent(Vec3 position) {
            // Same low-frequency treatment as the other bodies: a handful of large
            // maria/highland regions instead of a noisy speckle of small basins.
            return Simplex.noise3d(VIS_SEED, 2, 0.5f, 0.52f, position.x, position.y, position.z)
                + Simplex.noise3d(VIS_SEED + 6, 3, 0.5f, 4.5f, position.x, position.y, position.z) * 0.02f;
        }

        private float ridges(Vec3 position) {
            return Ridged.noise3d(VIS_SEED + 1, position.x, position.y, position.z, 4, 2.4f);
        }


        @Override
        public float getHeight(Vec3 position) {
            float c = continent(position);
            float base = 0.14f;
            if (c <= BASIN) return base;

            float t = Mathf.clamp((c - BASIN) / (1f - BASIN));
            float mountains = Math.max(0f, ridges(position) - 0.12f) * 0.58f * t;
            float hills = t * 0.28f;
            float detail = Simplex.noise3d(VIS_SEED + 2, 4, 0.55f, 6f,
                position.x, position.y, position.z) * 0.03f * t;
            return Math.min(1f, base + (hills + mountains + detail) * 1.3f);
        }

        @Override
        public float getSizeScl() {
            return 2200;
        }

        @Override
        public void getColor(Vec3 position, Color out) {
            float c = continent(position);
            float h = getHeight(position);
            float detail = Simplex.noise3d(VIS_SEED + 3, 3, 0.55f, 8f,
                position.x, position.y, position.z);

            if (c <= BASIN) {
                gradient(surface, Mathf.clamp((BASIN - c) / 0.35f) * 0.16f, out);
            } else {
                float t = Mathf.clamp((h - 0.14f) / 0.86f);
                gradient(surface, 0.2f + t * 0.8f + detail * 0.05f, out);

                float rock = smooth(0.3f, 0.9f, ridges(position)) * t;
                if (rock > 0f) out.lerp(peakColor, rock * 0.3f);
            }

            float ice = smooth(0.87f, 0.98f, Math.abs(position.y) + detail * 0.025f);
            if (ice > 0f) out.lerp(polarIce, ice);
            out.mul(0.95f + detail * 0.05f);
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
            pass((x, y) -> {
                if (noise(x, y, 20f, 0.6f) > 0.88f && floor.asFloor().hasSurface()) {
                    block = floor.asFloor().wall;
                }
            });
            // previously missing entirely: Hathor sectors contained no ore at all, which would make
            // the planet unwinnable the moment accessible is turned on
            genOres();
            Schematics.placeLaunchLoadout(width / 2, height / 2);
        }

        /** Moon-sized ore set: no thorium, no living steel sedimentation. */
        private void genOres() {
            Seq<Block> ores = Seq.with(Blocks.oreCopper, Blocks.oreLead, FREnvironment.oreGraphite);
            float poles = Math.abs(sector.tile.v.y);

            if (Simplex.noise3d(seed, 2, 0.5f, 1f, sector.tile.v.x, sector.tile.v.y, sector.tile.v.z) * 0.5f + poles > 0.3f) {
                ores.add(Blocks.oreCoal);
            }
            if (Simplex.noise3d(seed, 2, 0.5f, 1f, sector.tile.v.x + 1, sector.tile.v.y, sector.tile.v.z) * 0.5f + poles > 0.62f) {
                ores.add(Blocks.oreTitanium);
            }
            if (rand.chance(0.2f)) {
                ores.add(Blocks.oreScrap);
            }

            FloatSeq frequencies = new FloatSeq();
            for (int i = 0; i < ores.size; i++) {
                frequencies.add(rand.random(-0.1f, 0.01f) - i * 0.01f + poles * 0.04f);
            }

            pass((x, y) -> {
                if (!floor.asFloor().hasSurface()) return;

                int offsetX = x - 4, offsetY = y + 23;
                for (int i = ores.size - 1; i >= 0; i--) {
                    Block entry = ores.get(i);
                    float freq = frequencies.get(i);
                    if (Math.abs(0.5f - noise(offsetX, offsetY + i * 999, 2, 0.7f, (40 + i * 2))) > 0.22f + i * 0.01 &&
                        Math.abs(0.5f - noise(offsetX, offsetY - i * 999, 1, 1, (30 + i * 4))) > 0.37f + freq) {
                        ore = entry;
                        break;
                    }
                }
            });
        }
    }

    public static class CangirusGenerator extends PlanetGenerator {
        {
            seed = 69;
        }

        /** Visual seed, independent of the per-sector generation seed. */
        private static final int VIS_SEED = 6902;
        private static final float SEA = 0.18f;
        private static final float COAST = 0.46f;
        private static final Color[] seaColors = {
            Color.valueOf("142c46"), // deep ocean
            Color.valueOf("1f4c63"), // open ocean
            Color.valueOf("3f7f8a")  // shallow coastal water
        };
        private static final Color[] landColors = {
            Color.valueOf("c9b98b"), // beach
            Color.valueOf("6f9a52"), // lowland
            Color.valueOf("477541"), // forest
            Color.valueOf("7c7a5b"), // highland
            Color.valueOf("8e8b80"), // rock
            Color.valueOf("dce6e8")  // snow
        };
        private static final Color[] dryColors = {
            Color.valueOf("c9b98b"), // beach
            Color.valueOf("b9a36a"), // savanna
            Color.valueOf("a88b5c"), // steppe
            Color.valueOf("8f7a5a"), // badlands
            Color.valueOf("8e8b80"), // rock
            Color.valueOf("dce6e8")  // snow
        };
        private static final Color mountainColor = Color.valueOf("b8b3a5");
        private static final Color polarIce = Color.valueOf("d7e6ea");

        public float getNoise(Vec3 position) {
            return Simplex.noise3d(seed, 4, 0.5f, 10f, position.x, position.y, position.z);
        }

        /** Large, low-frequency continents instead of high-frequency noise blobs. */
        private float continent(Vec3 position) {
            // Two octaves at a low frequency: broad continents and a few large
            // islands. The old 4-octave scale-1.7 version produced hundreds of
            // small lakes and made the planet look like noise.
            return Simplex.noise3d(VIS_SEED, 2, 0.5f, 0.55f, position.x, position.y, position.z)
                + Simplex.noise3d(VIS_SEED + 6, 3, 0.5f, 4.5f, position.x, position.y, position.z) * 0.02f;
        }

        private float ridges(Vec3 position) {
            return Ridged.noise3d(VIS_SEED + 1, position.x, position.y, position.z, 4, 2.05f);
        }

        @Override
        public float getHeight(Vec3 position) {
            float c = continent(position);
            // Flat sea level: coastlines become sharp and continents read as solid landmasses.
            if (c <= COAST) return SEA;

            float t = Mathf.clamp((c - COAST) / (1f - COAST));
            float mountains = Math.max(0f, ridges(position) - 0.15f) * 0.65f * t;
            float hills = t * 0.28f;
            float detail = Simplex.noise3d(VIS_SEED + 2, 4, 0.55f, 6f,
                position.x, position.y, position.z) * 0.035f * t;
            // Stretch land relief away from the flat sea level so mountains read from orbit.
            return Math.min(1f, SEA + (hills + mountains + detail) * 1.35f);
        }

        @Override
        public float getSizeScl() {
            return 2200;
        }

        @Override
        public void getColor(Vec3 position, Color out) {
            float c = continent(position);
            float h = getHeight(position);
            float detail = Simplex.noise3d(VIS_SEED + 3, 3, 0.55f, 9f,
                position.x, position.y, position.z);

            if (c <= COAST) {
                float depth = Mathf.clamp((COAST - c) / 0.4f);
                gradient(seaColors, 1f - depth, out);
            } else {
                float t = Mathf.clamp((h - SEA) / (1f - SEA));
                float idx = t * 0.95f + detail * 0.05f;
                gradient(landColors, idx, out);

                // Dry lowlands break up the green continents with savanna and steppe.
                float moisture = Simplex.noise3d(VIS_SEED + 7, 4, 0.5f, 2.2f,
                    position.x, position.y, position.z);
                float dryness = smooth(0.62f, 0.38f, moisture) * (1f - smooth(0.42f, 0.68f, t));
                if (dryness > 0f) {
                    gradient(dryColors, idx, Tmp.c1);
                    out.lerp(Tmp.c1, dryness * 0.55f);
                }

                // Highlight the ridged mountain chains instead of leaving them as tiny specks.
                float rock = smooth(0.3f, 0.9f, ridges(position)) * t;
                if (rock > 0f) out.lerp(mountainColor, rock * 0.5f);
            }

            float ice = smooth(0.87f, 0.98f, Math.abs(position.y) + detail * 0.04f);
            if (ice > 0f) out.lerp(polarIce, ice);
            out.mul(0.95f + detail * 0.05f);
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
                // rare acid pools on dry terrain; provides acid for the acid processing chain
                if (floor == Blocks.sand || floor == Blocks.darksand) {
                    if (noise(x, y + 500, 12f, 0.8f) > 0.78f && rand.chance(0.6f)) {
                        floor = FREnvironment.tileAcid;
                    }
                }
            });
            genOres();
            Schematics.placeLaunchLoadout(width / 2, height / 2);
        }

        /** Ore distribution adapted from the vanilla Serpulo generator. */
        private void genOres() {
            // see CerberoGenerator.genOres(): graphite (-> silicon) and steel sedimentation
            // (-> living steel) must always be present or the sector cannot be finished
            Seq<Block> ores = Seq.with(Blocks.oreCopper, Blocks.oreLead, FREnvironment.oreGraphite,
                FREnvironment.steelSedimentation);
            float poles = Math.abs(sector.tile.v.y);
            float nmag = 0.5f;
            float scl = 1f;
            float addscl = 1.3f;

            if (Simplex.noise3d(seed, 2, 0.5f, scl, sector.tile.v.x, sector.tile.v.y, sector.tile.v.z) * nmag + poles > 0.25f * addscl) {
                ores.add(Blocks.oreCoal);
            }
            if (Simplex.noise3d(seed, 2, 0.5f, scl, sector.tile.v.x + 1, sector.tile.v.y, sector.tile.v.z) * nmag + poles > 0.5f * addscl) {
                ores.add(Blocks.oreTitanium);
            }
            if (Simplex.noise3d(seed, 2, 0.5f, scl, sector.tile.v.x + 2, sector.tile.v.y, sector.tile.v.z) * nmag + poles > 0.7f * addscl) {
                ores.add(Blocks.oreThorium);
            }
            if (rand.chance(0.3)) {
                ores.add(Blocks.oreScrap);
            }

            FloatSeq frequencies = new FloatSeq();
            for (int i = 0; i < ores.size; i++) {
                frequencies.add(rand.random(-0.1f, 0.01f) - i * 0.01f + poles * 0.04f);
            }

            pass((x, y) -> {
                if (!floor.asFloor().hasSurface()) return;

                int offsetX = x - 4, offsetY = y + 23;
                for (int i = ores.size - 1; i >= 0; i--) {
                    Block entry = ores.get(i);
                    float freq = frequencies.get(i);
                    if (Math.abs(0.5f - noise(offsetX, offsetY + i * 999, 2, 0.7f, (40 + i * 2))) > 0.22f + i * 0.01 &&
                        Math.abs(0.5f - noise(offsetX, offsetY - i * 999, 1, 1, (30 + i * 4))) > 0.37f + freq) {
                        ore = entry;
                        break;
                    }
                }
            });
        }
    }
}