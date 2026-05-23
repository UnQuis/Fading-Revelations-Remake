package fadingrevelations;

import fadingrevelations.content.*;
import mindustry.mod.Mod;

public class FadingRevelationsMod extends Mod {

    @Override
    public void loadContent() {
        FRItems.load();
        FRLiquids.load();
        FRStatus.load();
        FRGates.load();
        FRDistribution.load();
        FRDrills.load();
        FREnvironment.load();
        FRWalls.load();
        FRLiquidsBlocks.load();
        FRPowerNodes.load();
        FRCoreUnits.load();
        FREffectBlocks.load();
        FRPlanets.load();
        FRSectorPresets.load();
        FRTechTree.load();
        FRFx.load();
        FRTurrets.load();
        FRT1Units.load();
        FRT2Units.load();
        FRT3Units.load();
        FRMissiles.load();
        FRCerberianUnits.load();
        FRMothershipUnits.load();
        FRTranscendentUnits.load();
        FRProduction.load();

        FRFullTechTree.load();
    }

    @Override
    public void init() {
        FRSettings.init();
        if (FRSettings.mixTech) {
            FROverride.mixTech();
        }
        FROverride.hardcore();
        FROverride.noCoreBurn();
        FRMusic.load();
    }
}
//I'm so fucking tired
