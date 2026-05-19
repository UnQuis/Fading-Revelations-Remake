package fadingrevelations.content.production;
import fadingrevelations.content.FRGates;
import fadingrevelations.content.FRLiquids;
import fadingrevelations.content.FRItems;
import mindustry.content.*;
import mindustry.type.*;
import mindustry.world.blocks.production.AttributeCrafter;
import mindustry.world.meta.Attribute;

import mindustry.world.Block;
public class Greenhouse {
    public static Block block;
    public static Block load() {
        block = new AttributeCrafter("greenhouse") {{
            localizedName = "Greenhouse";
            description = "A better cultivator that creates a lot more spore pods.";
            size = 3; hasPower = true; hasItems = true; hasLiquids = false;
            attribute = Attribute.spores; maxBoost = 1; minEfficiency = 1;
            baseEfficiency = 1; boostScale = 0.5556f; envRequired = 8;
            craftTime = 50; craftEffect = Fx.smeltsmoke; updateEffect = Fx.pulverize;
            consumePower(1.5f); consumeLiquid(Liquids.water, 0.5f);
            outputItem = new ItemStack(Items.sporePod, 2);
            requirements(Category.production, ItemStack.with(Items.copper, 75, Items.lead, 75, Items.silicon, 30));
        }};
        return block;
    }
}
