package fadingrevelations.content;

import arc.graphics.*;
import arc.graphics.g2d.*;
import arc.math.*;
import arc.math.geom.*;
import arc.util.Time;
import arc.util.Tmp;
import mindustry.Vars;
import mindustry.entities.Effect;
import mindustry.gen.Building;
import mindustry.graphics.Drawf;
import mindustry.graphics.Layer;
import mindustry.graphics.Pal;
import mindustry.type.Item;
import mindustry.world.Block;

import static mindustry.Vars.tilesize;

public class Outpost extends Block {
    private static final Vec2 v1 = new Vec2();
    private static final Vec2 v2 = new Vec2();
    private static final Vec2 v3 = new Vec2();
    private static final Vec2 v4 = new Vec2();

    public static final Effect haloGold = new Effect(45f, e -> {
        float p = Interp.pow2Out.apply(e.fin());
        float alpha = Interp.fade.apply(e.fin());
        float baseRot = e.rotation + e.fin() * 60f;

        float timeOffset = (e.id * 33.3f) + e.time;
        float bobbing = 6f + Mathf.sin(timeOffset * 0.06f) * 2.5f;

        float radius = 12f + Mathf.sin(timeOffset * 0.03f) * 0.7f;
        float thick = 2.5f * alpha;

        Draw.z(Layer.turret + 0.01f);
        Draw.blend(Blending.additive);

        Drawf.light(e.x, e.y + bobbing, radius * 3f, Pal.accent, 0.8f * alpha);

        Draw.color(Pal.accent);
        Draw.alpha(alpha * 0.2f);
        Fill.circle(e.x, e.y + bobbing, radius + 2f);

        for(int i = 0; i < 4; i++){
            float a = baseRot + i * 90f;
            v1.trns(a, radius).add(e.x, e.y + bobbing);
            v2.trns(a + 30f, radius * 1.3f).add(e.x, e.y + bobbing);
            v3.trns(a + 60f, radius * 1.3f).add(e.x, e.y + bobbing);
            v4.trns(a + 90f, radius).add(e.x, e.y + bobbing);

            Draw.color(Pal.accent, Color.white, e.fin() * 0.4f);
            Draw.alpha(alpha);

            int steps = 16;
            float lastX = v1.x, lastY = v1.y;
            for(int j = 1; j <= steps; j++){
                float t = (float)j / steps;
                float u = 1f - t;
                float bx = u*u*u*v1.x + 3f*u*u*t*v2.x + 3f*u*t*t*v3.x + t*t*t*v4.x;
                float by = u*u*u*v1.y + 3f*u*u*t*v2.y + 3f*u*t*t*v3.y + t*t*t*v4.y;

                Lines.stroke(thick * (1f - Mathf.sin(t * Mathf.PI) * 0.3f));
                Lines.line(lastX, lastY, bx, by);
                lastX = bx;
                lastY = by;
            }
        }

        for(int i = 0; i < 4; i++){
            float floatAngle = baseRot * 1.2f + Mathf.randomSeed(e.id + i, 0f, 360f);
            float floatRad = radius * Mathf.randomSeed(e.id + i * 5, 0.3f, 1.1f);

            float px = e.x + Angles.trnsx(floatAngle, floatRad);
            float py = e.y + bobbing + Angles.trnsy(floatAngle, floatRad * 0.4f) + p * 8f;

            Draw.color(Color.white, Pal.accent, p);
            Draw.alpha(alpha * 0.9f);
            Fill.circle(px, py, 1.5f * (1f - p));
        }

        Draw.blend();
    });

    public Outpost(String name) {
        super(name);
        update = true;
        solid = true;
        hasItems = true;
        itemCapacity = 10000;
        buildType = OutpostBuild::new;
    }

    public class OutpostBuild extends Building {
        protected float effectTime = 0f;
        protected float warmup = 0f;
        protected float progress = 0f;

        @Override
        public void updateTile() {
            effectTime += edelta();
            if(effectTime >= 20f){
                haloGold.at(x, y, effectTime);
                effectTime = Mathf.random(5f);
            }

            Building c = core();
            boolean hasCore = c != null && c.isValid();

            if (hasCore && items.total() > 0) {
                warmup = Mathf.lerpDelta(warmup, 1f, 0.02f);
            } else {
                warmup = Mathf.lerpDelta(warmup, 0f, 0.04f);
            }

            progress += warmup * efficiency * Time.delta;

            if (hasCore && warmup >= 0.99f && items.total() > 0) {
                for (int i = 0; i < Vars.content.items().size; i++) {
                    Item item = Vars.content.item(i);
                    int amount = items.get(item);
                    if (amount > 0) {
                        items.remove(item, amount);
                        c.items.add(item, amount);
                    }
                }
            }
        }

        @Override
        public void draw() {
            super.draw();
            if (warmup <= 0.01f) return;

            Building c = core();
            if (c == null) return;

            Draw.z(Layer.effect - 1f);
            Draw.blend(Blending.additive);

            float angle = angleTo(c);
            float pulse = 1f + Mathf.absin(Time.time, 4f, 0.3f);

            Draw.color(team.color);
            Draw.alpha(warmup * 0.4f);
            Lines.stroke(4f * pulse * warmup);
            Lines.line(x, y, c.x, c.y);

            Draw.color(Color.white);
            Draw.alpha(warmup * 0.8f);
            Lines.stroke(1.2f * warmup);
            Lines.line(x, y, c.x, c.y);

            Draw.color(team.color, Color.white, 0.3f);
            for (int i = 0; i < 4; i++) {
                float f = (progress * 0.8f + i * 25f) % 100f / 100f;
                float dst = dst(c);

                Tmp.v1.trns(angle, f * dst);
                float px = x + Tmp.v1.x;
                float py = y + Tmp.v1.y;

                float alphaEdge = Mathf.sin(f * Mathf.PI) * warmup;
                Draw.alpha(alphaEdge);

                float sizeMod = (1f - f * 0.3f) * 6f;
                Fill.square(px, py, sizeMod, angle + 45f);

                Draw.color(Color.white);
                Draw.alpha(alphaEdge * 0.7f);
                Fill.square(px, py, sizeMod * 0.5f, angle + 45f);
                Draw.color(team.color, Color.white, 0.3f);
            }

            Draw.blend();
            Draw.reset();
        }

        @Override
        public void drawConfigure() {
            Building c = core();
            if (c == null) return;
            float ox = x, oy = y, cx = c.x, cy = c.y;

            Draw.color(team.color);
            Lines.stroke(1.5f);
            Lines.dashLine(ox, oy, cx, cy, 8);

            Draw.blend(Blending.additive);
            Fill.square(ox, oy, 4f, Time.time * 2f);
            Fill.square(cx, cy, 4f, -Time.time * 2f);
            Draw.blend();
            Draw.reset();
        }

        @Override
        public boolean canPickup() {
            return false;
        }
    }
}
