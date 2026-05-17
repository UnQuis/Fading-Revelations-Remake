package fadingrevelations.content.production;
import fadingrevelations.content.FRGates;
import fadingrevelations.content.FRLiquids;
import fadingrevelations.content.FRItems;
import mindustry.content.*;
import mindustry.type.*;
import mindustry.world.blocks.production.GenericCrafter;

import mindustry.world.Block;
public class AdvancedCoalCentrifuge {
    public static Block load() {
        return new GenericCrafter("advanced-coal-centrifuge") {{
            localizedName = "Coal Condenser";
            description = "Condenses oil into coal in a highly efficient process.";
            size = 2; health = 440; hasPower = true; hasLiquids = true; hasItems = true;
            craftTime = 90;
            consumePower(1f); consumeLiquid(Liquids.oil, 0.15f);
            outputItem = new ItemStack(Items.coal, 6);
            requirements(Category.crafting, ItemStack.with(Items.lead, 200, Items.graphite, 75, Items.silicon, 75, Items.titanium, 200));
        }};
    }
}
