package fadingrevelations.content;

import arc.graphics.*;
import arc.graphics.g2d.*;
import arc.math.*;
import arc.math.geom.*;
import mindustry.entities.Effect;
import mindustry.graphics.*;

public class FRFx {
    private static final Vec2 v1 = new Vec2();
    private static final Vec2 v2 = new Vec2();
    private static final Vec2 v3 = new Vec2();
    private static final Vec2 v4 = new Vec2();

    public static Effect
        bezierBurstGold, bezierBurstPurple, bezierBurstBlue, bezierBurstRed, bezierBurstWhite,
        bezierBurstGreen, bezierBurstOrange, bezierBurstCyan, bezierBurstPink,

        bezierArcGold, bezierArcPurple, bezierArcBlue, bezierArcRed, bezierArcGreen, bezierArcCyan,

        bezierRingGold, bezierRingPurple, bezierRingBlue, bezierRingRed, bezierRingWhite,
        bezierRingGreen, bezierRingCyan, bezierRingOrange, bezierRingPink, bezierRingRainbow,

        chargeBezierGold, chargeBezierPurple, chargeBezierBlue, chargeBezierRed,
        chargeBezierGreen, chargeBezierCyan, chargeBezierOrange, chargeBezierWhite,

        hitBezierGold, hitBezierPurple, hitBezierBlue, hitBezierRed,
        hitBezierWhite, hitBezierGreen, hitBezierCyan,

        smokeBezierGold, smokeBezierPurple, smokeBezierBlue, smokeBezierRed, smokeBezierDark,

        trailBezierGold, trailBezierPurple, trailBezierBlue, trailBezierRed, trailBezierGreen, trailBezierCyan,
        trailBezierOrange, trailBezierPink,

        hitBezierOrange;

    public static void load() {
        bezierBurstGold = bezierBurst(12, 65, 3.5f, Pal.accent, Color.white);
        bezierBurstPurple = bezierBurst(10, 55, 3.5f, Color.valueOf("9e78dc"), Color.valueOf("e2b3ff"));
        bezierBurstBlue = bezierBurst(10, 55, 3.5f, Color.valueOf("66b1ff"), Color.valueOf("e4fdff"));
        bezierBurstRed = bezierBurst(10, 55, 3.5f, Color.valueOf("ff6666"), Color.valueOf("ffcccc"));
        bezierBurstWhite = bezierBurst(10, 55, 3.5f, Color.white, Color.lightGray);
        bezierBurstGreen = bezierBurst(10, 55, 3.5f, Color.valueOf("66ff66"), Color.valueOf("ccffcc"));
        bezierBurstOrange = bezierBurst(10, 55, 3.5f, Color.valueOf("ffaa5f"), Color.valueOf("ffd699"));
        bezierBurstCyan = bezierBurst(10, 55, 3.5f, Color.valueOf("87ceeb"), Color.valueOf("c0ecff"));
        bezierBurstPink = bezierBurst(10, 55, 3.5f, Color.valueOf("ff69b4"), Color.valueOf("ffb6d9"));

        bezierArcGold = bezierArc(60, 4.5f, Pal.accent, Color.white);
        bezierArcPurple = bezierArc(55, 4.5f, Color.valueOf("9e78dc"), Color.valueOf("e2b3ff"));
        bezierArcBlue = bezierArc(55, 4.5f, Color.valueOf("66b1ff"), Color.valueOf("e4fdff"));
        bezierArcRed = bezierArc(55, 4.5f, Color.valueOf("ff6666"), Color.valueOf("ffcccc"));
        bezierArcGreen = bezierArc(55, 4.5f, Color.valueOf("66ff66"), Color.valueOf("ccffcc"));
        bezierArcCyan = bezierArc(55, 4.5f, Color.valueOf("87ceeb"), Color.valueOf("c0ecff"));

        bezierRingGold = bezierRing(8, 35, 2.5f, Pal.accent, Color.white);
        bezierRingPurple = bezierRing(8, 32, 2.5f, Color.valueOf("9e78dc"), Color.valueOf("e2b3ff"));
        bezierRingBlue = bezierRing(8, 32, 2.5f, Color.valueOf("66b1ff"), Color.valueOf("e4fdff"));
        bezierRingRed = bezierRing(8, 32, 2.5f, Color.valueOf("ff6666"), Color.valueOf("ffcccc"));
        bezierRingWhite = bezierRing(8, 32, 2.5f, Color.white, Color.lightGray);
        bezierRingGreen = bezierRing(8, 32, 2.5f, Color.valueOf("66ff66"), Color.valueOf("ccffcc"));
        bezierRingCyan = bezierRing(8, 32, 2.5f, Color.valueOf("87ceeb"), Color.valueOf("c0ecff"));
        bezierRingOrange = bezierRing(8, 32, 2.5f, Color.valueOf("ffaa5f"), Color.valueOf("ffd699"));
        bezierRingPink = bezierRing(8, 32, 2.5f, Color.valueOf("ff69b4"), Color.valueOf("ffb6d9"));
        bezierRingRainbow = bezierRingRainbow(10, 40, 3f);

        chargeBezierGold = chargeEffect(60, Pal.accent, Color.white);
        chargeBezierPurple = chargeEffect(60, Color.valueOf("9e78dc"), Color.valueOf("e2b3ff"));
        chargeBezierBlue = chargeEffect(60, Color.valueOf("66b1ff"), Color.valueOf("e4fdff"));
        chargeBezierRed = chargeEffect(60, Color.valueOf("ff6666"), Color.valueOf("ffcccc"));
        chargeBezierGreen = chargeEffect(60, Color.valueOf("66ff66"), Color.valueOf("ccffcc"));
        chargeBezierCyan = chargeEffect(60, Color.valueOf("87ceeb"), Color.valueOf("c0ecff"));
        chargeBezierOrange = chargeEffect(60, Color.valueOf("ffaa5f"), Color.valueOf("ffd699"));
        chargeBezierWhite = chargeEffect(60, Color.white, Color.lightGray);

        hitBezierGold = hitEffect(25, 24, 4.5f, Pal.accent, Color.white);
        hitBezierPurple = hitEffect(25, 22, 4.5f, Color.valueOf("9e78dc"), Color.valueOf("e2b3ff"));
        hitBezierBlue = hitEffect(25, 22, 4.5f, Color.valueOf("66b1ff"), Color.valueOf("e4fdff"));
        hitBezierRed = hitEffect(25, 22, 4.5f, Color.valueOf("ff6666"), Color.valueOf("ffcccc"));
        hitBezierWhite = hitEffect(25, 22, 4.5f, Color.white, Color.lightGray);
        hitBezierGreen = hitEffect(25, 22, 4.5f, Color.valueOf("66ff66"), Color.valueOf("ccffcc"));
        hitBezierCyan = hitEffect(25, 22, 4.5f, Color.valueOf("87ceeb"), Color.valueOf("c0ecff"));
        hitBezierOrange = hitEffect(25, 22, 4.5f, Color.valueOf("ffaa5f"), Color.valueOf("ffd699"));

        smokeBezierGold = smokeEffect(45, 28, Pal.accent, Color.white);
        smokeBezierPurple = smokeEffect(45, 25, Color.valueOf("9e78dc"), Color.valueOf("6d0071"));
        smokeBezierBlue = smokeEffect(45, 25, Color.valueOf("66b1ff"), Color.valueOf("e4fdff"));
        smokeBezierRed = smokeEffect(45, 25, Color.valueOf("ff6666"), Color.valueOf("ffcccc"));
        smokeBezierDark = smokeEffect(45, 25, Color.valueOf("3a3a3a"), Color.valueOf("6e6e6e"));

        trailBezierGold = trailEffect(18, 5, Pal.accent, Color.white);
        trailBezierPurple = trailEffect(18, 5, Color.valueOf("9e78dc"), Color.valueOf("e2b3ff"));
        trailBezierBlue = trailEffect(18, 5, Color.valueOf("66b1ff"), Color.valueOf("e4fdff"));
        trailBezierRed = trailEffect(18, 5, Color.valueOf("ff6666"), Color.valueOf("ffcccc"));
        trailBezierGreen = trailEffect(18, 5, Color.valueOf("66ff66"), Color.valueOf("ccffcc"));
        trailBezierCyan = trailEffect(18, 5, Color.valueOf("87ceeb"), Color.valueOf("c0ecff"));
        trailBezierOrange = trailEffect(18, 5, Color.valueOf("ffaa5f"), Color.valueOf("ffd699"));
        trailBezierPink = trailEffect(18, 5, Color.valueOf("ff69b4"), Color.valueOf("ffb6d9"));
    }

    private static Effect bezierBurst(int curves, float length, float width, Color c1, Color c2) {
        return new Effect(30, e -> {
            float progress = Interp.pow3Out.apply(e.fin());
            float alpha = Interp.fade.apply(e.fin());

            Draw.color(c1, c2, e.fin());
            Draw.alpha(alpha);

            for (int i = 0; i < curves; i++) {
                float angle = e.rotation + i * 360f / curves + e.fin() * 45f;
                v1.set(e.x, e.y);
                v2.trns(angle - 25f, length * 0.4f * progress).add(e.x, e.y);
                v3.trns(angle + 25f, length * 0.7f * progress).add(e.x, e.y);
                v4.trns(angle, length * progress).add(e.x, e.y);
                drawBezier(v1.x, v1.y, v2.x, v2.y, v3.x, v3.y, v4.x, v4.y, width * (1f - e.fin() * 0.7f));
            }
        });
    }

    private static Effect bezierArc(float length, float width, Color c1, Color c2) {
        return new Effect(24, e -> {
            float progress = Interp.pow2Out.apply(e.fin());
            float alpha = Interp.fade.apply(e.fin());

            Draw.color(c1, c2, e.fin());
            Draw.alpha(alpha);

            float angle = e.rotation;
            float wave = Mathf.sin(e.fin() * Mathf.PI) * 15f;

            v1.set(e.x, e.y);
            v2.trns(angle - 35f + wave, length * 0.35f * progress).add(e.x, e.y);
            v3.trns(angle + 35f - wave, length * 0.7f * progress).add(e.x, e.y);
            v4.trns(angle, length * progress).add(e.x, e.y);
            drawBezier(v1.x, v1.y, v2.x, v2.y, v3.x, v3.y, v4.x, v4.y, width * (1f - e.fin() * 0.5f));
        });
    }

    private static Effect bezierRing(int curves, float radius, float width, Color c1, Color c2) {
        return new Effect(35, e -> {
            float progress = Interp.pow2Out.apply(e.fin());
            float alpha = Interp.pow3Out.apply(1f - e.fin());
            float baseRot = e.rotation + e.fin() * 90f;

            Draw.color(c1, c2, e.fin());
            Draw.alpha(alpha);

            for (int i = 0; i < curves; i++) {
                float a = baseRot + i * 360f / curves;
                float halfArc = 180f / curves;

                v1.trns(a, radius * progress).add(e.x, e.y);
                v2.trns(a + halfArc * 0.3f, radius * progress * 1.4f).add(e.x, e.y);
                v3.trns(a + halfArc * 0.7f, radius * progress * 1.4f).add(e.x, e.y);
                v4.trns(a + halfArc, radius * progress).add(e.x, e.y);

                drawBezier(v1.x, v1.y, v2.x, v2.y, v3.x, v3.y, v4.x, v4.y, width * alpha);
            }
        });
    }

    private static Effect bezierRingRainbow(int curves, float radius, float width) {
        return new Effect(40, e -> {
            float progress = Interp.pow2Out.apply(e.fin());
            float alpha = Interp.fade.apply(e.fin());
            float baseRot = e.rotation + e.fin() * 110f;

            for (int i = 0; i < curves; i++) {
                float a = baseRot + i * 360f / curves;
                float hue = ((float)i / curves + e.fin() * 0.5f) * 360f;

                Draw.color(Color.HSVtoRGB(hue, 0.75f, 1f));
                Draw.alpha(alpha);

                float halfArc = 180f / curves;
                v1.trns(a, radius * progress).add(e.x, e.y);
                v2.trns(a + halfArc * 0.3f, radius * progress * 1.4f).add(e.x, e.y);
                v3.trns(a + halfArc * 0.7f, radius * progress * 1.4f).add(e.x, e.y);
                v4.trns(a + halfArc, radius * progress).add(e.x, e.y);

                drawBezier(v1.x, v1.y, v2.x, v2.y, v3.x, v3.y, v4.x, v4.y, width * alpha);
            }
        });
    }

    private static Effect chargeEffect(float lifetime, Color c1, Color c2) {
        return new Effect(lifetime, e -> {
            float invProgress = 1f - e.fin();
            float alpha = Interp.pow2In.apply(e.fin());
            float radius = 25f * invProgress + 2f;

            Draw.color(c1, c2, e.fin());
            Draw.alpha(alpha);

            Fill.circle(e.x, e.y, 1.5f + e.fin() * 4f);
            Lines.stroke(0.5f + alpha * 1.5f);
            Lines.circle(e.x, e.y, radius);

            drawBezierRing(e.x, e.y, e.rotation - e.fin() * 120f, 5, radius * 1.3f, 1.5f * alpha, c1, c2, invProgress);
        }).layer(Layer.bullet);
    }

    private static Effect hitEffect(float lifetime, float length, float width, Color c1, Color c2) {
        return new Effect(lifetime, e -> {
            float progress = Interp.pow3Out.apply(e.fin());
            float alpha = Interp.fade.apply(e.fin());

            Draw.color(c1, c2, e.fin());
            Draw.alpha(alpha);

            Fill.circle(e.x, e.y, width * (1f - progress));

            for (int i = 0; i < 6; i++) {
                float angle = e.rotation + i * 60f + Mathf.randomSeed(e.id + i, -20f, 20f);
                v1.set(e.x, e.y);
                v2.trns(angle - 15f, length * 0.4f * progress).add(e.x, e.y);
                v3.trns(angle + 15f, length * 0.7f * progress).add(e.x, e.y);
                v4.trns(angle, length * progress).add(e.x, e.y);
                drawBezier(v1.x, v1.y, v2.x, v2.y, v3.x, v3.y, v4.x, v4.y, width * (1f - e.fin()));
            }
        });
    }

    private static Effect smokeEffect(float lifetime, float length, Color c1, Color c2) {
        return new Effect(lifetime, e -> {
            float progress = Interp.pow2Out.apply(e.fin());
            float alpha = Interp.pow2Out.apply(1f - e.fin()) * 0.5f;

            Draw.color(c1, c2, e.fin());
            Draw.alpha(alpha);

            for (int i = 0; i < 4; i++) {
                float angle = e.rotation + i * 90f + Mathf.randomSeed(e.id + i, -25f, 25f) + (e.fin() * 20f);
                float len = length * progress * Mathf.randomSeed(e.id + i * 11, 0.6f, 1.2f);

                v1.set(e.x, e.y);
                v2.trns(angle - 30f, len * 0.35f).add(e.x, e.y);
                v3.trns(angle + 30f, len * 0.65f).add(e.x, e.y);
                v4.trns(angle, len).add(e.x, e.y);
                drawBezier(v1.x, v1.y, v2.x, v2.y, v3.x, v3.y, v4.x, v4.y, 4f * (1f - e.fin() * 0.8f));
            }
        });
    }

    private static Effect trailEffect(float lifetime, float size, Color c1, Color c2) {
        return new Effect(lifetime, e -> {
            float progress = e.fin();
            float alpha = Interp.pow2In.apply(1f - progress) * 0.8f;

            Draw.color(c1, c2, progress);
            Draw.alpha(alpha);

            v1.set(e.x, e.y);
            float len = size * 4f * progress;

            v2.trns(e.rotation - 180f - 15f, len * 0.4f).add(e.x, e.y);
            v3.trns(e.rotation - 180f + 15f, len * 0.7f).add(e.x, e.y);
            v4.trns(e.rotation - 180f, len).add(e.x, e.y);

            drawBezier(v1.x, v1.y, v2.x, v2.y, v3.x, v3.y, v4.x, v4.y, size * alpha);
        });
    }

    private static void drawBezier(float x1, float y1, float x2, float y2, float x3, float y3, float x4, float y4, float width) {
        int steps = Math.max(8, (int)(Math.sqrt(Math.abs(x4 - x1) + Math.abs(y4 - y1)) * 1.5f));
        float lastX = x1, lastY = y1;

        for (int i = 1; i <= steps; i++) {
            float t = (float)i / steps;
            float u = 1f - t;

            float bx = u * u * u * x1 + 3f * u * u * t * x2 + 3f * u * t * t * x3 + t * t * t * x4;
            float by = u * u * u * y1 + 3f * u * u * t * y2 + 3f * u * t * t * y3 + t * t * t * y4;

            Lines.stroke(width * (1f - t * 0.4f));
            Lines.line(lastX, lastY, bx, by);

            lastX = bx;
            lastY = by;
        }
    }

    private static void drawBezierRing(float cx, float cy, float rot, int curves, float radius, float width, Color c1, Color c2, float progress) {
        for (int i = 0; i < curves; i++) {
            float a = rot + i * 360f / curves;
            float ha = 180f / curves;

            v1.trns(a, radius).add(cx, cy);
            v2.trns(a + ha * 0.3f, radius * 1.3f).add(cx, cy);
            v3.trns(a + ha * 0.7f, radius * 1.3f).add(cx, cy);
            v4.trns(a + ha, radius).add(cx, cy);

            drawBezier(v1.x, v1.y, v2.x, v2.y, v3.x, v3.y, v4.x, v4.y, width);
        }
    }
}
