package fadingrevelations.content;
import arc.graphics.Color;
import mindustry.content.*;
import mindustry.entities.bullet.LightningBulletType;
import mindustry.entities.effect.WaveEffect;
import mindustry.entities.pattern.ShootSpread;
import mindustry.type.*;
import mindustry.world.Block;
import mindustry.world.blocks.defense.*;
import mindustry.world.blocks.defense.turrets.PowerTurret;
import mindustry.world.consumers.ConsumePower;
import mindustry.world.meta.Env;

public class FRWalls {
    public static Block
        // mod walls
        steelAlloyWallSmall, steelAlloyWallLarge, livingSteelWall, livingSteelWallLarge,
        // serpulo advanced walls
        copperWall2, copperWall3,
        titaniumWall2, titaniumWall3,
        thoriumWall2, thoriumWall3,
        plastaniumWall2, plastaniumWall3,
        surgeAlloyWall2, surgeAlloyWall3,
        phaseFabricWall2, phaseFabricWall3,
        armoredDoor, doorHuge, doorGigantic,
        armoredWall,
        // erekir advanced walls
        berylliumWallHuge, berylliumWallGigantic,
        tungstenWallHuge, tungstenWallGigantic,
        carbideWallHuge, carbideWallGigantic,
        reinforcedSurgeWallHuge, reinforcedSurgeWallGigantic;

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

        copperWall2 = new Wall("copper-wall-2") {{
            localizedName = "Huge Copperwall";
            description = "A copper wall. Bigger than the big one.";
            health = 2880; size = 3;
            envDisabled = Env.scorching;
            requirements(Category.defense, ItemStack.with(Items.copper, 54));
        }};

        copperWall3 = new Wall("copper-wall-3") {{
            localizedName = "Gigantic Copperwall.";
            description = "A gigantic copper wall";
            health = 5120; size = 4;
            envDisabled = Env.scorching;
            requirements(Category.defense, ItemStack.with(Items.copper, 96));
        }};

        titaniumWall2 = new Wall("titanium-wall-2") {{
            localizedName = "Huge Titanium-Wall";
            description = "A huge wall made from titanium.";
            health = 3960; size = 3;
            envDisabled = Env.scorching;
            requirements(Category.defense, ItemStack.with(Items.titanium, 54));
        }};

        titaniumWall3 = new Wall("titanium-wall-3") {{
            localizedName = "Gigantic Titanium-Wall";
            description = "A gigantic wall made out of titanium.";
            health = 7040; size = 4;
            envDisabled = Env.scorching;
            requirements(Category.defense, ItemStack.with(Items.titanium, 96));
        }};

        thoriumWall2 = new Wall("thorium-wall-2") {{
            localizedName = "Huge Thorium-Wall";
            description = "A huge wall of thorium.";
            health = 7200; size = 3;
            envDisabled = Env.scorching;
            requirements(Category.defense, ItemStack.with(Items.thorium, 54));
        }};

        thoriumWall3 = new Wall("thorium-wall-3") {{
            localizedName = "Gigantic Thorium-Wall";
            description = "A gigantic wall made out of thorium.";
            health = 12800; size = 4;
            envDisabled = Env.scorching;
            requirements(Category.defense, ItemStack.with(Items.thorium, 96));
        }};

        plastaniumWall2 = new Wall("plastanium-wall-2") {{
            localizedName = "Huge Plastanium-Wall";
            description = "A huge wall made from plastanium";
            health = 4680; size = 3; insulated = true; absorbLasers = true;
            envDisabled = Env.scorching;
            requirements(Category.defense, ItemStack.with(Items.plastanium, 54));
        }};

        plastaniumWall3 = new Wall("plastanium-wall-3") {{
            localizedName = "Gigantic Plastanium-Wall";
            description = "A gigantic wall made from plastanium.";
            health = 8320; size = 4; insulated = true; absorbLasers = true;
            envDisabled = Env.scorching;
            requirements(Category.defense, ItemStack.with(Items.plastanium, 96));
        }};

        surgeAlloyWall2 = new Wall("surge-alloy-wall-2") {{
            localizedName = "Huge Surge-Alloy-Wall";
            description = "A huge wall made of surge alloy. Can shoot lightning sometimes.";
            health = 8280; size = 3;
            lightningChance = 0.10f; lightningDamage = 25; lightningLength = 20; conductivePower = true;
            envDisabled = Env.scorching;
            requirements(Category.defense, ItemStack.with(Items.surgeAlloy, 54));
        }};

        surgeAlloyWall3 = new Wall("surge-alloy-wall-3") {{
            localizedName = "Gigantic Surge-Alloy-Wall";
            description = "A gigantic wall made of surge alloy.";
            health = 14720; size = 4;
            lightningChance = 0.20f; lightningDamage = 30; lightningLength = 22; conductivePower = true;
            envDisabled = Env.scorching;
            requirements(Category.defense, ItemStack.with(Items.surgeAlloy, 96));
        }};

        phaseFabricWall2 = new Wall("phase-fabric-wall-2") {{
            localizedName = "Huge Phase-Fabric Wall";
            description = "A huge wall made out of phase-fabric.";
            health = 5400; size = 3; flashHit = true; chanceDeflect = 15;
            envDisabled = Env.scorching;
            requirements(Category.defense, ItemStack.with(Items.phaseFabric, 54));
        }};

        phaseFabricWall3 = new Wall("phase-fabric-wall-3") {{
            localizedName = "Gigantic Phase-Fabric-Wall";
            description = "A gigantic wall made of phase-fabric. A lot of it.";
            health = 9600; size = 4; flashHit = true; chanceDeflect = 20;
            envDisabled = Env.scorching;
            requirements(Category.defense, ItemStack.with(Items.phaseFabric, 96));
        }};

        armoredDoor = new AutoDoor("armored-door") {{
            localizedName = "Armored Door";
            description = "A door that opens automatically for allied units. Cannot be manually opened or closed.";
            health = 1860; size = 2;
            envDisabled = Env.scorching;
            requirements(Category.defense, ItemStack.with(Items.titanium, 24, Items.silicon, 32));
        }};

        doorHuge = new Door("door-huge") {{
            localizedName = "Huge door";
            description = "A huge door.";
            health = 3600; size = 3;
            envDisabled = Env.scorching;
            requirements(Category.defense, ItemStack.with(Items.titanium, 54, Items.silicon, 36));
        }};

        doorGigantic = new Door("door-gigantic") {{
            localizedName = "Gigantic Door";
            description = "A gigantic door.";
            health = 6400; size = 4;
            envDisabled = Env.scorching;
            requirements(Category.defense, ItemStack.with(Items.titanium, 96, Items.silicon, 64));
        }};

        armoredWall = new ShieldWall("armored-wall") {{
            localizedName = "Armored Wall";
            description = "A better Shielded Wall that has more health and a stronger shield. Uses Carbide.";
            health = 5820; size = 2; armor = 18; chanceDeflect = 9; shieldHealth = 1200;
            hasPower = true; consumesPower = true; outputsPower = false; conductivePower = true;
            consumePower(0.06f);
            envDisabled = Env.scorching;
            requirements(Category.defense, ItemStack.with(Items.phaseFabric, 24, Items.surgeAlloy, 10, Items.beryllium, 18, Items.carbide, 12));
        }};

        berylliumWallHuge = new Wall("beryllium-wall-huge") {{
            localizedName = "Huge Beryllium Wall";
            description = "A huge wall made from Beryllium.";
            health = 4680; size = 3; armor = 2;
            envDisabled = Env.scorching;
            requirements(Category.defense, ItemStack.with(Items.beryllium, 54));
        }};

        berylliumWallGigantic = new Wall("beryllium-wall-gigantic") {{
            localizedName = "Gigantic Beryllium Wall";
            description = "A gigantic wall made from Beryllium.";
            health = 8320; size = 4; armor = 2;
            envDisabled = Env.scorching;
            requirements(Category.defense, ItemStack.with(Items.beryllium, 96));
        }};

        tungstenWallHuge = new Wall("tungsten-wall-huge") {{
            localizedName = "Huge Tungsten Wall";
            description = "A huge wall made from Tungsten.";
            health = 6480; size = 3; armor = 14;
            envDisabled = Env.scorching;
            requirements(Category.defense, ItemStack.with(Items.tungsten, 54));
        }};

        tungstenWallGigantic = new Wall("tungsten-wall-gigantic") {{
            localizedName = "Gigantic Tungsten Wall";
            description = "A gigantic wall made from Tungsten.";
            health = 11520; size = 4; armor = 14;
            envDisabled = Env.scorching;
            requirements(Category.defense, ItemStack.with(Items.tungsten, 96));
        }};

        carbideWallHuge = new Wall("carbide-wall-huge") {{
            localizedName = "Huge Carbide Wall";
            description = "A huge wall made out of Carbide. Is armored.";
            health = 9720; size = 3; armor = 16;
            envDisabled = Env.scorching;
            requirements(Category.defense, ItemStack.with(Items.thorium, 54, Items.carbide, 54));
        }};

        carbideWallGigantic = new Wall("carbide-wall-gigantic") {{
            localizedName = "Gigantic Carbide Wall";
            description = "A gigantic wall made out of Carbide. Is armored.";
            health = 17280; size = 4; armor = 16;
            envDisabled = Env.scorching;
            requirements(Category.defense, ItemStack.with(Items.thorium, 96, Items.carbide, 96));
        }};

        reinforcedSurgeWallHuge = new Wall("reinforced-surge-wall-huge") {{
            localizedName = "Huge Reinforced Surge Wall";
            description = "A huge wall made out of Surge Alloy and Tungsten. Is armored.";
            health = 9000; size = 3; armor = 20;
            lightningChance = 0.05f; lightningDamage = 30;
            envDisabled = Env.scorching;
            requirements(Category.defense, ItemStack.with(Items.surgeAlloy, 54, Items.tungsten, 18));
        }};

        reinforcedSurgeWallGigantic = new Wall("reinforced-surge-wall-gigantic") {{
            localizedName = "Gigantic Reinforced Surge Wall";
            description = "A gigantic wall made out of Surge Alloy and Tungsten. Is armored.";
            health = 16000; size = 4; armor = 20;
            lightningChance = 0.05f; lightningDamage = 30;
            envDisabled = Env.scorching;
            requirements(Category.defense, ItemStack.with(Items.surgeAlloy, 96, Items.tungsten, 32));
        }};
    }
}
