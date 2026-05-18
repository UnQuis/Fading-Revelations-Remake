package fadingrevelations.content;

import arc.graphics.Color;
import mindustry.content.Fx;
import mindustry.content.StatusEffects;
import mindustry.entities.effect.ParticleEffect;
import mindustry.type.StatusEffect;

public class FRStatus {
    public static StatusEffect acidicBurn, constructionShock, emp, hastened, highEnergyBurn,
            japonicaWeakened, mediumDowndraft, minimalDowndraft, neutronFrozen,
            polymorphousBuilding, powerfulDowndraft, radiated, sapped, shockslowed, slightDowndraft, superHastened;

    public static void load() {
        acidicBurn = new StatusEffect("acidic-burn") {{
            color = Color.valueOf("cde03a");
            localizedName = "Acidic Burn";
            description = "Burning from acid.";
            speedMultiplier = 0.8f;
            damageMultiplier = 0.8f;
            effect = Fx.none;
        }};

        constructionShock = new StatusEffect("construction-shock") {{
            color = Color.valueOf("ffd59e");
            localizedName = "Construction Shock";
            description = "Electrocuted by a construction pylon.";
            buildSpeedMultiplier = 0.8f;
            speedMultiplier = 0.8f;
        }};

        emp = new StatusEffect("emp") {{
            color = Color.valueOf("f3f3f3");
            localizedName = "EMP";
            description = "Electromagnetic pulse. Disables electronics.";
            speedMultiplier = 0.4f;
            damageMultiplier = 0.6f;
            healthMultiplier = 0.8f;
            reloadMultiplier = 0.7f;
        }};

        hastened = new StatusEffect("hastened") {{
            color = Color.valueOf("ffd59e");
            localizedName = "Hastened";
            description = "Movement speed increased.";
            reloadMultiplier = 1.6f;
            alwaysUnlocked = true;
        }};

        highEnergyBurn = new StatusEffect("high-energy-burn") {{
            color = Color.valueOf("e8d174");
            localizedName = "High-Energy Burn";
            description = "Burning from high-energy particles.";
            damage = 0.4f;
            reloadMultiplier = 0.85f;
            alwaysUnlocked = true;
            effect = new ParticleEffect() {{
                particles = 8;
                sizeFrom = 1f;
                sizeTo = 2.7f;
                colorFrom = Color.valueOf("e8d174");
                colorTo = Color.valueOf("fffab8");
                line = true;
                strokeFrom = 0.6f;
                strokeTo = 1f;
                interp = arc.math.Interp.pow2Out;
                sizeInterp = arc.math.Interp.pow2Out;
            }};
        }};

        japonicaWeakened = new StatusEffect("japonica-weakened") {{
            color = Color.valueOf("b0bf1a");
            localizedName = "Japonica Weakened";
            description = "Weakened by japonica.";
            buildSpeedMultiplier = 0.8f;
            damageMultiplier = 0.8f;
            reloadMultiplier = 0.8f;
            speedMultiplier = 0.8f;
            alwaysUnlocked = true;
        }};

        mediumDowndraft = new StatusEffect("medium-downdraft") {{
            color = Color.valueOf("8f8f8f");
            localizedName = "Medium Downdraft";
            description = "Pulled down by a medium downdraft.";
            speedMultiplier = 0.7f;
            alwaysUnlocked = true;
            effect = Fx.none;
        }};

        minimalDowndraft = new StatusEffect("minimal-downdraft") {{
            color = Color.valueOf("8f8f8f");
            localizedName = "Minimal Downdraft";
            description = "Pulled down by a minimal downdraft.";
            speedMultiplier = 0.9f;
            alwaysUnlocked = true;
            effect = Fx.none;
        }};

        neutronFrozen = new StatusEffect("neutron-frozen") {{
            color = Color.valueOf("e9e9e9");
            localizedName = "Neutron Frozen";
            description = "Frozen by neutron fluid.";
            speedMultiplier = 0.4f;
            reloadMultiplier = 0.7f;
            damage = 0.1f;
            alwaysUnlocked = true;
        }};

        polymorphousBuilding = new StatusEffect("polymorphous-building") {{
            color = Color.valueOf("4fa4c0");
            localizedName = "Polymorphous Building";
            description = "Building speed increased by polymorphous gel.";
            buildSpeedMultiplier = 1.2f;
        }};

        powerfulDowndraft = new StatusEffect("powerful-downdraft") {{
            color = Color.valueOf("8f8f8f");
            localizedName = "Powerful Downdraft";
            description = "Pulled down by a powerful downdraft.";
            speedMultiplier = 0.6f;
            alwaysUnlocked = true;
            effect = Fx.none;
        }};

        sapped = new StatusEffect("steel-sapped") {{
            color = Color.valueOf("7f42b5");
            localizedName = "Sapped";
            description = "Health sapped by living steel.";
        }};

        radiated = new StatusEffect("radiated") {{
            color = Color.valueOf("22b400");
            localizedName = "Radiated";
            description = "Exposed to radiation.";
            damage = 0.125f;
            permanent = true;
            speedMultiplier = 0.9f;
            reloadMultiplier = 0.9f;
            healthMultiplier = 0.9f;
            alwaysUnlocked = true;
            effect = new ParticleEffect() {{
                particles = 8;
                colorFrom = Color.valueOf("30af1f");
                colorTo = Color.valueOf("328926");
                interp = arc.math.Interp.circleOut;
            }};
        }};

        shockslowed = new StatusEffect("shockslowed") {{
            color = Color.valueOf("f3f3f3");
            localizedName = "Shock Slowed";
            description = "Slowed by electric shocks.";
            speedMultiplier = 0.3f;
            alwaysUnlocked = true;
        }};

        slightDowndraft = new StatusEffect("slight-downdraft") {{
            color = Color.valueOf("8f8f8f");
            localizedName = "Slight Downdraft";
            description = "Pulled down by a slight downdraft.";
            speedMultiplier = 0.8f;
            alwaysUnlocked = true;
            effect = Fx.none;
        }};

        superHastened = new StatusEffect("super-hastened") {{
            color = Color.valueOf("ffd59e");
            localizedName = "Super Hastened";
            description = "Greatly increased movement speed.";
            reloadMultiplier = 2.2f;
            alwaysUnlocked = true;
        }};

        highEnergyBurn.affinities.add(StatusEffects.wet);
        highEnergyBurn.opposites.add(StatusEffects.tarred);
        highEnergyBurn.opposites.add(StatusEffects.freezing);
    }
}
