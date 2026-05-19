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

public class FRFullTechTree {
    private static final ObjectMap<UnlockableContent, TechTree.TechNode> nodes = new ObjectMap<>();

    public static void load() {
        // Root - Mini OD (starting point)
        TechTree.TechNode root = TechTree.nodeRoot("fading-revelations", miniOd, false, () -> {});
        nodes.put(miniOd, root);

        // === OVERDRIVE PROGRESSION (MAIN CHAIN) ===
        addBlock(miniOd, overdriveRelay);
        addBlock(overdriveRelay, overdriveBeacon);
        addBlock(overdriveBeacon, forceDome);
        
        // === EFFECT BLOCKS BRANCH ===
        addBlock(overdriveRelay, enhancedMendProjector);
        addBlock(overdriveRelay, darkMender);
        addBlock(overdriveRelay, fastUnloader);
        addBlock(overdriveBeacon, bigLaunchPad);
        addBlock(bigLaunchPad, outpost);

        // === TURRETS - Item Turrets ===
        addBlock(miniOd, accel);
        addBlock(accel, bigSwarmer);
        addBlock(bigSwarmer, caats);
        addBlock(caats, corruptedCyclone);
        addBlock(corruptedCyclone, gattling);
        addBlock(gattling, ignitor);
        addBlock(ignitor, interitus);
        
        // Turrets - Power Turrets
        addBlock(interitus, uhlan);
        addBlock(uhlan, megaMeltdown);
        addBlock(megaMeltdown, kugelblitz);
        addBlock(kugelblitz, diffract);
        addBlock(diffract, cavalry);
        addBlock(cavalry, bigSegment);
        addBlock(bigSegment, bigScatter);
        addBlock(bigScatter, bigArc);
        
        // Turrets - Liquid Turrets
        addBlock(bigArc, zephyr);
        addBlock(zephyr, weave);
        addBlock(weave, sprunkler);

        // === DISTRIBUTION ===
        addBlock(miniOd, titaniumRouter);
        addBlock(titaniumRouter, titaniumJunction);
        addBlock(titaniumJunction, titaniumDistributor);
        addBlock(titaniumDistributor, titaniumBridgeConveyor);
        addBlock(titaniumBridgeConveyor, modGateMain);
        
        // Gates
        addBlock(modGateMain, modGateBlocks);
        addBlock(modGateBlocks, modGateItems);
        addBlock(modGateItems, modGateResources);
        addBlock(modGateResources, modGateAmmo);

        // === DRILLS ===
        addBlock(miniOd, tinyMechanicalDrill);
        addBlock(tinyMechanicalDrill, tinyPneumaticDrill);
        addBlock(tinyPneumaticDrill, tinyPlasmaBore);
        addBlock(tinyPlasmaBore, titaniumDrill);

        // === WALLS ===
        addBlock(miniOd, steelAlloyWallSmall);
        addBlock(steelAlloyWallSmall, steelAlloyWallLarge);
        addBlock(steelAlloyWallLarge, livingSteelWall);
        addBlock(livingSteelWall, livingSteelWallLarge);

        // === POWER ===
        addBlock(miniOd, reinforcedPowerNode);
        addBlock(reinforcedPowerNode, reinforcedLargePowerNode);
        addBlock(reinforcedLargePowerNode, powerReserve);
        addBlock(powerReserve, advancedSurgeTower);

        // === LIQUIDS ===
        addBlock(miniOd, steelTank);
        addBlock(steelTank, steelPump);
        addBlock(steelPump, plastaniumConduit);
    }

    private static void addBlock(UnlockableContent parent, UnlockableContent child) {
        TechTree.TechNode parentNode = nodes.get(parent);
        if (parentNode == null) return;

        TechTree.TechNode node = new TechTree.TechNode(parentNode, child, child.researchRequirements());
        nodes.put(child, node);

        node.objectives.add(new Research(parent));
    }
}