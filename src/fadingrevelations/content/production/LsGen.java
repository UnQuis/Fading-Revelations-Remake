package fadingrevelations.content.production;
import fadingrevelations.content.FRGates;
import fadingrevelations.content.FRLiquids;
import fadingrevelations.content.FRItems;
import mindustry.content.*;
import mindustry.type.*;
import mindustry.world.blocks.power.ThermalGenerator;
import mindustry.world.meta.Attribute;

import mindustry.world.Block;
public class LsGen {
    public static Block block;
    public static Block load() {
        block = new ThermalGenerator("ls-gen") {{
            localizedName = "Living Steel Thermal Generator";
            description = "Produces heat in hot locations using intelligent spore steel activity. Is more efficient than the normal thermal generator.";
            attribute = Attribute.heat; powerProduction = 3f; size = 2; health = 240;
            requirements(Category.power, ItemStack.with(Items.copper, 50, Items.lead, 60, Items.metaglass, 50, Items.graphite, 45, FRItems.livingSteel, 35));
        }};
        return block;
    }
}
