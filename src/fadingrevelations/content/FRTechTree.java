package fadingrevelations.content;

import arc.Core;
import arc.Events;
import arc.util.Log;
import mindustry.Vars;
import mindustry.game.EventType.ResearchEvent;
import mindustry.ctype.Content;
import mindustry.ctype.ContentType;
import mindustry.ctype.UnlockableContent;
import mindustry.type.Planet;

public class FRTechTree {
    private static final String SETTING_KEY = "fr-global-research";
    private static final Planet[] planets = {FRPlanets.cerbero, FRPlanets.hathor, FRPlanets.cangirus};
    private static final java.util.Set<String> researched = new java.util.HashSet<>();

    public static void load() {
        loadGlobalProgress();

        Events.on(ResearchEvent.class, event -> {
            if (event.content == null) return;
            String name = event.content.name;
            
            if (researched.add(name)) {
                saveGlobalProgress();
            }
            
            unlockOnAllPlanets(event.content);
            Log.info("FRTechTree: Unlocked @ on all planets", name);
        });
    }

    private static void unlockOnAllPlanets(UnlockableContent content) {
        if (content == null) return;
        
        content.unlock();
        
        if (Vars.state.isCampaign()) {
            try {
                var rules = Vars.state.rules;
                if (rules != null && rules.researched != null && !rules.researched.contains(content)) {
                    rules.researched.add(content);
                }
            } catch (Throwable t) {
                Log.err("FRTechTree: Failed to add to researched", t);
            }
        }
    }

    private static void saveGlobalProgress() {
        String[] arr = researched.toArray(new String[0]);
        Core.settings.putJson(SETTING_KEY, arr);
    }

    private static void loadGlobalProgress() {
        try {
            String[] arr = Core.settings.getJson(SETTING_KEY, String[].class, () -> new String[0]);
            for (String name : arr) {
                researched.add(name);
            }

            for (String name : researched) {
                Content content = findContent(name);
                if (content instanceof UnlockableContent unlockable) {
                    unlockOnAllPlanets(unlockable);
                }
            }

            if (!researched.isEmpty()) {
                Log.info("FRTechTree: Loaded @ researched items", researched.size());
            }
        } catch (Throwable t) {
            Log.err("FRTechTree: Error loading progress", t);
        }
    }

    private static Content findContent(String name) {
        for (ContentType type : ContentType.all) {
            Content c = Vars.content.getByName(type, name);
            if (c != null) return c;
        }
        return null;
    }
}