package fadingrevelations.content.production;
import fadingrevelations.content.FRGates;
import fadingrevelations.content.FRLiquids;
import fadingrevelations.content.FRItems;
import mindustry.content.*;
import mindustry.type.*;
import mindustry.world.blocks.production.GenericCrafter;

import mindustry.world.Block;
public class SporeCrusher {
    public static Block block;
    public static Block load() {
        block = new GenericCrafter("spore-crusher") {{
            localizedName = "Spore Crusher";
            description = "Creates oil by crushing spore pods.";
            size = 3; hasPower = true; hasItems = true; hasLiquids = true;
            itemCapacity = 20; health = 720; liquidCapacity = 120;
            craftTime = 25; updateEffect = Fx.none;
            consumePower(1.6f); consumeItem(Items.sporePod, 2);
            outputLiquid = new LiquidStack(Liquids.oil, 0.6f);
            requirements(Category.crafting, ItemStack.with(Items.lead, 80, Items.silicon, 70));
        }};
        return block;
    }
}
