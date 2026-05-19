package fadingrevelations.content.production;
import fadingrevelations.content.FRGates;
import fadingrevelations.content.FRLiquids;
import fadingrevelations.content.FRItems;
import mindustry.content.*;
import mindustry.type.*;
import mindustry.world.blocks.production.HeatCrafter;
import mindustry.gen.Sounds;

import mindustry.world.Block;
public class PhaseManufacturer {
    public static Block block;
    public static Block load() {
        block = new HeatCrafter("phase-manufacturer") {{
            localizedName = "Phase Manufacturer";
            description = "A better phase synthesizer that works more efficiently";
            size = 4; itemCapacity = 50; heatRequirement = 10; craftTime = 90; liquidCapacity = 50;
            outputItem = new ItemStack(Items.phaseFabric, 2);
            consumePower(9f); consumeItems(ItemStack.with(Items.thorium, 2, Items.sand, 6));
            consumeLiquid(Liquids.ozone, 0.0334f);
            ambientSound = Sounds.loopTech; ambientSoundVolume = 0.05f;
            requirements(Category.crafting, ItemStack.with(Items.carbide, 120, Items.silicon, 160, Items.thorium, 120, Items.tungsten, 280));
        }};
        return block;
    }
}
