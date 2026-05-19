package fadingrevelations.content.production;
import fadingrevelations.content.FRGates;
import fadingrevelations.content.FRLiquids;
import fadingrevelations.content.FRItems;
import mindustry.content.*;
import mindustry.type.*;
import mindustry.world.blocks.production.HeatCrafter;
import mindustry.gen.Sounds;

import mindustry.world.Block;
public class CyanogenFuser {
    public static Block block;
    public static Block load() {
        block = new HeatCrafter("cyanogen-fuser") {{
            localizedName = "Cyanogen Fuser";
            description = "Joins Arkycite and Graphite to make larger amounts of Cyanogen than a normal Cyanogen Synthesizer.";
            size = 4; heatRequirement = 7; liquidCapacity = 100;
            craftTime = 60;
            outputLiquid = new LiquidStack(Liquids.cyanogen, 0.075f);
            consumePower(2.5f); consumeItem(Items.graphite, 1); consumeLiquid(Liquids.arkycite, 0.656f);
            ambientSound = Sounds.loopExtract; ambientSoundVolume = 0.1f;
            requirements(Category.crafting, ItemStack.with(Items.carbide, 90, Items.silicon, 140, Items.beryllium, 160));
        }};
        return block;
    }
}
