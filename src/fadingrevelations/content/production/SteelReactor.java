package fadingrevelations.content.production;
import fadingrevelations.content.FRGates;
import fadingrevelations.content.FRLiquids;
import fadingrevelations.content.FRItems;
import mindustry.content.*;
import mindustry.type.*;
import mindustry.world.blocks.power.ImpactReactor;

import mindustry.world.Block;
public class SteelReactor {
    public static Block load() {
        return new ImpactReactor("steel-reactor") {{
            localizedName = "Amalgam Generator";
            description = "Uses the high flammability of Steel Amalgam to create large amounts of power from it.";
            health = 1100; size = 4; hasPower = true; hasLiquids = true; hasItems = true;
            explosionShake = 15; itemCapacity = 30; liquidCapacity = 200;
            itemDuration = 360; powerProduction = 270;
            explosionDamage = 4000; explosionRadius = 320;
            consumePower(20f); consumeLiquid(Liquids.cryofluid, 0.1f); consumeItem(FRItems.steelAlloy, 1);
            requirements(Category.power, ItemStack.with(Items.copper, 750, Items.lead, 700, Items.graphite, 400, Items.metaglass, 350, Items.silicon, 350, Items.thorium, 150, FRItems.steelAlloy, 50));
        }};
    }
}
