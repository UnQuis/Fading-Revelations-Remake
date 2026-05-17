package fadingrevelations.content.production;
import fadingrevelations.content.FRGates;
import fadingrevelations.content.FRLiquids;
import fadingrevelations.content.FRItems;
import mindustry.content.*;
import mindustry.type.*;
import mindustry.world.blocks.production.GenericCrafter;

import mindustry.world.Block;
public class Dissolver {
    public static Block load() {
        return new GenericCrafter("dissolver") {{
            localizedName = "Dissolver";
            description = "A larger and more powerful Melter.";
            size = 2; itemCapacity = 20; liquidCapacity = 25;
            craftTime = 10;
            consumePower(1.2f); consumeItem(Items.scrap, 1);
            outputLiquid = new LiquidStack(Liquids.slag, 0.25f);
            requirements(Category.crafting, ItemStack.with(Items.copper, 60, Items.lead, 70, Items.graphite, 50));
        }};
    }
}
