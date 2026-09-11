package fadingrevelations;

import fadingrevelations.content.*;
import fadingrevelations.ui.FRResearchDialog;
import mindustry.Vars;
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
        FRFx.load();
        FRCoreUnits.load();
        FREffectBlocks.load();
        FRPlanets.load();
        FRSectorPresets.load();
        FRTechTree.load();
        FRMissiles.load();
        FRTurrets.load();
        FRT1Units.load();
        FRT2Units.load();
        FRT3Units.load();
        FRCerberianUnits.load();
        FRMothershipUnits.load();
        FRTranscendentUnits.load();
        FRProduction.load();

        FRFullTechTree.load();
    }

    @Override
    public void init() {
        FROldContent.init();
        FRSettings.init();
        FROrbitalRing.load();
        FRLore.load();
        if (FRSettings.mixTech) {
            FROverride.mixTech();
        }
        FROverride.hardcore();
        FROverride.noCoreBurn();
        FRMusic.load();
        if (!Vars.headless && Vars.ui != null) {
            FRResearchDialog.init();
            //Satisfactory-style hover chart for the grid monitor
            new fadingrevelations.ui.GridMonitorTooltip().register();
            //block info UIs show the Orbital Ring's planet-wide boost as its own line
            Vars.ui.content = new fadingrevelations.ui.FRContentInfoDialog();
        }
    }
}
//I'm so fucking tired
