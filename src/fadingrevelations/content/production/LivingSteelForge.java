package fadingrevelations.content.production;
import fadingrevelations.content.FRGates;
import fadingrevelations.content.FRLiquids;
import fadingrevelations.content.FRItems;
import mindustry.content.*;
import mindustry.type.*;
import mindustry.world.blocks.production.GenericCrafter;

import mindustry.world.Block;
public class LivingSteelForge {
    public static Block block;
    public static Block load() {
        block = new GenericCrafter("living-steel-forge") {{
            localizedName = "Living Steel Forge";
            description = "Fuses titanium with spore pods, creating living steel in large quantities. More efficient than the infuser.";
            size = 3; health = 285; hasPower = true; hasItems = true; hasLiquids = false;
            craftTime = 40; craftEffect = Fx.smeltsmoke; updateEffect = Fx.smeltsmoke;
            consumePower(1.4f); consumeItems(ItemStack.with(Items.titanium, 2, Items.sporePod, 1));
            outputItem = new ItemStack(FRItems.livingSteel, 3);
            requirements(Category.crafting, ItemStack.with(Items.copper, 460, Items.lead, 320, Items.titanium, 145));
        }};
        return block;
    }
}