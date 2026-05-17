package fadingrevelations.content.production;
import fadingrevelations.content.FRGates;
import fadingrevelations.content.FRLiquids;
import fadingrevelations.content.FRItems;
import mindustry.content.*;
import mindustry.type.*;
import mindustry.world.blocks.production.GenericCrafter;
import mindustry.gen.Sounds;

import mindustry.world.Block;
public class InvertedPulverizer {
    public static Block load() {
        return new GenericCrafter("inverted-pulverizer") {{
            localizedName = "Inverted Pulverizer";
            description = "Pulverizes sand to scrap. Don't ask how but you will be wondering how useful that is!";
            size = 1; hasPower = true; hasLiquids = false; hasItems = true;
            craftTime = 50; updateEffect = Fx.pulverizeSmall; liquidCapacity = 50;
            consumePower(0.5f); consumeItem(Items.sand, 1);
            outputItem = new ItemStack(Items.scrap, 1);
            ambientSound = Sounds.loopGrind; ambientSoundVolume = 0.2f;
            requirements(Category.crafting, ItemStack.with(Items.copper, 30, Items.lead, 25));
        }};
    }
}
