package fadingrevelations.content.production;
import fadingrevelations.content.FRGates;
import fadingrevelations.content.FRLiquids;
import fadingrevelations.content.FRItems;
import mindustry.content.*;
import mindustry.type.*;
import mindustry.world.blocks.production.GenericCrafter;

import mindustry.world.Block;
public class BasicMultismelter {
    public static Block load() {
        return new GenericCrafter("basic-multismelter") {{
            localizedName = "Basic Multismelter";
            description = "Uses a combination of smelting and compression procedures to mass produce basic resources.";
            size = 3;
            consumePower(1.2f); consumeItems(ItemStack.with(Items.lead, 1, Items.sand, 3, Items.coal, 4));
            craftTime = 30;
            outputItems = ItemStack.with(Items.graphite, 1, Items.silicon, 1, Items.metaglass, 1);
            requirements(Category.crafting, ItemStack.with(Items.copper, 100, Items.lead, 80, Items.graphite, 50));
        }};
    }
}
