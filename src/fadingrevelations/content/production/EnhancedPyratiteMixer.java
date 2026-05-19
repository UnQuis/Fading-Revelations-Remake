package fadingrevelations.content.production;
import fadingrevelations.content.FRGates;
import fadingrevelations.content.FRLiquids;
import fadingrevelations.content.FRItems;
import mindustry.content.*;
import mindustry.type.*;
import mindustry.world.blocks.production.GenericCrafter;

import mindustry.world.Block;
public class EnhancedPyratiteMixer {
    public static Block block;
    public static Block load() {
        block = new GenericCrafter("enhanced-pyratite-mixer") {{
            localizedName = "Pyratite Forge";
            description = "A stronger pyratite mixer. Gets very hot so needs graphite for isolation.";
            size = 3; hasPower = true; hasItems = true; hasLiquids = false; health = 240; itemCapacity = 30;
            craftTime = 60; craftEffect = Fx.pulverizeRed; updateEffect = Fx.pulverizeRed;
            consumePower(0.6f); consumeItems(ItemStack.with(Items.coal, 3, Items.lead, 6, Items.sand, 6));
            outputItem = new ItemStack(Items.pyratite, 3);
            requirements(Category.crafting, ItemStack.with(Items.copper, 150, Items.lead, 120, Items.graphite, 60, Items.titanium, 30));
        }};
        return block;
    }
}
