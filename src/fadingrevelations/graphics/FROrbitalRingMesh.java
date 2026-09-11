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
 */
public class FROrbitalRingMesh implements GenericMesh {
    private static final Mat3D mat = new Mat3D();

    public final Planet planet;

    /** Ring plane tilt in degrees. */
    public float tilt = 24f;
    /** Spin around the ring axis in degrees per second. */
    public float spinSpeed = 0.6f;
    /** Ring radii, in units of planet radius. */
    public float innerRadius = 1.42f, outerRadius = 1.84f;

    public Color hullColor = Color.valueOf("9aa0b5");
    public Color hullDark = Color.valueOf("565b6b");
    public Color rimColor = Color.valueOf("c9cede");
    public Color accentColor = Color.valueOf("aa44ff");
    public Color blueprintColor = Color.valueOf("6fb7ff");
    public Color glowColor = Color.valueOf("c99bff");

    protected final Mesh[] arcs = new Mesh[4];
    protected Mesh blueprint, glow;

    public FROrbitalRingMesh(Planet planet) {
        this.planet = planet;
        FRRingShader.init();

        for (int i = 0; i < 4; i++) {
            arcs[i] = buildArc(i);
        }
        blueprint = buildBand(0f, 360f, midRadius() - 0.012f, midRadius() + 0.012f, c(blueprintColor, 0.16f), 64);
        glow = buildBand(0f, 360f, outerRadius, outerRadius + 0.06f, c(glowColor, 0.35f), 64);
    }

    private float midRadius() {
        return (innerRadius + outerRadius) / 2f;
    }

    private static Color c(Color color, float alpha) {
        return color.cpy().a(alpha);
    }

    @Override
    public void render(PlanetParams params, Mat3D projection, Mat3D transform) {
        Shader shader = FRRingShader.ring;
        if (shader == null) return;

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
        for (Mesh m : arcs) if (m != null) m.dispose();
        if (blueprint != null) blueprint.dispose();
        if (glow != null) glow.dispose();
    }

    /** Builds one 90 degree arc of the ring: dark inner rail, paneled hull band, bright rim. */
    private Mesh buildArc(int index) {
        float a0 = index * 90f, a1 = a0 + 90f;
        int segments = 18;

        Mesh mesh = newMesh(segments * 3 * 6);

        float railIn = innerRadius, railOut = innerRadius + 0.05f;
        float mainIn = innerRadius + 0.05f, mainOut = outerRadius - 0.05f;
        float rimIn = outerRadius - 0.05f, rimOut = outerRadius;

        boolean last = index == 3;

        for (int i = 0; i < segments; i++) {
            float ta = Mathf.lerp(a0, a1, (float)i / segments);
            float tb = Mathf.lerp(a0, a1, (float)(i + 1) / segments);

            //inner rail
            quad(mesh, ta, tb, railIn, railOut, c(hullDark, 0.95f), c(hullDark, 0.95f));

            //hull band, alternating panels + vertical gradient
            Color topA = (i % 2 == 0 ? hullColor : hullDark.cpy().lerp(hullColor, 0.55f)).cpy().a(0.96f);
            Color botA = hullDark.cpy().a(0.96f);
            Color topB = topA, botB = botA;
            if (last) { //completion arc carries the accent color
                topA = accentColor.cpy().a(0.9f);
                topB = accentColor.cpy().lerp(Color.white, 0.25f).a(0.9f);
                botA = accentColor.cpy().mul(0.55f).a(0.9f);
                botB = botA;
            }
            quadGradient(mesh, ta, tb, mainIn, mainOut, topA, topB, botA, botB);

            //outer rim
            quad(mesh, ta, tb, rimIn, rimOut, last ? c(accentColor, 0.95f) : c(rimColor, 0.9f), last ? c(accentColor, 0.95f) : c(rimColor, 0.9f));
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

    private static void put(Mesh mesh, float angle, float radius, Color color) {
        float rad = angle * Mathf.degRad;
        mesh.getVerticesBuffer()
            .put(Mathf.cos(rad) * radius)
            .put(0f)
            .put(Mathf.sin(rad) * radius)
            .put(color.toFloatBits());
    }

    /** Two triangles between radii r0..r1 across angles ta..tb with a single color. */
    private static void quad(Mesh mesh, float ta, float tb, float r0, float r1, Color ca, Color cb) {
        put(mesh, ta, r0, ca); put(mesh, tb, r0, ca); put(mesh, tb, r1, cb);
        put(mesh, ta, r0, ca); put(mesh, tb, r1, cb); put(mesh, ta, r1, cb);
    }

    /** Same as quad, but the outer edge can differ from the inner edge on both sides. */
    private static void quadGradient(Mesh mesh, float ta, float tb, float r0, float r1, Color topA, Color topB, Color botA, Color botB) {
        put(mesh, ta, r0, botA); put(mesh, tb, r0, botB); put(mesh, tb, r1, topB);
        put(mesh, ta, r0, botA); put(mesh, tb, r1, topB); put(mesh, ta, r1, topA);
    }
}
