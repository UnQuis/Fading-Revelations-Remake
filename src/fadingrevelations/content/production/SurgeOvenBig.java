package fadingrevelations.content.production;
import fadingrevelations.content.FRGates;
import fadingrevelations.content.FRLiquids;
import fadingrevelations.content.FRItems;
import mindustry.content.*;
import mindustry.type.*;
import mindustry.world.blocks.production.GenericCrafter;

import mindustry.world.Block;
public class SurgeOvenBig {
    public static Block block;
    public static Block load() {
        block = new GenericCrafter("surge-oven-big") {{
            localizedName = "Alloy Forge";
            description = "A better surge alloy forge that is more cost-efficient.";
            size = 4; hasPower = true; hasItems = true; hasLiquids = false; craftTime = 90;
            updateEffect = Fx.hitMeltdown;
            consumePower(6f); consumeItems(ItemStack.with(Items.copper, 3, Items.lead, 4, Items.silicon, 3, Items.titanium, 2));
            outputItem = new ItemStack(Items.surgeAlloy, 2);
            requirements(Category.crafting, ItemStack.with(Items.lead, 160, Items.silicon, 160, Items.thorium, 140, Items.surgeAlloy, 25));
        }};
        return block;
    }
}
