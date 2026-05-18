package fadingrevelations.content;

import arc.graphics.*;
import arc.math.*;
import mindustry.content.*;
import mindustry.entities.bullet.*;
import mindustry.entities.effect.*;
import mindustry.entities.pattern.*;
import mindustry.gen.*;
import mindustry.type.*;
import mindustry.type.unit.*;

import static mindustry.content.Fx.*;
import static mindustry.gen.Sounds.*;

public class FRMissiles {
    public static MissileUnitType tinyMissile, tinyMissile2, tinyMissile3, tinyMissile4,
        nukeMissile, plastMissile, phaseMissile, surgeMissile, onagerMissile,
        artillerySentry, railgunSentry;

    public static void load() {
        tinyMissile = new MissileUnitType("tiny-missile") {{
            flying = true; speed = 6; lifetime = 60;
            trailColor = Color.valueOf("ffaa5f"); trailLength = 10;
            drawCell = false;
            weapons.add(
                new Weapon() {{
                    shootCone = 360; mirror = false; reload = 1;
                    shootOnDeath = true; shake = 1;
                    bullet = new ExplosionBulletType(180f, 32f) {{
                        ammoMultiplier = 7;
                    }};
                }}
            );
        }};

        tinyMissile2 = new MissileUnitType("tiny-missile-2") {{
            flying = true; speed = 7; lifetime = 60;
            trailColor = Color.valueOf("cbd97f"); trailLength = 12;
            drawCell = false;
            weapons.add(
                new Weapon() {{
                    shootCone = 360; mirror = false; reload = 1;
                    shootOnDeath = true; shake = 2;
                    bullet = new ExplosionBulletType(360f, 32f) {{
                        ammoMultiplier = 15;
                    }};
                }}
            );
        }};

        tinyMissile3 = new MissileUnitType("tiny-missile-3") {{
            flying = true; speed = 6; lifetime = 60;
            trailColor = Color.valueOf("ff795e"); trailLength = 11;
            drawCell = false;
            weapons.add(
                new Weapon() {{
                    shootCone = 360; mirror = false; reload = 1;
                    shootOnDeath = true; shake = 1;
                    bullet = new ExplosionBulletType(220f, 32f) {{
                        ammoMultiplier = 10;
                    }};
                }}
            );
        }};

        tinyMissile4 = new MissileUnitType("tiny-missile-4") {{
            flying = true; speed = 6; lifetime = 120;
            trailColor = Color.valueOf("ffaa5f"); trailLength = 10;
            rotateSpeed = 10; drawCell = false;
            weapons.add(
                new Weapon() {{
                    shootCone = 360; mirror = false; reload = 1;
                    shootOnDeath = true; shake = 1;
                    bullet = new ExplosionBulletType(80f, 24f);
                }}
            );
        }};

        nukeMissile = new MissileUnitType("nuke-missile") {{
            flying = true; speed = 7; lifetime = 320;
            deathExplosionEffect = massiveExplosion;
            trailColor = Color.valueOf("9cb664"); trailLength = 25;
            deathSound = Sounds.none;
            weapons.add(
                new Weapon() {{
                    shootCone = 360; mirror = false; reload = 1;
                    shootOnDeath = true; shake = 20;
                    bullet = new ExplosionBulletType(5000f, 240f) {{
                        buildingDamageMultiplier = 0.05f;
                        suppressionRange = 240; suppressionDuration = 360;
                        hitShake = 12; reflectable = false;
                        hitSound = Sounds.explosion; hitSoundVolume = 6;
                        status = FRStatus.radiated;
                        hitEffect = new MultiEffect(
                            new WaveEffect() {{
                                sizeFrom = 0; sizeTo = 250;
                                lifetime = 120;
                                colorFrom = Color.valueOf("edf3a9"); colorTo = Color.valueOf("9cb664");
                                startDelay = 20;
                            }},
                            new ParticleEffect() {{
                                baseLength = 120; lifetime = 120;
                                colorFrom = Color.valueOf("edf3a9"); colorTo = Color.valueOf("fefefe");
                                interp = Interp.circleOut; cone = 360;
                                particles = 32; sizeFrom = 12; sizeTo = 0;
                                spin = 0.2f;
                            }}
                        );
                    }};
                }}
            );
        }};

        plastMissile = new MissileUnitType("plast-missile") {{
            flying = true; speed = 6.8f; lifetime = 240;
            deathExplosionEffect = massiveExplosion;
            trailColor = Color.valueOf("9cb664"); trailLength = 22;
            weapons.add(
                new Weapon() {{
                    shootCone = 360; mirror = false; reload = 1;
                    shootOnDeath = true; shake = 8;
                    bullet = new ExplosionBulletType(1800f, 60f) {{
                        hitColor = Color.valueOf("9cb664");
                        collidesAir = true; collidesGround = false;
                        buildingDamageMultiplier = 0;
                        fragLifeMin = 0.1f; fragBullets = 7;
                        fragBullet = new FlakBulletType(3.4f, 80f) {{
                            collidesAir = true; collidesGround = false; collidesTiles = false;
                            buildingDamageMultiplier = 0; drag = 0.02f;
                            hitEffect = massiveExplosion; despawnEffect = scatheSlash;
                            knockback = 0.8f; lifetime = 23;
                            height = 18; width = 9;
                            splashDamageRadius = 40; splashDamage = 80;
                            backColor = Color.valueOf("9cb664"); trailColor = Color.valueOf("9cb664");
                            hitColor = Color.valueOf("9cb664"); frontColor = Color.valueOf("ffffff");
                            smokeEffect = shootBigSmoke;
                            despawnShake = 5;
                            lightRadius = 30; lightColor = Color.valueOf("9cb664"); lightOpacity = 0.5f;
                            trailLength = 20; trailWidth = 3.5f;
                            trailEffect = Fx.none;
                        }};
                    }};
                }}
            );
        }};

        phaseMissile = new MissileUnitType("phase-missile") {{
            flying = true; speed = 6.8f; lifetime = 240;
            deathExplosionEffect = massiveExplosion;
            trailColor = Color.valueOf("f19583"); trailLength = 22;
            weapons.add(
                new Weapon() {{
                    shootCone = 360; mirror = false; reload = 1;
                    shootOnDeath = true; shake = 9;
                    bullet = new ExplosionBulletType(2200f, 70f) {{
                        hitColor = Color.valueOf("f19583");
                        collidesAir = true; collidesGround = false;
                        buildingDamageMultiplier = 0;
                        fragLifeMin = 0.1f; fragBullets = 7;
                        fragBullet = new FlakBulletType(3.4f, 100f) {{
                            collidesAir = true; collidesGround = false; collidesTiles = false;
                            buildingDamageMultiplier = 0; drag = 0.02f;
                            hitEffect = massiveExplosion; despawnEffect = scatheSlash;
                            knockback = 0.8f; lifetime = 23;
                            height = 18; width = 9;
                            splashDamageRadius = 40; splashDamage = 80;
                            backColor = Color.valueOf("f19583"); trailColor = Color.valueOf("f19583");
                            hitColor = Color.valueOf("f19583"); frontColor = Color.valueOf("ffffff");
                            smokeEffect = shootBigSmoke;
                            despawnShake = 6;
                            lightRadius = 30; lightColor = Color.valueOf("f19583"); lightOpacity = 0.5f;
                            trailLength = 20; trailWidth = 3.5f;
                            trailEffect = Fx.none;
                        }};
                    }};
                }}
            );
        }};

        surgeMissile = new MissileUnitType("surge-missile") {{
            flying = true; speed = 8; lifetime = 240;
            deathExplosionEffect = massiveExplosion;
            trailColor = Color.valueOf("f3e979"); trailLength = 22;
            weapons.add(
                new Weapon() {{
                    shootCone = 360; mirror = false; reload = 1;
                    shootOnDeath = true; shake = 10;
                    bullet = new ExplosionBulletType(2600f, 80f) {{
                        hitColor = Color.valueOf("f3e979");
                        collidesAir = true; collidesGround = false;
                        buildingDamageMultiplier = 0;
                        fragLifeMin = 0.1f; fragBullets = 7;
                        fragBullet = new FlakBulletType(3.4f, 140f) {{
                            collidesAir = true; collidesGround = false; collidesTiles = false;
                            buildingDamageMultiplier = 0; drag = 0.02f;
                            hitEffect = massiveExplosion; despawnEffect = scatheSlash;
                            knockback = 0.8f; lifetime = 23;
                            height = 18; width = 9;
                            splashDamageRadius = 40; splashDamage = 80;
                            backColor = Color.valueOf("f3e979"); trailColor = Color.valueOf("f3e979");
                            hitColor = Color.valueOf("f3e979"); frontColor = Color.valueOf("ffffff");
                            smokeEffect = shootBigSmoke;
                            despawnShake = 7;
                            lightRadius = 30; lightColor = Color.valueOf("f3e979"); lightOpacity = 0.5f;
                            trailLength = 20; trailWidth = 3.5f;
                            trailEffect = Fx.none;
                        }};
                    }};
                }}
            );
        }};

        onagerMissile = new MissileUnitType("onager-missile") {{
            flying = true; speed = 3; lifetime = 180;
            trailColor = Color.valueOf("ffa665"); trailLength = 15;
            drawCell = false;
            weapons.add(
                new Weapon() {{
                    shootCone = 360; mirror = false; reload = 1;
                    shootOnDeath = true; shake = 6;
                    bullet = new ExplosionBulletType(360f, 46f) {{
                        hitEffect = titanExplosion;
                    }};
                }}
            );
        }};

        artillerySentry = new MissileUnitType("artillery-sentry") {{
            flying = true;
            loopSound = Sounds.none; engineSize = 0;
            rotateMoveFirst = false; drawMinimap = true;
            range = 360; speed = 0; lifetime = 660;
            drawCell = false; health = 1200;
            localizedName = "Artillery Sentry";
            description = "A small sentry turret that shoots out Artillery bullets.";
            rotateSpeed = 2;
            weapons.add(
                new Weapon() {{
                    x = 0; y = 1; rotate = false;
                    alternate = false; mirror = true;
                    inaccuracy = 7; shootSound = shootArtillery;
                    reload = 60; shootOnDeath = true;
                    bullet = new ArtilleryBulletType(3f, 45f) {{
                        lifetime = 120; width = 12; height = 12;
                        splashDamage = 25; splashDamageRadius = 16;
                        pierce = true; pierceBuilding = true;
                    }};
                }}
            );
        }};

        railgunSentry = new MissileUnitType("railgun-sentry") {{
            flying = true; speed = 0; drawCell = false;
            loopSound = Sounds.none;
            drawMinimap = true; engineSize = 0;
            rotateMoveFirst = false; range = 240;
            health = 1200; lifetime = 660;
            localizedName = "Laser Sentry";
            description = "A small sentry turret that shoots a hail of lasers.";
            rotateSpeed = 2;
            weapons.add(
                new Weapon() {{
                    x = 0; y = 1; rotate = false;
                    alternate = false; mirror = true;
                    inaccuracy = 7; reload = 60;
                    shoot = new ShootPattern() {{ shots = 6; shotDelay = 1; }};
                    shootOnDeath = true; shootSound = shootLaser;
                    bullet = new LaserBulletType() {{
                        width = 3; length = 240; damage = 12;
                        pierce = true; pierceBuilding = true;
                    }};
                }}
            );
        }};
    }
}
