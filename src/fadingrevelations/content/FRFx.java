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
        bezierArcWhite, bezierArcOrange, bezierArcPink,

        bezierRingGold, bezierRingPurple, bezierRingBlue, bezierRingRed, bezierRingWhite,
        bezierRingGreen, bezierRingCyan, bezierRingOrange, bezierRingPink, bezierRingRainbow,

        chargeBezierGold, chargeBezierPurple, chargeBezierBlue, chargeBezierRed,
        chargeBezierGreen, chargeBezierCyan, chargeBezierOrange, chargeBezierWhite,

        hitBezierGold, hitBezierPurple, hitBezierBlue, hitBezierRed,
        hitBezierWhite, hitBezierGreen, hitBezierCyan, hitBezierOrange, hitBezierPink,

        smokeBezierGold, smokeBezierPurple, smokeBezierBlue, smokeBezierRed, smokeBezierDark,
        smokeBezierGreen, smokeBezierCyan, smokeBezierOrange, smokeBezierWhite,

        trailBezierGold, trailBezierPurple, trailBezierBlue, trailBezierRed, trailBezierGreen, trailBezierCyan,
        trailBezierOrange, trailBezierPink, trailBezierWhite;

    public static Effect stasisWave;

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
        bezierArcWhite = bezierArc(55, 4.5f, Color.white, Color.lightGray);
        bezierArcOrange = bezierArc(55, 4.5f, Color.valueOf("ffaa5f"), Color.valueOf("ffd699"));
        bezierArcPink = bezierArc(55, 4.5f, Color.valueOf("ff69b4"), Color.valueOf("ffb6d9"));

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
        hitBezierPink = hitEffect(25, 22, 4.5f, Color.valueOf("ff69b4"), Color.valueOf("ffb6d9"));

        smokeBezierGold = smokeEffect(45, 28, Pal.accent, Color.white);
        smokeBezierPurple = smokeEffect(45, 25, Color.valueOf("9e78dc"), Color.valueOf("6d0071"));
        smokeBezierBlue = smokeEffect(45, 25, Color.valueOf("66b1ff"), Color.valueOf("e4fdff"));
        smokeBezierRed = smokeEffect(45, 25, Color.valueOf("ff6666"), Color.valueOf("ffcccc"));
        smokeBezierDark = smokeEffect(45, 25, Color.valueOf("3a3a3a"), Color.valueOf("6e6e6e"));
        smokeBezierGreen = smokeEffect(45, 25, Color.valueOf("66ff66"), Color.valueOf("ccffcc"));
        smokeBezierCyan = smokeEffect(45, 25, Color.valueOf("87ceeb"), Color.valueOf("c0ecff"));
        smokeBezierOrange = smokeEffect(45, 25, Color.valueOf("ffaa5f"), Color.valueOf("ffd699"));
        smokeBezierWhite = smokeEffect(45, 25, Color.white, Color.lightGray);

        trailBezierGold = trailEffect(18, 5, Pal.accent, Color.white);
        trailBezierPurple = trailEffect(18, 5, Color.valueOf("9e78dc"), Color.valueOf("e2b3ff"));
        trailBezierBlue = trailEffect(18, 5, Color.valueOf("66b1ff"), Color.valueOf("e4fdff"));
        trailBezierRed = trailEffect(18, 5, Color.valueOf("ff6666"), Color.valueOf("ffcccc"));
        trailBezierGreen = trailEffect(18, 5, Color.valueOf("66ff66"), Color.valueOf("ccffcc"));
        trailBezierCyan = trailEffect(18, 5, Color.valueOf("87ceeb"), Color.valueOf("c0ecff"));
        trailBezierOrange = trailEffect(18, 5, Color.valueOf("ffaa5f"), Color.valueOf("ffd699"));
        trailBezierPink = trailEffect(18, 5, Color.valueOf("ff69b4"), Color.valueOf("ffb6d9"));
        trailBezierWhite = trailEffect(18, 5, Color.white, Color.lightGray);

        stasisWave = new Effect(50f, 1000f, e -> {
            float f = e.fin();
            float fout = e.fout();

            Draw.color(Color.valueOf("e5ed2c"), Color.valueOf("b8c418"), f);
            Draw.alpha(0.6f * fout);
            Fill.circle(e.x, e.y, 12f * fout);

            Draw.color(Color.valueOf("e5ed2c"), Color.valueOf("cfd712"), f);
            Lines.stroke(2.5f * fout + 0.3f);
            Lines.circle(e.x, e.y, 200f * f);

            Draw.color(Color.valueOf("cfd712"), Color.valueOf("a0aa10"), f);
            Lines.stroke(1.8f * fout + 0.2f);
            Lines.circle(e.x, e.y, 350f * f);

            Draw.color(Color.valueOf("a0aa10"), Color.valueOf("7a8510"), f);
            Lines.stroke(1.2f * fout + 0.1f);
            Lines.circle(e.x, e.y, 500f * f);

            Draw.color(Color.valueOf("e5ed2c"), Color.white, f * 0.5f);
            Lines.stroke(0.8f * fout);
            Lines.circle(e.x, e.y, 640f * f);

            Angles.randLenVectors(e.id, 24, 640f * f, (x, y) -> {
                float len = Mathf.sqrt(x * x + y * y);
                float alpha = fout * (1f - len / 700f);
                if (alpha <= 0f) return;
                Draw.color(Color.valueOf("e5ed2c"), Color.valueOf("cfd712"), f);
                Draw.alpha(alpha * 0.7f);
                Fill.circle(e.x + x, e.y + y, 2f * fout + 0.5f);
            });

            Draw.color(Color.white, 0.4f * fout);
            Fill.circle(e.x, e.y, 640f * f);
        }).layer(Layer.bullet);
    }

    private static Effect bezierBurst(int curves, float length, float width, Color c1, Color c2) {
        return new Effect(30f, e -> {
            Draw.color(c1, c2, e.fin());
            Lines.stroke(e.fout() * width);
            Angles.randLenVectors(e.id, curves, length * e.fin(), e.rotation, 25f, (x, y) -> {
                Lines.lineAngle(e.x + x, e.y + y, Mathf.angle(x, y), e.fout() * length * 0.2f);
            });
            Fill.circle(e.x, e.y, e.fout() * width * 1.5f);
        });
    }

    private static Effect bezierArc(float length, float width, Color c1, Color c2) {
        return new Effect(24f, e -> {
            Draw.color(c1, c2, e.fin());
            Lines.stroke(e.fout() * width);
            for(int i = -2; i <= 2; i++) {
                v1.trns(e.rotation + i * 15f, length * e.fin());
                Lines.lineAngle(e.x + v1.x, e.y + v1.y, e.rotation + i * 15f, e.fout() * length * 0.3f);
            }
        });
    }

    private static Effect bezierRing(int curves, float radius, float width, Color c1, Color c2) {
        return new Effect(35f, e -> {
            Draw.color(c1, c2, e.fin());
            Lines.stroke(e.fout() * width);
            Lines.circle(e.x, e.y, radius * e.fin());
            Angles.randLenVectors(e.id, curves, radius * 1.3f * e.fin(), (x, y) -> {
                Fill.circle(e.x + x, e.y + y, e.fout() * width * 0.8f);
            });
        });
    }

    private static Effect bezierRingRainbow(int curves, float radius, float width) {
        return new Effect(40f, e -> {
            Lines.stroke(e.fout() * width);
            for(int i = 0; i < curves; i++) {
                float a = i * 360f / curves;
                Draw.color(Color.HSVtoRGB((a + e.fin() * 180f) % 360f, 0.8f, 1f));
                v1.trns(a, radius * e.fin());
                Lines.lineAngle(e.x + v1.x, e.y + v1.y, a, e.fout() * radius * 0.4f);
            }
        });
    }

    private static Effect chargeEffect(float lifetime, Color c1, Color c2) {
        return new Effect(lifetime, e -> {
            Draw.color(c1, c2, e.fin());
            Fill.circle(e.x, e.y, e.fin() * 5f);
            Lines.stroke(e.fin() * 2f);
            Lines.circle(e.x, e.y, 25f * e.fout());
            Angles.randLenVectors(e.id, 8, 25f * e.fout(), (x, y) -> {
                Fill.circle(e.x + x, e.y + y, e.fin() * 2f);
            });
        }).layer(Layer.bullet);
    }

    private static Effect hitEffect(float lifetime, float length, float width, Color c1, Color c2) {
        return new Effect(lifetime, e -> {
            Draw.color(c1, c2, e.fin());
            Lines.stroke(e.fout() * width * 0.5f);
            Lines.circle(e.x, e.y, length * e.fin());
            Angles.randLenVectors(e.id, 6, length * 1.2f * e.fin(), (x, y) -> {
                Fill.circle(e.x + x, e.y + y, e.fout() * width * 0.6f);
            });
        });
    }

    private static Effect smokeEffect(float lifetime, float length, Color c1, Color c2) {
        return new Effect(lifetime, e -> {
            Draw.color(c1, c2, e.fin());
            Angles.randLenVectors(e.id, 4, length * e.fin(), e.rotation, 20f, (x, y) -> {
                Fill.circle(e.x + x, e.y + y, e.fout() * 4f);
            });
        });
    }

    private static Effect trailEffect(float lifetime, float size, Color c1, Color c2) {
        return new Effect(lifetime, e -> {
            Draw.color(c1, c2, e.fin());
            Fill.circle(e.x, e.y, size * e.fout());
        });
    }
}
