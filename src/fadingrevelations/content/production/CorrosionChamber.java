package fadingrevelations.content.production;
import fadingrevelations.content.FRGates;
import fadingrevelations.content.FRLiquids;
import fadingrevelations.content.FRItems;
import mindustry.content.*;
import mindustry.type.*;
import mindustry.world.blocks.heat.HeatProducer;
import mindustry.gen.Sounds;

import mindustry.world.Block;
public class CorrosionChamber {
    public static Block block;
    public static Block load() {
        block = new HeatProducer("corrosion-chamber") {{
            localizedName = "Corrosion Chamber";
            description = "A better Oxidation Chamber that creates Oxide more efficiently and outputs more heat.";
            size = 4; hasLiquids = true; rotateDraw = false; researchCostMultiplier = 1.1f;
            craftTime = 120; regionRotated1 = 2; heatOutput = 7;
            outputItem = new ItemStack(Items.oxide, 2);
            consumePower(0.8f); consumeLiquid(Liquids.ozone, 0.0333f); consumeItem(Items.beryllium, 1);
            ambientSound = Sounds.loopExtract; ambientSoundVolume = 0.1f; liquidCapacity = 40;
            requirements(Category.crafting, ItemStack.with(Items.tungsten, 180, Items.graphite, 120, Items.silicon, 180, Items.beryllium, 200));
        }};
        return block;
    }
}
