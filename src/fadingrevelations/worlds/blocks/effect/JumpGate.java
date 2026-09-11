package fadingrevelations.worlds.blocks.effect;

import arc.Core;
import arc.graphics.Color;
import arc.graphics.g2d.Draw;
import arc.graphics.g2d.TextureRegion;
import arc.math.Mathf;
import arc.util.Time;
import arc.util.io.Reads;
import arc.util.io.Writes;
import mindustry.entities.Effect;
import mindustry.entities.Units;
import mindustry.gen.Building;
import mindustry.gen.Sounds;
import mindustry.gen.Unit;
import mindustry.graphics.Drawf;
import mindustry.graphics.Pal;
import mindustry.Vars;
import mindustry.world.Block;
import mindustry.world.meta.Stat;
import mindustry.world.meta.StatUnit;

import fadingrevelations.content.FRFx;
import fadingrevelations.content.FRSettings;
import fadingrevelations.content.FRStatus;

/**
 * A pair-linked teleporter for units: units that enter the pickup radius of
 * a gate are instantly moved to the gate it is linked to.
 *
 * Simplified take on the New Horizon mod's jump gate concept (which is a full
 * recipe-based unit production & deployment system); this one only moves
 * existing units between two linked gates.
 */
public class JumpGate extends Block {
    public TextureRegion topRegion;

    /** Radius in which units are picked up, in world units. */
    public float radius = 56f;
    /** Ticks between teleports. */
    public float teleportInterval = 12f;
    /** Max distance from the destination gate at which units appear. */
    public float arrivalRadius = 36f;

    public Color portalColor = Color.valueOf("aa44ff");

    public Effect departEffect = FRFx.bezierRingRainbow;
    public Effect arriveEffect = FRFx.bezierRingCyan;

    public JumpGate(String name) {
        super(name);
        update = true;
        solid = true;
        hasPower = true;
        configurable = true;
        saveConfig = false;
        emitLight = true;
        lightRadius = 60f;
        lightColor = portalColor.cpy().a(0.6f);
        consumePower(3f);

        config(Integer.class, (JumpGateBuild gate, Integer value) -> gate.link = value == null ? -1 : value);
        configClear((JumpGateBuild gate) -> gate.link = -1);
    }

    @Override
    public void load() {
        super.load();
        //mods cannot use the @Load annotation, so regions are resolved manually
        topRegion = Core.atlas.find(name + "-top");
    }

    @Override
    public void setStats() {
        super.setStats();
        stats.add(Stat.range, radius / Vars.tilesize, StatUnit.blocks);
    }

    @Override
    public void drawPlace(int x, int y, int rotation, boolean valid) {
        super.drawPlace(x, y, rotation, valid);
        Drawf.dashCircle(x * Vars.tilesize + offset, y * Vars.tilesize + offset, radius, portalColor);
    }

    public static boolean linkValid(Building from, Building to) {
        return from != null && to != null
            && to != from
            && to.block instanceof JumpGate
            && to.team == from.team
            && to.isValid();
    }

    public class JumpGateBuild extends Building {
        public int link = -1;
        protected float teleTimer = 0f;
        protected float warmup = 0f;

        @Override
        public void updateTile() {
            warmup = Mathf.approachDelta(warmup, efficiency > 0 ? 1f : 0f, 0.05f);

            if (efficiency <= 0) return;

            teleTimer += Time.delta * timeScale;

            if (teleTimer >= teleportInterval) {
                teleTimer = 0f;

                Building dst = target();
                if (dst == null) return;

                Unit[] found = {null};
                Units.nearby(team, x, y, radius, unit -> {
                    if (found[0] != null) return;
                    if (unit.type == null || unit.type.naval) return; //naval units stay in the water
                    if (unit.hasEffect(FRStatus.jumpSickness)) return;
                    found[0] = unit;
                });

                Unit unit = found[0];
                if (unit != null) {
                    float ang = Mathf.random(360f);
                    float dstX = dst.x + Mathf.cosDeg(ang) * arrivalRadius * Mathf.random();
                    float dstY = dst.y + Mathf.sinDeg(ang) * arrivalRadius * Mathf.random();

                    departEffect.at(unit.x, unit.y);
                    arriveEffect.at(dstX, dstY);
                    Sounds.coreLaunch.at(unit.x, unit.y, 2f, 0.2f);
                    Sounds.padLaunch.at(dstX, dstY, 1.6f, 0.25f);

                    unit.set(dstX, dstY);
                    unit.snapInterpolation();
                    unit.apply(FRStatus.jumpSickness, 150f);
                }
            }
        }

        /** @return the linked destination gate, if valid. */
        public Building target() {
            if (link == -1) return null;
            Building b = Vars.world.build(link);
            return linkValid(this, b) ? b : null;
        }

        @Override
        public void draw() {
            super.draw();
            Draw.rect(topRegion, x, y, Time.time * 0.9f * warmup);
        }

        @Override
        public void drawLight() {
            super.drawLight();
            Drawf.light(x, y, lightRadius * warmup, portalColor, 0.7f * warmup);
        }

        @Override
        public void drawSelect() {
            Drawf.dashCircle(x, y, radius, portalColor);

            Building dst = target();
            if (dst != null) {
                Drawf.dashLine(Pal.accent, x, y, dst.x, dst.y);
                Drawf.dashCircle(dst.x, dst.y, radius, Pal.accent);
            } else {
                Drawf.text(FRSettings.bundle("fr.gate.link-hint", "Select another gate to link"),
                    x, y - size * Vars.tilesize / 2f - 6f, Pal.accent);
            }
        }

        @Override
        public boolean onConfigureBuildTapped(Building other) {
            if (other == self()) {
                deselect();
                configure(-1);
                return false;
            }
            if (linkValid(this, other)) {
                configure(other.pos());
                return false;
            }
            return true;
        }

        @Override
        public byte version() {
            return 1;
        }

        @Override
        public void write(Writes write) {
            super.write(write);
            write.i(link);
        }

        @Override
        public void read(Reads read, byte revision) {
            super.read(read, revision);
            if (revision >= 1) {
                link = read.i();
            } else {
                link = -1;
            }
        }
    }
}
