package fadingrevelations.content.production;
import fadingrevelations.content.FRGates;
import fadingrevelations.content.FRLiquids;
import fadingrevelations.content.FRItems;
import mindustry.content.*;
import mindustry.type.*;
import mindustry.world.blocks.production.GenericCrafter;

import mindustry.world.Block;
public class EnhancedBlastMixer {
    public static Block block;
    public static Block load() {
        block = new GenericCrafter("enhanced-blast-mixer") {{
            localizedName = "Blast Forge";
            description = "A better blast mixer. Gets very hot so it uses graphite for isolation.";
            size = 3; hasPower = true; hasItems = true; health = 240; hasLiquids = false;
            craftTime = 90; craftEffect = Fx.plasticExplosion; updateEffect = Fx.pulverizeRed;
            consumePower(0.6f); consumeItems(ItemStack.with(Items.pyratite, 2, Items.sporePod, 2));
            outputItem = new ItemStack(Items.blastCompound, 2);
            requirements(Category.crafting, ItemStack.with(Items.copper, 300, Items.lead, 240, Items.graphite, 120, Items.titanium, 60));
        }};
        return block;
    }
}
