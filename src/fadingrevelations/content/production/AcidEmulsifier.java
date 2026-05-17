package fadingrevelations.content.production;
import fadingrevelations.content.FRGates;
import fadingrevelations.content.FRLiquids;
import fadingrevelations.content.FRItems;
import mindustry.content.*;
import mindustry.type.*;
import mindustry.world.blocks.production.GenericCrafter;

import mindustry.world.Block;
public class AcidEmulsifier {
    public static Block load() {
        return new GenericCrafter("acid-emulsifier") {{
            localizedName = "Acid Emulsifier";
            description = "Emulsifies acid into oil.";
            size = 2; liquidCapacity = 60; craftTime = 20;
            craftEffect = Fx.none; updateEffect = Fx.none;
            consumePower(1.5f); consumeLiquid(FRLiquids.acid, 0.4f);
            outputLiquid = new LiquidStack(Liquids.oil, 0.4f);
            requirements(Category.crafting, ItemStack.with(Items.copper, 80, Items.graphite, 40, Items.titanium, 60));
        }};
    }
}
