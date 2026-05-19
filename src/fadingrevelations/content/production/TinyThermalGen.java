package fadingrevelations.content.production;
import fadingrevelations.content.FRGates;
import fadingrevelations.content.FRLiquids;
import fadingrevelations.content.FRItems;
import mindustry.content.*;
import mindustry.type.*;
import mindustry.world.blocks.power.ThermalGenerator;
import mindustry.world.meta.Attribute;

import mindustry.world.Block;
public class TinyThermalGen {
    public static Block block;
    public static Block load() {
        block = new ThermalGenerator("tiny-thermal-gen") {{
            localizedName = "Tiny Thermal Generator";
            description = "Produces power in hot locations.";
            attribute = Attribute.heat; powerProduction = 1.6f;
            requirements(Category.power, ItemStack.with(Items.copper, 10, Items.lead, 15, Items.metaglass, 10, Items.graphite, 8, Items.silicon, 8));
        }};
        return block;
    }
}
