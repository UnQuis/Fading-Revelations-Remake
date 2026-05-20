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

            for (Fi file : musicPath.list()) {
                String ext = file.extension();
                if (!ext.equals("mp3") && !ext.equals("ogg")) continue;

                try {
                    Music music = new Music(file);
                    Vars.control.sound.ambientMusic.add(music);
                    Log.info("[FR] Loaded music: " + file.name());
                } catch (Exception e) {
                    Log.err("[FR] Failed to load music: " + file.name(), e);
                }
            }
        });
    }
}
