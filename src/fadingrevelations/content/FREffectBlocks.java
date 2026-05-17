package fadingrevelations.content;
import arc.graphics.Color;
import mindustry.content.*;
import mindustry.type.*;
import mindustry.world.Block;
import mindustry.world.blocks.defense.*;
import mindustry.world.blocks.distribution.*;
import mindustry.world.blocks.storage.CoreBlock;
import mindustry.world.blocks.storage.Unloader;
import mindustry.world.blocks.campaign.LaunchPad;
import mindustry.content.UnitTypes;
import mindustry.gen.Sounds;

public class FREffectBlocks {
    public static Block outpost, miniOd, forceDome, fastUnloader, enhancedMendProjector, darkMender, bigLaunchPad,
            mainCore, coreLevel4, coreLevel5;

    public static void load() {
        outpost = new Outpost("outpost") {{
            localizedName = "Outpost";
            description = "An outpost that can be built anywhere on the map and transfers all items that are inputted directly to the main core.";
            health = 1200; size = 3; itemCapacity = 5;
            requirements(Category.effect, ItemStack.with(Items.copper, 3000, Items.lead, 2500, Items.silicon, 2200, Items.titanium, 2000, FRItems.livingSteelHard, 1000, Items.surgeAlloy, 600, FRItems.steelAlloy, 500));
        }};

        miniOd = new OverdriveProjector("mini-od") {{
            localizedName = "Tiny Overdrive Projector";
            description = "A tiny overdrive projector. Makes everything in its range faster but be aware that it also increases energy consumption!";
            range = 48; hasBoost = false; speedBoost = 1.15f; reload = 1f;
            consumePower(1.25f);
            requirements(Category.effect, ItemStack.with(Items.lead, 25, Items.titanium, 20, Items.silicon, 25));
        }};

        forceDome = new ForceProjector("force-dome") {{
            localizedName = "Force Dome";
            description = "A better Force Projector with more range and shield health. Cannot be boosted with Phase Fabric.";
            size = 4; health = 500; radius = 210; shieldHealth = 1500;
            cooldownNormal = 1.1f; cooldownBrokenBase = 0.9f;
            consumePower(5f);
            requirements(Category.effect, ItemStack.with(Items.lead, 300, Items.silicon, 265, Items.titanium, 150, Items.thorium, 200, Items.plastanium, 100));
        }};

        fastUnloader = new Unloader("fast-unloader") {{
            localizedName = "Advanced Unloader";
            description = "An unloader that unloads items way faster than the normal one and can also buffer some items.";
            size = 1; itemCapacity = 5; speed = 3;
            requirements(Category.effect, ItemStack.with(Items.silicon, 50, Items.titanium, 45));
        }};

        enhancedMendProjector = new MendProjector("enhanced-mend-projector") {{
            localizedName = "Repair Dome";
            description = "Heals nearby blocks rapidly.";
            health = 1000; size = 3; range = 120; reload = 100; healPercent = 5;
            consumePower(5f);
            consumeItem(Items.phaseFabric, 5).optional(true, true);
            requirements(Category.effect, ItemStack.with(Items.graphite, 60, Items.silicon, 140, Items.titanium, 150, Items.phaseFabric, 40));
        }};

        darkMender = new MendProjector("dark-mender") {{
            localizedName = "Dark Mender";
            description = "Heals nearby blocks in great amounts but needs a constant supply of living steel to do so.";
            health = 1350; size = 3; range = 80; reload = 120; consumesPower = false;
            hasPower = false; healPercent = 15;
            baseColor = Color.valueOf("6d5ac6"); phaseColor = Color.valueOf("6d5ac6");
            consumeItem(FRItems.livingSteel, 1).optional(false, false);
            requirements(Category.effect, ItemStack.with(Items.graphite, 60, Items.silicon, 140, Items.titanium, 150, FRItems.livingSteelHard, 120, Items.phaseFabric, 40));
        }};

        bigLaunchPad = new LaunchPad("big-launch-pad") {{
            localizedName = "Advanced Launch Pad";
            description = "Uses technology infused with living steel to launch items faster than the regular launch pad. Can be boosted by using water.";
            health = 740; launchTime = 780; lightColor = Color.valueOf("8c0291");
            itemCapacity = 200; size = 4;
            consumePower(10f);
            consumeLiquid(Liquids.water, 0.3f).boost();
            requirements(Category.effect, ItemStack.with(Items.lead, 700, FRItems.livingSteel, 800, Items.silicon, 700, Items.plastanium, 200, Items.phaseFabric, 200, FRItems.steelAlloy, 100));
        }};

        coreLevel4 = new CoreBlock("core-level-4") {{
            localizedName = "Core: Atom";
            description = "Core of the base. Very well armored and stores a good amount of resources.";
            size = 6; researchCostMultiplier = 0.5f; health = 8000; itemCapacity = 16000;
            unitType = UnitTypes.alpha; unitCapModifier = 32;
            requirements(Category.effect, ItemStack.with(Items.copper, 10000, Items.lead, 10000, Items.silicon, 6000, Items.titanium, 8000, Items.thorium, 10000, Items.plastanium, 1000, Items.phaseFabric, 1000));
        }};

        coreLevel5 = new CoreBlock("core-level-5") {{
            localizedName = "Core: Element";
            description = "Core of the base. Extremely well armored and stores a huge amount of resources.";
            size = 7; researchCostMultiplier = 0.5f; health = 9500; itemCapacity = 19000;
            unitType = UnitTypes.alpha; unitCapModifier = 42;
            requirements(Category.effect, ItemStack.with(Items.copper, 13000, Items.lead, 13000, Items.silicon, 9000, Items.titanium, 11000, Items.thorium, 12000, Items.plastanium, 2000, Items.phaseFabric, 2500, Items.surgeAlloy, 1500));
        }};

        mainCore = new CoreBlock("main-core") {{
            localizedName = "Main Core";
            description = "Core of the base. Unbelievably well armored and stores vast amounts of resources. Isn't defended by a unit but can instead build, mine and defend itself.";
            details = "An intelligent core that commands all other cores and that has fought a fierce battle in the past...";
            size = 8; researchCostMultiplier = 0.4f; health = 11200; itemCapacity = 22000;
            unitType = UnitTypes.oct; unitCapModifier = 56;
            requirements(Category.effect, ItemStack.with(Items.copper, 18000, Items.lead, 18000, Items.silicon, 11000, Items.titanium, 13000, Items.thorium, 15000, Items.plastanium, 3600, Items.phaseFabric, 5200, Items.surgeAlloy, 2300));
        }};
    }
}
