package fadingrevelations.content.production;
import fadingrevelations.content.FRGates;
import fadingrevelations.content.FRLiquids;
import fadingrevelations.content.FRItems;
import mindustry.content.*;
import mindustry.type.*;
import mindustry.world.blocks.power.ConsumeGenerator;

import mindustry.world.Block;
public class SlagReactor {
    public static Block load() {
        return new ConsumeGenerator("slag-reactor") {{
            localizedName = "Slag Reactor";
            description = "A reactor that uses the heat of Slag to make power.";
            size = 3; health = 785; itemDuration = 60;
            powerProduction = 7f; hasLiquids = true; liquidCapacity = 100;
            consumeLiquid(Liquids.slag, 0.33333f);
            requirements(Category.power, ItemStack.with(Items.graphite, 50, Items.silicon, 40, Items.beryllium, 60, Items.tungsten, 20));
        }};
    }
}
