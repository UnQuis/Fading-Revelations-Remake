package fadingrevelations.worlds.blocks.defense;

import arc.graphics.Color;
import arc.math.Mathf;
import arc.struct.Queue;
import arc.util.Time;
import mindustry.Vars;
import mindustry.gen.Building;
import mindustry.graphics.Drawf;
import mindustry.ui.Bar;
import mindustry.world.blocks.defense.Wall;
import mindustry.world.meta.BuildVisibility;

/**
 * Debug/sandbox-only DPS target. Invincible wall that tracks incoming damage
 * and displays rolling DPS when selected. Tapping resets counters.
 *
 * Reference: abomb4/super-cheat DPS wall (MIT), adapted for Fading Revelations.
 */
public class DPSTarget extends Wall {

    /** Ticks over which DPS is averaged (60 = 1 second). */
    public int dpsWindow = 60;

    public DPSTarget(String name) {
        super(name);
        update = true;
        solid = true;
        inEditor = true;
        // only visible in sandbox (infinite resources) — never in campaign/survival
        requirements(Category.defense, BuildVisibility.sandboxOnly, new mindustry.type.ItemStack[0]);
    }

    @Override
    public void setBars() {
        super.setBars();
        addBar("fr-dps-heal", (DPSTargetBuild entity) -> new Bar(
            () -> "Invincible",
            () -> Color.green,
            () -> 1f
        ));
    }

    public class DPSTargetBuild extends Building {
        /** Rolling window: {timestamp, damage} pairs. */
        private final Queue<float[]> damageHistory = new Queue<>();
        /** Total accumulated damage since last reset. */
        public long totalDamage = 0;
        /** Peak DPS observed since last reset. */
        public float peakDps = 0f;

        @Override
        public void updateTile() {
            // full heal every tick — the target cannot be destroyed
            if (health < maxHealth) {
                health = maxHealth;
            }
        }

        @Override
        public float handleDamage(float amount) {
            if (amount <= 0f) return 0f;

            damageHistory.addLast(new float[]{Time.time, amount});
            totalDamage += (long) amount;

            // update peak DPS
            float dps = currentDps();
            if (dps > peakDps) peakDps = dps;

            // return 0 → block takes NO actual damage (invincible)
            return 0f;
        }

        @Override
        public boolean configTapped() {
            // tap to reset all counters; return false to suppress config UI
            damageHistory.clear();
            totalDamage = 0;
            peakDps = 0f;
            return false;
        }

        @Override
        public void drawSelect() {
            super.drawSelect();

            float dps = currentDps();
            float offY = block.size * Vars.tilesize / 2f + 8f;

            // DPS line
            String dpsText = formatAmount((long)(dps * 60f)) + "/s";
            Color dpsColor = dpsToColor(dps);
            Drawf.text(dpsText, x, y + offY, dpsColor);

            // Total damage line
            if (totalDamage > 0) {
                String dmgText = "Total: " + formatAmount(totalDamage);
                Drawf.text(dmgText, x, y + offY - 14f, Color.valueOf("ffd37f"));
            }
        }

        /** Rolling DPS: sum of damage in the last {@code dpsWindow} ticks. */
        public float currentDps() {
            float cutoff = Time.time - dpsWindow;
            while (damageHistory.size > 0 && damageHistory.first()[0] < cutoff) {
                damageHistory.removeFirst();
            }
            float sum = 0f;
            for (float[] e : damageHistory) sum += e[1];
            return sum;
        }
    }

    // ---- static utilities ----

    /** Formats large numbers: 1234 → "1.23K", 1234567 → "1.23M", etc. */
    static String formatAmount(long value) {
        if (value < 1_000L) return String.valueOf(value);
        if (value < 1_000_000L) return String.format("%.2fK", value / 1_000.0);
        if (value < 1_000_000_000L) return String.format("%.2fM", value / 1_000_000.0);
        return String.format("%.2fB", value / 1_000_000_000.0);
    }

    /** Returns a color for the given raw-DPS value (ticks/s, not per-second).
     *  Logarithmic scale: green (0) → yellow (~10/s) → red (~1000/s). */
    static Color dpsToColor(float dps) {
        if (dps <= 0f) return Color.green;
        // Mathf.log(base, value) = log_base(value); normalize to [0,1]
        float t = Mathf.clamp(Mathf.log(10f, dps + 1f) / 3f);
        if (t < 0.5f) {
            return new Color().lerp(Color.green, Color.yellow, t * 2f);
        }
        return new Color().lerp(Color.yellow, Color.red, (t - 0.5f) * 2f);
    }
}
