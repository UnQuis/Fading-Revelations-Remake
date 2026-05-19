package fadingrevelations.content.production;
import fadingrevelations.content.FRGates;
import fadingrevelations.content.FRLiquids;
import fadingrevelations.content.FRItems;
import mindustry.content.*;
import mindustry.type.*;
import mindustry.world.blocks.production.GenericCrafter;

import mindustry.world.Block;
public class NanoAmmoCrafter {
    public static Block block;
    public static Block load() {
        block = new GenericCrafter("nano-ammo-crafter") {{
            localizedName = "Nanobot Ammo Crafter";
            description = "Crafts nanobot ammo out of living steel and coal.";
            size = 2; hasPower = true; hasItems = true; hasLiquids = false;
            craftTime = 50; craftEffect = Fx.pulverizeRed;
            consumePower(0.2f); consumeItems(ItemStack.with(Items.coal, 5, FRItems.livingSteel, 2));
            outputItem = new ItemStack(FRItems.nanoAmmo, 2);
            requirements(Category.crafting, ItemStack.with(Items.copper, 150, Items.lead, 120, Items.graphite, 65, Items.titanium, 55));
        }};
        return block;
    }
}
