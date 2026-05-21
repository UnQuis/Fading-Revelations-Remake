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
import static fadingrevelations.content.FRProduction.*;
import fadingrevelations.content.FRItems;
import fadingrevelations.content.FREnvironment;
import static mindustry.content.Blocks.*;

public class FRFullTechTree {
    private static final ObjectMap<UnlockableContent, TechTree.TechNode> nodes = new ObjectMap<>();

    public static void load() {
        TechTree.TechNode root = TechTree.nodeRoot("fading-revelations", modGateMain, false, () -> {});
        nodes.put(modGateMain, root);

        
        addBlock(modGateMain, modGateBlocks);

        
        addBlock(modGateBlocks, modGateLiquids);
        addBlock(modGateLiquids, steelTank);
        addBlock(steelTank, steelPump);
        addBlock(steelPump, plastaniumConduit);

        
        addBlock(modGateBlocks, modGateDistribution);
        addBlock(modGateDistribution, titaniumJunction);
        addBlock(titaniumJunction, titaniumRouter);
        addBlock(titaniumRouter, titaniumBridgeConveyor);
        addBlock(titaniumRouter, titaniumDistributor);
        addBlock(titaniumBridgeConveyor, surgeAlloyConveyor);
        addBlock(surgeAlloyConveyor, surgeBridgeConveyor);
        addBlock(surgeAlloyConveyor, amalgamConveyor);
        addBlock(amalgamConveyor, fusionConveyor);
        addBlock(titaniumRouter, kineticRouter);
        addBlock(titaniumJunction, kineticJunction);
        addBlock(titaniumDistributor, kineticDistributor);
        addBlock(surgeBridgeConveyor, bioConveyor);
        addBlock(bioConveyor, bioBridgeConveyor);
        addBlock(titaniumRouter, bioRouter);
        addBlock(titaniumJunction, bioJunction);
        addBlock(titaniumDistributor, bioDistributor);

        
        
        addBlock(modGateBlocks, modGateDrills);
        addBlock(modGateDrills, tinyMechanicalDrill);
        addBlock(tinyMechanicalDrill, tinyPneumaticDrill);
        addBlock(tinyPneumaticDrill, titaniumDrill);
        addBlock(titaniumDrill, compactLaserDrill);
        addBlock(compactLaserDrill, hyperDrill);
        addBlock(hyperDrill, cosmicDrill);
        addBlock(cosmicDrill, omniDrill);
        
        addBlock(modGateDrills, groundGrinder);
        addBlock(groundGrinder, groundMiller);
        addBlock(groundMiller, groundCrusher);
        addBlock(groundCrusher, oilBore);
        
        addToNode(cliffCrusher, () -> node(cliffGrinder, () -> node(cliffMiller)));
        addToNode(plasmaBore, () -> { node(tinyPlasmaBore); node(tungstenBore); });
        addToNode(ventCondenser, () -> node(ventConcentrator));

        
        addBlock(modGateBlocks, modGateWalls);
        
        addBlock(modGateWalls, livingSteelWall);
        addBlock(livingSteelWall, livingSteelWallLarge);
        addBlock(livingSteelWallLarge, steelAlloyWallSmall);
        addBlock(steelAlloyWallSmall, steelAlloyWallLarge);
        
        addBlock(modGateWalls, copperWall2);
        addBlock(copperWall2, copperWall3);
        addBlock(copperWall3, titaniumWall2);
        addBlock(titaniumWall2, titaniumWall3);
        addBlock(titaniumWall2, thoriumWall2);
        addBlock(thoriumWall2, thoriumWall3);
        addBlock(titaniumWall2, plastaniumWall2);
        addBlock(plastaniumWall2, plastaniumWall3);
        addBlock(thoriumWall2, surgeAlloyWall2);
        addBlock(surgeAlloyWall2, surgeAlloyWall3);
        addBlock(surgeAlloyWall2, phaseFabricWall2);
        addBlock(phaseFabricWall2, phaseFabricWall3);
        addBlock(phaseFabricWall3, nanoOpticWall);
        addBlock(nanoOpticWall, nanoOpticWallLarge);
        addBlock(nanoOpticWallLarge, nanoOpticWallHuge);
        addBlock(nanoOpticWallHuge, nanoOpticWallGigantic);
        addBlock(titaniumWall2, armoredDoor);
        addBlock(armoredDoor, doorHuge);
        addBlock(doorHuge, doorGigantic);
        
        addToNode(shieldedWall, () -> node(armoredWall));
        addToNode(berylliumWallLarge, () -> node(berylliumWallHuge, () -> node(berylliumWallGigantic)));
        addToNode(tungstenWallLarge, () -> node(tungstenWallHuge, () -> node(tungstenWallGigantic)));
        addToNode(carbideWallLarge, () -> node(carbideWallHuge, () -> node(carbideWallGigantic)));
        addToNode(reinforcedSurgeWallLarge, () -> node(reinforcedSurgeWallHuge, () -> node(reinforcedSurgeWallGigantic)));

        
        addBlock(modGateBlocks, modGatePower);

        
        addBlock(modGatePower, reinforcedPowerNode);
        addBlock(reinforcedPowerNode, reinforcedLargePowerNode);
        addBlock(reinforcedLargePowerNode, powerReserve);
        addBlock(reinforcedLargePowerNode, advancedSurgeTower);

        
        addBlock(modGatePower, tinyThermalGen);
        addBlock(tinyThermalGen, titaniumPanel);
        addBlock(titaniumPanel, advancedSolarPanel);
        addBlock(advancedSolarPanel, solarArray);
        addBlock(tinyThermalGen, steamTurbine);
        addAdditionalObjectives(steamTurbine, steamGenerator, FRItems.livingSteel);
        addBlock(modGatePower, slagGenerator);
        addBlock(slagGenerator, pyratiteGenerator);
        addBlock(advancedSolarPanel, uraniumReactor);
        addBlock(uraniumReactor, steelReactor);
        addBlock(modGatePower, lsGen);

        
        
        addBlock(modGateBlocks, modGateEffect);
        addBlock(modGateEffect, miniOd);
        addBlock(miniOd, enhancedMendProjector);
        addBlock(enhancedMendProjector, darkMender);
        addBlock(darkMender, forceDome);
        addBlock(forceDome, forceField);
        addBlock(modGateEffect, overdriveRelay);
        addBlock(overdriveRelay, overdriveBeacon);
        addBlock(modGateEffect, fastUnloader);
        addBlock(fastUnloader, tinyMd);
        addBlock(tinyMd, massAccelerator);
        addBlock(modGateEffect, depository);
        addBlock(depository, FRDistribution.frReinforcedVault);
        addBlock(FRDistribution.frReinforcedVault, FRDistribution.frQuantumVault);
        addBlock(miniOd, constructionPylon);

        
        addBlock(modGateBlocks, modGateTurrets);

        
        addBlock(modGateTurrets, trio);
        addBlock(trio, sear);
        addBlock(sear, sunflare);
        addBlock(sear, bigSwarmer);
        addBlock(bigSwarmer, corruptedCyclone);
        addBlock(bigSwarmer, gattling);
        addBlock(gattling, lightningChaingun);
        addBlock(trio, shotgun);
        addBlock(shotgun, sniper);
        addBlock(shotgun, upgradeTurret);
        addBlock(shotgun, oreTurret);
        addBlock(oreTurret, mineLauncher);
        addBlock(mineLauncher, mortar);
        addBlock(mineLauncher, missileBattery);
        addBlock(missileBattery, interitus);
        addBlock(missileBattery, missileSilo);
        addBlock(sniper, ringTurret);
        addBlock(ringTurret, ignitor);
        addBlock(modGateTurrets, caats);

        
        addBlock(modGateTurrets, zephyr);
        addBlock(zephyr, weave);
        addBlock(zephyr, sprunkler);
        addBlock(sprunkler, batter);

        
        addBlock(modGateTurrets, airArc);
        addBlock(airArc, uhlan);
        addBlock(uhlan, kugelblitz);
        addBlock(kugelblitz, statusWave);
        addBlock(kugelblitz, cavalry);
        addBlock(airArc, bigArc);
        addBlock(bigArc, bigParallax);
        addBlock(bigArc, bigSegment);
        addBlock(uhlan, accel);
        addBlock(accel, absole);
        addBlock(absole, megaMeltdown);
        
        addBlock(ringTurret, diffract);
        addBlock(oreTurret, bigScatter);

        
        addBlock(modGateBlocks, modGateCores);
        addBlock(modGateCores, coreLevel4);
        addBlock(coreLevel4, coreLevel5);
        addBlock(coreLevel5, mainCore);
        addBlock(mainCore, corePrime);

        
        
        addBlock(modGateBlocks, modGateCrafters);

        
        
        
        
        
        addBlock(modGateCrafters, steamCondenser);
        addBlock(steamCondenser, dissolver);
        addBlock(dissolver, acidVat);
        addBlock(acidVat, acidEmulsifier);
        addBlock(dissolver, advancedWaterExtractor);
        addBlock(advancedWaterExtractor, advancedCryofluidMixer);
        addBlock(advancedWaterExtractor, neutronBlender);

        
        
        
        
        addBlock(modGateCrafters, livingSteelInfuser);
        addBlock(livingSteelInfuser, livingSteelForge);
        addBlock(livingSteelInfuser, livingSteelLiquifier);
        addBlock(livingSteelLiquifier, livingSteelLiquifyingForge);
        addBlock(livingSteelLiquifier, livingSteelHardener);
        addBlock(livingSteelHardener, livingSteelHardeningForge);

        
        
        





        
        
        
        
        
        
        
        
        addBlock(modGateCrafters, invertedPulverizer);
        addBlock(invertedPulverizer, powderizer);
        addBlock(powderizer, inducedKiln);
        addBlock(inducedKiln, siliconForge);
        addBlock(siliconForge, basicMultismelter);
        addBlock(inducedKiln, greenhouse);
        addBlock(greenhouse, sporeCrusher);
        addBlock(greenhouse, enhancedPyratiteMixer);
        addBlock(enhancedPyratiteMixer, enhancedBlastMixer);
        addBlock(enhancedBlastMixer, graphiteForge);
        addBlock(graphiteForge, advancedCoalCentrifuge);
        addBlock(advancedCoalCentrifuge, bigPlastaniumPress);
        addBlock(advancedCoalCentrifuge, surgeOvenBig);
        addBlock(bigPlastaniumPress, bigPhaseWeaver);
        addBlock(bigPlastaniumPress, uraniumrodCrafter);
        addBlock(bigPhaseWeaver, advancedSeparator);
        addBlock(surgeOvenBig, amalgamSmelter);
        addBlock(surgeOvenBig, cryogenicGelMixer);
        addBlock(amalgamSmelter, amalgamForge);
        addBlock(cryogenicGelMixer, alloyCrafter);
        addBlock(alloyCrafter, cryogenicAlloyAssembler);
        addBlock(cryogenicAlloyAssembler, crystalSynthesizer);
        addBlock(crystalSynthesizer, crystalSynthesisArray);
        addBlock(crystalSynthesizer, cellFabricator);
        addBlock(cellFabricator, cellFabricationForge);
        addBlock(cellFabricator, nanoWeaver);
        addBlock(nanoWeaver, nanoWeavingForge);
        addBlock(greenhouse, bioRefinery);
        addBlock(bioRefinery, bioRefineryForge);

        
        addToNode(siliconArcFurnace, () -> node(siliconArcForge));
        addToNode(surgeCrucible, () -> node(surgeMelter));
        addToNode(phaseSynthesizer, () -> node(phaseManufacturer));
        addToNode(atmosphericConcentrator, () -> node(atmosphericExtractor));
        addToNode(electricHeater, () -> node(atmosphericHeatConcentrator));
        addToNode(electricHeater, () -> node(heatDiverter));
        addToNode(heatRouter, () -> node(smallHeatRouter));
        addToNode(electrolyzer, () -> node(esterificationChamber));
        addToNode(cyanogenSynthesizer, () -> node(cyanogenFuser));
        addToNode(oxidationChamber, () -> node(corrosionChamber));
        addToNode(carbideCrucible, () -> node(carbideBasin));
        addToNode(ventCondenser, () -> node(slagReactor));
        addToNode(turbineCondenser, () -> node(turbineConcentrator));

        
        addBlock(modGateMain, modGateItems);
        addBlock(modGateItems, FRItems.livingSteel);
        addBlock(FRItems.livingSteel, FRItems.livingSteelHard);
        addBlock(FRItems.livingSteel, FRItems.steelAlloy);
        addBlock(FRItems.steelAlloy, FRItems.fuelRod);
        addBlock(FRItems.fuelRod, FRItems.cryogenicGel);
        addBlock(FRItems.cryogenicGel, FRItems.igneousAlloy);
        addBlock(FRItems.igneousAlloy, FRItems.cryogenicAlloy);
        addBlock(FRItems.cryogenicAlloy, FRItems.optiCrystal);
        addBlock(FRItems.optiCrystal, FRItems.energyCell);
        addBlock(FRItems.energyCell, FRItems.nanoFabric);
        addBlock(FRItems.nanoFabric, FRItems.bioMatter);

        
        addBlock(modGateItems, FRItems.nuke);

        
        addBlock(modGateItems, modGateResources);
        addBlock(modGateResources, FREnvironment.steelSedimentation);
        addBlock(FREnvironment.steelSedimentation, FREnvironment.oreGraphite);

        
        addBlock(modGateMain, modGateUnits);
        addBlock(modGateUnits, modGateFactories);
        addBlock(modGateFactories, primaryFactory);
        addBlock(primaryFactory, basicReassembly);
        addBlock(basicReassembly, advancedReassembly);
        addBlock(advancedReassembly, progressiveAssembly);
        addBlock(progressiveAssembly, ascendedFactory);
        addBlock(modGateFactories, regenerator);
        addBlock(modGateUnits, modGateFlying);
        addBlock(modGateUnits, modGateLegs);
        addBlock(modGateUnits, modGateNaval);
        addBlock(modGateUnits, modGateCoreUnits);

        
        
        addBlock(modGateFlying, FRT1Units.heliaca);

        addBlock(modGateFlying, FRT1Units.apis);
        addBlock(FRT1Units.apis, FRT2Units.procer);
        addBlock(FRT2Units.procer, FRT3Units.ducalis);
        
        addBlock(modGateFlying, FRT1Units.seed);
        addBlock(FRT1Units.seed, FRT2Units.sapling);
        addBlock(FRT2Units.sapling, FRT3Units.plant);
        
        addBlock(modGateFlying, FRT1Units.lancerDrone);
        addBlock(FRT1Units.lancerDrone, FRT2Units.alopex);
        addBlock(FRT2Units.alopex, FRT3Units.kestrel);
        
        addBlock(modGateFlying, FRCerberianUnits.spark);
        addBlock(FRCerberianUnits.spark, FRCerberianUnits.vista);
        addBlock(FRCerberianUnits.vista, FRCerberianUnits.summit);
        addBlock(FRCerberianUnits.summit, FRCerberianUnits.penumbra);
        addBlock(FRCerberianUnits.penumbra, FRCerberianUnits.veil);
        
        addBlock(FRT3Units.kestrel, FRMothershipUnits.strahl);
        addBlock(FRT3Units.ducalis, FRMothershipUnits.hive);

        
        
        addBlock(modGateLegs, FRT1Units.annax);
        addBlock(FRT1Units.annax, FRT2Units.scofra);
        addBlock(FRT2Units.scofra, FRT3Units.auratus);
        
        addBlock(modGateLegs, FRT1Units.sambuca);
        addBlock(FRT1Units.sambuca, FRT2Units.scorpio);
        addBlock(FRT2Units.scorpio, FRT3Units.springald);
        
        addBlock(modGateLegs, FRCerberianUnits.straggle);
        addBlock(FRCerberianUnits.straggle, FRCerberianUnits.bayonet);
        addBlock(FRCerberianUnits.bayonet, FRCerberianUnits.hexathelid);
        addBlock(FRCerberianUnits.hexathelid, FRCerberianUnits.cudgel);
        addBlock(FRCerberianUnits.cudgel, FRCerberianUnits.citadel);
        addBlock(FRCerberianUnits.citadel, FRCerberianUnits.nephila);
        addBlock(FRCerberianUnits.nephila, FRCerberianUnits.curtulus);
        addBlock(FRCerberianUnits.curtulus, FRCerberianUnits.auctus);
        addBlock(FRCerberianUnits.auctus, FRCerberianUnits.baton);
        addBlock(FRCerberianUnits.baton, FRCerberianUnits.kaiser);
        addBlock(FRCerberianUnits.kaiser, FRCerberianUnits.setosus);
        addBlock(FRCerberianUnits.setosus, FRCerberianUnits.behemoth);
        
        addBlock(FRT3Units.auratus, FRMothershipUnits.lycosid);
        addBlock(FRT3Units.springald, FRMothershipUnits.onager);
        addBlock(FRT3Units.plant, FRMothershipUnits.corax);

        
        
        addBlock(modGateNaval, FRT1Units.alba);
        addBlock(FRT1Units.alba, FRT2Units.arvens);
        addBlock(FRT2Units.arvens, FRT3Units.aestiva);
        
        addBlock(modGateNaval, FRT1Units.mela);
        addBlock(FRT1Units.mela, FRT2Units.cromis);
        addBlock(FRT2Units.cromis, FRT3Units.arnux);
        
        addBlock(FRT3Units.arnux, FRMothershipUnits.japonica);
        addBlock(FRT3Units.aestiva, FRMothershipUnits.altaic);

        
        addBlock(ascendedFactory, FRTranscendentUnits.mygale);
        addBlock(ascendedFactory, FRTranscendentUnits.scepter);
        addBlock(ascendedFactory, FRTranscendentUnits.mangonel);
        addBlock(ascendedFactory, FRTranscendentUnits.thalass);
        addBlock(ascendedFactory, FRTranscendentUnits.vex);
        addBlock(ascendedFactory, FRTranscendentUnits.medusae);
        addBlock(ascendedFactory, FRTranscendentUnits.nivosa);

        
        addBlock(modGateCoreUnits, FRCoreUnits.delta);
        addBlock(FRCoreUnits.delta, FRCoreUnits.epsilon);
        addBlock(FRCoreUnits.epsilon, FRCoreUnits.omega);

        
    }

    private static void addBlock(UnlockableContent parent, UnlockableContent child) {
        TechTree.TechNode parentNode = nodes.get(parent);
        if (parentNode == null) return;

        TechTree.TechNode node = new TechTree.TechNode(parentNode, child, child.researchRequirements());
        nodes.put(child, node);

        node.objectives.add(new Research(parent));
    }

    
    private static TechTree.TechNode context;

    private static void addToNode(UnlockableContent parent, Runnable children) {
        context = TechTree.all.find(t -> t.content == parent);
        if (context == null) {
            for (TechTree.TechNode root : TechTree.roots) {
                context = findInTree(root, parent);
                if (context != null) break;
            }
        }
        children.run();
    }

    private static void node(UnlockableContent content, Runnable children) {
        node(content, content.researchRequirements(), children);
    }

    private static void node(UnlockableContent content, ItemStack[] requirements, Runnable children) {
        TechTree.TechNode node = new TechTree.TechNode(context, content, requirements);
        TechTree.TechNode prev = context;
        context = node;
        children.run();
        context = prev;
    }

    private static void node(UnlockableContent content) {
        node(content, () -> {});
    }

    private static TechTree.TechNode findInTree(TechTree.TechNode node, UnlockableContent target) {
        if (node.content == target) return node;
        for (TechTree.TechNode child : node.children) {
            TechTree.TechNode found = findInTree(child, target);
            if (found != null) return found;
        }
        return null;
    }

    private static void addAdditionalObjectives(UnlockableContent target, UnlockableContent... objectives) {
        TechTree.TechNode node = nodes.get(target);
        if (node == null) node = TechTree.all.find(t -> t.content == target);
        if (node == null) return;
        for (UnlockableContent obj : objectives) {
            node.objectives.add(new Research(obj));
        }
    }
}
