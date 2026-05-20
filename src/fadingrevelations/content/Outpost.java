package fadingrevelations.content;

import arc.graphics.*;
import arc.graphics.g2d.*;
import arc.math.*;
import arc.math.geom.*;
import arc.util.Time;
import arc.util.Tmp;
import mindustry.Vars;
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

    public Outpost(String name) {
        super(name);
        update = true;
        solid = true;
        hasItems = true;
        itemCapacity = 10000;
        buildType = OutpostBuild::new;
    }

    public class OutpostBuild extends Building {
        protected float warmup = 0f;
        protected float progress = 0f;

        @Override
        public boolean acceptItem(Building source, Item item) {
            Building c = core();
            return c != null && c.isValid() && c.acceptItem(this, item);
        }

        @Override
        public void handleItem(Building source, Item item) {
            Building c = core();
            if (c != null && c.isValid()) {
                c.handleItem(this, item);
            }
        }

        @Override
        public void updateTile() {
            Building c = core();
            boolean hasCore = c != null && c.isValid();

            if (hasCore) {
                warmup = Mathf.lerpDelta(warmup, 1f, 0.02f);
            } else {
                warmup = Mathf.lerpDelta(warmup, 0f, 0.04f);
            }

            progress += warmup * efficiency * Time.delta;
        }

        @Override
        public void draw() {
            super.draw();

            float time = Time.time;
            float bobbing = Mathf.sin(time * 0.05f) * 1.2f;
            float radius = 10f + Mathf.sin(time * 0.02f) * 0.4f;

            Draw.z(Layer.turret + 0.01f);
            Draw.blend(Blending.additive);

            Drawf.light(x, y + bobbing, radius * 3f, Pal.accent, 0.7f);

            Draw.color(Pal.accent);
            Draw.alpha(0.15f);
            Fill.circle(x, y + bobbing, radius + 2f);

            Draw.color(Pal.accent);
            Draw.alpha(0.8f);
            Lines.stroke(1.8f);
            Lines.circle(x, y + bobbing, radius);

            for (int i = 0; i < 3; i++) {
                float orbAngle = time * 1.5f + i * 120f;
                float px = x + Angles.trnsx(orbAngle, radius);
                float py = y + bobbing + Angles.trnsy(orbAngle, radius);

                Draw.color(Color.white);
                Draw.alpha(0.9f);
                Fill.circle(px, py, 2f);

                Draw.color(Pal.accent);
                Draw.alpha(0.4f);
                Fill.circle(px, py, 4f);
            }

            for (int i = 0; i < 2; i++) {
                float orbAngle = -time * 2f + i * 180f;
                float px = x + Angles.trnsx(orbAngle, radius - 3f);
                float py = y + bobbing + Angles.trnsy(orbAngle, radius - 3f);

                Draw.color(Pal.accent, Color.white, 0.3f);
                Draw.alpha(0.7f);
                Fill.circle(px, py, 1.3f);
            }

            Draw.blend();
            Draw.reset();

            if (warmup <= 0.01f) return;

            Building c = core();
            if (c == null) return;

            float laserOpacity = arc.Core.settings.getInt("laseropacity", 100) / 100f;

            Draw.z(Layer.effect - 1f);
            Draw.blend(Blending.additive);

            float angle = angleTo(c);
            float pulse = 1f + Mathf.absin(Time.time, 4f, 0.3f);

            Draw.color(team.color);
            Draw.alpha(warmup * 0.4f * laserOpacity);
            Lines.stroke(4f * pulse * warmup);
            Lines.line(x, y, c.x, c.y);

            Draw.color(Color.white);
            Draw.alpha(warmup * 0.8f * laserOpacity);
            Lines.stroke(1.2f * warmup);
            Lines.line(x, y, c.x, c.y);

            Draw.color(team.color, Color.white, 0.3f);
            for (int i = 0; i < 4; i++) {
                float f = (progress * 0.8f + i * 25f) % 100f / 100f;
                float dst = dst(c);

                Tmp.v1.trns(angle, f * dst);
                float px = x + Tmp.v1.x;
                float py = y + Tmp.v1.y;

                float alphaEdge = Mathf.sin(f * Mathf.PI) * warmup * laserOpacity;
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
