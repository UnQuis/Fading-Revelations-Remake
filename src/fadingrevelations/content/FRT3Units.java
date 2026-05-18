package fadingrevelations.content;

import arc.graphics.*;
import arc.math.*;
import arc.math.geom.*;
import mindustry.ai.types.*;
import mindustry.content.*;
import mindustry.entities.*;
import mindustry.entities.abilities.*;
import mindustry.entities.bullet.*;
import mindustry.entities.effect.*;
import mindustry.entities.part.*;
import mindustry.entities.pattern.*;
import mindustry.gen.*;
import mindustry.type.*;
import mindustry.type.weapons.*;

import static mindustry.content.Fx.*;
import static mindustry.gen.Sounds.*;

public class FRT3Units {
    public static UnitType aestiva, arnux, auratus, ducalis, kestrel, onirion, plant, springald;

    public static void load() {
        aestiva = new UnitType("aestiva") {{
            constructor = UnitWaterMove::create; localizedName = "Aestiva";
            health = 9000; armor = 12; hitSize = 28;
            speed = 0.8f; drag = 0.17f; accel = 0.3f; rotateSpeed = 1.8f;
            faceTarget = false;
            trailLength = 40; trailColor = Color.valueOf("ffffff");
            abilities.add(
                new UnitSpawnAbility(FRT1Units.alba, 600f, 0f, 0f)
            );
            weapons.add(
                new Weapon("heal-shotgun-weapon") {{
                    x = 0; y = -15; mirror = false; rotate = true;
                    reload = 180; inaccuracy = 10; rotateSpeed = 3;
                    shootSound = shootLaser;
                    shoot = new ShootPattern() {{ shots = 24; shotDelay = 1; }};
                    bullet = new LaserBoltBulletType() {{
                        speed = 5; lifetime = 45; inaccuracy = 10;
                        width = 4; height = 10;
                        frontColor = Color.valueOf("84f491"); backColor = Color.valueOf("62ae7f");
                        damage = 1; collidesTeam = true; healAmount = 5;
                    }};
                }}
            );
        }};

        arnux = new UnitType("arnux") {{
            constructor = UnitWaterMove::create; localizedName = "Arnux";
            health = 12100; armor = 12; hitSize = 32.5f;
            speed = 0.82f; drag = 0.17f; accel = 0.22f; rotateSpeed = 1.5f;
            faceTarget = false;
            trailLength = 60; trailColor = Color.valueOf("ffffff");
            weapons.addAll(
                new Weapon("large-bullet-mount") {{
                    x = 17.5f; y = -16.5f; mirror = true; alternate = true;
                    rotate = true; shootY = 7;
                    reload = 480; inaccuracy = 6; cooldownTime = 90;
                    rotateSpeed = 4; shake = 6; recoil = 4;
                    shootSound = shootLaser; chargeSound = chargeLancer;
                    ejectEffect = Fx.none;
                    shoot = new ShootPattern() {{ shots = 3; shotDelay = 8; firstShotDelay = 120; }};
                    bullet = new LaserBulletType() {{
                        damage = 600; length = 320; lifetime = 90;
                        hitShake = 4; largeHit = true;
                        status = FRStatus.emp; statusDuration = 120;
                        sideLength = 20; sideWidth = 4; sideAngle = 30;
                        colors = new Color[]{Color.valueOf("ec7458aa"), Color.valueOf("ff9c5a"), Color.white};
                        chargeEffect = new MultiEffect(
                            new WaveEffect() {{ sizeFrom = 0; sizeTo = 20; interp = Interp.pow2Out; }},
                            new WaveEffect() {{ sizeFrom = 22; sizeTo = 2; interp = Interp.pow2Out; }}
                        ) {{ followParent = true; lifetime = 45; }};
                    }};
                }},
                new Weapon("king-heal") {{
                    x = 0; y = 8; mirror = false; rotate = true;
                    reload = 120; shake = 7; rotateSpeed = 4;
                    shootSound = shootArtillery;
                    bullet = new BasicBulletType() {{
                        speed = 4; lifetime = 120; damage = 1200;
                        width = 40; height = 40; spin = 60;
                        pierce = true; pierceCap = 8;
                        status = StatusEffects.shocked; statusDuration = 60;
                        frontColor = Color.valueOf("ec7458aa"); backColor = Color.valueOf("ff9c5a");
                        trailChance = -1; trailColor = Color.valueOf("d99f6b");
                        trailLength = 12; trailWidth = 3;
                        sprite = "emp-bullet";
                        lightColor = Color.valueOf("ff9c5a"); lightOpacity = 0.7f; lightRadius = 40;
                        trailEffect = new MultiEffect(
                            new ParticleEffect() {{
                                colorFrom = Color.valueOf("ec7458aa"); colorTo = Color.valueOf("ff9c5a");
                                length = 10; baseLength = 20;
                                sizeInterp = Interp.pow2Out; interp = Interp.pow2In;
                                particles = 5; cone = 10;
                            }},
                            new ParticleEffect() {{
                                colorFrom = Color.valueOf("ec7458aa"); colorTo = Color.valueOf("ff9c5a");
                                length = 20; baseLength = 10;
                                sizeInterp = Interp.pow2In; interp = Interp.pow2Out;
                                particles = 5; cone = 10;
                            }}
                        );
                    }};
                }}
            );
        }};

        auratus = new UnitType("auratus") {{
            constructor = LegsUnit::create; localizedName = "Auratus";
            hovering = true;
            speed = 0.62f; drag = 0.1f; hitSize = 23; health = 9100; armor = 6; rotateSpeed = 2.7f;
            legCount = 6; legMoveSpace = 1; legPairOffset = 3; legLength = 35;
            legExtension = -15; legBaseOffset = 12; stepShake = 1;
            legLengthScl = 0.96f; rippleScale = 2; legSpeed = 0.2f;
            legSplashDamage = 40; legSplashRange = 32;
            drownTimeMultiplier = 2.2f;
            shadowElevation = 0.65f; groundLayer = 75;
            weapons.addAll(
                new Weapon("large-purple-mount") {{
                    x = 18; y = -3; mirror = false; rotate = true;
                    reload = 6; shootSound = shootSap;
                    bullet = new LiquidBulletType(Liquids.water) {{
                        speed = 3; lifetime = 60; damage = 20;
                        pierce = true; status = StatusEffects.wet;
                    }};
                }},
                new Weapon("large-purple-mount") {{
                    x = -18; y = -3; mirror = false; rotate = true;
                    reload = 10; shootSound = shootSap;
                    bullet = new LiquidBulletType(Liquids.slag) {{
                        speed = 3; lifetime = 60; damage = 40;
                        status = StatusEffects.burning;
                    }};
                }},
                new Weapon("reclusa-laser") {{
                    x = 0; y = -15; mirror = false; rotate = false;
                    reload = 32; shootSound = shootArtillery;
                    bullet = new MissileBulletType() {{
                        height = 12; width = 8;
                        frontColor = Color.valueOf("fcf387"); backColor = Color.valueOf("d99f6b");
                        trailColor = Color.valueOf("d99f6b"); trailChance = -1; trailLength = 12; trailWidth = 3;
                        speed = 5; lifetime = 60; damage = 60;
                        pierce = true; pierceBuilding = true; pierceCap = 6;
                    }};
                }}
            );
        }};

        ducalis = new UnitType("ducalis") {{
            constructor = UnitEntity::create; localizedName = "Ducalis"; flying = true; hovering = true; lowAltitude = true;
            health = 10000; armor = 10; hitSize = 27.5f;
            outlineColor = Color.valueOf("191919");
            itemCapacity = 60; speed = 1.8f;
            parts.addAll(
                new HoverPart() {{ x = 14; y = 11; mirror = true; radius = 6; phase = 170; color = Color.valueOf("ffd59e"); stroke = 2; layerOffset = -0.001f; }},
                new HoverPart() {{ x = 18; y = -6; mirror = true; radius = 7; phase = 180; color = Color.valueOf("ffd59e"); stroke = 3; layerOffset = -0.001f; }},
                new HoverPart() {{ x = 9; y = -22; mirror = true; radius = 7; phase = 175; color = Color.valueOf("ffd59e"); stroke = 3; layerOffset = -0.001f; rotation = 90; }}
            );
            weapons.addAll(
                new Weapon("dark-large-bullet-mount") {{
                    x = 14; y = -14; mirror = true; rotate = true;
                    reload = 40; alternate = true; shake = 3;
                    shootSound = shootLaser;
                    bullet = new LaserBulletType() {{
                        length = 320; damage = 50; lifetime = 90;
                        width = 16; status = StatusEffects.shocked;
                        lightning = 3; lightningLength = 12; lightningDamage = 20; lightningColor = Color.valueOf("ff9c5a");
                        colors = new Color[]{Color.valueOf("ec7458aa"), Color.valueOf("ff9c5a"), Color.white};
                    }};
                }},
                new Weapon("dark-large-bullet-mount") {{
                    x = 0; y = 11; mirror = false; rotate = true;
                    reload = 50; shake = 3;
                    shootSound = shootLaser;
                    bullet = new LaserBulletType() {{
                        length = 300; damage = 50; lifetime = 90;
                        width = 16; status = StatusEffects.shocked;
                        lightning = 3; lightningLength = 12; lightningDamage = 20; lightningColor = Color.valueOf("ff9c5a");
                        colors = new Color[]{Color.valueOf("ec7458aa"), Color.valueOf("ff9c5a"), Color.white};
                    }};
                }},
                new Weapon("dark-large-laser-mount") {{
                    x = 0; y = -15; mirror = false; rotate = false;
                    reload = 320; shake = 5;
                    shootSound = shootArtillery;
                    bullet = new MissileBulletType() {{
                        width = 20; height = 20; hitSize = 20;
                        speed = 3; lifetime = 90; damage = 180;
                        pierce = false; despawnHit = true;
                        weaveMag = 4; weaveScale = 4;
                        trailWidth = 10; trailLength = 40; trailChance = -1;
                        lightRadius = 40; lightOpacity = 0.7f;
                        fragBullets = 30; fragSpread = 18; fragRandomSpread = 0;
                        hitEffect = hitMeltdown;
                        fragBullet = new ShrapnelBulletType() {{
                            length = 80; width = 16; damage = 80; lifetime = 90;
                            hitLarge = true;
                            fromColor = Color.valueOf("ff9c5a"); toColor = Color.valueOf("ec7458aa");
                            hitSound = Sounds.shoot; despawnHit = true;
                        }};
                    }};
                }}
            );
        }};

        kestrel = new UnitType("kestrel") {{
            constructor = UnitEntity::create; localizedName = "Kestrel"; flying = true; lowAltitude = true;
            health = 8600; armor = 10; hitSize = 27;
            speed = 3; itemCapacity = 200;
            engineSize = 9; engineOffset = 27;
            weapons.addAll(
                new Weapon("kestrel-main") {{
                    x = 0; y = -15; mirror = false; rotate = false;
                    reload = 240; shootY = 10; recoilTime = 200;
                    shootSound = shootLaser; rotateSpeed = 0;
                    continuous = true;
                    bullet = new ContinuousLaserBulletType(7f) {{
                        lifetime = 180; fadeTime = 20; length = 240;
                        width = 4; makeFire = true; pierceBuilding = false;
                        status = StatusEffects.burning;
                        colors = new Color[]{Color.valueOf("d06b53"), Color.valueOf("d18877"), Color.valueOf("ffa665")};
                    }};
                }},
                new Weapon("arc-weapon") {{
                    x = -15; y = 5; mirror = false; rotate = true;
                    reload = 1; rotateSpeed = 6; top = true; recoil = 0;
                    shootSound = shockBullet;
                    bullet = new LightningBulletType() {{
                        lightningLength = 35; damage = 5;
                    }};
                }},
                new Weapon("arc-weapon") {{
                    x = 15; y = 5; mirror = false; rotate = true;
                    reload = 1; rotateSpeed = 6; top = true; recoil = 0;
                    shootSound = shockBullet;
                    bullet = new LightningBulletType() {{
                        lightningLength = 35; damage = 5;
                    }};
                }},
                new Weapon("kestrel-secondary") {{
                    x = 0; y = 15; mirror = false; rotate = true;
                    reload = 120; shootY = 3; rotateSpeed = 4;
                    shootSound = shootLaser;
                    bullet = new LaserBulletType() {{
                        length = 200; damage = 40;
                        makeFire = true; status = StatusEffects.burning; statusDuration = 60;
                        colors = new Color[]{Color.valueOf("d06b53"), Color.valueOf("d18877"), Color.valueOf("ffa665")};
                    }};
                }}
            );
        }};

        onirion = new UnitType("onirion") {{
            constructor = UnitEntity::create; localizedName = "Onirion"; flying = true; lowAltitude = true;
            engineSize = 0; payloadCapacity = 64;
            health = 5400; armor = 6; hitSize = 23;
            range = 240; speed = 1; strafePenalty = 1;
            weapons.addAll(
                new Weapon() {{
                    x = 0; y = 0; shootY = 0; shootX = 0;
                    mirror = false; shootSound = Sounds.none;
                    rotate = false; alwaysShooting = true; ignoreRotation = true;
                    shootCone = 360; reload = 15;
                    bullet = new BulletType() {{
                        status = FRStatus.mediumDowndraft; statusDuration = 20;
                        instantDisappear = true; knockback = 0.3f; damage = 0;
                        pierceBuilding = true;
                        splashDamage = 11; splashDamageRadius = 40;
                        shootEffect = Fx.none; smokeEffect = Fx.none; hitEffect = Fx.none; despawnEffect = Fx.none;
                        speed = 0;
                    }};
                }},
                new Weapon() {{
                    x = 0; y = -20; mirror = false; rotate = false;
                    reload = 240; ignoreRotation = true;
                    minShootVelocity = 0.1f; shootCone = 180;
                    shootSound = Sounds.none; shootY = 0; inaccuracy = 15;
                    bullet = new BombBulletType() {{
                        splashDamageRadius = 18; splashDamage = 20;
                        width = 36; height = 36;
                        hitEffect = flakExplosion;
                        shootEffect = Fx.none; smokeEffect = Fx.none;
                        status = StatusEffects.blasted; statusDuration = 120;
                        despawnEffect = Fx.none;
                        fragSpread = 90; fragRandomSpread = 0; fragBullets = 4;
                        fragBullet = new ShrapnelBulletType() {{
                            fromColor = Color.valueOf("ffffff00"); toColor = Color.valueOf("ffffff88");
                            length = 40; width = 6; lifetime = 90; damage = 5;
                            despawnEffect = Fx.none; hitEffect = Fx.none;
                        }};
                    }};
                }},
                new Weapon() {{
                    x = 8; y = -5; mirror = true; rotate = false;
                    reload = 180; baseRotation = 90; alternate = false;
                    shootCone = 360; ignoreRotation = true;
                    shootSound = shootArtillery;
                    bullet = new BulletType() {{
                        instantDisappear = true; damage = 0;
                        spawnUnit = FRMissiles.tinyMissile4;
                    }};
                }},
                new Weapon() {{
                    x = 15; y = 4; mirror = true; rotate = false;
                    reload = 12; alternate = false;
                    shootSound = Sounds.shoot;
                    bullet = new BasicBulletType() {{
                        width = 12; height = 16;
                        pierce = true; pierceCap = 4; pierceArmor = true;
                        homingPower = 0.01f;
                        weaveMag = 4; weaveScale = 4;
                        lifetime = 60; speed = 5; damage = 8;
                        trailWidth = 2; trailColor = Color.valueOf("d9dcbc");
                        trailLength = 9; trailChance = -1;
                        hitEffect = flakExplosion;
                    }};
                }},
                new Weapon() {{
                    x = 0; y = 16; mirror = false; rotate = false;
                    reload = 21;
                    shootSound = shootMissile;
                    bullet = new MissileBulletType() {{
                        width = 16; height = 22;
                        pierce = true; pierceCap = 4; pierceArmor = true;
                        lifetime = 90; homingPower = 0.01f;
                        speed = 3; damage = 10;
                        trailWidth = 2.6f; trailColor = Color.valueOf("d9dcbc");
                        trailLength = 11; trailChance = -1;
                        hitEffect = flakExplosion;
                    }};
                }}
            );
            abilities.addAll(
                new StatusFieldAbility(StatusEffects.none, 0f, 15f, 0f) {{
                    effectY = 13; parentizeEffects = true;
                    activeEffect = new ParticleEffect() {{
                        followParent = true; rotWithParent = true;
                        particles = 1; lifetime = 20; length = 0;
                        region = "onirion-rotor";
                        sizeFrom = 24; sizeTo = 24; spin = 16; layer = 95.1f;
                    }};
                }},
                new StatusFieldAbility(StatusEffects.none, 0f, 15f, 0f) {{
                    effectY = -13; parentizeEffects = true;
                    activeEffect = new ParticleEffect() {{
                        followParent = true; rotWithParent = true;
                        particles = 1; lifetime = 20; length = 0;
                        region = "onirion-rotor";
                        sizeFrom = 18; sizeTo = 18; spin = 16; layer = 95.1f;
                    }};
                }}
            );
        }};

        plant = new UnitType("plant") {{
            constructor = PayloadUnit::create; localizedName = "Plant";
            flying = true; lowAltitude = true;
            health = 7100; armor = 8; itemCapacity = 160; speed = 1.9f;
            canHeal = true; drawBuildBeam = true; mineHardnessScaling = true;
            mineSpeed = 4; mineTier = 4; canAttack = false; buildSpeed = 3;
            engineColor = Color.valueOf("84f491"); engineColorInner = Color.valueOf("62ae7f");
            payloadCapacity = 1024; hitSize = 30;
            controller = u -> new RepairAI();
            abilities.addAll(
                new RepairFieldAbility(10f, 60f, 240f) {{
                    activeEffect = new WaveEffect() {{
                        sizeFrom = 0; sizeTo = 120;
                        colorFrom = Color.valueOf("84f491"); colorTo = Color.valueOf("62ae7f");
                    }};
                }},
                new StatusFieldAbility(FRStatus.superHastened, 120f, 180f, 240f) {{
                    activeEffect = new WaveEffect() {{
                        sizeFrom = 0; sizeTo = 60;
                        colorFrom = Color.valueOf("ed9436"); colorTo = Color.valueOf("eb7e3c");
                    }};
                }}
            );
            weapons.addAll(
                new Weapon("repair-laser-weapon") {{
                    x = 18; y = -10; reload = 30; rotate = true;
                    noAttack = true; shootSound = shootLaser;
                    bullet = new LaserBulletType() {{
                        damage = 10; healPercent = 2; length = 260;
                        colors = new Color[]{Color.valueOf("84f491"), Color.white, Color.valueOf("62ae7f")};
                        collidesTeam = true; collidesTiles = true; collidesAir = true;
                    }};
                }},
                new Weapon("heal-laser-mount") {{
                    x = 0; y = 5; mirror = false; rotate = false;
                    reload = 240; continuous = true;
                    shootSound = shootLaser;
                    bullet = new ContinuousLaserBulletType(2f) {{
                        length = 250; width = 2;
                        lifetime = 60; fadeTime = 20;
                        collidesTeam = true; healPercent = 2;
                        damageInterval = 10;
                        status = FRStatus.sapped; statusDuration = 30;
                        colors = new Color[]{Color.valueOf("84f491"), Color.white, Color.valueOf("62ae7f")};
                    }};
                }},
                new BuildWeapon("building-weapon") {{ x = -20; y = -20; }},
                new BuildWeapon("building-weapon") {{ x = 20; y = -20; }}
            );
        }};

        springald = new UnitType("springald") {{
            constructor = TankUnit::create; localizedName = "Springald";
            hitSize = 28; treadPullOffset = 4;
            health = 12000; armor = 20; crushDamage = 3;
            treadRects = new Rect[]{new Rect(27, -67, 26, 134)};
            singleTarget = false; omniMovement = false; faceTarget = false;
            rotateSpeed = 1.5f;
            weapons.addAll(
                new Weapon("springald-weapon") {{
                    x = 0; y = 0; mirror = false; rotate = true;
                    reload = 90; shootY = 18; shake = 5; recoil = 4;
                    rotateSpeed = 1.1f; layerOffset = 0.0001f;
                    shootSound = shootArtillery;
                    heatColor = Color.valueOf("f9350f"); cooldownTime = 110;
                    shadow = 28;
                    bullet = new BasicBulletType() {{
                        width = 10; height = 15; lifetime = 18; hitSize = 7;
                        speed = 8; damage = 126;
                        buildingDamageMultiplier = 2.6f;
                        pierceCap = 3; pierce = true; pierceBuilding = true;
                        hitColor = Color.valueOf("ffa665");
                        backColor = Color.valueOf("ffa665"); trailColor = Color.valueOf("ffa665");
                        frontColor = Color.valueOf("ffffff");
                        trailWidth = 3.3f; trailLength = 9;
                        hitEffect = blastExplosion; despawnEffect = blastExplosion;
                        shootEffect = shootTitan; smokeEffect = shootSmokeTitan;
                        splashDamageRadius = 24; splashDamage = 42;
                        fragOnHit = false;
                        fragRandomSpread = 0; fragSpread = 45;
                        fragBullets = 8; fragVelocityMin = 1; fragVelocityMax = 1;
                        despawnSound = explosionDull;
                        fragBullet = new BasicBulletType() {{
                            sprite = "missile-large";
                            width = 8; height = 12; lifetime = 15; speed = 2;
                            damage = 13; buildingDamageMultiplier = 2.6f;
                            pierce = true; pierceBuilding = true; hitSize = 4;
                            hitColor = Color.valueOf("ffa665"); backColor = Color.valueOf("ffa665");
                            trailColor = Color.valueOf("ffa665"); frontColor = Color.valueOf("ffffff");
                            trailWidth = 2.6f; trailLength = 6;
                            hitEffect = blastExplosion; splashDamageRadius = 16; splashDamage = 10;
                            fragOnHit = false; fragRandomSpread = 0; fragSpread = 0;
                            fragBullets = 1; fragVelocityMin = 0; fragVelocityMax = 0;
                            despawnSound = explosionDull;
                            fragBullet = new ExplosionBulletType() {{
                                killShooter = false;
                                splashDamageRadius = 40; splashDamage = 17;
                                buildingDamageMultiplier = 2.6f;
                                speed = 0; lifetime = 30;
                            }};
                        }};
                    }};
                }},
                new Weapon("springald-side-arm") {{
                    x = 10; y = 8; alternate = true; shootY = 5.5f;
                    reload = 35; recoil = 2; rotate = true; rotateSpeed = 2;
                    shootSound = shootLaser;
                    bullet = new LaserBulletType() {{
                        length = 120; width = 3; damage = 15; lifetime = 40;
                        buildingDamageMultiplier = 2.6f;
                        pierce = true;
                        hitEffect = hitBulletColor; despawnEffect = hitBulletColor;
                    }};
                }},
                new Weapon("springald-side-arm") {{
                    x = 11; y = -9; alternate = true; shootY = 5.5f;
                    reload = 42; recoil = 2; rotate = true; rotateSpeed = 2;
                    shootSound = shootLaser;
                    bullet = new LaserBulletType() {{
                        length = 140; width = 3; damage = 18; lifetime = 40;
                        buildingDamageMultiplier = 2.6f;
                        pierce = true;
                        hitEffect = hitBulletColor; despawnEffect = hitBulletColor;
                    }};
                }}
            );
        }};
    }
}
