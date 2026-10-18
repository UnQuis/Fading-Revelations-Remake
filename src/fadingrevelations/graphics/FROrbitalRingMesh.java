package fadingrevelations.graphics;

import arc.graphics.Color;
import arc.graphics.Gl;
import arc.graphics.Mesh;
import arc.graphics.VertexAttribute;
import arc.graphics.gl.Shader;
import arc.math.Mathf;
import arc.math.geom.Mat3D;
import arc.math.geom.Vec3;
import arc.util.Time;
import mindustry.Vars;
import mindustry.graphics.g3d.GenericMesh;
import mindustry.graphics.g3d.PlanetParams;
import mindustry.type.Planet;

import fadingrevelations.content.FROrbitalRing;

/**
 * Renders the orbital ring megastructure around a planet (Cangirus).
 *
 * The ring consists of four quadrant arcs, one per construction stage of the
 * Orbital Ring project, plus a faint blueprint circle while incomplete and a
 * soft glow band when fully built. Geometry is static; visibility is decided
 * per frame from {@link FROrbitalRing#stage}, so stage-ups are reflected
 * immediately without rebuilding the mesh.
 *
 * Each arc is a solid extruded tube (hull + top spine + edge rails) rather
 * than a flat band, so it holds a silhouette against the starfield. The shader
 * is unlit, so the volume comes from a baked top/side/underside ramp in the
 * vertex colors instead of real normals.
 *
 * All GPU resources (shader + meshes) are created lazily on the first
 * {@link #render} call: planet mesh loaders run on an async loader thread
 * with no GL context, where shader compilation fails (Omaloon does the same
 * via Core.app.post; New Horizon creates its shaders in loadContent).
 */
public class FROrbitalRingMesh implements GenericMesh {
    private static final Mat3D mat = new Mat3D();

    public final Planet planet;

    /** Ring plane tilt in degrees. */
    public float tilt = 24f;
    /**
     * Spin around the ring axis, in degrees per second.
     *
     * `Mat3D.rotate(Vec3, float)` goes through `Quat.setFromAxis`, which
     * multiplies by 0.017453292 - so this really is degrees, not radians - and
     * `Time.globalTime` is seconds. The old 0.6 deg/s is one turn every ten
     * minutes, which still reads as "the ring is spinning" during a normal play
     * session and pulls the eye away from the planet. At 0.05 deg/s a turn takes
     * two hours, so the ring reads as a slow drift instead of a rotation. The
     * mod's own asteroid belt uses 0.04.
     */
    public float spinSpeed = 0.05f;
    /** Ring radii, in units of planet radius. */
    public float innerRadius = 1.42f, outerRadius = 1.84f;
    /** Half-height of the main hull tube, in units of planet radius. */
    public float height = 0.05f;
    /** Height of the raised spine running along the top of the hull. */
    public float spine = 0.028f;
    /** Radial thickness of the inner and outer rails. */
    public float rail = 0.045f;

    public Color hullColor = Color.valueOf("9aa0b5");
    public Color hullDark = Color.valueOf("565b6b");
    public Color rimColor = Color.valueOf("c9cede");
    public Color accentColor = Color.valueOf("aa44ff");
    public Color blueprintColor = Color.valueOf("6fb7ff");
    public Color glowColor = Color.valueOf("c99bff");

    protected Mesh[] arcs;
    protected Mesh blueprint, glow;
    protected boolean built = false;

    public FROrbitalRingMesh(Planet planet) {
        this.planet = planet;
        //NOTE: no GL work here - see the class javadoc
    }

    /** Builds shader and meshes. Called from render() only, i.e. on the GL thread. */
    protected void build() {
        built = true;
        if (!FRRingShader.ensureLoaded()) return;

        try {
            arcs = new Mesh[4];
            for (int i = 0; i < 4; i++) {
                arcs[i] = buildArc(i);
            }
            blueprint = buildBand(0f, 360f, midRadius() - 0.012f, midRadius() + 0.012f, c(blueprintColor, 0.16f), 64);
            glow = buildBand(0f, 360f, outerRadius, outerRadius + 0.06f, c(glowColor, 0.35f), 64);
        } catch (Throwable t) {
            arc.util.Log.err("Fading Revelations: failed to build the orbital ring mesh, ring disabled", t);
            dispose();
            arcs = null;
            blueprint = glow = null;
        }
    }

    private float midRadius() {
        return (innerRadius + outerRadius) / 2f;
    }

    private static Color c(Color color, float alpha) {
        return color.cpy().a(alpha);
    }

    @Override
    public void render(PlanetParams params, Mat3D projection, Mat3D transform) {
        if (!built) build();

        Shader shader = FRRingShader.ring;
        if (shader == null || arcs == null) return;

        //hide the ring in the zoomed-in surface view, like vanilla clouds
        if (params.planet == planet && Mathf.zero(1f - params.uiAlpha, 0.01f)) return;

        int stage = FROrbitalRing.stage;

        shader.bind();
        shader.setUniformMatrix4("u_proj", projection.val);
        shader.setUniformMatrix4("u_trans", mat.setToTranslation(planet.position)
            .rotate(Vec3.X, tilt)
            .rotate(Vec3.Y, Time.globalTime * spinSpeed).val);
        shader.apply();

        //the ring is a flat band and must stay visible from both sides,
        //and its translucent bands need blending (the planet renderer has it off)
        Gl.disable(Gl.cullFace);
        Gl.enable(Gl.blend);
        Gl.blendFunc(Gl.srcAlpha, Gl.oneMinusSrcAlpha);
        if (stage < 4) {
            blueprint.render(shader, Gl.triangles);
        }
        for (int i = 0; i < Math.min(stage, 4); i++) {
            arcs[i].render(shader, Gl.triangles);
        }
        if (stage >= 4) {
            glow.render(shader, Gl.triangles);
        }
        Gl.disable(Gl.blend);
        Gl.enable(Gl.cullFace);
    }

    @Override
    public void dispose() {
        if (arcs != null) for (Mesh m : arcs) if (m != null) m.dispose();
        if (blueprint != null) blueprint.dispose();
        if (glow != null) glow.dispose();
    }

    /**
     * Builds one 90 degree arc of the ring as a solid tube rather than a flat band: main hull
     * plus a raised spine on top and thin rails on both edges, so the megastructure keeps a
     * readable silhouette and self-shading from any viewing angle.
     */
    private Mesh buildArc(int index) {
        float a0 = index * 90f, a1 = a0 + 90f;
        int segments = 18;

        // 4 tubes x 4 faces x 2 triangles x 3 vertices
        Mesh mesh = newMesh(segments * 4 * 4 * 6);

        float h = height;
        float spineIn = innerRadius + rail + 0.03f, spineOut = outerRadius - rail - 0.06f;

        boolean last = index == 3;

        for (int i = 0; i < segments; i++) {
            float ta = Mathf.lerp(a0, a1, (float)i / segments);
            float tb = Mathf.lerp(a0, a1, (float)(i + 1) / segments);

            //alternating panels keep the hull from reading as one smooth tube
            Color hull = (i % 2 == 0 ? hullColor : hullDark.cpy().lerp(hullColor, 0.55f)).cpy();
            Color trim = last ? accentColor : rimColor;
            if (last) hull = accentColor.cpy().lerp(hullColor, 0.3f);

            //main hull
            tube(mesh, ta, tb, innerRadius + rail, outerRadius - rail, -h, h, hull, 0.96f);
            //raised spine along the top
            tube(mesh, ta, tb, spineIn, spineOut, h, h + spine, trim, 0.96f);
            //inner and outer rails
            tube(mesh, ta, tb, innerRadius, innerRadius + rail, -h * 0.45f, h * 0.45f, hullDark, 0.95f);
            tube(mesh, ta, tb, outerRadius - rail, outerRadius, -h * 0.5f, h * 0.5f, trim, 0.92f);
        }

        finish(mesh);
        return mesh;
    }

    /** Builds a flat circular band between two radii, spanning [a0, a1] degrees. */
    private Mesh buildBand(float a0, float a1, float r0, float r1, Color color, int segments) {
        Mesh mesh = newMesh(segments * 6);
        for (int i = 0; i < segments; i++) {
            float ta = Mathf.lerp(a0, a1, (float)i / segments);
            float tb = Mathf.lerp(a0, a1, (float)(i + 1) / segments);
            quad(mesh, ta, tb, r0, r1, color, color);
        }
        finish(mesh);
        return mesh;
    }

    private static Mesh newMesh(int vertices) {
        Mesh mesh = new Mesh(true, vertices, 0, VertexAttribute.position3, VertexAttribute.color);
        mesh.getVerticesBuffer().limit(mesh.getVerticesBuffer().capacity());
        mesh.getVerticesBuffer().position(0);
        return mesh;
    }

    private static void finish(Mesh mesh) {
        mesh.getVerticesBuffer().limit(mesh.getVerticesBuffer().position());
    }

    private static void put(Mesh mesh, float angle, float radius, float y, Color color) {
        float rad = angle * Mathf.degRad;
        mesh.getVerticesBuffer()
            .put(Mathf.cos(rad) * radius)
            .put(y)
            .put(Mathf.sin(rad) * radius)
            .put(color.toFloatBits());
    }

    /**
     * Extruded rectangular section along the arc: inner wall, outer wall, top cap, bottom cap.
     * The shader is unlit, so volume comes from baking a fixed top/outer/inner/bottom ramp into
     * the vertex colors - the top catches light, the underside and inner face fall into shadow.
     */
    private static void tube(Mesh mesh, float ta, float tb, float r0, float r1, float y0, float y1, Color base, float alpha){
        wall(mesh, ta, tb, r0, y0, y1, base.cpy().mul(0.26f).a(alpha), base.cpy().mul(0.42f).a(alpha));
        wall(mesh, ta, tb, r1, y0, y1, base.cpy().mul(0.52f).a(alpha), base.cpy().mul(0.74f).a(alpha));
        cap(mesh, ta, tb, r0, r1, y1, true, base.cpy().a(alpha), base.cpy().a(alpha));
        cap(mesh, ta, tb, r0, r1, y0, false, base.cpy().mul(0.34f).a(alpha), base.cpy().mul(0.34f).a(alpha));
    }

    /** Vertical wall at a single radius, graded from {@code bottom} at y0 to {@code top} at y1. */
    private static void wall(Mesh mesh, float ta, float tb, float radius, float y0, float y1, Color bottom, Color top) {
        put(mesh, ta, radius, y0, bottom); put(mesh, tb, radius, y0, bottom); put(mesh, tb, radius, y1, top);
        put(mesh, ta, radius, y0, bottom); put(mesh, tb, radius, y1, top); put(mesh, ta, radius, y1, top);
    }

    /** Horizontal face at height y spanning radii r0..r1, facing up or down. */
    private static void cap(Mesh mesh, float ta, float tb, float r0, float r1, float y, boolean up, Color ca, Color cb) {
        if (up) {
            put(mesh, ta, r0, y, ca); put(mesh, ta, r1, y, cb); put(mesh, tb, r1, y, cb);
            put(mesh, ta, r0, y, ca); put(mesh, tb, r1, y, cb); put(mesh, tb, r0, y, ca);
        } else {
            put(mesh, ta, r0, y, ca); put(mesh, tb, r1, y, cb); put(mesh, ta, r1, y, cb);
            put(mesh, ta, r0, y, ca); put(mesh, tb, r0, y, ca); put(mesh, tb, r1, y, cb);
        }
    }

    /** Two triangles between radii r0..r1 across angles ta..tb with a single color. */
    private static void quad(Mesh mesh, float ta, float tb, float r0, float r1, Color ca, Color cb) {
        put(mesh, ta, r0, 0f, ca); put(mesh, tb, r0, 0f, ca); put(mesh, tb, r1, 0f, cb);
        put(mesh, ta, r0, 0f, ca); put(mesh, tb, r1, 0f, cb); put(mesh, ta, r1, 0f, cb);
    }
}
