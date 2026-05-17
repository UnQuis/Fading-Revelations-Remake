package fadingrevelations.content.production;
import fadingrevelations.content.FRGates;
import fadingrevelations.content.FRLiquids;
import fadingrevelations.content.FRItems;
import mindustry.content.*;
import mindustry.type.*;
import mindustry.world.blocks.production.GenericCrafter;

import mindustry.world.Block;
public class SteamCondenser {
    public static Block load() {
        return new GenericCrafter("steam-condenser") {{
            localizedName = "Steam Condenser";
            description = "Pulls water vapor from the atmosphere, condensing it into steam.";
            size = 2; scaledHealth = 60;
            craftTime = 300;
            outputLiquid = new LiquidStack(FRLiquids.steam, 0.0125f);
            requirements(Category.crafting, ItemStack.with(Items.copper, 60, Items.lead, 35));
        }};
    }
}
