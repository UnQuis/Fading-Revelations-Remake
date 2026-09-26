package fadingrevelations;

import mindustry.Vars;
import mindustry.io.SaveFileReader;

public class FROldContent {
    public static void init(){
        // MappableContent prefixes every mod content name with "<modInternalName>-", so
        // block.name is already e.g. "fading-revelations-remake-outpost". Registering
        // "me-" + block.name therefore produced "me-fading-revelations-remake-outpost", which no
        // legacy save or map ever contains - the fallback never matched. Legacy files hold
        // "me-outpost", so the mod prefix has to be stripped from the KEY while the VALUE stays
        // the current (prefixed) name that content.getByName() can resolve.
        String prefix = modNamePrefix();

        for(mindustry.world.Block block : Vars.content.blocks()){
            SaveFileReader.fallback.put("me-" + block.name, block.name);
            if(!prefix.isEmpty() && block.name.startsWith(prefix)){
                SaveFileReader.fallback.put("me-" + block.name.substring(prefix.length()), block.name);
            }
        }
    }

    /**
     * Derives the content name prefix from the mod's own content instead of hardcoding it, so a
     * mod.hjson rename cannot silently break legacy loading again.
     */
    private static String modNamePrefix(){
        String full = fadingrevelations.content.FRItems.livingSteel.name;   // "living-steel"
        String bare = "living-steel";
        if(full.length() > bare.length() && full.endsWith(bare)){
            return full.substring(0, full.length() - bare.length());
        }
        return "";
    }
}
