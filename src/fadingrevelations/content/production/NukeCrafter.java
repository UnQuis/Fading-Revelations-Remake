package fadingrevelations.content.production;
import fadingrevelations.content.FRGates;
import fadingrevelations.content.FRLiquids;
import fadingrevelations.content.FRItems;
import mindustry.content.*;
import mindustry.type.*;
import mindustry.world.blocks.production.GenericCrafter;

import mindustry.world.Block;
public class NukeCrafter {
    public static Block load() {
        return new GenericCrafter("nuke-crafter") {{
            localizedName = "Nuke Crafter";
            description = "Crafts nuclear warheads using fuel rods and graphite.";
            size = 2; hasPower = true; hasItems = true; hasLiquids = false; itemCapacity = 60;
            craftTime = 600;
            consumePower(6f);
            consumeItems(ItemStack.with(FRItems.fuelRod, 10, Items.graphite, 20, Items.thorium, 10, Items.blastCompound, 10));
            outputItem = new ItemStack(FRItems.nuke, 1);
            requirements(Category.crafting, ItemStack.with(Items.copper, 1500, Items.lead, 1200, Items.graphite, 1000, Items.surgeAlloy, 600));
        }};
    }
}
