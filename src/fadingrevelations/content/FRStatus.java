package fadingrevelations.content;
import mindustry.type.*;

import mindustry.type.StatusEffect;
import arc.graphics.Color;

public class FRStatus {
    public static StatusEffect acidicBurn, constructionShock, emp, hastened, highEnergyBurn,
            japonicaWeakened, mediumDowndraft, minimalDowndraft, neutronFrozen,
            polymorphousBuilding, powerfulDowndraft, radiated, shockslowed, slightDowndraft, superHastened;

    public static void load() {
        acidicBurn = new StatusEffect("me-acidic-burn") {{
            color = Color.valueOf("cde03a");
            localizedName = "Acidic Burn";
            description = "Burning from acid.";
        }};

        constructionShock = new StatusEffect("me-construction-shock") {{
            color = Color.valueOf("ffd59e");
            localizedName = "Construction Shock";
            description = "Electrocuted by a construction pylon.";
        }};

        emp = new StatusEffect("me-emp") {{
            color = Color.valueOf("f3f3f3");
            localizedName = "EMP";
            description = "Electromagnetic pulse. Disables electronics.";
        }};

        hastened = new StatusEffect("me-hastened") {{
            color = Color.valueOf("ffd59e");
            localizedName = "Hastened";
            description = "Movement speed increased.";
        }};

        highEnergyBurn = new StatusEffect("me-high-energy-burn") {{
            color = Color.valueOf("ff9b59");
            localizedName = "High-Energy Burn";
            description = "Burning from high-energy particles.";
        }};

        japonicaWeakened = new StatusEffect("me-japonica-weakened") {{
            color = Color.valueOf("b0bf1a");
            localizedName = "Japonica Weakened";
            description = "Weakened by japonica.";
        }};

        mediumDowndraft = new StatusEffect("me-medium-downdraft") {{
            color = Color.valueOf("8f8f8f");
            localizedName = "Medium Downdraft";
            description = "Pulled down by a medium downdraft.";
        }};

        minimalDowndraft = new StatusEffect("me-minimal-downdraft") {{
            color = Color.valueOf("8f8f8f");
            localizedName = "Minimal Downdraft";
            description = "Pulled down by a minimal downdraft.";
        }};

        neutronFrozen = new StatusEffect("me-neutron-frozen") {{
            color = Color.valueOf("e9e9e9");
            localizedName = "Neutron Frozen";
            description = "Frozen by neutron fluid.";
        }};

        polymorphousBuilding = new StatusEffect("me-polymorphous-building") {{
            color = Color.valueOf("4fa4c0");
            localizedName = "Polymorphous Building";
            description = "Building speed increased by polymorphous gel.";
        }};

        powerfulDowndraft = new StatusEffect("me-powerful-downdraft") {{
            color = Color.valueOf("8f8f8f");
            localizedName = "Powerful Downdraft";
            description = "Pulled down by a powerful downdraft.";
        }};

        radiated = new StatusEffect("me-radiated") {{
            color = Color.valueOf("22b400");
            localizedName = "Radiated";
            description = "Exposed to radiation.";
        }};

        shockslowed = new StatusEffect("me-shockslowed") {{
            color = Color.valueOf("f3f3f3");
            localizedName = "Shock Slowed";
            description = "Slowed by electric shocks.";
        }};

        slightDowndraft = new StatusEffect("me-slight-downdraft") {{
            color = Color.valueOf("8f8f8f");
            localizedName = "Slight Downdraft";
            description = "Pulled down by a slight downdraft.";
        }};

        superHastened = new StatusEffect("me-super-hastened") {{
            color = Color.valueOf("ffd59e");
            localizedName = "Super Hastened";
            description = "Greatly increased movement speed.";
        }};
    }
}
