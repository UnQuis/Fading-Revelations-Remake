package fadingrevelations.content.production;
import fadingrevelations.content.FRGates;
import fadingrevelations.content.FRLiquids;
import fadingrevelations.content.FRItems;
import mindustry.content.*;
import mindustry.type.*;
import mindustry.world.blocks.production.GenericCrafter;

import mindustry.world.Block;
public class LivingSteelHardener {
    public static Block load() {
        return new GenericCrafter("living-steel-hardener") {{
            localizedName = "Living Steel Hardening Chamber";
            description = "Reinforces living steel via thorium infusion.";
            size = 2; hasPower = true; hasItems = true; health = 160; hasLiquids = false;
            craftTime = 50; craftEffect = Fx.smeltsmoke; updateEffect = Fx.smeltsmoke;
            consumePower(0.8f); consumeItems(ItemStack.with(FRItems.livingSteel, 1, Items.thorium, 2));
            outputItem = new ItemStack(FRItems.livingSteelHard, 2);
            requirements(Category.crafting, ItemStack.with(Items.copper, 150, Items.lead, 120, Items.titanium, 50));
        }};
    }
}
