package fadingrevelations.content;
import mindustry.type.*;
import mindustry.content.*;

import arc.Core;
import arc.graphics.Color;
import arc.graphics.Texture;
import arc.graphics.g2d.Draw;
import arc.graphics.g2d.TextureRegion;
import arc.util.Tmp;
import mindustry.entities.Effect;
import mindustry.type.Liquid;
import mindustry.content.StatusEffects;
import mindustry.type.CellLiquid;
import arc.struct.ObjectSet;

public class FRLiquids {
    public static Liquid steam, neutronFluid, livingSteelLiquid, acid;

    private static final Color brineNoiseColor = Color.valueOf("9fae8a");

    /**
     * Small, position-bound noise patches for puddles. NoiseEffect is screen-space and would
     * repaint the whole camera for every particle, so this effect uses the same built-in
     * distortion texture but draws a single small quad at the puddle.
     */
    private static Effect noiseParticle() {
        return new Effect(35f, e -> {
            Texture texture = Core.assets == null ? null
                : Core.assets.getOrNull("sprites/distortAlpha.png", Texture.class);
            if (texture == null) return;

            Tmp.tr1.set(texture);
            Draw.color(brineNoiseColor);
            Draw.alpha(0.16f * e.fout());
            Draw.rect(Tmp.tr1, e.x, e.y, 28f, 28f, e.rotation);
            Draw.reset();
        });
    }

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

        acid = new CellLiquid("acid", Color.valueOf("6f8f6a")) {{
            moveThroughBlocks = false; incinerable = false; capPuddles = true; maxSpread = 0f;
            temperature = 0.2f; coolant = false;
            // Muted sage/olive instead of the old neon acid green. The noise layer gives the
            // surface some texture without turning the whole map into a glowing highlighter.
            colorFrom = Color.valueOf("5f7355"); colorTo = Color.valueOf("8fa37a");
            blockReactive = false; viscosity = 0.2f; effect = FRStatus.acidicBurn;
            particleEffect = noiseParticle();
            particleSpacing = 90f;
            localizedName = "Caustic Brine";
            description = "A dull, caustic brine that corrodes exposed tissue. Its muted sheen makes it look far less dangerous than it is.";
        }};
    }
}
