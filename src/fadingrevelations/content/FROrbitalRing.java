package fadingrevelations.content;

import arc.Core;
import arc.Events;
import arc.struct.ObjectIntMap;
import mindustry.Vars;
import mindustry.content.Items;
import mindustry.game.EventType;
import mindustry.type.Item;
import mindustry.type.ItemStack;

/**
 * Global "Orbital Ring" campaign project for Cangirus.
 *
 * Players feed resources into Orbital Ring Stations built on Cangirus;
 * progress accumulates across all sectors and persists in settings
 * (same approach as FRTechTree's global research progress).
 *
 * Each of the 4 stages adds one quadrant of the ring around the planet
 * (see FROrbitalRingMesh) and grants a permanent campaign bonus:
 *
 * - Stage 1 Framework:      +15% unit mining speed
 * - Stage 2 Hull:           +20% build speed
 * - Stage 3 Habitat ring:   +12% unit & block health
 * - Stage 4 Full ring:      +15% unit damage, +16 unit cap
 */
public class FROrbitalRing {
    public static final int STAGES = 4;

    /** Current completed stage, 0..4. */
    public static int stage = 0;

    private static final String SETTING_KEY = "fr-orbital-ring";
    private static final ObjectIntMap<String> stageProgress = new ObjectIntMap<>();

    public static final ItemStack[][] stageRequirements = {
        {
            new ItemStack(Items.copper, 15000),
            new ItemStack(Items.lead, 15000),
            new ItemStack(Items.silicon, 10000)
        },
        {
            new ItemStack(Items.titanium, 12000),
            new ItemStack(Items.graphite, 9000),
            new ItemStack(FRItems.livingSteel, 8000)
        },
        {
            new ItemStack(Items.thorium, 10000),
            new ItemStack(Items.plastanium, 6000),
            new ItemStack(FRItems.steelAlloy, 4000)
        },
        {
            new ItemStack(Items.surgeAlloy, 6000),
            new ItemStack(Items.phaseFabric, 4000),
            new ItemStack(FRItems.optiCrystal, 2000),
            new ItemStack(FRItems.nanoFabric, 1000)
        }
    };

    public static void load() {
        loadProgress();

        Events.on(EventType.WorldLoadEvent.class, event -> applyBonuses());
        //stage 4: the finished ring overclocks the whole planet (see updateOrbitalBoost)
        Events.run(EventType.Trigger.update, FROrbitalRing::updateOrbitalBoost);
    }

    //region progress

    /** Requirements of the current (incomplete) stage; null when finished. */
    public static ItemStack[] currentRequirements() {
        return stage >= STAGES ? null : stageRequirements[stage];
    }

    /** Whether the station should accept this item right now. */
    public static boolean accepts(Item item) {
        return remaining(item) > 0;
    }

    /** How much more of this item the current stage needs. */
    public static int remaining(Item item) {
        if (stage >= STAGES) return 0;
        int need = 0;
        for (ItemStack stack : stageRequirements[stage]) {
            if (stack.item == item) {
                need = stack.amount;
                break;
            }
        }
        if (need == 0) return 0;
        return Math.max(0, need - stageProgress.get(item.name, 0));
    }

    /** Total 0..1 progress of the current stage. */
    public static float stageProgressFraction() {
        if (stage >= STAGES) return 1f;
        float total = 0f, done = 0f;
        for (ItemStack stack : stageRequirements[stage]) {
            total += stack.amount;
            done += Math.min(stageProgress.get(stack.item.name, 0), stack.amount);
        }
        return total <= 0f ? 0f : done / total;
    }

    /**
     * Contributes resources from a station. Handles stage rollover.
     * @return how many items were actually consumed.
     */
    public static int contribute(Item item, int amount) {
        return contribute(item, amount, 0f, 0f);
    }

    /**
     * Contributes resources from a station at the given world position.
     * The position is used for the stage-completion cutscene camera pan.
     * @return how many items were actually consumed.
     */
    public static int contribute(Item item, int amount, float stationX, float stationY) {
        int accepted = 0;

        while (amount > 0 && stage < STAGES) {
            int remaining = remaining(item);
            if (remaining <= 0) break;

            int used = Math.min(remaining, amount);
            stageProgress.increment(item.name, 0, used);
            accepted += used;
            amount -= used;

            if (remaining(item) <= 0 && stageComplete()) {
                completeStage(stationX, stationY);
            }
        }

        if (accepted > 0) saveProgress(false);
        return accepted;
    }

    private static boolean stageComplete() {
        if (stage >= STAGES) return false;
        for (ItemStack stack : stageRequirements[stage]) {
            if (stageProgress.get(stack.item.name, 0) < stack.amount) return false;
        }
        return true;
    }

    /** Called when a stage completes. If wx/wy are given, triggers a camera cutscene. */
    private static void completeStage(float wx, float wy) {
        stageProgress.clear();
        stage++;

        if (!Vars.headless && Vars.ui != null) {
            String stageName = FRSettings.bundle("fr.ring.stage." + stage, "Stage " + stage);
            String msg = FRSettings.bundle("fr.ring.stage-complete", "Orbital Ring stage complete") + ":\n" + stageName;

            // cutscene: pan camera to the station and zoom in
            if (wx != 0f || wy != 0f) {
                var input = Vars.control.input;
                input.logicCutscene = true;
                input.logicCamPan.set(wx, wy);
                input.logicCamSpeed = 3f;
                input.logicCutsceneZoom = 0.6f; // zoom in a bit

                Vars.ui.showInfoToast(msg, 6f);

                // restore camera after 5 seconds
                arc.util.Time.run(5f * 60f, () -> {
                    if (Vars.control.input.logicCutscene) {
                        Vars.control.input.logicCutscene = false;
                    }
                });
            } else {
                Vars.ui.showInfoToast(msg, 8f);
            }
        }
        applyBonuses();
        saveProgress(true);
    }

    private static void completeStage() {
        completeStage(0f, 0f);
    }

    //endregion

    //region orbital boost

    /** Stage 4 bonus: +200% speed for every overdrivable block on the planet. */
    public static final float BOOST_MULTIPLIER = 3f;
    /** Boost refresh period & duration, in ticks. */
    private static final float BOOST_DURATION = 120f;
    private static int boostTimer = 0;

    /** Whether the finished-ring planet-wide overdrive is currently active here. */
    public static boolean boostActive() {
        return stage >= STAGES
            && Vars.state.isCampaign()
            && Vars.state.rules.sector != null
            && Vars.state.rules.sector.planet == FRPlanets.cangirus;
    }

    /**
     * The completed Orbital Ring acts as a planet-wide overdrive: every
     * overdrivable building of the player's team works at +200% speed
     * (same mechanism as the vanilla Overdrive Projector - applyBoost).
     */
    private static void updateOrbitalBoost() {
        if (!boostActive()) return;
        if (++boostTimer < 60) return; //refresh once a second; duration keeps it smooth
        boostTimer = 0;

        var team = Vars.state.rules.defaultTeam;
        for (var build : mindustry.gen.Groups.build) {
            if (build.team == team && build.block.canOverdrive) {
                build.applyBoost(BOOST_MULTIPLIER, BOOST_DURATION);
            }
        }
    }

    //endregion

    //region persistence & bonuses

    private static void applyBonuses() {
        if (!Vars.state.isCampaign()) return;

        var rules = Vars.state.rules;
        var team = rules.teams.get(rules.defaultTeam);
        if (team == null) return;

        //set (not multiply): campaign base values are the defaults, and setting
        //fixed targets keeps this idempotent - a sector that saved with the
        //bonuses already baked into its rules never stacks them on re-entry
        team.unitMineSpeedMultiplier = stage >= 1 ? 1.15f : 1f;
        team.buildSpeedMultiplier = stage >= 2 ? 1.2f : 1f;
        if (stage >= 3) {
            team.unitHealthMultiplier = 1.12f;
            team.blockHealthMultiplier = 1.12f;
        }
        team.unitDamageMultiplier = stage >= 4 ? 1.15f : 1f;
        if (stage >= 4) {
            //on top of the core-based cap (unitCapVariable campaign rules)
            rules.unitCap = Math.max(rules.unitCap, 16);
        }
    }

    /** Throttled save so conveyor-fed stations don't serialize every tick. */
    private static void saveProgress(boolean force) {
        if (!force && arc.util.Time.time - lastSaveTick < 120f) return;
        lastSaveTick = arc.util.Time.time;
        Core.settings.putJson(SETTING_KEY, StageData.class, new StageData(stage, stageProgress));
    }

    private static float lastSaveTick = -999f;

    private static void loadProgress() {
        try {
            StageData data = Core.settings.getJson(SETTING_KEY, StageData.class, () -> new StageData(0, null));
            stage = Math.max(0, Math.min(STAGES, data.stage));
            stageProgress.clear();
            if (data.progress != null) {
                stageProgress.putAll(data.progress);
            }
            if (stage > 0) {
                arc.util.Log.info("FROrbitalRing: loaded stage @", stage);
            }
        } catch (Throwable t) {
            arc.util.Log.err("FROrbitalRing: error loading progress", t);
            stage = 0;
        }
    }

    private static class StageData {
        public int stage;
        public ObjectIntMap<String> progress;

        public StageData() {}

        public StageData(int stage, ObjectIntMap<String> progress) {
            this.stage = stage;
            this.progress = progress;
        }
    }

    //endregion
}
