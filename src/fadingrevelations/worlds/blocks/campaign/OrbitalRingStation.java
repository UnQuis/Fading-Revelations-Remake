package fadingrevelations.worlds.blocks.campaign;

import arc.Core;
import arc.graphics.Color;
import arc.graphics.g2d.Draw;
import arc.graphics.g2d.Fill;
import arc.graphics.g2d.TextureRegion;
import arc.math.Mathf;
import arc.util.Strings;
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
import mindustry.type.ItemStack;
import mindustry.ui.Bar;
import mindustry.world.Block;
import mindustry.world.Tile;
import mindustry.world.meta.Env;
import mindustry.world.meta.Stat;
import mindustry.world.meta.StatCat;
import mindustry.world.meta.StatValue;

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

    /**
     * Bundle key is {@code stat.fr-ring-project}; Stat.localized() looks the name up unprefixed,
     * so unlike block/item names this key must NOT carry the mod prefix.
     */
    private static final Stat ringProjectStat = new Stat("fr-ring-project", StatCat.function);

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
            () -> FRSettings.bundle("fr.ring.progress", "Ring") + " " + FROrbitalRing.stage + "/" + FROrbitalRing.STAGES + ": "
                + (int)(FROrbitalRing.stageProgressFraction() * 100f) + "%",
            () -> Pal.accent,
            () -> FROrbitalRing.stage >= FROrbitalRing.STAGES ? 1f : FROrbitalRing.stageProgressFraction()
        ));
    }

    @Override
    public void setStats() {
        super.setStats();

        // The station refuses anything the current stage does not need, and it has no cost to
        // guess wrong, so without this the player has to look the requirements up somewhere. The
        // stage (and with it the item list) changes as the project is built, so the value is
        // rendered live instead of being baked in here.
        stats.add(ringProjectStat, (StatValue)table -> {
            table.left().top();
            table.table(inner -> {
                inner.left().top();

                int stage = FROrbitalRing.stage;
                if(stage >= FROrbitalRing.STAGES){
                    inner.add(FRSettings.bundle("fr.ring.complete", "The Orbital Ring is complete"))
                        .padLeft(6f).color(Pal.accent).row();
                }else{
                    inner.add(FRSettings.bundle("fr.ring.stat.stage", "Stage") + " " + (stage + 1)
                        + "/" + FROrbitalRing.STAGES + " [accent]"
                        + (int)(FROrbitalRing.stageProgressFraction() * 100f) + "%[]")
                        .padLeft(6f).padBottom(2f).row();

                    for(ItemStack stack : FROrbitalRing.currentRequirements()){
                        int left = FROrbitalRing.remaining(stack.item);
                        inner.image(stack.item.uiIcon).size(16f).padLeft(6f).padRight(4f);
                        inner.add(stack.item.localizedName + " ").padRight(2f);
                        inner.add((stack.amount - left) + "/" + stack.amount)
                            .color(left > 0 ? Color.lightGray : Pal.accent).row();
                    }
                }

                long delivered = FROrbitalRing.totalDelivered();
                inner.add("[lightgray]" + FRSettings.bundle("fr.ring.stat.total", "Materials delivered") + ":[] [lightgray]"
                    + (delivered >= 1000 ? Strings.fixed(delivered / 1000f, 1) + "k" : String.valueOf(delivered)))
                    .padLeft(6f).padTop(4f).row();
            });
        });
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

                int accepted = FROrbitalRing.contribute(item, count, x, y);
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

        /** "+15%", "+20%", ... from a multiplier, so the UI cannot drift from the real bonus. */
        private String pct(float multiplier){
            return "+" + (int)Mathf.round((multiplier - 1f) * 100f) + "%";
        }

        @Override
        public void drawSelect() {
            super.drawSelect();

            float offY = size * Vars.tilesize / 2f + 8f;

            if (FROrbitalRing.stage >= FROrbitalRing.STAGES) {
                Drawf.text(FRSettings.bundle("fr.ring.complete", "The Orbital Ring is complete"), x, y + offY + 14f, Pal.accent);
                // percentages come from the same constants applyBonuses() uses
                float baseY = y + offY;
                Drawf.text(FRSettings.bundle("fr.ring.boost-mining", "Mining Speed") + ": " + pct(FROrbitalRing.miningBonus), x, baseY, Color.valueOf("84f491"));
                Drawf.text(FRSettings.bundle("fr.ring.boost-build", "Build Speed") + ": " + pct(FROrbitalRing.buildBonus), x, baseY - 14f, Color.valueOf("84f491"));
                Drawf.text(FRSettings.bundle("fr.ring.boost-health", "Unit & Block HP") + ": " + pct(FROrbitalRing.healthBonus), x, baseY - 28f, Color.valueOf("84f491"));
                Drawf.text(FRSettings.bundle("fr.ring.boost-damage", "Unit Damage") + ": " + pct(FROrbitalRing.damageBonus), x, baseY - 42f, Color.valueOf("84f491"));
                Drawf.text(FRSettings.bundle("fr.ring.boost-overdrive", "Overdrive") + ": +" + (int)((FROrbitalRing.BOOST_MULTIPLIER - 1f) * 100f) + "% " + FRSettings.bundle("fr.ring.planet-wide", "(planet-wide)"), x, baseY - 56f, Color.valueOf("ffd37f"));
            } else {
                // the station silently rejects anything the current stage does not need, so list
                // exactly what it wants and how much is still outstanding
                ItemStack[] req = FROrbitalRing.currentRequirements();
                float baseY = y + offY;
                Drawf.text(FRSettings.bundle("fr.ring.stat.stage", "Stage") + " " + (FROrbitalRing.stage + 1)
                    + "/" + FROrbitalRing.STAGES + ": "
                    + (int)(FROrbitalRing.stageProgressFraction() * 100f) + "%",
                    x, baseY, Pal.accent);

                if (req != null) {
                    int line = 1;
                    for (ItemStack stack : req) {
                        int left = FROrbitalRing.remaining(stack.item);
                        float f = stack.amount <= 0 ? 0f : (stack.amount - left) / (float)stack.amount;
                        float ly = baseY - line * 14f;
                        // item icon + name + "have / need", green once satisfied
                        Draw.color(left > 0 ? Color.white : Color.valueOf("84f491"));
                        Draw.rect(stack.item.uiIcon, x - 84f, ly, 10f, 10f);
                        Draw.color(left > 0 ? Pal.accent : Color.valueOf("84f491"));
                        Drawf.text(stack.item.localizedName + "  " + (stack.amount - left) + "/" + stack.amount,
                            x - 74f, ly, left > 0 ? Pal.accent : Color.valueOf("84f491"));
                        Draw.reset();
                        // progress underline
                        Draw.color(Pal.accent, 0.25f);
                        Fill.crect(x - 84f, ly - 7f, 170f, 2f);
                        Draw.color(Pal.accent);
                        Fill.crect(x - 84f, ly - 7f, 170f * Mathf.clamp(f), 2f);
                        Draw.reset();
                        line++;
                    }
                }

                // partial bonuses for completed stages
                int s = FROrbitalRing.stage;
                int line = req == null ? 1 : 1 + req.length;
                if (s >= 1) Drawf.text(FRSettings.bundle("fr.ring.boost-mining", "Mining Speed") + ": " + pct(FROrbitalRing.miningBonus), x, baseY - line++ * 14f, Color.valueOf("84f491"));
                if (s >= 2) Drawf.text(FRSettings.bundle("fr.ring.boost-build", "Build Speed") + ": " + pct(FROrbitalRing.buildBonus), x, baseY - line++ * 14f, Color.valueOf("84f491"));
                if (s >= 3) Drawf.text(FRSettings.bundle("fr.ring.boost-health", "Unit & Block HP") + ": " + pct(FROrbitalRing.healthBonus), x, baseY - line++ * 14f, Color.valueOf("84f491"));
                if (s >= 4) {
                    Drawf.text(FRSettings.bundle("fr.ring.boost-damage", "Unit Damage") + ": " + pct(FROrbitalRing.damageBonus), x, baseY - line++ * 14f, Color.valueOf("84f491"));
                    Drawf.text(FRSettings.bundle("fr.ring.boost-overdrive", "Overdrive") + ": +" + (int)((FROrbitalRing.BOOST_MULTIPLIER - 1f) * 100f) + "% " + FRSettings.bundle("fr.ring.planet-wide", "(planet-wide)"), x, baseY - line * 14f, Color.valueOf("ffd37f"));
                }
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
