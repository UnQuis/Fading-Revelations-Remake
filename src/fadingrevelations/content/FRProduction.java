package fadingrevelations.content;

import arc.graphics.*;
import arc.struct.Seq;
import mindustry.content.*;
import mindustry.gen.Sounds;
import mindustry.type.*;
import mindustry.world.Block;
import mindustry.world.blocks.heat.*;
import mindustry.world.blocks.power.*;
import mindustry.world.blocks.production.*;
import mindustry.world.blocks.units.*;
import mindustry.world.consumers.*;
import mindustry.world.draw.*;
import fadingrevelations.core.*;
import fadingrevelations.worlds.blocks.unit.FRAscendedUnitFactory;
import mindustry.world.meta.*;

import static mindustry.type.ItemStack.*;

public class FRProduction {
    public static Block
        tinyThermalGen, titaniumPanel, steamTurbine, slagGenerator, slagReactor,
        pyratiteGenerator, lsGen, steelReactor, uraniumReactor, solarArray,
        advancedSolarPanel, turbineConcentrator,
        livingSteelInfuser, livingSteelLiquifier, livingSteelLiquifyingForge,
        cryogenicGelMixer, alloyCrafter, cryogenicAlloyAssembler,
        amalgamSmelter, amalgamForge, basicMultismelter, steamCondenser,
        neutronBlender, acidVat, acidEmulsifier, uraniumrodCrafter,
        nukeCrafter,
        crystalSynthesizer,
        cellFabricator,
        nanoWeaver,
        bioRefinery,
        advancedSeparator, advancedCryofluidMixer, advancedCoalCentrifuge,
        atmosphericHeatConcentrator, bigPhaseWeaver, atmosphericExtractor,
        advancedWaterExtractor, enhancedPyratiteMixer, enhancedBlastMixer,
        dissolver, cyanogenFuser, corrosionChamber, phaseManufacturer,
        invertedPulverizer, surgeOvenBig, inducedKiln, heatDiverter, surgeMelter,
        bigPlastaniumPress, sporeCrusher, smallHeatRouter, esterificationChamber,
        siliconForge, siliconArcForge, graphiteForge, powderizer, greenhouse,
        carbideBasin,
        livingSteelComplex,
        primaryFactory, basicReassembly, advancedReassembly,
        progressiveAssembly, regenerator,
            ascendedFactory,
            fabricationNexus;

    public static void load() {
        tinyThermalGen = new ThermalGenerator("tiny-thermal-gen") {{
            localizedName = "Tiny Thermal Generator";
            description = "Produces power in hot locations.";
            attribute = Attribute.heat; powerProduction = 1.6f;
            requirements(Category.power, with(Items.copper, 10, Items.lead, 15, Items.metaglass, 10, Items.graphite, 8, Items.silicon, 8));
        }};

        titaniumPanel = new SolarGenerator("titanium-panel") {{
            localizedName = "Titanium Solar Panel";
            description = "A better solar panel made with titanium. Produces more power than a regular solar panel.";
            health = 90; size = 2; hasPower = true; hasLiquids = false; powerProduction = 0.4f;
            requirements(Category.power, with(Items.lead, 20, Items.silicon, 20, Items.titanium, 10));
        }};

        steamTurbine = new ConsumeGenerator("steam-turbine") {{
            localizedName = "Steam Turbine";
            description = "A better version of the Steam Turbine built using Living Steel. Uses more water in exchange for greatly improved power output. Slowly produces steam and builds up pressure. Explodes when the pressure reaches a critical point.";
            size = 3; hasLiquids = true; liquidCapacity = 20;
            outputLiquid = new LiquidStack(FRLiquids.steam, 0.05f);
            explodeOnFull = true; powerProduction = 13.33333f; itemDuration = 120;
            consumeLiquid(Liquids.water, 0.2f);
            consume(new ConsumeItemFlammable());
            consume(new ConsumeItemExplode());
            generateEffect = Fx.generatespark;
            ambientSound = Sounds.loopSmelter; ambientSoundVolume = 0.09f;
            requirements(Category.power, with(Items.copper, 60, Items.lead, 60, Items.graphite, 35, Items.silicon, 40, FRItems.livingSteel, 20));
        }};

        slagGenerator = new ConsumeGenerator("slag-generator") {{
            localizedName = "Slag Generator";
            description = "A generator that uses the heat of Slag to make power.";
            size = 3; health = 785; itemDuration = 60;
            powerProduction = 6.8f; hasLiquids = true; liquidCapacity = 100;
            consumeLiquid(Liquids.slag, 0.33333f);
            requirements(Category.power, with(Items.copper, 120, Items.lead, 100, Items.graphite, 60, Items.metaglass, 50, Items.titanium, 40));
        }};

        slagReactor = new ConsumeGenerator("slag-reactor") {{
            localizedName = "Slag Reactor";
            description = "A reactor that uses the heat of Slag to make power.";
            size = 3; health = 785; itemDuration = 60;
            powerProduction = 7f; hasLiquids = true; liquidCapacity = 100;
            consumeLiquid(Liquids.slag, 0.33333f);
            requirements(Category.power, with(Items.graphite, 50, Items.silicon, 40, Items.beryllium, 60, Items.tungsten, 20));
        }};

        pyratiteGenerator = new ConsumeGenerator("pyratite-generator") {{
            localizedName = "Pyratite Generator";
            description = "Uses the high flammability of pyratite to make a decent amount of power.";
            size = 2; hasPower = true; hasItems = true; itemDuration = 60; powerProduction = 12;
            consumeItem(Items.pyratite, 1);
            requirements(Category.power, with(Items.copper, 110, Items.lead, 90, Items.graphite, 50));
        }};

        lsGen = new ThermalGenerator("ls-gen") {{
            localizedName = "Living Steel Thermal Generator";
            description = "Produces heat in hot locations using intelligent spore steel activity. Is more efficient than the normal thermal generator.";
            attribute = Attribute.heat; powerProduction = 3f; size = 2; health = 240;
            requirements(Category.power, with(Items.copper, 50, Items.lead, 60, Items.metaglass, 50, Items.graphite, 45, FRItems.livingSteel, 35));
        }};

        steelReactor = new ImpactReactor("steel-reactor") {{
            localizedName = "Amalgam Generator";
            description = "Uses the high flammability of Steel Amalgam to create large amounts of power from it.";
            health = 1100; size = 4; hasPower = true; hasLiquids = true; hasItems = true;
            explosionShake = 15; itemCapacity = 30; liquidCapacity = 200;
            itemDuration = 360; powerProduction = 270;
            explosionDamage = 4000; explosionRadius = 320;
            consumePower(20f); consumeLiquid(Liquids.cryofluid, 0.1f); consumeItem(FRItems.steelAlloy, 1);
            requirements(Category.power, with(Items.copper, 750, Items.lead, 700, Items.graphite, 400, Items.metaglass, 350, Items.silicon, 350, Items.thorium, 150, FRItems.steelAlloy, 50));
        }};

        uraniumReactor = new NuclearReactor("uranium-reactor") {{
            localizedName = "Nuclear Reactor";
            description = "A reactor that uses uraniumrods to create a lot of power. Explodes violently when it is not cooled extensively!";
            size = 4; hasPower = true; hasLiquids = true; hasItems = true;
            explosionShake = 10; itemCapacity = 20; liquidCapacity = 120;
            itemDuration = 1650; powerProduction = 330; heating = 0.02f;
            smokeThreshold = 0.58f; explosionRadius = 500; explosionDamage = 10000;
            flashThreshold = 0.4f; coolantPower = 0.5f;
            fuelItem = FRItems.fuelRod;
            consumeLiquid(FRLiquids.livingSteelLiquid, 0.2f);
            consumeItem(FRItems.fuelRod, 9);
            requirements(Category.power, with(Items.copper, 1000, Items.lead, 900, Items.graphite, 750, Items.metaglass, 500, Items.silicon, 500, Items.titanium, 800, Items.thorium, 1200));
        }};

        solarArray = new SolarGenerator("solar-array") {{
            localizedName = "Solar Array";
            description = "A solar panel with a large surface area, enabling a high energy output.";
            health = 620; size = 6; hasPower = true; hasLiquids = false; powerProduction = 25;
            requirements(Category.power, with(Items.lead, 320, Items.silicon, 440, Items.phaseFabric, 80));
        }};

        advancedSolarPanel = new SolarGenerator("advanced-solar-panel") {{
            localizedName = "Advanced Solar Panel";
            description = "A better solar panel that takes up the same amount of space as a regular one.";
            health = 280; size = 3; hasPower = true; hasLiquids = false; powerProduction = 2.5f;
            requirements(Category.power, with(Items.lead, 160, Items.silicon, 220, Items.phaseFabric, 40));
        }};

        turbineConcentrator = new ThermalGenerator("turbine-concentrator") {{
            localizedName = "Turbine Concentrator";
            description = "Similar to a Turbine Condenser but creates larger amounts of power and more water.";
            size = 3; attribute = Attribute.steam; minEfficiency = 0.0001f;
            powerProduction = 0.48f; displayEfficiency = false;
            hasLiquids = true; outputLiquid = new LiquidStack(Liquids.water, 0.015f);
            generateEffect = Fx.turbinegenerate; effectChance = 0.05f;
            ambientSound = Sounds.loopHum; ambientSoundVolume = 0.08f;
            requirements(Category.power, with(Items.beryllium, 90, Items.graphite, 75));
        }};

        livingSteelInfuser = new GenericCrafter("living-steel-infuser") {{
            localizedName = "Living Steel Infusion Chamber";
            description = "Infuses titanium with spore pods, creating living steel.";
            size = 2; health = 140; hasPower = true; hasItems = true; hasLiquids = false;
            craftTime = 50; craftEffect = Fx.smeltsmoke; updateEffect = Fx.smeltsmoke;
            consumePower(0.8f); consumeItems(with(Items.titanium, 2, Items.sporePod, 1));
            outputItem = new ItemStack(FRItems.livingSteel, 2);
            requirements(Category.crafting, with(Items.copper, 150, Items.lead, 120, Items.titanium, 50));
        }};

        livingSteelLiquifier = new GenericCrafter("living-steel-liquifier") {{
            localizedName = "Living Steel Liquefaction Chamber";
            description = "Melts down living steel into a versatile fluid utilizing oil.";
            size = 2; hasPower = true; hasItems = true; health = 160;
            craftTime = 50; craftEffect = Fx.smeltsmoke; updateEffect = Fx.smeltsmoke;
            consumePower(0.8f); consumeItem(FRItems.livingSteel, 1);
            consumeLiquid(Liquids.oil, 0.3f);
            outputLiquid = new LiquidStack(FRLiquids.livingSteelLiquid, 0.2f);
            requirements(Category.crafting, with(Items.copper, 150, Items.lead, 120, Items.titanium, 50));
        }};

        livingSteelLiquifyingForge = new GenericCrafter("living-steel-liquifying-forge") {{
            localizedName = "Living Steel Liquefaction Forge";
            description = "Melts down living steel into a versatile fluid utilizing oil. More efficient than the liquefaction chamber.";
            size = 3; health = 285; hasPower = true; hasItems = true;
            craftTime = 50; craftEffect = Fx.smeltsmoke; updateEffect = Fx.smeltsmoke;
            consumePower(1.4f); consumeItem(FRItems.livingSteel, 2);
            consumeLiquid(Liquids.oil, 0.6f);
            outputLiquid = new LiquidStack(FRLiquids.livingSteelLiquid, 0.4f);
            requirements(Category.crafting, with(Items.copper, 460, Items.lead, 320, Items.titanium, 145, FRItems.livingSteel, 100));
        }};

        cryogenicGelMixer = new GenericCrafter("cryogenic-gel-mixer") {{
            localizedName = "Cryogenic Gel Mixer";
            description = "Used to create Cryogenic Gel needed to craft Cryogenic Alloy. Part of the [cyan]Cryogenic Alloy Assembly line structure.";
            size = 2; rotate = false; itemCapacity = 30; liquidCapacity = 100;
            consumePower(3f); consumeLiquid(Liquids.cryofluid, 0.8f); consumeItem(Items.silicon, 12);
            craftTime = 120;
            outputItem = new ItemStack(FRItems.cryogenicGel, 2);
            ambientSound = Sounds.loopHum; ambientSoundVolume = 0.2f;
            requirements(Category.crafting, with(Items.lead, 250, Items.metaglass, 210, Items.graphite, 200, Items.silicon, 150, Items.surgeAlloy, 75));
        }};

        alloyCrafter = new GenericCrafter("alloy-crafter") {{
            localizedName = "Igneous Alloy Smelter";
            description = "Smelts igneous alloy from surge alloy and titanium using slag. This smelter is a part of the [cyan]Cryogenic Alloy assembly line.";
            size = 2; rotate = false; itemCapacity = 200; liquidCapacity = 100;
            consumePower(5f); consumeLiquid(Liquids.slag, 0.8f);
            consumeItems(with(Items.surgeAlloy, 16, Items.titanium, 48));
            craftTime = 240;
            outputItem = new ItemStack(FRItems.igneousAlloy, 8);
            ambientSound = Sounds.loopSmelter; ambientSoundVolume = 0.3f;
            requirements(Category.crafting, with(Items.lead, 300, Items.graphite, 250, Items.silicon, 220, Items.titanium, 200, Items.surgeAlloy, 75));
        }};

        cryogenicAlloyAssembler = new GenericCrafter("cryogenic-alloy-assembler") {{
            localizedName = "Cryogenic Alloy Fusing Basin";
            description = "Amalgamates Igneous Alloy and Cryogenic Gel by shock-cooling it using Neutron Fluid. This basin is a part of the [cyan]Cryogenic Alloy assembly line.";
            size = 4; rotate = false;
            consumePower(10f); consumeLiquid(FRLiquids.neutronFluid, 0.4f);
            consumeItems(with(FRItems.igneousAlloy, 2, FRItems.cryogenicGel, 1, Items.graphite, 4));
            craftTime = 60;
            outputItem = new ItemStack(FRItems.cryogenicAlloy, 4);
            requirements(Category.crafting, with(Items.lead, 700, Items.graphite, 600, Items.metaglass, 500, Items.silicon, 500, Items.surgeAlloy, 260, FRItems.steelAlloy, 200));
        }};

        amalgamSmelter = new GenericCrafter("amalgam-smelter") {{
            localizedName = "Amalgam Smelter";
            description = "Using slag as a heat source, smelts copper, living steel, and surge alloy into steel amalgam.";
            size = 3; health = 240; itemCapacity = 30; hasPower = true; hasLiquids = true; hasItems = true;
            craftTime = 120; updateEffect = Fx.smeltsmoke;
            consumePower(7f); consumeLiquid(Liquids.slag, 0.2f);
            consumeItems(with(Items.copper, 2, FRItems.livingSteel, 3, Items.surgeAlloy, 2));
            outputItem = new ItemStack(FRItems.steelAlloy, 2);
            requirements(Category.crafting, with(Items.copper, 250, Items.lead, 200, FRItems.livingSteel, 100, Items.surgeAlloy, 50));
        }};

        amalgamForge = new GenericCrafter("amalgam-forge") {{
            localizedName = "Amalgam Forge";
            description = "Using slag as a heat source, smelts copper, living steel, and surge alloy into larger quantities of steel amalgam than the smelter.";
            size = 4; health = 240; itemCapacity = 30; hasPower = true; hasLiquids = true; hasItems = true;
            craftTime = 75; updateEffect = Fx.smeltsmoke;
            consumePower(7.4f); consumeLiquid(Liquids.slag, 0.2f);
            consumeItems(with(Items.copper, 2, FRItems.livingSteel, 3, Items.surgeAlloy, 2));
            outputItem = new ItemStack(FRItems.steelAlloy, 2);
            requirements(Category.crafting, with(Items.copper, 360, Items.lead, 300, FRItems.livingSteel, 150, Items.surgeAlloy, 75));
        }};

        basicMultismelter = new GenericCrafter("basic-multismelter") {{
            localizedName = "Basic Multismelter";
            description = "Uses a combination of smelting and compression procedures to mass produce basic resources.";
            size = 3;
            consumePower(1.2f); consumeItems(with(Items.lead, 1, Items.sand, 3, Items.coal, 4));
            craftTime = 30;
            outputItems = with(Items.graphite, 1, Items.silicon, 1, Items.metaglass, 1);
            requirements(Category.crafting, with(Items.copper, 100, Items.lead, 80, Items.graphite, 50));
        }};

        steamCondenser = new GenericCrafter("steam-condenser") {{
            localizedName = "Steam Condenser";
            description = "Pulls water vapor from the atmosphere, condensing it into steam.";
            size = 2; scaledHealth = 60;
            craftTime = 300;
            outputLiquid = new LiquidStack(FRLiquids.steam, 0.0125f);
            requirements(Category.crafting, with(Items.copper, 60, Items.lead, 35));
        }};

        neutronBlender = new GenericCrafter("neutron-blender") {{
            localizedName = "Neutron Blender";
            description = "Blends large quantities of cryofluid, oil and liquefied living steel into the highly potent neutron fluid coolant.";
            size = 3; hasItems = true; hasLiquids = true; itemCapacity = 30; liquidCapacity = 120;
            consumePower(2.2f);
            consumeLiquid(Liquids.oil, 0.2f);
            consumeLiquid(Liquids.cryofluid, 0.2f);
            consumeLiquid(FRLiquids.livingSteelLiquid, 0.2f);
            craftTime = 60;
            outputLiquid = new LiquidStack(FRLiquids.neutronFluid, 0.2f);
            ambientSound = Sounds.loopHum;
            ambientSoundVolume = 0.4f;
            requirements(Category.crafting, with(Items.metaglass, 250, Items.silicon, 180, Items.titanium, 220, FRItems.livingSteel, 140));
        }};

        acidVat = new GenericCrafter("acid-vat") {{
            localizedName = "Acid Vat";
            description = "Acidifies lead via chemical reaction.";
            size = 2; liquidCapacity = 40;
            consumePower(1.25f); consumeItem(Items.lead, 1);
            craftTime = 30;
            outputLiquid = new LiquidStack(FRLiquids.acid, 0.2f);
            requirements(Category.crafting, with(Items.copper, 60, Items.metaglass, 40, Items.titanium, 30));
        }};

        acidEmulsifier = new GenericCrafter("acid-emulsifier") {{
            localizedName = "Acid Emulsifier";
            description = "Emulsifies acid into oil.";
            size = 2; liquidCapacity = 60; craftTime = 20;
            craftEffect = Fx.none; updateEffect = Fx.none;
            consumePower(1.5f); consumeLiquid(FRLiquids.acid, 0.4f);
            outputLiquid = new LiquidStack(Liquids.oil, 0.4f);
            requirements(Category.crafting, with(Items.copper, 80, Items.graphite, 40, Items.titanium, 60));
        }};

        uraniumrodCrafter = new GenericCrafter("uraniumrod-crafter") {{
            localizedName = "Fuel Rod Crafter";
            description = "Layers a plastanium and lead core with thorium, creating fuel rods.";
            size = 2; hasPower = true; hasItems = true; hasLiquids = false;
            craftTime = 300; craftEffect = Fx.smeltsmoke; updateEffect = Fx.pulverizeRed;
            consumePower(1.1f);
            consumeItems(with(Items.thorium, 1, Items.plastanium, 2, Items.lead, 3, Items.phaseFabric, 1));
            outputItem = new ItemStack(FRItems.fuelRod, 3);
            requirements(Category.crafting, with(Items.copper, 200, Items.lead, 150, Items.titanium, 60, Items.thorium, 50));
        }};

        crystalSynthesizer = new GenericCrafter("crystal-synthesizer") {{
            localizedName = "Crystal Synthesizer";
            description = "Grows highly pure optical crystals from a silicon-thorium solution. Produces [#8a2be2]Optical Crystals[] used in advanced optics and energy weapons.";
            size = 3; hasPower = true; hasItems = true; hasLiquids = true;
            liquidCapacity = 30; itemCapacity = 20; craftTime = 90;
            craftEffect = Fx.pulverize;
            consumePower(2.5f);
            consumeItems(with(Items.silicon, 3, Items.thorium, 2));
            consumeLiquid(Liquids.water, 0.15f);
            outputItem = new ItemStack(FRItems.optiCrystal, 1);
            requirements(Category.crafting, with(Items.copper, 250, Items.lead, 200, Items.silicon, 150, Items.thorium, 100, Items.graphite, 80));
        }};

        cellFabricator = new GenericCrafter("cell-fabricator") {{
            localizedName = "Cell Fabricator";
            description = "Assembles compact energy cells from surge alloy and silicon. [#f0d000]Energy Cells[] store massive electrical charges for high-power equipment.";
            size = 3; hasPower = true; hasItems = true; hasLiquids = false;
            itemCapacity = 20; craftTime = 120;
            craftEffect = Fx.shootSmokeSquare;
            consumePower(3f);
            consumeItems(with(Items.silicon, 4, Items.surgeAlloy, 2, Items.lead, 3));
            outputItem = new ItemStack(FRItems.energyCell, 1);
            requirements(Category.crafting, with(Items.copper, 300, Items.lead, 250, Items.silicon, 200, Items.surgeAlloy, 120, Items.graphite, 100));
        }};

        nanoWeaver = new GenericCrafter("nano-weaver") {{
            localizedName = "Nano Weaver";
            description = "Weaves living steel fibers with phase fabric to create self-repairing [#20b2aa]Nano Fabric[]. Nearly indestructible and used in the most advanced equipment.";
            size = 3; hasPower = true; hasItems = true; hasLiquids = true;
            liquidCapacity = 30; itemCapacity = 20; craftTime = 150;
            craftEffect = Fx.smeltsmoke;
            consumePower(4f);
            consumeItems(with(FRItems.livingSteel, 3, Items.phaseFabric, 2));
            consumeLiquid(FRLiquids.neutronFluid, 0.1f);
            outputItem = new ItemStack(FRItems.nanoFabric, 1);
            requirements(Category.crafting, with(Items.copper, 350, Items.lead, 300, Items.silicon, 250, Items.phaseFabric, 150, FRItems.livingSteelHard, 100));
        }};

        bioRefinery = new GenericCrafter("bio-refinery") {{
            localizedName = "Bio-Refinery";
            description = "Processes spores into concentrated [#66cc00]Biological Matter[] through a controlled fermentation process. The resulting organic compound is rich in energy.";
            size = 3; hasPower = true; hasItems = true; hasLiquids = true;
            liquidCapacity = 60; itemCapacity = 30; craftTime = 60;
            craftEffect = Fx.steam;
            consumePower(1.5f);
            consumeItems(with(Items.sporePod, 4));
            consumeLiquid(Liquids.water, 0.2f);
            outputItem = new ItemStack(FRItems.bioMatter, 2);
            outputLiquid = new LiquidStack(Liquids.water, 0.1f);
            requirements(Category.crafting, with(Items.copper, 200, Items.lead, 150, Items.silicon, 80, Items.graphite, 60));
        }};

        fabricationNexus = new MultiCrafter("fabrication-nexus") {{
            localizedName = "Fabrication Nexus";
            description = "A centralized 6x6 multi-factory that combines all advanced material production chains. Switch between 8 different recipes to produce optical crystals, energy cells, nano fabric, and biological matter.";
            details = "MultiCrafter Lib integration by Fading Revelations team.";
            size = 6; health = 800;
            switchStyle = RecipeSwitchStyle.transform;
            craftEffect = Fx.pulverize;
            updateEffect = Fx.none;
            changeRecipeEffect = Fx.rotateBlock;
            ambientSound = Sounds.loopMachine;
            ambientSoundVolume = 0.06f;
            drawer = new DrawDefault();
            resolvedRecipes = Seq.with(
                new fadingrevelations.core.Recipe() {{
                    input = new IOEntry() {{
                        items = ItemStack.with(Items.silicon, 3, Items.thorium, 2);
                        fluids = LiquidStack.with(Liquids.water, 0.15f);
                        power = 2.5f;
                    }};
                    output = new IOEntry() {{
                        items = ItemStack.with(FRItems.optiCrystal, 1);
                    }};
                    craftTime = 90f;
                    craftEffect = Fx.pulverize;
                }},
                new fadingrevelations.core.Recipe() {{
                    input = new IOEntry() {{
                        items = ItemStack.with(Items.silicon, 4, Items.surgeAlloy, 2, Items.lead, 3);
                        power = 3f;
                    }};
                    output = new IOEntry() {{
                        items = ItemStack.with(FRItems.energyCell, 1);
                    }};
                    craftTime = 120f;
                    craftEffect = Fx.shootSmokeSquare;
                }},
                new fadingrevelations.core.Recipe() {{
                    input = new IOEntry() {{
                        items = ItemStack.with(FRItems.livingSteel, 3, Items.phaseFabric, 2);
                        fluids = LiquidStack.with(FRLiquids.neutronFluid, 0.1f);
                        power = 4f;
                    }};
                    output = new IOEntry() {{
                        items = ItemStack.with(FRItems.nanoFabric, 1);
                    }};
                    craftTime = 150f;
                    craftEffect = Fx.smeltsmoke;
                }},
                new fadingrevelations.core.Recipe() {{
                    input = new IOEntry() {{
                        items = ItemStack.with(Items.sporePod, 4);
                        fluids = LiquidStack.with(Liquids.water, 0.2f);
                        power = 1.5f;
                    }};
                    output = new IOEntry() {{
                        items = ItemStack.with(FRItems.bioMatter, 2);
                        fluids = LiquidStack.with(Liquids.water, 0.1f);
                    }};
                    craftTime = 60f;
                    craftEffect = Fx.steam;
                }},
                new fadingrevelations.core.Recipe() {{
                    input = new IOEntry() {{
                        items = ItemStack.with(FRItems.energyCell, 2, FRItems.livingSteelHard, 4);
                        fluids = LiquidStack.with(Liquids.cryofluid, 0.2f);
                        power = 5f;
                    }};
                    output = new IOEntry() {{
                        items = ItemStack.with(FRItems.optiCrystal, 3);
                    }};
                    craftTime = 75f;
                    craftEffect = Fx.pulverize;
                }},
                new fadingrevelations.core.Recipe() {{
                    input = new IOEntry() {{
                        items = ItemStack.with(FRItems.optiCrystal, 2, Items.surgeAlloy, 4);
                        fluids = LiquidStack.with(Liquids.water, 0.15f);
                        power = 6f;
                    }};
                    output = new IOEntry() {{
                        items = ItemStack.with(FRItems.energyCell, 3);
                    }};
                    craftTime = 90f;
                    craftEffect = Fx.shootSmokeSquare;
                }},
                new fadingrevelations.core.Recipe() {{
                    input = new IOEntry() {{
                        items = ItemStack.with(FRItems.bioMatter, 3, Items.phaseFabric, 3);
                        fluids = LiquidStack.with(FRLiquids.neutronFluid, 0.15f);
                        power = 7.5f;
                    }};
                    output = new IOEntry() {{
                        items = ItemStack.with(FRItems.nanoFabric, 3);
                    }};
                    craftTime = 110f;
                    craftEffect = Fx.smeltsmoke;
                }},
                new fadingrevelations.core.Recipe() {{
                    input = new IOEntry() {{
                        items = ItemStack.with(FRItems.nanoFabric, 1, Items.sporePod, 6);
                        fluids = LiquidStack.with(Liquids.water, 0.3f);
                        power = 3.5f;
                    }};
                    output = new IOEntry() {{
                        items = ItemStack.with(FRItems.bioMatter, 5);
                        fluids = LiquidStack.with(Liquids.water, 0.15f);
                    }};
                    craftTime = 50f;
                    craftEffect = Fx.steam;
                }}
            );
            requirements(Category.crafting,
                with(Items.copper, 1500, Items.lead, 1200, Items.silicon, 1000, Items.thorium, 600,
                    Items.surgeAlloy, 400, Items.phaseFabric, 300, FRItems.livingSteel, 250, FRItems.livingSteelHard, 200)
            );
        }};

        livingSteelComplex = new MultiCrafter("living-steel-complex") {{
            localizedName = "Living Steel Complex";
            description = "A centralized 5x5 multi-factory that combines living steel production and hardening into one versatile block. Switch between efficient and basic recipes for both processes.";
            details = "MultiCrafter Lib integration by Fading Revelations team.";
            size = 5; health = 680;
            switchStyle = RecipeSwitchStyle.transform;
            craftEffect = Fx.smeltsmoke;
            updateEffect = Fx.smeltsmoke;
            changeRecipeEffect = Fx.rotateBlock;
            ambientSound = Sounds.loopMachine;
            ambientSoundVolume = 0.05f;
            drawer = new DrawDefault();
            resolvedRecipes = Seq.with(
                new fadingrevelations.core.Recipe() {{
                    input = new IOEntry() {{
                        items = ItemStack.with(Items.titanium, 2, Items.sporePod, 1);
                        power = 1.4f;
                    }};
                    output = new IOEntry() {{
                        items = ItemStack.with(FRItems.livingSteel, 3);
                    }};
                    craftTime = 40f;
                    craftEffect = Fx.smeltsmoke;
                }},
                new fadingrevelations.core.Recipe() {{
                    input = new IOEntry() {{
                        items = ItemStack.with(Items.titanium, 2, Items.sporePod, 1);
                        power = 0.8f;
                    }};
                    output = new IOEntry() {{
                        items = ItemStack.with(FRItems.livingSteel, 2);
                    }};
                    craftTime = 50f;
                    craftEffect = Fx.smeltsmoke;
                }},
                new fadingrevelations.core.Recipe() {{
                    input = new IOEntry() {{
                        items = ItemStack.with(FRItems.livingSteel, 1, Items.thorium, 2);
                        power = 1.4f;
                    }};
                    output = new IOEntry() {{
                        items = ItemStack.with(FRItems.livingSteelHard, 3);
                    }};
                    craftTime = 40f;
                    craftEffect = Fx.smeltsmoke;
                }},
                new fadingrevelations.core.Recipe() {{
                    input = new IOEntry() {{
                        items = ItemStack.with(FRItems.livingSteel, 1, Items.thorium, 2);
                        power = 0.8f;
                    }};
                    output = new IOEntry() {{
                        items = ItemStack.with(FRItems.livingSteelHard, 2);
                    }};
                    craftTime = 50f;
                    craftEffect = Fx.smeltsmoke;
                }}
            );
            requirements(Category.crafting,
                with(Items.copper, 800, Items.lead, 600, Items.titanium, 300, Items.thorium, 200,
                    FRItems.livingSteel, 150, Items.graphite, 100)
            );
        }};

        nukeCrafter = new GenericCrafter("nuke-crafter") {{
            localizedName = "Nuke Crafter";
            description = "Crafts nuclear warheads using fuel rods and graphite.";
            size = 2; hasPower = true; hasItems = true; hasLiquids = false; itemCapacity = 60;
            craftTime = 600;
            consumePower(6f);
            consumeItems(with(FRItems.fuelRod, 10, Items.graphite, 20, Items.thorium, 10, Items.blastCompound, 10));
            outputItem = new ItemStack(FRItems.nuke, 1);
            requirements(Category.crafting, with(Items.copper, 1500, Items.lead, 1200, Items.graphite, 1000, Items.surgeAlloy, 600));
        }};

        advancedSeparator = new Separator("advanced-separator") {{
            localizedName = "Advanced Separator";
            description = "Separates slag and scrap into their raw material components. High output.";
            size = 4; hasPower = true; solid = true; hasLiquids = true; hasItems = true;
            liquidCapacity = 60; craftTime = 10; health = 690;
            consumePower(5.4f); consumeLiquid(Liquids.slag, 0.1f); consumeItem(Items.scrap, 2);
            results = with(Items.copper, 10, Items.lead, 10, Items.graphite, 6, Items.titanium, 6, Items.thorium, 4);
            requirements(Category.crafting, with(Items.lead, 300, Items.graphite, 275, Items.titanium, 275, Items.plastanium, 30, Items.phaseFabric, 30, Items.surgeAlloy, 30));
        }};

        advancedCryofluidMixer = new GenericCrafter("advanced-cryofluid-mixer") {{
            localizedName = "Advanced Cryofluid-Mixer";
            description = "Mixes large quantities of water with fine titanium powder to produce cryofluid.";
            size = 2; health = 240; itemCapacity = 30; liquidCapacity = 70;
            hasPower = true; hasLiquids = true; hasItems = true; updateEffect = Fx.smeltsmoke;
            craftTime = 60;
            consumePower(1.7f); consumeLiquid(Liquids.water, 0.4f); consumeItem(Items.titanium, 1);
            outputLiquid = new LiquidStack(Liquids.cryofluid, 0.4f);
            requirements(Category.crafting, with(Items.graphite, 100, Items.silicon, 75, Items.titanium, 100, Items.thorium, 200));
        }};

        advancedCoalCentrifuge = new GenericCrafter("advanced-coal-centrifuge") {{
            localizedName = "Coal Condenser";
            description = "Condenses oil into coal in a highly efficient process.";
            size = 2; health = 440; hasPower = true; hasLiquids = true; hasItems = true;
            craftTime = 90;
            consumePower(1f); consumeLiquid(Liquids.oil, 0.15f);
            outputItem = new ItemStack(Items.coal, 6);
            requirements(Category.crafting, with(Items.lead, 200, Items.graphite, 75, Items.silicon, 75, Items.titanium, 200));
        }};

        atmosphericHeatConcentrator = new HeatProducer("atmospheric-heat-concentrator") {{
            localizedName = "Advanced Heating Element";
            description = "An improved version of the Electric Heater. Creates more heat.";
            size = 2; rotateDraw = false; researchCostMultiplier = 4;
            heatOutput = 5; regionRotated1 = 1;
            consumePower(2f);
            ambientSound = Sounds.loopExtract; ambientSoundVolume = 0.09f;
            requirements(Category.crafting, with(Items.beryllium, 70, Items.tungsten, 50, Items.oxide, 40));
        }};

        bigPhaseWeaver = new GenericCrafter("big-phase-weaver") {{
            localizedName = "Fabric Forge";
            description = "A better phase fabric forge that is more cost-efficient.";
            size = 3; hasPower = true; hasItems = true; itemCapacity = 60; hasLiquids = false;
            craftTime = 130; health = 300;
            consumePower(9.9f); consumeItems(with(Items.thorium, 6, Items.sand, 15));
            outputItem = new ItemStack(Items.phaseFabric, 4);
            requirements(Category.crafting, with(Items.lead, 320, Items.silicon, 320, Items.titanium, 140, Items.phaseFabric, 25));
        }};

        atmosphericExtractor = new HeatCrafter("atmospheric-extractor") {{
            localizedName = "Atmospheric Extractor";
            description = "Extracts and concentrates Nitrogen out of the atmosphere. Works more efficiently than an Atmospheric Concentrator.";
            size = 4; hasLiquids = true; researchCostMultiplier = 1.1f; liquidCapacity = 50;
            consumePower(2.6f); heatRequirement = 8;
            craftTime = 60;
            outputLiquid = new LiquidStack(Liquids.nitrogen, 0.1f);
            ambientSound = Sounds.loopExtract; ambientSoundVolume = 0.09f;
            requirements(Category.crafting, with(Items.oxide, 100, Items.beryllium, 260, Items.silicon, 200));
        }};

        advancedWaterExtractor = new SolidPump("advanced-water-extractor") {{
            localizedName = "Advanced Water Extractor";
            description = "Extracts large quantities of groundwater. Used in locations with no surface water available.";
            health = 320; result = Liquids.water; hasPower = true; attribute = Attribute.water;
            hasLiquids = true; size = 3; pumpAmount = 0.3f; liquidCapacity = 250;
            consumePower(3.5f);
            requirements(Category.production, with(Items.copper, 175, Items.lead, 140, Items.graphite, 150, Items.metaglass, 65));
        }};

        enhancedPyratiteMixer = new GenericCrafter("enhanced-pyratite-mixer") {{
            localizedName = "Pyratite Forge";
            description = "A stronger pyratite mixer. Gets very hot so needs graphite for isolation.";
            size = 3; hasPower = true; hasItems = true; hasLiquids = false; health = 240; itemCapacity = 30;
            craftTime = 60; craftEffect = Fx.pulverizeRed; updateEffect = Fx.pulverizeRed;
            consumePower(0.6f); consumeItems(with(Items.coal, 3, Items.lead, 6, Items.sand, 6));
            outputItem = new ItemStack(Items.pyratite, 3);
            requirements(Category.crafting, with(Items.copper, 150, Items.lead, 120, Items.graphite, 60, Items.titanium, 30));
        }};

        enhancedBlastMixer = new GenericCrafter("enhanced-blast-mixer") {{
            localizedName = "Blast Forge";
            description = "A better blast mixer. Gets very hot so it uses graphite for isolation.";
            size = 3; hasPower = true; hasItems = true; health = 240; hasLiquids = false;
            craftTime = 90; craftEffect = Fx.plasticExplosion; updateEffect = Fx.pulverizeRed;
            consumePower(0.6f); consumeItems(with(Items.pyratite, 2, Items.sporePod, 2));
            outputItem = new ItemStack(Items.blastCompound, 2);
            requirements(Category.crafting, with(Items.copper, 300, Items.lead, 240, Items.graphite, 120, Items.titanium, 60));
        }};

        dissolver = new GenericCrafter("dissolver") {{
            localizedName = "Dissolver";
            description = "A larger and more powerful Melter.";
            size = 2; itemCapacity = 20; liquidCapacity = 25;
            craftTime = 10;
            consumePower(1.2f); consumeItem(Items.scrap, 1);
            outputLiquid = new LiquidStack(Liquids.slag, 0.25f);
            requirements(Category.crafting, with(Items.copper, 60, Items.lead, 70, Items.graphite, 50));
        }};

        cyanogenFuser = new HeatCrafter("cyanogen-fuser") {{
            localizedName = "Cyanogen Fuser";
            description = "Joins Arkycite and Graphite to make larger amounts of Cyanogen than a normal Cyanogen Synthesizer.";
            size = 4; heatRequirement = 7; liquidCapacity = 100;
            craftTime = 60;
            outputLiquid = new LiquidStack(Liquids.cyanogen, 0.075f);
            consumePower(2.5f); consumeItem(Items.graphite, 1); consumeLiquid(Liquids.arkycite, 0.656f);
            ambientSound = Sounds.loopExtract; ambientSoundVolume = 0.1f;
            requirements(Category.crafting, with(Items.carbide, 90, Items.silicon, 140, Items.beryllium, 160));
        }};

        corrosionChamber = new HeatProducer("corrosion-chamber") {{
            localizedName = "Corrosion Chamber";
            description = "A better Oxidation Chamber that creates Oxide more efficiently and outputs more heat.";
            size = 4; hasLiquids = true; rotateDraw = false; researchCostMultiplier = 1.1f;
            craftTime = 120; regionRotated1 = 2; heatOutput = 7;
            outputItem = new ItemStack(Items.oxide, 2);
            consumePower(0.8f); consumeLiquid(Liquids.ozone, 0.0333f); consumeItem(Items.beryllium, 1);
            ambientSound = Sounds.loopExtract; ambientSoundVolume = 0.1f; liquidCapacity = 40;
            requirements(Category.crafting, with(Items.tungsten, 180, Items.graphite, 120, Items.silicon, 180, Items.beryllium, 200));
        }};

        phaseManufacturer = new HeatCrafter("phase-manufacturer") {{
            localizedName = "Phase Manufacturer";
            description = "A better phase synthesizer that works more efficiently";
            size = 4; itemCapacity = 50; heatRequirement = 10; craftTime = 90; liquidCapacity = 50;
            outputItem = new ItemStack(Items.phaseFabric, 2);
            consumePower(9f); consumeItems(with(Items.thorium, 2, Items.sand, 6));
            consumeLiquid(Liquids.ozone, 0.0334f);
            ambientSound = Sounds.loopTech; ambientSoundVolume = 0.05f;
            requirements(Category.crafting, with(Items.carbide, 120, Items.silicon, 160, Items.thorium, 120, Items.tungsten, 280));
        }};

        invertedPulverizer = new GenericCrafter("inverted-pulverizer") {{
            localizedName = "Inverted Pulverizer";
            description = "Pulverizes sand to scrap. Don't ask how but you will be wondering how useful that is!";
            size = 1; hasPower = true; hasLiquids = false; hasItems = true;
            craftTime = 50; updateEffect = Fx.pulverizeSmall; liquidCapacity = 50;
            consumePower(0.5f); consumeItem(Items.sand, 1);
            outputItem = new ItemStack(Items.scrap, 1);
            ambientSound = Sounds.loopGrind; ambientSoundVolume = 0.2f;
            requirements(Category.crafting, with(Items.copper, 30, Items.lead, 25));
        }};

        surgeOvenBig = new GenericCrafter("surge-oven-big") {{
            localizedName = "Alloy Forge";
            description = "A better surge alloy forge that is more cost-efficient.";
            size = 4; hasPower = true; hasItems = true; hasLiquids = false; craftTime = 90;
            updateEffect = Fx.hitMeltdown;
            consumePower(6f); consumeItems(with(Items.copper, 3, Items.lead, 4, Items.silicon, 3, Items.titanium, 2));
            outputItem = new ItemStack(Items.surgeAlloy, 2);
            requirements(Category.crafting, with(Items.lead, 160, Items.silicon, 160, Items.thorium, 140, Items.surgeAlloy, 25));
        }};

        inducedKiln = new GenericCrafter("induced-kiln") {{
            localizedName = "Induced Kiln";
            description = "A better kiln that is a lot more efficient.";
            size = 3; hasPower = true; hasItems = true; health = 320; hasLiquids = false;
            craftTime = 60;
            consumePower(1.5f); consumeItems(with(Items.lead, 3, Items.sand, 4));
            outputItem = new ItemStack(Items.metaglass, 5);
            ambientSound = Sounds.loopSmelter; ambientSoundVolume = 0.35f;
            requirements(Category.crafting, with(Items.copper, 70, Items.lead, 60, Items.graphite, 20, Items.metaglass, 15));
        }};

        heatDiverter = new HeatConductor("heat-diverter") {{
            localizedName = "Heat Diverter";
            description = "A small block that is used to redirect heat to other blocks.";
            size = 1; researchCostMultiplier = 10; regionRotated1 = 1;
            requirements(Category.crafting, with(Items.tungsten, 5));
        }};

        surgeMelter = new HeatCrafter("surge-melter") {{
            localizedName = "Surge Melter";
            description = "A better version of the surge crucible that creates surge alloy more efficiently.";
            size = 4; itemCapacity = 30; heatRequirement = 11; hasLiquids = true; hasPower = true; hasItems = true;
            craftTime = 90; liquidCapacity = 600;
            outputItem = new ItemStack(Items.surgeAlloy, 1);
            consumePower(2.5f); consumeItem(Items.silicon, 3); consumeLiquid(Liquids.slag, 0.667f);
            ambientSound = Sounds.loopSmelter; ambientSoundVolume = 1f;
            requirements(Category.crafting, with(Items.silicon, 160, Items.graphite, 160, Items.tungsten, 110, Items.oxide, 100));
        }};

        bigPlastaniumPress = new GenericCrafter("big-plastanium-press") {{
            localizedName = "Plastanium Forge";
            description = "A better plastanium forge that is more cost-efficient.";
            size = 3; hasPower = true; hasItems = true; hasLiquids = true; health = 600;
            updateEffect = Fx.plasticburn; craftEffect = Fx.formsmoke;
            craftTime = 60; itemCapacity = 20; liquidCapacity = 84;
            consumePower(3.75f); consumeItem(Items.titanium, 6); consumeLiquid(Liquids.oil, 0.6f);
            outputItem = new ItemStack(Items.plastanium, 4);
            requirements(Category.crafting, with(Items.lead, 160, Items.silicon, 160, Items.titanium, 140, Items.plastanium, 25));
        }};

        sporeCrusher = new GenericCrafter("spore-crusher") {{
            localizedName = "Spore Crusher";
            description = "Creates oil by crushing spore pods.";
            size = 3; hasPower = true; hasItems = true; hasLiquids = true;
            itemCapacity = 20; health = 720; liquidCapacity = 120;
            craftTime = 25; updateEffect = Fx.none;
            consumePower(1.6f); consumeItem(Items.sporePod, 2);
            outputLiquid = new LiquidStack(Liquids.oil, 0.6f);
            requirements(Category.crafting, with(Items.lead, 80, Items.silicon, 70));
        }};

        smallHeatRouter = new HeatConductor("small-heat-router") {{
            localizedName = "Small Heat Router";
            description = "A smaller Heat Router that takes up less space!";
            size = 1; regionRotated1 = 1; splitHeat = true; researchCostMultiplier = 10;
            requirements(Category.crafting, with(Items.tungsten, 3, Items.graphite, 3));
        }};

        esterificationChamber = new GenericCrafter("esterification-chamber") {{
            localizedName = "Esterification Chamber";
            description = "A better Electrolyzer that works more efficiently.";
            size = 4; researchCostMultiplier = 1.2f; craftTime = 5;
            rotate = true; invertFlip = true; liquidCapacity = 75;
            consumePower(1.6f); consumeLiquid(Liquids.water, 0.1667f);
            outputLiquids = LiquidStack.with(Liquids.ozone, 0.07f, Liquids.hydrogen, 0.1f);
            liquidOutputDirections = new int[]{1, 3};
            ambientSound = Sounds.loopElectricHum; ambientSoundVolume = 0.1f;
            regionRotated1 = 3;
            requirements(Category.crafting, with(Items.silicon, 90, Items.graphite, 70, Items.beryllium, 165, Items.tungsten, 110));
        }};

        siliconForge = new GenericCrafter("silicon-forge") {{
            localizedName = "Silicon Forge";
            description = "A better silicon smelter that is more cost-efficient.";
            size = 3; hasPower = true; hasItems = true; hasLiquids = false;
            craftTime = 60; itemCapacity = 30; health = 320;
            consumePower(1f); consumeItems(with(Items.coal, 3, Items.sand, 6));
            outputItem = new ItemStack(Items.silicon, 3);
            requirements(Category.crafting, with(Items.copper, 90, Items.lead, 75, Items.graphite, 25, Items.silicon, 15));
        }};

        siliconArcForge = new GenericCrafter("silicon-arc-forge") {{
            localizedName = "Silicon Arc Forge";
            description = "A better version of the silicon arc furnace that creates silicon more efficiently.";
            size = 4; hasPower = true; hasLiquids = false; itemCapacity = 40;
            craftTime = 60; craftEffect = Fx.none; fogRadius = 5;
            outputItem = new ItemStack(Items.silicon, 6);
            consumePower(7f); consumeItems(with(Items.graphite, 1, Items.sand, 4));
            ambientSound = Sounds.loopSmelter; ambientSoundVolume = 0.14f;
            requirements(Category.crafting, with(Items.beryllium, 140, Items.graphite, 150));
        }};

        graphiteForge = new GenericCrafter("graphite-forge") {{
            localizedName = "Graphite Forge";
            description = "A better graphite press that makes Graphite way faster and more efficiently.";
            size = 4; itemCapacity = 40; hasItems = true; hasLiquids = true; hasPower = true;
            craftTime = 30; craftEffect = Fx.blastExplosion;
            outputItem = new ItemStack(Items.graphite, 4);
            consumePower(2.5f); consumeItem(Items.coal, 4); consumeLiquid(Liquids.water, 0.2f);
            requirements(Category.crafting, with(Items.lead, 160, Items.graphite, 75, Items.silicon, 45, Items.titanium, 140, Items.plastanium, 15));
        }};

        powderizer = new GenericCrafter("powderizer") {{
            localizedName = "Powderizer";
            description = "A better and bigger version of the pulverizer.";
            size = 2; craftTime = 30; craftEffect = Fx.pulverize;
            outputItem = new ItemStack(Items.sand, 2);
            consumePower(0.75f); consumeItem(Items.scrap, 2);
            ambientSound = Sounds.loopGrind; ambientSoundVolume = 0.4f;
            requirements(Category.crafting, with(Items.copper, 70, Items.lead, 60));
        }};

        greenhouse = new AttributeCrafter("greenhouse") {{
            localizedName = "Greenhouse";
            description = "A better cultivator that creates a lot more spore pods.";
            size = 3; hasPower = true; hasItems = true; hasLiquids = false;
            attribute = Attribute.spores; maxBoost = 1; minEfficiency = 1;
            baseEfficiency = 1; boostScale = 0.5556f; envRequired = 8;
            craftTime = 50; craftEffect = Fx.smeltsmoke; updateEffect = Fx.pulverize;
            consumePower(1.5f); consumeLiquid(Liquids.water, 0.5f);
            outputItem = new ItemStack(Items.sporePod, 2);
            requirements(Category.production, with(Items.copper, 75, Items.lead, 75, Items.silicon, 30));
        }};

        carbideBasin = new HeatCrafter("carbide-basin") {{
            localizedName = "Carbide Basin";
            description = "A better Carbide Crucible that works more efficiently.";
            size = 4; itemCapacity = 30; hasPower = true; hasItems = true;
            craftTime = 90; craftEffect = Fx.none; heatRequirement = 12;
            outputItem = new ItemStack(Items.carbide, 1);
            consumePower(2.6f); consumeItems(with(Items.tungsten, 2, Items.graphite, 3));
            ambientSound = Sounds.loopSmelter; ambientSoundVolume = 0.11f;
            requirements(Category.crafting, with(Items.tungsten, 160, Items.thorium, 190, Items.oxide, 90));
        }};

        primaryFactory = new UnitFactory("primary-factory") {{
            localizedName = "Primary Factory";
            description = "Creates small and light units which may be upgraded using Reassembly Chambers.";
            size = 3;
            consumePower(1.2f);
            plans = arc.struct.Seq.with(
                new UnitPlan(FRT1Units.seed, 2400f, with(Items.lead, 30, Items.silicon, 30)),
                new UnitPlan(FRT1Units.lancerDrone, 1200f, with(Items.lead, 15, Items.silicon, 15)),
                new UnitPlan(FRT1Units.mela, 3000f, with(Items.silicon, 30, Items.metaglass, 45)),
                new UnitPlan(FRT1Units.apis, 1200f, with(Items.lead, 15, Items.silicon, 15)),
                new UnitPlan(FRT1Units.alba, 3600f, with(Items.silicon, 25, Items.metaglass, 30, Items.titanium, 25)),
                new UnitPlan(FRT1Units.annax, 2400f, with(Items.scrap, 40, Items.silicon, 30, Items.lead, 20)),
                new UnitPlan(FRT1Units.sambuca, 3000f, with(Items.silicon, 40, Items.lead, 30, Items.titanium, 20))
            );
            requirements(Category.units, with(Items.copper, 90, Items.lead, 110, Items.silicon, 40, Items.metaglass, 60));
        }};

        basicReassembly = new Reconstructor("basic-reassembly-chamber") {{
            localizedName = "Basic Reassembly Chamber";
            description = "Upgrades units created in the Basic Factory to the next tier. Units produced by this Reassembly Chamber may be further upgraded.";
            health = 1550;
            size = 5;
            constructTime = 3600f;
            consumePower(0.8f);
            consumeItems(with(Items.silicon, 300, Items.graphite, 120, Items.titanium, 180, Items.metaglass, 100));
            addUpgrade(FRT1Units.seed, FRT2Units.sapling);
            addUpgrade(FRT1Units.lancerDrone, FRT2Units.alopex);
            addUpgrade(FRT1Units.mela, FRT2Units.cromis);
            addUpgrade(FRT1Units.apis, FRT2Units.procer);
            addUpgrade(FRT1Units.alba, FRT2Units.arvens);
            addUpgrade(FRT1Units.annax, FRT2Units.scofra);
            addUpgrade(FRT1Units.sambuca, FRT2Units.scorpio);
            requirements(Category.units, with(Items.copper, 500, Items.lead, 800, Items.silicon, 700, Items.titanium, 800));
        }};

        advancedReassembly = new Reconstructor("advanced-reassembly-chamber") {{
            localizedName = "Advanced Reassembly Chamber";
            description = "Upgrades units to the 3rd tier. These upgraded units may be upgraded a final time.";
            health = 3400;
            size = 9;
            constructTime = 6000f;
            consumePower(13.3333333333333f);
            consumeLiquid(FRLiquids.neutronFluid, 0.4f);
            consumeItems(with(Items.silicon, 1000, Items.titanium, 900, Items.plastanium, 720));
            addUpgrade(FRT2Units.sapling, FRT3Units.plant);
            addUpgrade(FRT2Units.alopex, FRT3Units.kestrel);
            addUpgrade(FRT2Units.cromis, FRT3Units.arnux);
            addUpgrade(FRT2Units.procer, FRT3Units.ducalis);
            addUpgrade(FRT2Units.arvens, FRT3Units.aestiva);
            addUpgrade(FRT2Units.scofra, FRT3Units.auratus);
            addUpgrade(FRT2Units.scorpio, FRT3Units.springald);
            requirements(Category.units, with(Items.lead, 2200, Items.titanium, 2100, Items.thorium, 900, Items.plastanium, 520, Items.phaseFabric, 640));
        }};

        progressiveAssembly = new Reconstructor("progressive-reassembly-chamber") {{
            localizedName = "Progressive Assembly Chamber";
            description = "Upgrades units to the 4th and final tier.";
            health = 7200;
            size = 13;
            constructTime = 21600f;
            consumePower(30f);
            consumeLiquid(FRLiquids.neutronFluid, 0.8f);
            consumeItems(with(Items.silicon, 800, FRItems.livingSteel, 600, Items.plastanium, 660, Items.surgeAlloy, 550, Items.phaseFabric, 450));
            addUpgrade(FRT3Units.plant, FRMothershipUnits.corax);
            addUpgrade(FRT3Units.kestrel, FRMothershipUnits.strahl);
            addUpgrade(FRT3Units.arnux, FRMothershipUnits.japonica);
            addUpgrade(FRT3Units.ducalis, FRMothershipUnits.hive);
            addUpgrade(FRT3Units.aestiva, FRMothershipUnits.altaic);
            addUpgrade(FRT3Units.auratus, FRMothershipUnits.lycosid);
            addUpgrade(FRT3Units.springald, FRMothershipUnits.onager);
            requirements(Category.units, with(FRItems.livingSteel, 2000, Items.thorium, 1500, Items.silicon, 3800, Items.plastanium, 700, Items.phaseFabric, 700, Items.surgeAlloy, 920));
        }};

        ascendedFactory = new FRAscendedUnitFactory("ascended-factory") {{
            localizedName = "Ascended Factory";
            description = "An advanced unit fabricator capable of constructing mothership-class units and transcendent units using the most advanced materials.";
            size = 5;
            consumePower(30f);
            consumeLiquid(FRLiquids.neutronFluid, 0.3f);
            plans = arc.struct.Seq.with(
                new UnitPlan(FRMothershipUnits.toruct, 4800f, with(FRItems.livingSteel, 100, Items.silicon, 80, Items.titanium, 60, Items.phaseFabric, 30)),
                new UnitPlan(FRMothershipUnits.reduct, 4800f, with(FRItems.livingSteel, 100, Items.silicon, 80, Items.titanium, 60, Items.phaseFabric, 30)),
                new UnitPlan(FRMothershipUnits.hiveAttack, 3600f, with(FRItems.livingSteel, 80, Items.silicon, 60, Items.plastanium, 40)),
                new UnitPlan(FRMothershipUnits.lycosid, 12000f, with(Items.silicon, 400, FRItems.livingSteel, 300, Items.plastanium, 200, Items.surgeAlloy, 150, Items.phaseFabric, 120)),
                new UnitPlan(FRMothershipUnits.strahl, 12000f, with(Items.silicon, 400, FRItems.livingSteel, 300, Items.plastanium, 200, Items.surgeAlloy, 150, Items.phaseFabric, 120)),
                new UnitPlan(FRMothershipUnits.onager, 15000f, with(Items.silicon, 500, FRItems.livingSteel, 400, Items.plastanium, 250, Items.surgeAlloy, 200, Items.phaseFabric, 150)),
                new UnitPlan(FRMothershipUnits.japonica, 15000f, with(Items.silicon, 500, FRItems.livingSteel, 400, Items.plastanium, 250, Items.surgeAlloy, 200, Items.phaseFabric, 150)),
                new UnitPlan(FRMothershipUnits.hive, 15000f, with(Items.silicon, 500, FRItems.livingSteel, 400, Items.plastanium, 250, Items.surgeAlloy, 200, Items.phaseFabric, 150)),
                new UnitPlan(FRMothershipUnits.corax, 15000f, with(Items.silicon, 500, FRItems.livingSteel, 400, Items.plastanium, 250, Items.surgeAlloy, 200, Items.phaseFabric, 150)),
                new UnitPlan(FRMothershipUnits.altaic, 15000f, with(Items.silicon, 500, FRItems.livingSteel, 400, Items.plastanium, 250, Items.surgeAlloy, 200, Items.phaseFabric, 150)),
                new UnitPlan(FRTranscendentUnits.mygale, 24000f, with(Items.silicon, 800, Items.surgeAlloy, 500, FRItems.nanoFabric, 300, FRItems.optiCrystal, 200, FRItems.energyCell, 200)),
                new UnitPlan(FRTranscendentUnits.scepter, 24000f, with(Items.silicon, 800, Items.surgeAlloy, 500, FRItems.nanoFabric, 300, FRItems.optiCrystal, 200, FRItems.energyCell, 200)),
                new UnitPlan(FRTranscendentUnits.mangonel, 30000f, with(Items.silicon, 1000, Items.surgeAlloy, 600, FRItems.nanoFabric, 400, FRItems.optiCrystal, 250, FRItems.energyCell, 250)),
                new UnitPlan(FRTranscendentUnits.thalass, 30000f, with(Items.silicon, 1000, Items.surgeAlloy, 600, FRItems.nanoFabric, 400, FRItems.optiCrystal, 250, FRItems.energyCell, 250)),
                new UnitPlan(FRTranscendentUnits.vex, 30000f, with(Items.silicon, 1000, Items.surgeAlloy, 600, FRItems.nanoFabric, 400, FRItems.optiCrystal, 250, FRItems.energyCell, 250)),
                new UnitPlan(FRTranscendentUnits.medusae, 30000f, with(Items.silicon, 1000, Items.surgeAlloy, 600, FRItems.nanoFabric, 400, FRItems.optiCrystal, 250, FRItems.energyCell, 250)),
                new UnitPlan(FRTranscendentUnits.nivosa, 30000f, with(Items.silicon, 1000, Items.surgeAlloy, 600, FRItems.nanoFabric, 400, FRItems.optiCrystal, 250, FRItems.energyCell, 250))
            );
            requirements(Category.units, with(FRItems.livingSteelHard, 3000, Items.surgeAlloy, 2000, Items.silicon, 4000, Items.plastanium, 1500, Items.phaseFabric, 1200, FRItems.cryogenicAlloy, 800));
        }};

        regenerator = new RepairTower("unit-repair-field") {{
            localizedName = "Regenerator";
            description = "A tower that continuously heals all allied units in its range. Requires Cryofluid.";
            size = 2;
            range = 100f;
            healAmount = 1.5f;
            consumePower(7f);
            consumeLiquid(mindustry.content.Liquids.cryofluid, 0.18f);
            requirements(Category.units, with(Items.graphite, 110, Items.silicon, 110, Items.titanium, 100));
        }};
    }
}
