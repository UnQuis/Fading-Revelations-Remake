package fadingrevelations.content.production;
import fadingrevelations.content.FRGates;
import fadingrevelations.content.FRLiquids;
import fadingrevelations.content.FRItems;
import mindustry.content.*;
import mindustry.type.*;
import mindustry.world.blocks.production.GenericCrafter;
import mindustry.gen.Sounds;

import mindustry.world.Block;
public class EsterificationChamber {
    public static Block block;
    public static Block load() {
        block = new GenericCrafter("esterification-chamber") {{
            localizedName = "Esterification Chamber";
            description = "A better Electrolyzer that works more efficiently.";
            size = 4; researchCostMultiplier = 1.2f; craftTime = 5;
            rotate = true; invertFlip = true; liquidCapacity = 75;
            consumePower(1.6f); consumeLiquid(Liquids.water, 0.1667f);
            outputLiquids = LiquidStack.with(Liquids.ozone, 0.07f, Liquids.hydrogen, 0.1f);
            liquidOutputDirections = new int[]{1, 3};
            ambientSound = Sounds.loopElectricHum; ambientSoundVolume = 0.1f;
            regionRotated1 = 3;
            requirements(Category.crafting, ItemStack.with(Items.silicon, 90, Items.graphite, 70, Items.beryllium, 165, Items.tungsten, 110));
        }};
        return block;
    }
}
