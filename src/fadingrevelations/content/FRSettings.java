package fadingrevelations.content;

import arc.Core;
import arc.scene.ui.CheckBox;
import arc.scene.ui.Slider;
import arc.scene.ui.layout.Table;
import arc.scene.ui.Tooltip;
import mindustry.Vars;
import mindustry.gen.Icon;
import mindustry.graphics.Pal;
import mindustry.ui.Styles;

/**
 * Mod settings, grouped into categories (New Horizon style):
 *
 * - Gameplay: hardcore, mix tech
 * - Visuals:  HUD version, core burn effect, custom effect intensity (slider)
 * - Units:    unit abilities toggle
 */
public class FRSettings {
    public static boolean hardcore, mixTech, showVersion, noCoreBurnEffect;
    /** Whether the mod's unit abilities (e.g. ramp-up) are active. */
    public static boolean unitAbilities = true;
    /** Intensity (0.25 - 1.0) of the mod's custom particle effects. Scales particle counts live. */
    public static float fxDetail = 1f;

    public static void init() {
        Core.settings.defaults("fr-hardcore", false);
        Core.settings.defaults("fr-mix-tech", false);
        Core.settings.defaults("fr-show-version", true);
        Core.settings.defaults("fr-no-core-burn-effect", false);
        Core.settings.defaults("fr-unit-abilities", true);
        Core.settings.defaults("fr-fx-detail", 100);

        hardcore = Core.settings.getBool("fr-hardcore");
        mixTech = Core.settings.getBool("fr-mix-tech");
        showVersion = Core.settings.getBool("fr-show-version");
        noCoreBurnEffect = Core.settings.getBool("fr-no-core-burn-effect");
        unitAbilities = Core.settings.getBool("fr-unit-abilities", true);
        fxDetail = Core.settings.getInt("fr-fx-detail", 100) / 100f;

        if (Vars.ui != null && Vars.ui.settings != null) {
            Vars.ui.settings.addCategory("Fading Revelations", Icon.settings, table -> table.pane(t -> {
                t.margin(16f);

                header(t, "fr.setting.gameplay", "Gameplay");

                check(t, "fr-hardcore", "[scarlet]Hardcore Mode[]",
                    "[gray]Enemy units have 5x health[].\n[gray]All enemy production is 3x faster[].\n[red]Requires restarting the sector.[]",
                    b -> hardcore = b);

                check(t, "fr-mix-tech", "[accent]Mix Tech[]",
                    "[gray]All content becomes available on[]\n[gray]all planets instead of being[]\n[gray]planet-exclusive.[]\n[orange]Requires restarting the game.[]",
                    b -> mixTech = b);

                header(t, "fr.setting.visuals", "Visuals");

                check(t, "fr-show-version", "[lightgray]Show version on HUD[]",
                    "[gray]Displays the mod version on the[]\n[gray]HUD screen.[]",
                    b -> showVersion = b);

                check(t, "fr-no-core-burn-effect", "[orange]Disable core burn effect[]",
                    "[gray]Prevents the burn/fire effect when[]\n[gray]the core is full and items overflow.[]\n[lightgray]Helps with lag on busy maps.[]",
                    b -> noCoreBurnEffect = b);

                slider(t, "fr-fx-detail", 25, 100, 5,
                    "fr.setting.fx-detail", "Custom effect intensity",
                    "[gray]Scales the particle count of the[]\n[gray]mod's custom effects.[]\n[lightgray]Lower values help with lag.[]",
                    v -> fxDetail = v / 100f);

                header(t, "fr.setting.units", "Units");

                check(t, "fr-unit-abilities", "[accent]Unit abilities[]",
                    "[gray]Enables the special abilities of[]\n[gray]some endgame units (e.g. fire rate[]\n[gray]ramp-up while shooting).[]",
                    b -> unitAbilities = b);
            }).growX().get().setForceScroll(false, true));
        }
    }

    /** Bundle lookup with an English fallback. */
    public static String bundle(String key, String fallback) {
        return Core.bundle == null ? fallback : Core.bundle.get(key, fallback);
    }

    private static void header(Table t, String key, String fallback) {
        t.label(() -> bundle(key, fallback)).color(Pal.accent).padTop(14f).row();
        t.image().color(Pal.accent).height(4f).growX().pad(4f).row();
    }

    private static void check(Table t, String key, String name, String tooltip, arc.func.Boolc onChange) {
        CheckBox box = new CheckBox(name);
        box.update(() -> box.setChecked(Core.settings.getBool(key)));
        box.changed(() -> {
            Core.settings.put(key, box.isChecked());
            onChange.get(box.isChecked());
        });
        box.left();
        box.addListener(new Tooltip(tt -> {
            tt.background(Styles.black6);
            tt.add(tooltip).width(300f);
        }));
        t.add(box).left().padTop(3f).row();
    }

    private static void slider(Table t, String key, int min, int max, int step, String nameKey, String nameFallback, String tooltip, arc.func.Intc onChange) {
        Slider slider = new Slider(min, max, step, false);
        slider.setValue(Core.settings.getInt(key));
        slider.changed(() -> {
            Core.settings.put(key, (int)slider.getValue());
            onChange.get((int)slider.getValue());
        });

        Table row = new Table();
        row.add(slider).growX().padRight(8f);
        row.label(() -> Core.settings.getInt(key) + "%").width(48f).right();
        row.addListener(new Tooltip(tt -> {
            tt.background(Styles.black6);
            tt.add(tooltip).width(300f);
        }));

        t.label(() -> bundle(nameKey, nameFallback)).color(Pal.accent).padTop(8f).left().row();
        t.add(row).growX().padTop(3f).row();
    }
}
