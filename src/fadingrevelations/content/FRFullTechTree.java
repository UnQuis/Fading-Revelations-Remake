package fadingrevelations.content;

import arc.struct.ObjectFloatMap;
import arc.struct.Seq;
import mindustry.Vars;
import mindustry.content.TechTree;
import mindustry.content.TechTree.TechNode;
import mindustry.ctype.UnlockableContent;
import mindustry.game.Objectives;
import mindustry.type.Item;
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
import static fadingrevelations.content.FRSectorPresets.*;
import static mindustry.content.Blocks.*;
import static mindustry.content.TechTree.*;

public class FRFullTechTree {
    private static TechNode context;
    public static TechNode rootNode;

    public static void load() {
        ObjectFloatMap<Item> costMultipliers = new ObjectFloatMap<>();
        for(Item item : Vars.content.items()) costMultipliers.put(item, 0.5f);

        rootNode = nodeRoot("fading-revelations", modGateMain, false, () -> {
            context().researchCostMultipliers = costMultipliers;

            node(coreLevel4, () -> {
                node(coreLevel5, () -> {
                    node(mainCore, () -> {
                        node(corePrime, () -> {
                            node(FRCoreUnits.delta, () -> {
                                node(FRCoreUnits.epsilon, () -> {
                                    node(FRCoreUnits.coreTurretUnit);
                                });
                            });
                        });
                    });
                });
            });

            node(livingSteelWall, () -> {
                node(livingSteelWallLarge, () -> {
                    node(steelAlloyWallSmall, () -> {
                        node(steelAlloyWallLarge);
                    });
                });
            });
            node(copperWall2, () -> {
                node(copperWall3, () -> {
                    node(titaniumWall2, () -> {
                        node(titaniumWall3);
                        node(thoriumWall2, () -> {
                            node(thoriumWall3);
                        });
                        node(plastaniumWall2, () -> {
                            node(plastaniumWall3);
                        });
                        node(surgeAlloyWall2, () -> {
                            node(surgeAlloyWall3);
                            node(phaseFabricWall2, () -> {
                                node(phaseFabricWall3, () -> {
                                    node(nanoOpticWall, () -> {
                                        node(nanoOpticWallLarge, () -> {
                                            node(nanoOpticWallHuge, () -> {
                                                node(nanoOpticWallGigantic);
                                            });
                                        });
                                    });
                                });
                            });
                        });
                        node(armoredDoor, () -> {
                            node(doorHuge, () -> {
                                node(doorGigantic);
                            });
                        });
                    });
                });
            });

            node(trio, () -> {
                node(sear, () -> {
                    node(sunflare);
                    node(bigSwarmer, () -> {
                        node(corruptedCyclone);
                        node(gattling, () -> {
                            node(lightningChaingun);
                        });
                    });
                });
                node(shotgun, () -> {
                    node(sniper, () -> {
                        node(ringTurret, () -> {
                            node(ignitor);
                            node(diffract);
                        });
                    });
                    node(oreTurret, () -> {
                        node(mineLauncher, () -> {
                            node(mortar);
                            node(missileBattery, () -> {
                                node(interitus);
                                node(missileSilo);
                            });
                        });
                        node(bigScatter);
                    });
                });
            });
            node(caats);
            node(batter);
            node(airArc, () -> {
                node(uhlan, () -> {
                    node(kugelblitz, () -> {
                        node(statusWave);
                        node(cavalry);
                    });
                    node(accel, () -> {
                        node(absole, () -> {
                            node(megaMeltdown);
                        });
                    });
                });
                node(bigArc, () -> {
                    node(bigParallax);
                    node(bigSegment);
                });
            });

            node(reinforcedPowerNode, () -> {
                node(reinforcedLargePowerNode, () -> {
                    node(powerReserve, () -> {
                        node(crystalAccumulator);
                    });
                    node(advancedSurgeTower);
                });
            });
            node(tinyThermalGen, () -> {
                node(titaniumPanel, () -> {
                    node(advancedSolarPanel, () -> {
                        node(solarArray);
                    });
                });
                node(steamTurbine, Seq.with(
                    new Objectives.Research(steamGenerator),
                    new Objectives.Research(FRItems.livingSteel)
                ), () -> {});
            });
            node(slagGenerator, () -> {
                node(pyratiteGenerator);
            });
            node(steelReactor, () -> {
                node(plasmaReactor);
            });
            node(lsGen);

            node(miniOd, () -> {
                node(enhancedMendProjector, () -> {
                    node(darkMender, () -> {
                        node(nanoRepairField, () -> {
                            node(forceDome, () -> {
                                node(forceField);
                            });
                        });
                    });
                });
                node(constructionPylon);
            });
            node(overdriveRelay, () -> {
                node(overdriveBeacon);
            });
            node(fastUnloader, () -> {
                node(tinyMd, () -> {
                    node(massAccelerator);
                });
            });
            node(depository, () -> {
                node(FRDistribution.frReinforcedVault, () -> {
                    node(FRDistribution.frQuantumVault);
                });
            });

            node(titaniumJunction, () -> {
                node(titaniumRouter, () -> {
                    node(titaniumBridgeConveyor, () -> {
                        node(kineticConveyor, () -> {
                            node(surgeBridgeConveyor, () -> {
                                node(bioBridgeConveyor);
                            });
                            node(amalgamConveyor, () -> {
                                node(fusionConveyor);
                            });
                            node(bioConveyor);
                        });
                    });
                    node(kineticRouter);
                    node(bioRouter);
                });
                node(kineticJunction);
                node(titaniumDistributor, () -> {
                    node(kineticDistributor);
                    node(bioDistributor);
                });
            });

            node(steelTank, () -> {
                node(steelPump);
                node(bioLiquidContainer);
                node(kineticConduit, () -> {
                    node(kineticLiquidRouter, () -> {
                        node(liquidUnloader);
                    });
                    node(kineticLiquidBridge);
                });
            });

            node(tinyMechanicalDrill, () -> {
                node(tinyPneumaticDrill, () -> {
                    node(titaniumDrill, () -> {
                        node(compactLaserDrill, () -> {
                            node(hyperDrill, () -> {
                                node(cosmicDrill, () -> {
                                    node(omniDrill);
                                });
                            });
                        });
                    });
                });
            });
            node(groundGrinder, () -> {
                node(groundMiller, () -> {
                    node(groundCrusher, () -> {
                        node(oilBore);
                    });
                });
            });

            node(steamCondenser, () -> {
                node(dissolver, () -> {
                    node(acidVat, () -> {
                        node(acidEmulsifier);
                    });
                    node(advancedWaterExtractor, () -> {
                        node(advancedCryofluidMixer);
                        node(neutronBlender);
                    });
                });
            });
            node(livingSteelComplex);
            node(livingSteelLiquifier, () -> {
                node(livingSteelLiquifyingForge);
            });
            node(invertedPulverizer, () -> {
                node(powderizer, () -> {
                    node(inducedKiln, () -> {
                        node(siliconForge, () -> {
                            node(basicMultismelter);
                        });
                        node(greenhouse, () -> {
                            node(sporeCrusher);
                            node(enhancedPyratiteMixer, () -> {
                                node(enhancedBlastMixer, () -> {
                                    node(graphiteForge, () -> {
                                        node(advancedCoalCentrifuge, () -> {
                                            node(bigPlastaniumPress, () -> {
                                                node(bigPhaseWeaver, () -> {
                                                    node(advancedSeparator);
                                                });
                                                node(uraniumrodCrafter);
                                            });
                                            node(surgeOvenBig, () -> {
                                                node(amalgamSmelter, () -> {
                                                    node(amalgamForge);
                                                });
                                                node(cryogenicGelMixer, () -> {
                                                    node(alloyCrafter, () -> {
                                                        node(cryogenicAlloyAssembler, () -> {
                                                            node(crystalSynthesizer, () -> {
                                                                node(cellFabricator, () -> {
                                                                    node(nanoWeaver, () -> {
                                                                        node(fabricationNexus);
                                                                    });
                                                                });
                                                            });
                                                        });
                                                    });
                                                });
                                            });
                                        });
                                    });
                                });
                            });
                            node(bioRefinery, () -> {
                                nodeProduce(FRItems.bioMatter);
                            });
                        });
                    });
                });
            });

            nodeProduce(FRItems.livingSteel, () -> {
                nodeProduce(FRItems.livingSteelHard);
                nodeProduce(FRItems.steelAlloy, () -> {
                    nodeProduce(FRItems.fuelRod, () -> {
                        nodeProduce(FRItems.cryogenicGel, () -> {
                            nodeProduce(FRItems.igneousAlloy, () -> {
                                nodeProduce(FRItems.cryogenicAlloy, () -> {
                                    nodeProduce(FRItems.optiCrystal, () -> {
                                        nodeProduce(FRItems.energyCell, () -> {
                                            nodeProduce(FRItems.nanoFabric);
                                        });
                                    });
                                });
                            });
                        });
                    });
                });
            });
            nodeProduce(FRItems.nuke);
            nodeProduce(FREnvironment.steelSedimentation, () -> {
                nodeProduce(FREnvironment.oreGraphite);
            });

            node(primaryFactory, () -> {
                node(basicReassembly, () -> {
                    node(advancedReassembly, () -> {
                        node(progressiveAssembly, () -> {
                            node(ascendedFactory, () -> {
                                node(FRTranscendentUnits.mygale);
                                node(FRTranscendentUnits.scepter);
                                node(FRTranscendentUnits.mangonel);
                                node(FRTranscendentUnits.thalass);
                                node(FRTranscendentUnits.vex);
                                node(FRTranscendentUnits.medusae);
                                node(FRTranscendentUnits.nivosa);
                            });
                        });
                    });
                });
                node(regenerator);
            });

            node(FRT1Units.heliaca);
            node(FRT1Units.apis, () -> {
                node(FRT2Units.procer, () -> {
                    node(FRT3Units.ducalis, () -> {
                        node(FRMothershipUnits.hive);
                    });
                });
            });
            node(FRT1Units.seed, () -> {
                node(FRT2Units.sapling, () -> {
                    node(FRT3Units.plant, () -> {
                        node(FRMothershipUnits.corax);
                    });
                });
            });
            node(FRT1Units.lancerDrone, () -> {
                node(FRT2Units.alopex, () -> {
                    node(FRT3Units.kestrel, () -> {
                        node(FRMothershipUnits.strahl);
                    });
                });
            });
            node(FRCerberianUnits.spark, () -> {
                node(FRCerberianUnits.vista, () -> {
                    node(FRCerberianUnits.summit, () -> {
                        node(FRCerberianUnits.penumbra, () -> {
                            node(FRCerberianUnits.veil);
                        });
                    });
                });
            });

            node(FRT1Units.annax, () -> {
                node(FRT2Units.scofra, () -> {
                    node(FRT3Units.auratus, () -> {
                        node(FRMothershipUnits.lycosid);
                    });
                });
            });
            node(FRT1Units.sambuca, () -> {
                node(FRT2Units.scorpio, () -> {
                    node(FRT3Units.springald, () -> {
                        node(FRMothershipUnits.onager);
                    });
                });
            });
            node(FRCerberianUnits.straggle, () -> {
                node(FRCerberianUnits.bayonet, () -> {
                    node(FRCerberianUnits.hexathelid, () -> {
                        node(FRCerberianUnits.cudgel, () -> {
                            node(FRCerberianUnits.citadel, () -> {
                                node(FRCerberianUnits.nephila, () -> {
                                    node(FRCerberianUnits.curtulus, () -> {
                                        node(FRCerberianUnits.auctus, () -> {
                                            node(FRCerberianUnits.baton, () -> {
                                                node(FRCerberianUnits.kaiser, () -> {
                                                    node(FRCerberianUnits.setosus, () -> {
                                                        node(FRCerberianUnits.behemoth);
                                                    });
                                                });
                                            });
                                        });
                                    });
                                });
                            });
                        });
                    });
                });
            });

            node(FRT1Units.alba, () -> {
                node(FRT2Units.arvens, () -> {
                    node(FRT3Units.aestiva, () -> {
                        node(FRMothershipUnits.altaic);
                    });
                });
            });
            node(FRT1Units.mela, () -> {
                node(FRT2Units.cromis, () -> {
                    node(FRT3Units.arnux, () -> {
                        node(FRMothershipUnits.japonica);
                    });
                });
            });

            node(exordium, () -> {
                node(dree, Seq.with(
                    new Objectives.OnSector(exordium)
                ), () -> {
                    node(sporeDunes, Seq.with(
                        new Objectives.SectorComplete(dree)
                    ), () -> {
                        node(abandonedBattlefield, Seq.with(
                            new Objectives.SectorComplete(sporeDunes)
                        ), () -> {});
                    });
                });
            });
            node(etnaticIsles, () -> {
                node(carbonicDownpour, Seq.with(
                    new Objectives.SectorComplete(etnaticIsles)
                ), () -> {});
            });
        });

        FRPlanets.cerbero.techTree = rootNode;
        FRPlanets.cangirus.techTree = rootNode;

        addToNode(largePlasmaBore, () -> node(plasmaArcBore));
        addToNode(impactDrill, () -> node(percussionDrill));
        addToNode(cliffCrusher, () -> node(cliffGrinder, () -> node(cliffMiller)));
        addToNode(plasmaBore, () -> { node(tinyPlasmaBore); node(tungstenBore); });
        addToNode(ventCondenser, () -> node(ventConcentrator));
        addToNode(shieldedWall, () -> node(armoredWall));
        addToNode(berylliumWallLarge, () -> node(berylliumWallHuge, () -> node(berylliumWallGigantic)));
        addToNode(tungstenWallLarge, () -> node(tungstenWallHuge, () -> node(tungstenWallGigantic)));
        addToNode(carbideWallLarge, () -> node(carbideWallHuge, () -> node(carbideWallGigantic)));
        addToNode(reinforcedSurgeWallLarge, () -> node(reinforcedSurgeWallHuge, () -> node(reinforcedSurgeWallGigantic)));
        addToNode(siliconArcFurnace, () -> node(siliconArcForge));
        addToNode(surgeCrucible, () -> node(surgeMelter));
        addToNode(phaseSynthesizer, () -> node(phaseManufacturer));
        addToNode(atmosphericConcentrator, () -> node(atmosphericExtractor));
        addToNode(electricHeater, () -> { node(atmosphericHeatConcentrator); node(heatDiverter); });
        addToNode(heatRouter, () -> node(smallHeatRouter));
        addToNode(electrolyzer, () -> node(esterificationChamber));
        addToNode(cyanogenSynthesizer, () -> node(cyanogenFuser));
        addToNode(oxidationChamber, () -> node(corrosionChamber));
        addToNode(carbideCrucible, () -> node(carbideBasin));
        addToNode(ventCondenser, () -> node(slagReactor));
        addToNode(turbineCondenser, () -> node(turbineConcentrator));
    }

    private static void node(UnlockableContent content, Runnable children) {
        node(content, content.researchRequirements(), null, children);
    }

    private static void node(UnlockableContent content, Seq<Objectives.Objective> objectives, Runnable children) {
        node(content, content.researchRequirements(), objectives, children);
    }

    private static void node(UnlockableContent content, ItemStack[] requirements, Seq<Objectives.Objective> objectives, Runnable children) {
        TechNode parent = context != null ? context : TechTree.context();
        TechNode node = new TechNode(parent, content, requirements);
        if(objectives != null){
            node.objectives.addAll(objectives);
        }
        if(parent != null && parent.content != content){
            node.objectives.add(new Objectives.Research(parent.content));
        }
        TechNode prev = context;
        context = node;
        children.run();
        context = prev;
    }

    private static void node(UnlockableContent content) {
        node(content, () -> {});
    }

    private static void nodeProduce(UnlockableContent content, Runnable children) {
        node(content, content.researchRequirements(), Seq.with(new Objectives.Produce(content)), children);
    }

    private static void nodeProduce(UnlockableContent content) {
        nodeProduce(content, () -> {});
    }

    private static void addToNode(UnlockableContent parent, Runnable children) {
        context = TechTree.all.find(t -> t.content == parent);
        if(context == null){
            for(TechNode root : TechTree.roots){
                context = findInTree(root, parent);
                if(context != null) break;
            }
        }
        if(context != null){
            children.run();
        }
    }

    private static TechNode findInTree(TechNode node, UnlockableContent target) {
        if(node.content == target) return node;
        for(TechNode child : node.children){
            TechNode found = findInTree(child, target);
            if(found != null) return found;
        }
        return null;
    }
}