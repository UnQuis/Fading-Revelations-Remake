package fadingrevelations.content.production;
import fadingrevelations.content.FRGates;
import fadingrevelations.content.FRLiquids;
import fadingrevelations.content.FRItems;
import mindustry.content.*;
import mindustry.type.*;
import mindustry.world.blocks.power.ThermalGenerator;
import mindustry.gen.Sounds;
import mindustry.world.meta.Attribute;

import mindustry.world.Block;
public class TurbineConcentrator {
    public static Block block;
    public static Block load() {
        block = new ThermalGenerator("turbine-concentrator") {{
            localizedName = "Turbine Concentrator";
            description = "Similar to a Turbine Condenser but creates larger amounts of power and more water.";
            size = 3; attribute = Attribute.steam; minEfficiency = 0.0001f;
            powerProduction = 0.48f; displayEfficiency = false;
            hasLiquids = true; outputLiquid = new LiquidStack(Liquids.water, 0.015f);
            generateEffect = Fx.turbinegenerate; effectChance = 0.05f;
            ambientSound = Sounds.loopHum; ambientSoundVolume = 0.08f;
            requirements(Category.power, ItemStack.with(Items.beryllium, 90, Items.graphite, 75));
        }};
        return block;
    }
}
