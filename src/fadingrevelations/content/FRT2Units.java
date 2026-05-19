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

public class FRT2Units {
    public static UnitType armiger, arvens, alopex, cromis, procer, sapling, scofra, scorpio;

    public static void load() {
        armiger = new UnitType("armiger") {{
            constructor = UnitEntity::create; localizedName = "Armiger"; flying = true; lowAltitude = true; engineSize = 0;
            health = 525; armor = 6; speed = 1; range = 180; hitSize = 12; strafePenalty = 1;
            weapons.addAll(
                new Weapon() {{
                    x = 0; y = 0; shootY = 0; shootX = 0;
                    mirror = false; rotate = false;
                    alwaysShooting = true; shootSound = Sounds.none;
                    recoil = 0; ignoreRotation = true; shootCone = 360; reload = 15;
                    bullet = new BulletType() {{
                        status = FRStatus.slightDowndraft; statusDuration = 20;
                        instantDisappear = true; damage = 0;
                        splashDamage = 7; splashDamageRadius = 32;
                        pierceBuilding = true; knockback = 0.2f;
                        shootEffect = new ParticleEffect() {{
                            followParent = true; rotWithParent = true;
                            particles = 1; lifetime = 20; length = 0;
                            region = "fading-revelations-patched-armiger-rotor";
                            sizeFrom = 13; sizeTo = 13; spin = 12; layer = 95.1f;
                        }};
                        smokeEffect = Fx.none; hitEffect = Fx.none; despawnEffect = Fx.none;
                        speed = 0;
                    }};
                }},
                new Weapon() {{
                    x = 0; y = 4;
                    rotate = false; mirror = true; alternate = false;
                    reload = 20; shootSound = Sounds.shoot;
                    bullet = new BasicBulletType() {{
                        width = 8; height = 12;
                        weaveMag = 4; weaveScale = 4;
                        lifetime = 60; speed = 3; damage = 6;
                        trailWidth = 1.7f; trailColor = Color.valueOf("d9dcbc");
                        trailLength = 7; trailChance = -1;
                    }};
                }},
                new Weapon() {{
                    x = 7; y = 1; mirror = true; rotate = false;
                    reload = 26;
                    shoot = new ShootPattern() {{ shots = 2; shotDelay = 1; }};
                    shootSound = Sounds.shootMissile;
                    bullet = new MissileBulletType() {{
                        width = 8; height = 12; lifetime = 60; speed = 3; damage = 8;
                        trailWidth = 1.7f; trailColor = Color.valueOf("d9dcbc");
                        trailLength = 7; trailChance = -1;
                    }};
                }}
            );
        }};

        arvens = new UnitType("arvens") {{
            constructor = UnitWaterMove::create; localizedName = "Arvens";
            health = 920; armor = 6; hitSize = 17;
            speed = 0.96f; accel = 0.27f; rotateSpeed = 3; drag = 0.16f;
            faceTarget = false; range = 200;
            trailLength = 20; trailColor = Color.valueOf("ffffff");
            weapons.add(
                new RepairBeamWeapon("repair-beam-weapon") {{
                    repairSpeed = 1; targetUnits = true; targetBuildings = true;
                    x = 0; y = -10; mirror = false;
                    rotate = true; rotateSpeed = 3;
                    bullet = new BulletType() {{ maxRange = 160; }};
                }}
            );
            abilities.addAll(
                new RepairFieldAbility(15f, 90f, 200f),
                new StatusFieldAbility(FRStatus.polymorphousBuilding, 360f, 720f, 200f)
            );
        }};

        alopex = new UnitType("close-range") {{
            constructor = UnitEntity::create; localizedName = "Alopex"; flying = true;
            health = 320; armor = 2; hitSize = 17;
            speed = 5; drag = 0.055f; accel = 0.075f;
            aimDst = 15;
            faceTarget = true; circleTarget = true; rotateSpeed = 8;
            lowAltitude = true; omniMovement = true;
            engineOffset = 15; engineSize = 6;
            weapons.add(
                new Weapon() {{
                    x = 0; y = 6; mirror = false; alternate = false;
                    reload = 1; rotate = false; shootSound = Sounds.shootLaser;
                    bullet = new LaserBulletType() {{
                        damage = 20; speed = 10; pierce = true; pierceCap = 6;
                        width = 4; length = 100; lifetime = 15;
                    }};
                }}
            );
        }};

        cromis = new UnitType("cromis") {{
            constructor = UnitWaterMove::create; localizedName = "Cromis";
            health = 1180; armor = 7; hitSize = 15;
            speed = 0.92f; accel = 0.25f; drag = 0.17f;
            faceTarget = false; rotateSpeed = 2;
            trailLength = 20; trailColor = Color.valueOf("ffffff");
            abilities.add(
                new ShieldRegenFieldAbility(25f, 42f, 300f, 64f)
            );
            weapons.addAll(
                new Weapon("missiles-mount") {{
                    x = 0; y = 7; mirror = false; rotate = true; reload = 40;
                    shootSound = Sounds.shootMissile; shake = 1;
                    shoot = new ShootPattern() {{ shots = 3; shotDelay = 8; }};
                    bullet = new MissileBulletType() {{
                        speed = 3; damage = 30;
                        splashDamage = 8; splashDamageRadius = 16;
                        homingRange = 90; lifetime = 90;
                        pierce = true; pierceCap = 1;
                    }};
                }},
                new Weapon("king-heal") {{
                    x = 0; y = -9; mirror = false; rotate = true;
                    reload = 240; shootSound = Sounds.shootLaser;
                    shoot = new ShootPattern() {{ firstShotDelay = 45; }};
                    chargeSound = Sounds.chargeLancer;
                    shootStatus = StatusEffects.unmoving; shootStatusDuration = 90;
                    parentizeEffects = true;
                    shake = 5; recoil = 4; inaccuracy = 6;
                    bullet = new LaserBulletType() {{
                        damage = 160; length = 260; lifetime = 90;
                        hitShake = 4; largeHit = true;
                        status = FRStatus.emp; statusDuration = 60;
                        sideLength = 20; sideWidth = 4; sideAngle = 30;
                        colors = new Color[]{Color.valueOf("ec7458aa"), Color.valueOf("ff9c5a"), Color.white};
                        chargeEffect = new MultiEffect(
                            new WaveEffect() {{ sizeFrom = 0; sizeTo = 20; interp = Interp.pow2Out; }},
                            new WaveEffect() {{ sizeFrom = 22; sizeTo = 2; interp = Interp.pow2Out; }}
                        ) {{ followParent = true; lifetime = 45; }};
                    }};
                }}
            );
        }};

        procer = new UnitType("procer") {{
            constructor = UnitEntity::create; localizedName = "Procer"; flying = true; hovering = true; lowAltitude = true;
            health = 1100; armor = 9; hitSize = 17.5f;
            speed = 1.2f; itemCapacity = 25;
            outlineColor = Color.valueOf("191919");
            engineOffset = 32; engineSize = 8;
            parts.addAll(
                new HoverPart() {{ x = 8; y = -13; mirror = true; radius = 6; phase = 240; stroke = 2; rotation = 90; layerOffset = -0.001f; color = Color.valueOf("ffd59e"); }},
                new HoverPart() {{ x = 13; y = -6; mirror = true; radius = 6; phase = 240; stroke = 2; layerOffset = -0.001f; color = Color.valueOf("ffd59e"); }},
                new HoverPart() {{ x = 11; y = 6; mirror = true; radius = 6; phase = 240; stroke = 2; layerOffset = -0.001f; color = Color.valueOf("ffd59e"); }}
            );
            weapons.addAll(
                new Weapon("dark-missiles-mount") {{
                    x = -9; y = -8; mirror = true; rotate = true;
                    reload = 20; alternate = true; shootSound = Sounds.shootMissile;
                    shoot = new ShootPattern() {{ shots = 2; shotDelay = 2; }};
                    bullet = new MissileBulletType() {{
                        width = 8; height = 12;
                        splashDamage = 15; splashDamageRadius = 24;
                        speed = 4; lifetime = 60; pierce = true; pierceCap = 3; damage = 10;
                    }};
                }},
                new Weapon("dark-missiles-mount") {{
                    x = 0; y = 0; mirror = false; rotate = false;
                    reload = 120; inaccuracy = 4; shootSound = Sounds.shootLaser;
                    bullet = new LaserBulletType() {{
                        length = 240; damage = 110; lifetime = 60;
                        colors = new Color[]{Color.valueOf("ec7458aa"), Color.valueOf("ff9c5a"), Color.white};
                    }};
                }}
            );
        }};

        sapling = new UnitType("sapling") {{
            constructor = PayloadUnit::create; localizedName = "Sapling";
            flying = true; lowAltitude = true;
            health = 680; armor = 3; itemCapacity = 75; speed = 2.7f;
            canHeal = true; drawBuildBeam = true; mineHardnessScaling = true;
            mineSpeed = 4; mineTier = 3; canAttack = false; buildSpeed = 3.2f;
            engineColor = Color.valueOf("84f491"); engineColorInner = Color.valueOf("62ae7f");
            payloadCapacity = 512; hitSize = 14;
            controller = u -> new RepairAI();
            abilities.addAll(
                new RepairFieldAbility(5f, 60f, 120f) {{
                    activeEffect = new WaveEffect() {{
                        sizeFrom = 0; sizeTo = 60;
                        colorFrom = Color.valueOf("84f491"); colorTo = Color.valueOf("62ae7f");
                    }};
                }},
                new StatusFieldAbility(FRStatus.hastened, 120f, 180f, 120f) {{
                    activeEffect = new WaveEffect() {{
                        sizeFrom = 0; sizeTo = 60;
                        colorFrom = Color.valueOf("ed9436"); colorTo = Color.valueOf("eb7e3c");
                    }};
                }}
            );
            weapons.addAll(
                new Weapon("building-weapon") {{
                    x = 0; y = -8; reload = 30; rotate = true;
                    noAttack = true; mirror = false; shootSound = Sounds.shootLaser;
                    bullet = new LaserBulletType() {{
                        damage = 5; healPercent = 12; length = 140;
                        colors = new Color[]{Color.valueOf("84f491"), Color.white, Color.valueOf("62ae7f")};
                        collidesTeam = true; collidesTiles = true; collidesAir = true;
                    }};
                }},
                new Weapon() {{
                    x = 0; y = 5; reload = 5; rotate = false;
                    shootSound = Sounds.shockBullet;
                    bullet = new LightningBulletType() {{
                        lightningLength = 15; lightningDamage = 20; lightningColor = Color.valueOf("84f491");
                    }};
                }}
            );
        }};

        scofra = new UnitType("scofra") {{
            constructor = LegsUnit::create; localizedName = "Scofra";
            hovering = true;
            speed = 0.54f; drag = 0.4f; hitSize = 15; rotateSpeed = 3; health = 1150;
            legCount = 6; legLength = 14; legForwardScl = 0.8f; legMoveSpace = 1.5f; legBaseOffset = 2;
            armor = 5; shadowElevation = 0.3f; groundLayer = 75;
            weapons.addAll(
                new Weapon("missiles-mount") {{
                    x = 0; y = -6; mirror = false; rotate = true;
                    reload = 6; shootSound = Sounds.shootSap; recoil = 1; shootY = 4;
                    bullet = new LiquidBulletType(Liquids.oil) {{
                        speed = 4; lifetime = 40; damage = 10;
                        status = StatusEffects.tarred; statusDuration = 120;
                        shootEffect = shootSmall; hitEffect = hitBulletColor; smokeEffect = shootSmallSmoke;
                    }};
                }},
                new Weapon("atrax-weapon") {{
                    x = 10; y = -2; mirror = true; rotate = true;
                    reload = 3;
                    shoot = new ShootPattern() {{ shots = 6; }};
                    shootSound = Sounds.shootFlame;
                    bullet = new BasicBulletType() {{
                        damage = 5; speed = 4; lifetime = 45;
                        status = StatusEffects.melting; statusDuration = 120;
                        makeFire = true;
                        width = 0.01f; height = 0.01f;
                        despawnEffect = Fx.none;
                        smokeEffect = new ParticleEffect() {{
                            particles = 12; length = 200; lifetime = 12;
                            interp = Interp.circleOut; cone = 7;
                            colorFrom = Color.valueOf("ffdd55"); colorTo = Color.valueOf("db401c");
                            sizeFrom = 3; sizeTo = 0.4f;
                        }};
                    }};
                }}
            );
        }};

        scorpio = new UnitType("scorpio") {{
            constructor = TankUnit::create; localizedName = "Scorpio";
            hitSize = 18; treadPullOffset = 5;
            speed = 0.85f; rotateSpeed = 3; health = 2300; armor = 8;
            treadRects = new Rect[]{new Rect(13, -38, 17, 76)};
            singleTarget = false; omniMovement = false; faceTarget = false;
            weapons.add(
                new Weapon("scorpio-weapon") {{
                    x = 0; y = 0; shootY = 10; mirror = false; rotate = true;
                    shootSound = Sounds.shootSpectre;
                    reload = 18; recoil = 2; shootCone = 4; rotateSpeed = 1.5f;
                    layerOffset = 0.0001f; cooldownTime = 30;
                    heatColor = Color.valueOf("f9350f");
                    bullet = new LaserBulletType() {{
                        width = 5; length = 160; damage = 32; lifetime = 30;
                        pierceBuilding = true; laserAbsorb = false;
                        buildingDamageMultiplier = 1.9f;
                        hitColor = Color.valueOf("feb380");
                        hitEffect = hitBulletColor; smokeEffect = shootSmallSmoke;
                        shootEffect = lightningCharge;
                    }};
                }}
            );
        }};
    }
}
