package fadingrevelations.content;
import mindustry.type.*;
import mindustry.content.*;

import mindustry.type.Category;
import mindustry.world.Block;
import mindustry.world.blocks.liquid.*;
import mindustry.world.blocks.production.Pump;
import fadingrevelations.worlds.blocks.liquid.LiquidUnloader;

public class FRLiquidsBlocks {
    public static Block steelTank, steelPump, plastaniumConduit,
            kineticConduit, kineticLiquidRouter, kineticLiquidBridge, kineticLiquidJunction,
            bioLiquidContainer, liquidUnloader;

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

        kineticConduit = new Conduit("kinetic-conduit") {{
            localizedName = "Kinetic Conduit";
            description = "A high-pressure conduit reinforced with surge alloy for rapid liquid transport.";
            placeableLiquid = true;
            health = 300; liquidPressure = 4;
            requirements(Category.liquid, ItemStack.with(Items.metaglass, 2, Items.surgeAlloy, 2, Items.silicon, 1));
        }};

        kineticLiquidRouter = new LiquidRouter("kinetic-liquid-router") {{
            localizedName = "Kinetic Liquid Router";
            description = "Distributes liquids to up to 3 output directions equally. Built with surge alloy for high throughput.";
            placeableLiquid = true;
            size = 1; health = 200; liquidCapacity = 60;
            requirements(Category.liquid, ItemStack.with(Items.metaglass, 5, Items.surgeAlloy, 4, Items.silicon, 3));
        }};

        kineticLiquidBridge = new LiquidBridge("kinetic-liquid-bridge") {{
            localizedName = "Kinetic Liquid Bridge";
            description = "Transports liquids over terrain or buildings. Built with surge alloy for fast transfer.";
            placeableLiquid = true;
            health = 250; range = 8; liquidCapacity = 40;
            consumePower(0.3f);
            requirements(Category.liquid, ItemStack.with(Items.metaglass, 10, Items.surgeAlloy, 6, Items.silicon, 5));
        }};

        kineticLiquidJunction = new Conduit("kinetic-liquid-junction") {{
            localizedName = "Kinetic Liquid Junction";
            description = "Allows two crossing liquid conduits to pass through each other without mixing. A four-way liquid crossroad.";
            placeableLiquid = true;
            health = 160; liquidPressure = 4;
            requirements(Category.liquid, ItemStack.with(Items.metaglass, 4, Items.surgeAlloy, 3, Items.silicon, 2));
        }};

        bioLiquidContainer = new LiquidRouter("bio-liquid-container") {{
            localizedName = "Bio-Alloy Liquid Container";
            description = "A massive liquid reservoir grown from bio-matter. Stores enormous quantities of any liquid.";
            placeableLiquid = true;
            size = 4; health = 1600; liquidCapacity = 10000;
            requirements(Category.liquid, ItemStack.with(Items.metaglass, 300, Items.titanium, 200, Items.surgeAlloy, 120, FRItems.bioMatter, 200, FRItems.livingSteelHard, 150, FRItems.nanoFabric, 50));
        }};

        liquidUnloader = new LiquidUnloader("liquid-unloader") {{
            localizedName = "Liquid Unloader";
            description = "Extracts a selected liquid from adjacent blocks and outputs it. Useful for sorting and redirecting liquids.";
            placeableLiquid = true;
            requirements(Category.liquid, ItemStack.with(Items.silicon, 20, Items.titanium, 15, Items.surgeAlloy, 4));
        }};
    }
}
