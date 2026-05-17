package fadingrevelations.content.production;
import fadingrevelations.content.FRGates;
import fadingrevelations.content.FRLiquids;
import fadingrevelations.content.FRItems;
import mindustry.content.*;
import mindustry.type.*;
import mindustry.world.blocks.production.GenericCrafter;
import mindustry.gen.Sounds;

import mindustry.world.Block;
public class CryogenicGelMixer {
    public static Block load() {
        return new GenericCrafter("cryogenic-gel-mixer") {{
            localizedName = "Cryogenic Gel Mixer";
            description = "Used to create Cryogenic Gel needed to craft Cryogenic Alloy. Part of the [cyan]Cryogenic Alloy Assembly line structure.";
            size = 2; rotate = false; itemCapacity = 30; liquidCapacity = 100;
            consumePower(3f); consumeLiquid(Liquids.cryofluid, 0.8f); consumeItem(Items.silicon, 12);
            craftTime = 120;
            outputItem = new ItemStack(FRItems.cryogenicGel, 2);
            ambientSound = Sounds.loopHum; ambientSoundVolume = 0.2f;
            requirements(Category.crafting, ItemStack.with(Items.lead, 250, Items.metaglass, 210, Items.graphite, 200, Items.silicon, 150, Items.surgeAlloy, 75));
        }};
    }
}
