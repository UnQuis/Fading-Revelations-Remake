package fadingrevelations.content.production;
import fadingrevelations.content.FRGates;
import fadingrevelations.content.FRLiquids;
import fadingrevelations.content.FRItems;
import mindustry.content.*;
import mindustry.type.*;
import mindustry.world.blocks.production.HeatCrafter;
import mindustry.gen.Sounds;

import mindustry.world.Block;
public class AtmosphericExtractor {
    public static Block block;
    public static Block load() {
        block = new HeatCrafter("atmospheric-extractor") {{
            localizedName = "Atmospheric Extractor";
            description = "Extracts and concentrates Nitrogen out of the atmosphere. Works more efficiently than an Atmospheric Concentrator.";
            size = 4; hasLiquids = true; researchCostMultiplier = 1.1f; liquidCapacity = 50;
            consumePower(2.6f); heatRequirement = 8;
            craftTime = 60;
            outputLiquid = new LiquidStack(Liquids.nitrogen, 0.1f);
            ambientSound = Sounds.loopExtract; ambientSoundVolume = 0.09f;
            requirements(Category.crafting, ItemStack.with(Items.oxide, 100, Items.beryllium, 260, Items.silicon, 200));
        }};
        return block;
    }
}
