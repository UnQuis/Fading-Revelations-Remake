package fadingrevelations.content.production;
import fadingrevelations.content.FRGates;
import fadingrevelations.content.FRLiquids;
import fadingrevelations.content.FRItems;
import mindustry.content.*;
import mindustry.type.*;
import mindustry.world.blocks.power.SolarGenerator;

import mindustry.world.Block;
public class TitaniumPanel {
    public static Block block;
    public static Block load() {
        block = new SolarGenerator("titanium-panel") {{
            localizedName = "Titanium Solar Panel";
            description = "A better solar panel made with titanium. Produces more power than a regular solar panel.";
            health = 90; size = 2; hasPower = true; hasLiquids = false; powerProduction = 0.4f;
            requirements(Category.power, ItemStack.with(Items.lead, 20, Items.silicon, 20, Items.titanium, 10));
        }};
        return block;
    }
}
