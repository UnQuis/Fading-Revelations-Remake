package fadingrevelations.content.production;
import fadingrevelations.content.FRGates;
import fadingrevelations.content.FRLiquids;
import fadingrevelations.content.FRItems;
import mindustry.content.*;
import mindustry.type.*;
import mindustry.world.blocks.production.GenericCrafter;
import mindustry.gen.Sounds;

import mindustry.world.Block;
public class AlloyCrafter {
    public static Block block;
    public static Block load() {
        block = new GenericCrafter("alloy-crafter") {{
            localizedName = "Igneous Alloy Smelter";
            description = "Smelts igneous alloy from surge alloy and titanium using slag. This smelter is a part of the [cyan]Cryogenic Alloy assembly line.";
            size = 2; rotate = false; itemCapacity = 200; liquidCapacity = 100;
            consumePower(5f); consumeLiquid(Liquids.slag, 0.8f);
            consumeItems(ItemStack.with(Items.surgeAlloy, 16, Items.titanium, 48));
            craftTime = 240;
            outputItem = new ItemStack(FRItems.igneousAlloy, 8);
            ambientSound = Sounds.loopSmelter; ambientSoundVolume = 0.3f;
            requirements(Category.crafting, ItemStack.with(Items.lead, 300, Items.graphite, 250, Items.silicon, 220, Items.titanium, 200, Items.surgeAlloy, 75));
        }};
        return block;
    }
}
