package fadingrevelations.content;
import arc.graphics.Color;
import mindustry.content.*;
import mindustry.entities.bullet.LightningBulletType;
import mindustry.entities.effect.WaveEffect;
import mindustry.entities.pattern.ShootSpread;
import mindustry.type.*;
import mindustry.world.Block;
import mindustry.world.blocks.defense.MendProjector;
import mindustry.world.blocks.defense.turrets.PowerTurret;
import mindustry.world.consumers.ConsumePower;
import mindustry.world.meta.Env;

public class FRWalls {
    public static Block steelAlloyWallSmall, steelAlloyWallLarge, livingSteelWall, livingSteelWallLarge;

    public static void load() {
        steelAlloyWallSmall = new PowerTurret("steel-alloy-wall-small") {{
            localizedName = "Amalgam Wall";
            description = "A small wall made from Steel Amalgam. Zaps enemies around it when they get close.";
            hasItems = false; hasPower = false; hasLiquids = false; canOverdrive = false;
            size = 1; recoil = 0f; health = 1200; rotate = false; rotateSpeed = 0f; shootY = 0f;
            shootCone = 360; range = 30; targetAir = false; targetGround = true; reload = 90;
            consume(new ConsumePower(0.2f, 0f, true));
            shoot = new ShootSpread(8, 60);
            shootType = new LightningBulletType() {{
                lightningLength = 7; damage = 3; lightningColor = Color.valueOf("bd7374");
                hitEffect = new WaveEffect() {{
                    colorFrom = Color.valueOf("dbaf85"); colorTo = Color.valueOf("ba6a83");
                }};
            }};
            envDisabled = Env.scorching;
            requirements(Category.defense, ItemStack.with(FRItems.steelAlloy, 6));
        }};

        steelAlloyWallLarge = new PowerTurret("steel-alloy-wall-large") {{
            localizedName = "Large Amalgam Wall";
            description = "A large wall made from Steel Amalgam. Zaps enemies around it when they get close.";
            hasItems = false; hasPower = false; hasLiquids = false; canOverdrive = false;
            size = 2; recoil = 0f; health = 4200; rotate = false; rotateSpeed = 0f; shootY = 0f;
            shootCone = 360; range = 60; targetAir = false; targetGround = true; reload = 90;
            consume(new ConsumePower(0.3f, 0f, true));
            shoot = new ShootSpread(12, 50);
            shootType = new LightningBulletType() {{
                lightningLength = 10; damage = 4; lightningColor = Color.valueOf("bd7374");
                hitEffect = new WaveEffect() {{
                    colorFrom = Color.valueOf("dbaf85"); colorTo = Color.valueOf("ba6a83");
                }};
            }};
            envDisabled = Env.scorching;
            requirements(Category.defense, ItemStack.with(FRItems.steelAlloy, 24));
        }};

        livingSteelWall = new MendProjector("living-steel-wall") {{
            localizedName = "Living Steel Wall";
            description = "A wall made from living steel that heals itself slowly.";
            health = 960; size = 1; range = 1; itemCapacity = 0; hasItems = false; hasPower = false;
            baseColor = Color.valueOf("8c0291"); phaseColor = Color.valueOf("8c0291"); insulated = true;
            reload = 2500; healPercent = 1;
            envDisabled = Env.scorching;
            requirements(Category.defense, ItemStack.with(FRItems.livingSteelHard, 6));
        }};

        livingSteelWallLarge = new MendProjector("living-steel-wall-2") {{
            localizedName = "Living Steel Wall Large";
            description = "A large wall made of Living Steel. Heals itself slowly.";
            health = 3220; healPercent = 1; reload = 2500; insulated = true;
            hasPower = false; range = 1; baseColor = Color.valueOf("8c0291"); phaseColor = Color.valueOf("8c0291");
            hasItems = false; itemCapacity = 0; size = 2;
            envDisabled = Env.scorching;
            requirements(Category.defense, ItemStack.with(FRItems.livingSteelHard, 24));
        }};
    }
}
