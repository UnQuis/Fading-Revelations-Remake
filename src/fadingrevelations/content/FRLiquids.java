package fadingrevelations.content;
import mindustry.type.*;
import mindustry.content.*;

import arc.graphics.Color;
import mindustry.type.Liquid;
import mindustry.content.StatusEffects;
import mindustry.type.CellLiquid;
import arc.struct.ObjectSet;

public class FRLiquids {
    public static Liquid steam, neutronFluid, livingSteelLiquid, acid;

    public static void load() {
        steam = new Liquid("steam", Color.valueOf("ececec")) {{
            heatCapacity = 0.1f; explosiveness = 0f; temperature = 0.1f; flammability = 0f;
            gas = true; coolant = false;
            localizedName = "Steam";
            description = "Evaporated water.";
        }};

        neutronFluid = new Liquid("neutron-fluid", Color.valueOf("e9e9e9")) {{
            heatCapacity = 1.5f; explosiveness = 2.5f; temperature = 0.1f; viscosity = 0.5f; flammability = 0f;
            coolant = true; effect = FRStatus.neutronFrozen;
            localizedName = "Neutron Fluid";
            description = "A highly potent coolant created by mixing oil, cryofluid and living steel liquid.";
        }};

        livingSteelLiquid = new CellLiquid("living-steel-liquid", Color.valueOf("9000a4")) {{
            moveThroughBlocks = true; canStayOn = ObjectSet.with(mindustry.content.Liquids.oil);
            incinerable = true; spreadDamage = 0f; capPuddles = true; spreadTarget = mindustry.content.Liquids.oil;
            heatCapacity = 0.9f; explosiveness = 0.05f; temperature = 0.125f;
            colorFrom = Color.valueOf("6d0071"); colorTo = Color.valueOf("9e78dc");
            blockReactive = false; maxSpread = 0.4f; viscosity = 0.6f; flammability = 1f;
            localizedName = "Living Steel Liquid";
            description = "Living Steel that was infused with oil thus making it a liquid. Spreads to nearby oil to 'self-replicate'.";
        }};

        acid = new CellLiquid("acid", Color.valueOf("cde03a")) {{
            moveThroughBlocks = false; incinerable = false; capPuddles = true; maxSpread = 0f;
            temperature = 0.2f; coolant = false;
            colorFrom = Color.valueOf("b0bf1a"); colorTo = Color.valueOf("f8f854");
            blockReactive = false; viscosity = 0.2f; effect = FRStatus.acidicBurn;
            localizedName = "Acid";
            description = "A dangerous liquid.";
        }};
    }
}
