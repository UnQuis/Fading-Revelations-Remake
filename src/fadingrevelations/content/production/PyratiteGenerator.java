package fadingrevelations.content.production;
import fadingrevelations.content.FRGates;
import fadingrevelations.content.FRLiquids;
import fadingrevelations.content.FRItems;
import mindustry.content.*;
import mindustry.type.*;
import mindustry.world.blocks.power.ConsumeGenerator;

import mindustry.world.Block;
public class PyratiteGenerator {
    public static Block load() {
        return new ConsumeGenerator("pyratite-generator") {{
            localizedName = "Pyratite Generator";
            description = "Uses the high flammability of pyratite to make a decent amount of power.";
            size = 2; hasPower = true; hasItems = true; itemDuration = 60; powerProduction = 12;
            consumeItem(Items.pyratite, 1);
            requirements(Category.power, ItemStack.with(Items.copper, 110, Items.lead, 90, Items.graphite, 50));
        }};
    }
}
