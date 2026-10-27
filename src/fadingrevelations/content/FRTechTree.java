package fadingrevelations.content;

import arc.Core;
import arc.Events;
import arc.util.Log;
import mindustry.Vars;
import mindustry.ctype.Content;
import mindustry.ctype.ContentType;
import mindustry.ctype.UnlockableContent;
import mindustry.game.EventType.ResearchEvent;
import mindustry.net.NetConnection;
import mindustry.type.Planet;

public class FRTechTree {
    private static final String LEGACY_SETTING_KEY = "fr-global-research";
    private static final Planet[] planets = {FRPlanets.cerbero, FRPlanets.hathor, FRPlanets.cangirus};

    public static void load() {
        Events.on(ResearchEvent.class, event -> {
            if (event.content == null || !isResearchable(event.content)) return;
            UnlockableContent content = event.content;

            if (Vars.net.client()) {
                // Research happens in the client's dialog. Tell the host, otherwise the
                // researched content is lost on the next rules sync. Add it locally as well so
                // the dialog updates immediately; the host's next setRules is authoritative.
                Vars.net.send(new FRCampaignPacket(content), true);
                content.unlock();
                if (Vars.state.isCampaign() && Vars.state.rules != null
                    && Vars.state.rules.researched != null) {
                    Vars.state.rules.researched.add(content);
                }
                return;
            }

            research(content);
        });
    }

    /** Only this mod's tech-tree content may be recorded by the global research tracker. */
    static boolean isResearchable(UnlockableContent content) {
        if (content == null || content.techNode == null || content.minfo == null || content.minfo.mod == null) {
            return false;
        }
        return content.minfo.mod == Vars.mods.getMod(fadingrevelations.FadingRevelationsMod.class);
    }

    static void research(UnlockableContent content) {
        if (!isResearchable(content)) return;

        String id = id(content);
        boolean added = FRCampaignState.research.add(id);

        content.unlock();
        if (!Vars.net.client() && Vars.state.isCampaign() && Vars.state.rules != null
            && Vars.state.rules.researched != null) {
            Vars.state.rules.researched.add(content);
        }

        if (added) {
            FRCampaignState.save(true);
        }
        Log.info("FRTechTree: Unlocked [{}] globally", content.name);
    }

    static void researchFromNetwork(UnlockableContent content, NetConnection connection) {
        // The host validates the ID; a client cannot unlock arbitrary content.
        research(content);
    }

    /** Adds every globally researched entry to the current sector's rules. */
    public static void applyResearchToRules() {
        if (Vars.net.client() || Vars.state == null || !Vars.state.isCampaign()
            || Vars.state.rules == null || Vars.state.rules.researched == null) {
            return;
        }

        for (String id : FRCampaignState.research) {
            Content content = findContent(id);
            if (content instanceof UnlockableContent unlockable && isResearchable(unlockable)) {
                Vars.state.rules.researched.add(unlockable);
                unlockable.unlock();
            }
        }
    }

    static String id(UnlockableContent content) {
        return content.getContentType().name() + ":" + content.name;
    }

    /** Resolves a ContentType:name ID without relying on globally unique content names. */
    public static Content findContent(String id) {
        if (id == null) return null;

        int split = id.indexOf(':');
        if (split <= 0 || split + 1 >= id.length()) return null;

        try {
            ContentType type = ContentType.valueOf(id.substring(0, split));
            return Vars.content.getByName(type, id.substring(split + 1));
        } catch (IllegalArgumentException ignored) {
            return null;
        }
    }

    static void loadLegacyProgress() {
        try {
            String[] names = Core.settings.getJson(LEGACY_SETTING_KEY, String[].class, () -> new String[0]);
            for (String name : names) {
                Content content = findLegacyContent(name);
                if (content instanceof UnlockableContent unlockable && isResearchable(unlockable)) {
                    FRCampaignState.research.add(id(unlockable));
                }
            }
            if (!FRCampaignState.research.isEmpty()) {
                Log.info("FRTechTree: migrated {} researched items", FRCampaignState.research.size);
            }
        } catch (Throwable t) {
            Log.err("FRTechTree: error migrating progress", t);
        }
    }

    private static Content findLegacyContent(String name) {
        for (ContentType type : ContentType.all) {
            Content content = Vars.content.getByName(type, name);
            if (content != null) return content;
        }
        return null;
    }
}
