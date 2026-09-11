package fadingrevelations.ui;

import arc.Core;
import arc.math.Mathf;
import arc.scene.event.Touchable;
import arc.scene.ui.layout.Table;
import mindustry.Vars;
import mindustry.core.UI;
import mindustry.gen.Building;
import mindustry.ui.Styles;

import fadingrevelations.content.FRSettings;
import fadingrevelations.worlds.blocks.power.GridMonitor;
import fadingrevelations.worlds.blocks.power.GridMonitor.GridMonitorBuild;

/**
 * Satisfactory-style hover panel for the Grid Monitor: hold the cursor over
 * the block and a live chart of its power grid appears next to it - new
 * samples arrive every second, showing rises and falls of production,
 * consumption and battery storage, plus current numbers (production,
 * consumption, max generator output, battery charge).
 *
 * Client-side only; touch devices use the block's tap dialog instead.
 */
public class GridMonitorTooltip extends Table {
    private GridMonitorBuild current;
    private final Table panel = new Table();

    public GridMonitorTooltip() {
        setTouchable(Touchable.disabled);
        setVisible(false);
        add(panel);
    }

    /** Adds this tooltip to the HUD layer. */
    public void register() {
        Vars.ui.hudGroup.addChild(this);
    }

    @Override
    public void act(float delta) {
        super.act(delta);

        if (Vars.headless || Vars.ui == null || !Vars.state.isGame()) {
            hide();
            return;
        }

        //don't fight with placement ghosts
        var input = Vars.control.input;
        if (input == null || input.block != null || Vars.player == null) {
            hide();
            return;
        }

        Building b = Vars.world.buildWorld(Core.input.mouseWorldX(), Core.input.mouseWorldY());
        GridMonitorBuild monitor = b instanceof GridMonitorBuild ? (GridMonitorBuild)b : null;
        if (monitor == null || !monitor.interactable(Vars.player.team())) {
            hide();
            return;
        }

        if (monitor != current) {
            current = monitor;
            rebuild();
        }

        //follow the cursor, clamped to the screen
        pack();
        float x = Core.input.mouseX() + 18f;
        float y = Core.input.mouseY() - getHeight() - 18f;
        setPosition(
            Mathf.clamp(x, 4f, Core.graphics.getWidth() - getWidth() - 4f),
            Mathf.clamp(y, 4f, Core.graphics.getHeight() - getHeight() - 4f)
        );
        setVisible(true);
        toFront();
    }

    private void hide() {
        current = null;
        setVisible(false);
    }

    private void rebuild() {
        panel.clear();

        panel.background(Styles.black6);
        panel.touchable(Touchable.disabled);

        panel.add("[accent]" + FRSettings.bundle("fr.monitor.title", "Power Grid Monitor") + "[]").pad(4f).row();

        //live chart; new samples land every second
        panel.add(new GridMonitor.GridGraphElement(current)).size(220f, 110f).pad(4f).row();

        panel.table(t -> {
            t.left().defaults().left().pad(2f);
            t.label(() -> "[#84f491]" + FRSettings.bundle("fr.monitor.produced", "Production") + ":[] " +
                UI.formatAmount((long)(current.getLastProduced() * 60f)) + "/s").row();
            t.label(() -> "[#ff6e5e]" + FRSettings.bundle("fr.monitor.needed", "Consumption") + ":[] " +
                UI.formatAmount((long)(current.getLastNeeded() * 60f)) + "/s").row();
            t.label(() -> "[#ffd37f]" + FRSettings.bundle("fr.monitor.max-output", "Max output") + ":[] " +
                UI.formatAmount((long)(current.getMaxOutput() * 60f)) + "/s").row();
            t.label(() -> "[#ffd37f]" + FRSettings.bundle("fr.monitor.stored", "Stored") + ":[] " +
                UI.formatAmount((long)current.getLastStored()) + "/" + UI.formatAmount((long)current.getCapacity())).row();
            t.label(() -> (current.getLastNeeded() > 0f && current.getLastProduced() < current.getLastNeeded() ? "[red]" : "[green]") +
                FRSettings.bundle("fr.monitor.satisfaction", "Satisfaction") + ":[] " +
                Mathf.round(current.getSatisfaction() * 100f) + "%");
        }).pad(2f);
    }
}
