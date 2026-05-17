package fadingrevelations.content.production;
import fadingrevelations.content.FRGates;
import fadingrevelations.content.FRLiquids;
import fadingrevelations.content.FRItems;
import mindustry.content.*;
import mindustry.type.*;
import mindustry.world.blocks.production.GenericCrafter;

import mindustry.world.Block;
public class LivingSteelLiquifyingForge {
    public static Block load() {
        return new GenericCrafter("living-steel-liquifying-forge") {{
            localizedName = "Living Steel Liquefaction Forge";
            description = "Melts down living steel into a versatile fluid utilizing oil. More efficient than the liquefaction chamber.";
            size = 3; health = 285; hasPower = true; hasItems = true;
            craftTime = 50; craftEffect = Fx.smeltsmoke; updateEffect = Fx.smeltsmoke;
            consumePower(1.4f); consumeItem(FRItems.livingSteel, 2);
            consumeLiquid(Liquids.oil, 0.6f);
            outputLiquid = new LiquidStack(FRLiquids.livingSteelLiquid, 0.4f);
            requirements(Category.crafting, ItemStack.with(Items.copper, 460, Items.lead, 320, Items.titanium, 145, FRItems.livingSteel, 100));
        }};
    }
}
