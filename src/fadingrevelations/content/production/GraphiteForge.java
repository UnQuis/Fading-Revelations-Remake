package fadingrevelations.content.production;
import fadingrevelations.content.FRGates;
import fadingrevelations.content.FRLiquids;
import fadingrevelations.content.FRItems;
import mindustry.content.*;
import mindustry.type.*;
import mindustry.world.blocks.production.GenericCrafter;

import mindustry.world.Block;
public class GraphiteForge {
    public static Block block;
    public static Block load() {
        block = new GenericCrafter("graphite-forge") {{
            localizedName = "Graphite Forge";
            description = "A better graphite press that makes Graphite way faster and more efficiently.";
            size = 4; itemCapacity = 40; hasItems = true; hasLiquids = true; hasPower = true;
            craftTime = 30; craftEffect = Fx.blastExplosion;
            outputItem = new ItemStack(Items.graphite, 4);
            consumePower(2.5f); consumeItem(Items.coal, 4); consumeLiquid(Liquids.water, 0.2f);
            requirements(Category.crafting, ItemStack.with(Items.lead, 160, Items.graphite, 75, Items.silicon, 45, Items.titanium, 140, Items.plastanium, 15));
        }};
        return block;
    }
}
