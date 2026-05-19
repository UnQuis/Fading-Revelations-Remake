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
        // Root - Main Gate
        TechTree.TechNode root = TechTree.nodeRoot("fading-revelations", modGateMain, false, () -> {});
        nodes.put(modGateMain, root);

        // === OVERDRIVE CHAIN FROM GATES ===
        addBlock(modGateMain, modGateBlocks);
        addBlock(modGateBlocks, overdriveRelay);
        addBlock(overdriveRelay, overdriveBeacon);
        addBlock(overdriveBeacon, forceDome);
        addBlock(forceDome, forceField);
        
        // Effect branch
        addBlock(overdriveBeacon, modGateEffect);
        addBlock(modGateEffect, enhancedMendProjector);
        addBlock(modGateEffect, darkMender);
        addBlock(modGateEffect, fastUnloader);
        addBlock(overdriveBeacon, bigLaunchPad);
        addBlock(bigLaunchPad, outpost);
        addBlock(modGateEffect, miniOd);

        // === ITEMS ===
        addBlock(modGateMain, modGateItems);
        addBlock(modGateItems, modGateResources);
        addBlock(modGateResources, modGateLiquids);
        addBlock(modGateLiquids, steelTank);
        addBlock(steelTank, steelPump);
        addBlock(steelPump, plastaniumConduit);

        // === TURRETS ===
        addBlock(miniOd, modGateTurrets);
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

        // === DISTRIBUTION ===
        addBlock(miniOd, modGateDistribution);
        addBlock(modGateDistribution, titaniumRouter);
        addBlock(titaniumRouter, titaniumJunction);
        addBlock(titaniumJunction, titaniumDistributor);
        addBlock(titaniumDistributor, titaniumBridgeConveyor);
        addBlock(titaniumBridgeConveyor, modGateAmmo);
        addBlock(modGateAmmo, modGateCrafters);

        // === DRILLS ===
        addBlock(miniOd, modGateDrills);
        addBlock(modGateDrills, tinyMechanicalDrill);
        addBlock(tinyMechanicalDrill, tinyPneumaticDrill);
        addBlock(tinyPneumaticDrill, tinyPlasmaBore);
        addBlock(tinyPlasmaBore, titaniumDrill);

        // === WALLS ===
        addBlock(miniOd, modGateWalls);
        addBlock(modGateWalls, steelAlloyWallSmall);
        addBlock(steelAlloyWallSmall, steelAlloyWallLarge);
        addBlock(steelAlloyWallLarge, livingSteelWall);
        addBlock(livingSteelWall, livingSteelWallLarge);

        // === POWER ===
        addBlock(miniOd, modGatePower);
        addBlock(modGatePower, reinforcedPowerNode);
        addBlock(reinforcedPowerNode, reinforcedLargePowerNode);
        addBlock(reinforcedLargePowerNode, powerReserve);
        addBlock(powerReserve, advancedSurgeTower);

        // ========================
        // === PRODUCTION CHAIN ===
        // ========================
        // === Basic Smelting ===
addBlock(modGateCrafters, BasicMultismelter.block);
        addBlock(BasicMultismelter.block, SiliconForge.block);
        addBlock(SiliconForge.block, GraphiteForge.block);
        addBlock(GraphiteForge.block, BigPlastaniumPress.block);
        addBlock(BigPlastaniumPress.block, SurgeOvenBig.block);
        addBlock(modGateCrafters, LivingSteelForge.block);
        addBlock(LivingSteelForge.block, LivingSteelInfuser.block);
        addBlock(LivingSteelInfuser.block, LivingSteelHardener.block);
        addBlock(LivingSteelHardener.block, LivingSteelHardeningForge.block);
        addBlock(SurgeOvenBig.block, AlloyCrafter.block);
        addBlock(AlloyCrafter.block, AmalgamSmelter.block);
        addBlock(AmalgamSmelter.block, AmalgamForge.block);
        addBlock(AmalgamForge.block, CryogenicGelMixer.block);
        addBlock(CryogenicGelMixer.block, CryogenicAlloyAssembler.block);
        addBlock(AmalgamForge.block, SiliconArcForge.block);
        addBlock(SiliconArcForge.block, BigPhaseWeaver.block);
        addBlock(BigPhaseWeaver.block, PhaseManufacturer.block);
        addBlock(PhaseManufacturer.block, InducedKiln.block);
        addBlock(InducedKiln.block, SurgeMelter.block);
        addBlock(SurgeMelter.block, InvertedPulverizer.block);
        addBlock(InvertedPulverizer.block, Powderizer.block);
        addBlock(modGateCrafters, AmmoCrafter1.block);
        addBlock(AmmoCrafter1.block, AmmoCrafter2.block);
        addBlock(AmmoCrafter2.block, AmmoCrafter3.block);
        addBlock(AmmoCrafter1.block, HealAmmoCrafter.block);
        addBlock(AmmoCrafter2.block, HomingAmmoCrafter.block);
        addBlock(AmmoCrafter3.block, NanoAmmoCrafter.block);
        addBlock(NanoAmmoCrafter.block, UraniumrodCrafter.block);
        addBlock(UraniumrodCrafter.block, NukeCrafter.block);
        addBlock(modGateCrafters, AcidVat.block);
        addBlock(AcidVat.block, AcidEmulsifier.block);
        addBlock(AcidEmulsifier.block, Dissolver.block);
        addBlock(Dissolver.block, AdvancedSeparator.block);
        addBlock(AdvancedSeparator.block, AdvancedCoalCentrifuge.block);
        addBlock(AdvancedCoalCentrifuge.block, EnhancedPyratiteMixer.block);
        addBlock(EnhancedPyratiteMixer.block, EnhancedBlastMixer.block);
        addBlock(modGateCrafters, AtmosphericExtractor.block);
        addBlock(AtmosphericExtractor.block, AtmosphericHeatConcentrator.block);
        addBlock(AtmosphericHeatConcentrator.block, AdvancedCryofluidMixer.block);
        addBlock(AdvancedCryofluidMixer.block, NeutronBlender.block);
        addBlock(CryogenicAlloyAssembler.block, EsterificationChamber.block);
        addBlock(EsterificationChamber.block, CyanogenFuser.block);
        addBlock(CyanogenFuser.block, CorrosionChamber.block);
        addBlock(CorrosionChamber.block, CarbideBasin.block);
        addBlock(modGateCrafters, SporeCrusher.block);
        addBlock(SporeCrusher.block, Greenhouse.block);
        addBlock(modGateCrafters, HeatDiverter.block);
        addBlock(HeatDiverter.block, SmallHeatRouter.block);
        addBlock(LivingSteelHardeningForge.block, LivingSteelLiquifier.block);
        addBlock(LivingSteelLiquifier.block, LivingSteelLiquifyingForge.block);
        addBlock(modGateCrafters, SteamCondenser.block);
        addBlock(SteamCondenser.block, SteamTurbine.block);
        addBlock(SteamTurbine.block, LsGen.block);
        addBlock(LsGen.block, SlagGenerator.block);
        addBlock(SlagGenerator.block, PyratiteGenerator.block);
        addBlock(PyratiteGenerator.block, SlagReactor.block);
        addBlock(SlagReactor.block, SteelReactor.block);
        addBlock(SteelReactor.block, UraniumReactor.block);
        addBlock(modGateCrafters, SolarArray.block);
        addBlock(SolarArray.block, AdvancedSolarPanel.block);
        addBlock(AdvancedSolarPanel.block, TinyThermalGen.block);
        addBlock(modGateCrafters, TitaniumPanel.block);
        addBlock(TitaniumPanel.block, TurbineConcentrator.block);
        addBlock(TurbineConcentrator.block, AdvancedWaterExtractor.block);
    }

    private static void addBlock(UnlockableContent parent, UnlockableContent child) {
        TechTree.TechNode parentNode = nodes.get(parent);
        if (parentNode == null) return;

        TechTree.TechNode node = new TechTree.TechNode(parentNode, child, child.researchRequirements());
        nodes.put(child, node);

        node.objectives.add(new Research(parent));
    }
}