package fadingrevelations.graphics;

import arc.files.Fi;
import arc.graphics.gl.Shader;
import mindustry.Vars;
import mindustry.mod.Mods;

/**
 * Minimal unlit vertex-color shader used for the orbital ring meshes.
 * Shader files live in assets/shaders and are resolved through the mod root
 * (same approach as the New Horizon mod's ModShader).
 */
public class FRRingShader extends Shader {
    public static FRRingShader ring;

    public FRRingShader() {
        super(getFi("fr-ring.vert"), getFi("fr-ring.frag"));
    }

    /** Creates the shader once, on the client. */
    public static void init() {
        if (Vars.headless || ring != null) return;
        ring = new FRRingShader();
    }

    private static Fi getFi(String file) {
        if (Vars.mods != null) {
            Mods.LoadedMod mod = Vars.mods.getMod(fadingrevelations.FadingRevelationsMod.class);
            if (mod != null && mod.root != null) {
                Fi f = mod.root.child("shaders").child(file);
                if (f.exists()) return f;
            }
        }
        return Vars.tree.get("shaders/" + file);
    }
}
