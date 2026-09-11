package fadingrevelations.worlds.blocks.effect;

import arc.Core;
import arc.graphics.Color;
import arc.graphics.g2d.Draw;
import arc.util.Time;
import mindustry.content.StatusEffects;
import mindustry.entities.Units;
import mindustry.graphics.Drawf;
import mindustry.type.StatusEffect;
import mindustry.world.blocks.defense.OverdriveProjector;
import mindustry.world.meta.Stat;

import static mindustry.Vars.tilesize;

/**
 * An endgame overdrive projector that, in addition to boosting buildings,
 * periodically applies a beneficial status effect to every friendly unit
 * inside its radius.
 */
public class OverdriveHive extends OverdriveProjector {
    /** Status effect applied to friendly units inside the radius. */
    public StatusEffect unitEffect = StatusEffects.overclock;
    /** How long the unit status effect lasts, in ticks. */
    public float unitEffectDuration = 180f;
    /** How often the unit status effect is re-applied, in ticks. */
    public float unitReload = 90f;
    /** Extra unit-aura range provided by the booster item. */
    public float unitPhaseRangeBoost = 40f;
    /** Color of the unit aura circle. */
    public Color unitColor = Color.valueOf("aa44ff");

    public OverdriveHive(String name){
        super(name);
    }

    @Override
    public void setStats(){
        super.setStats();
        stats.add(Stat.abilities, Core.bundle.get("fr.stat.overdrivehive",
            "Also overclocks every friendly unit in range"));
    }

    public class OverdriveHiveBuild extends OverdriveBuild {
        public float unitTimer;

        @Override
        public void updateTile(){
            super.updateTile();

            if(efficiency > 0){
                unitTimer += Time.delta * efficiency;

                if(unitTimer >= unitReload){
                    unitTimer = 0f;

                    float auraRange = range() + phaseHeat * unitPhaseRangeBoost;
                    Units.nearby(team, x, y, auraRange, unit -> unit.apply(unitEffect, unitEffectDuration));
                }
            }
        }

        @Override
        public void drawSelect(){
            super.drawSelect();

            float auraRange = range() + phaseHeat * unitPhaseRangeBoost;
            if(auraRange > range()){
                Drawf.dashCircle(x, y, auraRange, unitColor);
            }
        }
    }
}
