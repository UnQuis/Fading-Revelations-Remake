package fadingrevelations.content.production;
import fadingrevelations.content.FRGates;
import fadingrevelations.content.FRLiquids;
import fadingrevelations.content.FRItems;
import mindustry.content.*;
import mindustry.type.*;
import mindustry.world.blocks.production.GenericCrafter;
import mindustry.gen.Sounds;

import mindustry.world.Block;
public class NeutronBlender {
    public static Block load() {
        return new GenericCrafter("neutron-blender") {{
            localizedName = "Neutron Blender";
            description = "Blends large quantities of cryofluid, oil and liquefied living steel into the highly potent neutron fluid coolant.";
            size = 3; hasItems = true; hasLiquids = true; itemCapacity = 30; liquidCapacity = 120;
            consumePower(2.2f);
            consumeLiquid(Liquids.oil, 0.2f);
            consumeLiquid(Liquids.cryofluid, 0.2f);
            consumeLiquid(FRLiquids.livingSteelLiquid, 0.2f);
            craftTime = 60;
            outputLiquid = new LiquidStack(FRLiquids.neutronFluid, 0.2f);
            ambientSound = Sounds.loopHum;
            ambientSoundVolume = 0.4f;
            requirements(Category.crafting, ItemStack.with(Items.metaglass, 250, Items.silicon, 180, Items.titanium, 220, FRItems.livingSteel, 140));
        }};
    }
}
