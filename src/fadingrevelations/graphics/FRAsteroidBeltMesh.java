package fadingrevelations.graphics;

import arc.graphics.Color;
import arc.math.Mathf;
import arc.math.geom.Mat3D;
import arc.math.geom.Vec3;
import arc.struct.Seq;
import arc.util.Rand;
import arc.util.Time;
import mindustry.graphics.g3d.GenericMesh;
import mindustry.graphics.g3d.MatMesh;
import mindustry.graphics.g3d.MultiMesh;
import mindustry.graphics.g3d.NoiseMesh;
import mindustry.graphics.g3d.PlanetParams;
import mindustry.type.Planet;

/**
 * A toroidal asteroid belt rendered around a planet - debris fields of
 * Cerbero, remnants of whatever happened there long ago.
 *
 * Purely visual: it is a mesh attached to an existing planet and adds no
 * Planet object, so orbit, sector and launch logic is untouched. The belt
 * radius must stay outside the orbits of the planet's moons (see FRPlanets).
 *
 * Structure adapted from the Omaloon mod's AsteroidBeltMesh (GPL-3): vanilla
 * {@link NoiseMesh} rocks, each offset by a {@link MatMesh}, combined in a
 * {@link MultiMesh}. GPU resources are created lazily on the first render
 * call (GL thread), like {@link FROrbitalRingMesh}.
 */
public class FRAsteroidBeltMesh implements GenericMesh {
    private static final Mat3D mat = new Mat3D();

    public final Planet planet;

    /** Number of rocks in the belt. */
    public int rockCount = 24;
    /** Belt center distance from the planet center, in units of planet radius. */
    public float beltRadius = 3.4f;
    /** Randomized spread of the belt along and around its radius. */
    public float beltWidth = 0.5f, beltHeight = 0.22f;
    /** Belt plane tilt in degrees. */
    public float tilt = 14f;
    /** Slow drift of the whole belt, degrees per second. */
    public float spinSpeed = 0.04f;
    /** Rock size range, in units of planet radius. */
    public float minSize = 0.03f, maxSize = 0.09f;
    /** Noise seed. */
    public int seed = 1337;

    public Color rockColor = Color.valueOf("6e6a63");
    public Color rockColor2 = Color.valueOf("443f39");

    protected MultiMesh rocks;
    protected boolean built = false, failed = false;

    public FRAsteroidBeltMesh(Planet planet) {
        this.planet = planet;
        //NOTE: no GPU work here - mesh loaders run on a thread without GL context
    }

    protected void build() {
        built = true;
        try {
            Rand rand = new Rand(seed);
            Seq<GenericMesh> seq = new Seq<>(rockCount);

            for (int i = 0; i < rockCount; i++) {
                float angleStep = 360f / rockCount;
                float angle = i * angleStep + rand.range(angleStep / 1.5f);
                float dist = beltRadius + rand.range(beltWidth / 2f);
                float yOff = rand.range(beltHeight / 2f);
                float size = rand.random(minSize, maxSize);

                float cx = Mathf.cosDeg(angle) * dist, cy = yOff, cz = Mathf.sinDeg(angle) * dist;

                seq.add(new MatMesh(
                    new NoiseMesh(planet, seed + i * 7, 1, size, 2, 0.55f, 35f, size * 0.35f,
                        rockColor, rockColor2, 3, 0.6f, 0.38f, 0.5f),
                    new Mat3D().setToTranslation(cx, cy, cz)
                ));
            }

            rocks = new MultiMesh(seq.toArray(GenericMesh.class));
        } catch (Throwable t) {
            failed = true;
            arc.util.Log.err("Fading Revelations: failed to build the asteroid belt mesh, belt disabled", t);
        }
    }

    @Override
    public void render(PlanetParams params, Mat3D projection, Mat3D transform) {
        if (failed) return;
        if (!built) build();
        if (rocks == null) return;

        //hide the belt in the zoomed-in surface view, like vanilla clouds
        if (params.planet == planet && Mathf.zero(1f - params.uiAlpha, 0.01f)) return;

        mat.setToTranslation(planet.position)
            .rotate(Vec3.X, tilt)
            .rotate(Vec3.Y, Time.globalTime * spinSpeed);
        rocks.render(params, projection, mat);
    }

    @Override
    public void dispose() {
        if (rocks != null) rocks.dispose();
    }
}
