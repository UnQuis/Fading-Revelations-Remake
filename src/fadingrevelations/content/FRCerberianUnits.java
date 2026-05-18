package fadingrevelations.content;

import arc.graphics.*;
import arc.math.*;
import mindustry.ai.types.*;
import mindustry.content.*;
import mindustry.entities.abilities.*;
import mindustry.entities.bullet.*;
import mindustry.entities.effect.*;
import mindustry.entities.pattern.*;
import mindustry.gen.*;
import mindustry.type.*;

import static mindustry.content.Fx.*;
import static mindustry.gen.Sounds.*;

public class FRCerberianUnits {
    public static UnitType penumbra, hexathelid, curtulus, auctus, behemoth, straggle,
        bayonet, veil, spark, citadel, vista, cudgel, kaiser, summit, nephila, setosus, baton;

    public static void load() {
        penumbra = new UnitType("cerberian-penumbra") {{
            constructor = UnitEntity::create; flying = true; lowAltitude = true;
            speed = 0.93333f; accel = 0.04f; drag = 0.04f; rotateSpeed = 1.9f;
            health = 10000; armor = 10; engineOffset = 21; engineSize = 5.3f; hitSize = 46;
            localizedName = "Penumbra";
            description = "A unit with technology similar to that of the Serpulo Antumbra but the main cannons shoot a laser instead of regular bullets.";
            weapons.addAll(
                new Weapon("cerberian-missile-mount") {{
                    y = 8; x = 17; reload = 20; ejectEffect = casing1;
                    rotateSpeed = 8; shootSound = shootMissile; rotate = true;
                    shoot = new ShootPattern() {{ shots = 2; shotDelay = 7; }};
                    bullet = new MissileBulletType() {{
                        speed = 2.7f; damage = 26; width = 8; height = 8;
                        shrinkY = 0; drag = -0.01f; splashDamageRadius = 20;
                        splashDamage = 30; lifetime = 50; hitEffect = blastExplosion;
                        despawnEffect = blastExplosion; trailColor = Color.valueOf("5461c5");
                        frontColor = Color.valueOf("6d7dff"); backColor = Color.valueOf("5461c5");
                        status = StatusEffects.blasted; statusDuration = 60;
                    }};
                }},
                new Weapon("cerberian-missile-mount") {{
                    y = -8; x = 17; reload = 35; ejectEffect = casing1;
                    rotateSpeed = 8; shootSound = shootMissile; rotate = true;
                    shoot = new ShootPattern() {{ shots = 2; shotDelay = 7; }};
                    bullet = new MissileBulletType() {{
                        speed = 2.7f; damage = 26; width = 8; height = 8;
                        shrinkY = 0; drag = -0.01f; splashDamageRadius = 20;
                        splashDamage = 30; lifetime = 50; hitEffect = blastExplosion;
                        despawnEffect = blastExplosion; trailColor = Color.valueOf("5461c5");
                        frontColor = Color.valueOf("6d7dff"); backColor = Color.valueOf("5461c5");
                        status = StatusEffects.blasted; statusDuration = 60;
                    }};
                }},
                new Weapon("cerberian-large-bullet-mount") {{
                    x = 10; y = 2; shootY = 10; reload = 48; shake = 1;
                    rotateSpeed = 2; ejectEffect = Fx.none; shootSound = shootLaser; rotate = true;
                    bullet = new LaserBulletType() {{
                        pierce = false; damage = 300; width = 12; lifetime = 25;
                        colors = new Color[]{Color.valueOf("5461c5"), Color.valueOf("99a4ff"), Color.white};
                        pierceCap = 1; pierceBuilding = false;
                    }};
                }}
            );
        }};

        hexathelid = new UnitType("cerberian-hexathelid") {{
            health = 500; armor = 2; hitSize = 13; rotateSpeed = 3;
            speed = 0.6f; drag = 0.4f; targetAir = false;
            immunities.addAll(StatusEffects.burning, StatusEffects.melting);
            localizedName = "Hexathelid";
            description = "A unit type similar to an Atrax but shoots fragging slag bullets. Exchanges less health and armor for a lot more damage.";
            constructor = LegsUnit::create; hovering = true;
            legCount = 4; legLength = 9; legForwardScl = 0.6f;
            legMoveSpace = 1.4f; shadowElevation = 0.2f; groundLayer = 74;
            weapons.add(
                new Weapon("cerberian-atrax-weapon") {{
                    top = false; shootY = 3; reload = 9;
                    ejectEffect = Fx.none; x = 7; shootSound = shootFlame;
                    bullet = new LiquidBulletType(Liquids.slag) {{
                        damage = 30; drag = 0.009f; speed = 2.5f;
                        shootEffect = shootSmall; lifetime = 57; collidesAir = false;
                        fragBullets = 2; status = StatusEffects.burning; statusDuration = 120;
                        fragBullet = new LiquidBulletType(Liquids.slag) {{
                            knockback = 1.6f; speed = 1; damage = 8;
                            lifetime = 40; collidesAir = false;
                            status = StatusEffects.melting; statusDuration = 60;
                        }};
                    }};
                }}
            );
        }};

        curtulus = new UnitType("cerberian-curtulus") {{
            health = 8000; armor = 6; hitSize = 23; rotateSpeed = 2.7f;
            speed = 0.62f; drag = 0.1f;
            localizedName = "Curtulus";
            description = "A unit with an appearance close to that of an Arkyid but instead of the main cannon shooting artillery bullets, it shoots a liquid shell.";
            constructor = LegsUnit::create; hovering = true;
            legCount = 6; legMoveSpace = 1; legPairOffset = 3;
            legLength = 30; legExtension = -15; legBaseOffset = 10;
            stepShake = 1; legLengthScl = 0.96f; rippleScale = 2;
            legSpeed = 2; legSplashDamage = 32; legSplashRange = 30;
            drownTimeMultiplier = 2.2f; shadowElevation = 0.65f; groundLayer = 75;
            weapons.addAll(
                new Weapon("cerberian-spiroct-weapon") {{
                    reload = 9; x = 4; y = 8; rotate = true;
                    shootSound = shootSap;
                    bullet = new SapBulletType() {{
                        sapStrength = 0.9f; length = 55; damage = 40;
                        shootEffect = shootSmall; hitColor = Color.valueOf("bf92f9");
                        despawnEffect = Fx.none; width = 0.56f;
                        lifetime = 30; knockback = -1;
                    }};
                }},
                new Weapon("cerberian-spiroct-weapon") {{
                    reload = 14; x = 9; y = 6; rotate = true;
                    shootSound = shootSap;
                    bullet = new SapBulletType() {{
                        sapStrength = 0.9f; length = 55; damage = 40;
                        shootEffect = shootSmall; hitColor = Color.valueOf("bf92f9");
                        despawnEffect = Fx.none; width = 0.56f;
                        lifetime = 30; knockback = -1;
                    }};
                }},
                new Weapon("cerberian-spiroct-weapon") {{
                    reload = 22; x = 14; y = 0; rotate = true;
                    shootSound = shootSap;
                    bullet = new SapBulletType() {{
                        sapStrength = 0.9f; length = 55; damage = 40;
                        shootEffect = shootSmall; hitColor = Color.valueOf("bf92f9");
                        despawnEffect = Fx.none; width = 0.56f;
                        lifetime = 30; knockback = -1;
                    }};
                }},
                new Weapon("cerberian-large-green-mount") {{
                    y = -7; x = 9; shootY = 7; reload = 45; shake = 3;
                    rotateSpeed = 2; ejectEffect = casing1; shootSound = shootLaser;
                    rotate = true; recoil = 3;
                    bullet = new LiquidBulletType(Liquids.slag) {{
                        speed = 2; damage = 0; splashDamage = 42;
                        splashDamageRadius = 70; orbSize = 4; fragBullets = 4;
                        lifetime = 70; status = StatusEffects.melting; fragLifeMin = 0.3f;
                        fragBullet = new LiquidBulletType(Liquids.oil) {{
                            speed = 2; orbSize = 3; damage = 0;
                            splashDamage = 7; splashDamageRadius = 70; status = StatusEffects.tarred;
                            fragBullets = 3; lifetime = 55; fragLifeMin = 0.3f;
                            fragBullet = new LiquidBulletType(Liquids.cryofluid) {{
                                speed = 2; lifetime = 30; damage = 0;
                                splashDamage = 20; orbSize = 2; splashDamageRadius = 70;
                            }};
                        }};
                    }};
                }}
            );
        }};

        auctus = new UnitType("cerberian-auctus") {{
            constructor = CrawlUnit::create;
            health = 12000; armor = 12; drawCell = false; outlines = false;
            hitSize = 120; squareShape = false; segments = 8;
            omniMovement = false; rotateSpeed = 0.78f; drownTimeMultiplier = 8;
            drawBody = false; hidden = false; crushDamage = 2; targetAir = false;
            segmentScl = 4; segmentPhase = 5; speed = 1;
            localizedName = "Auctus";
            description = "A long, snake-like unit that stomps buildings.";
        }};

        behemoth = new UnitType("cerberian-behemoth") {{
            health = 38000; armor = 26; speed = 0.4f; rotateSpeed = 0.31f;
            localizedName = "Cerberian Behemoth";
            description = "A huge unit and Cerberos proudest invention. Shoots with a ginormous laser, huge fragging bullets, laser sweepers and smaller FLAK turrets.";
            constructor = LegsUnit::create; groundLayer = 75;
            legMoveSpace = 0.8f; legPairOffset = 3; legLength = 160;
            legExtension = -20; legBaseOffset = 26; stepShake = 14;
            legLengthScl = 0.93f; rippleScale = 8; legSpeed = 0.2f;
            legSplashDamage = 120; legSplashRange = 72; legCount = 8;
            weapons.addAll(
                new Weapon("cerberian-flak-mount") {{
                    x = 3; y = 46; rotate = true; rotateSpeed = 4;
                    shake = 1; reload = 50; mirror = true; shootSound = shootArtillery;
                    bullet = new FlakBulletType(4f, 10f) {{
                        height = 12; width = 8;
                        frontColor = Color.valueOf("a93e3e"); backColor = Color.valueOf("6f2626");
                        pierce = true; pierceCap = 3; homingPower = 0.09f;
                        lifetime = 90; splashDamage = 10; splashDamageRadius = 12;
                        collidesAir = true; collidesGround = false; collidesTiles = false;
                    }};
                }},
                new Weapon("cerberian-flak-mount") {{
                    x = 11; y = 40; rotate = true; shake = 1;
                    rotateSpeed = 4; reload = 30; mirror = true; shootSound = shootArtillery;
                    bullet = new FlakBulletType(4f, 8f) {{
                        damage = 8; pierce = true; width = 12; height = 8;
                        frontColor = Color.valueOf("a93e3e"); backColor = Color.valueOf("6f2626");
                        pierceCap = 3; homingPower = 0.09f;
                        lifetime = 90; splashDamage = 8; splashDamageRadius = 12;
                        collidesAir = true; collidesGround = false; collidesTiles = false;
                    }};
                }},
                new Weapon("cerberian-laser-annihilator") {{
                    shootStatus = StatusEffects.unmoving; shootStatusDuration = 700;
                    x = 0; y = -40; shake = 11; reload = 720;
                    shoot = new ShootPattern() {{ firstShotDelay = 420; }};
                    rotate = false; mirror = false; continuous = true;
                    chargeSound = shootLaser; shootSound = shootLaser;
                    bullet = new ContinuousLaserBulletType(800f) {{
                        width = 24; length = 460; shake = 7;
                        damageInterval = 20; lifetime = 280; fadeTime = 110;
                        hittable = false; absorbable = false; laserAbsorb = false;
                        colors = new Color[]{Color.valueOf("a93e3e"), Color.valueOf("6f2626"), Color.valueOf("b34343"), Color.valueOf("932a2a"), Color.white};
                        hitEffect = new MultiEffect(
                            new ParticleEffect() {{
                                sizeFrom = 8; sizeTo = 4; colorFrom = Color.valueOf("a93e3e");
                                colorTo = Color.valueOf("6f2626"); length = -95;
                                baseLength = 95; lifetime = 120; particles = 50;
                                interp = Interp.exp5; sizeInterp = Interp.pow5Out;
                            }},
                            new ParticleEffect() {{
                                sizeFrom = 8; sizeTo = 4; colorFrom = Color.valueOf("a93e3e");
                                colorTo = Color.valueOf("6f2626"); length = 95;
                                baseLength = -95; lifetime = 120; particles = 50;
                                interp = Interp.exp5; sizeInterp = Interp.pow5Out;
                            }}
                        );
                        chargeEffect = new MultiEffect(
                            new ParticleEffect() {{ particles = 60; length = 100; baseLength = -100; lifetime = 200; layer = 106; interp = Interp.exp5; sizeFrom = 8; sizeTo = 3; colorFrom = Color.valueOf("a93e3e"); colorTo = Color.valueOf("6f2626"); }},
                            new ParticleEffect() {{ particles = 60; offset = 15; baseLength = 18; sizeFrom = 0; sizeTo = 4; length = 80; lifetime = 340; colorFrom = Color.valueOf("a93e3e"); colorTo = Color.valueOf("6f2626"); cone = 360; }},
                            new WaveEffect() {{ lifetime = 340; sizeFrom = 55; sizeTo = 0; interp = Interp.pow5Out; strokeFrom = 0; strokeTo = 8; colorFrom = Color.valueOf("a93e3e"); colorTo = Color.valueOf("6f2626"); }},
                            new WaveEffect() {{ lifetime = 340; sizeFrom = 65; sizeTo = 0; interp = Interp.pow10Out; strokeFrom = 0; strokeTo = 8; colorFrom = Color.valueOf("a93e3e"); colorTo = Color.valueOf("6f2626"); }},
                            new ParticleEffect() {{ particles = 60; offset = 100; sizeFrom = 1; sizeTo = 6; length = 120; baseLength = -120; interp = Interp.pow3In; sizeInterp = Interp.pow5Out; lifetime = 220; colorFrom = Color.valueOf("a93e3e"); colorTo = Color.valueOf("6f2626"); }},
                            new ParticleEffect() {{ particles = 60; offset = 100; sizeFrom = 1; sizeTo = 6; length = 120; baseLength = -120; interp = Interp.pow3In; sizeInterp = Interp.pow5Out; lifetime = 260; colorFrom = Color.valueOf("a93e3e"); colorTo = Color.valueOf("6f2626"); }},
                            new WaveEffect() {{ lifetime = 340; sizeFrom = 55; sizeTo = 0; interp = Interp.pow5Out; strokeFrom = 0; strokeTo = 8; colorFrom = Color.valueOf("a93e3e"); colorTo = Color.valueOf("6f2626"); }},
                            new WaveEffect() {{ lifetime = 340; sizeFrom = 65; sizeTo = 0; interp = Interp.pow10Out; strokeFrom = 0; strokeTo = 8; colorFrom = Color.valueOf("a93e3e"); colorTo = Color.valueOf("6f2626"); }},
                            new ParticleEffect() {{ particles = 70; offset = 100; sizeFrom = 1; sizeTo = 6; length = 200; baseLength = -200; interp = Interp.pow3In; sizeInterp = Interp.pow5Out; lifetime = 300; colorFrom = Color.valueOf("a93e3e"); colorTo = Color.valueOf("6f2626"); }},
                            new ParticleEffect() {{ particles = 120; offset = 100; sizeFrom = 1; sizeTo = 6; length = 250; baseLength = -250; interp = Interp.pow3In; sizeInterp = Interp.pow5Out; lifetime = 240; colorFrom = Color.valueOf("a93e3e"); colorTo = Color.valueOf("6f2626"); }},
                            new ParticleEffect() {{ particles = 130; offset = 100; sizeFrom = 1; sizeTo = 6; length = 300; baseLength = -400; interp = Interp.pow3In; sizeInterp = Interp.pow5Out; lifetime = 400; colorFrom = Color.valueOf("a93e3e"); colorTo = Color.valueOf("6f2626"); }},
                            new ParticleEffect() {{ particles = 145; offset = 100; sizeFrom = 1; sizeTo = 6; length = 350; baseLength = -350; interp = Interp.pow3In; sizeInterp = Interp.pow5Out; lifetime = 420; colorFrom = Color.valueOf("a93e3e"); colorTo = Color.valueOf("6f2626"); }}
                        );
                    }};
                }},
                new Weapon("cerberian-behemoth-weapon") {{
                    x = 36; y = 0; shake = 5; recoil = 6; reload = 180;
                    alternate = true; shootSound = shootArtillery; mirror = true;
                    rotate = false; top = false;
                    bullet = new BasicBulletType() {{
                        sprite = "fading-revelations-patched-lycosid-bullet"; frontColor = Color.valueOf("a93e3e");
                        hitSound = Sounds.shoot; hitShake = 9; hitEffect = instHit;
                        backColor = Color.valueOf("6f2626"); width = 42; height = 42;
                        speed = 1.2f; lifetime = 360; scaleLife = true; damage = 60;
                        spin = 1.6f; pierce = true; pierceBuilding = true;
                        buildingDamageMultiplier = 0.75f;
                        lightning = 6; lightningColor = Color.valueOf("a93e3e");
                        lightningLength = 12; lightningDamage = 15; fragBullets = 6;
                        fragBullet = new ArtilleryBulletType(1f, 35f) {{
                            hitShake = 6; hitEffect = hitMeltdown;
                            hitSound = Sounds.explosion; sprite = "fading-revelations-patched-lycosid-bullet";
                            frontColor = Color.valueOf("a93e3e"); backColor = Color.valueOf("6f2626");
                            width = 38; height = 38; speed = 1; lifetime = 60;
                            lightning = 3; lightningLength = 9; lightningDamage = 12;
                            fragBullets = 12;
                            fragBullet = new BombBulletType() {{
                                hitShake = 3; splashDamageRadius = 70;
                                sprite = "fading-revelations-patched-lml-mine"; hitSound = Sounds.explosion;
                                width = 12; height = 12;
                                hitEffect = new WaveEffect() {{ sizeFrom = 0; sizeTo = 18; colorFrom = Color.valueOf("a93e3e"); colorTo = Color.valueOf("6f2626"); }};
                                splashDamage = 30; speed = 0.5f; lifetime = 60;
                                fragBullets = 1;
                                fragBullet = new MissileBulletType() {{
                                    hitShake = 1; speed = 1.5f; lifetime = 40;
                                    height = 16; width = 10; damage = 25;
                                    homingPower = 0.09f; homingRange = 160; homingDelay = 60;
                                    hitEffect = blastExplosion;
                                    lightning = 2; lightningLength = 6; lightningDamage = 4;
                                }};
                            }};
                        }};
                    }};
                }},
                new Weapon("cerberian-laser-sweeper") {{
                    x = 18; y = 26; reload = 240; rotate = false;
                    shake = 1; shootCone = 180; alternate = true;
                    shootSound = shockBullet; ignoreRotation = true;
                    chargeSound = chargeLancer;
                    shoot = new ShootSpread(12, 2) {{ shotDelay = 3; firstShotDelay = 60; }};
                    bullet = new LaserBulletType() {{
                        damage = 240; length = 360; width = 26;
                        collidesAir = true; collidesGround = true;
                        hitSound = shockBullet; hitEffect = instHit;
                        colors = new Color[]{Color.valueOf("a93e3e"), Color.valueOf("6f2626"), Color.white};
                    }};
                }}
            );
            abilities.add(
                new ShieldArcAbility() {{
                    radius = 100; regen = 7; max = 10000; cooldown = 1200;
                    width = 6; angle = 83; x = 0; y = -35;
                }}
            );
        }};

        straggle = new UnitType("cerberian-straggle") {{
            constructor = MechUnit::create; health = 200; hitSize = 8; speed = 1; range = 40;
            localizedName = "Straggle";
            description = "A cerberian version of the Crawler unit.";
            controller = u -> new SuicideAI();
            weapons.add(
                new Weapon() {{
                    shootOnDeath = true; reload = 24; shootCone = 180;
                    ejectEffect = Fx.none; shootSound = Sounds.explosion;
                    x = 0; shootY = 0; mirror = false;
                    bullet = new BulletType() {{
                        collides = false; collidesTiles = false;
                        hitSound = Sounds.explosion; rangeOverride = 30;
                        hitEffect = pulverize; speed = 0;
                        splashDamageRadius = 55; instantDisappear = true;
                        splashDamage = 105; killShooter = true;
                        hittable = false; collidesAir = true; damage = 0;
                    }};
                }}
            );
        }};

        bayonet = new UnitType("cerberian-bayonet") {{
            constructor = MechUnit::create; health = 150; speed = 0.5f;
            localizedName = "Bayonet";
            description = "A stronger version of the Dagger unit typically found on Serpulo.";
            weapons.add(
                new Weapon("large-cerberian-weapon") {{
                    reload = 9; x = 4; y = 2; top = false;
                    shootSound = shootLaser; ejectEffect = casing1;
                    bullet = new MissileBulletType() {{
                        speed = 2.5f; damage = 11; lifetime = 90;
                        homingPower = 0.2f; weaveMag = 4; weaveScale = 4;
                        shootEffect = shootSmall; frontColor = Color.valueOf("ffffff");
                        hitSound = Sounds.none; width = 7; height = 9;
                        lightColor = Color.valueOf("8ca9e8"); trailColor = Color.valueOf("8ca9e8");
                        backColor = Color.valueOf("8ca9e8"); lightRadius = 40; lightOpacity = 0.7f;
                        trailWidth = 2.2f; trailLength = 18; trailChance = -1;
                        despawnEffect = Fx.none; despawnHit = true;
                        hitEffect = new ExplosionEffect() {{
                            lifetime = 20; waveStroke = 1.2f; waveColor = Color.valueOf("8ca9e8");
                            sparkColor = Color.valueOf("8ca9e8"); waveRad = 12;
                            smokeSize = 0; smokeSizeBase = 0; sparks = 8;
                            sparkRad = 35; sparkLen = 3; sparkStroke = 1.2f;
                        }};
                    }};
                }}
            );
        }};

        veil = new UnitType("cerberian-veil") {{
            constructor = UnitEntity::create; flying = true; lowAltitude = true;
            speed = 0.73333f; accel = 0.04f; drag = 0.04f; rotateSpeed = 1;
            health = 40000; armor = 6; engineOffset = 38; engineSize = 7.3f; hitSize = 58;
            localizedName = "Veil";
            description = "Like the Eclipse unit but instead of shooting flak bullets, it shoots energy missiles. Also shoots stronger lasers. Sacrifices some of it's armor for more health and damage.";
            weapons.addAll(
                new Weapon("cerberian-large-laser-mount") {{
                    shake = 4; shootY = 9; x = 18; y = 5;
                    rotateSpeed = 2; reload = 45; recoil = 4;
                    shootSound = shootLaser; rotate = true;
                    bullet = new LaserBulletType() {{
                        damage = 300; sideAngle = 20; sideWidth = 1.5f;
                        sideLength = 80; width = 25; length = 230;
                        shootEffect = shockwave;
                        colors = new Color[]{Color.valueOf("5461c5"), Color.valueOf("99a4ff"), Color.white};
                    }};
                }},
                new Weapon("cerberian-large-artillery") {{
                    x = 11; y = 27; rotateSpeed = 2; reload = 9;
                    shootSound = shockBullet; rotate = true; recoil = 0.5f; shootY = 7.25f;
                    bullet = new MissileBulletType() {{
                        speed = 4; damage = 32; splashDamage = 66;
                        splashDamageRadius = 25; collidesGround = true; lifetime = 47;
                        status = StatusEffects.blasted; statusDuration = 60;
                        homingPower = 0.2f; weaveMag = 4; weaveScale = 4;
                        shootEffect = shootBig2; frontColor = Color.valueOf("ffffff");
                        hitSound = Sounds.none; width = 10; height = 10;
                        lightColor = Color.valueOf("8ca9e8"); trailColor = Color.valueOf("8ca9e8");
                        backColor = Color.valueOf("8ca9e8"); lightRadius = 40; lightOpacity = 0.7f;
                        trailWidth = 2.8f; trailLength = 20; trailChance = -1;
                        despawnSound = explosionDull; despawnEffect = Fx.none;
                        hitEffect = new ExplosionEffect() {{
                            lifetime = 20; waveStroke = 2; waveColor = Color.valueOf("8ca9e8");
                            sparkColor = Color.valueOf("8ca9e8"); waveRad = 12;
                            smokeSize = 0; smokeSizeBase = 0; sparks = 10;
                            sparkRad = 35; sparkLen = 4; sparkStroke = 1.5f;
                        }};
                    }};
                }},
                new Weapon("cerberian-large-artillery") {{
                    y = -13; x = 20; rotateSpeed = 2; reload = 9;
                    shootSound = shockBullet; rotate = true; recoil = 0.5f; shootY = 7.25f;
                    bullet = new MissileBulletType() {{
                        speed = 4; damage = 16; splashDamage = 66;
                        splashDamageRadius = 25; collidesGround = true; lifetime = 47;
                        status = StatusEffects.blasted; statusDuration = 60;
                        homingPower = 0.2f; weaveMag = 4; weaveScale = 4;
                        shootEffect = shootBig2; frontColor = Color.valueOf("ffffff");
                        hitSound = Sounds.none; width = 10; height = 10;
                        lightColor = Color.valueOf("8ca9e8"); trailColor = Color.valueOf("8ca9e8");
                        backColor = Color.valueOf("8ca9e8"); lightRadius = 40; lightOpacity = 0.7f;
                        trailWidth = 2.8f; trailLength = 20; trailChance = -1;
                        despawnSound = explosionDull; despawnEffect = Fx.none;
                        hitEffect = new ExplosionEffect() {{
                            lifetime = 20; waveStroke = 2; waveColor = Color.valueOf("8ca9e8");
                            sparkColor = Color.valueOf("8ca9e8"); waveRad = 12;
                            smokeSize = 0; smokeSizeBase = 0; sparks = 10;
                            sparkRad = 35; sparkLen = 4; sparkStroke = 1.5f;
                        }};
                    }};
                }}
            );
        }};

        spark = new UnitType("cerberian-spark") {{
            constructor = UnitEntity::create; flying = true; speed = 2.7f; accel = 0.08f; drag = 0.04f;
            health = 70; engineOffset = 5.75f; hitSize = 9; itemCapacity = 10;
            localizedName = "Spark";
            description = "A unit similar to a Flare from Serpulo but with upgraded weaponry.";
            weapons.add(
                new Weapon() {{
                    y = 0; x = 2; reload = 20; ejectEffect = casing1;
                    shootSound = Sounds.shoot;
                    bullet = new BasicBulletType(2.5f, 11f) {{
                        width = 7; height = 9; lifetime = 45;
                        shootEffect = shootSmall; smokeEffect = shootSmallSmoke;
                        frontColor = Color.valueOf("6d7dff"); backColor = Color.valueOf("5461c5");
                    }};
                }}
            );
        }};

        citadel = new UnitType("cerberian-citadel") {{
            constructor = MechUnit::create; health = 900; armor = 9; speed = 0.43f; hitSize = 13;
            rotateSpeed = 3; targetAir = false;
            localizedName = "Citadel";
            description = "An upgraded version of the Fortress that hails from Cerbero.";
            weapons.add(
                new Weapon("cerberian-artillery") {{
                    top = false; y = 1; x = 9; reload = 40;
                    recoil = 3; shake = 2; ejectEffect = casing2;
                    shootSound = shootArtillery;
                    bullet = new ArtilleryBulletType(2.2f, 28f) {{
                        hitEffect = blastExplosion; knockback = 0.8f;
                        lifetime = 140; sprite = "shell"; width = 14; height = 14;
                        collides = true; collidesTiles = true;
                        splashDamageRadius = 42; splashDamage = 85;
                        backColor = Color.valueOf("5461c5"); frontColor = Color.valueOf("6d7dff");
                    }};
                }}
            );
        }};

        vista = new UnitType("cerberian-vista") {{
            constructor = UnitEntity::create; flying = true; health = 340; speed = 1.65f; accel = 0.08f;
            drag = 0.016f; hitSize = 10; targetAir = false; engineOffset = 7.8f;
            range = 140; faceTarget = false; armor = 3; itemCapacity = 0;
            localizedName = "Vista";
            description = "A Horizon-like unit that throws down stronger bombs.";
            weapons.add(
                new Weapon() {{
                    minShootVelocity = 0.75f; x = 3; shootY = 0;
                    shootCone = 180; ejectEffect = Fx.none; inaccuracy = 15;
                    reload = 12; ignoreRotation = true; shootSound = Sounds.none;
                    bullet = new BombBulletType() {{
                        splashDamageRadius = 25; splashDamage = 30;
                        width = 10; height = 14; hitEffect = flakExplosion;
                        shootEffect = Fx.none; smokeEffect = Fx.none;
                        status = StatusEffects.blasted; statusDuration = 60;
                        fragBullets = 2;
                        fragBullet = new BasicBulletType(2f, 2f) {{ lifetime = 20; }};
                    }};
                }}
            );
        }};

        cudgel = new UnitType("cerberian-cudgel") {{
            constructor = MechUnit::create; health = 550; armor = 4; speed = 0.5f; hitSize = 10;
            immunities.add(StatusEffects.burning);
            localizedName = "Cudgel";
            description = "A unit similar to a Mace from Serpulo but stronger. Shoots with a different type of Flamethrower.";
            weapons.add(
                new Weapon("cerberian-flamethrower") {{
                    top = false; shootY = 2; reload = 180; mirror = true;
                    alternate = false; recoil = 1; ejectEffect = Fx.none;
                    shootSound = shootFlame; continuous = true;
                    bullet = new ContinuousFlameBulletType(45f) {{
                        drawFlare = false;
                        colors = new Color[]{Color.valueOf("043F98"), Color.valueOf("1165C1"), Color.valueOf("9CDEEB"), Color.valueOf("B7E8EB"), Color.valueOf("66BEF9")};
                        length = 44; width = 2; largeHit = false;
                        damageInterval = 20; lifetime = 240;
                        pierce = true; pierceCap = 2; despawnEffect = Fx.none;
                        status = StatusEffects.burning; pierceBuilding = true;
                        speed = 8; pierceArmor = false;
                    }};
                }}
            );
        }};

        kaiser = new UnitType("cerberian-kaiser") {{
            constructor = MechUnit::create; health = 48000; armor = 20; speed = 0.24f; hitSize = 26;
            rotateSpeed = 1.65f; drownTimeMultiplier = 6;
            localizedName = "Kaiser";
            description = "A unit inspired by the Reign from Serpulo but with upgraded technology. Hass massively increased health and armor at the cost of sacrificing some agility.";
            weapons.add(
                new Weapon("cerberian-reign-weapon") {{
                    top = false; y = 1; x = 21.5f; reload = 9;
                    shootY = 11; recoil = 5; shake = 2; ejectEffect = casing4;
                    shootSound = Sounds.shoot;
                    bullet = new ShrapnelBulletType() {{
                        damage = 240; toColor = Color.valueOf("6d7dff"); fromColor = Color.valueOf("5461c5");
                        pierce = true; pierceCap = 13;
                        hitEffect = blastExplosion; shootEffect = shootBig;
                        length = 280; width = 18;
                        serrationLenScl = 3; serrationSpaceOffset = 50;
                        serrations = 40; serrationWidth = 4;
                    }};
                }}
            );
        }};

        summit = new UnitType("cerberian-summit") {{
            constructor = UnitEntity::create; flying = true; lowAltitude = true; health = 700; speed = 1.7f;
            accel = 0.04f; drag = 0.016f; range = 140; hitSize = 20;
            forceMultiTarget = true; armor = 5; engineOffset = 12; engineSize = 3;
            localizedName = "Summit";
            description = "A unit similar to a Zenith but the missile launchers fire barrages of missiles instead of single ones.";
            weapons.add(
                new Weapon("cerberian-zenith-missiles") {{
                    reload = 40; x = 7; rotate = true; shake = 1;
                    shoot = new ShootPattern() {{ shots = 4; shotDelay = 7; }};
                    velocityRnd = 0.2f; shootSound = shootMissile; inaccuracy = 5;
                    bullet = new MissileBulletType() {{
                        speed = 3; damage = 8; width = 8; height = 8;
                        shrinkY = 0; drag = -0.003f; homingRange = 60;
                        keepVelocity = false; splashDamageRadius = 28;
                        splashDamage = 16; lifetime = 50;
                        trailColor = Color.valueOf("5461c5"); backColor = Color.valueOf("5461c5");
                        frontColor = Color.valueOf("6d7dff");
                        hitEffect = blastExplosion; despawnEffect = blastExplosion;
                        weaveScale = 6; weaveMag = 1;
                    }};
                }}
            );
        }};

        nephila = new UnitType("cerberian-nephila") {{
            health = 1000; armor = 5; hitSize = 15; rotateSpeed = 3;
            speed = 0.54f; drag = 0.4f;
            localizedName = "Nephila";
            description = "Like the Spiroct unit but steals more life using enhanced sapping lasers.";
            constructor = LegsUnit::create; hovering = true;
            legCount = 6; legForwardScl = 0.8f; legMoveSpace = 1.4f;
            legBaseOffset = 2; shadowElevation = 0.3f; groundLayer = 75;
            weapons.addAll(
                new Weapon("cerberian-spiroct-weapon") {{
                    shootY = 4; reload = 14; ejectEffect = Fx.none;
                    recoil = 2; rotate = true; shootSound = shootSap;
                    x = 8.5f; y = -1.5f;
                    bullet = new SapBulletType() {{
                        sapStrength = 0.59f; length = 80; damage = 24;
                        shootEffect = shootSmall; hitColor = Color.valueOf("bf92f9");
                        despawnEffect = Fx.none; width = 0.55f;
                        lifetime = 35; knockback = -1.24f;
                    }};
                }},
                new Weapon("cerberian-mount-green-weapon") {{
                    reload = 18; rotate = true; x = 4; y = 3;
                    shootSound = shootSap;
                    bullet = new SapBulletType() {{
                        sapStrength = 0.86f; length = 40; damage = 18;
                        shootEffect = shootSmall; hitColor = Color.valueOf("bf92f9");
                        despawnEffect = Fx.none; width = 0.4f;
                        lifetime = 25; knockback = -0.65f;
                    }};
                }}
            );
        }};

        setosus = new UnitType("cerberian-setosus") {{
            health = 30000; armor = 19; hitSize = 26; rotateSpeed = 1.9f;
            speed = 0.53333f; drag = 0.1f; lightRadius = 140; drownTimeMultiplier = 3;
            localizedName = "Setosus";
            description = "A Toxopid-like unit. The secondary cannons shoot out sapping lasers instead of normal ones. Equipped with significantly more armor and attack power than a regular Toxopid.";
            constructor = LegsUnit::create; hovering = true;
            shadowElevation = 0.95f; groundLayer = 75;
            legCount = 8; legMoveSpace = 0.8f; legPairOffset = 3;
            legLength = 75; legExtension = -20; legBaseOffset = 8;
            stepShake = 1; legLengthScl = 0.93f; rippleScale = 3;
            legSpeed = 0.19f; legSplashDamage = 80; legSplashRange = 60;
            weapons.addAll(
                new Weapon("cerberian-large-green-mount") {{
                    y = -5; x = 11; shootY = 7; reload = 17;
                    rotateSpeed = 2; ejectEffect = Fx.none;
                    shootSound = shootSap; rotate = true; recoil = 1;
                    shoot = new ShootSpread(2, 17) {{ shotDelay = 8; }};
                    bullet = new SapBulletType() {{
                        sapStrength = 0.1f; length = 90; damage = 180;
                        width = 0.7f; shootEffect = shootSmall;
                        knockback = -1.4f; lifetime = 45;
                    }};
                }},
                new Weapon("cerberian-toxopid-cannon") {{
                    y = -14; x = 0; shootY = 22; mirror = false;
                    reload = 210; shake = 10; recoil = 10;
                    rotateSpeed = 1; rotate = true;
                    shootSound = shootArtillery; ejectEffect = casing3;
                    rotationLimit = 80;
                    bullet = new ArtilleryBulletType(3f, 18f) {{
                        hitEffect = sapExplosion; knockback = 2; lifetime = 80;
                        width = 25; height = 25; collidesTiles = true; collides = true;
                        splashDamageRadius = 80; splashDamage = 24;
                        backColor = Color.valueOf("6d56bf"); frontColor = Color.valueOf("bf92f9");
                        lightning = 5; lightningLength = 20;
                        smokeEffect = shootBigSmoke; hitShake = 10;
                        lightRadius = 40; lightColor = Color.valueOf("665c9f"); lightOpacity = 0.6f;
                        status = FRStatus.sapped; statusDuration = 600;
                        fragLifeMin = 0.3f; fragBullets = 9;
                        fragBullet = new ArtilleryBulletType(2.3f, 4f) {{
                            hitEffect = sapExplosion; knockback = 0.8f; lifetime = 90;
                            width = 20; height = 20; collidesTiles = false;
                            splashDamageRadius = 70; splashDamage = 8;
                            backColor = Color.valueOf("6d56bf"); frontColor = Color.valueOf("bf92f9");
                            lightning = 2; lightningLength = 5;
                            smokeEffect = shootBigSmoke; hitShake = 5;
                            lightRadius = 30; lightColor = Color.valueOf("665c9f"); lightOpacity = 0.5f;
                            status = FRStatus.sapped; statusDuration = 600;
                        }};
                    }};
                }}
            );
        }};

        baton = new UnitType("cerberian-baton") {{
            constructor = MechUnit::create; health = 9000; armor = 10; speed = 0.36f; hitSize = 22;
            rotateSpeed = 2.1f; drownTimeMultiplier = 4; singleTarget = true;
            localizedName = "Baton";
            description = "A re-manufactured version of the Scepter unit.";
            weapons.addAll(
                new Weapon("cerberian-scepter-weapon") {{
                    top = false; y = 1; x = 16; shootY = 8;
                    reload = 45; recoil = 5; shake = 2; ejectEffect = casing3;
                    shootSound = Sounds.shoot; inaccuracy = 3;
                    shoot = new ShootPattern() {{ shots = 3; shotDelay = 4; }};
                    bullet = new BasicBulletType(7f, 60f) {{
                        width = 11; height = 20; lifetime = 25;
                        shootEffect = shootBig;
                        lightning = 2; lightningLength = 7;
                        lightningColor = Color.valueOf("6d7dff"); lightningDamage = 22;
                    }};
                }},
                new Weapon("cerberian-mount-weapon") {{
                    reload = 13; x = 8.5f; y = 6; rotate = true;
                    ejectEffect = casing1;
                    bullet = new LightningBulletType() {{
                        lightningLength = 35; lightningDamage = 14; pierce = false;
                    }};
                }},
                new Weapon("cerberian-mount-weapon") {{
                    reload = 16; x = 8.5f; y = -7; rotate = true;
                    ejectEffect = casing1;
                    bullet = new BasicBulletType(3f, 14f) {{ lifetime = 50; width = 7; height = 9; }};
                }}
            );
        }};
    }
}
