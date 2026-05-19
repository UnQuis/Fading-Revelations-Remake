package fadingrevelations.content;

import arc.struct.ObjectMap;
import mindustry.content.TechTree;
import mindustry.ctype.UnlockableContent;
import mindustry.game.Objectives.Research;
import mindustry.type.ItemStack;

import static fadingrevelations.content.FREffectBlocks.*;
import static fadingrevelations.content.FRTurrets.*;
import static fadingrevelations.content.FRDistribution.*;
import static fadingrevelations.content.FRDrills.*;
import static fadingrevelations.content.FRWalls.*;
import static fadingrevelations.content.FRPowerNodes.*;
import static fadingrevelations.content.FRLiquidsBlocks.*;
import static fadingrevelations.content.FRGates.*;
import fadingrevelations.content.production.*;

public class FRFullTechTree {
    private static final ObjectMap<UnlockableContent, TechTree.TechNode> nodes = new ObjectMap<>();

    public static void load() {
        TechTree.TechNode root = TechTree.nodeRoot("fading-revelations", modGateMain, false, () -> {});
        nodes.put(modGateMain, root);

        // === BLOCKS GATE → overdrive/force/launch ===
        addBlock(modGateMain, modGateBlocks);
        addBlock(modGateBlocks, overdriveRelay);
        addBlock(overdriveRelay, overdriveBeacon);
        addBlock(overdriveBeacon, forceDome);
        addBlock(forceDome, forceField);
        addBlock(overdriveBeacon, bigLaunchPad);
        addBlock(bigLaunchPad, outpost);

        // === ITEMS GATE ===
        addBlock(modGateMain, modGateItems);
        addBlock(modGateItems, FRItems.livingSteel);
        addBlock(FRItems.livingSteel, FRItems.steelAlloy);
        addBlock(FRItems.steelAlloy, FRItems.fuelRod);
        addBlock(FRItems.fuelRod, FRItems.cryogenicGel);
        addBlock(FRItems.cryogenicGel, FRItems.igneousAlloy);
        addBlock(FRItems.igneousAlloy, FRItems.cryogenicAlloy);

        // === RESOURCES GATE → environment/ore blocks ===
        addBlock(modGateMain, modGateResources);
        addBlock(modGateResources, FREnvironment.steelSedimentation);
        addBlock(FREnvironment.steelSedimentation, FREnvironment.oreGraphite);

        // === LIQUIDS GATE → liquid handling ===
        addBlock(modGateMain, modGateLiquids);
        addBlock(modGateLiquids, steelTank);
        addBlock(steelTank, steelPump);
        addBlock(steelPump, plastaniumConduit);

        // === TURRETS GATE → item branch + power branch ===
        addBlock(modGateMain, modGateTurrets);
        addBlock(modGateTurrets, accel);
        addBlock(accel, bigSwarmer);
        addBlock(bigSwarmer, caats);
        addBlock(caats, corruptedCyclone);
        addBlock(corruptedCyclone, gattling);
        addBlock(gattling, ignitor);
        addBlock(ignitor, interitus);
        addBlock(modGateTurrets, uhlan);
        addBlock(uhlan, megaMeltdown);
        addBlock(megaMeltdown, kugelblitz);
        addBlock(kugelblitz, diffract);
        addBlock(diffract, cavalry);
        addBlock(cavalry, bigSegment);
        addBlock(bigSegment, bigScatter);
        addBlock(bigScatter, bigArc);
        addBlock(bigArc, zephyr);
        addBlock(zephyr, weave);
        addBlock(weave, sprunkler);

        // === DISTRIBUTION GATE ===
        addBlock(modGateMain, modGateDistribution);
        addBlock(modGateDistribution, titaniumRouter);
        addBlock(titaniumRouter, titaniumJunction);
        addBlock(titaniumJunction, titaniumDistributor);
        addBlock(titaniumDistributor, titaniumBridgeConveyor);

        // === AMMO GATE → ammo items ===
        addBlock(modGateMain, modGateAmmo);
        addBlock(modGateAmmo, FRItems.ammoLevel1);
        addBlock(FRItems.ammoLevel1, FRItems.ammoLevel2);
        addBlock(FRItems.ammoLevel2, FRItems.ammoLevel3);
        addBlock(FRItems.ammoLevel1, FRItems.healAmmo);
        addBlock(FRItems.ammoLevel2, FRItems.homingAmmo);
        addBlock(FRItems.ammoLevel3, FRItems.nanoAmmo);
        addBlock(FRItems.nanoAmmo, FRItems.nuke);

        // === DRILLS GATE ===
        addBlock(modGateMain, modGateDrills);
        addBlock(modGateDrills, tinyMechanicalDrill);
        addBlock(tinyMechanicalDrill, tinyPneumaticDrill);
        addBlock(tinyPneumaticDrill, tinyPlasmaBore);
        addBlock(tinyPlasmaBore, titaniumDrill);

        // === WALLS GATE ===
        addBlock(modGateMain, modGateWalls);
        addBlock(modGateWalls, steelAlloyWallSmall);
        addBlock(steelAlloyWallSmall, steelAlloyWallLarge);
        addBlock(steelAlloyWallLarge, livingSteelWall);
        addBlock(livingSteelWall, livingSteelWallLarge);

        // === POWER GATE ===
        addBlock(modGateMain, modGatePower);
        addBlock(modGatePower, reinforcedPowerNode);
        addBlock(reinforcedPowerNode, reinforcedLargePowerNode);
        addBlock(reinforcedLargePowerNode, powerReserve);
        addBlock(powerReserve, advancedSurgeTower);

        // === EFFECT GATE ===
        addBlock(modGateMain, modGateEffect);
        addBlock(modGateEffect, enhancedMendProjector);
        addBlock(enhancedMendProjector, darkMender);
        addBlock(darkMender, fastUnloader);
        addBlock(fastUnloader, miniOd);

        // === CRAFTERS GATE → all production blocks ===
        addBlock(modGateMain, modGateCrafters);

        // Basic Smelting
        addBlock(modGateCrafters, BasicMultismelter.block);
        addBlock(BasicMultismelter.block, SiliconForge.block);
        addBlock(SiliconForge.block, GraphiteForge.block);
        addBlock(GraphiteForge.block, BigPlastaniumPress.block);
        addBlock(BigPlastaniumPress.block, SurgeOvenBig.block);

        // Living Steel
        addBlock(modGateCrafters, LivingSteelForge.block);
        addBlock(LivingSteelForge.block, LivingSteelInfuser.block);
        addBlock(LivingSteelInfuser.block, LivingSteelHardener.block);
        addBlock(LivingSteelHardener.block, LivingSteelHardeningForge.block);

        // Alloys
        addBlock(SurgeOvenBig.block, AlloyCrafter.block);
        addBlock(AlloyCrafter.block, AmalgamSmelter.block);
        addBlock(AmalgamSmelter.block, AmalgamForge.block);
        addBlock(AmalgamForge.block, CryogenicGelMixer.block);
        addBlock(CryogenicGelMixer.block, CryogenicAlloyAssembler.block);

        // Advanced Crafting
        addBlock(AmalgamForge.block, SiliconArcForge.block);
        addBlock(SiliconArcForge.block, BigPhaseWeaver.block);
        addBlock(BigPhaseWeaver.block, PhaseManufacturer.block);
        addBlock(PhaseManufacturer.block, InducedKiln.block);
        addBlock(InducedKiln.block, SurgeMelter.block);
        addBlock(SurgeMelter.block, InvertedPulverizer.block);
        addBlock(InvertedPulverizer.block, Powderizer.block);

        // Ammo Crafters
        addBlock(modGateCrafters, AmmoCrafter1.block);
        addBlock(AmmoCrafter1.block, AmmoCrafter2.block);
        addBlock(AmmoCrafter2.block, AmmoCrafter3.block);
        addBlock(AmmoCrafter1.block, HealAmmoCrafter.block);
        addBlock(AmmoCrafter2.block, HomingAmmoCrafter.block);
        addBlock(AmmoCrafter3.block, NanoAmmoCrafter.block);
        addBlock(NanoAmmoCrafter.block, UraniumrodCrafter.block);
        addBlock(UraniumrodCrafter.block, NukeCrafter.block);

        // Liquid Processing
        addBlock(modGateCrafters, AcidVat.block);
        addBlock(AcidVat.block, AcidEmulsifier.block);
        addBlock(AcidEmulsifier.block, Dissolver.block);
        addBlock(Dissolver.block, AdvancedSeparator.block);
        addBlock(AdvancedSeparator.block, AdvancedCoalCentrifuge.block);
        addBlock(AdvancedCoalCentrifuge.block, EnhancedPyratiteMixer.block);
        addBlock(EnhancedPyratiteMixer.block, EnhancedBlastMixer.block);

        // Atmospheric
        addBlock(modGateCrafters, AtmosphericExtractor.block);
        addBlock(AtmosphericExtractor.block, AtmosphericHeatConcentrator.block);
        addBlock(AtmosphericHeatConcentrator.block, AdvancedCryofluidMixer.block);
        addBlock(AdvancedCryofluidMixer.block, NeutronBlender.block);

        // Chemical
        addBlock(CryogenicAlloyAssembler.block, EsterificationChamber.block);
        addBlock(EsterificationChamber.block, CyanogenFuser.block);
        addBlock(CyanogenFuser.block, CorrosionChamber.block);
        addBlock(CorrosionChamber.block, CarbideBasin.block);

        // Spores
        addBlock(modGateCrafters, SporeCrusher.block);
        addBlock(SporeCrusher.block, Greenhouse.block);

        // Heat
        addBlock(modGateCrafters, HeatDiverter.block);
        addBlock(HeatDiverter.block, SmallHeatRouter.block);

        // Liquid Steel
        addBlock(LivingSteelHardeningForge.block, LivingSteelLiquifier.block);
        addBlock(LivingSteelLiquifier.block, LivingSteelLiquifyingForge.block);

        // Power Generation
        addBlock(modGateCrafters, SteamCondenser.block);
        addBlock(SteamCondenser.block, SteamTurbine.block);
        addBlock(SteamTurbine.block, LsGen.block);
        addBlock(LsGen.block, SlagGenerator.block);
        addBlock(SlagGenerator.block, PyratiteGenerator.block);
        addBlock(PyratiteGenerator.block, SlagReactor.block);
        addBlock(SlagReactor.block, SteelReactor.block);
        addBlock(SteelReactor.block, UraniumReactor.block);

        // Solar
        addBlock(modGateCrafters, SolarArray.block);
        addBlock(SolarArray.block, AdvancedSolarPanel.block);
        addBlock(AdvancedSolarPanel.block, TinyThermalGen.block);

        // Utilities
        addBlock(modGateCrafters, TitaniumPanel.block);
        addBlock(TitaniumPanel.block, TurbineConcentrator.block);
        addBlock(TurbineConcentrator.block, AdvancedWaterExtractor.block);

        // === FUTURE GATES (empty, for later use) ===
        addBlock(modGateMain, modGateCores);
        addBlock(modGateMain, modGateUnits);
        addBlock(modGateMain, modGateFactories);
        addBlock(modGateMain, modGateFlying);
        addBlock(modGateMain, modGateLegs);
        addBlock(modGateMain, modGateNaval);
        addBlock(modGateMain, modGateCoreUnits);
    }

    private static void addBlock(UnlockableContent parent, UnlockableContent child) {
        TechTree.TechNode parentNode = nodes.get(parent);
        if (parentNode == null) return;

        TechTree.TechNode node = new TechTree.TechNode(parentNode, child, child.researchRequirements());
        nodes.put(child, node);

        node.objectives.add(new Research(parent));
    }
}
