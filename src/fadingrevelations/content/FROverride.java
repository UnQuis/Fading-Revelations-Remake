package fadingrevelations.content;

import arc.struct.ObjectSet;
import arc.struct.Seq;
import mindustry.Vars;
import mindustry.content.Planets;
import mindustry.type.Planet;
import mindustry.type.Item;
import mindustry.type.Liquid;
import mindustry.world.Block;

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
}
