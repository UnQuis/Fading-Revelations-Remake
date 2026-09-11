package fadingrevelations.graphics;

import arc.files.Fi;
import arc.graphics.gl.Shader;
import arc.util.Log;
import mindustry.Vars;
import mindustry.mod.Mods;

/**
 * Minimal unlit vertex-color shader used for the orbital ring meshes.
 * Shader files live in assets/shaders and are resolved through the mod root
 * (same approach as the New Horizon mod's ModShader).
 *
 * Must be created on the render/GL thread: planet mesh loaders execute on an
 * async loader thread without a GL context, where Gl.createShader returns 0
 * and compilation fails with an empty log.
 */
public class FRRingShader extends Shader {
    public static FRRingShader ring;
    private static boolean failed = false;

    public FRRingShader() {
        super(getFi("fr-ring.vert"), getFi("fr-ring.frag"));
    }

    /**
     * Creates the shader once, on the GL thread. Never throws: on failure the
     * ring is silently disabled and the error is logged.
     * @return true if the shader is ready to use.
     */
    public static boolean ensureLoaded() {
        if (Vars.headless || failed) return false;
        if (ring != null) return true;
        try {
            ring = new FRRingShader();
            return true;
        } catch (Throwable t) {
            failed = true;
            Log.err("Fading Revelations: failed to compile the ring shader, orbital ring disabled", t);
            return false;
        }
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
