package fadingrevelations.content;

import arc.graphics.*;
import arc.graphics.g2d.*;
import arc.math.*;
import arc.math.geom.*;
import arc.util.Time;
import arc.util.Tmp;
import mindustry.gen.Building;
import mindustry.graphics.Drawf;
import mindustry.graphics.Layer;
import mindustry.type.Item;
import mindustry.world.Block;

public class Outpost extends Block {
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
            float bobbing = Mathf.sin(time * 0.05f) * 1.5f;
            float rx = x;
            float ry = y + 4f + bobbing;

            Draw.z(Layer.turret + 0.01f);
            Draw.blend(Blending.additive);

            float glowRadius = 12f + Mathf.absin(time, 4f, 2f);
            Drawf.light(rx, ry, glowRadius * 2.5f, team.color, 0.7f);

            Draw.color(team.color, Color.white, 0.3f);
            Draw.alpha(0.8f);

            for (int i = 0; i < 4; i++) {
                Drawf.tri(rx, ry, 3.5f, 9f + Mathf.absin(time, 5f, 2f), i * 90f + time * 1.5f);
            }

            Lines.stroke(1.5f);
            for (int i = 0; i < 4; i++) {
                Lines.arc(rx, ry, 8f, 0.15f, i * 90f - time * 2f);
            }

            for (int i = 0; i < 3; i++) {
                float a = time * 3f + i * 120f;
                float px = rx + Angles.trnsx(a, 12f);
                float py = ry + Angles.trnsy(a, 12f);

                Fill.square(px, py, 2f, a + 45f);
            }

            Draw.blend();
            Draw.reset();

            if (warmup <= 0.01f) return;

            Building c = core();
            if (c == null) return;

            float laserOpacity = arc.Core.settings.getInt("lasersopacity", 100) / 100f;
            if (laserOpacity <= 0.01f) return;

            Draw.z(Layer.power - 1f);
            Draw.blend(Blending.additive);

            float angle = angleTo(c);
            float dst = dst(c);

            float pulse = 0.7f + Mathf.absin(time, 3f, 0.3f);
            Draw.color(team.color);
            Draw.alpha(warmup * 0.5f * laserOpacity * pulse);
            Lines.stroke(3.5f * warmup);
            Lines.line(x, y, c.x, c.y);

            Draw.color(Color.white);
            Draw.alpha(warmup * 0.8f * laserOpacity * pulse);
            Lines.stroke(1.2f * warmup);
            Lines.line(x, y, c.x, c.y);

            Draw.color(team.color, Color.white, 0.4f);
            for (int i = 0; i < 5; i++) {
                float f = (progress * 1.5f + i * 20f) % 100f / 100f;
                float alphaEdge = Mathf.sin(f * Mathf.PI) * warmup * laserOpacity;

                Tmp.v1.trns(angle, f * dst);
                float px = x + Tmp.v1.x;
                float py = y + Tmp.v1.y;

                Draw.alpha(alphaEdge);
                Drawf.tri(px, py, 5f * warmup, 9f * warmup, angle);
                Drawf.tri(px, py, 5f * warmup, 3f * warmup, angle + 180f);
            }

            Draw.blend();
            Draw.reset();
        }

        @Override
        public void drawConfigure() {
            Building c = core();
            if (c == null) return;

            Draw.color(team.color);
            Lines.stroke(1.5f);
            Lines.dashLine(x, y, c.x, c.y, 8);

            Draw.blend(Blending.additive);
            Fill.square(x, y, 4f, Time.time * 2f);
            Fill.square(c.x, c.y, 4f, -Time.time * 2f);
            Draw.blend();
            Draw.reset();
        }

        @Override
        public boolean canPickup() {
            return false;
        }
    }
}
