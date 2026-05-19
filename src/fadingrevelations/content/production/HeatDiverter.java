package fadingrevelations.content.production;
import fadingrevelations.content.FRGates;
import fadingrevelations.content.FRLiquids;
import fadingrevelations.content.FRItems;
import mindustry.content.*;
import mindustry.type.*;
import mindustry.world.blocks.heat.HeatConductor;

import mindustry.world.Block;
public class HeatDiverter {
    public static Block block;
    public static Block load() {
        block = new HeatConductor("heat-diverter") {{
            localizedName = "Heat Diverter";
            description = "A small block that is used to redirect heat to other blocks.";
            size = 1; researchCostMultiplier = 10; regionRotated1 = 1;
            requirements(Category.crafting, ItemStack.with(Items.tungsten, 5));
        }};
        return block;
    }
}
