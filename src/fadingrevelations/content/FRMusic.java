package fadingrevelations.content;

import arc.Events;
import arc.audio.Music;
import arc.files.Fi;
import arc.util.Log;
import mindustry.Vars;
import mindustry.game.EventType.MusicRegisterEvent;

public class FRMusic {
    public static void load() {
        if (Vars.headless) return;

        Events.on(MusicRegisterEvent.class, event -> {
            Fi musicPath = Vars.mods.locateMod("fading-revelations-remake").root.child("music");

            for (String name : new String[]{"mysterious-world", "impending-doom"}) {
                Fi file = musicPath.child(name + ".mp3");
                if (file.exists()) {
                    try {
                        Music music = new Music(file);
                        Vars.control.sound.ambientMusic.add(music);
                        Log.info("[FR] Loaded music: " + name);
                    } catch (Exception e) {
                        Log.err("[FR] Failed to load music: " + name, e);
                    }
                } else {
                    Log.warn("[FR] Music file not found: " + file.path());
                }
            }
        });
    }
}
