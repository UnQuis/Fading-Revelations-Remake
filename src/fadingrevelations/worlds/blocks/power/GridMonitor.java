package fadingrevelations.worlds.blocks.power;

import arc.graphics.Color;
import arc.graphics.g2d.Draw;
import arc.graphics.g2d.Fill;
import arc.graphics.g2d.Font;
import arc.graphics.g2d.Lines;
import arc.scene.Element;
import arc.scene.ui.layout.Table;
import arc.struct.FloatSeq;
import arc.util.Time;
import arc.util.io.Reads;
import arc.util.io.Writes;
import mindustry.Vars;
import mindustry.core.UI;
import mindustry.graphics.Fonts;
import mindustry.graphics.Pal;
import mindustry.ui.Bar;
import mindustry.ui.Styles;
import mindustry.ui.dialogs.BaseDialog;
import mindustry.world.blocks.power.PowerNode;

import fadingrevelations.content.FRSettings;

/**
 * A power node that shows a live chart of its grid: production, consumption
 * and battery storage, sampled once per second over the last few minutes.
 *
 * Tap the block to open the graph; link it like any other node to pick the
 * grid to monitor. Purely informational - the idea of an in-game power graph
 * display is borrowed from the MI2-Utilities mod (own implementation).
 *
 * Note: samples are collected where block logic runs, so in multiplayer the
 * chart is only filled on the server / singleplayer session.
 */
public class GridMonitor extends PowerNode {
    private static final Color producedColor = Color.valueOf("84f491");
    private static final Color neededColor = Color.valueOf("ff6e5e");
    private static final Color storedColor = Color.valueOf("ffd37f");

    /** Ticks between samples. */
    public int sampleInterval = 60;
    /** Number of samples kept (3 minutes at 60 tps). */
    public int historyLength = 180;

    public GridMonitor(String name) {
        super(name);
        update = true; //PowerNode disables updates by default; sampling needs them
        maxNodes = 2;
        laserRange = 9f;
        emitLight = true;
        lightRadius = 30f;
        lightColor = Color.valueOf("ffd37f").cpy().a(0.55f);
    }

    public class GridMonitorBuild extends PowerNodeBuild {
        public final FloatSeq produced = new FloatSeq(historyLength);
        public final FloatSeq needed = new FloatSeq(historyLength);
        public final FloatSeq stored = new FloatSeq(historyLength);
        protected float sampleTimer = 0f;

        @Override
        public void updateTile() {
            if (power == null || power.graph == null) return;

            sampleTimer += Time.delta * timeScale;
            if (sampleTimer < sampleInterval) return;
            sampleTimer -= sampleInterval;

            pushSample(produced, power.graph.getLastPowerProduced());
            pushSample(needed, power.graph.getLastPowerNeeded());
            pushSample(stored, power.graph.getLastPowerStored());
        }

        private void pushSample(FloatSeq seq, float value) {
            seq.add(value);
            if (seq.size > historyLength) {
                seq.removeRange(0, seq.size - historyLength - 1);
            }
        }

        @Override
        public void tapped() {
            super.tapped();
            if (!Vars.headless && Vars.ui != null) {
                showGraph();
            }
        }

        public void showGraph() {
            BaseDialog dialog = new BaseDialog(FRSettings.bundle("fr.monitor.title", "Power Grid Monitor"));
            dialog.addCloseButton();

            dialog.cont.add(new GridGraphElement(this)).growX().height(190f).pad(6f).row();

            dialog.cont.table(Styles.black6, t -> {
                t.add("[#84f491]■ " + FRSettings.bundle("fr.monitor.produced", "Production")).left();
                t.add("[#ff6e5e]■ " + FRSettings.bundle("fr.monitor.needed", "Consumption")).left().padLeft(14f);
                t.add("[#ffd37f]■ " + FRSettings.bundle("fr.monitor.stored", "Stored")).left().padLeft(14f);
            }).pad(4f).row();

            dialog.cont.table(bars -> {
                bars.add(new Bar(
                    () -> FRSettings.bundle("fr.monitor.produced", "Production") + ": " + UI.formatAmount((long)(getLastProduced() * 60f)) + "/s",
                    () -> producedColor,
                    () -> getLastProduced() <= 0f ? 0f : Math.min(1f, getLastProduced() / getMaxScale())
                )).growX().height(22f).pad(2f).row();
                bars.add(new Bar(
                    () -> FRSettings.bundle("fr.monitor.needed", "Consumption") + ": " + UI.formatAmount((long)(getLastNeeded() * 60f)) + "/s",
                    () -> neededColor,
                    () -> getLastProduced() <= 0f ? 1f : Math.min(1f, getLastNeeded() / Math.max(getLastProduced(), getMaxScale()))
                )).growX().height(22f).pad(2f).row();
                bars.add(new Bar(
                    () -> FRSettings.bundle("fr.monitor.stored", "Stored") + ": " + UI.formatAmount((long)getLastStored()) + "/" + UI.formatAmount((long)getCapacity()),
                    () -> storedColor,
                    () -> getCapacity() <= 0f ? 0f : Math.min(1f, getLastStored() / getCapacity())
                )).growX().height(22f).pad(2f).row();
            }).growX().padTop(4f);

            dialog.show();
        }

        //graph accessors, safe when unlinked
        public float getLastProduced() {
            return power != null && power.graph != null ? power.graph.getLastPowerProduced() : 0f;
        }
        public float getLastNeeded() {
            return power != null && power.graph != null ? power.graph.getLastPowerNeeded() : 0f;
        }
        public float getLastStored() {
            return power != null && power.graph != null ? power.graph.getLastPowerStored() : 0f;
        }
        public float getCapacity() {
            return power != null && power.graph != null ? power.graph.getTotalBatteryCapacity() : 0f;
        }

        /** Max of production/consumption across history, for chart scaling. */
        public float getMaxScale() {
            float max = 1f;
            for (int i = 0; i < produced.size; i++) max = Math.max(max, produced.get(i));
            for (int i = 0; i < needed.size; i++) max = Math.max(max, needed.get(i));
            return max;
        }

        @Override
        public byte version() {
            return 1;
        }

        @Override
        public void write(Writes write) {
            super.write(write);
            writeShot(write, produced);
            writeShot(write, needed);
            writeShot(write, stored);
        }

        @Override
        public void read(Reads read, byte revision) {
            super.read(read, revision);
            if (revision >= 1) {
                readShot(read, produced);
                readShot(read, needed);
                readShot(read, stored);
            }
        }

        private void writeShot(Writes write, FloatSeq seq) {
            write.s(seq.size);
            for (int i = 0; i < seq.size; i++) write.f(seq.get(i));
        }

        private void readShot(Reads read, FloatSeq seq) {
            seq.clear();
            int n = read.s();
            for (int i = 0; i < n && i < historyLength; i++) seq.add(read.f());
        }
    }

    /** Live line chart of the grid history, drawn in UI space. */
    public static class GridGraphElement extends Element {
        private static final FloatSeq scratch = new FloatSeq();

        public final GridMonitorBuild build;

        public GridGraphElement(GridMonitorBuild build) {
            this.build = build;
        }

        @Override
        public void draw() {
            super.draw();

            float w = width, h = height;

            //background
            Draw.color(Pal.darkerGray);
            Fill.crect(x, y, w, h);

            //horizontal grid lines
            Draw.color(Color.gray.cpy().a(0.2f));
            Lines.stroke(1f);
            for (int i = 1; i <= 3; i++) {
                Lines.line(x, y + h * i / 4f, x + w, y + h * i / 4f);
            }

            Font font = Fonts.def;

            if (build.produced.size < 2) {
                font.setColor(Color.lightGray);
                font.draw(FRSettings.bundle("fr.monitor.collecting", "Collecting data..."), x + w / 2f - 40f, y + h / 2f);
                Draw.reset();
                return;
            }

            float maxVal = build.getMaxScale() * 60f; //per second

            //produced / needed share the scale
            Lines.stroke(2f);
            drawSeries(build.produced, producedColor, maxVal / 60f);
            drawSeries(build.needed, neededColor, maxVal / 60f);

            //stored uses the battery capacity scale
            float capacity = build.getCapacity();
            if (capacity > 0f) {
                drawSeries(build.stored, storedColor, capacity);
            }

            //scale label
            font.setColor(Color.lightGray);
            font.draw(UI.formatAmount((long)maxVal) + "/s", x + 4f, y + h - 4f);

            Draw.reset();
        }

        private void drawSeries(FloatSeq seq, Color color, float scale) {
            if (seq.size < 2 || scale <= 0f) return;

            scratch.clear();
            int n = seq.size;
            for (int i = 0; i < n; i++) {
                float px = x + width * i / (float)(n - 1);
                float py = y + Math.min(1f, seq.get(i) / scale) * (height - 6f) + 3f;
                scratch.add(px, py);
            }

            Lines.color(color);
            Lines.polyline(scratch, false);
        }
    }
}
