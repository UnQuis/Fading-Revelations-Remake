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

public class FRFullTechTree {
    private static final ObjectMap<UnlockableContent, TechTree.TechNode> nodes = new ObjectMap<>();

    public static void load() {
        // Root - Mini OD
        TechTree.TechNode root = TechTree.nodeRoot("fading-revelations", miniOd, false, () -> {});
        nodes.put(miniOd, root);

        // === OVERDRIVE PROGRESSION (MAIN) ===
        addBlock(miniOd, overdriveRelay);
        addBlock(overdriveRelay, overdriveBeacon);

        // === EFFECT BLOCKS ===
        addBlock(overdriveRelay, enhancedMendProjector);
        addBlock(overdriveRelay, darkMender);
        addBlock(overdriveRelay, fastUnloader);
        addBlock(overdriveBeacon, forceDome);
        addBlock(overdriveBeacon, bigLaunchPad);
        addBlock(bigLaunchPad, outpost);
        
        // === SOME TURRETS (examples) ===
        addBlock(miniOd, accel);
        addBlock(accel, bigSwarmer);
        addBlock(bigSwarmer, caats);
        
        // === DISTRIBUTION ===
        addBlock(miniOd, titaniumRouter);
        addBlock(titaniumRouter, titaniumJunction);
        addBlock(titaniumJunction, titaniumDistributor);
        addBlock(titaniumDistributor, titaniumBridgeConveyor);
        
        // === DRILLS ===
        addBlock(miniOd, tinyMechanicalDrill);
        addBlock(tinyMechanicalDrill, tinyPneumaticDrill);
        addBlock(tinyPneumaticDrill, tinyPlasmaBore);
        
        // === WALLS ===
        addBlock(miniOd, steelAlloyWallSmall);
        addBlock(steelAlloyWallSmall, steelAlloyWallLarge);
        
        // === POWER ===
        addBlock(miniOd, reinforcedPowerNode);
        
        // === LIQUIDS ===
        addBlock(miniOd, steelTank);
    }

    private static void addBlock(UnlockableContent parent, UnlockableContent child) {
        TechTree.TechNode parentNode = nodes.get(parent);
        if (parentNode == null) return;

        TechTree.TechNode node = new TechTree.TechNode(parentNode, child, child.researchRequirements());
        nodes.put(child, node);

        node.objectives.add(new Research(parent));
    }
}