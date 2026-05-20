package fadingrevelations.content;

import arc.graphics.*;
import arc.math.*;
import arc.struct.*;
import mindustry.Vars;
import mindustry.content.*;
import mindustry.core.*;
import mindustry.ctype.*;
import mindustry.entities.*;
import mindustry.entities.bullet.*;
import mindustry.entities.effect.*;
import mindustry.entities.part.*;
import mindustry.entities.pattern.*;
import mindustry.gen.*;
import mindustry.type.*;
import mindustry.world.*;
import mindustry.world.blocks.defense.*;
import mindustry.world.blocks.defense.turrets.*;
import mindustry.world.consumers.*;
import mindustry.world.draw.*;
import mindustry.world.meta.*;

import static mindustry.content.Items.*;
import static mindustry.content.Liquids.*;
import static mindustry.gen.Sounds.*;
import static fadingrevelations.content.FRFx.*;
import static mindustry.type.ItemStack.*;

public class FRTurrets {
    
    public static Block
        accel, bigSwarmer, caats, corruptedCyclone, gattling, ignitor, interitus,
        mineLauncher, missileBattery, missileSilo, mortar, oreTurret, ringTurret,
        sear, shotgun, sniper, sunflare, trio, upgradeTurret,
    
        uhlan, megaMeltdown, lightningChaingun, kugelblitz, diffract, cavalry,
        bigSegment, bigScatter, bigParallax, bigArc, airArc, absole, statusWave,
    
        zephyr, weave, sprunkler, batter,
    
        constructionPylon;

    public static void load() {
        

        accel = new ItemTurret("accel") {{
            localizedName = "Accel";
            requirements(Category.turret, with(copper, 850, metaglass, 500, silicon, 500, plastanium, 120, surgeAlloy, 100));
            size = 4;
            reload = 360f;
            range = 420f;
            shootSound = Sounds.shootSpectre;
            unitSort = UnitSorts.strongest;
            shake = 5f;
            recoil = 5f;
            rotateSpeed = 2f;
            targetAir = true;
            coolantMultiplier = 0.5f;
            consumePower(6f);
            consumeCoolant(1f);
            shoot = new ShootPattern() {{ firstShotDelay = 35f; }};
            cooldownTime = 300f;
            ammoPerShot = 2;
            maxAmmo = 20;
            scaledHealth = 140;
            ammoTypes = ObjectMap.of(
                Items.surgeAlloy, new MissileBulletType() {{
                    speed = 6f; lifetime = 72f; damage = 900f;
                    splashDamage = 250f; splashDamageRadius = 24f;
                    pierce = true; pierceBuilding = false;
                    hitSound = Sounds.shootMissileLarge;
                    despawnSound = Sounds.shootMissileLarge;
                    shrinkX = -1f; shrinkY = -0.5f;
                    sprite = "shell";
                    height = 34f; width = 22f;
                    status = StatusEffects.shocked;
                    frontColor = Color.white; backColor = Color.valueOf("f3e979");
                    trailColor = Color.valueOf("f3e979");
                    trailWidth = 8f; trailLength = 50; trailChance = -1;
                    lightColor = Color.valueOf("f3e979");
                    lightRadius = 40f; lightOpacity = 0.7f;
                    shootEffect = Fx.shootBig2;
                    smokeEffect = new MultiEffect(
                        new ParticleEffect() {{
                            particles = 8; length = 40f; baseLength = 50f; cone = 20f;
                            sizeFrom = 6f; sizeTo = 2f; lifetime = 30f;
                            colorFrom = Color.valueOf("f3e979"); colorTo = Color.white;
                        }},
                        new ParticleEffect() {{
                            particles = 8; length = 40f; baseLength = 50f; cone = 20f;
                            sizeFrom = 6f; sizeTo = 2f; lifetime = 30f;
                            colorFrom = Color.white; colorTo = Color.valueOf("f3e979");
                        }}
                    );
                    hitEffect = new MultiEffect(
                        new WaveEffect() {{
                            colorFrom = Color.valueOf("f3e979"); colorTo = Color.white;
                            sizeFrom = 2f; sizeTo = 60f;
                        }},
                        new ParticleEffect() {{
                            particles = 22; length = 60f; baseLength = 50f;
                            interp = Interp.pow2Out; sizeInterp = Interp.pow2Out;
                            sizeFrom = 12f; sizeTo = 2f;
                        }}
                    );
                    spawnBullets = Seq.with(
                        new LaserBulletType() {{
                            damage = 100f; pierce = true; pierceBuilding = false;
                            length = 432f; lifetime = 120f; width = 28f;
                            sideWidth = 1.5f; sideAngle = 20f; sideLength = 80f;
                            colors = new Color[]{Color.valueOf("f3e979"), Color.valueOf("d99f6b"), Color.white};
                        }}
                    );
                    trailEffect = trailBezierGold;
                }},
                FRItems.steelAlloy, new MissileBulletType() {{
                    speed = 6f; lifetime = 72f; damage = 1100f;
                    splashDamage = 350f; splashDamageRadius = 24f;
                    pierce = true; pierceBuilding = false;
                    hitSound = Sounds.shootMissileLarge;
                    despawnSound = Sounds.shootMissileLarge;
                    shrinkX = -1f; shrinkY = -0.5f;
                    sprite = "shell";
                    height = 34f; width = 22f;
                    status = StatusEffects.shocked;
                    frontColor = Color.white; backColor = Color.valueOf("dbaf85");
                    trailColor = Color.valueOf("dbaf85");
                    trailWidth = 8f; trailLength = 50; trailChance = -1;
                    lightColor = Color.valueOf("dbaf85");
                    lightRadius = 40f; lightOpacity = 0.7f;
                    shootEffect = Fx.shootBig2;
                    smokeEffect = new MultiEffect(
                        new ParticleEffect() {{
                            particles = 8; length = 40f; baseLength = 50f; cone = 20f;
                            sizeFrom = 6f; sizeTo = 2f; lifetime = 30f;
                            colorFrom = Color.valueOf("dbaf85"); colorTo = Color.white;
                        }},
                        new ParticleEffect() {{
                            particles = 8; length = 40f; baseLength = 50f; cone = 20f;
                            sizeFrom = 6f; sizeTo = 2f; lifetime = 30f;
                            colorFrom = Color.white; colorTo = Color.valueOf("dbaf85");
                        }}
                    );
                    hitEffect = new MultiEffect(
                        new WaveEffect() {{
                            colorFrom = Color.valueOf("f3e979"); colorTo = Color.white;
                            sizeFrom = 2f; sizeTo = 60f;
                        }},
                        new ParticleEffect() {{
                            particles = 22; length = 60f; baseLength = 50f;
                            interp = Interp.pow2Out; sizeInterp = Interp.pow2Out;
                            sizeFrom = 12f; sizeTo = 2f;
                        }}
                    );
                    spawnBullets = Seq.with(
                        new LaserBulletType() {{
                            damage = 150f; pierce = true; pierceBuilding = false;
                            length = 432f; lifetime = 120f; width = 28f;
                            sideWidth = 1.5f; sideAngle = 20f; sideLength = 80f;
                            colors = new Color[]{Color.valueOf("a3506e"), Color.valueOf("dbaf85"), Color.white};
                        }}
                    );
                    trailEffect = trailBezierPurple;
                }}
            );

            drawer = new DrawTurret() {{
                parts = Seq.with(
                    new RegionPart() {{
                        suffix = "-back"; progress = PartProgress.warmup;
                        mirror = true; under = true;
                        moveX = 2.66667f; moveY = -0.5f; moveRot = -40f;
                        moves = Seq.with(
                            new DrawPart.PartMove() {{ progress = PartProgress.warmup; moveY = -2f; moveRot = 3f; }},
                            new DrawPart.PartMove() {{ progress = PartProgress.recoil; moveX = 2f; moveY = -1f; moveRot = -25f; }}
                        );
                    }},
                    new RegionPart() {{
                        suffix = "-front"; progress = PartProgress.recoil;
                        mirror = true; under = true;
                        moveX = 2f; moveY = -1f; moveRot = -14f;
                    }}
                );
            }};
        }};

        trio = new ItemTurret("trio") {{
            localizedName = "Trio";
            requirements(Category.turret, with(copper, 70, lead, 35));
            size = 2;
            health = 500;
            reload = 48f;
            range = 120f;
            inaccuracy = 3f;
            targetAir = true;
            consumeCoolant(0.1f);
            shoot = new ShootAlternate(3f) {{ shots = 5; }};
            ammoTypes = ObjectMap.of(
                Items.copper, new BasicBulletType() {{
                    speed = 3f; damage = 14f; lifetime = 50f; width = 8f; height = 8f;
                    ammoMultiplier = 3f;
                    shootEffect = bezierBurstOrange;
                    smokeEffect = smokeBezierDark;
                }},
                Items.graphite, new BasicBulletType() {{
                    speed = 3f; damage = 17f; ammoMultiplier = 6f;
                    shootEffect = bezierBurstBlue;
                    smokeEffect = smokeBezierDark;
                }},
                Items.pyratite, new BasicBulletType() {{
                    speed = 3f; damage = 16f; ammoMultiplier = 3f;
                    status = StatusEffects.burning;
                    makeFire = true;
                    shootEffect = bezierBurstRed;
                    smokeEffect = smokeBezierDark;
                }},
                Items.silicon, new BasicBulletType() {{
                    speed = 3f; damage = 18f; ammoMultiplier = 7f;
                    homingPower = 0.9f; homingRange = 120f;
                    shootEffect = bezierBurstCyan;
                    smokeEffect = smokeBezierDark;
                    trailEffect = trailBezierCyan;
                }}
            );
            drawer = new DrawTurret() {{
                parts = Seq.with(
                    new RegionPart() {{
                        suffix = "-back"; progress = PartProgress.warmup;
                        mirror = true; under = true;
                        moveX = 1.35f; moveY = 1.5f; moveRot = -22f;
                        moves = Seq.with(
                            new DrawPart.PartMove() {{ progress = PartProgress.recoil; moveRot = -32f; moveY = -2f; }}
                        );
                    }}
                );
            }};
        }};

        upgradeTurret = new ItemTurret("upgrade-turret") {{
            localizedName = "Van Turretheim";
            requirements(Category.turret, with(copper, 400, lead, 300, titanium, 200, plastanium, 100));
            size = 2;
            health = 900;
            reload = 72f;
            range = 200f;
            inaccuracy = 6f;
            maxAmmo = 600;
            ammoPerShot = 1;
            targetAir = true;
            targetGround = true;
            targetHealing = true;
            shoot = new ShootPattern() {{ shots = 3; }};

            ammoTypes = ObjectMap.of(
                FRItems.ammoLevel1, new BasicBulletType() {{
                    damage = 31f; speed = 8f; lifetime = 25f;
                    splashDamage = 2f; splashDamageRadius = 5f; ammoMultiplier = 2f;
                    shootEffect = bezierBurstWhite; hitEffect = hitBezierWhite;
                }},
                FRItems.ammoLevel2, new BasicBulletType() {{
                    damage = 40f; speed = 12f; pierce = true;
                    splashDamage = 8f; splashDamageRadius = 10f; ammoMultiplier = 2f;
                    shootEffect = bezierBurstBlue; hitEffect = hitBezierBlue;
                }},
                FRItems.ammoLevel3, new MissileBulletType() {{
                    damage = 50f; speed = 10f;
                    splashDamage = 14f; splashDamageRadius = 20f; ammoMultiplier = 1f;
                    shootEffect = bezierBurstRed; hitEffect = hitBezierRed;
                    trailEffect = trailBezierRed;
                }},
                FRItems.healAmmo, new LaserBoltBulletType() {{
                    damage = 0f; healPercent = 5f;
                    collidesTeam = true;
                    shootEffect = bezierBurstGreen; hitEffect = hitBezierGreen;
                }},
                FRItems.livingSteel, new BasicBulletType() {{
                    damage = 40f; speed = 8f;
                    splashDamage = 8f; splashDamageRadius = 10f;
                    status = StatusEffects.slow; statusDuration = 20f;
                    shootEffect = bezierBurstPurple; hitEffect = hitBezierPurple;
                }},
                FRItems.nanoAmmo, new BasicBulletType() {{
                    damage = 9f; speed = 10f;
                    splashDamage = 1f; splashDamageRadius = 1f;
                    status = StatusEffects.melting; statusDuration = 8000000f;
                    shootEffect = bezierBurstPink; hitEffect = hitBezierWhite;
                }},
                FRItems.steelAlloy, new BasicBulletType() {{
                    damage = 61f; speed = 10f;
                    splashDamage = 12f; splashDamageRadius = 14f;
                    ammoMultiplier = 5f;
                    lightning = 3; lightningLength = 4; lightningDamage = 9f;
                    shootEffect = Fx.shootBig; hitEffect = hitBezierGold;
                    trailEffect = trailBezierGold;
                }}
            );
        }};

        
        
        

        bigSwarmer = new ItemTurret("big-swarmer") {{
            localizedName = "Assail";
            requirements(Category.turret, with(graphite, 200, silicon, 200, titanium, 300, plastanium, 150));
            size = 3; health = 1800; reload = 37f; range = 190f;
            shootCone = 20f; inaccuracy = 1f; maxAmmo = 75;
            coolantMultiplier = 2.8f;
            consumeCoolant(0.6f);
            shoot = new ShootAlternate(7f) {{ shots = 3; }};
            ammoTypes = ObjectMap.of(
                Items.blastCompound, new MissileBulletType() {{
                    damage = 10f; speed = 6f; lifetime = 30f;
                    splashDamage = 42f; splashDamageRadius = 30f;
                    status = StatusEffects.blasted; ammoMultiplier = 5f;
                    width = 8f; height = 8f; shrinkY = 0f;
                    hitEffect = Fx.blastExplosion; despawnEffect = Fx.blastExplosion;
                    shootEffect = bezierBurstOrange; trailEffect = trailBezierOrange;
                }},
                Items.pyratite, new MissileBulletType() {{
                    damage = 14f; speed = 6f;
                    splashDamage = 40f; splashDamageRadius = 20f;
                    homingPower = 0.08f;
                    status = StatusEffects.burning; ammoMultiplier = 5f;
                    width = 8f; height = 8f; shrinkY = 0f;
                    hitEffect = Fx.blastExplosion; despawnEffect = Fx.blastExplosion;
                    shootEffect = bezierBurstRed; trailEffect = trailBezierRed;
                }},
                Items.surgeAlloy, new MissileBulletType() {{
                    damage = 24f; speed = 6f;
                    splashDamage = 60f; splashDamageRadius = 25f;
                    ammoMultiplier = 4f;
                    lightning = 2; lightningLength = 10; lightningDamage = 15f;
                    width = 8f; height = 8f; shrinkY = 0f;
                    hitEffect = Fx.blastExplosion; despawnEffect = Fx.blastExplosion;
                    shootEffect = bezierBurstGold; trailEffect = trailBezierGold;
                }},
                FRItems.steelAlloy, new MissileBulletType() {{
                    damage = 30f; speed = 6f;
                    splashDamage = 71f; splashDamageRadius = 25f;
                    ammoMultiplier = 4f;
                    lightning = 3; lightningLength = 12; lightningDamage = 18f;
                    width = 8f; height = 8f; shrinkY = 0f;
                    hitEffect = Fx.blastExplosion; despawnEffect = Fx.blastExplosion;
                    shootEffect = bezierBurstPurple; trailEffect = trailBezierPurple;
                }}
            );
        }};

        caats = new ItemTurret("caats") {{
            localizedName = "CAATS";
            requirements(Category.turret, with(graphite, 1));
            size = 5; health = 11375; reload = 90f; range = 480f;
            maxAmmo = 1000;
            minWarmup = 0.99f; shootWarmupSpeed = 0.05f; warmupMaintainTime = 600f;
            targetGround = false;
            buildVisibility = BuildVisibility.editorOnly;
            consumePower(10f);
            shoot = new ShootBarrel() {{
                barrels = new float[9];
                shots = 3; shotDelay = 10f; firstShotDelay = 120f;
            }};
            ammoTypes = ObjectMap.of(
                FRItems.livingSteel, new MissileBulletType() {{
                    sprite = "aa-missile"; width = 20f; height = 40f;
                    speed = 6f; lifetime = 90f; damage = 1500f;
                    splashDamage = 800f; splashDamageRadius = 80f;
                    homingPower = 0.9f;
                    frontColor = Color.valueOf("9e78dc"); backColor = Color.valueOf("6d0071");
                    trailColor = Color.valueOf("9e78dc");
                    fragBullets = 3; fragSpread = 120f;
                    fragBullet = new LaserBulletType() {{
                        length = 120f; damage = 600f; width = 20f;
                        colors = new Color[]{Color.valueOf("8c0291"), Color.valueOf("9e78dc"), Color.white};
                    }};
                    chargeEffect = new MultiEffect(
                        new WaveEffect() {{ sizeFrom = 0f; sizeTo = 15f; sides = 3; colorFrom = Color.valueOf("6d7dff"); colorTo = Color.valueOf("5461c5"); }},
                        new WaveEffect() {{ sizeFrom = 0f; sizeTo = 20f; sides = 3; colorFrom = Color.valueOf("6d7dff"); colorTo = Color.valueOf("5461c5"); }},
                        new WaveEffect() {{ sizeFrom = 0f; sizeTo = 25f; sides = 3; colorFrom = Color.valueOf("6d7dff"); colorTo = Color.valueOf("5461c5"); }}
                    );
                    hitEffect = new MultiEffect(
                        new WaveEffect() {{ sizeFrom = 0f; sizeTo = 30f; sides = 3; colorFrom = Color.valueOf("9e78dc"); colorTo = Color.valueOf("8c0291"); }},
                        new WaveEffect() {{ sizeFrom = 0f; sizeTo = 40f; sides = 3; colorFrom = Color.valueOf("9e78dc"); colorTo = Color.valueOf("8c0291"); }},
                        new WaveEffect() {{ sizeFrom = 0f; sizeTo = 50f; sides = 3; colorFrom = Color.valueOf("9e78dc"); colorTo = Color.valueOf("8c0291"); }}
                    );
                    trailEffect = trailBezierPurple;
                }}
            );
            drawer = new DrawTurret() {{
                basePrefix = "cerberian-";
                parts = Seq.with(
                    new ShapePart() {{
                        hollow = true; sides = 3;
                        radius = 6f; radiusTo = 7f;
                        rotateSpeed = 1.5f;
                        color = Color.valueOf("6d7dff"); colorTo = Color.valueOf("5461c5");
                    }},
                    new ShapePart() {{
                        hollow = true; sides = 3;
                        radius = 6f; radiusTo = 7f;
                        rotateSpeed = -1.5f;
                        color = Color.valueOf("6d7dff"); colorTo = Color.valueOf("5461c5");
                    }},
                    new RegionPart() {{
                        suffix = "-body"; progress = PartProgress.warmup;
                        mirror = true; moveX = 5f; moveY = -1f; moveRot = -5f;
                    }},
                    new RegionPart() {{
                        suffix = "-blade"; progress = PartProgress.warmup;
                        mirror = true; under = true;
                        moveX = 10f; moveY = 5f;
                        moves = Seq.with( new DrawPart.PartMove() {{ progress = PartProgress.recoil; }} );
                    }}
                );
            }};
        }};

        corruptedCyclone = new ItemTurret("corrupted-cyclone") {{
            localizedName = "Hurricane";
            requirements(Category.turret, with(copper, 500, titanium, 300, plastanium, 150));
            size = 3; health = 1905; reload = 9f; range = 224f;
            inaccuracy = 7f; xRand = 4f;
            targetAir = true;
            ammoTypes = ObjectMap.of(
                Items.metaglass, new FlakBulletType() {{
                    damage = 12f; speed = 4f; lifetime = 55f;
                    splashDamage = 42f; splashDamageRadius = 25f;
                    reloadMultiplier = 0.8f;
                    fragBullets = 4;
                    fragBullet = new BasicBulletType() {{ damage = 5f; speed = 3f; lifetime = 20f; }};
                    shootEffect = bezierBurstCyan; hitEffect = hitBezierCyan;
                }},
                Items.blastCompound, new FlakBulletType() {{
                    damage = 14f; splashDamage = 46f; splashDamageRadius = 60f;
                    ammoMultiplier = 5f; status = StatusEffects.blasted;
                    shootEffect = bezierBurstOrange; hitEffect = hitBezierOrange;
                }},
                Items.plastanium, new FlakBulletType() {{
                    damage = 28f; splashDamage = 68f; splashDamageRadius = 40f;
                    fragBullets = 6;
                    fragBullet = new BasicBulletType() {{ damage = 8f; speed = 3f; lifetime = 20f; }};
                    hitEffect = Fx.plasticExplosion;
                    shootEffect = bezierBurstGold;
                }},
                Items.surgeAlloy, new FlakBulletType() {{
                    damage = 18f; splashDamage = 82f; splashDamageRadius = 38f;
                    ammoMultiplier = 5f;
                    lightning = 2; lightningLength = 7; lightningDamage = 15f;
                    shootEffect = bezierBurstGold; hitEffect = hitBezierGold;
                }},
                FRItems.steelAlloy, new FlakBulletType() {{
                    damage = 25f; splashDamage = 91f; splashDamageRadius = 38f;
                    ammoMultiplier = 5f;
                    lightning = 3; lightningLength = 9; lightningDamage = 19f;
                    shootEffect = bezierBurstPurple; hitEffect = hitBezierPurple;
                }}
            );
        }};

        gattling = new ItemTurret("gattling") {{
            localizedName = "Gattling";
            requirements(Category.turret, with(copper, 500, lead, 300, titanium, 200, plastanium, 85));
            size = 4; health = 900; reload = 2f; range = 320f;
            maxAmmo = 600; ammoPerShot = 1;
            inaccuracy = 10f;
            targetAir = false;
            hasLiquids = true; liquidCapacity = 20f;
            ammoTypes = ObjectMap.of(
                FRItems.ammoLevel1, new BasicBulletType() {{
                    damage = 12f; speed = 12f; lifetime = 25f;
                    splashDamage = 5f; splashDamageRadius = 5f; ammoMultiplier = 10f;
                    shootEffect = bezierBurstWhite; hitEffect = hitBezierWhite;
                }},
                FRItems.ammoLevel2, new BasicBulletType() {{
                    damage = 18f; speed = 12f; pierce = true;
                    splashDamage = 12f; splashDamageRadius = 10f; ammoMultiplier = 15f;
                    shootEffect = bezierBurstBlue; hitEffect = hitBezierBlue;
                }},
                FRItems.ammoLevel3, new BasicBulletType() {{
                    damage = 22f; speed = 12f; pierce = true;
                    splashDamage = 25f; splashDamageRadius = 12f; ammoMultiplier = 15f;
                    shootEffect = bezierBurstRed; hitEffect = hitBezierRed;
                }},
                FRItems.homingAmmo, new MissileBulletType() {{
                    damage = 32f; speed = 12f; pierce = true;
                    splashDamage = 18f; splashDamageRadius = 14f;
                    homingPower = 0.2f; status = StatusEffects.burning;
                    shootEffect = bezierBurstOrange; hitEffect = hitBezierOrange;
                    trailEffect = trailBezierOrange;
                }},
                FRItems.livingSteel, new BasicBulletType() {{
                    damage = 30f; speed = 12f;
                    splashDamage = 10f; splashDamageRadius = 6f;
                    status = StatusEffects.slow; ammoMultiplier = 15f;
                    shootEffect = bezierBurstPurple; hitEffect = hitBezierPurple;
                }},
                FRItems.nanoAmmo, new BasicBulletType() {{
                    damage = 5f; speed = 12f;
                    splashDamage = 1f; splashDamageRadius = 1f;
                    status = StatusEffects.melting; statusDuration = 8000000f; ammoMultiplier = 15f;
                    shootEffect = bezierBurstPink; hitEffect = hitBezierWhite;
                }},
                FRItems.steelAlloy, new BasicBulletType() {{
                    damage = 9f; speed = 12f;
                    splashDamage = 16f; splashDamageRadius = 9f;
                    lightning = 2; lightningLength = 5; lightningDamage = 11f; ammoMultiplier = 15f;
                    shootEffect = bezierBurstGold; hitEffect = hitBezierGold;
                    trailEffect = trailBezierGold;
                }}
            );
        }};

        ignitor = new ItemTurret("ignitor") {{
            localizedName = "Ignitor";
            requirements(Category.turret, with(copper, 550, graphite, 400, thorium, 220, FRItems.cryogenicAlloy, 80));
            size = 5; reload = 45f; range = 120f;
            shake = 5f; recoil = 5f;
            shootCone = 45f; inaccuracy = 9f; coolantMultiplier = 0.6f;
            consumeCoolant(0.5f);
            shoot = new ShootSpread(7, 15f);
            ammoTypes = ObjectMap.of(
                Items.titanium, new ShrapnelBulletType() {{
                    length = 130f; damage = 70f; width = 12f;
                    ammoMultiplier = 5f; reloadMultiplier = 1.3f;
                    shootEffect = bezierBurstBlue; hitEffect = hitBezierBlue;
                }},
                Items.thorium, new ShrapnelBulletType() {{
                    length = 130f; damage = 100f; width = 12f;
                    ammoMultiplier = 6f; toColor = Color.valueOf("f9a3c7");
                    shootEffect = bezierBurstRed; hitEffect = hitBezierRed;
                }},
                FRItems.cryogenicAlloy, new ShrapnelBulletType() {{
                    length = 130f; damage = 180f; width = 12f;
                    ammoMultiplier = 7f; toColor = Color.valueOf("8ce8f1");
                    shootEffect = bezierBurstCyan; hitEffect = hitBezierCyan;
                }}
            );
            drawer = new DrawTurret() {{
                parts = Seq.with(
                    new RegionPart() {{ suffix = "-barrel"; progress = PartProgress.recoil; heatProgress = PartProgress.recoil; moveY = -4f; }},
                    new RegionPart() {{ suffix = "-top"; }}
                );
            }};
        }};

        interitus = new ItemTurret("interitus") {{
            localizedName = "Interitus";
            requirements(Category.turret, with(copper, 2000, lead, 1700, graphite, 1000, silicon, 1000, thorium, 700, Items.phaseFabric, 350, FRItems.steelAlloy, 200));
            size = 5; health = 2890; reload = 560f; range = 800f;
            targetAir = true; targetGround = false;
            consumePower(5f);
            shoot = new ShootPattern() {{ firstShotDelay = 101f; }};
            ammoTypes = ObjectMap.of(
                Items.surgeAlloy, new BasicBulletType() {{
                    damage = 0f; speed = 0f; spawnUnit = FRTurrets.unit("surge-missile");
                    shootEffect = Fx.shootBig; smokeEffect = Fx.shootSmokeMissile;
                    chargeEffect = new MultiEffect(
                        bezierRingGold, bezierRingGold, bezierRingGold,
                        new ParticleEffect() {{ particles = 12; length = 60f; cone = 360f; colorFrom = Color.valueOf("f3e979"); colorTo = Color.white; lifetime = 40f; }},
                        new ParticleEffect() {{ particles = 8; length = 80f; cone = 360f; colorFrom = Color.valueOf("d99f6b"); colorTo = Color.valueOf("f3e979"); lifetime = 60f; }},
                        new ParticleEffect() {{ particles = 6; length = 100f; cone = 360f; colorFrom = Color.white; colorTo = Color.valueOf("f3e979"); lifetime = 80f; }}
                    );
                }},
                Items.plastanium, new BasicBulletType() {{
                    damage = 0f; speed = 0f; spawnUnit = FRTurrets.unit("plast-missile");
                    shootEffect = Fx.shootBig; smokeEffect = Fx.shootSmokeMissile;
                    chargeEffect = new MultiEffect(
                        bezierRingGreen, bezierRingGreen, bezierRingGreen,
                        new ParticleEffect() {{ particles = 12; length = 60f; cone = 360f; colorFrom = Color.valueOf("9cb664"); colorTo = Color.white; lifetime = 40f; }},
                        new ParticleEffect() {{ particles = 8; length = 80f; cone = 360f; colorFrom = Color.valueOf("edf3a9"); colorTo = Color.valueOf("9cb664"); lifetime = 60f; }}
                    );
                }},
                Items.phaseFabric, new BasicBulletType() {{
                    damage = 0f; speed = 0f; spawnUnit = FRTurrets.unit("phase-missile");
                    shootEffect = Fx.shootBig; smokeEffect = Fx.shootSmokeMissile;
                    chargeEffect = new MultiEffect(
                        bezierRingPurple, bezierRingPurple, bezierRingPurple,
                        new ParticleEffect() {{ particles = 12; length = 60f; cone = 360f; colorFrom = Color.valueOf("9e78dc"); colorTo = Color.white; lifetime = 40f; }},
                        new ParticleEffect() {{ particles = 8; length = 80f; cone = 360f; colorFrom = Color.valueOf("6d0071"); colorTo = Color.valueOf("9e78dc"); lifetime = 60f; }},
                        new ParticleEffect() {{ particles = 6; length = 100f; cone = 360f; colorFrom = Color.white; colorTo = Color.valueOf("9e78dc"); lifetime = 80f; }}
                    );
                }}
            );
        }};

        mineLauncher = new ItemTurret("mine-launcher") {{
            localizedName = "Landmine Launcher";
            requirements(Category.turret, with(copper, 180, lead, 150, graphite, 120, silicon, 100));
            size = 3; health = 800; reload = 300f; range = 200f;
            minWarmup = 0.8f; shootWarmupSpeed = 0.08f;
            targetAir = false;
            shootEffect = Fx.shootTitan; smokeEffect = Fx.shootSmokeTitan;
            ammoTypes = ObjectMap.of(
                Items.blastCompound, new BasicBulletType() {{
                    damage = 0f; speed = 3f; lifetime = 40f;
                    fragBullets = 3; fragSpread = 45f;
                    fragBullet = new BasicBulletType() {{
                        speed = 18f; lifetime = 2f; damage = 0f;
                        fragBullets = 1;
                        fragBullet = new BasicBulletType() {{
                            damage = 75f; speed = 0f; lifetime = 720f;
                            splashDamage = 75f; splashDamageRadius = 45f;
                            sprite = "lml-mine";
                            status = StatusEffects.melting; statusDuration = 240f;
                            shootEffect = bezierBurstRed; hitEffect = hitBezierRed;
                        }};
                    }};
                }},
                FRItems.ammoLevel3, new BasicBulletType() {{
                    damage = 0f; speed = 3f; lifetime = 40f;
                    fragBullets = 3; fragSpread = 45f;
                    fragBullet = new BasicBulletType() {{
                        speed = 18f; lifetime = 2f; damage = 0f;
                        fragBullets = 1;
                        fragBullet = new BasicBulletType() {{
                            damage = 90f; speed = 0f; lifetime = 720f;
                            splashDamage = 75f; splashDamageRadius = 45f;
                            sprite = "lml-mine";
                            status = StatusEffects.melting; statusDuration = 300f;
                            shootEffect = bezierBurstPurple; hitEffect = hitBezierPurple;
                        }};
                    }};
                }}
            );
            drawer = new DrawTurret() {{
                parts = Seq.with(
                    new RegionPart() {{ suffix = "-body"; progress = PartProgress.warmup; moveX = 2f; moveY = 1.1f; }}
                );
            }};
        }};

        missileBattery = new ItemTurret("missile-battery") {{
            localizedName = "Missile Battery";
            requirements(Category.turret, with(lead, 260, metaglass, 200, titanium, 110));
            size = 3; scaledHealth = 110; reload = 570f; range = 304f;
            targetAir = false;
            shoot = new ShootBarrel() {{
                barrels = new float[15]; shots = 15; shotDelay = 6f; firstShotDelay = 120f;
            }};
            ammoTypes = ObjectMap.of(
                Items.pyratite, new BasicBulletType() {{
                    damage = 0f; speed = 0f; instantDisappear = true;
                    ammoMultiplier = 7f; rangeChange = -16f;
                    spawnUnit = FRTurrets.unit("tiny-missile");
                    shootEffect = Fx.shootBig; smokeEffect = Fx.shootSmokeMissile;
                }},
                Items.blastCompound, new BasicBulletType() {{
                    damage = 0f; speed = 0f; instantDisappear = true;
                    ammoMultiplier = 10f;
                    spawnUnit = FRTurrets.unit("tiny-missile-3");
                    shootEffect = Fx.shootBig; smokeEffect = Fx.shootSmokeMissile;
                }},
                Items.plastanium, new BasicBulletType() {{
                    damage = 0f; speed = 0f; instantDisappear = true;
                    ammoMultiplier = 15f; rangeChange = 16f;
                    spawnUnit = FRTurrets.unit("tiny-missile-2");
                    shootEffect = Fx.shootBig; smokeEffect = Fx.shootSmokeMissile;
                }}
            );
            drawer = new DrawTurret() {{
                parts = Seq.with(
                    dronePart("-drone-1", 20f, 0f, 20f),
                    dronePart("-drone-2", 30f, 7f, 25f),
                    dronePart("-drone-3", -20f, 0f, -20f),
                    dronePart("-drone-4", -30f, 7f, -25f)
                );
            }};
        }};

        missileSilo = new ItemTurret("missile-silo") {{
            localizedName = "Nuclear Launch Silo";
            requirements(Category.turret, with(copper, 6000, lead, 5000, graphite, 4000, silicon, 3500, plastanium, 2400, FRItems.steelAlloy, 1800));
            size = 3; health = 300; reload = 3600f; range = 1600f;
            maxAmmo = 10;
            rotateSpeed = 0f; shootCone = 360f;
            targetAir = false;
            consumePower(63.333f);
            ammoTypes = ObjectMap.of(
                FRItems.nuke, new BasicBulletType() {{
                    damage = 0f; speed = 0f; instantDisappear = true;
                    spawnUnit = FRTurrets.unit("nuke-missile");
                    shootEffect = new ParticleEffect() {{
                        particles = 1; spin = 60f; lifetime = 600f;
                        colorFrom = Color.valueOf("c80000"); colorTo = Color.valueOf("ff2424");
                    }};
                }}
            );
        }};

        mortar = new ItemTurret("mortar") {{
            localizedName = "Mortar";
            requirements(Category.turret, with(copper, 120, lead, 100, graphite, 50, metaglass, 65, titanium, 50));
            size = 3; scaledHealth = 110; reload = 360f; range = 320f; minRange = 64f;
            inaccuracy = 4f; velocityRnd = 0.2f;
            targetAir = false;
            itemCapacity = 20;
            consumeCoolant(0.3f); consumePower(4.2f);
            shoot = new ShootPattern() {{ firstShotDelay = 60f; }};
            shootEffect = bezierBurstOrange; smokeEffect = Fx.shootSmokeMissile;
            ammoTypes = ObjectMap.of(
                Items.sporePod, new ArtilleryBulletType() {{
                    sprite = "mortar-shell";
                    speed = 1.6f; lifetime = 130f;
                    splashDamage = 160f; splashDamageRadius = 64f;
                    status = StatusEffects.burning; statusDuration = 780f;
                    incendAmount = 12; incendSpread = 4.1f; incendChance = 1f;
                    makeFire = true; knockback = 0.6f;
                    width = 30f; height = 42f;
                    frontColor = Color.valueOf("9e78dc"); backColor = Color.valueOf("5541b1");
                    chargeEffect = new MultiEffect(
                        new ParticleEffect() {{ particles = 20; length = 80f; cone = 360f; colorFrom = Color.valueOf("9e78dc"); colorTo = Color.valueOf("5541b1"); lifetime = 50f; }},
                        new WaveEffect() {{ sizeFrom = 0f; sizeTo = 20f; colorFrom = Color.valueOf("9e78dc"); colorTo = Color.valueOf("5541b1"); }},
                        new WaveEffect() {{ sizeFrom = 0f; sizeTo = 30f; colorFrom = Color.valueOf("5541b1"); colorTo = Color.valueOf("9e78dc"); }}
                    );
                    hitEffect = new MultiEffect(
                        new ParticleEffect() {{ particles = 24; length = 100f; cone = 360f; colorFrom = Color.valueOf("9e78dc"); colorTo = Color.valueOf("5541b1"); lifetime = 40f; }},
                        new WaveEffect() {{ sizeFrom = 0f; sizeTo = 40f; colorFrom = Color.valueOf("9e78dc"); colorTo = Color.valueOf("5541b1"); }}
                    );
                }}
            );
        }};

        oreTurret = new ItemTurret("ore-turret") {{
            localizedName = "Itemslinger";
            requirements(Category.turret, with(copper, 220, lead, 180, graphite, 90, silicon, 75));
            size = 2; health = 320; reload = 60f; range = 176f;
            targetAir = true;
            ammoTypes = ObjectMap.of(
                Items.coal, new BasicBulletType() {{
                    sprite = "coal-bullet"; speed = 3f; lifetime = 60f;
                    damage = 20f; splashDamage = 25f; splashDamageRadius = 16f;
                    pierceCap = 4; status = StatusEffects.burning; makeFire = true;
                    despawnHit = true;
                    backColor = Color.valueOf("404040"); frontColor = Color.valueOf("2a2a2a");
                    shootEffect = bezierBurstOrange; hitEffect = hitBezierOrange;
                }},
                Items.graphite, new BasicBulletType() {{
                    sprite = "graphite-bullet"; speed = 3f; lifetime = 60f;
                    damage = 30f; splashDamage = 35f; splashDamageRadius = 24f;
                    pierceCap = 6; ammoMultiplier = 3f;
                    despawnHit = true;
                    backColor = Color.valueOf("899bc0"); frontColor = Color.valueOf("626f9b");
                    shootEffect = bezierBurstBlue; hitEffect = hitBezierBlue;
                }},
                Items.sand, new BasicBulletType() {{
                    sprite = "sand-bullet"; speed = 3f; lifetime = 60f;
                    damage = 10f; splashDamage = 15f; splashDamageRadius = 8f;
                    pierceCap = 3; status = StatusEffects.muddy;
                    despawnHit = true;
                    backColor = Color.valueOf("f7cba4"); frontColor = Color.valueOf("d3ae8d");
                    shootEffect = bezierBurstWhite; hitEffect = hitBezierWhite;
                }},
                Items.thorium, new BasicBulletType() {{
                    sprite = "thorium-bullet"; speed = 3f; lifetime = 60f;
                    damage = 50f; splashDamage = 55f; splashDamageRadius = 32f;
                    pierceCap = 8; ammoMultiplier = 4f; homingPower = 0.2f;
                    despawnHit = true;
                    backColor = Color.valueOf("f9a3c7"); frontColor = Color.valueOf("cb8ebf");
                    shootEffect = bezierBurstRed; hitEffect = hitBezierRed;
                }},
                Items.blastCompound, new BasicBulletType() {{
                    sprite = "blast-bullet"; speed = 3f; lifetime = 60f;
                    damage = 35f; splashDamage = 40f; splashDamageRadius = 24f;
                    pierceCap = 7; status = StatusEffects.blasted; makeFire = true;
                    despawnHit = true;
                    backColor = Color.valueOf("ff795e"); frontColor = Color.valueOf("c85c51");
                    shootEffect = bezierBurstOrange; hitEffect = hitBezierOrange;
                }},
                Items.pyratite, new BasicBulletType() {{
                    sprite = "blast-bullet"; speed = 3f; lifetime = 60f;
                    damage = 30f; splashDamage = 35f; splashDamageRadius = 24f;
                    pierceCap = 6; status = StatusEffects.melting; makeFire = true;
                    despawnHit = true;
                    backColor = Color.valueOf("ffaa5f"); frontColor = Color.valueOf("d37f47");
                    shootEffect = bezierBurstRed; hitEffect = hitBezierRed;
                }},
                FRItems.fuelRod, new BasicBulletType() {{
                    sprite = "rod-bullet"; speed = 3f; lifetime = 60f;
                    damage = 75f; splashDamage = 80f; splashDamageRadius = 24f;
                    pierceCap = 9; spin = 1f; ammoMultiplier = 4f;
                    despawnHit = true;
                    backColor = Color.valueOf("31d926"); frontColor = Color.valueOf("2cfa1f");
                    shootEffect = bezierBurstGreen; hitEffect = hitBezierGreen;
                }}
            );
        }};

        ringTurret = new ItemTurret("ring-turret") {{
            localizedName = "CBST";
            requirements(Category.turret, with(copper, 200, lead, 100, graphite, 200, titanium, 200, Items.phaseFabric, 100));
            size = 4; health = 1000; reload = 240f; range = 293f;
            inaccuracy = 0f; recoil = 6f;
            targetAir = true;
            shoot = new ShootPattern() {{ shots = 21; }};
            ammoTypes = ObjectMap.of(
                FRItems.ammoLevel1, new MissileBulletType() {{
                    speed = 2f; lifetime = 140f; damage = 40f;
                    trailWidth = 6f; trailLength = 60; trailChance = -1;
                    lightRadius = 40f; lightOpacity = 0.7f;
                    width = 24f; height = 24f;
                    fragBullets = 10;
                    fragBullet = new BasicBulletType() {{ damage = 15f; speed = 3f; }};
                    shootEffect = bezierBurstWhite; trailEffect = trailBezierGold;
                }},
                FRItems.ammoLevel2, new MissileBulletType() {{
                    speed = 2f; lifetime = 140f; damage = 60f;
                    trailWidth = 6f; trailLength = 60; trailChance = -1;
                    width = 24f; height = 24f;
                    fragBullets = 10;
                    fragBullet = new BasicBulletType() {{ damage = 20f; pierce = true; pierceCap = 4; frontColor = Color.valueOf("7575c8"); }};
                    shootEffect = bezierBurstBlue; trailEffect = trailBezierBlue;
                }},
                FRItems.ammoLevel3, new MissileBulletType() {{
                    speed = 2f; lifetime = 140f; damage = 90f;
                    trailWidth = 6f; trailLength = 60; trailChance = -1;
                    width = 24f; height = 24f;
                    status = StatusEffects.blasted;
                    fragBullets = 10;
                    fragBullet = new BasicBulletType() {{ damage = 25f; status = StatusEffects.blasted; frontColor = Color.valueOf("c85c51"); }};
                    shootEffect = bezierBurstRed; trailEffect = trailBezierRed;
                }},
                FRItems.nanoAmmo, new MissileBulletType() {{
                    speed = 2f; lifetime = 140f; damage = 5f;
                    trailWidth = 6f; trailLength = 60; trailChance = -1;
                    width = 24f; height = 24f;
                    fragBullets = 10;
                    fragBullet = new BasicBulletType() {{ damage = 2f; status = StatusEffects.melting; statusDuration = 8000000f; frontColor = Color.valueOf("570047"); }};
                    shootEffect = bezierBurstPink; trailEffect = trailBezierPink;
                }},
                FRItems.livingSteel, new MissileBulletType() {{
                    speed = 2f; lifetime = 140f; damage = 30f;
                    trailWidth = 6f; trailLength = 60; trailChance = -1;
                    width = 24f; height = 24f;
                    status = FRStatus.sapped;
                    fragBullets = 10;
                    fragBullet = new BasicBulletType() {{ damage = 10f; status = StatusEffects.slow; frontColor = Color.valueOf("8c0291"); }};
                    shootEffect = bezierBurstPurple; trailEffect = trailBezierPurple;
                }},
                FRItems.steelAlloy, new MissileBulletType() {{
                    speed = 2f; lifetime = 140f; damage = 40f;
                    trailWidth = 6f; trailLength = 60; trailChance = -1;
                    width = 24f; height = 24f;
                    status = StatusEffects.shocked;
                    lightning = 3; lightningLength = 8; lightningDamage = 12f;
                    fragBullets = 10;
                    fragBullet = new BasicBulletType() {{ damage = 14f; status = StatusEffects.shocked; frontColor = Color.valueOf("ba6a83"); }};
                    shootEffect = bezierBurstGold; trailEffect = trailBezierGold;
                }}
            );
            drawer = new DrawTurret() {{
                parts = Seq.with(
                    new RegionPart() {{
                        suffix = "-horn"; progress = PartProgress.recoil;
                        mirror = true; under = true;
                        moveY = -2.5f; moveRot = -16f;
                    }}
                );
            }};
        }};

        sear = new ItemTurret("sear") {{
            localizedName = "Sear";
            requirements(Category.turret, with(copper, 50, graphite, 44));
            size = 2; health = 800; reload = 7f; range = 112f;
            targetAir = false;
            ammoTypes = ObjectMap.of(
                Items.coal, new BasicBulletType() {{
                    damage = 24f; speed = 6f; lifetime = 18f;
                    pierce = true; pierceBuilding = true;
                    ammoMultiplier = 5f; status = StatusEffects.burning; makeFire = true;
                    width = 0.01f; height = 0.01f;
                    shootEffect = bezierBurstOrange; smokeEffect = smokeBezierRed;
                }},
                Items.pyratite, new BasicBulletType() {{
                    damage = 34f; speed = 6f; lifetime = 18f;
                    pierce = true; pierceBuilding = true;
                    ammoMultiplier = 7f; status = StatusEffects.burning; makeFire = true;
                    width = 0.01f; height = 0.01f;
                    shootEffect = bezierBurstRed; smokeEffect = smokeBezierRed;
                }},
                FRItems.livingSteel, new BasicBulletType() {{
                    damage = 44f; speed = 6f; lifetime = 18f;
                    pierce = true; pierceBuilding = true;
                    ammoMultiplier = 8f; status = StatusEffects.melting; makeFire = true;
                    width = 0.01f; height = 0.01f;
                    shootEffect = bezierBurstPurple; smokeEffect = smokeBezierPurple;
                }}
            );
        }};

        shotgun = new ItemTurret("shotgun") {{
            localizedName = "Shotgun";
            requirements(Category.turret, with(copper, 225, lead, 180, graphite, 100, titanium, 120));
            size = 3; health = 800; reload = 120f; range = 175f;
            inaccuracy = 16f; maxAmmo = 10;
            targetAir = false;
            shoot = new ShootAlternate(25f) {{ shots = 2; }};
            ammoTypes = ObjectMap.of(
                FRItems.ammoLevel1, new BasicBulletType() {{
                    damage = 50f; speed = 5f; width = 3f; height = 6f;
                    backColor = Color.valueOf("d17f4d"); frontColor = Color.valueOf("b87044");
                    shootEffect = bezierBurstWhite; hitEffect = hitBezierWhite;
                }},
                FRItems.ammoLevel2, new BasicBulletType() {{
                    damage = 75f; speed = 6f; lifetime = 30f; pierce = true;
                    width = 3f; height = 6f;
                    backColor = Color.valueOf("2a4bc2"); frontColor = Color.valueOf("2442ab");
                    shootEffect = bezierBurstBlue; hitEffect = hitBezierBlue;
                }},
                FRItems.ammoLevel3, new BasicBulletType() {{
                    damage = 100f; speed = 6f; lifetime = 30f;
                    width = 3f; height = 6f;
                    backColor = Color.valueOf("c03939"); frontColor = Color.valueOf("a23030");
                    shootEffect = bezierBurstRed; hitEffect = hitBezierRed;
                }},
                FRItems.homingAmmo, new BasicBulletType() {{
                    damage = 120f; speed = 6f; lifetime = 30f;
                    width = 3f; height = 6f;
                    backColor = Color.valueOf("963ac4"); frontColor = Color.valueOf("772c9c");
                    shootEffect = bezierBurstPurple; hitEffect = hitBezierPurple;
                }},
                FRItems.livingSteel, new BasicBulletType() {{
                    damage = 75f; speed = 6f; lifetime = 24f;
                    width = 3f; height = 6f;
                    backColor = Color.valueOf("8b0091"); frontColor = Color.valueOf("af00b5");
                    shootEffect = bezierBurstPurple; hitEffect = hitBezierPurple;
                }},
                FRItems.nanoAmmo, new BasicBulletType() {{
                    damage = 10f; speed = 7f;
                    splashDamage = 1f; splashDamageRadius = 1f;
                    status = StatusEffects.melting; statusDuration = 8000000f;
                    shootEffect = bezierBurstPink; hitEffect = hitBezierWhite;
                }},
                FRItems.steelAlloy, new BasicBulletType() {{
                    damage = 90f; speed = 6f; lifetime = 24f;
                    width = 3f; height = 6f;
                    lightning = 3; lightningLength = 2; lightningDamage = 6f;
                    backColor = Color.valueOf("8b0091"); frontColor = Color.valueOf("af00b5");
                    shootEffect = bezierBurstGold; hitEffect = hitBezierGold;
                }}
            );
        }};

        sniper = new ItemTurret("sniper") {{
            localizedName = "Sniper";
            requirements(Category.turret, with(copper, 255, lead, 180, titanium, 120, plastanium, 55));
            size = 3; health = 1200; reload = 400f; range = 500f;
            inaccuracy = 0f; unitSort = UnitSorts.strongest;
            targetAir = false;
            maxAmmo = 50; coolantMultiplier = 1f;
            consumeCoolant(0.8f);
            ammoTypes = ObjectMap.of(
                FRItems.ammoLevel1, new PointBulletType() {{
                    damage = 300f; speed = 12f; pierce = true; buildingDamageMultiplier = 0.2f;
                    splashDamage = 300f; splashDamageRadius = 16f; ammoMultiplier = 2f;
                    shootEffect = bezierBurstWhite; hitEffect = hitBezierWhite;
                    trailEffect = new ParticleEffect() {{ particles = 16; line = true; colorFrom = Color.white; colorTo = Color.lightGray; lifetime = 15f; }};
                }},
                FRItems.ammoLevel2, new PointBulletType() {{
                    damage = 400f; speed = 12f; pierce = true; buildingDamageMultiplier = 0.2f;
                    splashDamage = 400f; splashDamageRadius = 24f; ammoMultiplier = 1f;
                    shootEffect = bezierBurstBlue; hitEffect = hitBezierBlue;
                    trailEffect = new ParticleEffect() {{ particles = 16; line = true; colorFrom = Color.valueOf("66b1ff"); colorTo = Color.valueOf("e4fdff"); lifetime = 15f; }};
                }},
                FRItems.ammoLevel3, new PointBulletType() {{
                    damage = 500f; speed = 12f; pierce = true; buildingDamageMultiplier = 0.2f;
                    splashDamage = 500f; splashDamageRadius = 24f;
                    shootEffect = bezierBurstRed; hitEffect = hitBezierRed;
                    trailEffect = new ParticleEffect() {{ particles = 16; line = true; colorFrom = Color.valueOf("ff6666"); colorTo = Color.valueOf("ffcccc"); lifetime = 15f; }};
                }},
                FRItems.homingAmmo, new PointBulletType() {{
                    damage = 600f; speed = 12f; pierce = true; buildingDamageMultiplier = 0.2f;
                    splashDamage = 600f; splashDamageRadius = 32f; homingPower = 0.9f;
                    shootEffect = bezierBurstGold; hitEffect = hitBezierGold;
                    trailEffect = new ParticleEffect() {{ particles = 16; line = true; colorFrom = Color.valueOf("f3e979"); colorTo = Color.valueOf("d99f6b"); lifetime = 15f; }};
                }},
                FRItems.nanoAmmo, new PointBulletType() {{
                    damage = 30f; speed = 12f; pierce = true; buildingDamageMultiplier = 0.2f;
                    splashDamage = 30f; splashDamageRadius = 24f;
                    status = StatusEffects.melting; statusDuration = 8000000f;
                    shootEffect = bezierBurstPink; hitEffect = hitBezierWhite;
                    trailEffect = new ParticleEffect() {{ particles = 16; line = true; colorFrom = Color.valueOf("ff69b4"); colorTo = Color.valueOf("ffb6d9"); lifetime = 15f; }};
                }},
                FRItems.livingSteel, new PointBulletType() {{
                    damage = 600f; speed = 12f; pierce = true; buildingDamageMultiplier = 0.2f;
                    splashDamage = 600f; splashDamageRadius = 24f;
                    status = StatusEffects.slow; statusDuration = 250f; ammoMultiplier = 2f;
                    shootEffect = bezierBurstPurple; hitEffect = hitBezierPurple;
                    trailEffect = new ParticleEffect() {{ particles = 16; line = true; colorFrom = Color.valueOf("9e78dc"); colorTo = Color.valueOf("6d0071"); lifetime = 15f; }};
                }},
                FRItems.steelAlloy, new PointBulletType() {{
                    damage = 800f; speed = 12f; pierce = true; buildingDamageMultiplier = 0.2f;
                    splashDamage = 800f; splashDamageRadius = 24f;
                    lightning = 3; lightningLength = 6; lightningDamage = 20f;
                    status = StatusEffects.shocked; ammoMultiplier = 2f;
                    shootEffect = bezierBurstGold; hitEffect = hitBezierGold;
                    trailEffect = new ParticleEffect() {{ particles = 16; line = true; colorFrom = Color.valueOf("f3e979"); colorTo = Color.valueOf("d99f6b"); lifetime = 15f; }};
                }}
            );
        }};

        sunflare = new ItemTurret("sunflare") {{
            localizedName = "Sunflare";
            requirements(Category.turret, with(copper, 2000, lead, 3000, graphite, 2000, titanium, 1500, plastanium, 600, FRItems.steelAlloy, 400));
            size = 4; health = 1720; reload = 6f; range = 240f;
            shoot = new ShootPattern() {{ shots = 12; }};
            ammoTypes = ObjectMap.of(
                Items.coal, new BasicBulletType() {{
                    damage = 3f; speed = 6f; lifetime = 45f;
                    pierce = true; pierceCap = 2; pierceBuilding = true;
                    ammoMultiplier = 10f; status = StatusEffects.burning; makeFire = true;
                    width = 0.01f; height = 0.01f; hittable = false; reflectable = false;
                    smokeEffect = new ParticleEffect() {{ particles = 25; length = 280f; colorFrom = Color.valueOf("ffdd55"); colorTo = Color.valueOf("db401c"); lifetime = 30f; }};
                    hitEffect = Fx.hitFlameSmall;
                    shootEffect = bezierBurstOrange;
                }},
                Items.pyratite, new BasicBulletType() {{
                    damage = 6f; speed = 6f; lifetime = 45f;
                    pierce = true; pierceBuilding = true;
                    ammoMultiplier = 15f; status = StatusEffects.burning; makeFire = true;
                    width = 0.01f; height = 0.01f; hittable = false; reflectable = false;
                    smokeEffect = new ParticleEffect() {{ particles = 25; length = 280f; colorFrom = Color.valueOf("ffdd55"); colorTo = Color.valueOf("db401c"); lifetime = 30f; }};
                    hitEffect = Fx.hitFlameSmall;
                    shootEffect = bezierBurstRed;
                }},
                Items.blastCompound, new BasicBulletType() {{
                    damage = 8f; speed = 6f; lifetime = 45f;
                    pierce = true; pierceBuilding = true;
                    ammoMultiplier = 15f; status = StatusEffects.blasted; makeFire = true;
                    width = 0.01f; height = 0.01f; hittable = false; reflectable = false;
                    smokeEffect = new ParticleEffect() {{ particles = 25; length = 280f; colorFrom = Color.valueOf("ffdd55"); colorTo = Color.valueOf("db401c"); lifetime = 30f; }};
                    hitEffect = Fx.hitFlameSmall;
                    shootEffect = bezierBurstOrange;
                }},
                FRItems.livingSteel, new BasicBulletType() {{
                    damage = 10f; speed = 6f; lifetime = 45f;
                    pierce = true; pierceBuilding = true;
                    ammoMultiplier = 18f; status = StatusEffects.melting; makeFire = true;
                    width = 0.01f; height = 0.01f; hittable = false; reflectable = false;
                    smokeEffect = new ParticleEffect() {{ particles = 25; length = 280f; colorFrom = Color.valueOf("ffdd55"); colorTo = Color.valueOf("db401c"); lifetime = 30f; }};
                    hitEffect = Fx.hitFlameSmall;
                    shootEffect = bezierBurstPurple;
                }},
                FRItems.steelAlloy, new BasicBulletType() {{
                    damage = 14f; speed = 6f; lifetime = 45f;
                    pierce = true; pierceBuilding = true;
                    ammoMultiplier = 25f; status = StatusEffects.melting; makeFire = true;
                    width = 0.01f; height = 0.01f; hittable = false; reflectable = false;
                    lightning = 2; lightningLength = 1; lightningDamage = 6f;
                    smokeEffect = new ParticleEffect() {{ particles = 25; length = 280f; colorFrom = Color.valueOf("ffdd55"); colorTo = Color.valueOf("db401c"); lifetime = 30f; }};
                    hitEffect = Fx.hitFlameSmall;
                    shootEffect = bezierBurstGold;
                }}
            );
        }};

        

        uhlan = new PowerTurret("uhlan") {{
            localizedName = "Uhlan";
            requirements(Category.turret, with(copper, 90, lead, 100, silicon, 75, titanium, 50));
            size = 3; health = 1420; reload = 75f; range = 220f;
            inaccuracy = 3f;
            consumePower(7f);
            shoot = new ShootBarrel() {{
                barrels = new float[9];
                shots = 3; shotDelay = 4.5f; firstShotDelay = 30f;
            }};
            shootType = new LaserBulletType() {{
                length = 210f; width = 20f; damage = 55f;
                colors = new Color[]{Color.valueOf("7382f040"), Color.valueOf("7382f0"), Color.white};
                chargeEffect = bezierRingBlue;
                shootEffect = bezierBurstBlue;
            }};
            drawer = new DrawTurret() {{
                parts = Seq.with(
                    new RegionPart() {{
                        suffix = "-side"; progress = PartProgress.warmup;
                        mirror = true; moveX = 1f; moveY = 2f; under = true;
                        moves = Seq.with( new DrawPart.PartMove() {{ progress = PartProgress.recoil; moveY = -1f; }} );
                    }}
                );
            }};
        }};

        megaMeltdown = new LaserTurret("mega-meltdown") {{
            localizedName = "Armageddon";
            requirements(Category.turret, with(copper, 1600, lead, 600, graphite, 460, silicon, 500, Items.surgeAlloy, 420));
            size = 5; health = 2150; reload = 300f; range = 240f;
            liquidCapacity = 200f;
            shootDuration = 240f;
            consumePower(28f);
            consume(new ConsumeCoolant() {{ amount = 0.6f; }});
            shoot = new ShootSpread(3, 10f);
            shootType = new ContinuousLaserBulletType() {{
                damage = 45f; lifetime = 150f; fadeTime = 10f;
                length = 320f; width = 10f;
                chargeEffect = chargeBezierRed;
                hitEffect = hitBezierRed;
            }};
        }};

        lightningChaingun = new PowerTurret("lightning-chaingun") {{
            localizedName = "Lightning Chaingun";
            requirements(Category.turret, with(copper, 2400, graphite, 1950, silicon, 1800, FRItems.cryogenicAlloy, 500));
            size = 6; health = 5400; reload = 6f; range = 400f;
            minWarmup = 0.75f; shootWarmupSpeed = 0.005f; warmupMaintainTime = 60f;
            consumePower(80f);
            consumeCoolant(2f);
            coolantMultiplier = 0.1f;
            shoot = new ShootAlternate(2f) {{ shots = 14; shotDelay = 8f; }};
            shootType = new BasicBulletType() {{
                width = 12f; height = 72f; speed = 16f; lifetime = 25f; damage = 120f;
                pierce = true; pierceBuilding = true;
                backColor = Color.valueOf("a9d8ff"); frontColor = Color.white;
                mixColorFrom = Color.valueOf("a9d8ff"); mixColorTo = Color.white;
                homingPower = 0.078f; homingDelay = 60f;
                shootEffect = Fx.shootBigColor; smokeEffect = Fx.shootSmokeSquareSparse;
                trailEffect = trailBezierCyan;
                chargeEffect = chargeBezierCyan;
                hitEffect = hitBezierCyan;
            }};
            drawer = new DrawTurret() {{
                parts = Seq.with(
                    new RegionPart() {{ suffix = "-top"; heatColor = Color.valueOf("ffffff4D"); heatProgress = PartProgress.recoil; }},
                    new RegionPart() {{ suffix = "-barrel"; mirror = true; under = true; moveY = -4f; progress = PartProgress.recoil; heatProgress = PartProgress.recoil; heatColor = Color.valueOf("ffffff4D"); }},
                    new RegionPart() {{ suffix = "-wing-1"; mirror = true; under = true; progress = PartProgress.warmup; moveRot = 40f; moveX = 9f; moveY = -5f; }},
                    new RegionPart() {{ suffix = "-wing-2"; mirror = true; under = true; progress = PartProgress.warmup; moveRot = 40f; moveX = 10f; }}
                );
            }};
        }};

        kugelblitz = new ItemTurret("kugelblitz") {{
            localizedName = "Kugelblitz";
            requirements(Category.turret, with(copper, 210, lead, 180, graphite, 110, metaglass, 90, silicon, 80, titanium, 65));
            size = 3; scaledHealth = 125; reload = 600f; range = 208f;
            hasPower = true; targetAir = false;
            consumePower(7f); consumeCoolant(0.3f);
            shoot = new ShootPattern() {{ firstShotDelay = 120f; }};
            ammoTypes = ObjectMap.of(
                Items.titanium, new BasicBulletType() {{
                    sprite = "large-orb"; speed = 0.5f; lifetime = 420f; damage = 14f;
                    pierce = true; pierceBuilding = true; pierceCap = 30; buildingDamageMultiplier = 0.4f;
                    width = 36f; height = 36f; shrinkX = 0f; shrinkY = 0f;
                    backColor = Color.valueOf("7575c8"); frontColor = Color.valueOf("a4b8fa");
                    trailColor = Color.valueOf("7575c8"); trailLength = 12; trailWidth = 2.2f; trailEffect = Fx.missileTrail;
                    intervalBullet = new LightningBulletType() {{ damage = 2f; lightningLength = 14; lightningColor = Color.valueOf("7575c8"); }};
                    intervalBullets = 4; intervalSpread = 360f; bulletInterval = 2f;
                    chargeEffect = new MultiEffect(
                        new ParticleEffect() {{ particles = 20; length = 60f; cone = 360f; colorFrom = Color.valueOf("7575c8"); colorTo = Color.valueOf("a4b8fa"); lifetime = 40f; }},
                        new WaveEffect() {{ sizeFrom = 0f; sizeTo = 15f; colorFrom = Color.valueOf("7575c8"); colorTo = Color.valueOf("a4b8fa"); }},
                        new WaveEffect() {{ sizeFrom = 0f; sizeTo = 25f; colorFrom = Color.valueOf("a4b8fa"); colorTo = Color.valueOf("7575c8"); }}
                    );
                    shootEffect = bezierBurstBlue; hitEffect = hitBezierBlue;
                }},
                Items.plastanium, new BasicBulletType() {{
                    sprite = "large-orb"; speed = 0.5f; lifetime = 420f; damage = 14f;
                    pierce = true; pierceBuilding = true; pierceCap = 30; buildingDamageMultiplier = 0.4f;
                    width = 36f; height = 36f; shrinkX = 0f; shrinkY = 0f;
                    backColor = Color.valueOf("edf3a9"); frontColor = Color.valueOf("9cb664");
                    trailColor = Color.valueOf("9cb664"); trailLength = 12; trailWidth = 2.2f; trailEffect = Fx.missileTrail;
                    intervalBullet = new LightningBulletType() {{ damage = 10f; lightningLength = 14; lightningColor = Color.valueOf("9cb664"); }};
                    intervalBullets = 3; intervalSpread = 360f; bulletInterval = 3f;
                    chargeEffect = new MultiEffect(
                        new ParticleEffect() {{ particles = 20; length = 60f; cone = 360f; colorFrom = Color.valueOf("edf3a9"); colorTo = Color.valueOf("9cb664"); lifetime = 40f; }},
                        new WaveEffect() {{ sizeFrom = 0f; sizeTo = 15f; colorFrom = Color.valueOf("edf3a9"); colorTo = Color.valueOf("9cb664"); }},
                        new WaveEffect() {{ sizeFrom = 0f; sizeTo = 25f; colorFrom = Color.valueOf("9cb664"); colorTo = Color.valueOf("edf3a9"); }}
                    );
                    shootEffect = bezierBurstGreen; hitEffect = hitBezierGreen;
                }},
                Items.surgeAlloy, new BasicBulletType() {{
                    sprite = "large-orb"; speed = 0.5f; lifetime = 420f; damage = 14f;
                    pierce = true; pierceBuilding = true; pierceCap = 30; buildingDamageMultiplier = 0.4f;
                    width = 36f; height = 36f; shrinkX = 0f; shrinkY = 0f;
                    backColor = Color.valueOf("f3e979"); frontColor = Color.white;
                    trailColor = Color.valueOf("f3e979"); trailLength = 12; trailWidth = 2.2f; trailEffect = Fx.missileTrail;
                    intervalBullet = new LightningBulletType() {{ damage = 3f; lightningLength = 14; lightningColor = Color.valueOf("f3e979"); }};
                    intervalBullets = 8; intervalSpread = 360f; bulletInterval = 2f;
                    chargeEffect = new MultiEffect(
                        new ParticleEffect() {{ particles = 20; length = 60f; cone = 360f; colorFrom = Color.valueOf("f3e979"); colorTo = Color.white; lifetime = 40f; }},
                        new WaveEffect() {{ sizeFrom = 0f; sizeTo = 15f; colorFrom = Color.valueOf("f3e979"); colorTo = Color.white; }},
                        new WaveEffect() {{ sizeFrom = 0f; sizeTo = 25f; colorFrom = Color.white; colorTo = Color.valueOf("f3e979"); }}
                    );
                    shootEffect = bezierBurstGold; hitEffect = hitBezierGold;
                }}
            );
        }};

        diffract = new ItemTurret("diffract") {{
            localizedName = "Diffract";
            requirements(Category.turret, with(lead, 700, graphite, 560, titanium, 320, FRItems.cryogenicAlloy, 105));
            size = 4; scaledHealth = 210; reload = 240f; range = 300f;
            shake = 5f; shootWarmupSpeed = 0.07f; minWarmup = 0.76f; warmupMaintainTime = 90f;
            consumePower(8f); consumeCoolant(1f); coolantMultiplier = 0.25f;
            ammoTypes = ObjectMap.of(
                Items.titanium, new BasicBulletType() {{
                    speed = 3f; lifetime = 25f; damage = 50f;
                    pierce = true; pierceBuilding = true; pierceCap = 3;
                    width = 22f; height = 28f;
                    homingPower = 0.03f;
                    trailColor = Color.valueOf("7575c8");
                    rangeChange = -80f; reloadMultiplier = 0.6f;
                    lightning = 3; lightningLength = 12; lightningDamage = 5f; lightningColor = Color.valueOf("7575c8");
                    fragBullets = 3; fragSpread = 22.5f;
                    fragBullet = new BasicBulletType() {{
                        damage = 30f;
                        fragBullets = 3; fragSpread = 22.5f;
                        fragBullet = new BasicBulletType() {{
                            damage = 20f;
                            fragBullets = 1;
                            fragBullet = new BasicBulletType() {{ damage = 20f; lightning = 1; lightningLength = 12; }};
                        }};
                    }};
                    shootEffect = bezierBurstBlue; hitEffect = hitBezierBlue;
                    chargeEffect = chargeBezierBlue;
                }},
                Items.surgeAlloy, new BasicBulletType() {{
                    speed = 3f; lifetime = 20f; damage = 70f;
                    pierce = true; pierceBuilding = true; pierceCap = 3;
                    width = 22f; height = 28f;
                    trailColor = Color.valueOf("d99f6b"); backColor = Color.valueOf("d99f6b"); frontColor = Color.white;
                    ammoMultiplier = 3f;
                    lightning = 3; lightningLength = 15; lightningDamage = 7f;
                    fragBullets = 3; fragSpread = 22.5f;
                    fragBullet = new BasicBulletType() {{
                        spawnBullets = Seq.with( new LaserBulletType() {{ length = 100f; width = 16f; damage = 30f; colors = new Color[]{Color.valueOf("d99f6b"), Color.valueOf("f3e979"), Color.white}; }} );
                        fragBullets = 3; fragSpread = 22.5f;
                        fragBullet = new BasicBulletType() {{
                            damage = 40f; weaveMag = 4f; weaveScale = 4f;
                            fragBullets = 1;
                            fragBullet = new BasicBulletType() {{ damage = 30f; }};
                        }};
                    }};
                    shootEffect = bezierBurstGold; hitEffect = hitBezierGold;
                    chargeEffect = chargeBezierGold;
                }},
                FRItems.cryogenicAlloy, new BasicBulletType() {{
                    speed = 3f; lifetime = 25f; damage = 90f;
                    pierce = true; pierceBuilding = true; pierceCap = 3;
                    width = 22f; height = 28f;
                    trailColor = Color.valueOf("218b8f");
                    ammoMultiplier = 4f; rangeChange = 80f;
                    lightning = 3; lightningLength = 15; lightningDamage = 9f;
                    fragBullets = 3; fragSpread = 22.5f;
                    fragBullet = new BasicBulletType() {{
                        spawnBullets = Seq.with( new LaserBulletType() {{ length = 120f; width = 16f; damage = 50f; colors = new Color[]{Color.valueOf("218b8f"), Color.valueOf("87ceeb"), Color.white}; }} );
                        fragBullets = 3; fragSpread = 22.5f;
                        fragBullet = new BasicBulletType() {{ damage = 30f; }};
                    }};
                    shootEffect = bezierBurstCyan; hitEffect = hitBezierCyan;
                    chargeEffect = chargeBezierCyan;
                }}
            );
            drawer = new DrawTurret() {{
                parts = Seq.with(
                    new RegionPart() {{
                        suffix = "-side"; progress = PartProgress.warmup; heatProgress = PartProgress.recoil;
                        mirror = true; under = true; moveX = 1.9f;
                        moves = Seq.with( new DrawPart.PartMove() {{ progress = PartProgress.recoil; moveY = -1f; moveRot = -11f; }} );
                    }},
                    new RegionPart() {{ suffix = "-top"; progress = PartProgress.warmup; heatProgress = PartProgress.recoil; }}
                );
            }};
        }};

        cavalry = new PowerTurret("cavalry") {{
            localizedName = "Cavalry";
            requirements(Category.turret, with(copper, 1500, lead, 500, graphite, 600, silicon, 550, Items.surgeAlloy, 575));
            size = 5; health = 2850; reload = 200f; range = 240f;
            shootCone = 7f;
            consumePower(32f);
            shoot = new ShootPattern() {{ firstShotDelay = 120f; }};
            shootEffect = new MultiEffect(
                new WaveEffect() {{ sizeFrom = 0f; sizeTo = 25f; colorFrom = Color.valueOf("66b1ff"); colorTo = Color.valueOf("e4fdff"); }}
            );
            shootType = new BasicBulletType() {{
                sprite = "large-orb"; width = 0.001f; height = 0.001f; hitSize = 32f;
                speed = 3f; lifetime = 80f; damage = 880f; spin = 9f;
                pierce = true;
                trailColor = Color.valueOf("66b1ff"); backColor = Color.valueOf("66b1ff"); frontColor = Color.valueOf("e4fdff");
                trailLength = 50; trailWidth = 18; trailChance = -1;
                despawnHit = true;
                fragBullets = 5; fragSpread = 72f;
                fragBullet = new LaserBulletType() {{
                    length = 80f; width = 16f; damage = 60f;
                    colors = new Color[]{Color.valueOf("66b1ff"), Color.valueOf("e4fdff"), Color.white};
                }};
                intervalBullets = 8; intervalSpread = 45f; bulletInterval = 10f;
                intervalBullet = new ContinuousLaserBulletType() {{
                    length = 100f; width = 4f; damage = 75f; damageInterval = 8f;
                    colors = new Color[]{Color.valueOf("66b1ff"), Color.valueOf("e4fdff"), Color.valueOf("56b1d7"), Color.valueOf("91f7ff"), Color.white};
                }};
                lightning = 6; lightningCone = 360f; lightningLength = 6; lightningDamage = 145f;
                trailEffect = trailBezierBlue;
                chargeEffect = new MultiEffect(
                    new ParticleEffect() {{ particles = 12; length = 50f; cone = 360f; colorFrom = Color.valueOf("66b1ff"); colorTo = Color.valueOf("e4fdff"); lifetime = 30f; }},
                    new ParticleEffect() {{ particles = 8; length = 70f; cone = 360f; colorFrom = Color.valueOf("e4fdff"); colorTo = Color.valueOf("66b1ff"); lifetime = 45f; }},
                    new ParticleEffect() {{ particles = 6; length = 90f; cone = 360f; colorFrom = Color.valueOf("66b1ff"); colorTo = Color.white; lifetime = 60f; }},
                    new ParticleEffect() {{ particles = 4; length = 110f; cone = 360f; colorFrom = Color.white; colorTo = Color.valueOf("66b1ff"); lifetime = 75f; }},
                    new WaveEffect() {{ sizeFrom = 0f; sizeTo = 20f; colorFrom = Color.valueOf("66b1ff"); colorTo = Color.valueOf("e4fdff"); }},
                    new WaveEffect() {{ sizeFrom = 0f; sizeTo = 35f; colorFrom = Color.valueOf("e4fdff"); colorTo = Color.valueOf("66b1ff"); }}
                );
            }};
        }};

        bigSegment = new PointDefenseTurret("big-segment") {{
            localizedName = "Fragment";
            requirements(Category.turret, with(copper, 200, lead, 180, silicon, 180, thorium, 200, Items.phaseFabric, 100));
            size = 3; health = 410; reload = 4f; range = 236f;
            consumePower(10f);
            bulletDamage = 45f; shootLength = 14f;
        }};

        bigScatter = new ItemTurret("big-scatter") {{
            localizedName = "Shatter";
            requirements(Category.turret, with(copper, 200, lead, 150, graphite, 100, titanium, 25));
            size = 3; health = 1200; reload = 20f; range = 300f;
            inaccuracy = 14f; shootCone = 25f; maxAmmo = 50;
            targetAir = true; targetGround = false;
            consumeCoolant(0.3f);
            shoot = new ShootPattern() {{ shots = 3; firstShotDelay = 10f; }};
            shootEffect = Fx.shootSmall;
            ammoTypes = ObjectMap.of(
                Items.scrap, new FlakBulletType() {{ speed = 4f; damage = 10f; splashDamage = 15f; splashDamageRadius = 24f; ammoMultiplier = 5f; hitEffect = Fx.flakExplosion; shootEffect = bezierBurstWhite; }},
                Items.lead, new FlakBulletType() {{ speed = 4.2f; damage = 14f; splashDamage = 17f; splashDamageRadius = 16f; ammoMultiplier = 4f; frontColor = Color.valueOf("83008b"); backColor = Color.valueOf("7e7e7e"); hitEffect = Fx.flakExplosion; shootEffect = bezierBurstPurple; }},
                Items.metaglass, new FlakBulletType() {{ damage = 14f; splashDamage = 25f; splashDamageRadius = 22f; ammoMultiplier = 5f; fragBullets = 3; fragBullet = new FlakBulletType() {{ damage = 8f; splashDamage = 8f; splashDamageRadius = 5f; }}; hitEffect = Fx.flakExplosion; shootEffect = bezierBurstCyan; }},
                Items.pyratite, new FlakBulletType() {{ damage = 22f; splashDamage = 29f; splashDamageRadius = 28f; ammoMultiplier = 5f; status = StatusEffects.burning; fragBullets = 2; fragBullet = new FlakBulletType() {{ damage = 14f; splashDamage = 4f; }}; hitEffect = Fx.flakExplosion; shootEffect = bezierBurstRed; }},
                FRItems.livingSteel, new FlakBulletType() {{ damage = 18f; splashDamage = 17f; splashDamageRadius = 32f; status = StatusEffects.slow; statusDuration = 30f; hitEffect = Fx.flakExplosion; shootEffect = bezierBurstPurple; }},
                FRItems.nanoAmmo, new FlakBulletType() {{ damage = 4f; splashDamage = 5f; splashDamageRadius = 24f; status = StatusEffects.melting; statusDuration = 8000000f; hitEffect = Fx.flakExplosion; shootEffect = bezierBurstPink; }}
            );
            drawer = new DrawTurret() {{
                parts = Seq.with(
                    new RegionPart() {{ suffix = "-side"; progress = PartProgress.warmup; mirror = true; under = true; moveRot = -12f; }}
                );
            }};
        }};

        bigParallax = new TractorBeamTurret("big-parallax") {{
            localizedName = "Deracination";
            requirements(Category.turret, with(graphite, 90, silicon, 210, titanium, 270));
            size = 3; health = 780; range = 256f;
            force = 20f; scaledForce = 10f; damage = 0.6f;
            targetAir = true; targetGround = false;
            consumePower(4f);
        }};

        bigArc = new PowerTurret("big-arc") {{
            localizedName = "Arc Storm";
            requirements(Category.turret, with(copper, 150, lead, 150, silicon, 20));
            size = 1; health = 420; reload = 13f; range = 130f;
            shootCone = 30f; rotateSpeed = 100f;
            targetAir = false;
            consumePower(4f); consumeCoolant(0.1f);
            shootType = new LightningBulletType() {{ lightningLength = 18; damage = 4f; }};
            shootEffect = bezierBurstGold;
        }};

        statusWave = new PowerTurret("status-wave") {{
            localizedName = "Stasis Field";
            description = "Shoots out shockwaves that deal damage and shock enemies, slowing them down strongly.";
            size = 3;
            range = 640f;
            canOverdrive = false;
            shootY = 0f;
            reload = 60f;
            consumePower(7f);
            rotate = false;
            rotateSpeed = 0f;
            shootCone = 360f;
            recoil = 0f;
            shootEffect = new WaveEffect() {{
                sizeFrom = 0f; sizeTo = 900f;
                strokeFrom = 1f; strokeTo = 1.4f;
                colorFrom = Color.valueOf("e5ed2c"); colorTo = Color.valueOf("cfd712");
                lifetime = 45f;
            }};
            shootType = new BulletType() {{
                instantDisappear = true; damage = 0f;
                splashDamage = 1f; splashDamageRadius = 640f;
                status = FRStatus.shockslowed; statusDuration = 60f;
            }};
            requirements(Category.turret, ItemStack.with(Items.copper, 300, Items.lead, 250, Items.graphite, 200, Items.silicon, 150, FRItems.livingSteelHard, 100));
        }};

        airArc = new PowerTurret("air-arc") {{
            localizedName = "Air Arc";
            requirements(Category.turret, with(copper, 150, lead, 150, graphite, 20));
            size = 1; health = 320; reload = 20f; range = 130f;
            shootCone = 30f; rotateSpeed = 100f;
            targetAir = true;
            consumePower(3.5f); consumeCoolant(0.1f);
            shootType = new LightningBulletType() {{ lightningLength = 16; damage = 10f; }};
            shootEffect = bezierBurstBlue;
        }};

        absole = new LaserTurret("absole") {{
            localizedName = "Absole";
            requirements(Category.turret, with(copper, 800, metaglass, 500, silicon, 500, titanium, 700, plastanium, 110, Items.surgeAlloy, 150));
            size = 4; scaledHealth = 210; reload = 120f; range = 350f;
            shootDuration = 240f;
            shootWarmupSpeed = 0.025f; minWarmup = 0.99f; warmupMaintainTime = 300f;
            consumePower(8f);
            consume(new ConsumeCoolant() {{ amount = 0.4f; }});
            coolantMultiplier = 0.5f;
            shoot = new ShootBarrel() {{
                barrels = new float[9];
                shots = 3; shotDelay = 20f; firstShotDelay = 110f;
            }};
            shootType = new ContinuousFlameBulletType() {{
                colors = new Color[]{Color.valueOf("d99f6b"), Color.valueOf("e8d174"), Color.valueOf("fcf387"), Color.valueOf("fffab8"), Color.white};
                width = 3f; length = 310f; damage = 160f; damageInterval = 20f;
                pierceCap = 10; largeHit = true; shake = 3f;
                flareColor = Color.valueOf("e8d174"); flareLength = 15f; flareWidth = 5f; flareRotSpeed = 0.6f;
                status = FRStatus.highEnergyBurn; statusDuration = 180f;
                chargeEffect = new MultiEffect(
                    new ParticleEffect() {{ particles = 8; length = 40f; cone = 360f; colorFrom = Color.valueOf("d99f6b"); colorTo = Color.valueOf("e8d174"); lifetime = 30f; }},
                    new ParticleEffect() {{ particles = 6; length = 60f; cone = 360f; colorFrom = Color.valueOf("e8d174"); colorTo = Color.valueOf("fcf387"); lifetime = 45f; }}
                );
                hitEffect = hitBezierOrange;
            }};
            drawer = new DrawTurret() {{
                parts = Seq.with(
                    new RegionPart() {{ suffix = "-laser-unit-l"; progress = PartProgress.warmup; under = true; moveX = -6f; moveY = -2f; moveRot = 4.5f; }},
                    new RegionPart() {{ suffix = "-laser-unit-r"; progress = PartProgress.warmup; under = true; moveX = 6f; moveY = -2f; moveRot = -4.5f; }}
                );
            }};
        }};

        

        zephyr = new LiquidTurret("zephyr") {{
            localizedName = "Zephyr";
            requirements(Category.turret, with(copper, 80, lead, 70));
            size = 2; liquidCapacity = 30f;
            reload = 40f; ammoPerShot = 27;
            targetAir = true;
            ammoTypes = ObjectMap.of(
                FRLiquids.steam, new BasicBulletType() {{
                    sprite = "wind-blade"; lifetime = 72f; speed = 3f;
                    damage = 0f; knockback = 15f; buildingDamageMultiplier = 0f;
                    status = StatusEffects.wet;
                    width = 26f; height = 10f;
                    homingPower = 0.01f;
                    frontColor = Color.white; lightColor = Color.valueOf("cfcfcf");
                    trailColor = Color.valueOf("cfcfcf"); backColor = Color.valueOf("cfcfcf");
                    trailWidth = 8f; trailLength = 16; trailChance = -1;
                    shootEffect = new ParticleEffect() {{ particles = 9; cone = 25f; colorFrom = Color.white; colorTo = Color.valueOf("c1c3d4"); lifetime = 20f; }};
                    hitEffect = hitBezierCyan;
                    trailEffect = trailBezierCyan;
                }}
            );
            drawer = new DrawTurret() {{
                parts = Seq.with(
                    new RegionPart() {{
                        suffix = "-front"; progress = PartProgress.recoil;
                        mirror = true; under = true;
                        moveX = -0.24f; moveY = 0.6f; moveRot = -18f;
                        moves = Seq.with( new DrawPart.PartMove() {{ progress = PartProgress.recoil; moveRot = -32f; moveY = -2f; }} );
                    }}
                );
            }};
        }};

        weave = new LiquidTurret("weave") {{
            localizedName = "Weave";
            requirements(Category.turret, with(copper, 40, lead, 35, graphite, 25));
            size = 2; health = 450; range = 160f;
            reload = 33f; rotateSpeed = 2.6f;
            consumePower(0.75f); consumeCoolant(0.1f);
            shoot = new ShootSpread(2, 8f);
            ammoTypes = ObjectMap.of(
                Liquids.water, new BasicBulletType() {{
                    sprite = "particle"; width = 18f; height = 18f;
                    damage = 10f; splashDamage = 10f; splashDamageRadius = 16f;
                    speed = 3f; lifetime = 55f;
                    status = StatusEffects.wet;
                    frontColor = Color.valueOf("7090ea"); backColor = Color.valueOf("363f9a");
                    weaveMag = 5f; weaveScale = 4f;
                    trailChance = -1;
                    trailEffect = new ParticleEffect() {{ particles = 1; colorFrom = Color.valueOf("7090ea"); colorTo = Color.valueOf("363f9a"); lifetime = 15f; }};
                    hitEffect = new WaveEffect() {{ sizeFrom = 0f; sizeTo = 18f; colorFrom = Color.valueOf("7090ea"); colorTo = Color.valueOf("363f9a"); }};
                    despawnHit = true;
                }}
            );
            drawer = new DrawTurret() {{
                parts = Seq.with(
                    new RegionPart() {{ suffix = "-body"; progress = PartProgress.warmup; mirror = true; moveX = 1.5f; }}
                );
            }};
        }};

        sprunkler = new LiquidTurret("sprunkler") {{
            localizedName = "Sprinkler";
            requirements(Category.turret, with(copper, 50, graphite, 20, metaglass, 15));
            size = 3; health = 200; range = 200f;
            reload = 1f; shootCone = 360f; rotateSpeed = 200f;
            targetAir = true;
            shoot = new ShootSpread(120, 6f);
            ammoTypes = ObjectMap.of(
                Liquids.water, new LiquidBulletType() {{
                    damage = 0f; lifetime = 57f; knockback = 0f;
                    liquid = Liquids.water;
                    shootEffect = new ParticleEffect() {{ particles = 6; cone = 360f; length = 40f; colorFrom = Color.valueOf("0053e8"); colorTo = Color.valueOf("0046c2"); lifetime = 50f; }};
                }}
            );
        }};

        batter = new LiquidTurret("batter") {{
            localizedName = "Batter";
            requirements(Category.turret, with(copper, 75, graphite, 50, metaglass, 50));
            size = 3; health = 420; range = 200f;
            reload = 60f; inaccuracy = 1f; rotateSpeed = 200f;
            targetAir = false;
            ammoTypes = ObjectMap.of(
                Liquids.water, new BasicBulletType() {{
                    sprite = "liquid-vortex"; spin = 6f; speed = 2f; lifetime = 100f;
                    damage = 20f; knockback = 30f; pierceCap = 10;
                    width = 20f; height = 20f; shrinkX = 0f; shrinkY = 0f;
                    status = StatusEffects.wet;
                    backColor = Color.valueOf("486acd"); frontColor = Color.valueOf("7090ea");
                    shootEffect = bezierBurstBlue; hitEffect = hitBezierBlue;
                }},
                Liquids.cryofluid, new BasicBulletType() {{
                    sprite = "liquid-vortex"; spin = 6f; speed = 2f; lifetime = 100f;
                    damage = 40f; knockback = 50f; pierceCap = 15;
                    width = 20f; height = 20f; shrinkX = 0f; shrinkY = 0f;
                    status = StatusEffects.freezing;
                    backColor = Color.valueOf("87ceeb"); frontColor = Color.valueOf("c0ecff");
                    shootEffect = bezierBurstCyan; hitEffect = hitBezierCyan;
                }},
                Liquids.slag, new BasicBulletType() {{
                    sprite = "liquid-vortex"; spin = 6f; speed = 2f; lifetime = 100f;
                    damage = 30f; knockback = 40f; pierceCap = 25;
                    width = 20f; height = 20f; shrinkX = 0f; shrinkY = 0f;
                    status = StatusEffects.melting;
                    backColor = Color.valueOf("ffcd66"); frontColor = Color.valueOf("ffffa3");
                    shootEffect = bezierBurstOrange; hitEffect = hitBezierOrange;
                }},
                Liquids.oil, new BasicBulletType() {{
                    sprite = "liquid-vortex"; spin = 6f; speed = 2f; lifetime = 100f;
                    damage = 35f; knockback = 40f; pierceCap = 15;
                    width = 20f; height = 20f; shrinkX = 0f; shrinkY = 0f;
                    status = StatusEffects.tarred;
                    backColor = Color.valueOf("313131"); frontColor = Color.valueOf("61615b");
                    shootEffect = bezierBurstWhite; hitEffect = hitBezierWhite;
                }},
                FRLiquids.livingSteelLiquid, new BasicBulletType() {{
                    sprite = "liquid-vortex"; spin = 6f; speed = 2f; lifetime = 100f;
                    damage = 40f; knockback = 45f; pierceCap = 25;
                    width = 20f; height = 20f; shrinkX = 0f; shrinkY = 0f;
                    status = FRStatus.sapped; statusDuration = 120f;
                    backColor = Color.valueOf("9e78dc"); frontColor = Color.valueOf("8161b5");
                    shootEffect = bezierBurstPurple; hitEffect = hitBezierPurple;
                }},
                FRLiquids.neutronFluid, new BasicBulletType() {{
                    sprite = "liquid-vortex"; spin = 6f; speed = 2f; lifetime = 100f;
                    damage = 65f; knockback = 60f; pierceCap = 40;
                    width = 20f; height = 20f; shrinkX = 0f; shrinkY = 0f;
                    status = FRStatus.neutronFrozen; statusDuration = 180f;
                    backColor = Color.white; frontColor = Color.valueOf("cdcdcd");
                    shootEffect = bezierBurstWhite; hitEffect = hitBezierWhite;
                }},
                FRLiquids.acid, new BasicBulletType() {{
                    sprite = "liquid-vortex"; spin = 6f; speed = 2f; lifetime = 100f;
                    damage = 40f; knockback = 45f; pierceCap = 25;
                    width = 20f; height = 20f; shrinkX = 0f; shrinkY = 0f;
                    status = FRStatus.acidicBurn; statusDuration = 120f;
                    backColor = Color.valueOf("f8f854"); frontColor = Color.valueOf("b0bf1a");
                    shootEffect = bezierBurstGreen; hitEffect = hitBezierGreen;
                }}
            );
        }};

        

        constructionPylon = new BuildTurret("construction-pylon") {{
            localizedName = "Construction Pylon";
            requirements(Category.turret, with(copper, 90, silicon, 60, titanium, 150, FRItems.livingSteel, 150));
            size = 3; range = 160f;
            consumePower(9f);
            consume(new ConsumeLiquid(Liquids.cryofluid, 0.2f));
        }};
    }

    
    private static DrawPart dronePart(String suffix, float x, float y, float rot) {
        return new RegionPart() {{
            this.suffix = suffix; progress = PartProgress.warmup;
            mirror = false; under = true;
            moveX = x; moveY = y; moveRot = rot;
            moves = Seq.with( new DrawPart.PartMove() {{ progress = PartProgress.recoil; moveY = -4f; }} );
        }};
    }

    
    private static UnitType unit(String name) {
        return Vars.content.getByName(ContentType.unit, name);
    }
}
