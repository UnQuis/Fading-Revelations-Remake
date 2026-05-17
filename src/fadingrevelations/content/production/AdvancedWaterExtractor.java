package fadingrevelations.content.production;
import fadingrevelations.content.FRGates;
import fadingrevelations.content.FRLiquids;
import fadingrevelations.content.FRItems;
import mindustry.content.*;
import mindustry.type.*;
import mindustry.world.blocks.production.SolidPump;
import mindustry.world.meta.Attribute;

import mindustry.world.Block;
public class AdvancedWaterExtractor {
    public static Block load() {
        return new SolidPump("advanced-water-extractor") {{
            localizedName = "Advanced Water Extractor";
            description = "Extracts large quantities of groundwater. Used in locations with no surface water available.";
            health = 320; result = Liquids.water; hasPower = true; attribute = Attribute.water;
            hasLiquids = true; size = 3; pumpAmount = 0.3f; liquidCapacity = 250;
            consumePower(3.5f);
            requirements(Category.production, ItemStack.with(Items.copper, 175, Items.lead, 140, Items.graphite, 150, Items.metaglass, 65));
        }};
    }
}
