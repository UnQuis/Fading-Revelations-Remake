package fadingrevelations.content;

import arc.Core;
import arc.scene.ui.*;
import arc.scene.ui.layout.Table;
import mindustry.Vars;
import mindustry.gen.Icon;
import mindustry.ui.Styles;
import mindustry.ui.dialogs.SettingsMenuDialog;

public class FRSettings {
    public static boolean hardcore, mixTech, showVersion;

    public static void init() {
        Core.settings.defaults("fr-hardcore", false);
        Core.settings.defaults("fr-mix-tech", false);
        Core.settings.defaults("fr-show-version", true);

        hardcore = Core.settings.getBool("fr-hardcore");
        mixTech = Core.settings.getBool("fr-mix-tech");
        showVersion = Core.settings.getBool("fr-show-version");

        if (Vars.ui != null && Vars.ui.settings != null) {
            Vars.ui.settings.addCategory("Fading Revelations", Icon.settings, table -> {
                table.pref(new SettingsMenuDialog.SettingsTable.CheckSetting("fr-hardcore", false, null) {
                    @Override
                    public void add(SettingsMenuDialog.SettingsTable parent) {
                        CheckBox box = new CheckBox("[scarlet]Hardcore Mode[]");
                        box.update(() -> box.setChecked(Core.settings.getBool(name)));
                        box.changed(() -> {
                            Core.settings.put(name, box.isChecked());
                            hardcore = box.isChecked();
                        });
                        box.left();
                        addDesc(parent.add(box).left().padTop(3f).get());
                        parent.row();
                    }
                });

                table.pref(new SettingsMenuDialog.SettingsTable.CheckSetting("fr-mix-tech", false, null) {
                    @Override
                    public void add(SettingsMenuDialog.SettingsTable parent) {
                        CheckBox box = new CheckBox("[accent]Mix Tech[]");
                        box.update(() -> box.setChecked(Core.settings.getBool(name)));
                        box.changed(() -> {
                            Core.settings.put(name, box.isChecked());
                            mixTech = box.isChecked();
                        });
                        box.left();
                        addDesc(parent.add(box).left().padTop(3f).get());
                        parent.row();
                    }
                });

                table.pref(new SettingsMenuDialog.SettingsTable.CheckSetting("fr-show-version", true, null) {
                    @Override
                    public void add(SettingsMenuDialog.SettingsTable parent) {
                        CheckBox box = new CheckBox("[lightgray]Show version on HUD[]");
                        box.update(() -> box.setChecked(Core.settings.getBool(name)));
                        box.changed(() -> {
                            Core.settings.put(name, box.isChecked());
                            showVersion = box.isChecked();
                        });
                        box.left();
                        addDesc(parent.add(box).left().padTop(3f).get());
                        parent.row();
                    }
                });
            });
        }
    }
}
