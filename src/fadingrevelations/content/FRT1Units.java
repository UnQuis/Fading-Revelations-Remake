package fadingrevelations.content;

import arc.*;
import arc.graphics.*;
import arc.math.geom.*;
import mindustry.ai.types.*;
import mindustry.content.*;
import mindustry.entities.*;
import mindustry.entities.abilities.*;
import mindustry.entities.bullet.*;
import mindustry.entities.effect.*;
import mindustry.entities.pattern.*;
import mindustry.gen.*;
import mindustry.type.*;

import mindustry.gen.Sounds;

public class FRT1Units {
    public static UnitType aedes, alba, annax, apis, heliaca, lancerDrone, mela, sambuca, seed;

    public static void load() {
        aedes = new UnitType("aedes") {{
            constructor = UnitEntity::create; localizedName = "Aedes"; flying = true; lowAltitude = true;
            engineSize = 0;
            health = 55; hitSize = 9;
            range = 135; speed = 1; strafePenalty = 1;
            weapons.addAll(
                new Weapon() {{
                    x = 0; y = 0; shootY = 0; shootX = 0;
                    mirror = false; rotate = false;
                    shootSound = Sounds.none;
                    alwaysShooting = true; ignoreRotation = true;
                    recoil = 0; shootCone = 360; reload = 15;
                    bullet = new BulletType() {{
                        status = FRStatus.minimalDowndraft; statusDuration = 30;
                        instantDisappear = true; damage = 0;
                        shootEffect = new ParticleEffect() {{
                            followParent = true; rotWithParent = true;
                            particles = 1; lifetime = 20; length = 0;
                            region = "fading-revelations-patched-aedes-rotor";
                            sizeFrom = 10; sizeTo = 10; spin = 10; layer = 95.1f;
                        }};
                        pierceBuilding = true; knockback = 0.1f;
                        smokeEffect = Fx.none; hitEffect = Fx.none; despawnEffect = Fx.none;
                        splashDamage = 10; splashDamageRadius = 16;
                        speed = 0;
                    }};
                }},
                new Weapon() {{
                    x = 0; y = 0; rotate = false; mirror = false;
                    reload = 20; shootSound = Sounds.shoot;
                    bullet = new BasicBulletType() {{
                        width = 6; height = 10;
                        weaveMag = 4; weaveScale = 4;
                        lifetime = 45; speed = 3; damage = 9;
                        trailWidth = 1.5f; trailColor = Color.valueOf("d9dcbc");
                        trailLength = 6; trailChance = -1;
                    }};
                }}
            );
        }};

        alba = new UnitType("alba") {{
            constructor = UnitWaterMove::create; localizedName = "Alba";
            health = 310; armor = 3; speed = 1.2f; drag = 0.14f; hitSize = 9;
            accel = 0.5f; rotateSpeed = 5.5f;
            trailLength = 16; trailColor = Color.valueOf("ffffff");
            faceTarget = false; range = 120;
            weapons.add(
                new Weapon() {{
                    x = 0; y = -8; reload = 30; mirror = false;
                    rotate = true; shootSound = Sounds.shockBullet;
                    bullet = new LightningBulletType() {{
                        damage = 1; buildingDamageMultiplier = 0;
                        collidesTeam = true; healAmount = 10;
                        lightningLength = 15; lightningColor = Color.valueOf("98ffa9");
                    }};
                }}
            );
        }};

        annax = new UnitType("annax") {{
            constructor = MechUnit::create; localizedName = "Annax";
            speed = 1; hitSize = 8; health = 300; mechSideSway = 0.25f;
            weapons.add(
                new Weapon("atrax-weapon") {{
                    x = 6; y = -1; reload = 180;
                    shootSound = Sounds.shootFlame; rotate = false; mirror = true;
                    shoot = new ShootPattern() {{
                        firstShotDelay = 60; shots = 12; shotDelay = 3;
                    }};
                    chargeSound = Sounds.chargeLancer; alternate = false;
                    bullet = new LiquidBulletType(Liquids.slag) {{
                        speed = 4; lifetime = 40; damage = 3;
                        pierce = true; pierceCap = 2; buildingDamageMultiplier = 0.9f;
                        chargeEffect = Fx.lightningCharge;
                        shootEffect = Fx.shootSmall;
                    }};
                }}
            );
        }};

        apis = new UnitType("apis") {{
            constructor = UnitEntity::create; localizedName = "Apis"; flying = true;
            health = 180; armor = 0; hitSize = 8; itemCapacity = 20;
            outlineColor = Color.valueOf("191919"); speed = 1.3f;
            weapons.add(
                new Weapon() {{
                    x = 0; y = 2; reload = 180; mirror = false;
                    top = false; rotate = false; shootCone = 30;
                    shootSound = Sounds.shootSpectre;
                    bullet = new PointBulletType() {{
                        speed = 8; lifetime = 20; damage = 0;
                        splashDamage = 170; splashDamageRadius = 32;
                        hitEffect = Fx.instHit; despawnHit = true;
                        pierce = true; buildingDamageMultiplier = 0.4f;
                    }};
                }}
            );
        }};

        heliaca = new UnitType("heliaca") {{
            constructor = UnitEntity::create; localizedName = "Heliaca"; flying = true;
            health = 75; circleTarget = true; speed = 1.7f;
        }};

        lancerDrone = new UnitType("lancer-drone") {{
            constructor = UnitEntity::create; localizedName = "Lancer Drone"; flying = true;
            health = 150; armor = 1; engineOffset = 5.5f; hitSize = 6;
            speed = 3;
            weapons.add(
                new Weapon("chrome-blaster") {{
                    reload = 90; shootSound = Sounds.shootLaser; alternate = true;
                    bullet = new LaserBulletType() {{
                        damage = 30; width = 15; length = 180;
                        shootEffect = Fx.shockwave;
                        colors = new Color[]{Color.valueOf("ec7458aa"), Color.valueOf("ff9c5a"), Color.white};
                    }};
                }}
            );
        }};

        mela = new UnitType("mela") {{
            constructor = UnitWaterMove::create; localizedName = "Mela";
            health = 320; armor = 2; hitSize = 14; speed = 1.3f;
            canBoost = false; faceTarget = false;
            drag = 0.13f; accel = 0.5f; rotateSpeed = 3.8f; range = 160;
            weapons.addAll(
                new Weapon() {{
                    x = 0; y = 2; reload = 10; rotate = true; mirror = false;
                    ejectEffect = Fx.casing1;
                    bullet = new BasicBulletType() {{
                        speed = 2; damage = 15; pierce = true; pierceCap = 3;
                        width = 9; height = 11; lifetime = 80; ammoMultiplier = 6;
                    }};
                }},
                new Weapon() {{
                    x = -3; shootY = 0; shootCone = 90;
                    ejectEffect = Fx.none; reload = 180; rotate = false;
                    baseRotation = 180; alwaysShooting = true; minShootVelocity = 0.9f;
                    bullet = new BulletType() {{
                        instantDisappear = true;
                        fragBullets = 1; fragVelocityMin = 0; fragVelocityMax = 0; fragRandomSpread = 0;
                        shootEffect = Fx.none; smokeEffect = Fx.none; hitEffect = Fx.none; despawnHit = false;
                        fragBullet = new BasicBulletType() {{
                            sprite = "lml-mine";
                            height = 12; width = 12; collidesAir = false; hitSize = 12;
                            damage = 5; frontColor = Color.valueOf("d99f6b"); shrinkX = 0; shrinkY = 0;
                            despawnEffect = Fx.none; backColor = Color.valueOf("f3e979");
                            splashDamage = 25; splashDamageRadius = 32;
                            speed = 0; hittable = false; collides = true; pierce = false; lifetime = 720;
                        }};
                    }};
                }}
            );
        }};

        sambuca = new UnitType("sambuca") {{
            constructor = TankUnit::create; localizedName = "Sambuca";
            health = 500; armor = 3;
            squareShape = true; treadPullOffset = 8;
            treadRects = new Rect[]{new Rect(7, -26, 14, 51)};
            singleTarget = true; omniMovement = false;
            rotateSpeed = 2; faceTarget = false; range = 120;
            weapons.add(
                new Weapon() {{
                    x = 0; y = -1; reload = 18; mirror = false; rotate = true;
                    shoot = new ShootPattern() {{ shots = 2; shotDelay = 2; }};
                    bullet = new BasicBulletType() {{
                        width = 6; height = 10; speed = 3; lifetime = 40;
                        damage = 8; pierce = false; buildingDamageMultiplier = 2.8f;
                    }};
                }}
            );
        }};

        seed = new UnitType("seed") {{
            constructor = UnitEntity::create; localizedName = "Seed"; flying = true;
            health = 250; armor = 0; itemCapacity = 25; speed = 1.6f;
            canHeal = true; drawBuildBeam = true; mineHardnessScaling = true;
            mineSpeed = 2; mineTier = 1; canAttack = false; buildSpeed = 0.2f;
            engineColor = Color.valueOf("84f491"); engineColorInner = Color.valueOf("62ae7f");
            controller = u -> new MinerAI();
            hitSize = 8;
            abilities.add(new RepairFieldAbility(2f, 60f, 40f) {{
                activeEffect = new WaveEffect() {{
                    sizeFrom = 0; sizeTo = 60;
                    colorFrom = Color.valueOf("84f491"); colorTo = Color.valueOf("62ae7f");
                }};
            }});
        }};
    }
}
