package fadingrevelations.content;
import mindustry.type.*;
import mindustry.content.*;

import mindustry.type.Category;
import mindustry.world.Block;
import mindustry.world.blocks.liquid.*;
import mindustry.world.blocks.production.Pump;

public class FRLiquidsBlocks {
    public static Block steelTank, steelPump, plastaniumConduit;

    public static void load() {
        steelTank = new LiquidRouter("steel-tank") {{
            localizedName = "Steel Tank";
            description = "A liquid tank made from living steel that has more capacity than a regular liquid tank but the same size.";
            size = 3; solid = true; health = 750; liquidCapacity = 2100;
            requirements(Category.liquid, ItemStack.with(Items.metaglass, 40, Items.titanium, 30, FRItems.livingSteel, 20));
        }};

        steelPump = new Pump("steel-pump") {{
            localizedName = "Steel Pump";
            description = "A pump made using living steel that uses power to pump great amounts of liquid.";
            hasPower = true; hasLiquids = true; liquidCapacity = 40; size = 3;
            consumePower(1.6f); pumpAmount = 0.25f; squareSprite = false;
            requirements(Category.liquid, ItemStack.with(Items.copper, 90, Items.metaglass, 100, Items.silicon, 35, FRItems.livingSteel, 60, Items.thorium, 45));
        }};

        plastaniumConduit = new Conduit("plastanium-conduit") {{
            localizedName = "Isolated Conduit";
            description = "A decently fast conduit made from plastanium.";
            health = 200; liquidPressure = 2;
            requirements(Category.liquid, ItemStack.with(Items.metaglass, 2, Items.thorium, 3, Items.plastanium, 2));
        }};
    }
}
