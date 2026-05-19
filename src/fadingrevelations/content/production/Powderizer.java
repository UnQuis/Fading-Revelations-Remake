package fadingrevelations.content.production;
import fadingrevelations.content.FRGates;
import fadingrevelations.content.FRLiquids;
import fadingrevelations.content.FRItems;
import mindustry.content.*;
import mindustry.type.*;
import mindustry.world.blocks.production.GenericCrafter;
import mindustry.gen.Sounds;

import mindustry.world.Block;
public class Powderizer {
    public static Block block;
    public static Block load() {
        block = new GenericCrafter("powderizer") {{
            localizedName = "Powderizer";
            description = "A better and bigger version of the pulverizer.";
            size = 2; craftTime = 30; craftEffect = Fx.pulverize;
            outputItem = new ItemStack(Items.sand, 2);
            consumePower(0.75f); consumeItem(Items.scrap, 2);
            ambientSound = Sounds.loopGrind; ambientSoundVolume = 0.4f;
            requirements(Category.crafting, ItemStack.with(Items.copper, 70, Items.lead, 60));
        }};
        return block;
    }
}
