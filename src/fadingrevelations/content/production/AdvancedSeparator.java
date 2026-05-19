package fadingrevelations.content.production;
import fadingrevelations.content.FRGates;
import fadingrevelations.content.FRLiquids;
import fadingrevelations.content.FRItems;
import mindustry.content.*;
import mindustry.type.*;
import mindustry.world.blocks.production.Separator;

import mindustry.world.Block;
public class AdvancedSeparator {
    public static Block block;
    public static Block load() {
        block = new Separator("advanced-separator") {{
            localizedName = "Advanced Separator";
            description = "Separates slag and scrap into their raw material components. High output.";
            size = 4; hasPower = true; solid = true; hasLiquids = true; hasItems = true;
            liquidCapacity = 60; craftTime = 10; health = 690;
            consumePower(5.4f); consumeLiquid(Liquids.slag, 0.1f); consumeItem(Items.scrap, 2);
            results = ItemStack.with(Items.copper, 10, Items.lead, 10, Items.graphite, 6, Items.titanium, 6, Items.thorium, 4);
            requirements(Category.crafting, ItemStack.with(Items.lead, 300, Items.graphite, 275, Items.titanium, 275, Items.plastanium, 30, Items.phaseFabric, 30, Items.surgeAlloy, 30));
        }};
        return block;
    }
}
