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

public class FRTranscendentUnits {
    public static UnitType scepter, mangonel, thalass, vex, medusae, nivosa, mygale;

    public static void load() {
        mygale = new UnitType("mygale") {{
            constructor = LegsUnit::create; localizedName = "Mygale"; hovering = true;
            speed = 1.1f; health = 40000; armor = 38; baseRotateSpeed = 3; hitSize = 68;
            legCount = 10; legMoveSpace = 0.9f; legPairOffset = 3; legLength = 140;
            legExtension = -20; legBaseOffset = 8; stepShake = 10; legLengthScl = 0.93f;
            rippleScale = 10; legSpeed = 0.22f; legSplashDamage = 200; legSplashRange = 90;
            allowLegStep = true; canDrown = false; legContinuousMove = false;
            drownTimeMultiplier = 99999999999f;
            immunities.addAll(StatusEffects.melting, StatusEffects.burning, FRStatus.sapped, StatusEffects.freezing, StatusEffects.wet);
            outlines = false;
            weapons.addAll(
                new Weapon("reclusa-laser") {{
                    x = -30; y = 30; mirror = false; rotate = true;
                    reload = 22; shake = 3; rotateSpeed = 4; recoil = 5;
                    shootSound = Sounds.shootArtillery; ejectEffect = casing1;
                    bullet = new ShrapnelBulletType() {{
                        damage = 180; width = 40; length = 300;
                        serrationLenScl = 10; serrationSpaceOffset = 70; serrationFadeOffset = 0;
                        serrations = 22; serrationWidth = 9;
                        fromColor = Color.valueOf("8a2be2"); toColor = Color.valueOf("bf92f9");
                        shootEffect = sparkShoot; smokeEffect = sparkShoot;
                        status = FRStatus.sapped; statusDuration = 60;
                    }};
                }},
                new Weapon("reclusa-main") {{
                    x = 0; y = -12; mirror = false; rotate = true;
                    reload = 220; shake = 10; recoil = 15; rotateSpeed = 2; rotationLimit = 90;
                    shootSound = shootArtillery; ejectEffect = casing3;
                    bullet = new ArtilleryBulletType() {{
                        spin = 2; damage = 80; lifetime = 260; width = 60; height = 60;
                        splashDamage = 100; splashDamageRadius = 90; knockback = 1.5f;
                        sprite = "lycosid-bullet";
                        backColor = Color.valueOf("8a2be2"); frontColor = Color.valueOf("bf92f9");
                        lightning = 10; lightningLength = 30; lightningDamage = 50;
                        lightningColor = Color.valueOf("bf92f9");
                        smokeEffect = shootBigSmoke2; hitShake = 15;
                        lightRadius = 60; lightColor = Color.valueOf("bf92f9"); lightOpacity = 0.6f;
                        status = FRStatus.sapped; statusDuration = 90;
                        fragLifeMin = 0.4f; fragBullets = 12;
                        fragBullet = new ArtilleryBulletType() {{
                            damage = 60; lifetime = 150; width = 30; height = 30;
                            knockback = 1.5f; collidesTiles = false;
                            splashDamage = 100; splashDamageRadius = 100;
                            sprite = "lycosid-bullet";
                            backColor = Color.valueOf("8a2be2"); frontColor = Color.valueOf("bf92f9");
                            lightning = 6; lightningLength = 10;
                            lightningColor = Color.valueOf("bf92f9");
                            smokeEffect = shootBigSmoke2; hitShake = 6;
                            lightRadius = 50; lightColor = Color.valueOf("bf92f9"); lightOpacity = 0.5f;
                            status = FRStatus.sapped; statusDuration = 90;
                        }};
                    }};
                }}
            );
            abilities.addAll(
                new ForceFieldAbility(120f, 6f, 15000f, 800f)
            );
        }};

        scepter = new UnitType("scepter") {{
            constructor = UnitEntity::create; localizedName = "Scepter"; flying = true; lowAltitude = true;
            speed = 1.1f; drag = 0.12f; health = 22000; armor = 34; hitSize = 100;
            baseRotateSpeed = 0.08f; outlineColor = Color.valueOf("2a2a2a");
            immunities.addAll(StatusEffects.melting, StatusEffects.burning, FRStatus.sapped, StatusEffects.freezing, StatusEffects.wet);
            weapons.addAll(
                new Weapon("sps-air-fr") {{
                    x = -60; y = 20; mirror = false; rotate = true; reload = 22;
                    shootSound = shootLaser;
                    bullet = new LaserBulletType() {{
                        damage = 200; width = 8; length = 250;
                        colors = new Color[]{Color.valueOf("8a2be2"), Color.valueOf("bf92f9"), Color.valueOf("e0c0ff")};
                    }};
                }},
                new Weapon("sps-air-fr") {{
                    x = 60; y = 20; mirror = false; rotate = true; reload = 22;
                    shootSound = shootLaser;
                    bullet = new LaserBulletType() {{
                        damage = 200; width = 8; length = 250;
                        colors = new Color[]{Color.valueOf("8a2be2"), Color.valueOf("bf92f9"), Color.valueOf("e0c0ff")};
                    }};
                }},
                new Weapon("sps-lancer-left") {{
                    x = -36; y = -14; rotate = true; continuous = true;
                    reload = 160; shootSound = shootLaser;
                    bullet = new ContinuousLaserBulletType(35f) {{
                        length = 300; width = 3; lifetime = 130; fadeTime = 35;
                        colors = new Color[]{Color.valueOf("8a2be2"), Color.valueOf("bf92f9"), Color.valueOf("e0c0ff")};
                        status = FRStatus.sapped; statusDuration = 60;
                    }};
                }},
                new Weapon("sps-lancer-left") {{
                    x = 36; y = -14; rotate = true; continuous = true;
                    reload = 200; recoil = 5; recoilTime = 180; shootSound = shootLaser;
                    bullet = new ContinuousLaserBulletType(35f) {{
                        length = 300; width = 3; lifetime = 130; fadeTime = 35;
                        colors = new Color[]{Color.valueOf("8a2be2"), Color.valueOf("bf92f9"), Color.valueOf("e0c0ff")};
                        status = FRStatus.sapped; statusDuration = 60;
                    }};
                }},
                new Weapon("sps-rail") {{
                    x = 0; y = -4; mirror = false; continuous = true;
                    recoil = 10; recoilTime = 180; reload = 400;
                    shootSound = shootLaser;
                    bullet = new ContinuousLaserBulletType(45f) {{
                        width = 8; lifetime = 300; fadeTime = 30; length = 400;
                        colors = new Color[]{Color.valueOf("8a2be2"), Color.valueOf("bf92f9"), Color.valueOf("e0c0ff")};
                        status = FRStatus.sapped; statusDuration = 60;
                    }};
                }}
            );
            abilities.addAll(
                new ForceFieldAbility(130f, 6f, 12000f, 700f)
            );
        }};

        mangonel = new UnitType("mangonel") {{
            constructor = TankUnit::create; localizedName = "Mangonel";
            speed = 0.4f; health = 40000; armor = 38; hitSize = 104;
            rotateSpeed = 1.0f; crushDamage = 8;
            treadRects = new Rect[]{new Rect(68, -220, 144, 440)};
            omniMovement = false; singleTarget = false; faceTarget = false;
            weapons.addAll(
                new Weapon("onager-weapon") {{
                    x = 0; y = -6; mirror = false; rotate = true;
                    shootY = 72; reload = 240; shake = 8; recoil = 8;
                    rotateSpeed = 0.8f; layerOffset = 0.1f; shadow = 55;
                    heatColor = Color.valueOf("ff6600"); cooldownTime = 340; minWarmup = 0.9f;
                    shootSound = shootArtillery;
                    bullet = new BasicBulletType() {{
                        width = 48; height = 48; hitSize = 36;
                        speed = 3.5f; lifetime = 100;
                        shootEffect = shootTitan; smokeEffect = shootSmokeTitan;
                        sprite = "large-orb";
                        frontColor = Color.valueOf("ffffff"); backColor = Color.valueOf("f0d000");
                        trailColor = Color.valueOf("f0d000"); hitColor = Color.valueOf("f0d000");
                        pierce = true; pierceBuilding = true;
                        trailWidth = 8; trailLength = 32;
                        hitEffect = blastExplosion; despawnEffect = blastExplosion;
                        splashDamage = 100; splashDamageRadius = 80; damage = 300;
                        buildingDamageMultiplier = 2.5f;
                        fragOnHit = false; fragRandomSpread = 0; fragSpread = 90;
                        fragBullets = 6; fragVelocityMin = 1; fragVelocityMax = 1;
                        despawnSound = explosionDull;
                        fragBullet = new BasicBulletType() {{
                            sprite = "missile-large"; width = 20; height = 28;
                            lifetime = 25; speed = 2.5f; damage = 30;
                            buildingDamageMultiplier = 2.5f;
                            pierce = true; pierceBuilding = true; hitSize = 10;
                            hitColor = Color.valueOf("f0d000"); backColor = Color.valueOf("f0d000");
                            frontColor = Color.valueOf("ffffff"); trailColor = Color.valueOf("f0d000");
                            trailWidth = 4f; trailLength = 10;
                            hitEffect = blastExplosion;
                            splashDamageRadius = 30; splashDamage = 60;
                            fragBullets = 10; fragVelocityMin = 1; fragVelocityMax = 1;
                            fragRandomSpread = 0; fragSpread = 45;
                            fragBullet = new BasicBulletType() {{
                                sprite = "missile-large"; width = 20; height = 28;
                                lifetime = 25; speed = 2.5f; damage = 30;
                                buildingDamageMultiplier = 2.5f;
                                pierce = true; pierceBuilding = true; hitSize = 10;
                                hitColor = Color.valueOf("f0d000"); backColor = Color.valueOf("f0d000");
                                frontColor = Color.valueOf("ffffff"); trailColor = Color.valueOf("f0d000");
                                trailWidth = 4f; trailLength = 10;
                                hitEffect = blastExplosion;
                                splashDamageRadius = 30; splashDamage = 30;
                            }};
                        }};
                    }};
                }},
                new Weapon("springald-side-arm") {{
                    x = 40; y = 28; mirror = true; alternate = true;
                    reload = 45; rotate = true; rotateSpeed = 2.5f;
                    shootSound = Sounds.shoot;
                    shoot = new ShootPattern() {{ shots = 4; shotDelay = 3; }};
                    bullet = new BasicBulletType() {{
                        width = 12; height = 18; speed = 5; lifetime = 65; damage = 55;
                        buildingDamageMultiplier = 2.5f;
                        splashDamage = 12; splashDamageRadius = 20;
                        pierce = true; pierceCap = 4; pierceBuilding = true;
                        shootEffect = shootSmall; smokeEffect = shootSmallSmoke;
                    }};
                }},
                new Weapon("springald-side-arm") {{
                    x = 40; y = -40; mirror = true; alternate = true;
                    reload = 55; rotate = true; rotateSpeed = 2.5f;
                    shootSound = Sounds.shoot;
                    shoot = new ShootPattern() {{ shots = 4; shotDelay = 4; }};
                    bullet = new BasicBulletType() {{
                        width = 12; height = 18; speed = 5; lifetime = 95; damage = 55;
                        buildingDamageMultiplier = 2.5f;
                        splashDamage = 12; splashDamageRadius = 20;
                        pierce = true; pierceCap = 4; pierceBuilding = true;
                        shootEffect = shootSmall; smokeEffect = shootSmallSmoke;
                    }};
                }}
            );
        }};

        thalass = new UnitType("thalass") {{
            constructor = UnitWaterMove::create; localizedName = "Thalass";
            health = 35000; armor = 16; speed = 1.1f; hitSize = 100;
            drag = 0.18f; rotateSpeed = 1.4f; accel = 0.2f;
            faceTarget = false;
            trailLength = 360; waveTrailX = 30; waveTrailY = -40; trailScl = 4f;
            immunities.addAll(StatusEffects.melting, StatusEffects.burning, FRStatus.sapped, StatusEffects.freezing, StatusEffects.wet);
            weapons.addAll(
                new Weapon("japonica-lancer") {{
                    x = 0; y = 80; mirror = false; rotate = true;
                    shootY = 10; reload = 160; rotateSpeed = 2.5f;
                    continuous = true;
                    shootSound = shootLaser;
                    bullet = new ContinuousLaserBulletType(90f) {{
                        length = 500; width = 5; lifetime = 100; fadeTime = 35;
                        colors = new Color[]{Color.valueOf("20b2aa"), Color.valueOf("66ffcc"), Color.valueOf("ffffff")};
                    }};
                }},
                new Weapon("japonica-lancer") {{
                    x = 0; y = 12; mirror = false; rotate = true;
                    shootY = 10; reload = 160; rotateSpeed = 2.5f;
                    continuous = true;
                    shootSound = shootLaser;
                    bullet = new ContinuousLaserBulletType(90f) {{
                        length = 550; width = 5; lifetime = 100; fadeTime = 35;
                        colors = new Color[]{Color.valueOf("20b2aa"), Color.valueOf("66ffcc"), Color.valueOf("ffffff")};
                    }};
                }},
                new Weapon("japonica-air") {{
                    x = -48; y = -24; mirror = true; rotate = true;
                    reload = 45; rotateSpeed = 3.5f;
                    shootSound = shootArtillery;
                    shoot = new ShootPattern() {{ shots = 4; shotDelay = 8; }};
                    bullet = new BasicBulletType() {{
                        damage = 55; width = 9; height = 15; speed = 6; lifetime = 65;
                        splashDamage = 30; splashDamageRadius = 28;
                        lightning = 6; lightningLength = 3; lightningDamage = 12;
                        collidesAir = true; collidesGround = false;
                        homingPower = 0.12f; homingRange = 360;
                        sprite = "shell";
                        frontColor = Color.valueOf("66ffcc"); backColor = Color.valueOf("20b2aa");
                        status = FRStatus.sapped; statusDuration = 120;
                        shootEffect = shootBig; smokeEffect = shootSmallSmoke;
                    }};
                }},
                new Weapon("japonica-air") {{
                    x = 48; y = 55; mirror = true; rotate = true;
                    reload = 45; rotateSpeed = 3.5f;
                    shootSound = shootArtillery;
                    shoot = new ShootPattern() {{ shots = 4; shotDelay = 8; }};
                    bullet = new BasicBulletType() {{
                        damage = 55; width = 9; height = 15; speed = 6; lifetime = 65;
                        splashDamage = 30; splashDamageRadius = 28;
                        lightning = 6; lightningLength = 3; lightningDamage = 12;
                        collidesAir = true; collidesGround = false;
                        homingPower = 0.12f; homingRange = 360;
                        sprite = "shell";
                        frontColor = Color.valueOf("66ffcc"); backColor = Color.valueOf("20b2aa");
                        status = FRStatus.sapped; statusDuration = 120;
                        shootEffect = shootBig; smokeEffect = shootSmallSmoke;
                    }};
                }}
            );
            abilities.addAll(
                new EnergyFieldAbility(4f, 12f, 220f) {{
                    color = Color.valueOf("20b2aa");
                    targetAir = true; targetGround = true;
                    hitBuildings = false; hitUnits = true;
                    healPercent = 3; maxTargets = 16;
                    rotateSpeed = 0.25f; sectors = 4;
                    shootSound = shockBullet;
                    status = FRStatus.sapped; statusDuration = 60;
                    x = 0; y = 45;
                }}
            );
        }};

        vex = new UnitType("vex") {{
            constructor = UnitEntity::create; localizedName = "Vex"; flying = true; lowAltitude = true;
            speed = 1.1f; drag = 0.12f; health = 18000; armor = 28; hitSize = 95;
            baseRotateSpeed = 0.08f; aimDst = 650;
            createWreck = true; outlineColor = Color.valueOf("191919");
            immunities.addAll(StatusEffects.melting, StatusEffects.burning, FRStatus.sapped, StatusEffects.freezing, StatusEffects.wet);
            parts.addAll(
                new HaloPart() {{
                    x = 0; y = 6; mirror = false;
                    color = Color.valueOf("ff6600"); colorTo = Color.valueOf("ff3300");
                    hollow = true; shapes = 10; sides = 12; radius = 16;
                    stroke = 3; haloRotateSpeed = 1.5f;
                }},
                new HaloPart() {{
                    x = 0; y = 6; mirror = false;
                    color = Color.valueOf("ff6600"); colorTo = Color.valueOf("ff3300");
                    hollow = true; shapes = 10; tri = true; stroke = 4;
                    haloRadius = 26; triLength = 20; triLengthTo = 20; haloRotateSpeed = -1.5f;
                }}
            );
            weapons.addAll(
                new Weapon("hive-nuke") {{
                    x = 0; y = 0; mirror = false; alternate = false;
                    shootY = 55; reload = 450; cooldownTime = 400;
                    shake = 9; recoil = 0; rotate = false;
                    continuous = true; chargeSound = Sounds.chargeLancer;
                    shoot = new ShootPattern() {{ firstShotDelay = 90; }};
                    shootSound = shootLaser;
                    bullet = new ContinuousLaserBulletType(110f) {{
                        length = 600; width = 20; lifetime = 300; fadeTime = 60;
                        makeFire = false;
                        status = StatusEffects.melting; statusDuration = 600;
                        hitEffect = blastExplosion;
                    }};
                }},
                new Weapon("hive-mg") {{
                    x = 24; y = -24; mirror = false; rotate = true;
                    reload = 28; shake = 1.5f; rotateSpeed = 22;
                    shootSound = shootLaser;
                    bullet = new LaserBulletType() {{
                        damage = 160; length = 500;
                        status = StatusEffects.burning; statusDuration = 60;
                        colors = new Color[]{Color.valueOf("ff6600"), Color.valueOf("ff9c5a"), Color.valueOf("ffffff")};
                    }};
                }},
                new Weapon("hive-mg") {{
                    x = -24; y = -24; mirror = false; rotate = true;
                    reload = 20; shake = 1.5f; rotateSpeed = 22;
                    shootSound = shootLaser;
                    bullet = new LaserBulletType() {{
                        damage = 140; length = 500;
                        status = StatusEffects.burning; statusDuration = 60;
                        colors = new Color[]{Color.valueOf("ff6600"), Color.valueOf("ff9c5a"), Color.valueOf("ffffff")};
                    }};
                }},
                new Weapon("hive-laser") {{
                    x = -45; y = 0; mirror = false; rotate = true;
                    shootY = 25; reload = 200; shake = 5; recoil = 4;
                    rotateSpeed = 22; continuous = true;
                    shootSound = shootSpectre;
                    bullet = new PointBulletType() {{
                        speed = 14; lifetime = 35; maxRange = 450;
                        damage = 1000; splashDamage = 1000; splashDamageRadius = 30;
                        trailEffect = instTrail; hitEffect = massiveExplosion;
                        shootEffect = instShoot;
                        pierce = true; pierceBuilding = true;
                    }};
                }},
                new Weapon("hive-laser") {{
                    x = 45; y = 0; mirror = false; rotate = true;
                    shootY = 25; reload = 170; shake = 5; recoil = 4;
                    rotateSpeed = 22; continuous = true;
                    shootSound = shootSpectre;
                    bullet = new PointBulletType() {{
                        speed = 14; lifetime = 35; maxRange = 450;
                        damage = 950; splashDamage = 950; splashDamageRadius = 30;
                        trailEffect = instTrail; hitEffect = massiveExplosion;
                        shootEffect = instShoot;
                        pierce = true; pierceBuilding = true;
                    }};
                }}
            );
            abilities.addAll(
                new UnitSpawnAbility(FRMothershipUnits.hiveAttack, 0, -20, 350),
                new ForceFieldAbility(130f, 6f, 30000f, 800f)
            );
        }};

        medusae = new UnitType("medusae") {{
            constructor = LegsUnit::create; localizedName = "Medusae"; hovering = true;
            speed = 0.55f; health = 32000; armor = 20; hitSize = 60;
            rotateSpeed = 0.55f;
            legCount = 8; legMoveSpace = 0.9f; legPairOffset = 2; legLength = 100;
            legExtension = -20; legBaseOffset = 10; stepShake = 6; legLengthScl = 0.93f;
            rippleScale = 8; legSpeed = 0.35f; legSplashDamage = 120; legSplashRange = 60;
            allowLegStep = true; canDrown = false; shadowElevation = 0.2f;
            legContinuousMove = false; drownTimeMultiplier = 99999999999f;
            drawShields = false;
            immunities.addAll(StatusEffects.melting, StatusEffects.burning, FRStatus.sapped, StatusEffects.freezing, StatusEffects.wet);
            weapons.addAll(
                new Weapon("heal-laser-mount") {{
                    x = -30; y = 10; mirror = false;
                    reload = 140; recoil = 3; shake = 3;
                    top = false; predictTarget = true;
                    shoot = new ShootPattern() {{ firstShotDelay = 60; }};
                    shootSound = shootLaser; chargeSound = Sounds.chargeLancer;
                    soundPitchMin = 1.1f; continuous = true;
                    bullet = new LaserBulletType() {{
                        width = 40; damage = 140; length = 250; lifetime = 120;
                        largeHit = true;
                        lightColor = Color.valueOf("66ffcc"); lightningColor = Color.valueOf("66ffcc");
                        healPercent = 35; collidesTeam = true;
                        colors = new Color[]{Color.valueOf("20b2aa"), Color.valueOf("66ffcc"), Color.valueOf("ffffff")};
                    }};
                }},
                new Weapon("heal-laser-mount") {{
                    x = 30; y = 10; mirror = false;
                    reload = 180; recoil = 3; shake = 3;
                    top = false; predictTarget = true;
                    shoot = new ShootPattern() {{ firstShotDelay = 60; }};
                    shootSound = shootLaser; chargeSound = Sounds.chargeLancer;
                    soundPitchMin = 1.1f; continuous = true;
                    bullet = new LaserBulletType() {{
                        width = 40; damage = 140; length = 250; lifetime = 120;
                        largeHit = true;
                        lightColor = Color.valueOf("66ffcc"); lightningColor = Color.valueOf("66ffcc");
                        healPercent = 35; collidesTeam = true;
                        colors = new Color[]{Color.valueOf("20b2aa"), Color.valueOf("66ffcc"), Color.valueOf("ffffff")};
                    }};
                }},
                new Weapon("nothing") {{
                    x = 0; y = 24; mirror = false;
                    predictTarget = true; top = false;
                    reload = 360; recoil = 0; shake = 20;
                    cooldownTime = 480;
                    shootSound = shootLaser; chargeSound = Sounds.chargeLancer;
                    soundPitchMin = 1;
                    shoot = new ShootPattern() {{ firstShotDelay = 60; }};
                    shootStatus = StatusEffects.unmoving; shootStatusDuration = 180;
                    parentizeEffects = true;
                    bullet = new LaserBulletType() {{
                        length = 650; damage = 1400; width = 100; lifetime = 120;
                        lightningSpacing = 35; lightningLength = 15; lightningDelay = 1.1f;
                        lightningLengthRand = 18; lightningDamage = 90; lightningAngleRand = 40;
                        largeHit = true;
                        lightColor = Color.valueOf("66ffcc"); lightningColor = Color.valueOf("66ffcc");
                        healPercent = 35; collidesTeam = true;
                        sideAngle = 15; sideWidth = 0; sideLength = 0;
                        colors = new Color[]{Color.valueOf("20b2aa"), Color.valueOf("66ffcc"), Color.valueOf("ffffff")};
                        chargeEffect = Fx.greenLaserCharge;
                    }};
                }}
            );
        }};

        nivosa = new UnitType("nivosa") {{
            constructor = UnitWaterMove::create; localizedName = "Nivosa";
            health = 36000; armor = 22; speed = 0.85f; hitSize = 68;
            drag = 0.15f; accel = 0.28f; rotateSpeed = 1.8f;
            faceTarget = false; buildSpeed = 4.5f;
            trailLength = 100; trailScl = 6; waveTrailX = 32; waveTrailY = -44;
            weapons.addAll(
                new PointDefenseWeapon("point-defense-mount") {{
                    x = -30; y = 24; mirror = true;
                    reload = 4; targetInterval = 4; targetSwitchInterval = 6;
                    bullet = new BulletType() {{
                        shootEffect = sparkShoot; hitEffect = pointHit;
                        maxRange = 280; damage = 45;
                    }};
                }},
                new PointDefenseWeapon("point-defense-mount") {{
                    x = -30; y = -36; mirror = true;
                    reload = 5; targetInterval = 5; targetSwitchInterval = 8;
                    bullet = new BulletType() {{
                        shootEffect = sparkShoot; hitEffect = pointHit;
                        maxRange = 280; damage = 50;
                    }};
                }},
                new Weapon("nothing") {{
                    x = 0; y = 48; mirror = false;
                    reload = 130; recoil = 3; shake = 4;
                    rotate = false; controllable = true; autoTarget = true;
                    continuous = true; cooldownTime = 170; ignoreRotation = true;
                    shootSound = shootLaser;
                    bullet = new ContinuousLaserBulletType(45f) {{
                        maxRange = 280; length = 300; lifetime = 170;
                        hitEffect = hitMeltHeal; drawSize = 520; shake = 1.5f;
                        shootEffect = shootHeal; smokeEffect = Fx.none;
                        width = 8; largeHit = true;
                        incendSpread = 0; incendChance = 0; incendAmount = 0;
                        healPercent = 0.8f; collidesTeam = true;
                        colors = new Color[]{Color.valueOf("20b2aa"), Color.valueOf("66ffcc"), Color.valueOf("ffffff")};
                    }};
                }},
                new Weapon("plasma-laser-mount") {{
                    x = 14; y = -44; mirror = true;
                    reload = 4; inaccuracy = 4; shootCone = 90;
                    rotate = true; rotateSpeed = 2.5f;
                    shoot = new ShootPattern() {{ shots = 16; }};
                    shootSound = shootFlame;
                    bullet = new BasicBulletType() {{
                        speed = 7; damage = 3; lifetime = 50;
                        buildingDamageMultiplier = 0.25f;
                        height = 0.01f; width = 0.01f;
                        makeFire = false; incendSpread = 0; incendChance = 0; incendAmount = 0;
                        pierceBuilding = true; pierceCap = 5;
                        collidesTeam = true; healAmount = 6;
                        despawnEffect = Fx.none;
                        smokeEffect = new ParticleEffect() {{
                            particles = 40; length = 350; lifetime = 35;
                            interp = Interp.circleOut; cone = 8;
                            colorFrom = Color.valueOf("66ffcc"); colorTo = Color.valueOf("20b2aa");
                            sizeFrom = 8; sizeTo = 1.5f;
                            line = true; strokeFrom = 1f; strokeTo = 0.8f;
                        }};
                    }};
                }}
            );
            abilities.addAll(
                new EnergyFieldAbility(25f, 14f, 280f) {{
                    color = Color.valueOf("66ffcc");
                    healPercent = 1.5f; hitBuildings = true; hitUnits = true;
                    maxTargets = 16; rotateSpeed = 1.4f;
                    status = FRStatus.constructionShock; statusDuration = 360;
                    targetAir = true; targetGround = true;
                    y = 12;
                }}
            );
        }};
    }
}
