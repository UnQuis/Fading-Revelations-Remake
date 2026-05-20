package fadingrevelations.content;
import arc.graphics.Color;
import mindustry.content.*;
import mindustry.type.*;
import mindustry.world.Block;
import mindustry.world.blocks.distribution.*;
import mindustry.world.blocks.storage.StorageBlock;

public class FRDistribution {
    public static Block titaniumRouter, titaniumJunction, titaniumDistributor, titaniumBridgeConveyor,
            tinyMd, surgeBridgeConveyor, surgeAlloyConveyor, massAccelerator, depository, amalgamConveyor,
            frReinforcedVault, frQuantumVault;

    public static void load() {
        titaniumRouter = new Router("titanium-router") {{
            localizedName = "Titanium Router";
            description = "Distributes items to 3 output directions equally. Faster than the regular router.";
            dumpTime = 1; speed = 0.1f; health = 75; itemCapacity = 20; size = 1;
            requirements(Category.distribution, ItemStack.with(Items.copper, 5, Items.titanium, 2));
        }};

        titaniumJunction = new Junction("titanium-junction") {{
            localizedName = "Titanium Junction";
            description = "Acts as a bridge for two crossing titanium conveyor belts.";
            speed = 18; health = 45; size = 1;
            requirements(Category.distribution, ItemStack.with(Items.copper, 6, Items.titanium, 4));
        }};

        titaniumDistributor = new Router("titanium-distributor") {{
            localizedName = "Titanium Distributor";
            description = "Distributes input items to 7 output directions equally. Faster than the regular distributor.";
            dumpTime = 1; speed = 0.1f; health = 220; itemCapacity = 20; size = 2;
            requirements(Category.distribution, ItemStack.with(Items.copper, 8, Items.lead, 6, Items.titanium, 4));
        }};

        titaniumBridgeConveyor = new ItemBridge("titanium-bridge-conveyor") {{
            localizedName = "Titanium Bridge Conveyor";
            description = "Transports items over terrain or buildings. Faster than the regular bridge conveyor.";
            health = 250; range = 6; transportTime = 4; hasPower = false;
            requirements(Category.distribution, ItemStack.with(Items.copper, 8, Items.lead, 8, Items.titanium, 8));
        }};

        tinyMd = new MassDriver("tiny-md") {{
            localizedName = "Tiny Mass Driver";
            description = "Short range item transport structure. Collects small batches of items and shoots them to other tiny mass drivers.";
            health = 160; itemCapacity = 20; reload = 10; range = 88; size = 1;
            consumePower(0.3333333333f);
            requirements(Category.distribution, ItemStack.with(Items.copper, 75, Items.lead, 50, Items.titanium, 25));
        }};

        surgeBridgeConveyor = new ItemBridge("surge-bridge-conveyor") {{
            localizedName = "Alloy Bridge Conveyor";
            description = "Transports items over terrain or buildings. Faster than the titanium bridge conveyor.";
            health = 250; range = 6; transportTime = 2; hasPower = false;
            requirements(Category.distribution, ItemStack.with(Items.copper, 6, Items.lead, 6, Items.surgeAlloy, 4));
        }};

        surgeAlloyConveyor = new Conveyor("surge-alloy-conveyor") {{
            localizedName = "Alloy Conveyor";
            description = "Transports items forward. Faster than a titanium conveyor.";
            details = "You hear a faint Speeeeeeedwagggoooooooon in the distance.";
            health = 350; speed = 0.2f; displayedSpeed = 22;
            requirements(Category.distribution, ItemStack.with(Items.copper, 1, Items.lead, 1, Items.titanium, 1, Items.surgeAlloy, 1));
        }};

        massAccelerator = new MassDriver("mass-accelerator") {{
            localizedName = "Mass Accelerator";
            description = "Long-range item transport structure. Collects large batches of items and shoots them to other mass accelerators.";
            health = 720; itemCapacity = 300; reload = 120; range = 600; size = 4;
            consumePower(3.3333333333f);
            requirements(Category.distribution, ItemStack.with(Items.lead, 200, Items.silicon, 110, Items.titanium, 200, Items.phaseFabric, 50));
        }};

        depository = new StorageBlock("depository") {{
            localizedName = "Depository";
            description = "Stores a gigantic amount of items of each type, akin to a vault. Expands storage when placed next to a core. Contents can be retrieved with an unloader.";
            size = 4; hasItems = true; itemCapacity = 1800; health = 760; coreMerge = true;
            requirements(Category.effect, ItemStack.with(Items.titanium, 300, Items.thorium, 180, Items.plastanium, 20));
        }};

        frReinforcedVault = new StorageBlock("fr-reinforced-vault") {{
            localizedName = "Reinforced Vault";
            description = "Stores a massive amount of items. Expands storage when placed next to a core.";
            size = 5; hasItems = true; itemCapacity = 10000; health = 1280; coreMerge = true;
            requirements(Category.effect, ItemStack.with(Items.titanium, 600, Items.thorium, 350, Items.plastanium, 80, FRItems.livingSteel, 40));
        }};

        frQuantumVault = new StorageBlock("fr-quantum-vault") {{
            localizedName = "Quantum Vault";
            description = "Stores an astronomical amount of items. Expands storage when placed next to a core.";
            size = 6; hasItems = true; itemCapacity = 25000; health = 1920; coreMerge = true;
            requirements(Category.effect, ItemStack.with(Items.titanium, 1200, Items.thorium, 800, Items.plastanium, 200, FRItems.livingSteel, 120, Items.surgeAlloy, 60));
        }};

        amalgamConveyor = new StackConveyor("amalgam-conveyor") {{
            localizedName = "Amalgam Conveyor";
            description = "Quickly transports items forward in larger batches, akin to the plastanium conveyor. Identical functionality.";
            details = "Something's wrong with my copy of Factorio...";
            health = 110; speed = 0.09f; itemCapacity = 20; outputRouter = true;
            glowColor = Color.valueOf("dbaf85");
            requirements(Category.distribution, ItemStack.with(Items.graphite, 1, Items.silicon, 1, FRItems.steelAlloy, 1));
        }};
    }
}
