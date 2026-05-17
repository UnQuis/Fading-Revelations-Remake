package fadingrevelations.content.production;
import fadingrevelations.content.FRGates;
import fadingrevelations.content.FRLiquids;
import fadingrevelations.content.FRItems;
import mindustry.content.*;
import mindustry.type.*;
import mindustry.world.blocks.production.GenericCrafter;

import mindustry.world.Block;
public class AdvancedCryofluidMixer {
    public static Block load() {
        return new GenericCrafter("advanced-cryofluid-mixer") {{
            localizedName = "Advanced Cryofluid-Mixer";
            description = "Mixes large quantities of water with fine titanium powder to produce cryofluid.";
            size = 2; health = 240; itemCapacity = 30; liquidCapacity = 70;
            hasPower = true; hasLiquids = true; hasItems = true; updateEffect = Fx.smeltsmoke;
            craftTime = 60;
            consumePower(1.7f); consumeLiquid(Liquids.water, 0.4f); consumeItem(Items.titanium, 1);
            outputLiquid = new LiquidStack(Liquids.cryofluid, 0.4f);
            requirements(Category.crafting, ItemStack.with(Items.graphite, 100, Items.silicon, 75, Items.titanium, 100, Items.thorium, 200));
        }};
    }
}
