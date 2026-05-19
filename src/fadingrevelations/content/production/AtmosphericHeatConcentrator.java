package fadingrevelations.content.production;
import fadingrevelations.content.FRGates;
import fadingrevelations.content.FRLiquids;
import fadingrevelations.content.FRItems;
import mindustry.content.*;
import mindustry.type.*;
import mindustry.world.blocks.heat.HeatProducer;
import mindustry.gen.Sounds;

import mindustry.world.Block;
public class AtmosphericHeatConcentrator {
    public static Block block;
    public static Block load() {
        block = new HeatProducer("atmospheric-heat-concentrator") {{
            localizedName = "Advanced Heating Element";
            description = "An improved version of the Electric Heater. Creates more heat.";
            size = 2; rotateDraw = false; researchCostMultiplier = 4;
            heatOutput = 5; regionRotated1 = 1;
            consumePower(2f);
            ambientSound = Sounds.loopExtract; ambientSoundVolume = 0.09f;
            requirements(Category.crafting, ItemStack.with(Items.beryllium, 70, Items.tungsten, 50, Items.oxide, 40));
        }};
        return block;
    }
}
