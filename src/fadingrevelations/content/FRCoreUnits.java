package fadingrevelations.content;

import arc.graphics.*;
import mindustry.content.*;
import mindustry.entities.bullet.*;
import mindustry.entities.effect.*;
import mindustry.entities.pattern.*;
import mindustry.type.*;
import mindustry.gen.*;
import mindustry.entities.abilities.*;

import static mindustry.content.Fx.*;
import static mindustry.gen.Sounds.*;

public class FRCoreUnits {
    public static UnitType coreTurretUnit, epsilon, delta;

    public static void load() {
        coreTurretUnit = new UnitType("ultimate-unit") {{
            constructor = UnitEntity::create; localizedName = "Ultimate Unit"; hidden = true; flying = true;

            hittable = false; hitSize = 1;
            killable = false; targetable = false;

            immunities.addAll(
                StatusEffects.burning, StatusEffects.freezing, StatusEffects.unmoving,
                StatusEffects.melting, StatusEffects.wet, StatusEffects.sapped,
                StatusEffects.tarred, StatusEffects.overdrive, StatusEffects.overclock,
                StatusEffects.boss, StatusEffects.shocked, StatusEffects.blasted
            );

            healFlash = false; canBoost = false; canAttack = true; canDrown = false; canHeal = false;
            speed = 0; rotateSpeed = 0;

            buildRange = Float.MAX_VALUE; buildSpeed = 3.5f;
            drawBuildBeam = true; buildBeamOffset = -2;
            drawCell = false; drawItems = false; itemCapacity = 0;
            drawShields = false; drawMinimap = false; engineSize = 0;
            coreUnitDock = false;
            createWreck = false; createScorch = false;
            lightColor = Color.valueOf("ffffff"); lightRadius = 0;
            lowAltitude = true;

            mineFloor = true; mineWalls = true; mineRange = Float.MAX_VALUE;
            mineHardnessScaling = true; mineSpeed = 20; mineTier = 10;

            physics = false; playerControllable = true; useUnitCap = false; bounded = true;

            weapons.addAll(
                new Weapon("main-core-hail") {{
                    x = 23; y = 20; mirror = false; reload = 60; alternate = true;
                    shootSound = Sounds.shoot; rotate = true; inaccuracy = 1;
                    bullet = new ArtilleryBulletType(3f, 10f) {{
                        splashDamage = 10; splashDamageRadius = 20; lifetime = 80;
                        knockback = 0.8f; hitEffect = blastExplosion;
                        width = 13; height = 13; collidesTiles = false;
                        frontColor = Color.valueOf("f8ad42"); backColor = Color.valueOf("f68021");
                    }};
                }},
                new Weapon("main-core-hail") {{
                    x = -23; y = 20; mirror = false; reload = 60; alternate = true;
                    shootSound = Sounds.shoot; rotate = true; inaccuracy = 1;
                    bullet = new ArtilleryBulletType(3f, 10f) {{
                        splashDamage = 10; splashDamageRadius = 20; lifetime = 80;
                        knockback = 0.8f; hitEffect = blastExplosion;
                        width = 13; height = 13; collidesTiles = false;
                        frontColor = Color.valueOf("f8ad42"); backColor = Color.valueOf("f68021");
                    }};
                }},
                new Weapon("main-core-hail") {{
                    x = 23; y = -27; mirror = false; reload = 60; alternate = true;
                    shootSound = Sounds.shoot; rotate = true; inaccuracy = 1;
                    bullet = new ArtilleryBulletType(3f, 10f) {{
                        splashDamage = 10; splashDamageRadius = 20; lifetime = 80;
                        knockback = 0.8f; hitEffect = blastExplosion;
                        width = 13; height = 13; collidesTiles = false;
                        frontColor = Color.valueOf("f8ad42"); backColor = Color.valueOf("f68021");
                    }};
                }},
                new Weapon("main-core-hail") {{
                    x = -23; y = -27; mirror = false; reload = 60; alternate = true;
                    shootSound = Sounds.shoot; rotate = true; inaccuracy = 1;
                    bullet = new ArtilleryBulletType(3f, 10f) {{
                        splashDamage = 10; splashDamageRadius = 20; lifetime = 80;
                        knockback = 0.8f; hitEffect = blastExplosion;
                        width = 13; height = 13; collidesTiles = false;
                        frontColor = Color.valueOf("f8ad42"); backColor = Color.valueOf("f68021");
                    }};
                }},
                new Weapon() {{
                    x = 0; y = -1; mirror = false; rotate = true;
                    controllable = false; autoTarget = true; reload = 360;
                    bullet = new BulletType() {{
                        damage = 0; splashDamage = 1; splashDamageRadius = 80;
                        status = StatusEffects.sporeSlowed; statusDuration = 180;
                        instantDisappear = true;
                        despawnHit = true;
                        hitEffect = new WaveEffect() {{
                            sizeFrom = 0; sizeTo = 120; lifetime = 60;
                            colorFrom = Color.valueOf("b0bac0"); colorTo = Color.valueOf("818181");
                        }};
                    }};
                }},
                new Weapon() {{
                    x = 0; y = 29; reload = 35; shootSound = Sounds.shoot;
                    rotate = false; baseRotation = 0; shootCone = 20; mirror = false;
                    shoot = new ShootSpread(3, 20);
                    bullet = new ShrapnelBulletType() {{
                        damage = 20; shootEffect = thoriumShoot; smokeEffect = thoriumShoot;
                        toColor = Color.valueOf("f9a3c7");
                    }};
                }},
                new Weapon() {{
                    x = 29; y = -2; reload = 35; shootSound = Sounds.shoot;
                    rotate = false; baseRotation = -90; mirror = false; shootCone = 20;
                    shoot = new ShootSpread(3, 20);
                    bullet = new ShrapnelBulletType() {{
                        damage = 20; shootEffect = thoriumShoot; smokeEffect = thoriumShoot;
                        toColor = Color.valueOf("f9a3c7");
                    }};
                }},
                new Weapon() {{
                    x = -29; y = -2; reload = 35; shootSound = Sounds.shoot;
                    rotate = false; baseRotation = 90; mirror = false; shootCone = 20;
                    shoot = new ShootSpread(3, 20);
                    bullet = new ShrapnelBulletType() {{
                        damage = 20; shootEffect = thoriumShoot; smokeEffect = thoriumShoot;
                        toColor = Color.valueOf("f9a3c7");
                    }};
                }},
                new Weapon() {{
                    x = 0; y = -29; reload = 35; shootSound = Sounds.shoot;
                    rotate = false; baseRotation = -180; mirror = false; shootCone = 20;
                    shoot = new ShootSpread(3, 20);
                    bullet = new ShrapnelBulletType() {{
                        damage = 20; shootEffect = thoriumShoot; smokeEffect = thoriumShoot;
                        toColor = Color.valueOf("f9a3c7");
                    }};
                }}
            );
            abilities.addAll(
                new UnitSpawnAbility(UnitTypes.mono, 0, -2, 600),
                new EnergyFieldAbility(20f, 12f, 120f) {{ color = Color.valueOf("dcc6c6");
                    healPercent = 4; hitBuildings = false; hitUnits = true;
                    maxTargets = 8; sectors = 4;
                    shootSound = shockBullet;
                    status = StatusEffects.electrified; statusDuration = 60;
                    targetAir = true; targetGround = true;
                    x = 0; y = -2;
                }},
                new ForceFieldAbility(100f, 1f, 2000f, 720f) {{ alpha = 0.1f; }}
            );
        }};

        epsilon = new UnitType("epsilon") {{
            constructor = UnitEntity::create; localizedName = "[yellow]Epsilon";
            description = "Defends the Element core from enemies. Rapidly builds a variety of structures, and is equipped with multiple healing beams.";
            drag = 0.15f; speed = 4.25f; buildSpeed = 3; mineSpeed = 16;
            coreUnitDock = true; mineTier = 3; armor = 10; health = 310;
            flying = true; itemCapacity = 100; hitSize = 10;
            weapons.add(
                new Weapon("nothing") {{
                    x = 3; y = 2; reload = 5; mirror = true; alternate = true;
                    shootSound = shootLaser;
                    bullet = new LaserBoltBulletType(6f, 11f) {{
                        buildingDamageMultiplier = 0.01f; pierce = true; pierceCap = 10;
                        splashDamage = 6; splashDamageRadius = 5;
                        homingRange = 170; homingPower = 0.18f;
                        healPercent = 2; collidesTeam = true;
                        hitEffect = new MultiEffect(
                            new WaveEffect() {{ colorFrom = Color.valueOf("ffd37f"); colorTo = Color.valueOf("d4816b"); rotation = 3; sizeFrom = 0; sizeTo = 5; }},
                            new WaveEffect() {{ colorFrom = Color.valueOf("d4816b"); colorTo = Color.valueOf("ffd37f"); rotation = 3; sizeFrom = 5; sizeTo = 0; }}
                        );
                    }};
                }}
            );
            abilities.add(new ForceFieldAbility(10f, 2f, 240f, 480f));
        }};

        delta = new UnitType("delta") {{
            constructor = UnitEntity::create; localizedName = "[yellow]Delta";
            description = "Defends the Atom core from enemies. Swiftly builds structures, and is equipped with healing beams.";
            drag = 0.15f; speed = 4; buildSpeed = 2.5f; mineSpeed = 14; mineTier = 3;
            armor = 10; coreUnitDock = true; health = 260;
            flying = true; itemCapacity = 80; hitSize = 7;
            weapons.add(
                new Weapon("nothing") {{
                    x = 0; y = 2; reload = 7; shootSound = shootLaser;
                    bullet = new LaserBoltBulletType(6f, 11f) {{
                        buildingDamageMultiplier = 0.01f; pierce = true; pierceCap = 10;
                        splashDamage = 6; splashDamageRadius = 4;
                        homingRange = 170; homingPower = 0.18f;
                        healPercent = 1; collidesTeam = true;
                        hitEffect = new MultiEffect(
                            new WaveEffect() {{ colorFrom = Color.valueOf("d4816b"); colorTo = Color.valueOf("ffd37f"); rotation = 3; sizeFrom = 0; sizeTo = 4; }},
                            new WaveEffect() {{ colorFrom = Color.valueOf("ffd37f"); colorTo = Color.valueOf("d4816b"); rotation = 3; sizeFrom = 4; sizeTo = 0; }}
                        );
                    }};
                }}
            );
            abilities.add(new ForceFieldAbility(10f, 1f, 140f, 480f));
        }};
    }

    private static Weapon copyWeapon(Weapon weapon, float x, float y) {
        weapon.x = x; weapon.y = y; weapon.mirror = true; weapon.alternate = true;
        return weapon;
    }
}
