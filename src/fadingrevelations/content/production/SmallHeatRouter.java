package fadingrevelations.content.production;
import fadingrevelations.content.FRGates;
import fadingrevelations.content.FRLiquids;
import fadingrevelations.content.FRItems;
import mindustry.content.*;
import mindustry.type.*;
import mindustry.world.blocks.heat.HeatConductor;

import mindustry.world.Block;
public class SmallHeatRouter {
    public static Block block;
    public static Block load() {
        block = new HeatConductor("small-heat-router") {{
            localizedName = "Small Heat Router";
            description = "A smaller Heat Router that takes up less space!";
            size = 1; regionRotated1 = 1; splitHeat = true; researchCostMultiplier = 10;
            requirements(Category.crafting, ItemStack.with(Items.tungsten, 3, Items.graphite, 3));
        }};
        return block;
    }
}
