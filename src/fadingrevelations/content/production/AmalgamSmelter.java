package fadingrevelations.content.production;
import fadingrevelations.content.FRGates;
import fadingrevelations.content.FRLiquids;
import fadingrevelations.content.FRItems;
import mindustry.content.*;
import mindustry.type.*;
import mindustry.world.blocks.production.GenericCrafter;

import mindustry.world.Block;
public class AmalgamSmelter {
    public static Block block;
    public static Block load() {
        block = new GenericCrafter("amalgam-smelter") {{
            localizedName = "Amalgam Smelter";
            description = "Using slag as a heat source, smelts copper, living steel, and surge alloy into steel amalgam.";
            size = 3; health = 240; itemCapacity = 30; hasPower = true; hasLiquids = true; hasItems = true;
            craftTime = 120; updateEffect = Fx.smeltsmoke;
            consumePower(7f); consumeLiquid(Liquids.slag, 0.2f);
            consumeItems(ItemStack.with(Items.copper, 2, FRItems.livingSteel, 3, Items.surgeAlloy, 2));
            outputItem = new ItemStack(FRItems.steelAlloy, 2);
            requirements(Category.crafting, ItemStack.with(Items.copper, 250, Items.lead, 200, FRItems.livingSteel, 100, Items.surgeAlloy, 50));
        }};
        return block;
    }
}
