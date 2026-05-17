package fadingrevelations.content.production;
import fadingrevelations.content.FRGates;
import fadingrevelations.content.FRLiquids;
import fadingrevelations.content.FRItems;
import mindustry.content.*;
import mindustry.type.*;
import mindustry.world.blocks.production.GenericCrafter;

import mindustry.world.Block;
public class AcidVat {
    public static Block load() {
        return new GenericCrafter("acid-vat") {{
            localizedName = "Acid Vat";
            description = "Acidifies lead via chemical reaction.";
            size = 2; liquidCapacity = 40;
            consumePower(1.25f); consumeItem(Items.lead, 1);
            craftTime = 30;
            outputLiquid = new LiquidStack(FRLiquids.acid, 0.2f);
            requirements(Category.crafting, ItemStack.with(Items.copper, 60, Items.metaglass, 40, Items.titanium, 30));
        }};
    }
}
