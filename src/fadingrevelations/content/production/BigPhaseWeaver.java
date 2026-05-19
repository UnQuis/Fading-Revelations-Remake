package fadingrevelations.content.production;
import fadingrevelations.content.FRGates;
import fadingrevelations.content.FRLiquids;
import fadingrevelations.content.FRItems;
import mindustry.content.*;
import mindustry.type.*;
import mindustry.world.blocks.production.GenericCrafter;

import mindustry.world.Block;
public class BigPhaseWeaver {
    public static Block block;
    public static Block load() {
        block = new GenericCrafter("big-phase-weaver") {{
            localizedName = "Fabric Forge";
            description = "A better phase fabric forge that is more cost-efficient.";
            size = 3; hasPower = true; hasItems = true; itemCapacity = 60; hasLiquids = false;
            craftTime = 130; health = 300;
            consumePower(9.9f); consumeItems(ItemStack.with(Items.thorium, 6, Items.sand, 15));
            outputItem = new ItemStack(Items.phaseFabric, 4);
            requirements(Category.crafting, ItemStack.with(Items.lead, 320, Items.silicon, 320, Items.titanium, 140, Items.phaseFabric, 25));
        }};
        return block;
    }
}
