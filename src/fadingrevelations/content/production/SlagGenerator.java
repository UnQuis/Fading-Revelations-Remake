package fadingrevelations.content.production;
import fadingrevelations.content.FRGates;
import fadingrevelations.content.FRLiquids;
import fadingrevelations.content.FRItems;
import mindustry.content.*;
import mindustry.type.*;
import mindustry.world.blocks.power.ConsumeGenerator;

import mindustry.world.Block;
public class SlagGenerator {
    public static Block block;
    public static Block load() {
        block = new ConsumeGenerator("slag-generator") {{
            localizedName = "Slag Generator";
            description = "A generator that uses the heat of Slag to make power.";
            size = 3; health = 785; itemDuration = 60;
            powerProduction = 6.8f; hasLiquids = true; liquidCapacity = 100;
            consumeLiquid(Liquids.slag, 0.33333f);
            requirements(Category.power, ItemStack.with(Items.copper, 120, Items.lead, 100, Items.graphite, 60, Items.metaglass, 50, Items.titanium, 40));
        }};
        return block;
    }
}
