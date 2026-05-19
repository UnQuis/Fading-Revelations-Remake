package fadingrevelations;

import fadingrevelations.content.*;
import fadingrevelations.content.production.*;
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
        FREffectBlocks.load();
        FRPlanets.load();
        FRTechTree.load();
        FRFx.load();
        FRTurrets.load();
        FRT1Units.load();
        FRT2Units.load();
        FRT3Units.load();
        FRMissiles.load();
        FRCoreUnits.load();
        FRCerberianUnits.load();
        FRMothershipUnits.load();
        FRUnitFactories.load();
        
        // Power generators
        TinyThermalGen.load();
        TitaniumPanel.load();
        SteamTurbine.load();
        SlagGenerator.load();
        SlagReactor.load();
        PyratiteGenerator.load();
        LsGen.load();
        SteelReactor.load();
        UraniumReactor.load();
        SolarArray.load();
        AdvancedSolarPanel.load();
        TurbineConcentrator.load();

        // Production — living steel
        LivingSteelForge.load();
        LivingSteelInfuser.load();
        LivingSteelHardener.load();
        LivingSteelHardeningForge.load();
        LivingSteelLiquifier.load();
        LivingSteelLiquifyingForge.load();

        // Production — cryogenic alloy
        CryogenicGelMixer.load();
        AlloyCrafter.load();
        CryogenicAlloyAssembler.load();

        // Production — crafters
        AmalgamSmelter.load();
        AmalgamForge.load();
        BasicMultismelter.load();
        SteamCondenser.load();
        NeutronBlender.load();
        AcidVat.load();
        AcidEmulsifier.load();
        UraniumrodCrafter.load();

        // Production — ammo crafters
        AmmoCrafter1.load();
        AmmoCrafter2.load();
        AmmoCrafter3.load();
        HealAmmoCrafter.load();
        HomingAmmoCrafter.load();
        NanoAmmoCrafter.load();
        NukeCrafter.load();

        // Production — advanced vanilla blocks
        AdvancedSeparator.load();
        AdvancedCryofluidMixer.load();
        AdvancedCoalCentrifuge.load();
        AtmosphericHeatConcentrator.load();
        BigPhaseWeaver.load();
        AtmosphericExtractor.load();
        AdvancedWaterExtractor.load();
        EnhancedPyratiteMixer.load();
        EnhancedBlastMixer.load();
        Dissolver.load();
        CyanogenFuser.load();
        CorrosionChamber.load();
        PhaseManufacturer.load();
        InvertedPulverizer.load();
        SurgeOvenBig.load();
        InducedKiln.load();
        HeatDiverter.load();
        SurgeMelter.load();
        BigPlastaniumPress.load();
        SporeCrusher.load();
        SmallHeatRouter.load();
        EsterificationChamber.load();
        SiliconForge.load();
        SiliconArcForge.load();
        GraphiteForge.load();
        Powderizer.load();
        Greenhouse.load();
        CarbideBasin.load();
        
        FRFullTechTree.load();
    }
}
