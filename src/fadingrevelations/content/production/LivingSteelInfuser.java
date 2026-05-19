package fadingrevelations.content.production;
import fadingrevelations.content.FRGates;
import fadingrevelations.content.FRLiquids;
import fadingrevelations.content.FRItems;
import mindustry.content.*;
import mindustry.type.*;
import mindustry.world.blocks.production.GenericCrafter;

import mindustry.world.Block;
public class LivingSteelInfuser {
    public static Block block;
    public static Block load() {
        block = new GenericCrafter("living-steel-infuser") {{
            localizedName = "Living Steel Infusion Chamber";
            description = "Infuses titanium with spore pods, creating living steel.";
            size = 2; health = 140; hasPower = true; hasItems = true; hasLiquids = false;
            craftTime = 50; craftEffect = Fx.smeltsmoke; updateEffect = Fx.smeltsmoke;
            consumePower(0.8f); consumeItems(ItemStack.with(Items.titanium, 2, Items.sporePod, 1));
            outputItem = new ItemStack(FRItems.livingSteel, 2);
            requirements(Category.crafting, ItemStack.with(Items.copper, 150, Items.lead, 120, Items.titanium, 50));
        }};
        return block;
    }
}
