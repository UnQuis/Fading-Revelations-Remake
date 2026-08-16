package fadingrevelations;

import mindustry.Vars;
import mindustry.io.SaveFileReader;

public class FROldContent {
    public static void init() {
        for (mindustry.world.Block block : Vars.content.blocks()) {
            SaveFileReader.fallback.put("me-" + block.name, block.name);
        }
    }
}