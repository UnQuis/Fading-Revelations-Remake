package fadingrevelations.content.production;
import fadingrevelations.content.FRGates;
import fadingrevelations.content.FRLiquids;
import fadingrevelations.content.FRItems;
import mindustry.content.*;
import mindustry.type.*;
import mindustry.world.blocks.production.GenericCrafter;

import mindustry.world.Block;
public class LivingSteelLiquifier {
    public static Block block;
    public static Block load() {
        block = new GenericCrafter("living-steel-liquifier") {{
            localizedName = "Living Steel Liquefaction Chamber";
            description = "Melts down living steel into a versatile fluid utilizing oil.";
            size = 2; hasPower = true; hasItems = true; health = 160;
            craftTime = 50; craftEffect = Fx.smeltsmoke; updateEffect = Fx.smeltsmoke;
            consumePower(0.8f); consumeItem(FRItems.livingSteel, 1);
            consumeLiquid(Liquids.oil, 0.3f);
            outputLiquid = new LiquidStack(FRLiquids.livingSteelLiquid, 0.2f);
            requirements(Category.crafting, ItemStack.with(Items.copper, 150, Items.lead, 120, Items.titanium, 50));
        }};
        return block;
    }
}
