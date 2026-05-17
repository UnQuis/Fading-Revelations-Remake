package fadingrevelations.content.production;
import fadingrevelations.content.FRGates;
import fadingrevelations.content.FRLiquids;
import fadingrevelations.content.FRItems;
import mindustry.content.*;
import mindustry.type.*;
import mindustry.world.consumers.*;
import mindustry.world.blocks.power.ConsumeGenerator;
import mindustry.gen.Sounds;

import mindustry.world.Block;
public class SteamTurbine {
    public static Block load() {
        return new ConsumeGenerator("steam-turbine") {{
            localizedName = "Steam Turbine";
            description = "A better version of the Steam Turbine built using Living Steel. Uses more water in exchange for greatly improved power output. Slowly produces steam and builds up pressure. Explodes when the pressure reaches a critical point.";
            size = 3; hasLiquids = true; liquidCapacity = 20;
            outputLiquid = new LiquidStack(FRLiquids.steam, 0.05f);
            explodeOnFull = true; powerProduction = 13.33333f; itemDuration = 120;
            consumeLiquid(Liquids.water, 0.2f);
            consume(new ConsumeItemFlammable());
            consume(new ConsumeItemExplode());
            generateEffect = Fx.generatespark;
            ambientSound = Sounds.loopSmelter; ambientSoundVolume = 0.09f;
            requirements(Category.power, ItemStack.with(Items.copper, 60, Items.lead, 60, Items.graphite, 35, Items.silicon, 40, FRItems.livingSteel, 20));
        }};
    }
}
