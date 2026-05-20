package fadingrevelations.content;

import arc.Events;
import arc.struct.ObjectSet;
import arc.struct.Seq;
import mindustry.Vars;
import mindustry.content.Planets;
import mindustry.game.EventType;
import mindustry.game.Team;
import mindustry.type.Planet;
import mindustry.type.Item;
import mindustry.type.Liquid;
import mindustry.world.Block;
import mindustry.world.blocks.storage.CoreBlock;

public class FROverride {
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

            var enemyRules = Vars.state.rules.teams.get(Team.crux);
            enemyRules.unitHealthMultiplier = 5f;
            enemyRules.buildSpeedMultiplier = 3f;
            enemyRules.unitBuildSpeedMultiplier = 3f;
        });
    }

    public static void noCoreBurn() {
        Events.on(EventType.WorldLoadEvent.class, event -> {
            if (!FRSettings.noCoreBurnEffect) return;
            Vars.state.rules.coreIncinerates = true;

            for (CoreBlock.CoreBuild core : Vars.state.teams.playerCores()) {
                core.storageCapacity = Integer.MAX_VALUE;
            }
        });

        Events.run(EventType.Trigger.update, () -> {
            if (!FRSettings.noCoreBurnEffect) return;
            if (Vars.state == null || Vars.state.isMenu()) return;

            for (CoreBlock.CoreBuild core : Vars.state.teams.playerCores()) {
                core.storageCapacity = Integer.MAX_VALUE;
            }
        });
    }
}
