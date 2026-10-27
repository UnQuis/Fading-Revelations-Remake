package fadingrevelations.content;

import arc.Core;
import arc.Events;
import arc.input.KeyCode;
import arc.math.Mathf;
import arc.struct.ObjectIntMap;
import arc.util.Time;
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
    static final ObjectIntMap<String> stageProgress = new ObjectIntMap<>();
    /** Running total across all stages; stageProgress is wiped on every stage-up. */
    static long deliveredTotal = 0L;

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
        Events.on(EventType.WorldLoadEvent.class, event -> applyBonuses());
        //stage 4: the finished ring overclocks the whole planet (see updateOrbitalBoost)
        Events.run(EventType.Trigger.update, FROrbitalRing::updateOrbitalBoost);
        Events.run(EventType.Trigger.update, FROrbitalRing::updateCutscene);
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

    /**
     * Total items accepted across all stages. stageProgress is cleared when a stage completes, so
     * the running total is kept separately and persisted alongside it.
     */
    public static long totalDelivered() {
        return deliveredTotal;
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
     * Contributes resources from a station at the given world position.
     * The position is used for the stage-completion cutscene camera pan; pass 0, 0 to skip it.
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

        if (accepted > 0) {
            deliveredTotal += accepted;
            saveProgress(false);
        }
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

            // cutscene: hold the camera on the station for a few seconds
            if (wx != 0f || wy != 0f) {
                startCutscene(wx, wy);
                Vars.ui.showInfoToast(msg, cutsceneDuration);
            } else {
                Vars.ui.showInfoToast(msg, 8f);
            }
        }
        applyBonuses();
        saveProgress(true);
    }

    //region cutscene

    private static final float cutsceneDuration = 5f;
    /**
     * How much closer the cutscene sits than the player's own zoom.
     *
     * {@code Renderer.minZoom}/{@code maxZoom} (1.5..6) are the *cutscene* range,
     * and vanilla's own comment calls maxZoom "zooming in" - so a cutscene can
     * only move towards the camera, never away from it. Multiplying by 1.3 is a
     * push in on the station.
     */
    private static final float cutsceneZoomIn = 1.3f;
    private static final float cutsceneEaseIn = 0.22f;

    private static float cutsceneLeft = 0f;
    private static float cutsceneToX, cutsceneToY, cutsceneFromX, cutsceneFromY, cutsceneScale;

    /**
     * Starts the stage-completion cutscene.
     *
     * The obvious implementation - set {@code input.logicCutscene} once and let
     * vanilla's camera handling do the rest - shows nothing, and that is what the
     * first version did. Three separate reasons, all verified against v160.5:
     *
     * <ol>
     * <li>{@code DesktopInput.update()} ends every frame with
     *     {@code Core.camera.position.lerpDelta(playerOrUnit, smoothCamera ? 0.08 : 1.0)}.
     *     Smooth camera is off by default on desktop, so that is a pull of 1.0 per
     *     second straight back onto the player, undoing whatever the cutscene did.
     * <li>The player is standing at the station they just fed, so panning the
     *     camera <i>to the station</i> is not a pan at all. The push-in on the
     *     station and the shake are what carry the beat.
     * <li>{@code logicCutsceneZoom} is not a zoom level. {@code Renderer.update}
     *     reads it as a 0..1 blend between {@code minZoom} (1.5) and {@code maxZoom}
     *     (6) and lerps {@code camerascale} toward the result. The old code set
     *     0.6, which is scale 4.2 - a hard zoom-in that snapped back when the
     *     five seconds ran out. There is no {@code Camera.zoom} field in v160.5;
     *     the scale lives on {@link mindustry.core.Renderer#camerascale}.
     * </ol>
     *
     * So the camera position is written directly from
     * {@link EventType.Trigger#update}, which fires after the input update, and
     * the scale is requested through {@code logicCutsceneZoom} so the renderer's
     * own easing moves it rather than fighting it. Any camera input from the
     * player cancels the shot immediately.
     */
    private static void startCutscene(float wx, float wy){
        if(Vars.headless || Vars.control == null || Core.camera == null
            || Vars.state.isMenu() || Vars.renderer == null) return;

        cutsceneToX = wx;
        cutsceneToY = wy;
        cutsceneFromX = Core.camera.position.x;
        cutsceneFromY = Core.camera.position.y;
        // `camerascale` is the live value; `targetscale` is recomputed every frame
        // from the input flags and must not be used as "where we started".
        cutsceneScale = Mathf.clamp(Vars.renderer.camerascale * cutsceneZoomIn,
            Vars.renderer.minZoom, Vars.renderer.maxZoom);
        cutsceneLeft = cutsceneDuration;
        // A short shake, so the stage registering lands even when the player was
        // already looking at the station and there is no pan to see.
        Vars.renderer.shake(6f, 0.6f);
    }

    private static void updateCutscene(){
        if(cutsceneLeft <= 0f || Core.camera == null || Vars.control == null) return;

        if(Vars.state.isMenu()){
            releaseCutscene();
            return;
        }

        // any attempt to move the camera hands control straight back. arc.Input
        // exposes mouse movement as deltaX()/deltaY(); v160.5 has no scroll
        // accessor and no mouseDeltaX.
        if(Core.input != null && (Core.input.deltaX() != 0 || Core.input.deltaY() != 0
            || Core.input.keyDown(KeyCode.escape))){
            releaseCutscene();
            return;
        }

        cutsceneLeft -= Time.delta;
        if(cutsceneLeft <= 0f){
            releaseCutscene();
            return;
        }

        // smoothstep in over the first fifth of the shot, then hold
        float t = 1f - cutsceneLeft / cutsceneDuration;
        float e = Mathf.clamp(t / cutsceneEaseIn);
        float k = e * e * (3f - 2f * e);   // smoothstep, arc.Mathf has none

        var input = Vars.control.input;
        input.logicCutscene = true;
        input.logicHideHud = true;
        input.logicCamPan.set(cutsceneToX, cutsceneToY);
        input.logicCamSpeed = 6f;
        // blend factor between minZoom and maxZoom, not a scale
        input.logicCutsceneZoom = Mathf.clamp(
            (Mathf.lerp(Vars.renderer.camerascale, cutsceneScale, k) - Vars.renderer.minZoom)
                / (Vars.renderer.maxZoom - Vars.renderer.minZoom), 0f, 1f);

        Core.camera.position.set(Mathf.lerp(cutsceneFromX, cutsceneToX, k),
            Mathf.lerp(cutsceneFromY, cutsceneToY, k));
    }

    private static void releaseCutscene(){
        cutsceneLeft = 0f;
        if(Vars.control == null) return;
        Vars.control.input.logicCutscene = false;
        Vars.control.input.logicHideHud = false;
    }

    //endregion

    //endregion

    //region orbital boost

    /** Stage 4 bonus: +200% speed for every overdrivable block on the planet. */
    // Stage bonuses, as multipliers. The station UI reads these instead of hardcoding the
    // percentages, so changing a bonus can never leave the display lying.
    /** Stage 1: mining speed. */
    public static final float miningBonus = 1.15f;
    /** Stage 2: build speed. */
    public static final float buildBonus = 1.2f;
    /** Stage 3: unit and block health. */
    public static final float healthBonus = 1.12f;
    /** Stage 4: unit damage. */
    public static final float damageBonus = 1.15f;

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

    public static void applyBonuses() {
        if (!Vars.state.isCampaign()) return;

        var rules = Vars.state.rules;
        var team = rules.teams.get(rules.defaultTeam);
        if (team == null) return;

        //set (not multiply): campaign base values are the defaults, and setting
        //fixed targets keeps this idempotent - a sector that saved with the
        //bonuses already baked into its rules never stacks them on re-entry
        team.unitMineSpeedMultiplier = stage >= 1 ? miningBonus : 1f;
        team.buildSpeedMultiplier = stage >= 2 ? buildBonus : 1f;
        team.unitHealthMultiplier = stage >= 3 ? healthBonus : 1f;
        team.blockHealthMultiplier = stage >= 3 ? healthBonus : 1f;
        team.unitDamageMultiplier = stage >= 4 ? damageBonus : 1f;

        int baseCap = rules.tags.getInt("fr.unitcap.base", -1);
        if (baseCap < 0) {
            baseCap = rules.unitCap;
            // Old versions used max(unitCap, 16), so exactly 16 is probably the bonus,
            // not the map's base cap. Migrate that case without stacking another +16.
            if (stage >= STAGES && baseCap == 16) baseCap = 0;
            rules.tags.put("fr.unitcap.base", String.valueOf(baseCap));
        }
        rules.unitCap = baseCap + (stage >= STAGES ? 16 : 0);
    }

    /** Throttled save so conveyor-fed stations don't serialize every tick. */
    private static void saveProgress(boolean force) {
        FRCampaignState.save(force);
    }

    static void loadLegacyProgress() {
        try {
            StageData data = Core.settings.getJson(SETTING_KEY, StageData.class, () -> new StageData(0, null, 0L));
            stage = Math.max(0, Math.min(STAGES, data.stage));
            stageProgress.clear();
            if (data.progress != null) {
                stageProgress.putAll(data.progress);
            }
            deliveredTotal = data.delivered;
            if (stage > 0) {
                arc.util.Log.info("FROrbitalRing: loaded stage @ ({} delivered)", stage, deliveredTotal);
            }
        } catch (Throwable t) {
            arc.util.Log.err("FROrbitalRing: error loading progress", t);
            stage = 0;
        }
    }

    private static class StageData {
        public int stage;
        public ObjectIntMap<String> progress;
        /** absent in saves written before this field existed; treated as "unknown" */
        public long delivered;

        public StageData() {}

        public StageData(int stage, ObjectIntMap<String> progress, long delivered) {
            this.stage = stage;
            this.progress = progress;
            this.delivered = delivered;
        }
    }

    //endregion
}
