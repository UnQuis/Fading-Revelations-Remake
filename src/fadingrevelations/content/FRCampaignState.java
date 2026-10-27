package fadingrevelations.content;

import arc.Core;
import arc.Events;
import arc.struct.ObjectIntMap;
import arc.struct.ObjectSet;
import arc.util.Log;
import arc.util.Time;
import mindustry.Vars;
import mindustry.game.EventType;
import mindustry.game.Rules;
import mindustry.gen.Call;
import mindustry.io.JsonIO;

/**
 * Server-authoritative storage for campaign-wide progress.
 *
 * The values live in the world's {@link Rules#tags} while a game is running, so they are saved
 * with the sector and sent to clients through {@link Call#setRules(Rules)}. Core.settings is only
 * written by the host and only acts as the cross-sector/global save between games.
 */
public class FRCampaignState {
    private static final String SETTING_KEY = "fr-campaign-state";
    private static final String TAG_VERSION = "fr.state.version";
    private static final String TAG_STATE = "fr.state.state";

    public static final ObjectSet<String> lore = new ObjectSet<>();
    public static final ObjectSet<String> research = new ObjectSet<>();

    private static int version = 0;
    private static int seenVersion = -1;
    private static float lastSaveTick = -999f;
    private static float pollTimer = 0f;
    private static boolean loadedFromSettings = false;

    public static void load() {
        if (authority()) {
            loadSettings();
        }

        Events.on(EventType.WorldLoadEvent.class, event -> onWorldLoad());
        Events.run(EventType.Trigger.update, FRCampaignState::poll);
    }

    public static boolean authority() {
        return Vars.net == null || !Vars.net.client();
    }

    private static void loadSettings() {
        try {
            StateData data = Core.settings.getJson(SETTING_KEY, StateData.class, () -> null);
            if (data != null) {
                apply(data);
                loadedFromSettings = true;
                return;
            }
        } catch (Throwable t) {
            Log.err("FRCampaignState: error loading settings", t);
        }

        // Migrate the old per-system settings keys.
        FROrbitalRing.loadLegacyProgress();
        FRLore.loadLegacyProgress();
        FRTechTree.loadLegacyProgress();
        loadedFromSettings = FROrbitalRing.stage > 0 || !lore.isEmpty() || !research.isEmpty();
    }

    private static void onWorldLoad() {
        if (Vars.state == null || !Vars.state.isCampaign()) return;

        if (authority()) {
            // A save made by another host may contain state we have never seen. Import it once,
            // then the global settings file becomes the source of truth again.
            if (!loadedFromSettings && readRules()) {
                loadedFromSettings = true;
            }

            writeSettings();
            writeRules();
            FROrbitalRing.applyBonuses();
            FRTechTree.applyResearchToRules();
            sync();
        } else {
            readRules();
            FROrbitalRing.applyBonuses();
            FRTechTree.applyResearchToRules();
        }
    }

    /** Host-side save. Clients never write campaign progress. */
    public static void save(boolean force) {
        if (!authority() || Vars.state == null || !Vars.state.isCampaign()) return;
        if (!force && Time.time - lastSaveTick < 120f) return;

        lastSaveTick = Time.time;
        writeSettings();
        writeRules();
        sync();
    }

    private static void writeSettings() {
        try {
            Core.settings.putJson(SETTING_KEY, StateData.class, snapshot());
        } catch (Throwable t) {
            Log.err("FRCampaignState: error saving settings", t);
        }
    }

    private static void writeRules() {
        Rules rules = Vars.state == null ? null : Vars.state.rules;
        if (rules == null || rules.tags == null) return;

        version++;
        StateData data = snapshot();
        data.version = version;
        rules.tags.put(TAG_VERSION, Integer.toString(version));
        rules.tags.put(TAG_STATE, JsonIO.write(data));
    }

    private static boolean readRules() {
        Rules rules = Vars.state == null ? null : Vars.state.rules;
        if (rules == null || rules.tags == null) return false;

        String json = rules.tags.get(TAG_STATE);
        if (json == null || json.isEmpty()) return false;

        try {
            StateData data = JsonIO.read(StateData.class, json);
            if (data == null) return false;
            apply(data);
            seenVersion = data.version;
            return true;
        } catch (Throwable t) {
            Log.err("FRCampaignState: error reading rules state", t);
            return false;
        }
    }

    private static void sync() {
        if (Vars.net != null && Vars.net.server() && Vars.state != null) {
            Call.setRules(Vars.state.rules);
        }
    }

    /** Clients poll the rules tag; Call.setRules has no mod-visible callback. */
    private static void poll() {
        if (Vars.net == null || !Vars.net.client() || Vars.state == null || !Vars.state.isCampaign()) return;

        pollTimer += Time.delta;
        if (pollTimer < 30f) return;
        pollTimer = 0f;

        Rules rules = Vars.state.rules;
        if (rules == null || rules.tags == null) return;

        int current = rules.tags.getInt(TAG_VERSION, -1);
        if (current == seenVersion) return;

        if (readRules()) {
            FROrbitalRing.applyBonuses();
            FRTechTree.applyResearchToRules();
            seenVersion = current;
        }
    }

    private static void apply(StateData data) {
        FROrbitalRing.stage = Math.max(0, Math.min(FROrbitalRing.STAGES, data.ringStage));

        FROrbitalRing.stageProgress.clear();
        if (data.ringProgress != null) {
            FROrbitalRing.stageProgress.putAll(data.ringProgress);
        }
        FROrbitalRing.deliveredTotal = Math.max(0L, data.delivered);

        lore.clear();
        if (data.lore != null) {
            for (String id : data.lore) {
                if (id != null && !id.isEmpty()) lore.add(id);
            }
        }

        research.clear();
        if (data.research != null) {
            for (String id : data.research) {
                if (id != null && !id.isEmpty()) research.add(id);
            }
        }

        version = Math.max(0, data.version);
        seenVersion = data.version;
    }

    private static StateData snapshot() {
        StateData data = new StateData();
        data.version = version;
        data.ringStage = FROrbitalRing.stage;
        data.ringProgress = new ObjectIntMap<>();
        data.ringProgress.putAll(FROrbitalRing.stageProgress);
        data.delivered = FROrbitalRing.deliveredTotal;

        data.lore = toArray(lore);
        data.research = toArray(research);
        return data;
    }

    private static String[] toArray(ObjectSet<String> set) {
        String[] result = new String[set.size];
        int i = 0;
        for (String value : set) {
            result[i++] = value;
        }
        return result;
    }

    public static class StateData {
        public int version;
        public int ringStage;
        public ObjectIntMap<String> ringProgress;
        public long delivered;
        public String[] lore;
        public String[] research;

        public StateData() {}
    }
}
