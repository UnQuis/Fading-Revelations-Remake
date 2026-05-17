package fadingrevelations.content;
import mindustry.world.Block;
import mindustry.world.blocks.defense.Wall;
import mindustry.world.meta.BuildVisibility;

public class FRGates {
    public static Block modGateMain, modGateBlocks, modGateItems, modGateResources, modGateAmmo,
            modGateCrafters, modGateDrills, modGateDistribution, modGatePower, modGateLiquids,
            modGateEffect, modGateWalls, modGateCores, modGateUnits, modGateFactories,
            modGateFlying, modGateLegs, modGateNaval, modGateCoreUnits, modGateTurrets;

    public static void load() {
        modGateMain = gate("mod-gate-main", "Gate: Fading Revelations", "The gate to all of the content included in the Fading Revelations Mod.", true);

        modGateBlocks = gate("mod-gate-blocks", "Gate: Blocks", "The gate to all of the blocks included in the Fading Revelations Mod.", false);
        modGateItems = gate("mod-gate-items", "Gate: Items", "The gate to all of the items included in the Fading Revelations Mod.", false);
        modGateResources = gate("mod-gate-resources", "Gate: Resources", "The gate to all of the resources and ores included in the Fading Revelations Mod.", false);
        modGateAmmo = gate("mod-gate-ammo", "Gate: Ammo", "The gate to all of the ammo included in the Fading Revelations Mod.", false);
        modGateCrafters = gate("mod-gate-crafters", "Gate: Production", "The gate to all of the production blocks included in the Fading Revelations Mod.", false);
        modGateDrills = gate("mod-gate-drills", "Gate: Drills", "The gate to all of the drills included in the Fading Revelations Mod.", false);
        modGateDistribution = gate("mod-gate-distribution", "Gate: Distribution", "The gate to all of the conveyors and similar blocks included in the Fading Revelations Mod.", false);
        modGatePower = gate("mod-gate-power", "Gate: Power", "The gate to all of the power blocks included in the Fading Revelations Mod.", false);
        modGateLiquids = gate("mod-gate-liquids", "Gate: Liquid", "The gate to all of the liquid related blocks included in the Fading Revelations Mod.", false);
        modGateEffect = gate("mod-gate-effect", "Gate: Effect Blocks", "The gate to all of the effect blocks included in the Fading Revelations Mod.", false);
        modGateWalls = gate("mod-gate-walls", "Gate: Walls", "The gate to all of the walls included in the Fading Revelations Mod.", false);
        modGateCores = gate("mod-gate-cores", "Gate: Cores", "The gate to all of the cores included in the Fading Revelations Mod.", false);

        modGateUnits = gate("mod-gate-units", "Gate: Units", "Contains the tech tree for all units included in the Fading Revelations mod.", false);
        modGateFactories = gate("mod-gate-factories", "Gate: Factories", "The gate to all of the unit factories included in the Fading Revelations Mod.", false);
        modGateFlying = gate("mod-gate-flying", "Gate: Flying Units", "Contains the tech tree for all flying units included in the Fading Revelations mod.", false);
        modGateLegs = gate("mod-gate-legs", "Gate: Ground Units", "Contains the tech tree for all ground units included in the Fading Revelations mod.", false);
        modGateNaval = gate("mod-gate-naval", "Gate: Naval Units", "Contains the tech tree for all naval units included in the Fading Revelations mod.", false);
        modGateCoreUnits = gate("mod-gate-core-units", "Gate: Core Units", "The gate to all of the core units included in the Fading Revelations Mod.", false);
        modGateTurrets = gate("mod-gate-turrets", "Gate: Turrets", "The gate to all of the turrets included in the Fading Revelations Mod.", false);
    }

    private static Block gate(String name, String localizedName, String description, boolean root) {
        return new Wall(name) {{
            this.localizedName = localizedName;
            this.description = description;
            health = 0;
            buildVisibility = BuildVisibility.hidden;
            alwaysUnlocked = true;
            size = 1;
        }};
    }
}
