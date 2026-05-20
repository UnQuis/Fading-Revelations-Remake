package fadingrevelations.content;

import arc.graphics.*;
import arc.struct.Seq;
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
    public static UnitType mainCoreUnit, epsilon, delta, omega;

    public static void load() {
        mainCoreUnit = new UnitType("main-core-unit") {{
            constructor = UnitEntity::create; localizedName = "Main Core Unit"; hidden = true; flying = true; hittable = false; hitSize = 1;
            killable = false; targetable = false;
            immunities.addAll(
                StatusEffects.burning, StatusEffects.freezing, StatusEffects.unmoving,
                StatusEffects.melting, StatusEffects.wet, StatusEffects.sapped,
                StatusEffects.tarred, StatusEffects.overdrive, StatusEffects.overclock,
                StatusEffects.boss, StatusEffects.shocked, StatusEffects.blasted
            );
            healFlash = false; canBoost = false; canAttack = true; canDrown = false; canHeal = false;
            speed = 0; rotateSpeed = 0;
            buildRange = 999999999999f; buildSpeed = 3.5f;
            drawBuildBeam = true; buildBeamOffset = -2;
            drawCell = false; drawItems = false; itemCapacity = 0;
            drawShields = false; drawMinimap = false; engineSize = 0;
            coreUnitDock = false; createWreck = false; createScorch = false;
            lightColor = Color.valueOf("ffffff"); lightRadius = 0;
            lowAltitude = true;
            mineFloor = true; mineWalls = true; mineRange = 999999999999f;
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

        omega = new UnitType("omega") {{
            constructor = UnitEntity::create; localizedName = "[cyan]Omega";
            description = "The ultimate core defense unit. Rapidly constructs and repairs structures, and projects a large force field.";
            drag = 0.12f; speed = 5f; buildSpeed = 4f; mineSpeed = 20; mineTier = 4;
            armor = 14; coreUnitDock = true; health = 480;
            flying = true; itemCapacity = 140; hitSize = 14;
            weapons.addAll(
                // 1. Healing laser — supports allies and damages enemies
                copyWeapon(new Weapon("nothing") {{
                    reload = 4; shootSound = shootLaser; rotate = true; rotationLimit = 20;
                    bullet = new LaserBoltBulletType(7f, 12f) {{
                        buildingDamageMultiplier = 0.01f; pierce = true; pierceCap = 12;
                        splashDamage = 8; splashDamageRadius = 6;
                        homingRange = 180; homingPower = 0.2f;
                        healPercent = 3; collidesTeam = true;
                        status = StatusEffects.shocked; statusDuration = 30;
                        hitEffect = new MultiEffect(
                            new WaveEffect() {{ colorFrom = Color.valueOf("44aaff"); colorTo = Color.valueOf("88eeff"); rotation = 3; sizeFrom = 0; sizeTo = 6; }},
                            new WaveEffect() {{ colorFrom = Color.valueOf("88eeff"); colorTo = Color.valueOf("44aaff"); rotation = 3; sizeFrom = 6; sizeTo = 0; }}
                        );
                    }};
                }}, 0, 2),
                // 2. Point defense — shoots down enemy projectiles (front and rear)
                copyWeapon(new Weapon("nothing") {{
                    reload = 5; rotate = true; rotateSpeed = 12; shootCone = 60;
                    bullet = new BasicBulletType(10f, 15) {{
                        lifetime = 40; despawnHit = true; keepVelocity = false;
                        hittable = false;
                    }};
                }}, 0, 6),
                // 3. Lightning arc — chain electricity (left)
                copyWeapon(new Weapon("nothing") {{
                    reload = 30; shootSound = Sounds.shoot; rotate = true; rotationLimit = 45;
                    shoot = new ShootSpread(3, 15);
                    bullet = new LightningBulletType() {{
                        lightningLength = 8; lightningLengthRand = 12;
                        damage = 18; lightningType = new LightningBulletType() {{
                            lightningLength = 4; lightningLengthRand = 6; damage = 9;
                            lightningColor = Color.valueOf("44aaff");
                        }};
                        lightningColor = Color.valueOf("88eeff");
                    }};
                }}, -7, 3),
                // 4. Lightning arc — chain electricity (right)
                copyWeapon(new Weapon("nothing") {{
                    reload = 30; shootSound = Sounds.shoot; rotate = true; rotationLimit = 45;
                    shoot = new ShootSpread(3, 15);
                    bullet = new LightningBulletType() {{
                        lightningLength = 8; lightningLengthRand = 12;
                        damage = 18; lightningType = new LightningBulletType() {{
                            lightningLength = 4; lightningLengthRand = 6; damage = 9;
                            lightningColor = Color.valueOf("44aaff");
                        }};
                        lightningColor = Color.valueOf("88eeff");
                    }};
                }}, 7, 3),
                // 5. Homing missiles — AoE damage
                copyWeapon(new Weapon("nothing") {{
                    reload = 45; shootSound = Sounds.shoot; rotate = true; rotationLimit = 30;
                    shoot = new ShootSpread(4, 12);
                    bullet = new MissileBulletType(5f, 22) {{
                        lifetime = 120; splashDamage = 35; splashDamageRadius = 24;
                        homingPower = 0.08f; homingRange = 160;
                        trailColor = Color.valueOf("88eeff");
                        hitEffect = blastExplosion;
                    }};
                }}, 0, -5)
            );
            abilities.addAll(
                new ForceFieldAbility(14f, 3f, 400f, 600f) {{ alpha = 0.08f; }},
                new EnergyFieldAbility(18f, 10f, 90f) {{
                    color = Color.valueOf("44aaff"); healPercent = 3; hitBuildings = true; hitUnits = true;
                    maxTargets = 6; sectors = 6;
                    status = StatusEffects.shielded; statusDuration = 30;
                    targetAir = true; targetGround = true;
                }}
            );
        }};
    }

    private static Weapon copyWeapon(Weapon weapon, float x, float y) {
        weapon.x = x; weapon.y = y; weapon.mirror = true; weapon.alternate = true;
        return weapon;
    }
}
