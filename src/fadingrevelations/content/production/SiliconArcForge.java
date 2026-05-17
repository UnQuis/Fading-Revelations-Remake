package fadingrevelations.content.production;
import fadingrevelations.content.FRGates;
import fadingrevelations.content.FRLiquids;
import fadingrevelations.content.FRItems;
import mindustry.content.*;
import mindustry.type.*;
import mindustry.world.blocks.production.GenericCrafter;
import mindustry.gen.Sounds;

import mindustry.world.Block;
public class SiliconArcForge {
    public static Block load() {
        return new GenericCrafter("silicon-arc-forge") {{
            localizedName = "Silicon Arc Forge";
            description = "A better version of the silicon arc furnace that creates silicon more efficiently.";
            size = 4; hasPower = true; hasLiquids = false; itemCapacity = 40;
            craftTime = 60; craftEffect = Fx.none; fogRadius = 5;
            outputItem = new ItemStack(Items.silicon, 6);
            consumePower(7f); consumeItems(ItemStack.with(Items.graphite, 1, Items.sand, 4));
            ambientSound = Sounds.loopSmelter; ambientSoundVolume = 0.14f;
            requirements(Category.crafting, ItemStack.with(Items.beryllium, 140, Items.graphite, 150));
        }};
    }
}
