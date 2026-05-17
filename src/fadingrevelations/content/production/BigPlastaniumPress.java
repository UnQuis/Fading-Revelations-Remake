package fadingrevelations.content.production;
import fadingrevelations.content.FRGates;
import fadingrevelations.content.FRLiquids;
import fadingrevelations.content.FRItems;
import mindustry.content.*;
import mindustry.type.*;
import mindustry.world.blocks.production.GenericCrafter;

import mindustry.world.Block;
public class BigPlastaniumPress {
    public static Block load() {
        return new GenericCrafter("big-plastanium-press") {{
            localizedName = "Plastanium Forge";
            description = "A better plastanium forge that is more cost-efficient.";
            size = 3; hasPower = true; hasItems = true; hasLiquids = true; health = 600;
            updateEffect = Fx.plasticburn; craftEffect = Fx.formsmoke;
            craftTime = 60; itemCapacity = 20; liquidCapacity = 84;
            consumePower(3.75f); consumeItem(Items.titanium, 6); consumeLiquid(Liquids.oil, 0.6f);
            outputItem = new ItemStack(Items.plastanium, 4);
            requirements(Category.crafting, ItemStack.with(Items.lead, 160, Items.silicon, 160, Items.titanium, 140, Items.plastanium, 25));
        }};
    }
}
