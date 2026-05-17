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
        // Bezier bursts
        bezierBurstGold, bezierBurstPurple, bezierBurstBlue, bezierBurstRed, bezierBurstWhite,
        bezierBurstGreen, bezierBurstOrange, bezierBurstCyan, bezierBurstPink,

        // Bezier arcs
        bezierArcGold, bezierArcPurple, bezierArcBlue, bezierArcRed, bezierArcGreen, bezierArcCyan,

        // Bezier rings
        bezierRingGold, bezierRingPurple, bezierRingBlue, bezierRingRed, bezierRingWhite,
        bezierRingGreen, bezierRingCyan, bezierRingOrange, bezierRingPink, bezierRingRainbow,

        // Beam charge effects
        chargeBezierGold, chargeBezierPurple, chargeBezierBlue, chargeBezierRed,
        chargeBezierGreen, chargeBezierCyan, chargeBezierOrange, chargeBezierWhite,

        // Hit effects
        hitBezierGold, hitBezierPurple, hitBezierBlue, hitBezierRed,
        hitBezierWhite, hitBezierGreen, hitBezierCyan,

        // Smoke effects
        smokeBezierGold, smokeBezierPurple, smokeBezierBlue, smokeBezierRed, smokeBezierDark,

        // Trail effects
        trailBezierGold, trailBezierPurple, trailBezierBlue, trailBezierRed, trailBezierGreen, trailBezierCyan,
        trailBezierOrange, trailBezierPink,

        // Additional hit effects
        hitBezierOrange;

    public static void load() {
        // --- Bezier Burst: multiple curves radiating outward ---
        bezierBurstGold = bezierBurst(12, 50, 3f, Pal.accent, Color.white);
        bezierBurstPurple = bezierBurst(10, 45, 3f, Color.valueOf("9e78dc"), Color.valueOf("6d0071"));
        bezierBurstBlue = bezierBurst(10, 45, 3f, Color.valueOf("66b1ff"), Color.valueOf("e4fdff"));
        bezierBurstRed = bezierBurst(10, 45, 3f, Color.valueOf("ff6666"), Color.valueOf("ffcccc"));
        bezierBurstWhite = bezierBurst(10, 45, 3f, Color.white, Color.lightGray);
        bezierBurstGreen = bezierBurst(10, 45, 3f, Color.valueOf("66ff66"), Color.valueOf("ccffcc"));
        bezierBurstOrange = bezierBurst(10, 45, 3f, Color.valueOf("ffaa5f"), Color.valueOf("ffd699"));
        bezierBurstCyan = bezierBurst(10, 45, 3f, Color.valueOf("87ceeb"), Color.valueOf("c0ecff"));
        bezierBurstPink = bezierBurst(10, 45, 3f, Color.valueOf("ff69b4"), Color.valueOf("ffb6d9"));

        // --- Bezier Arc: single sweeping arc ---
        bezierArcGold = bezierArc(50, 4f, Pal.accent, Color.white);
        bezierArcPurple = bezierArc(45, 4f, Color.valueOf("9e78dc"), Color.valueOf("6d0071"));
        bezierArcBlue = bezierArc(45, 4f, Color.valueOf("66b1ff"), Color.valueOf("e4fdff"));
        bezierArcRed = bezierArc(45, 4f, Color.valueOf("ff6666"), Color.valueOf("ffcccc"));
        bezierArcGreen = bezierArc(45, 4f, Color.valueOf("66ff66"), Color.valueOf("ccffcc"));
        bezierArcCyan = bezierArc(45, 4f, Color.valueOf("87ceeb"), Color.valueOf("c0ecff"));

        // --- Bezier Ring: rotating ring of curves ---
        bezierRingGold = bezierRing(8, 30, 2f, Pal.accent, Color.white);
        bezierRingPurple = bezierRing(8, 28, 2f, Color.valueOf("9e78dc"), Color.valueOf("6d0071"));
        bezierRingBlue = bezierRing(8, 28, 2f, Color.valueOf("66b1ff"), Color.valueOf("e4fdff"));
        bezierRingRed = bezierRing(8, 28, 2f, Color.valueOf("ff6666"), Color.valueOf("ffcccc"));
        bezierRingWhite = bezierRing(8, 28, 2f, Color.white, Color.lightGray);
        bezierRingGreen = bezierRing(8, 28, 2f, Color.valueOf("66ff66"), Color.valueOf("ccffcc"));
        bezierRingCyan = bezierRing(8, 28, 2f, Color.valueOf("87ceeb"), Color.valueOf("c0ecff"));
        bezierRingOrange = bezierRing(8, 28, 2f, Color.valueOf("ffaa5f"), Color.valueOf("ffd699"));
        bezierRingPink = bezierRing(8, 28, 2f, Color.valueOf("ff69b4"), Color.valueOf("ffb6d9"));
        bezierRingRainbow = bezierRingRainbow(10, 35, 2.5f);

        // --- Beam charge effects ---
        chargeBezierGold = new Effect(80, e -> {
            Draw.color(Pal.accent, Color.white, e.fin());
            Draw.alpha(1f - e.fin());
            float radius = 4f + e.finpow() * 22f;
            Fill.circle(e.x, e.y, radius);
            Lines.stroke(e.fin() * 2f + 0.5f);
            Lines.circle(e.x, e.y, radius * 1.3f);
            drawBezierRing(e.x, e.y, 0, 6, radius * 1.8f, 2f, Pal.accent, Color.white, e.fin());
        }).layer(Layer.bullet).startDelay(20);

        chargeBezierPurple = chargeEffect(80, Color.valueOf("9e78dc"), Color.valueOf("6d0071"));
        chargeBezierBlue = chargeEffect(80, Color.valueOf("66b1ff"), Color.valueOf("e4fdff"));
        chargeBezierRed = chargeEffect(80, Color.valueOf("ff6666"), Color.valueOf("ffcccc"));
        chargeBezierGreen = chargeEffect(80, Color.valueOf("66ff66"), Color.valueOf("ccffcc"));
        chargeBezierCyan = chargeEffect(80, Color.valueOf("87ceeb"), Color.valueOf("c0ecff"));
        chargeBezierOrange = chargeEffect(80, Color.valueOf("ffaa5f"), Color.valueOf("ffd699"));
        chargeBezierWhite = chargeEffect(80, Color.white, Color.lightGray);

        // --- Hit effects ---
        hitBezierGold = hitEffect(30, 18, 4f, Pal.accent, Color.white);
        hitBezierPurple = hitEffect(30, 16, 4f, Color.valueOf("9e78dc"), Color.valueOf("6d0071"));
        hitBezierBlue = hitEffect(30, 16, 4f, Color.valueOf("66b1ff"), Color.valueOf("e4fdff"));
        hitBezierRed = hitEffect(30, 16, 4f, Color.valueOf("ff6666"), Color.valueOf("ffcccc"));
        hitBezierWhite = hitEffect(30, 16, 4f, Color.white, Color.lightGray);
        hitBezierGreen = hitEffect(30, 16, 4f, Color.valueOf("66ff66"), Color.valueOf("ccffcc"));
        hitBezierCyan = hitEffect(30, 16, 4f, Color.valueOf("87ceeb"), Color.valueOf("c0ecff"));

        // --- Smoke effects ---
        smokeBezierGold = smokeEffect(35, 20, Pal.accent, Color.white);
        smokeBezierPurple = smokeEffect(35, 18, Color.valueOf("9e78dc"), Color.valueOf("6d0071"));
        smokeBezierBlue = smokeEffect(35, 18, Color.valueOf("66b1ff"), Color.valueOf("e4fdff"));
        smokeBezierRed = smokeEffect(35, 18, Color.valueOf("ff6666"), Color.valueOf("ffcccc"));
        smokeBezierDark = smokeEffect(35, 18, Color.valueOf("404040"), Color.valueOf("808080"));

        // --- Trail effects ---
        trailBezierGold = trailEffect(20, 6, Pal.accent, Color.white);
        trailBezierPurple = trailEffect(20, 6, Color.valueOf("9e78dc"), Color.valueOf("6d0071"));
        trailBezierBlue = trailEffect(20, 6, Color.valueOf("66b1ff"), Color.valueOf("e4fdff"));
        trailBezierRed = trailEffect(20, 6, Color.valueOf("ff6666"), Color.valueOf("ffcccc"));
        trailBezierGreen = trailEffect(20, 6, Color.valueOf("66ff66"), Color.valueOf("ccffcc"));
        trailBezierCyan = trailEffect(20, 6, Color.valueOf("87ceeb"), Color.valueOf("c0ecff"));
        trailBezierOrange = trailEffect(20, 6, Color.valueOf("ffaa5f"), Color.valueOf("ffd699"));
        trailBezierPink = trailEffect(20, 6, Color.valueOf("ff69b4"), Color.valueOf("ffb6d9"));

        // --- Additional hit effects ---
        hitBezierOrange = hitEffect(30, 16, 4f, Color.valueOf("ffaa5f"), Color.valueOf("ffd699"));
    }

    // --- Bezier burst factory ---
    private static Effect bezierBurst(int curves, float length, float width, Color c1, Color c2) {
        return new Effect(25, e -> {
            Draw.color(c1, c2, e.fin());
            Draw.alpha(1f - e.fin());
            float progress = e.fin();
            for (int i = 0; i < curves; i++) {
                float angle = e.rotation + i * 360f / curves + progress * 30f;
                v1.set(e.x, e.y);
                v2.trns(angle - 20f, length * 0.6f * progress).add(e.x, e.y);
                v3.trns(angle + 20f, length * 0.6f * progress).add(e.x, e.y);
                v4.trns(angle, length * progress).add(e.x, e.y);
                drawBezier(v1.x, v1.y, v2.x, v2.y, v3.x, v3.y, v4.x, v4.y, width * (1f - progress * 0.5f));
            }
        });
    }

    // --- Bezier arc factory ---
    private static Effect bezierArc(float length, float width, Color c1, Color c2) {
        return new Effect(20, e -> {
            Draw.color(c1, c2, e.fin());
            Draw.alpha(1f - e.fin());
            float progress = e.fin();
            float angle = e.rotation;
            v1.set(e.x, e.y);
            v2.trns(angle - 40f, length * 0.4f * progress).add(e.x, e.y);
            v3.trns(angle + 40f, length * 0.4f * progress).add(e.x, e.y);
            v4.trns(angle, length * progress).add(e.x, e.y);
            drawBezier(v1.x, v1.y, v2.x, v2.y, v3.x, v3.y, v4.x, v4.y, width * (1f - progress * 0.3f));
        });
    }

    // --- Bezier ring factory ---
    private static Effect bezierRing(int curves, float radius, float width, Color c1, Color c2) {
        return new Effect(30, e -> {
            Draw.color(c1, c2, e.fin());
            Draw.alpha(1f - e.fin());
            float progress = e.fin();
            float baseRot = e.rotation + progress * 60f;
            for (int i = 0; i < curves; i++) {
                float a = baseRot + i * 360f / curves;
                float halfArc = 180f / curves;
                v1.set(e.x + Angles.trnsx(a, radius * progress), e.y + Angles.trnsy(a, radius * progress));
                v2.set(e.x + Angles.trnsx(a - halfArc * 0.5f, radius * progress * 1.5f),
                       e.y + Angles.trnsy(a - halfArc * 0.5f, radius * progress * 1.5f));
                v3.set(e.x + Angles.trnsx(a + halfArc * 0.5f, radius * progress * 1.5f),
                       e.y + Angles.trnsy(a + halfArc * 0.5f, radius * progress * 1.5f));
                v4.set(e.x + Angles.trnsx(a + halfArc, radius * progress),
                       e.y + Angles.trnsy(a + halfArc, radius * progress));
                drawBezier(v1.x, v1.y, v2.x, v2.y, v3.x, v3.y, v4.x, v4.y, width);
            }
        });
    }

    // --- Rainbow ring ---
    private static Effect bezierRingRainbow(int curves, float radius, float width) {
        return new Effect(35, e -> {
            float progress = e.fin();
            float baseRot = e.rotation + progress * 80f;
            for (int i = 0; i < curves; i++) {
                float a = baseRot + i * 360f / curves;
                float hue = (float)i / curves + progress * 0.3f;
                Draw.color(Color.HSVtoRGB(hue * 360f, 0.8f, 1f), 1f - progress);
                float halfArc = 180f / curves;
                v1.set(e.x + Angles.trnsx(a, radius * progress), e.y + Angles.trnsy(a, radius * progress));
                v2.set(e.x + Angles.trnsx(a - halfArc * 0.5f, radius * progress * 1.5f),
                       e.y + Angles.trnsy(a - halfArc * 0.5f, radius * progress * 1.5f));
                v3.set(e.x + Angles.trnsx(a + halfArc * 0.5f, radius * progress * 1.5f),
                       e.y + Angles.trnsy(a + halfArc * 0.5f, radius * progress * 1.5f));
                v4.set(e.x + Angles.trnsx(a + halfArc, radius * progress),
                       e.y + Angles.trnsy(a + halfArc, radius * progress));
                drawBezier(v1.x, v1.y, v2.x, v2.y, v3.x, v3.y, v4.x, v4.y, width);
            }
        });
    }

    // --- Generic charge effect ---
    private static Effect chargeEffect(float lifetime, Color c1, Color c2) {
        return new Effect(lifetime, e -> {
            Draw.color(c1, c2, e.fin());
            Draw.alpha(1f - e.fin());
            float radius = 3f + e.finpow() * 20f;
            Fill.circle(e.x, e.y, radius);
            Lines.stroke(e.fin() * 2f + 0.5f);
            Lines.circle(e.x, e.y, radius * 1.4f);
            drawBezierRing(e.x, e.y, e.rotation, 6, radius * 1.8f, 1.5f, c1, c2, e.fin());
        }).layer(Layer.bullet).startDelay(15);
    }

    // --- Hit effect ---
    private static Effect hitEffect(float lifetime, float length, float width, Color c1, Color c2) {
        return new Effect(lifetime, e -> {
            Draw.color(c1, c2, e.fin());
            Draw.alpha(1f - e.fin());
            float progress = e.fin();
            for (int i = 0; i < 8; i++) {
                float angle = i * 45f + progress * 40f;
                v1.set(e.x, e.y);
                v2.trns(angle - 25f, length * 0.5f * progress).add(e.x, e.y);
                v3.trns(angle + 25f, length * 0.5f * progress).add(e.x, e.y);
                v4.trns(angle, length * progress).add(e.x, e.y);
                drawBezier(v1.x, v1.y, v2.x, v2.y, v3.x, v3.y, v4.x, v4.y, width * (1f - progress * 0.6f));
            }
        });
    }

    // --- Smoke effect ---
    private static Effect smokeEffect(float lifetime, float length, Color c1, Color c2) {
        return new Effect(lifetime, e -> {
            Draw.color(c1, c2, e.fin());
            float alpha = 1f - e.fin();
            Draw.alpha(alpha * 0.6f);
            float progress = e.fin();
            for (int i = 0; i < 6; i++) {
                float angle = e.rotation + i * 60f + Mathf.randomSeed(e.id + i, -15f, 15f);
                v1.set(e.x, e.y);
                float len = length * progress * Mathf.randomSeed(e.id + i * 7, 0.5f, 1f);
                v2.trns(angle - 15f, len * 0.4f).add(e.x, e.y);
                v3.trns(angle + 15f, len * 0.4f).add(e.x, e.y);
                v4.trns(angle, len).add(e.x, e.y);
                drawBezier(v1.x, v1.y, v2.x, v2.y, v3.x, v3.y, v4.x, v4.y, 3f * (1f - progress * 0.7f));
            }
        });
    }

    // --- Trail effect ---
    private static Effect trailEffect(float lifetime, float size, Color c1, Color c2) {
        return new Effect(lifetime, e -> {
            Draw.color(c1, c2, e.fin());
            float alpha = 1f - e.fin();
            Draw.alpha(alpha * 0.7f);
            float progress = e.fin();
            v1.set(e.x, e.y);
            float len = size * 3f * progress;
            v2.trns(e.rotation - 20f, len * 0.4f).add(e.x, e.y);
            v3.trns(e.rotation + 20f, len * 0.4f).add(e.x, e.y);
            v4.trns(e.rotation, len).add(e.x, e.y);
            drawBezier(v1.x, v1.y, v2.x, v2.y, v3.x, v3.y, v4.x, v4.y, size * (1f - progress * 0.5f));
        });
    }

    // --- Cubic Bezier drawing helper ---
    private static void drawBezier(float x1, float y1, float x2, float y2, float x3, float y3, float x4, float y4, float width) {
        int steps = Math.max(6, (int)(Math.sqrt(Math.abs(x4 - x1) + Math.abs(y4 - y1)) * 2));
        float lastX = x1, lastY = y1;
        for (int i = 1; i <= steps; i++) {
            float t = (float)i / steps;
            float u = 1f - t;
            float uu = u * u, uuu = uu * u;
            float tt = t * t, ttt = tt * t;
            float bx = uuu * x1 + 3f * uu * t * x2 + 3f * u * tt * x3 + ttt * x4;
            float by = uuu * y1 + 3f * uu * t * y2 + 3f * u * tt * y3 + ttt * y4;
            Lines.stroke(width * (1f - (float)i / steps * 0.3f));
            Lines.line(lastX, lastY, bx, by);
            lastX = bx;
            lastY = by;
        }
    }

    // --- Bezier ring drawing helper ---
    private static void drawBezierRing(float cx, float cy, float rot, int curves, float radius, float width, Color c1, Color c2, float progress) {
        Draw.color(c1, c2, progress);
        Draw.alpha(1f - progress);
        for (int i = 0; i < curves; i++) {
            float a = rot + i * 360f / curves + progress * 50f;
            float ha = 180f / curves;
            v1.set(cx + Angles.trnsx(a, radius * progress), cy + Angles.trnsy(a, radius * progress));
            v2.set(cx + Angles.trnsx(a - ha * 0.4f, radius * progress * 1.3f),
                   cy + Angles.trnsy(a - ha * 0.4f, radius * progress * 1.3f));
            v3.set(cx + Angles.trnsx(a + ha * 0.4f, radius * progress * 1.3f),
                   cy + Angles.trnsy(a + ha * 0.4f, radius * progress * 1.3f));
            v4.set(cx + Angles.trnsx(a + ha, radius * progress),
                   cy + Angles.trnsy(a + ha, radius * progress));
            drawBezier(v1.x, v1.y, v2.x, v2.y, v3.x, v3.y, v4.x, v4.y, width);
        }
    }
}
