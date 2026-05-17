package fadingrevelations.content.production;
import fadingrevelations.content.FRGates;
import fadingrevelations.content.FRLiquids;
import fadingrevelations.content.FRItems;
import mindustry.content.*;
import mindustry.type.*;
import mindustry.world.blocks.production.HeatCrafter;
import mindustry.gen.Sounds;

import mindustry.world.Block;
public class SurgeMelter {
    public static Block load() {
        return new HeatCrafter("surge-melter") {{
            localizedName = "Surge Melter";
            description = "A better version of the surge crucible that creates surge alloy more efficiently.";
            size = 4; itemCapacity = 30; heatRequirement = 11; hasLiquids = true; hasPower = true; hasItems = true;
            craftTime = 90; liquidCapacity = 600;
            outputItem = new ItemStack(Items.surgeAlloy, 1);
            consumePower(2.5f); consumeItem(Items.silicon, 3); consumeLiquid(Liquids.slag, 0.667f);
            ambientSound = Sounds.loopSmelter; ambientSoundVolume = 1f;
            requirements(Category.crafting, ItemStack.with(Items.silicon, 160, Items.graphite, 160, Items.tungsten, 110, Items.oxide, 100));
        }};
    }
}
