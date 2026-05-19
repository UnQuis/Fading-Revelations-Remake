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

public class FRMothershipUnits {
    public static UnitType lycosid, strahl, onager, japonica, hive, hiveAttack,
        toruct, reduct, culiseta, corax, altaic;

    public static void load() {
        toruct = new UnitType("toruct") {{
            constructor = LegsUnit::create; localizedName = "Toruct"; hovering = true;
            speed = 1.5f; rotateSpeed = 4; health = 660; armor = 3; hitSize = 10; range = 80;
            legCount = 6; legLength = 10; legForwardScl = 0.7f; legMoveSpace = 1.2f;
            allowLegStep = true; mechSideSway = 0.25f;
            weapons.add(
                new Weapon("nothing") {{
                    x = 0; y = 0; mirror = false; rotate = true; reload = 60;
                    shake = 2; rotateSpeed = 3; recoil = 1;
                    shootSound = Sounds.shoot; ejectEffect = casing1;
                    bullet = new ShrapnelBulletType() {{
                        damage = 10; width = 2; length = 100;
                        serrationLenScl = 2; serrationSpaceOffset = 70; serrationFadeOffset = 0;
                        serrations = 8; serrationWidth = 7;
                        fromColor = Color.valueOf("c85c51"); toColor = Color.valueOf("ff795e");
                        shootEffect = sparkShoot; smokeEffect = sparkShoot;
                        status = StatusEffects.melting; statusDuration = 30;
                    }};
                }}
            );
        }};

        reduct = new UnitType("reduct") {{
            constructor = LegsUnit::create; localizedName = "Reduct"; hovering = true;
            speed = 1.5f; rotateSpeed = 4; health = 660; armor = 3; hitSize = 10; range = 80;
            legCount = 6; legLength = 10; legForwardScl = 0.7f; legMoveSpace = 1.2f;
            allowLegStep = true; mechSideSway = 0.25f;
            weapons.add(
                new Weapon("nothing") {{
                    x = 0; y = 0; mirror = false; rotate = true; reload = 60;
                    shake = 2; rotateSpeed = 3; recoil = 1;
                    shootSound = Sounds.shoot; ejectEffect = casing1;
                    bullet = new ShrapnelBulletType() {{
                        damage = 10; width = 2; length = 100;
                        serrationLenScl = 2; serrationSpaceOffset = 70; serrationFadeOffset = 0;
                        serrations = 8; serrationWidth = 7;
                        fromColor = Color.valueOf("6586b0"); toColor = Color.valueOf("87ceeb");
                        shootEffect = sparkShoot; smokeEffect = sparkShoot;
                        status = StatusEffects.freezing; statusDuration = 30;
                    }};
                }}
            );
        }};

        hiveAttack = new UnitType("hive-attack") {{
            constructor = UnitEntity::create; localizedName = "Hive Attack"; flying = true; lowAltitude = true;
            speed = 2; health = 1070; outlineColor = Color.valueOf("191919");
            baseRotateSpeed = 0.05f;
            weapons.add(
                new Weapon("nothing") {{
                    x = 0; y = -2; reload = 50; shootCone = 360;
                    shootSound = shootLaser;
                    bullet = new MissileBulletType() {{
                        speed = 10; damage = 50;
                        pierce = true; pierceBuilding = true; pierceCap = 4;
                        homingPower = 0.0003f; homingRange = 25;
                    }};
                }}
            );
        }};

        lycosid = new UnitType("lycosid") {{
            constructor = LegsUnit::create; localizedName = "Lycosid"; hovering = true;
            speed = 0.96f; health = 22000; armor = 26; baseRotateSpeed = 3; hitSize = 56;
            legCount = 8; legMoveSpace = 0.8f; legPairOffset = 3; legLength = 120;
            legExtension = -20; legBaseOffset = 8; stepShake = 8; legLengthScl = 0.93f;
            rippleScale = 8; legSpeed = 0.19f; legSplashDamage = 120; legSplashRange = 72;
            allowLegStep = true; canDrown = false; legContinuousMove = false;
            drownTimeMultiplier = 99999999999f;
            immunities.addAll(StatusEffects.melting, StatusEffects.burning, FRStatus.sapped, StatusEffects.freezing, StatusEffects.wet);
            outlines = false;
            weapons.addAll(
                new Weapon("reclusa-laser") {{
                    x = -28; y = 25; mirror = false; rotate = true;
                    reload = 30; shake = 2; rotateSpeed = 3; recoil = 4;
                    shootSound = Sounds.shootArtillery; ejectEffect = casing1;
                    bullet = new ShrapnelBulletType() {{
                        damage = 100; width = 30; length = 240;
                        serrationLenScl = 8; serrationSpaceOffset = 70; serrationFadeOffset = 0;
                        serrations = 18; serrationWidth = 7;
                        fromColor = Color.valueOf("6586b0"); toColor = Color.valueOf("87ceeb");
                        shootEffect = sparkShoot; smokeEffect = sparkShoot;
                        status = StatusEffects.freezing; statusDuration = 30;
                    }};
                }},
                new Weapon("reclusa-laser") {{
                    x = 28; y = 25; mirror = false; rotate = true;
                    reload = 30; shake = 2; rotateSpeed = 3; recoil = 4;
                    shootSound = Sounds.shootArtillery; ejectEffect = casing1;
                    bullet = new ShrapnelBulletType() {{
                        damage = 100; width = 30; length = 240;
                        serrationLenScl = 8; serrationSpaceOffset = 70; serrationFadeOffset = 0;
                        serrations = 18; serrationWidth = 7;
                        fromColor = Color.valueOf("c85c51"); toColor = Color.valueOf("ff795e");
                        shootEffect = sparkShoot; smokeEffect = sparkShoot;
                        status = StatusEffects.melting; statusDuration = 30;
                    }};
                }},
                new Weapon("reclusa-main") {{
                    x = 0; y = -10; mirror = false; rotate = true;
                    reload = 300; shake = 8; recoil = 12; rotateSpeed = 2; rotationLimit = 90;
                    shootSound = shootArtillery; ejectEffect = casing3;
                    bullet = new ArtilleryBulletType() {{
                        spin = 2; damage = 50; lifetime = 240; width = 50; height = 50;
                        splashDamage = 65; splashDamageRadius = 80; knockback = 1;
                        sprite = "lycosid-bullet";
                        backColor = Color.valueOf("665c9f"); frontColor = Color.valueOf("bf92f9");
                        lightning = 6; lightningLength = 25; lightningDamage = 35;
                        lightningColor = Color.valueOf("bf92f9");
                        smokeEffect = shootBigSmoke2; hitShake = 13;
                        lightRadius = 50; lightColor = Color.valueOf("bf92f9"); lightOpacity = 0.6f;
                        status = FRStatus.sapped; statusDuration = 60;
                        hitEffect = new MultiEffect(
                            new ParticleEffect() {{
                                particles = 8; length = 20; sizeFrom = 8; sizeTo = 4;
                                colorFrom = Color.valueOf("bf92f9"); colorTo = Color.valueOf("665c9f");
                                interp = Interp.circleIn;
                            }},
                            new WaveEffect() {{
                                sizeFrom = 14; sizeTo = 0;
                                colorFrom = Color.valueOf("bf92f9"); colorTo = Color.valueOf("665c9f");
                            }}
                        );
                        fragLifeMin = 0.4f; fragBullets = 9;
                        fragBullet = new ArtilleryBulletType() {{
                            damage = 40; lifetime = 140; width = 25; height = 25;
                            knockback = 1; collidesTiles = false;
                            splashDamage = 80; splashDamageRadius = 90;
                            sprite = "lycosid-bullet";
                            backColor = Color.valueOf("665c9f"); frontColor = Color.valueOf("bf92f9");
                            lightning = 4; lightningLength = 7;
                            lightningColor = Color.valueOf("bf92f9");
                            smokeEffect = shootBigSmoke2; hitShake = 4;
                            hitEffect = sapExplosion;
                            lightRadius = 40; lightColor = Color.valueOf("bf92f9"); lightOpacity = 0.5f;
                            status = FRStatus.sapped; statusDuration = 60;
                        }};
                    }};
                }}
            );
            abilities.addAll(
                new UnitSpawnAbility(reduct, -20, -48, 200),
                new UnitSpawnAbility(toruct, 20, -48, 200)
            );
        }};

        strahl = new UnitType("sps-strahl") {{
            constructor = UnitEntity::create; localizedName = "Strahl"; flying = true; lowAltitude = true;
            speed = 0.96f; drag = 0.15f; health = 11000; armor = 24; hitSize = 90;
            baseRotateSpeed = 0.06f; outlineColor = Color.valueOf("2a2a2a");
            immunities.addAll(StatusEffects.melting, StatusEffects.burning, FRStatus.sapped, StatusEffects.freezing, StatusEffects.wet);
            weapons.addAll(
                new Weapon("sps-air-fr") {{
                    x = -56; y = 16; mirror = false; rotate = true; reload = 30;
                    shootSound = shootLaser;
                    bullet = new LaserBulletType() {{
                        damage = 100; width = 6; length = 200;
                        colors = new Color[]{Color.valueOf("6586b0"), Color.valueOf("87ceeb"), Color.valueOf("c0ecff")};
                    }};
                }},
                new Weapon("sps-air-fr") {{
                    x = 56; y = 16; mirror = false; rotate = true; reload = 30;
                    shootSound = shootLaser;
                    bullet = new LaserBulletType() {{
                        damage = 100; width = 6; length = 200;
                        colors = new Color[]{Color.valueOf("6586b0"), Color.valueOf("87ceeb"), Color.valueOf("c0ecff")};
                    }};
                }},
                new Weapon("sps-lancer-left") {{
                    x = -32; y = -12; rotate = true; continuous = true;
                    reload = 200; shootSound = shootLaser;
                    bullet = new ContinuousLaserBulletType(25f) {{
                        length = 250; width = 2; lifetime = 120; fadeTime = 30;
                        colors = new Color[]{Color.valueOf("6586b0"), Color.valueOf("87ceeb"), Color.valueOf("c0ecff")};
                        status = StatusEffects.freezing; statusDuration = 30;
                    }};
                }},
                new Weapon("sps-lancer-left") {{
                    x = 32; y = -12; rotate = true; continuous = true;
                    reload = 240; recoil = 4; recoilTime = 180; shootSound = shootLaser;
                    bullet = new ContinuousLaserBulletType(25f) {{
                        length = 250; width = 2; lifetime = 120; fadeTime = 30;
                        colors = new Color[]{Color.valueOf("6586b0"), Color.valueOf("87ceeb"), Color.valueOf("c0ecff")};
                        status = StatusEffects.freezing; statusDuration = 30;
                    }};
                }},
                new Weapon("sps-rail") {{
                    x = 0; y = -2; mirror = false; continuous = true;
                    recoil = 8; recoilTime = 180; reload = 500;
                    shootSound = shootLaser;
                    bullet = new ContinuousLaserBulletType(30f) {{
                        width = 6; lifetime = 280; fadeTime = 25; length = 350;
                        colors = new Color[]{Color.valueOf("6586b0"), Color.valueOf("87ceeb"), Color.valueOf("c0ecff")};
                        status = StatusEffects.freezing; statusDuration = 30;
                    }};
                }}
            );
            abilities.addAll(
                new UnitSpawnAbility(FRT2Units.alopex, 0, -30, 240),
                new ForceFieldAbility(110f, 4f, 9000f, 600f)
            );
        }};

        onager = new UnitType("onager") {{
            constructor = TankUnit::create; localizedName = "Onager";
            speed = 0.36f; health = 23000; armor = 26; hitSize = 92;
            rotateSpeed = 0.9f; crushDamage = 5;
            treadRects = new Rect[]{new Rect(60, -200, 128, 420)};
            omniMovement = false; singleTarget = false; faceTarget = false;
            weapons.addAll(
                new Weapon("onager-weapon") {{
                    x = 0; y = -4; mirror = false; rotate = true;
                    shootY = 64; reload = 300; shake = 6; recoil = 6;
                    rotateSpeed = 0.72f; layerOffset = 0.1f; shadow = 50;
                    heatColor = Color.valueOf("f9350f"); cooldownTime = 340; minWarmup = 0.9f;
                    shootSound = shootArtillery;
                    bullet = new BasicBulletType() {{
                        width = 42; height = 42; hitSize = 32;
                        speed = 3; lifetime = 90;
                        shootEffect = shootTitan; smokeEffect = shootSmokeTitan;
                        sprite = "large-orb";
                        frontColor = Color.valueOf("ffffff"); backColor = Color.valueOf("d06b53");
                        trailColor = Color.valueOf("d06b53"); hitColor = Color.valueOf("d06b53");
                        pierce = true; pierceBuilding = true;
                        trailWidth = 6; trailLength = 28;
                        hitEffect = blastExplosion; despawnEffect = blastExplosion;
                        splashDamage = 64; splashDamageRadius = 64; damage = 192;
                        buildingDamageMultiplier = 2;
                        fragOnHit = false; fragRandomSpread = 0; fragSpread = 90;
                        fragBullets = 4; fragVelocityMin = 1; fragVelocityMax = 1;
                        despawnSound = explosionDull;
                        fragBullet = new BasicBulletType() {{
                            sprite = "missile-large"; width = 16; height = 24;
                            lifetime = 20; speed = 2; damage = 20;
                            buildingDamageMultiplier = 2;
                            pierce = true; pierceBuilding = true; hitSize = 8;
                            hitColor = Color.valueOf("d06b53"); backColor = Color.valueOf("d06b53");
                            frontColor = Color.valueOf("ffffff"); trailColor = Color.valueOf("d06b53");
                            trailWidth = 3.2f; trailLength = 8;
                            hitEffect = blastExplosion;
                            splashDamageRadius = 24; splashDamage = 44;
                            fragBullets = 8; fragVelocityMin = 1; fragVelocityMax = 1;
                            fragRandomSpread = 0; fragSpread = 45;
                            fragBullet = new BasicBulletType() {{
                                sprite = "missile-large"; width = 16; height = 24;
                                lifetime = 20; speed = 2; damage = 20;
                                buildingDamageMultiplier = 2;
                                pierce = true; pierceBuilding = true; hitSize = 8;
                                hitColor = Color.valueOf("d06b53"); backColor = Color.valueOf("d06b53");
                                frontColor = Color.valueOf("ffffff"); trailColor = Color.valueOf("d06b53");
                                trailWidth = 3.2f; trailLength = 8;
                                hitEffect = blastExplosion;
                                splashDamageRadius = 24; splashDamage = 20;
                            }};
                        }};
                    }};
                }},
                new Weapon("springald-side-arm") {{
                    x = 35; y = 25; mirror = true; alternate = true;
                    reload = 60; rotate = true; rotateSpeed = 2.2f;
                    shootSound = Sounds.shoot;
                    shoot = new ShootPattern() {{ shots = 3; shotDelay = 3; }};
                    bullet = new BasicBulletType() {{
                        width = 10; height = 16; speed = 4; lifetime = 60; damage = 36;
                        buildingDamageMultiplier = 2;
                        splashDamage = 8; splashDamageRadius = 16;
                        pierce = true; pierceCap = 3; pierceBuilding = true;
                        shootEffect = shootSmall; smokeEffect = shootSmallSmoke;
                    }};
                }},
                new Weapon("springald-side-arm") {{
                    x = 35; y = -35; mirror = true; alternate = true;
                    reload = 72; rotate = true; rotateSpeed = 2.2f;
                    shootSound = Sounds.shoot;
                    shoot = new ShootPattern() {{ shots = 3; shotDelay = 4; }};
                    bullet = new BasicBulletType() {{
                        width = 10; height = 16; speed = 4; lifetime = 90; damage = 36;
                        buildingDamageMultiplier = 2;
                        splashDamage = 8; splashDamageRadius = 16;
                        pierce = true; pierceCap = 3; pierceBuilding = true;
                        shootEffect = shootSmall; smokeEffect = shootSmallSmoke;
                    }};
                }},
                new Weapon("scorpio-weapon") {{
                    x = 35; y = -1; mirror = true; alternate = true;
                    reload = 134; rotate = true; rotateSpeed = 1.4f; inaccuracy = 7;
                    shootSound = shootArtillery;
                    bullet = new BasicBulletType() {{
                        width = 16; height = 16; speed = 5; lifetime = 60; damage = 72;
                        buildingDamageMultiplier = 2;
                        pierceCap = 3;
                        backColor = Color.valueOf("d06b53"); frontColor = Color.valueOf("ffffff");
                        trailColor = Color.valueOf("d06b53"); hitColor = Color.valueOf("d06b53");
                        shrinkX = 0; shrinkY = 0;
                        trailLength = 12; trailWidth = 2.2f;
                        trailEffect = missileTrail; trailInterval = 3; trailParam = 4;
                        fragOnHit = false;
                        shootEffect = shootTitan; smokeEffect = shootSmokeTitan;
                        sprite = "large-orb";
                        hitEffect = new ExplosionEffect() {{
                            waveColor = Color.valueOf("d06b53"); smokeColor = Color.valueOf("6e7080");
                            sparkColor = Color.valueOf("7e6cba"); waveStroke = 4; waveRad = 40;
                        }};
                        despawnEffect = new ExplosionEffect() {{
                            waveColor = Color.valueOf("d06b53"); smokeColor = Color.valueOf("6e7080");
                            sparkColor = Color.valueOf("7e6cba"); waveStroke = 4; waveRad = 40;
                        }};
                        despawnSound = explosionDull;
                        fragBullets = 10; fragVelocityMin = 0.5f; fragVelocityMax = 1.5f; fragLifeMin = 0.5f;
                        fragBullet = new BasicBulletType() {{
                            speed = 2; damage = 20; lifetime = 21; width = 8; height = 12; hitSize = 4;
                            buildingDamageMultiplier = 2;
                            pierce = true; pierceBuilding = true;
                            hitColor = Color.valueOf("d06b53"); backColor = Color.valueOf("d06b53");
                            trailColor = Color.valueOf("d06b53"); frontColor = Color.valueOf("ffffff");
                            trailWidth = 1.9f; trailLength = 4;
                            homingPower = 0.2f;
                            hitEffect = new WaveEffect() {{
                                sizeTo = 4; strokeFrom = 4; lifetime = 10;
                                colorFrom = Color.valueOf("d06b53"); colorTo = Color.valueOf("d06b53");
                            }};
                            despawnEffect = new WaveEffect() {{
                                sizeTo = 4; strokeFrom = 4; lifetime = 10;
                                colorFrom = Color.valueOf("d06b53"); colorTo = Color.valueOf("d06b53");
                            }};
                        }};
                        intervalBullet = new BasicBulletType() {{
                            speed = 2; damage = 20; lifetime = 21; width = 8; height = 12; hitSize = 4;
                            buildingDamageMultiplier = 2;
                            pierce = true; pierceBuilding = true;
                            hitColor = Color.valueOf("d06b53"); backColor = Color.valueOf("d06b53");
                            trailColor = Color.valueOf("d06b53"); frontColor = Color.valueOf("ffffff");
                            trailWidth = 1.9f; trailLength = 4;
                            homingPower = 0.2f;
                            hitEffect = new WaveEffect() {{
                                sizeTo = 4; strokeFrom = 4; lifetime = 10;
                                colorFrom = Color.valueOf("d06b53"); colorTo = Color.valueOf("d06b53");
                            }};
                            despawnEffect = new WaveEffect() {{
                                sizeTo = 4; strokeFrom = 4; lifetime = 10;
                                colorFrom = Color.valueOf("d06b53"); colorTo = Color.valueOf("d06b53");
                            }};
                        }};
                        bulletInterval = 3; intervalRandomSpread = 20;
                        intervalBullets = 2; intervalAngle = 180; intervalSpread = 300;
                    }};
                }}
            );
        }};

        japonica = new UnitType("japonica") {{
            constructor = UnitWaterMove::create; localizedName = "Japonica";
            health = 20000; armor = 10; speed = 0.96f; hitSize = 93;
            drag = 0.2f; rotateSpeed = 1.2f; accel = 0.18f;
            faceTarget = false;
            trailLength = 320; waveTrailX = 26; waveTrailY = -35; trailScl = 3.7f;
            immunities.addAll(StatusEffects.melting, StatusEffects.burning, FRStatus.sapped, StatusEffects.freezing, StatusEffects.wet);
            weapons.addAll(
                new Weapon("japonica-main") {{
                    x = 0; y = -40; mirror = false; rotate = true;
                    shootY = 23; reload = 440; shake = 6; recoil = 0.5f;
                    rotateSpeed = 0.9f; shootCone = 1; minWarmup = 0.94f;
                    shootWarmupSpeed = 0.03f;
                    shootSound = shootMissile;
                    bullet = new BasicBulletType() {{
                        speed = 0; damage = 0;
                        shootEffect = shootBig; smokeEffect = shootSmokeMissile;
                        spawnUnit = null;
                    }};
                }},
                new Weapon("japonica-lancer") {{
                    x = 0; y = 72; mirror = false; rotate = true;
                    shootY = 8; reload = 220; rotateSpeed = 2;
                    continuous = true;
                    shootSound = shootLaser;
                    bullet = new ContinuousLaserBulletType(65f) {{
                        length = 400; width = 4; lifetime = 90; fadeTime = 30;
                    }};
                }},
                new Weapon("japonica-lancer") {{
                    x = 0; y = 10; mirror = false; rotate = true;
                    shootY = 8; reload = 220; rotateSpeed = 2;
                    continuous = true;
                    shootSound = shootLaser;
                    bullet = new ContinuousLaserBulletType(65f) {{
                        length = 450; width = 4; lifetime = 90; fadeTime = 30;
                    }};
                }},
                new Weapon("japonica-air") {{
                    x = -42; y = -20; mirror = true; rotate = true;
                    reload = 60; rotateSpeed = 3;
                    shootSound = shootArtillery;
                    shoot = new ShootPattern() {{ shots = 3; shotDelay = 10; }};
                    bullet = new BasicBulletType() {{
                        damage = 35; width = 7; height = 13; speed = 5; lifetime = 60;
                        splashDamage = 20; splashDamageRadius = 22;
                        lightning = 4; lightningLength = 2; lightningDamage = 8;
                        collidesAir = true; collidesGround = false;
                        homingPower = 0.09f; homingRange = 320;
                        sprite = "shell";
                        frontColor = Color.valueOf("f8ae4b"); backColor = Color.valueOf("e9901a");
                        status = StatusEffects.burning; statusDuration = 180;
                        shootEffect = shootBig; smokeEffect = shootSmallSmoke;
                    }};
                }},
                new Weapon("japonica-air") {{
                    x = 42; y = 48; mirror = true; rotate = true;
                    reload = 60; rotateSpeed = 3;
                    shootSound = shootArtillery;
                    shoot = new ShootPattern() {{ shots = 3; shotDelay = 10; }};
                    bullet = new BasicBulletType() {{
                        damage = 35; width = 7; height = 13; speed = 5; lifetime = 60;
                        splashDamage = 20; splashDamageRadius = 22;
                        lightning = 4; lightningLength = 2; lightningDamage = 8;
                        collidesAir = true; collidesGround = false;
                        homingPower = 0.09f; homingRange = 320;
                        sprite = "shell";
                        frontColor = Color.valueOf("f8ae4b"); backColor = Color.valueOf("e9901a");
                        status = StatusEffects.burning; statusDuration = 180;
                        shootEffect = shootBig; smokeEffect = shootSmallSmoke;
                    }};
                }}
            );
            abilities.addAll(
                new EnergyFieldAbility(3f, 10f, 180f) {{
                    color = Color.valueOf("d06b53");
                    targetAir = true; targetGround = true;
                    hitBuildings = false; hitUnits = true;
                    healPercent = 2; maxTargets = 12;
                    rotateSpeed = 0.2f; sectors = 3;
                    shootSound = shockBullet;
                    status = FRStatus.japonicaWeakened; statusDuration = 60;
                    x = 0; y = 40;
                }}
                //TODO: UnitSpawnAbility for "risso" — not yet converted to Java
            );
        }};

        hive = new UnitType("hive") {{
            constructor = UnitEntity::create; localizedName = "Hive"; flying = true; lowAltitude = true;
            speed = 0.96f; drag = 0.15f; health = 9100; armor = 20; hitSize = 85;
            baseRotateSpeed = 0.06f; aimDst = 600;
            createWreck = true; outlineColor = Color.valueOf("191919");
            immunities.addAll(StatusEffects.melting, StatusEffects.burning, FRStatus.sapped, StatusEffects.freezing, StatusEffects.wet);
            parts.addAll(
                new HaloPart() {{
                    x = 0; y = 4; mirror = false;
                    color = Color.valueOf("ff3232"); colorTo = Color.valueOf("c80000");
                    hollow = true; shapes = 8; sides = 10; radius = 13;
                    stroke = 2; haloRotateSpeed = 1;
                }},
                new HaloPart() {{
                    x = 0; y = 4; mirror = false;
                    color = Color.valueOf("ff3232"); colorTo = Color.valueOf("c80000");
                    hollow = true; shapes = 8; tri = true; stroke = 3;
                    haloRadius = 22; triLength = 15; triLengthTo = 15; haloRotateSpeed = -1;
                }}
            );
            weapons.addAll(
                new Weapon("hive-nuke") {{
                    x = 0; y = 0; mirror = false; alternate = false;
                    shootY = 50; reload = 600; cooldownTime = 400;
                    shake = 7; recoil = 0; rotate = false;
                    continuous = true; chargeSound = Sounds.chargeLancer;
                    shoot = new ShootPattern() {{ firstShotDelay = 120; }};
                    shootSound = shootLaser;
                    bullet = new ContinuousLaserBulletType(75f) {{
                        length = 500; width = 16; lifetime = 280; fadeTime = 60;
                        makeFire = false;
                        status = StatusEffects.melting; statusDuration = 600;
                        hitEffect = new MultiEffect(
                            new WaveEffect() {{ sizeFrom = 32; sizeTo = 22; colorFrom = Color.valueOf("ec7458aa"); colorTo = Color.valueOf("ff9c5a"); lifetime = 120; }},
                            new WaveEffect() {{ sizeFrom = 22; sizeTo = 32; colorFrom = Color.valueOf("ec7458aa"); colorTo = Color.valueOf("ff9c5a"); lifetime = 120; }},
                            new ParticleEffect() {{ particles = 8; length = 35; colorFrom = Color.valueOf("ec7458aa"); colorTo = Color.valueOf("ff9c5a"); lifetime = 420; }},
                            new ParticleEffect() {{ particles = 8; length = 35; colorFrom = Color.valueOf("ec7458aa"); colorTo = Color.valueOf("ff9c5a"); lifetime = 420; }}
                        );
                    }};
                }},
                new Weapon("hive-mg") {{
                    x = 20; y = -20; mirror = false; rotate = true;
                    reload = 36; shake = 1; rotateSpeed = 20;
                    shootSound = shootLaser;
                    bullet = new LaserBulletType() {{
                        damage = 108; length = 460;
                        status = StatusEffects.burning; statusDuration = 60;
                        colors = new Color[]{Color.valueOf("ec7458aa"), Color.valueOf("ff9c5a"), Color.valueOf("ffffff")};
                    }};
                }},
                new Weapon("hive-mg") {{
                    x = -20; y = -20; mirror = false; rotate = true;
                    reload = 26; shake = 1; rotateSpeed = 20;
                    shootSound = shootLaser;
                    bullet = new LaserBulletType() {{
                        damage = 92; length = 460;
                        status = StatusEffects.burning; statusDuration = 60;
                        colors = new Color[]{Color.valueOf("ec7458aa"), Color.valueOf("ff9c5a"), Color.valueOf("ffffff")};
                    }};
                }},
                new Weapon("hive-laser") {{
                    x = -40; y = 0; mirror = false; rotate = true;
                    shootY = 23; reload = 257; shake = 4; recoil = 3;
                    rotateSpeed = 20; continuous = true;
                    shootSound = shootSpectre;
                    bullet = new PointBulletType() {{
                        speed = 12; lifetime = 30; maxRange = 400;
                        damage = 740; splashDamage = 740; splashDamageRadius = 24;
                        trailEffect = instTrail; hitEffect = massiveExplosion;
                        shootEffect = instShoot;
                        pierce = true; pierceBuilding = true;
                    }};
                }},
                new Weapon("hive-laser") {{
                    x = 40; y = 0; mirror = false; rotate = true;
                    shootY = 23; reload = 221; shake = 4; recoil = 3;
                    rotateSpeed = 20; continuous = true;
                    shootSound = shootSpectre;
                    bullet = new PointBulletType() {{
                        speed = 12; lifetime = 30; maxRange = 400;
                        damage = 720; splashDamage = 720; splashDamageRadius = 24;
                        trailEffect = instTrail; hitEffect = massiveExplosion;
                        shootEffect = instShoot;
                        pierce = true; pierceBuilding = true;
                    }};
                }}
            );
            abilities.addAll(
                new UnitSpawnAbility(FRMothershipUnits.hiveAttack, 0, -16, 400),
                new ForceFieldAbility(115f, 5f, 20900f, 600f)
            );
        }};

        culiseta = new UnitType("culiseta") {{
            constructor = PayloadUnit::create; localizedName = "Culiseta"; flying = true; lowAltitude = true;
            health = 16500; armor = 3; speed = 1; hitSize = 64;
            range = 270; strafePenalty = 1; payloadCapacity = 256; engineSize = 0;
            weapons.addAll(
                new Weapon() {{
                    x = 0; y = 3; shootY = 0; shootX = 0;
                    mirror = false; rotate = false; shootCone = 360;
                    alwaysShooting = true; ignoreRotation = true;
                    reload = 15; recoil = 0;
                    shootSound = Sounds.none;
                    bullet = new BulletType() {{
                        status = FRStatus.powerfulDowndraft; statusDuration = 20;
                        instantDisappear = true; damage = 0;
                        splashDamage = 15; splashDamageRadius = 88;
                        pierceBuilding = true; knockback = 0.4f;
                        shootEffect = new ParticleEffect() {{
                            followParent = true; rotWithParent = true;
                            particles = 1; lifetime = 20; length = 0;
                            region = "fading-revelations-patched-culiseta-rotor";
                            sizeFrom = 120; sizeTo = 120; spin = 16; layer = 95.1f;
                        }};
                        smokeEffect = Fx.none; hitEffect = Fx.none; despawnEffect = Fx.none;
                        speed = 0;
                    }};
                }},
                new Weapon() {{
                    x = 0; y = 0; mirror = false; rotate = false;
                    reload = 153; shootSound = shootMissile;
                    shoot = new ShootSpread(4, 20) {{ shotDelay = 6; }};
                    bullet = new BulletType() {{
                        instantDisappear = true; damage = 0;
                        spawnUnit = FRMissiles.tinyMissile4;
                    }};
                }},
                new Weapon() {{
                    x = 0; y = 45; mirror = false; rotate = false;
                    shootCone = 60; reload = 180;
                    shootSound = shootArtillery;
                    bullet = new BasicBulletType() {{
                        speed = 3; lifetime = 90; width = 32; height = 32;
                        sprite = "large-orb"; hitSize = 32;
                        pierce = true; pierceBuilding = true; damage = 425;
                        knockback = -10;
                        lightning = 4; lightningLength = 9; lightningDamage = 20;
                        shootEffect = shootTitan; smokeEffect = shootSmokeTitan;
                        hitEffect = blastExplosion;
                    }};
                }},
                new Weapon() {{
                    x = 35; y = 20; mirror = true; alternate = false;
                    rotate = false; reload = 10; inaccuracy = 5;
                    bullet = new BasicBulletType() {{
                        speed = 3; lifetime = 80; width = 10; height = 14; damage = 40;
                        pierce = true; pierceBuilding = true; pierceCap = 5;
                        buildingDamageMultiplier = 0.5f;
                        lightning = 1; lightningLength = 4; lightningDamage = 4;
                        shootEffect = shootSmall; hitEffect = flakExplosion;
                        despawnEffect = flakExplosion; smokeEffect = shootSmallSmoke;
                    }};
                }},
                new Weapon() {{
                    x = 55; y = 5; mirror = true; alternate = true;
                    rotate = false; reload = 120; shootCone = 32; inaccuracy = 3;
                    shootSound = shootLaser;
                    bullet = new LaserBulletType() {{
                        length = 200; damage = 350; lifetime = 90;
                        knockback = -2;
                    }};
                }},
                new Weapon() {{
                    x = 16; y = 42; mirror = true; alternate = true;
                    rotate = false; reload = 12; shootCone = 40; inaccuracy = 4;
                    shootSound = shootMissile;
                    bullet = new MissileBulletType() {{
                        speed = 4; lifetime = 75; width = 10; height = 14; damage = 65;
                        pierce = true; pierceCap = 5; pierceBuilding = true;
                        buildingDamageMultiplier = 0.5f;
                        lightning = 1; lightningLength = 4; lightningDamage = 10;
                        shootEffect = shootSmall; hitEffect = flakExplosion;
                        despawnEffect = flakExplosion; smokeEffect = shootSmallSmoke;
                    }};
                }},
                new Weapon() {{
                    x = 0; y = -40; mirror = false; rotate = false;
                    shootCone = 360; ignoreRotation = true;
                    reload = 240; minShootVelocity = 0.1f;
                    shootSound = shootArtillery;
                    bullet = new BombBulletType() {{
                        splashDamage = 375; splashDamageRadius = 32;
                        width = 48; height = 48;
                        hitEffect = massiveExplosion; shootEffect = Fx.none;
                        smokeEffect = Fx.none; despawnEffect = Fx.none;
                        status = StatusEffects.blasted; statusDuration = 120;
                        fragSpread = 45; fragLifeMin = 1; fragRandomSpread = 0; fragBullets = 8;
                        fragBullet = new MissileBulletType() {{
                            width = 16; height = 16; speed = 2; lifetime = 30; damage = 125;
                            pierce = true; pierceBuilding = true; pierceCap = 1;
                            fragBullets = 4; fragLifeMin = 1; fragRandomSpread = 0; fragSpread = 90;
                            hitEffect = Fx.none;
                            fragBullet = new MissileBulletType() {{
                                width = 12; height = 12; speed = 2; lifetime = 20; damage = 85;
                                pierce = true; pierceBuilding = true; pierceCap = 1;
                                hitEffect = Fx.none;
                                fragBullets = 1; fragLifeMin = 1; fragRandomSpread = 0; fragAngle = 90;
                                fragBullet = new ExplosionBulletType() {{
                                    killShooter = false;
                                    splashDamage = 170; splashDamageRadius = 24;
                                }};
                            }};
                        }};
                    }};
                }},
                new Weapon() {{
                    x = 15; y = -10; mirror = true; rotate = false;
                    reload = 180; baseRotation = 90; ignoreRotation = true;
                    alternate = false; shootCone = 180;
                    shootSound = shootArtillery;
                    bullet = new ArtilleryBulletType() {{
                        collidesAir = false; speed = 2; lifetime = 60;
                        damage = 127; width = 16; height = 16;
                        splashDamage = 34; splashDamageRadius = 24;
                        pierce = false;
                        fragBullets = 16; fragSpread = 22.5f; fragRandomSpread = 0; fragLifeMin = 1;
                        hitEffect = sapExplosion; despawnHit = true;
                        fragBullet = new MissileBulletType() {{
                            speed = 2; lifetime = 15; damage = 65;
                            hitEffect = flakExplosion; despawnEffect = Fx.none;
                        }};
                    }};
                }},
                new Weapon() {{
                    x = 15; y = -20; mirror = true; rotate = false;
                    reload = 185; baseRotation = 90; ignoreRotation = true;
                    alternate = false; shootCone = 180;
                    shootSound = shootArtillery;
                    bullet = new ArtilleryBulletType() {{
                        collidesAir = false; speed = 2; lifetime = 60;
                        damage = 136; width = 16; height = 16;
                        splashDamage = 34; splashDamageRadius = 16;
                        pierce = false;
                        fragBullets = 16; fragSpread = 22.5f; fragRandomSpread = 0; fragLifeMin = 1;
                        hitEffect = sapExplosion; despawnHit = true;
                        fragBullet = new MissileBulletType() {{
                            speed = 2; lifetime = 15; damage = 65;
                            hitEffect = flakExplosion; despawnEffect = Fx.none;
                        }};
                    }};
                }},
                new Weapon() {{
                    x = 18; y = -30; mirror = true; rotate = false;
                    reload = 190; baseRotation = 90; ignoreRotation = true;
                    alternate = false; shootCone = 180;
                    shootSound = shootArtillery;
                    bullet = new ArtilleryBulletType() {{
                        collidesAir = false; speed = 2; lifetime = 60;
                        damage = 102; width = 16; height = 16;
                        splashDamage = 34; splashDamageRadius = 16;
                        pierce = false;
                        fragBullets = 16; fragSpread = 22.5f; fragRandomSpread = 0; fragLifeMin = 1;
                        hitEffect = sapExplosion; despawnHit = true;
                        fragBullet = new MissileBulletType() {{
                            speed = 2; lifetime = 15; damage = 50;
                            hitEffect = flakExplosion; despawnEffect = Fx.none;
                        }};
                    }};
                }},
                new PointDefenseWeapon() {{
                    x = 28; y = -56; mirror = true; alternate = true;
                    rotate = false; shootCone = 360; reload = 10;
                    targetInterval = 6; targetSwitchInterval = 8;
                    bullet = new BulletType() {{
                        shootEffect = sparkShoot;
                        maxRange = 160; damage = 15;
                    }};
                }}
            );
            abilities.addAll(
                new SpawnDeathAbility(FRT1Units.sambuca, 4, 90) {{ faceOutwards = true; }},
                new SpawnDeathAbility(UnitTypes.dagger, 8, 45) {{ faceOutwards = true; }}
            );
        }};

        corax = new UnitType("corax") {{
            constructor = LegsUnit::create; localizedName = "Corax"; hovering = true;
            speed = 0.45f; health = 18000; armor = 14; hitSize = 52;
            rotateSpeed = 0.48f;
            legCount = 6; legMoveSpace = 0.8f; legPairOffset = 2; legLength = 80;
            legExtension = -20; legBaseOffset = 8; stepShake = 4; legLengthScl = 0.93f;
            rippleScale = 6; legSpeed = 0.31f; legSplashDamage = 75; legSplashRange = 48;
            allowLegStep = true; canDrown = false; shadowElevation = 0.2f;
            legContinuousMove = false; drownTimeMultiplier = 99999999999f;
            drawShields = false;
            immunities.addAll(StatusEffects.melting, StatusEffects.burning, FRStatus.sapped, StatusEffects.freezing, StatusEffects.wet);
            weapons.addAll(
                new Weapon("heal-laser-mount") {{
                    x = -25; y = 8; mirror = false;
                    reload = 180; recoil = 2; shake = 2;
                    top = false; predictTarget = true;
                    shoot = new ShootPattern() {{ firstShotDelay = 90; }};
                    shootSound = shootLaser; chargeSound = Sounds.chargeLancer;
                    soundPitchMin = 1.1f; continuous = true;
                    bullet = new LaserBulletType() {{
                        width = 30; damage = 90; length = 200; lifetime = 100;
                        largeHit = true;
                        lightColor = Color.valueOf("92e02f"); lightningColor = Color.valueOf("92e02f");
                        healPercent = 30; collidesTeam = true;
                        colors = new Color[]{Color.valueOf("6fa629"), Color.valueOf("92e02f"), Color.valueOf("ffffff")};
                    }};
                }},
                new Weapon("heal-laser-mount") {{
                    x = 25; y = 8; mirror = false;
                    reload = 240; recoil = 2; shake = 2;
                    top = false; predictTarget = true;
                    shoot = new ShootPattern() {{ firstShotDelay = 90; }};
                    shootSound = shootLaser; chargeSound = Sounds.chargeLancer;
                    soundPitchMin = 1.1f; continuous = true;
                    bullet = new LaserBulletType() {{
                        width = 30; damage = 90; length = 200; lifetime = 100;
                        largeHit = true;
                        lightColor = Color.valueOf("92e02f"); lightningColor = Color.valueOf("92e02f");
                        healPercent = 30; collidesTeam = true;
                        colors = new Color[]{Color.valueOf("6fa629"), Color.valueOf("92e02f"), Color.valueOf("ffffff")};
                    }};
                }},
                new Weapon("nothing") {{
                    x = 0; y = 20; mirror = false;
                    predictTarget = true; top = false;
                    reload = 480; recoil = 0; shake = 16;
                    cooldownTime = 480;
                    shootSound = shootLaser; chargeSound = Sounds.chargeLancer;
                    soundPitchMin = 1;
                    shoot = new ShootPattern() {{ firstShotDelay = 90; }};
                    shootStatus = StatusEffects.unmoving; shootStatusDuration = 180;
                    parentizeEffects = true;
                    bullet = new LaserBulletType() {{
                        length = 580; damage = 920; width = 80; lifetime = 100;
                        lightningSpacing = 35; lightningLength = 12; lightningDelay = 1.1f;
                        lightningLengthRand = 15; lightningDamage = 60; lightningAngleRand = 40;
                        largeHit = true;
                        lightColor = Color.valueOf("92e02f"); lightningColor = Color.valueOf("92e02f");
                        healPercent = 30; collidesTeam = true;
                        sideAngle = 15; sideWidth = 0; sideLength = 0;
                        colors = new Color[]{Color.valueOf("6fa629"), Color.valueOf("92e02f"), Color.valueOf("ffffff")};
                        chargeEffect = Fx.greenLaserCharge;
                    }};
                }}
            );
        }};

        altaic = new UnitType("altaic") {{
            constructor = UnitWaterMove::create; localizedName = "Altaic";
            health = 21000; armor = 16; speed = 0.75f; hitSize = 61;
            drag = 0.17f; accel = 0.25f; rotateSpeed = 1.6f;
            faceTarget = false; buildSpeed = 3.8f;
            trailLength = 85; trailScl = 5; waveTrailX = 28; waveTrailY = -39;
            weapons.addAll(
                new PointDefenseWeapon("point-defense-mount") {{
                    x = -25; y = 20; mirror = true;
                    reload = 5; targetInterval = 5; targetSwitchInterval = 8;
                    bullet = new BulletType() {{
                        shootEffect = sparkShoot; hitEffect = pointHit;
                        maxRange = 240; damage = 30;
                    }};
                }},
                new PointDefenseWeapon("point-defense-mount") {{
                    x = -25; y = -30; mirror = true;
                    reload = 7; targetInterval = 7; targetSwitchInterval = 10;
                    bullet = new BulletType() {{
                        shootEffect = sparkShoot; hitEffect = pointHit;
                        maxRange = 240; damage = 35;
                    }};
                }},
                new Weapon("nothing") {{
                    x = 0; y = 40; mirror = false;
                    reload = 170; recoil = 2; shake = 3;
                    rotate = false; controllable = true; autoTarget = true;
                    continuous = true; cooldownTime = 170; ignoreRotation = true;
                    shootSound = shootLaser;
                    bullet = new ContinuousLaserBulletType(30f) {{
                        maxRange = 220; length = 240; lifetime = 155;
                        hitEffect = hitMeltHeal; drawSize = 460; shake = 1;
                        shootEffect = shootHeal; smokeEffect = Fx.none;
                        width = 6; largeHit = true;
                        incendSpread = 0; incendChance = 0; incendAmount = 0;
                        healPercent = 0.5f; collidesTeam = true;
                        colors = new Color[]{Color.valueOf("62ae7f"), Color.valueOf("84f491"), Color.valueOf("ffffff")};
                    }};
                }},
                new Weapon("plasma-laser-mount") {{
                    x = 12; y = -38; mirror = true;
                    reload = 6; inaccuracy = 5; shootCone = 90;
                    rotate = true; rotateSpeed = 2;
                    shoot = new ShootPattern() {{ shots = 12; }};
                    shootSound = shootFlame;
                    bullet = new BasicBulletType() {{
                        speed = 6; damage = 2; lifetime = 45;
                        buildingDamageMultiplier = 0.25f;
                        height = 0.01f; width = 0.01f;
                        makeFire = false; incendSpread = 0; incendChance = 0; incendAmount = 0;
                        pierceBuilding = true; pierceCap = 4;
                        collidesTeam = true; healAmount = 4;
                        despawnEffect = Fx.none;
                        smokeEffect = new ParticleEffect() {{
                            particles = 30; length = 300; lifetime = 30;
                            interp = Interp.circleOut; cone = 8;
                            colorFrom = Color.valueOf("84f491"); colorTo = Color.valueOf("62ae7f");
                            sizeFrom = 6; sizeTo = 1;
                            line = true; strokeFrom = 0.8f; strokeTo = 0.6f;
                        }};
                    }};
                }}
            );
            abilities.addAll(
                //TODO: UnitSpawnAbility for "oxynoe" — not yet converted to Java
                new EnergyFieldAbility(20f, 12f, 240f) {{
                    color = Color.valueOf("84f491");
                    healPercent = 1; hitBuildings = true; hitUnits = true;
                    maxTargets = 12; rotateSpeed = 1.2f;
                    status = FRStatus.constructionShock; statusDuration = 360;
                    targetAir = true; targetGround = true;
                    y = 10;
                }}
            );
        }};
    }
}
