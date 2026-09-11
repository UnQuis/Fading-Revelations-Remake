package fadingrevelations.worlds.abilities;

import arc.math.Mathf;
import arc.scene.ui.layout.Table;
import arc.util.Strings;
import arc.util.Time;
import fadingrevelations.content.FRSettings;
import mindustry.entities.abilities.Ability;
import mindustry.gen.Unit;
import mindustry.world.meta.StatUnit;

/**
 * A unit ability that makes the unit shoot progressively faster the longer it
 * keeps firing, and quickly wind back down when it stops.
 *
 * Inspired by AccumulateAccelerate from the New Horizon mod (GPL-3, Yuria & Lao),
 * adapted for Fading Revelations.
 */
public class RampUpAbility extends Ability {
    /** Current reload multiplier. */
    public float reloadMultiplier = 1f;
    /** Maximum reload multiplier. */
    public float maxMultiplier = 2.5f;
    /** Reload multiplier growth per second while shooting. */
    public float increasePerSecond = 0.2f;
    /** Reload multiplier decay per second while not shooting. */
    public float decreasePerSecond = 2f;

    public RampUpAbility() {}

    public RampUpAbility(float maxMultiplier, float increasePerSecond, float decreasePerSecond) {
        this.maxMultiplier = maxMultiplier;
        this.increasePerSecond = increasePerSecond;
        this.decreasePerSecond = decreasePerSecond;
    }

    @Override
    public void update(Unit unit) {
        super.update(unit);

        if (!FRSettings.unitAbilities) return;

        float increment = (unit.isShooting ? increasePerSecond : -decreasePerSecond) * Time.delta;
        reloadMultiplier = Mathf.clamp(reloadMultiplier + increment, 1f, maxMultiplier);
        unit.reloadMultiplier *= reloadMultiplier;
    }

    @Override
    public void addStats(Table t) {
        t.add("[lightgray]" + FRSettings.bundle("fr.ability.rampup.gain", "Rate of fire gain while shooting") + ": [white]+" + Strings.autoFixed(increasePerSecond * 100f, 0) + "%" + StatUnit.perSecond.localized());
        t.row();
        t.add("[lightgray]" + FRSettings.bundle("fr.ability.rampup.loss", "Rate of fire loss when idle") + ": [white]-" + Strings.autoFixed(decreasePerSecond * 100f, 0) + "%" + StatUnit.perSecond.localized());
        t.row();
        t.add("[lightgray]" + FRSettings.bundle("fr.ability.rampup.max", "Maximum fire rate") + ": [white]" + Strings.autoFixed(maxMultiplier * 100f, 0) + "%");
    }
}
