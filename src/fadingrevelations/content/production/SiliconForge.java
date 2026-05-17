package fadingrevelations.content.production;
import fadingrevelations.content.FRGates;
import fadingrevelations.content.FRLiquids;
import fadingrevelations.content.FRItems;
import mindustry.content.*;
import mindustry.type.*;
import mindustry.world.blocks.production.GenericCrafter;

import mindustry.world.Block;
public class SiliconForge {
    public static Block load() {
        return new GenericCrafter("silicon-forge") {{
            localizedName = "Silicon Forge";
            description = "A better silicon smelter that is more cost-efficient.";
            size = 3; hasPower = true; hasItems = true; hasLiquids = false;
            craftTime = 60; itemCapacity = 30; health = 320;
            consumePower(1f); consumeItems(ItemStack.with(Items.coal, 3, Items.sand, 6));
            outputItem = new ItemStack(Items.silicon, 3);
            requirements(Category.crafting, ItemStack.with(Items.copper, 90, Items.lead, 75, Items.graphite, 25, Items.silicon, 15));
        }};
    }
}
