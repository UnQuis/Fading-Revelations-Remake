package fadingrevelations.content;
import mindustry.world.meta.Attribute;
import mindustry.type.*;
import mindustry.content.*;

import mindustry.type.Category;
import mindustry.world.Block;
import mindustry.world.blocks.production.*;
import mindustry.world.draw.*;
import arc.graphics.Color;
import mindustry.entities.effect.WaveEffect;
import mindustry.gen.Sounds;

public class FRDrills {
    public static Block tinyMechanicalDrill, tinyPneumaticDrill, tinyPlasmaBore, titaniumDrill,
            compactLaserDrill, hyperDrill, cosmicDrill, omniDrill,

            groundGrinder, groundMiller, groundCrusher,
            cliffGrinder, cliffMiller, ventConcentrator, oilBore, tungstenBore;

    public static void load() {
        tinyMechanicalDrill = new Drill("tiny-mechanical-drill") {{
            localizedName = "Tiny Mechanical Drill";
            description = "A tiny version of the mechanical drill.";
            size = 1; tier = 2; itemCapacity = 3; drillTime = 540; drawMineItem = false;
            consumeLiquid(Liquids.water, 0.009f).boost();
            requirements(Category.production, ItemStack.with(Items.copper, 4));
        }};

        tinyPneumaticDrill = new Drill("tiny-pneumatic-drill") {{
            localizedName = "Tiny Pneumatic Drill";
            description = "A tiny version of the pneumatic drill.";
            size = 1; itemCapacity = 3; tier = 3; drillTime = 355; drawMineItem = false;
            consumeLiquid(Liquids.water, 0.009f).boost();
            requirements(Category.production, ItemStack.with(Items.copper, 5, Items.graphite, 2));
        }};

        tinyPlasmaBore = new BeamDrill("tiny-plasma-bore") {{
            localizedName = "Tiny Plasma Bore";
            description = "A small version of the Plasma Bore.";
            size = 1; tier = 3; range = 3; drillTime = 280; fogRadius = 2;
            consumePower(0.0375f);
            consumeLiquid(Liquids.hydrogen, 0.0014f).boost();
            requirements(Category.production, ItemStack.with(Items.beryllium, 10));
        }};

        titaniumDrill = new Drill("titanium-drill") {{
            localizedName = "Titanium Drill";
            description = "A cheap drill for everything.";
            size = 2; liquidCapacity = 300; tier = 5; drillTime = 400;
            consumeLiquid(Liquids.water, 0.05f).boost();
            requirements(Category.production, ItemStack.with(Items.graphite, 10, Items.silicon, 10, Items.titanium, 20));
        }};

        compactLaserDrill = new Drill("compact-laser-drill") {{
            localizedName = "Compact Laser Drill";
            description = "Allows small-scale drilling via laser technology, but requires power. Capable of mining thorium.";
            size = 2; itemCapacity = 4; drawRim = true; tier = 4; drillTime = 250;
            consumePower(0.6f);
            consumeLiquid(Liquids.water, 0.018f).boost();
            requirements(Category.production, ItemStack.with(Items.copper, 25, Items.graphite, 20, Items.silicon, 20, Items.titanium, 10));
        }};

        hyperDrill = new Drill("hyper-drill") {{
            localizedName = "Hyper Drill";
            description = "The magnum opus of drill technology. Incapable of mining sand, but mines other ores at massive rates, consuming cryofluid in the process.";
            blockedItem = Items.sand; updateEffectChance = 0.1f; drawMineItem = false;
            warmupSpeed = 0.0025f; size = 5; hasPower = true; drawRim = false;
            hasLiquids = true; liquidCapacity = 40; hasItems = true; itemCapacity = 60;
            tier = 8; drillTime = 30; rotateSpeed = 12; liquidBoostIntensity = 1.3f;
            consumePower(4f);
            consumeLiquid(Liquids.cryofluid, 0.09f);
            requirements(Category.production, ItemStack.with(Items.copper, 130, Items.silicon, 120, Items.titanium, 100, FRItems.livingSteel, 100, FRItems.livingSteelHard, 150, Items.plastanium, 75));
        }};

        cosmicDrill = new Drill("cosmic-drill") {{
            localizedName = "Cosmic Drill";
            description = "A drill that has reached the limits of physics. Mines at impossible speeds. Requires cryofluid.";
            size = 4; drillTime = 3; tier = 25; itemCapacity = 500; liquidCapacity = 80;
            warmupSpeed = 0.01f; drawRim = true; hasPower = true; hasLiquids = true; hasItems = true;
            rotateSpeed = 20; liquidBoostIntensity = 2f; updateEffectChance = 0.2f; drawMineItem = false;
            consumePower(50f);
            consumeLiquid(Liquids.cryofluid, 0.3f);
            requirements(Category.production, ItemStack.with(
                Items.copper, 500, Items.silicon, 400, Items.titanium, 300,
                FRItems.livingSteelHard, 300, Items.plastanium, 200,
                Items.surgeAlloy, 150, Items.phaseFabric, 100
            ));
        }};

        omniDrill = new Drill("omni-drill") {{
            localizedName = "Omni Drill";
            description = "The ultimate drilling machine. Tears through reality itself to extract ores. Consumes massive power.";
            size = 5; drillTime = 0.5f; tier = 50; itemCapacity = 2000; liquidCapacity = 150;
            warmupSpeed = 0.02f; drawRim = true; hasPower = true; hasLiquids = true; hasItems = true;
            rotateSpeed = 30; liquidBoostIntensity = 3f; updateEffectChance = 0.3f; drawMineItem = false;
            consumePower(200f);
            consumeLiquid(Liquids.cryofluid, 1f);
            requirements(Category.production, ItemStack.with(
                Items.copper, 2000, Items.lead, 1500, Items.silicon, 1000,
                Items.titanium, 1000, Items.thorium, 500, Items.plastanium, 500,
                Items.surgeAlloy, 400, Items.phaseFabric, 300,
                FRItems.livingSteelHard, 500, FRItems.cryogenicAlloy, 200,
                FRItems.optiCrystal, 100, FRItems.energyCell, 100, FRItems.nanoFabric, 50, FRItems.bioMatter, 200
            ));
        }};

        groundGrinder = new GenericCrafter("ground-grinder") {{
            localizedName = "Ground Grinder";
            description = "Slowly grinds up the ground it sits on to produce sand. Requires power.";
            size = 1; hasPower = true; hasItems = true; hasLiquids = false;
            craftTime = 60; itemCapacity = 15;
            consumePower(1f);
            outputItem = new ItemStack(Items.sand, 2);
            requirements(Category.production, ItemStack.with(Items.copper, 60, Items.lead, 30));
        }};

        groundMiller = new GenericCrafter("ground-miller") {{
            localizedName = "Ground Miller";
            description = "Grinds up the ground it sits on to produce sand. Requires a hefty amount of power.";
            size = 2; hasPower = true; hasItems = true; hasLiquids = false;
            craftTime = 60; itemCapacity = 30;
            consumePower(2f);
            outputItem = new ItemStack(Items.sand, 4);
            requirements(Category.production, ItemStack.with(Items.copper, 100, Items.lead, 60, Items.graphite, 40));
        }};

        groundCrusher = new GenericCrafter("ground-crusher") {{
            localizedName = "Ground Crusher";
            description = "Quickly grinds up the ground it sits on to produce sand. Requires large quantities of power.";
            size = 3; hasPower = true; hasItems = true; hasLiquids = false;
            craftTime = 60; itemCapacity = 50;
            consumePower(6f);
            outputItem = new ItemStack(Items.sand, 12);
            requirements(Category.production, ItemStack.with(Items.copper, 160, Items.lead, 100, Items.graphite, 60, Items.silicon, 40));
        }};

        cliffGrinder = new WallCrafter("cliff-grinder") {{
            localizedName = "Cliff Grinder";
            description = "A faster Cliff Crusher.";
            size = 2; drillTime = 80; attribute = Attribute.sand; output = Items.sand; fogRadius = 2;
            consumePower(0.2f);
            requirements(Category.production, ItemStack.with(Items.graphite, 40, Items.beryllium, 35));
        }};

        cliffMiller = new WallCrafter("cliff-miller") {{
            localizedName = "Cliff Miller";
            description = "A faster Cliff Grinder.";
            size = 3; drillTime = 70; attribute = Attribute.sand; output = Items.sand; fogRadius = 3;
            consumePower(0.4f);
            requirements(Category.production, ItemStack.with(Items.graphite, 75, Items.beryllium, 60));
        }};

        ventConcentrator = new AttributeCrafter("vent-concentrator") {{
            localizedName = "Vent Concentrator";
            description = "A better Vent Condensator that creates more water.";
            size = 3; attribute = Attribute.steam; minEfficiency = 0.00001f; baseEfficiency = 0f;
            displayEfficiency = false; craftTime = 120; hasLiquids = true; boostScale = 0.111f;
            outputLiquid = new LiquidStack(Liquids.water, 0.7f); liquidCapacity = 80;
            consumePower(0.7f);
            craftEffect = Fx.turbinegenerate;
            ambientSound = Sounds.loopHum; ambientSoundVolume = 0.08f;
            requirements(Category.production, ItemStack.with(Items.graphite, 45, Items.beryllium, 80));
        }};

        oilBore = new Fracker("oil-bore") {{
            localizedName = "Oil Bore";
            description = "A better oil extractor that runs more efficiently.";
            health = 590; result = Liquids.oil; pumpAmount = 0.35f; baseEfficiency = 0f;
            itemUseTime = 60; size = 4; hasPower = true; hasLiquids = true; hasItems = true;
            itemCapacity = 15; liquidCapacity = 50; attribute = Attribute.oil;
            consumePower(6f); consumeItem(Items.sand, 1); consumeLiquid(Liquids.water, 0.15f);
            requirements(Category.production, ItemStack.with(Items.copper, 220, Items.lead, 190, Items.graphite, 210, Items.silicon, 120, Items.thorium, 160, Items.plastanium, 80));
        }};

        tungstenBore = new BeamDrill("tungsten-bore") {{
            localizedName = "Tungsten Bore";
            description = "A Plasma Bore made from Tungsten. Has more range than a normal Plasma Bore.";
            size = 2; tier = 3; range = 8; drillTime = 160; fogRadius = 3;
            consumePower(0.2f);
            consumeLiquid(Liquids.hydrogen, 0.005f).boost();
            requirements(Category.production, ItemStack.with(Items.beryllium, 60, Items.tungsten, 20));
        }};
    }
}
