package fadingrevelations.content.production;
import fadingrevelations.content.FRGates;
import fadingrevelations.content.FRLiquids;
import fadingrevelations.content.FRItems;
import mindustry.content.*;
import mindustry.type.*;
import mindustry.world.blocks.production.GenericCrafter;

import mindustry.world.Block;
public class LivingSteelHardeningForge {
    public static Block load() {
        return new GenericCrafter("living-steel-hardening-forge") {{
            localizedName = "Living Steel Hardening Forge";
            description = "Reinforces living steel via thorium infusion. More efficient than the hardening chamber.";
            size = 3; health = 285; hasPower = true; hasItems = true; hasLiquids = false;
            craftTime = 40; craftEffect = Fx.smeltsmoke; updateEffect = Fx.smeltsmoke;
            consumePower(1.4f); consumeItems(ItemStack.with(FRItems.livingSteel, 1, Items.thorium, 2));
            outputItem = new ItemStack(FRItems.livingSteelHard, 3);
            requirements(Category.crafting, ItemStack.with(Items.copper, 460, Items.lead, 320, Items.titanium, 145));
        }};
    }
}
