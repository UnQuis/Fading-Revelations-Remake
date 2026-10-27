package fadingrevelations.worlds.blocks.turrets;

import arc.Core;
import arc.math.Mathf;
import arc.util.Strings;
import arc.util.Time;
import arc.util.io.Reads;
import arc.util.io.Writes;
import mindustry.Vars;
import mindustry.content.Fx;
import mindustry.entities.Effect;
import mindustry.entities.Mover;
import mindustry.entities.bullet.BulletType;
import mindustry.gen.Building;
import mindustry.graphics.Pal;
import mindustry.type.Liquid;
import mindustry.ui.Bar;
import mindustry.world.blocks.defense.turrets.PowerTurret;
import mindustry.world.meta.Stat;
import mindustry.world.meta.StatUnit;

/**
 * A turret that speeds up the longer it keeps firing, but overheats and has
 * to cool down completely afterwards.
 *
 * Inspired by the SpeedupTurret from the New Horizon mod (GPL-3, Yuria & Lao),
 * adapted for Fading Revelations.
 */
public class SpinUpTurret extends PowerTurret {
    /** Total heat at which the turret is forced to cool down. */
    public float overheatTime = 360f;
    /** Heat removed per tick while cooling. */
    public float overheatCoolAmount = 1.25f;
    /** Maximum fire rate bonus. +0.5 => 150% fire rate. */
    public float maxSpeedupScl = 0.5f;
    /** Fire rate bonus gained per volley. */
    public float speedupPerShoot = 0.075f;
    /** Time (ticks) after the last volley before the spin-up starts decaying. */
    public float slowDownReloadTime = 150f;
    /** Extra inaccuracy at maximum spin-up. */
    public float inaccuracyUp = 0f;
    /** Chance per tick of the overheat smoke effect at full heat. */
    public float maxHeatEffectChance = 0.3f;
    public Effect heatEffect = Fx.reactorsmoke;

    public SpinUpTurret(String name) {
        super(name);
    }

    @Override
    public void setBars() {
        super.setBars();

        addBar("fr-spinup",
            (SpinUpTurretBuild entity) -> new Bar(
                () -> Core.bundle.format("fr.bar.spinup", Strings.autoFixed(entity.speedupScl * 100, 0)),
                () -> Pal.accent,
                () -> entity.speedupScl / maxSpeedupScl
            )
        );

        addBar("fr-overheat",
            (SpinUpTurretBuild entity) -> new Bar(
                () -> Core.bundle.format("fr.bar.overheat", Strings.autoFixed(Mathf.clamp(entity.overheat / overheatTime, 0f, 1f) * 100, 0)),
                () -> entity.requireCompleteCooling ? Pal.redderDust : Pal.powerLight,
                () -> entity.overheat / overheatTime
            )
        );
    }

    @Override
    public void setStats() {
        super.setStats();
        stats.add(Stat.speedIncrease, "+" + (int)(maxSpeedupScl * 100f) + "%");
        stats.add(Stat.cooldownTime, overheatTime / overheatCoolAmount / Time.toSeconds, StatUnit.seconds);
    }

    public class SpinUpTurretBuild extends PowerTurretBuild {
        public float speedupScl = 0f;
        public float slowDownReload = 0f;
        public float overheat = 0f;
        public boolean requireCompleteCooling = false;
        /** True while the turret is forced to cool down and must not acquire targets or fire. */
        public transient boolean coolingDown = false;

        @Override
        public void updateTile() {
            updateCooldown();
            coolingDown = overheat >= overheatTime || requireCompleteCooling;
            if (coolingDown) {
                target = null;
            }

            // Keep vanilla warmup/recoil/rotation updates, but findTarget()/updateShooting() are
            // disabled while coolingDown, so the turret cannot fire during the forced cooldown.
            super.updateTile();
        }

        public void updateCooldown() {
            if (requireCompleteCooling) {
                // Forced cooling must not wait for slowDownReload, otherwise the turret can stay
                // overheated forever when it overheated during a burst.
                slowDownReload = 0f;
            }

            if (slowDownReload > 0f) {
                slowDownReload -= Time.delta;
            } else {
                speedupScl = Mathf.lerpDelta(speedupScl, 0f, 0.05f);
                coolDown();
            }

            if (overheat > overheatTime * 0.3f) {
                if (Mathf.chanceDelta(maxHeatEffectChance * (requireCompleteCooling ? 1f : overheat / overheatTime))) {
                    heatEffect.at(x + Mathf.range(Vars.tilesize * size / 2f), y + Mathf.range(Vars.tilesize * size / 2f), rotation, heatColor);
                }
            }
        }

        public void coolDown() {
            if (overheat > 0f) {
                overheat = Math.max(0f, overheat - overheatCoolAmount * (1f + coolantEfficiency()) * Time.delta);
            }
            if (overheat <= 0f) {
                overheat = 0f;
                requireCompleteCooling = false;
            }
        }

        public float coolantEfficiency() {
            Liquid liquid = liquids.current();
            return liquid != null && liquid.coolant ? liquid.heatCapacity : 0f;
        }

        protected void addHeat() {
            float ratio = maxSpeedupScl <= 0f ? 1f : Mathf.clamp(speedupScl / maxSpeedupScl, 0.05f, 1f);
            overheat = Mathf.approachDelta(overheat, overheatTime + 0.05f, efficiency * timeScale * ratio / (1f + coolantEfficiency()));
            if (overheat >= overheatTime) {
                requireCompleteCooling = true;
            }
        }

        @Override
        protected void findTarget() {
            if (!coolingDown) {
                super.findTarget();
            }
        }

        @Override
        protected void updateShooting() {
            if (coolingDown) return;

            // the vanilla gate is reloadCounter >= reload && !charging() && shootWarmup >= minWarmup.
            // Without !charging() a multi-shot pattern (ShootBarrel with shotDelay) starts a full
            // burst every `reload` ticks while the previous one is still queued - and without the
            // warmup check the turret can fire before it is spun up.
            if (reloadCounter >= reload && !charging() && shootWarmup >= minWarmup) {
                BulletType type = peekAmmo();

                shoot(type);
                addHeat();

                // vanilla does `reloadCounter -= reload`; clamp so the residue cannot grow without
                // bound once the shot queue, not reload, is what limits the burst rate (status()
                // and logic sensors read reloadCounter directly).
                reloadCounter = Mathf.clamp(reloadCounter - reload, 0f, reload);
            } else {
                reloadCounter += (1f + speedupScl) * delta() * peekAmmo().reloadMultiplier * baseReloadSpeed();
                addHeat();
            }
        }

        @Override
        protected void shoot(BulletType type) {
            super.shoot(type);

            slowDownReload = slowDownReloadTime;
            if (speedupScl < maxSpeedupScl) {
                speedupScl += speedupPerShoot;
            } else {
                speedupScl = maxSpeedupScl;
            }
        }

        @Override
        protected void bullet(BulletType type, float xOffset, float yOffset, float angleOffset, Mover mover) {
            super.bullet(type, xOffset, yOffset, angleOffset + Mathf.range(speedupScl * inaccuracyUp), mover);
        }

        @Override
        public byte version() {
            return 2;
        }

        @Override
        public void write(Writes write) {
            super.write(write);

            write.f(overheat);
            write.f(slowDownReload);
            write.f(speedupScl);
            write.bool(requireCompleteCooling);
        }

        @Override
        public void read(Reads read, byte revision) {
            super.read(read, revision);

            //revision 1 = old PowerTurret data, before this block became a SpinUpTurret
            if (revision >= 2) {
                overheat = read.f();
                slowDownReload = read.f();
                speedupScl = read.f();
                requireCompleteCooling = read.bool();
            }
        }
    }
}
