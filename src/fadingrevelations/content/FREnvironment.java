package fadingrevelations.content;
import mindustry.world.Block;
import mindustry.world.blocks.environment.*;
import mindustry.world.meta.Attribute;
import mindustry.content.*;
import mindustry.graphics.CacheLayer;
import arc.graphics.Color;

public class FREnvironment {
    public static Block tileAcid, deepTileAcid, steelSedimentation, oreGraphite,
            gnarledTree, charredTree, envLivingSteelWall, envPetrifiedSteelWall;

    public static void load() {
        tileAcid = new Floor("tile-acid") {{
            localizedName = "Acid";
            description = "A puddle of acid liquid. Beware!";
            status = FRStatus.acidicBurn; statusDuration = 300; speedMultiplier = 0.7f;
            variants = 0; liquidDrop = FRLiquids.acid; isLiquid = true; cacheLayer = CacheLayer.water;
            emitLight = true; lightRadius = 5; lightColor = Color.valueOf("9ed17161");
            albedo = 0.9f; supportsOverlay = true;
        }};

        deepTileAcid = new Floor("deep-tile-acid") {{
            localizedName = "Deep Acid";
            description = "A puddle of acid liquid. Beware!";
            status = FRStatus.acidicBurn; statusDuration = 300; speedMultiplier = 0.4f;
            variants = 0; liquidDrop = FRLiquids.acid; liquidMultiplier = 1.3f; isLiquid = true;
            cacheLayer = CacheLayer.water;
            emitLight = true; lightRadius = 5; lightColor = Color.valueOf("9ed17161");
            albedo = 0.9f; shallow = false; supportsOverlay = true; placeableOn = false;
        }};

        steelSedimentation = new OreBlock("steel-sedimentation", FRItems.livingSteel) {{
            localizedName = "Steel Sedimentation";
            description = "A sedimentation of living steel. Can be mined but beware as it damages units walking over it and slows them.";
            hasColor = true; mapColor = Color.valueOf("8c0291"); variants = 6;
        }};

        oreGraphite = new OreBlock("ore-graphite", mindustry.content.Items.graphite) {{
            localizedName = "Graphite";
            hasColor = true; mapColor = Color.valueOf("7a89bc"); variants = 3;
        }};

        gnarledTree = new TreeBlock("gnarled-tree") {{
            localizedName = "Gnarled Tree";
            description = "A gnarly tree that has lost all of its leafs but the wood is still standing.";
        }};

        charredTree = new TreeBlock("charred-tree") {{
            localizedName = "Charred Tree";
            description = "A tree that has endured high heat and lots of fire. The wood is charred.";
        }};

        envLivingSteelWall = new StaticWall("env-living-steel-wall") {{
            localizedName = "Living Steel Wall";
            attributes.set(Attribute.spores, 0.3f);
        }};

        envPetrifiedSteelWall = new StaticWall("env-petrified-steel-wall") {{
            localizedName = "Petrified Steel Wall";
            attributes.set(Attribute.spores, 0.3f);
        }};
    }
}
