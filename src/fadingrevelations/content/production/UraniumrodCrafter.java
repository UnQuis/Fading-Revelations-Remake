package fadingrevelations.content.production;
import fadingrevelations.content.FRGates;
import fadingrevelations.content.FRLiquids;
import fadingrevelations.content.FRItems;
import mindustry.content.*;
import mindustry.type.*;
import mindustry.world.blocks.production.GenericCrafter;

import mindustry.world.Block;
public class UraniumrodCrafter {
    public static Block load() {
        return new GenericCrafter("uraniumrod-crafter") {{
            localizedName = "Fuel Rod Crafter";
            description = "Layers a plastanium and lead core with thorium, creating fuel rods.";
            size = 2; hasPower = true; hasItems = true; hasLiquids = false;
            craftTime = 300; craftEffect = Fx.smeltsmoke; updateEffect = Fx.pulverizeRed;
            consumePower(1.1f);
            consumeItems(ItemStack.with(Items.thorium, 1, Items.plastanium, 2, Items.lead, 3, Items.phaseFabric, 1));
            outputItem = new ItemStack(FRItems.fuelRod, 3);
            requirements(Category.crafting, ItemStack.with(Items.copper, 200, Items.lead, 150, Items.titanium, 60, Items.thorium, 50));
        }};
    }
}
