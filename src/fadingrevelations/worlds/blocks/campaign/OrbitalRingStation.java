package fadingrevelations.worlds.blocks.campaign;

import arc.Core;
import arc.graphics.Color;
import arc.graphics.g2d.Draw;
import arc.graphics.g2d.TextureRegion;
import arc.math.Mathf;
import arc.util.Time;
import arc.util.io.Reads;
import arc.util.io.Writes;
import mindustry.Vars;
import mindustry.content.Fx;
import mindustry.entities.Effect;
import mindustry.gen.Building;
import mindustry.graphics.Drawf;
import mindustry.graphics.Pal;
import mindustry.type.Item;
import mindustry.ui.Bar;
import mindustry.world.Block;
import mindustry.world.Tile;
import mindustry.world.meta.Env;

import fadingrevelations.content.FROrbitalRing;
import fadingrevelations.content.FRPlanets;
import fadingrevelations.content.FRSettings;

/**
 * Campaign block for the Cangirus Orbital Ring project.
 *
 * Only placeable on Cangirus sectors (campaign). Accepts exactly the items
 * required by the current project stage and feeds them into the shared,
 * persistent project progress. When a stage completes, the next quadrant of
 * the ring appears around the planet and a permanent campaign bonus is
 * granted (see FROrbitalRing).
 */
public class OrbitalRingStation extends Block {
    public TextureRegion topRegion;

    public Effect contributeEffect = Fx.smeltsmoke;
    public Color beaconColor = Color.valueOf("6fb7ff");

    public OrbitalRingStation(String name) {
        super(name);
        update = true;
        solid = true;
        hasItems = true;
        itemCapacity = 80;
        configurable = false;
        emitLight = true;
        lightRadius = 40f;
        lightColor = beaconColor.cpy().a(0.5f);
        envEnabled |= Env.space;
    }

    @Override
    public void load() {
        super.load();
        //mods cannot use the @Load annotation, so regions are resolved manually
        topRegion = Core.atlas.find(name + "-top");
    }

    @Override
    public void setBars() {
        super.setBars();
        addBar("fr-ring-progress", (OrbitalRingStationBuild build) -> new Bar(
            () -> FRSettings.bundle("fr.ring.progress", "Ring") + " " + FROrbitalRing.stage + "/4: "
                + (int)(FROrbitalRing.stageProgressFraction() * 100f) + "%",
            () -> Pal.accent,
            () -> FROrbitalRing.stage >= FROrbitalRing.STAGES ? 1f : FROrbitalRing.stageProgressFraction()
        ));
    }

    @Override
    public boolean canPlaceOn(Tile tile, mindustry.game.Team team, int rotation) {
        return placeableHere();
    }

    @Override
    public void drawPlace(int x, int y, int rotation, boolean valid) {
        super.drawPlace(x, y, rotation, valid);
        if (!valid && !placeableHere()) {
            drawPlaceText(FRSettings.bundle("fr.ring.wrong-planet", "Only on Cangirus"), x, y, false);
        }
    }

    private boolean placeableHere() {
        if (!Vars.state.isCampaign()) return true;
        return Vars.state.rules.sector != null && Vars.state.rules.sector.planet == FRPlanets.cangirus;
    }

    public class OrbitalRingStationBuild extends Building {
        protected float warmup = 0f;
        protected float contributionPulse = 0f;

        @Override
        public void updateTile() {
            boolean active = FROrbitalRing.stage < FROrbitalRing.STAGES;

            warmup = Mathf.approachDelta(warmup, active && items.total() > 0 ? 1f : 0f, 0.04f);
            contributionPulse = Mathf.approachDelta(contributionPulse, 0f, 0.02f);

            if (!active) return;

            //push every stored item into the project
            boolean any = false;
            for (Item item : Vars.content.items()) {
                int count = items.get(item);
                if (count <= 0 || !FROrbitalRing.accepts(item)) continue;

                int accepted = FROrbitalRing.contribute(item, count);
                if (accepted > 0) {
                    items.remove(item, accepted);
                    any = true;
                }
            }

            if (any) {
                contributionPulse = 1f;
                if (Mathf.chanceDelta(0.25f)) {
                    contributeEffect.at(x + Mathf.range(size * 4f), y + Mathf.range(size * 4f));
                }
            }
        }

        @Override
        public boolean acceptItem(Building source, Item item) {
            return items.get(item) < getMaximumAccepted(item)
                && FROrbitalRing.accepts(item);
        }

        @Override
        public void draw() {
            super.draw();
            Draw.rect(topRegion, x, y, Time.time * 1.2f * warmup);
        }

        @Override
        public void drawLight() {
            super.drawLight();
            Drawf.light(x, y, (lightRadius + contributionPulse * 40f) * warmup, beaconColor, (0.5f + contributionPulse * 0.4f) * warmup);
        }

        @Override
        public void drawSelect() {
            super.drawSelect();
            if (FROrbitalRing.stage >= FROrbitalRing.STAGES) {
                Drawf.text(FRSettings.bundle("fr.ring.complete", "The Orbital Ring is complete"), x, y - size * Vars.tilesize / 2f - 6f, Pal.accent);
            }
        }

        @Override
        public byte version() {
            return 1;
        }

        @Override
        public void write(Writes write) {
            super.write(write);
            write.f(warmup);
        }

        @Override
        public void read(Reads read, byte revision) {
            super.read(read, revision);
            if (revision >= 1) {
                warmup = read.f();
            }
        }
    }
}
