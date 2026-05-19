package fadingrevelations.content.production;
import fadingrevelations.content.FRGates;
import fadingrevelations.content.FRLiquids;
import fadingrevelations.content.FRItems;
import mindustry.content.*;
import mindustry.type.*;
import mindustry.world.blocks.production.GenericCrafter;

import mindustry.world.Block;
public class AmmoCrafter2 {
    public static Block block;
    public static Block load() {
        block = new GenericCrafter("ammo-crafter-2") {{
            localizedName = "Level 2 Ammocrafter";
            description = "Crafts titanium ammo out of titanium and coal.";
            size = 2; hasPower = true; hasItems = true; hasLiquids = false;
            craftTime = 50; craftEffect = Fx.pulverizeRed;
            consumePower(0.15f); consumeItems(ItemStack.with(Items.coal, 3, Items.titanium, 2));
            outputItem = new ItemStack(FRItems.ammoLevel2, 1);
            requirements(Category.crafting, ItemStack.with(Items.copper, 150, Items.lead, 120, Items.graphite, 55));
        }};
        return block;
    }
}
