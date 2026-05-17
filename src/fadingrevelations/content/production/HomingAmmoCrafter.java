package fadingrevelations.content.production;
import fadingrevelations.content.FRGates;
import fadingrevelations.content.FRLiquids;
import fadingrevelations.content.FRItems;
import mindustry.content.*;
import mindustry.type.*;
import mindustry.world.blocks.production.GenericCrafter;

import mindustry.world.Block;
public class HomingAmmoCrafter {
    public static Block load() {
        return new GenericCrafter("homing-ammo-crafter") {{
            localizedName = "Homing-Ammo Crafter";
            description = "Crafts homing ammo out of thorium and coal.";
            size = 2; hasPower = true; hasItems = true; hasLiquids = false;
            craftTime = 50; craftEffect = Fx.pulverizeRed;
            consumePower(0.2f); consumeItems(ItemStack.with(Items.thorium, 2, Items.coal, 2));
            outputItem = new ItemStack(FRItems.homingAmmo, 2);
            requirements(Category.crafting, ItemStack.with(Items.copper, 150, Items.lead, 120, Items.titanium, 55));
        }};
    }
}
