package fadingrevelations.content;
import mindustry.type.*;
import mindustry.content.*;

import mindustry.type.Category;
import mindustry.world.Block;
import mindustry.world.blocks.power.*;

public class FRPowerNodes {
    public static Block reinforcedPowerNode, reinforcedLargePowerNode, powerReserve, advancedSurgeTower, crystalAccumulator;

    public static void load() {
        reinforcedPowerNode = new PowerNode("reinforced-power-node") {{
            localizedName = "Reinforced Power Node";
            description = "A power node reinforced using titanium. Is a lot more durable than a regular beam node while also maintaining slightly more range.";
            size = 1; health = 120; laserRange = 8; maxNodes = 10;
            requirements(Category.power, ItemStack.with(Items.copper, 1, Items.lead, 2, Items.titanium, 1));
        }};

        reinforcedLargePowerNode = new PowerNode("reinforced-large-power-node") {{
            localizedName = "Reinforced Large Power Node";
            description = "A large power node reinforced using titanium. Is a lot more durable than a regular beam node while also maintaining slightly more range.";
            size = 2; health = 480; laserRange = 18; maxNodes = 15;
            requirements(Category.power, ItemStack.with(Items.lead, 12, Items.silicon, 5, Items.titanium, 6));
        }};

        powerReserve = new Battery("power-reserve") {{
            localizedName = "Power Reserve";
            description = "A HUGE battery capable of storing vast amounts of power.";
            size = 6; health = 1480;
            consumePowerBuffered(500000f);
            requirements(Category.power, ItemStack.with(Items.lead, 300, Items.silicon, 100, Items.titanium, 120, Items.surgeAlloy, 25));
        }};

        advancedSurgeTower = new PowerNode("advanced-surge-tower") {{
            localizedName = "Advanced Surge Tower";
            description = "A power node with a lot of range but a max of 2 connections. A better surge tower.";
            details = "And upgraded version of the original.";
            size = 3; health = 320; laserRange = 80; maxNodes = 2;
            requirements(Category.power, ItemStack.with(Items.lead, 20, Items.silicon, 30, Items.titanium, 14, Items.phaseFabric, 25, Items.surgeAlloy, 30));
        }};

        crystalAccumulator = new Battery("crystal-accumulator") {{
            localizedName = "Crystal Accumulator";
            description = "A massive accumulator that stores energy in a crystalline lattice. Capable of holding astronomical amounts of power.";
            size = 5; health = 2200;
            consumePowerBuffered(5000000f);
            requirements(Category.power, ItemStack.with(Items.lead, 600, Items.silicon, 500, Items.titanium, 400, Items.surgeAlloy, 300, Items.phaseFabric, 200, FRItems.optiCrystal, 200, FRItems.energyCell, 150, FRItems.nanoFabric, 100, FRItems.livingSteelHard, 250));
        }};
    }
}
