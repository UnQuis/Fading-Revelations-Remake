package fadingrevelations.content.production;
import fadingrevelations.content.FRGates;
import fadingrevelations.content.FRLiquids;
import fadingrevelations.content.FRItems;
import mindustry.content.*;
import mindustry.type.*;
import mindustry.world.blocks.production.GenericCrafter;

import mindustry.world.Block;
public class HealAmmoCrafter {
    public static Block block;
    public static Block load() {
        block = new GenericCrafter("heal-ammo-crafter") {{
            localizedName = "Heal-Ammo Crafter";
            description = "Crafts healing ammunition using spore pods and coal. Used in turrets, this ammo can heal allied blocks.";
            size = 2; hasPower = true; hasItems = true; hasLiquids = false;
            craftTime = 50; craftEffect = Fx.pulverizeRed;
            consumePower(0.2f); consumeItems(ItemStack.with(Items.sporePod, 2, Items.coal, 2));
            outputItem = new ItemStack(FRItems.healAmmo, 2);
            requirements(Category.crafting, ItemStack.with(Items.copper, 150, Items.lead, 120, Items.titanium, 55));
        }};
        return block;
    }
}
