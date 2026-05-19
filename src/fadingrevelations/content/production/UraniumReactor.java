package fadingrevelations.content.production;
import fadingrevelations.content.FRGates;
import fadingrevelations.content.FRLiquids;
import fadingrevelations.content.FRItems;
import mindustry.content.*;
import mindustry.type.*;
import mindustry.world.blocks.power.NuclearReactor;

import mindustry.world.Block;
public class UraniumReactor {
    public static Block block;
    public static Block load() {
        block = new NuclearReactor("uranium-reactor") {{
            localizedName = "Nuclear Reactor";
            description = "A reactor that uses uraniumrods to create a lot of power. Explodes violently when it is not cooled extensively!";
            size = 4; hasPower = true; hasLiquids = true; hasItems = true;
            explosionShake = 10; itemCapacity = 20; liquidCapacity = 120;
            itemDuration = 1650; powerProduction = 330; heating = 0.02f;
            smokeThreshold = 0.58f; explosionRadius = 500; explosionDamage = 10000;
            flashThreshold = 0.4f; coolantPower = 0.5f;
            fuelItem = FRItems.fuelRod;
            consumeLiquid(FRLiquids.livingSteelLiquid, 0.2f);
            consumeItem(FRItems.fuelRod, 9);
            requirements(Category.power, ItemStack.with(Items.copper, 1000, Items.lead, 900, Items.graphite, 750, Items.metaglass, 500, Items.silicon, 500, Items.titanium, 800, Items.thorium, 1200));
        }};
        return block;
    }
}
