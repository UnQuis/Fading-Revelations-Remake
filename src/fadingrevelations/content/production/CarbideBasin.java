package fadingrevelations.content.production;
import fadingrevelations.content.FRGates;
import fadingrevelations.content.FRLiquids;
import fadingrevelations.content.FRItems;
import mindustry.content.*;
import mindustry.type.*;
import mindustry.world.blocks.production.HeatCrafter;
import mindustry.gen.Sounds;

import mindustry.world.Block;
public class CarbideBasin {
    public static Block block;
    public static Block load() {
        block = new HeatCrafter("carbide-basin") {{
            localizedName = "Carbide Basin";
            description = "A better Carbide Crucible that works more efficiently.";
            size = 4; itemCapacity = 30; hasPower = true; hasItems = true;
            craftTime = 90; craftEffect = Fx.none; heatRequirement = 12;
            outputItem = new ItemStack(Items.carbide, 1);
            consumePower(2.6f); consumeItems(ItemStack.with(Items.tungsten, 2, Items.graphite, 3));
            ambientSound = Sounds.loopSmelter; ambientSoundVolume = 0.11f;
            requirements(Category.crafting, ItemStack.with(Items.tungsten, 160, Items.thorium, 190, Items.oxide, 90));
        }};
        return block;
    }
}
