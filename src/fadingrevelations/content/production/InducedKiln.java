package fadingrevelations.content.production;
import fadingrevelations.content.FRGates;
import fadingrevelations.content.FRLiquids;
import fadingrevelations.content.FRItems;
import mindustry.content.*;
import mindustry.type.*;
import mindustry.world.blocks.production.GenericCrafter;
import mindustry.gen.Sounds;

import mindustry.world.Block;
public class InducedKiln {
    public static Block block;
    public static Block load() {
        block = new GenericCrafter("induced-kiln") {{
            localizedName = "Induced Kiln";
            description = "A better kiln that is a lot more efficient.";
            size = 3; hasPower = true; hasItems = true; health = 320; hasLiquids = false;
            craftTime = 60;
            consumePower(1.5f); consumeItems(ItemStack.with(Items.lead, 3, Items.sand, 4));
            outputItem = new ItemStack(Items.metaglass, 5);
            ambientSound = Sounds.loopSmelter; ambientSoundVolume = 0.35f;
            requirements(Category.crafting, ItemStack.with(Items.copper, 70, Items.lead, 60, Items.graphite, 20, Items.metaglass, 15));
        }};
        return block;
    }
}
