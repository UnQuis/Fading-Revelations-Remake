package fadingrevelations.content;

import mindustry.content.Items;
import mindustry.type.Category;
import mindustry.type.ItemStack;
import mindustry.world.Block;
import mindustry.world.blocks.units.UnitFactory;
import mindustry.world.blocks.units.Reconstructor;
import mindustry.world.blocks.units.RepairTower;

public class FRUnitFactories {
    public static Block primaryFactory, basicReassembly, advancedReassembly, progressiveAssembly, regenerator;

    public static void load() {
        primaryFactory = new UnitFactory("primary-factory") {{
            localizedName = "Primary Factory";
            description = "Creates small and light units which may be upgraded using Reassembly Chambers.";
            size = 3;
            consumePower(1.2f);
            plans = arc.struct.Seq.with(
                new UnitPlan(FRT1Units.seed, 2400f, ItemStack.with(Items.lead, 30, Items.silicon, 30)),
                new UnitPlan(FRT1Units.lancerDrone, 1200f, ItemStack.with(Items.lead, 15, Items.silicon, 15)),
                new UnitPlan(FRT1Units.mela, 3000f, ItemStack.with(Items.silicon, 30, Items.metaglass, 45)),
                new UnitPlan(FRT1Units.apis, 1200f, ItemStack.with(Items.lead, 15, Items.silicon, 15)),
                new UnitPlan(FRT1Units.alba, 3600f, ItemStack.with(Items.silicon, 25, Items.metaglass, 30, Items.titanium, 25)),
                new UnitPlan(FRT1Units.annax, 2400f, ItemStack.with(Items.scrap, 40, Items.silicon, 30, Items.lead, 20)),
                new UnitPlan(FRT1Units.sambuca, 3000f, ItemStack.with(Items.silicon, 40, Items.lead, 30, Items.titanium, 20)),
                new UnitPlan(FRT1Units.aedes, 1600f, ItemStack.with(Items.silicon, 30, Items.lead, 20, Items.graphite, 20))
            );
            requirements(Category.units, ItemStack.with(Items.copper, 90, Items.lead, 110, Items.silicon, 40, Items.metaglass, 60));
        }};

        basicReassembly = new Reconstructor("basic-reassembly-chamber") {{
            localizedName = "Basic Reassembly Chamber";
            description = "Upgrades units created in the Basic Factory to the next tier. Units produced by this Reassembly Chamber may be further upgraded.";
            health = 1550;
            size = 5;
            constructTime = 3600f;
            consumePower(0.8f);
            consumeItems(ItemStack.with(Items.silicon, 300, Items.graphite, 120, Items.titanium, 180, Items.metaglass, 100));
            addUpgrade(FRT1Units.seed, FRT2Units.sapling);
            addUpgrade(FRT1Units.lancerDrone, FRT2Units.alopex);
            addUpgrade(FRT1Units.mela, FRT2Units.cromis);
            addUpgrade(FRT1Units.apis, FRT2Units.procer);
            addUpgrade(FRT1Units.alba, FRT2Units.arvens);
            addUpgrade(FRT1Units.annax, FRT2Units.scofra);
            addUpgrade(FRT1Units.sambuca, FRT2Units.scorpio);
            addUpgrade(FRT1Units.aedes, FRT2Units.armiger);
            requirements(Category.units, ItemStack.with(Items.copper, 500, Items.lead, 800, Items.silicon, 700, Items.titanium, 800));
        }};

        advancedReassembly = new Reconstructor("advanced-reassembly-chamber") {{
            localizedName = "Advanced Reassembly Chamber";
            description = "Upgrades units to the 3rd tier. These upgraded units may be upgraded a final time.";
            health = 3400;
            size = 9;
            constructTime = 6000f;
            consumePower(13.3333333333333f);
            consumeLiquid(FRLiquids.neutronFluid, 0.4f);
            consumeItems(ItemStack.with(Items.silicon, 1000, Items.titanium, 900, Items.plastanium, 720));
            addUpgrade(FRT2Units.sapling, FRT3Units.plant);
            addUpgrade(FRT2Units.alopex, FRT3Units.kestrel);
            addUpgrade(FRT2Units.cromis, FRT3Units.arnux);
            addUpgrade(FRT2Units.procer, FRT3Units.ducalis);
            addUpgrade(FRT2Units.arvens, FRT3Units.aestiva);
            addUpgrade(FRT2Units.scofra, FRT3Units.auratus);
            addUpgrade(FRT2Units.scorpio, FRT3Units.springald);
            addUpgrade(FRT2Units.armiger, FRT3Units.onirion);
            requirements(Category.units, ItemStack.with(Items.lead, 2200, Items.titanium, 2100, Items.thorium, 900, Items.plastanium, 520, Items.phaseFabric, 640));
        }};

        progressiveAssembly = new Reconstructor("progressive-reassembly-chamber") {{
            localizedName = "Progressive Assembly Chamber";
            description = "Upgrades units to the 4th and final tier.";
            health = 7200;
            size = 13;
            constructTime = 21600f;
            consumePower(30f);
            consumeLiquid(FRLiquids.neutronFluid, 0.8f);
            consumeItems(ItemStack.with(Items.silicon, 800, FRItems.livingSteel, 600, Items.plastanium, 660, Items.surgeAlloy, 550, Items.phaseFabric, 450));
            addUpgrade(FRT3Units.plant, FRMothershipUnits.corax);
            addUpgrade(FRT3Units.kestrel, FRMothershipUnits.strahl);
            addUpgrade(FRT3Units.arnux, FRMothershipUnits.japonica);
            addUpgrade(FRT3Units.ducalis, FRMothershipUnits.hive);
            addUpgrade(FRT3Units.aestiva, FRMothershipUnits.altaic);
            addUpgrade(FRT3Units.auratus, FRMothershipUnits.lycosid);
            addUpgrade(FRT3Units.springald, FRMothershipUnits.onager);
            addUpgrade(FRT3Units.onirion, FRMothershipUnits.culiseta);
            requirements(Category.units, ItemStack.with(FRItems.livingSteel, 2000, Items.thorium, 1500, Items.silicon, 3800, Items.plastanium, 700, Items.phaseFabric, 700, Items.surgeAlloy, 920));
        }};

        regenerator = new RepairTower("unit-repair-field") {{
            localizedName = "Regenerator";
            description = "A tower that continuously heals all allied units in its range. Requires Cryofluid.";
            size = 2;
            range = 100f;
            healAmount = 1.5f;
            consumePower(7f);
            consumeLiquid(mindustry.content.Liquids.cryofluid, 0.18f);
            requirements(Category.units, ItemStack.with(Items.graphite, 110, Items.silicon, 110, Items.titanium, 100));
        }};
    }
}
