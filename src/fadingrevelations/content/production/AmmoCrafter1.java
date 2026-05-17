package fadingrevelations.content.production;
import fadingrevelations.content.FRGates;
import fadingrevelations.content.FRLiquids;
import fadingrevelations.content.FRItems;
import mindustry.content.*;
import mindustry.type.*;
import mindustry.world.blocks.production.GenericCrafter;

import mindustry.world.Block;
public class AmmoCrafter1 {
    public static Block load() {
        return new GenericCrafter("ammo-crafter-1") {{
            localizedName = "Level 1 Ammocrafter";
            description = "Crafts copper ammo out of copper and coal.";
            size = 2; hasPower = true; hasItems = true; hasLiquids = false;
            craftTime = 50; craftEffect = Fx.pulverizeRed;
            consumeItems(ItemStack.with(Items.coal, 1, Items.copper, 2));
            outputItem = new ItemStack(FRItems.ammoLevel1, 2);
            requirements(Category.crafting, ItemStack.with(Items.copper, 150, Items.lead, 120));
        }};
    }
}
