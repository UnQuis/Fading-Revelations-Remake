package fadingrevelations.content.production;
import fadingrevelations.content.FRGates;
import fadingrevelations.content.FRLiquids;
import fadingrevelations.content.FRItems;
import mindustry.content.*;
import mindustry.type.*;
import mindustry.world.blocks.production.GenericCrafter;

import mindustry.world.Block;
public class AmmoCrafter3 {
    public static Block block;
    public static Block load() {
        block = new GenericCrafter("ammo-crafter-3") {{
            localizedName = "Level 3 Ammocrafter";
            description = "Crafts explosive ammo out of blast compound and coal.";
            size = 2; hasPower = true; hasItems = true; hasLiquids = false;
            craftTime = 50; craftEffect = Fx.pulverizeRed;
            consumePower(0.2f); consumeItems(ItemStack.with(Items.coal, 5, Items.blastCompound, 2));
            outputItem = new ItemStack(FRItems.ammoLevel3, 2);
            requirements(Category.crafting, ItemStack.with(Items.copper, 150, Items.lead, 120, Items.titanium, 55));
        }};
        return block;
    }
}
