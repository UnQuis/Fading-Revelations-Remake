package fadingrevelations.content.production;
import fadingrevelations.content.FRGates;
import fadingrevelations.content.FRLiquids;
import fadingrevelations.content.FRItems;
import mindustry.content.*;
import mindustry.type.*;
import mindustry.world.blocks.production.GenericCrafter;

import mindustry.world.Block;
public class CryogenicAlloyAssembler {
    public static Block block;
    public static Block load() {
        block = new GenericCrafter("cryogenic-alloy-assembler") {{
            localizedName = "Cryogenic Alloy Fusing Basin";
            description = "Amalgamates Igneous Alloy and Cryogenic Gel by shock-cooling it using Neutron Fluid. This basin is a part of the [cyan]Cryogenic Alloy assembly line.";
            size = 4; rotate = false;
            consumePower(10f); consumeLiquid(FRLiquids.neutronFluid, 0.4f);
            consumeItems(ItemStack.with(FRItems.igneousAlloy, 2, FRItems.cryogenicGel, 1, Items.graphite, 4));
            craftTime = 60;
            outputItem = new ItemStack(FRItems.cryogenicAlloy, 4);
            requirements(Category.crafting, ItemStack.with(Items.lead, 700, Items.graphite, 600, Items.metaglass, 500, Items.silicon, 500, Items.surgeAlloy, 260, FRItems.steelAlloy, 200));
        }};
        return block;
    }
}
