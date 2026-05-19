package fadingrevelations.content.production;
import fadingrevelations.content.FRGates;
import fadingrevelations.content.FRLiquids;
import fadingrevelations.content.FRItems;
import mindustry.content.*;
import mindustry.type.*;
import mindustry.world.blocks.power.SolarGenerator;

import mindustry.world.Block;
import static fadingrevelations.content.FRGates.modGatePower;
public class AdvancedSolarPanel {
    public static Block block;
    public static Block load() {
        block = new SolarGenerator("advanced-solar-panel") {{
            localizedName = "Advanced Solar Panel";
            description = "A better solar panel that takes up the same amount of space as a regular one.";
            health = 280; size = 3; hasPower = true; hasLiquids = false; powerProduction = 2.5f;
            requirements(Category.power, ItemStack.with(Items.lead, 160, Items.silicon, 220, Items.phaseFabric, 40));
        }};
        return block;
    }
}
