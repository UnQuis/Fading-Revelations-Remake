package fadingrevelations.content;

import arc.Events;
import arc.func.Cons;
import arc.struct.ObjectMap;
import arc.struct.ObjectSet;
import arc.struct.Seq;
import mindustry.Vars;
import mindustry.content.Fx;
import mindustry.content.Planets;
import mindustry.ctype.UnlockableContent;
import mindustry.entities.Effect;
import mindustry.game.EventType;
import mindustry.game.Team;
import mindustry.mod.Mods;
import mindustry.type.Planet;
import mindustry.type.Item;
import mindustry.type.Liquid;
import mindustry.type.UnitType;
import mindustry.world.Block;
import fadingrevelations.FadingRevelationsMod;

public class FROverride {
    /**
     * Sandbox maps use Serpulo as their default rules planet. Mod content that is restricted to
     * the campaign planets would then fail environmentBuildable() and disappear from the build
     * menu entirely, even though it is unlocked. Clear the restriction while playing outside the
     * campaign and restore it when a campaign sector loads.
     */
    private static final ObjectMap<UnlockableContent, ObjectSet<Planet>> originalShownPlanets = new ObjectMap<>();

    public static void sandboxContent() {
        Events.on(EventType.WorldLoadEvent.class, event -> {
            if (Vars.state == null) return;

            boolean sandbox = !Vars.state.isCampaign();
            Mods.LoadedMod mod = Vars.mods.getMod(FadingRevelationsMod.class);
            if (mod == null) return;

            for (Block block : Vars.content.blocks()) applySandbox(block, mod, sandbox);
            for (Item item : Vars.content.items()) applySandbox(item, mod, sandbox);
            for (Liquid liquid : Vars.content.liquids()) applySandbox(liquid, mod, sandbox);
            for (UnitType unit : Vars.content.units()) applySandbox(unit, mod, sandbox);
        });
    }

    private static void applySandbox(UnlockableContent content, Mods.LoadedMod mod, boolean sandbox) {
        if (content.minfo == null || content.minfo.mod != mod) return;

        if (sandbox) {
            if (!originalShownPlanets.containsKey(content)) {
                ObjectSet<Planet> original = new ObjectSet<>();
                original.addAll(content.shownPlanets);
                originalShownPlanets.put(content, original);
            }
            content.shownPlanets.clear();
        } else if (originalShownPlanets.containsKey(content)) {
            content.shownPlanets = originalShownPlanets.remove(content);
        }
    }

    public static void mixTech() {
        Seq<Planet> allPlanetsSeq = Vars.content.planets().copy().removeAll(p -> p == Planets.sun);

        for (Block block : Vars.content.blocks()) {
            if (!block.shownPlanets.isEmpty()) {
                ObjectSet<Planet> planets = new ObjectSet<>();
                planets.addAll(allPlanetsSeq);
                block.shownPlanets = planets;
                block.postInit();
            }
        }

        for (Item item : Vars.content.items()) {
            if (!item.shownPlanets.isEmpty()) {
                ObjectSet<Planet> planets = new ObjectSet<>();
                planets.addAll(allPlanetsSeq);
                item.shownPlanets = planets;
                item.postInit();
            }
        }

        for (Liquid liquid : Vars.content.liquids()) {
            if (!liquid.shownPlanets.isEmpty()) {
                ObjectSet<Planet> planets = new ObjectSet<>();
                planets.addAll(allPlanetsSeq);
                liquid.shownPlanets = planets;
                liquid.postInit();
            }
        }
    }

    public static void hardcore() {
        Events.on(EventType.WorldLoadEvent.class, event -> {
            if (!FRSettings.hardcore) return;
            // Only affect campaign gameplay, not sandbox/editor/PvP.
            if (!Vars.state.isCampaign()) return;

            mindustry.game.Rules.TeamRule enemyRules = Vars.state.rules.teams.get(Team.crux);
            enemyRules.unitHealthMultiplier = 5f;
            enemyRules.buildSpeedMultiplier = 3f;
            enemyRules.unitBuildSpeedMultiplier = 3f;
        });
    }

    public static void noCoreBurn() {
        Cons<Effect.EffectContainer> orig = Fx.coreBurn.renderer;
        Fx.coreBurn.renderer = e -> {
            if (!FRSettings.noCoreBurnEffect) {
                orig.get(e);
            }
        };
    }
}
