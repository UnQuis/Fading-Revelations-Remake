package fadingrevelations.worlds.blocks.power;

import arc.*;
import arc.math.*;
import arc.util.*;
import mindustry.content.*;
import mindustry.entities.*;
import mindustry.game.EventType.*;
import mindustry.gen.*;
import mindustry.graphics.*;
import mindustry.type.*;
import mindustry.world.blocks.power.*;
import mindustry.world.*;
import fadingrevelations.content.*;

import static mindustry.Vars.*;

public class PlasmaReactor extends NuclearReactor {

    public Liquid coolant;

    public PlasmaReactor(String name) {
        super(name);
    }

    public class PlasmaReactorBuild extends NuclearReactorBuild {

        @Override
        public void updateTile() {
            int fuel = items.get(fuelItem);
            float fullness = (float) fuel / itemCapacity;
            productionEfficiency = fullness;

            if (fuel > 0 && enabled) {
                heat += fullness * heating * Math.min(delta(), 4f);
                if (timer(timerFuel, itemDuration / timeScale)) {
                    consume();
                }
            } else {
                productionEfficiency = 0f;
                heat = Math.max(0f, heat - Time.delta / ambientCooldownTime);
            }

            if (heat > 0) {
                float coolantAmount = liquids.get(coolant);
                float maxUsed = Math.min(coolantAmount, heat / coolantPower);
                heat -= maxUsed * coolantPower;
                liquids.remove(coolant, maxUsed);
            }

            if (heat > smokeThreshold) {
                float smoke = 1.0f + (heat - smokeThreshold) / (1f - smokeThreshold);
                if (Mathf.chance(smoke / 20.0 * delta())) {
                    Fx.reactorsmoke.at(x + Mathf.range(size * tilesize / 2f),
                        y + Mathf.range(size * tilesize / 2f));
                }
            }

            heat = Mathf.clamp(heat);
            heatProgress = heatOutput > 0f ? Mathf.approachDelta(heatProgress, heat * heatOutput * (enabled ? 1f : 0f), heatWarmupRate * delta()) : 0f;

            if (heat >= 0.999f) {
                Events.fire(Trigger.thoriumReactorOverheat);
                kill();
            }
        }

        @Override
        public void createExplosion() {
            float radius = explosionRadius * tilesize;
            float damage = explosionDamage;
            float cx = x, cy = y;

            Effect.shake(explosionShake * 3f, explosionShakeDuration * 3f, cx, cy);

            for (int i = 0; i < 4; i++) {
                Time.run(i * 5f, () -> {
                    Fx.reactorExplosion.at(cx + Mathf.range(radius * 0.1f), cy + Mathf.range(radius * 0.1f));
                });
            }

            for (int i = 0; i < 12; i++) {
                float angle = i * 30f + Mathf.random(15f);
                float len = radius + Mathf.random(radius * 0.5f);
                Time.run(Mathf.random(30f), () -> {
                    Lightning.create(team, Pal.reactorPurple, damage * 0.05f, cx, cy, angle, (int) (len / tilesize));
                });
            }

            for (int i = 0; i < 6; i++) {
                Time.run(10f + Mathf.random(40f), () -> {
                    float a = Mathf.random(360f);
                    float d = Mathf.random(radius * 0.8f);
                    float ex = cx + Angles.trnsx(a, d);
                    float ey = cy + Angles.trnsy(a, d);
                    Fx.massiveExplosion.at(ex, ey);
                    Damage.damage(ex, ey, radius * 0.25f, damage * 0.15f);
                    Effect.shake(explosionShake, explosionShakeDuration * 0.5f, ex, ey);
                });
            }

            Damage.damage(cx, cy, radius, damage);
        }
    }
}
