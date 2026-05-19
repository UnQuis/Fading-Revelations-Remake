package fadingrevelations.content.production;
import fadingrevelations.content.FRGates;
import fadingrevelations.content.FRLiquids;
import fadingrevelations.content.FRItems;
import mindustry.content.*;
import mindustry.type.*;
import mindustry.world.blocks.production.GenericCrafter;

import mindustry.world.Block;
public class AmalgamForge {
    public static Block block;
    public static Block load() {
        block = new GenericCrafter("amalgam-forge") {{
            localizedName = "Amalgam Forge";
            description = "Using slag as a heat source, smelts copper, living steel, and surge alloy into larger quantities of steel amalgam than the smelter.";
            size = 4; health = 240; itemCapacity = 30; hasPower = true; hasLiquids = true; hasItems = true;
            craftTime = 75; updateEffect = Fx.smeltsmoke;
            consumePower(7.4f); consumeLiquid(Liquids.slag, 0.2f);
            consumeItems(ItemStack.with(Items.copper, 2, FRItems.livingSteel, 3, Items.surgeAlloy, 2));
            outputItem = new ItemStack(FRItems.steelAlloy, 2);
            requirements(Category.crafting, ItemStack.with(Items.copper, 360, Items.lead, 300, FRItems.livingSteel, 150, Items.surgeAlloy, 75));
        }};
        return block;
    }
}
