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

public class FRFullTechTree {
    private static final ObjectMap<UnlockableContent, TechTree.TechNode> nodes = new ObjectMap<>();

    public static void load() {
        TechTree.TechNode root = TechTree.nodeRoot("fading-revelations", modGateMain, false, () -> {});
        nodes.put(modGateMain, root);

        // === BLOCKS GATE → container for all block sub-gates ===
        addBlock(modGateMain, modGateBlocks);

        // --- LIQUIDS GATE → liquid handling ---
        addBlock(modGateBlocks, modGateLiquids);
        addBlock(modGateLiquids, steelTank);
        addBlock(steelTank, steelPump);
        addBlock(steelPump, plastaniumConduit);

        // --- DISTRIBUTION GATE ---
        addBlock(modGateBlocks, modGateDistribution);
        addBlock(modGateDistribution, titaniumRouter);
        addBlock(titaniumRouter, titaniumBridgeConveyor);
        addBlock(titaniumBridgeConveyor, surgeAlloyConveyor);
        addBlock(surgeAlloyConveyor, surgeBridgeConveyor);
        addBlock(surgeAlloyConveyor, amalgamConveyor);
        addBlock(modGateDistribution, depository);
        addBlock(modGateDistribution, massAccelerator);

        // --- DRILLS GATE ---
        addBlock(modGateBlocks, modGateDrills);
        addBlock(modGateDrills, tinyMechanicalDrill);
        addBlock(tinyMechanicalDrill, tinyPneumaticDrill);
        addBlock(modGateDrills, titaniumDrill);
        addBlock(titaniumDrill, compactLaserDrill);
        addBlock(modGateDrills, groundGrinder);
        addBlock(modGateDrills, groundMiller);
        addBlock(modGateDrills, groundCrusher);
        addBlock(modGateDrills, cliffGrinder);
        addBlock(modGateDrills, cliffMiller);
        addBlock(modGateDrills, oilBore);
        addBlock(modGateDrills, hyperDrill);

        // --- WALLS GATE ---
        // JSON: living-steel-wall → living-steel-wall-2 → steel-alloy-wall-small → steel-alloy-wall-large
        addBlock(modGateBlocks, modGateWalls);
        addBlock(modGateWalls, livingSteelWall);
        addBlock(livingSteelWall, livingSteelWallLarge);
        addBlock(livingSteelWallLarge, steelAlloyWallSmall);
        addBlock(steelAlloyWallSmall, steelAlloyWallLarge);

        // --- POWER GATE ---
        addBlock(modGateBlocks, modGatePower);

        // Power nodes
        addBlock(modGatePower, reinforcedPowerNode);
        addBlock(reinforcedPowerNode, reinforcedLargePowerNode);
        addBlock(reinforcedLargePowerNode, powerReserve);
        addBlock(reinforcedLargePowerNode, advancedSurgeTower);

        // Generators
        addBlock(modGatePower, tinyThermalGen);
        addBlock(tinyThermalGen, titaniumPanel);
        addBlock(titaniumPanel, advancedSolarPanel);
        addBlock(advancedSolarPanel, solarArray);
        addBlock(tinyThermalGen, steamTurbine);
        addBlock(modGatePower, slagGenerator);
        addBlock(slagGenerator, pyratiteGenerator);
        addBlock(advancedSolarPanel, uraniumReactor);
        addBlock(uraniumReactor, steelReactor);
        addBlock(modGatePower, slagReactor);
        addBlock(modGatePower, lsGen);

        // --- EFFECT GATE ---
        // JSON: mini-od → enhanced-mend-projector → dark-mender → force-dome
        addBlock(modGateBlocks, modGateEffect);
        addBlock(modGateEffect, miniOd);
        addBlock(miniOd, enhancedMendProjector);
        addBlock(enhancedMendProjector, darkMender);
        addBlock(darkMender, forceDome);
        addBlock(forceDome, forceField);
        addBlock(modGateEffect, overdriveRelay);
        addBlock(overdriveRelay, overdriveBeacon);
        addBlock(modGateEffect, fastUnloader);
        addBlock(modGateEffect, constructionPylon);

        // --- TURRETS GATE ---
        addBlock(modGateBlocks, modGateTurrets);

        // Item branch
        addBlock(modGateTurrets, trio);
        addBlock(trio, sear);
        addBlock(sear, sunflare);
        addBlock(modGateTurrets, accel);
        addBlock(modGateTurrets, bigSwarmer);
        addBlock(modGateTurrets, caats);
        addBlock(modGateTurrets, corruptedCyclone);
        addBlock(modGateTurrets, gattling);
        addBlock(gattling, lightningChaingun);
        addBlock(modGateTurrets, ignitor);
        addBlock(modGateTurrets, interitus);
        addBlock(modGateTurrets, mineLauncher);
        addBlock(mineLauncher, missileBattery);
        addBlock(missileBattery, interitus);
        addBlock(modGateTurrets, mortar);
        addBlock(modGateTurrets, oreTurret);
        addBlock(modGateTurrets, ringTurret);
        addBlock(modGateTurrets, shotgun);
        addBlock(shotgun, upgradeTurret);
        addBlock(modGateTurrets, sniper);
        addBlock(modGateTurrets, missileSilo);

        // Power branch
        addBlock(modGateTurrets, airArc);
        addBlock(airArc, uhlan);
        addBlock(airArc, bigArc);
        addBlock(bigArc, bigParallax);
        addBlock(bigArc, bigSegment);
        addBlock(bigArc, bigScatter);
        addBlock(modGateTurrets, absole);
        addBlock(absole, megaMeltdown);
        addBlock(megaMeltdown, kugelblitz);
        addBlock(kugelblitz, statusWave);
        addBlock(kugelblitz, diffract);
        addBlock(diffract, cavalry);
        addBlock(cavalry, zephyr);
        addBlock(zephyr, weave);
        addBlock(weave, sprunkler);
        addBlock(modGateTurrets, batter);

        // --- CORES GATE ---
        addBlock(modGateBlocks, modGateCores);
        addBlock(modGateCores, coreLevel4);
        addBlock(coreLevel4, coreLevel5);
        addBlock(coreLevel5, mainCore);

        // --- CRAFTERS GATE → all production blocks ---
        // JSON: research → mod-gate-blocks
        addBlock(modGateBlocks, modGateCrafters);

        // === Chain 1: Steam → Liquid Processing → Water ===
        // steam-condenser → dissolver → acid-vat → acid-emulsifier
        //                     ↓
        //               advanced-water-extractor → advanced-cryofluid-mixer
        //                                      → neutron-blender
        addBlock(modGateCrafters, steamCondenser);
        addBlock(steamCondenser, dissolver);
        addBlock(dissolver, acidVat);
        addBlock(acidVat, acidEmulsifier);
        addBlock(dissolver, advancedWaterExtractor);
        addBlock(advancedWaterExtractor, advancedCryofluidMixer);
        addBlock(advancedWaterExtractor, neutronBlender);

        // === Chain 2: Living Steel Family ===
        // living-steel-infuser → living-steel-forge
        //                     → living-steel-liquifier → living-steel-liquifying-forge
        //                                              → living-steel-hardener → living-steel-hardening-forge
        addBlock(modGateCrafters, livingSteelInfuser);
        addBlock(livingSteelInfuser, livingSteelForge);
        addBlock(livingSteelInfuser, livingSteelLiquifier);
        addBlock(livingSteelLiquifier, livingSteelLiquifyingForge);
        addBlock(livingSteelLiquifier, livingSteelHardener);
        addBlock(livingSteelHardener, livingSteelHardeningForge);

        // === Chain 3: Ammo Family ===
        // ammo-crafter-1 → ammo-crafter-2 → ammo-crafter-3 → heal-ammo-crafter
        //                                                    → homing-ammo-crafter → nano-ammo-crafter → nuke-crafter
        addBlock(modGateCrafters, ammoCrafter1);
        addBlock(ammoCrafter1, ammoCrafter2);
        addBlock(ammoCrafter2, ammoCrafter3);
        addBlock(ammoCrafter3, healAmmoCrafter);
        addBlock(healAmmoCrafter, homingAmmoCrafter);
        addBlock(homingAmmoCrafter, nanoAmmoCrafter);
        addBlock(nanoAmmoCrafter, nukeCrafter);

        // === Chain 4: Main Crafting Family ===
        // inverted-pulverizer → powderizer → induced-kiln → silicon-forge → basic-multismelter
        //                                        ↓
        //                                   greenhouse → spore-crusher
        //                                             → enhanced-pyratite-mixer → enhanced-blast-mixer
        //                                                                      → graphite-forge → advanced-coal-centrifuge
        //                                                                                          → big-plastanium-press → big-phase-weaver → advanced-separator
        //                                                                                         │                    → uraniumrod-crafter
        //                                                                                         → surge-oven-big → amalgam-smelter → amalgam-forge
        //                                                                                                       → cryogenic-gel-mixer → alloy-crafter → cryogenic-alloy-assembler
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

        // === Standalone from modGateCrafters (vanilla parent blocks) ===
        addBlock(modGateCrafters, siliconArcForge);
        addBlock(modGateCrafters, surgeMelter);
        addBlock(modGateCrafters, phaseManufacturer);
        addBlock(modGateCrafters, atmosphericExtractor);
        addBlock(modGateCrafters, atmosphericHeatConcentrator);
        addBlock(modGateCrafters, heatDiverter);
        addBlock(modGateCrafters, smallHeatRouter);
        addBlock(modGateCrafters, esterificationChamber);
        addBlock(modGateCrafters, cyanogenFuser);
        addBlock(modGateCrafters, corrosionChamber);
        addBlock(modGateCrafters, carbideBasin);

        // === ITEMS GATE → main items branch ===
        addBlock(modGateMain, modGateItems);
        addBlock(modGateItems, FRItems.livingSteel);
        addBlock(FRItems.livingSteel, FRItems.steelAlloy);
        addBlock(FRItems.steelAlloy, FRItems.fuelRod);
        addBlock(FRItems.fuelRod, FRItems.cryogenicGel);
        addBlock(FRItems.cryogenicGel, FRItems.igneousAlloy);
        addBlock(FRItems.igneousAlloy, FRItems.cryogenicAlloy);

        // --- AMMO GATE → ammo items (research: mod-gate-items) ---
        addBlock(modGateItems, modGateAmmo);
        addBlock(modGateAmmo, FRItems.ammoLevel1);
        addBlock(FRItems.ammoLevel1, FRItems.ammoLevel2);
        addBlock(FRItems.ammoLevel2, FRItems.ammoLevel3);
        addBlock(FRItems.ammoLevel1, FRItems.healAmmo);
        addBlock(FRItems.ammoLevel2, FRItems.homingAmmo);
        addBlock(FRItems.ammoLevel3, FRItems.nanoAmmo);
        addBlock(FRItems.nanoAmmo, FRItems.nuke);

        // --- RESOURCES GATE → environment/ore blocks (research: mod-gate-items) ---
        addBlock(modGateItems, modGateResources);
        addBlock(modGateResources, FREnvironment.steelSedimentation);
        addBlock(FREnvironment.steelSedimentation, FREnvironment.oreGraphite);

        // === UNITS GATE → sub-gates for unit types ===
        addBlock(modGateMain, modGateUnits);
        addBlock(modGateUnits, modGateFactories);
        addBlock(modGateFactories, primaryFactory);
        addBlock(primaryFactory, basicReassembly);
        addBlock(basicReassembly, advancedReassembly);
        addBlock(advancedReassembly, progressiveAssembly);
        addBlock(modGateFactories, regenerator);
        addBlock(modGateUnits, modGateFlying);
        addBlock(modGateUnits, modGateLegs);
        addBlock(modGateUnits, modGateNaval);
        addBlock(modGateUnits, modGateCoreUnits);

        // === FLYING UNITS ===
        // Branch A: light fighters
        addBlock(modGateFlying, FRT1Units.aedes);
        addBlock(FRT1Units.aedes, FRT2Units.armiger);
        addBlock(FRT2Units.armiger, FRT3Units.kestrel);
        addBlock(FRT3Units.kestrel, FRT3Units.onirion);
        // Branch B: scouts
        addBlock(FRT1Units.aedes, FRT1Units.heliaca);
        addBlock(FRT1Units.heliaca, FRT2Units.alopex);
        addBlock(FRT2Units.alopex, FRT3Units.ducalis);
        // Branch C: bomber/support
        addBlock(modGateFlying, FRT1Units.apis);
        addBlock(FRT1Units.apis, FRT2Units.procer);
        addBlock(FRT2Units.procer, FRT3Units.plant);
        // Branch D: builder chain
        addBlock(modGateFlying, FRT1Units.seed);
        addBlock(FRT1Units.seed, FRT1Units.lancerDrone);
        addBlock(FRT1Units.lancerDrone, FRT2Units.sapling);
        // Cerberian flying
        addBlock(modGateFlying, FRCerberianUnits.spark);
        addBlock(FRCerberianUnits.spark, FRCerberianUnits.vista);
        addBlock(FRCerberianUnits.vista, FRCerberianUnits.summit);
        addBlock(FRCerberianUnits.summit, FRCerberianUnits.penumbra);
        addBlock(FRCerberianUnits.penumbra, FRCerberianUnits.veil);
        // Mothership flying
        addBlock(modGateFlying, FRMothershipUnits.hiveAttack);
        addBlock(FRMothershipUnits.hiveAttack, FRMothershipUnits.strahl);
        addBlock(FRMothershipUnits.strahl, FRMothershipUnits.hive);
        addBlock(FRMothershipUnits.hive, FRMothershipUnits.culiseta);

        // === GROUND UNITS ===
        // Main progression: mech → tank → legs
        addBlock(modGateLegs, FRT1Units.annax);
        addBlock(FRT1Units.annax, FRT1Units.sambuca);
        addBlock(FRT1Units.sambuca, FRT2Units.scofra);
        addBlock(FRT2Units.scofra, FRT2Units.scorpio);
        addBlock(FRT2Units.scorpio, FRT3Units.auratus);
        addBlock(FRT3Units.auratus, FRT3Units.springald);
        // Cerberian ground: from weakest to strongest
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
        // Mothership ground
        addBlock(modGateLegs, FRMothershipUnits.toruct);
        addBlock(FRMothershipUnits.toruct, FRMothershipUnits.reduct);
        addBlock(FRMothershipUnits.reduct, FRMothershipUnits.corax);
        addBlock(FRMothershipUnits.corax, FRMothershipUnits.lycosid);
        addBlock(FRMothershipUnits.lycosid, FRMothershipUnits.onager);

        // === NAVAL UNITS ===
        addBlock(modGateNaval, FRT1Units.alba);
        addBlock(FRT1Units.alba, FRT1Units.mela);
        addBlock(FRT1Units.mela, FRT2Units.arvens);
        addBlock(FRT2Units.arvens, FRT2Units.cromis);
        addBlock(FRT2Units.cromis, FRT3Units.aestiva);
        addBlock(FRT3Units.aestiva, FRT3Units.arnux);
        addBlock(FRT3Units.arnux, FRMothershipUnits.japonica);
        addBlock(FRMothershipUnits.japonica, FRMothershipUnits.altaic);

        // === CORE UNITS ===
        addBlock(modGateCoreUnits, FRCoreUnits.delta);
        addBlock(FRCoreUnits.delta, FRCoreUnits.epsilon);

        // === FUTURE GATES (empty, for later use) ===
    }

    private static void addBlock(UnlockableContent parent, UnlockableContent child) {
        TechTree.TechNode parentNode = nodes.get(parent);
        if (parentNode == null) return;

        TechTree.TechNode node = new TechTree.TechNode(parentNode, child, child.researchRequirements());
        nodes.put(child, node);

        node.objectives.add(new Research(parent));
    }
}
