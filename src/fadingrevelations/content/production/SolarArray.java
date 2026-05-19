package fadingrevelations.content.production;
import fadingrevelations.content.FRGates;
import fadingrevelations.content.FRLiquids;
import fadingrevelations.content.FRItems;
import mindustry.content.*;
import mindustry.type.*;
import mindustry.world.blocks.power.SolarGenerator;

import mindustry.world.Block;
public class SolarArray {
    public static Block block;
    public static Block load() {
        block = new SolarGenerator("solar-array") {{
            localizedName = "Solar Array";
            description = "A solar panel with a large surface area, enabling a high energy output.";
            health = 620; size = 6; hasPower = true; hasLiquids = false; powerProduction = 25;
            requirements(Category.power, ItemStack.with(Items.lead, 320, Items.silicon, 440, Items.phaseFabric, 80));
        }};
        return block;
    }
}
