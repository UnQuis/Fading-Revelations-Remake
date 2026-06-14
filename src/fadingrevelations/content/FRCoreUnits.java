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
        coreTurretUnit = new UnitType("core-turret-unit") {{
            constructor = UnitEntity::create; localizedName = "Core Defense Unit"; hidden = true; flying = true;
            drag = 0.12f; speed = 4.5f; buildSpeed = 4f; mineSpeed = 20; mineTier = 4;
            armor = 16; coreUnitDock = true; health = 600;
            itemCapacity = 160; hitSize = 14;
            playerControllable = true;
            weapons.addAll(
                copyWeapon(new Weapon("nothing") {{
                    reload = 4; shootSound = shootLaser; rotate = true; rotationLimit = 20;
                    bullet = new LaserBoltBulletType(7f, 14f) {{
                        buildingDamageMultiplier = 0.01f; pierce = true; pierceCap = 12;
                        splashDamage = 10; splashDamageRadius = 8;
                        homingRange = 200; homingPower = 0.22f;
                        healPercent = 3; collidesTeam = true;
                        status = StatusEffects.shocked; statusDuration = 30;
                        hitEffect = new MultiEffect(
                            new WaveEffect() {{ colorFrom = Color.valueOf("44aaff"); colorTo = Color.valueOf("88eeff"); rotation = 3; sizeFrom = 0; sizeTo = 7; }},
                            new WaveEffect() {{ colorFrom = Color.valueOf("88eeff"); colorTo = Color.valueOf("44aaff"); rotation = 3; sizeFrom = 7; sizeTo = 0; }}
                        );
                    }};
                }}, 0, 2),
                copyWeapon(new Weapon("nothing") {{
                    reload = 30; shootSound = Sounds.shoot; rotate = true; rotationLimit = 45;
                    shoot = new ShootSpread(3, 15);
                    bullet = new LightningBulletType() {{
                        lightningLength = 8; lightningLengthRand = 14;
                        damage = 22; lightningType = new LightningBulletType() {{
                            lightningLength = 4; lightningLengthRand = 6; damage = 11;
                            lightningColor = Color.valueOf("44aaff");
                        }};
                        lightningColor = Color.valueOf("88eeff");
                    }};
                }}, -7, 3),
                copyWeapon(new Weapon("nothing") {{
                    reload = 30; shootSound = Sounds.shoot; rotate = true; rotationLimit = 45;
                    shoot = new ShootSpread(3, 15);
                    bullet = new LightningBulletType() {{
                        lightningLength = 8; lightningLengthRand = 14;
                        damage = 22; lightningType = new LightningBulletType() {{
                            lightningLength = 4; lightningLengthRand = 6; damage = 11;
                            lightningColor = Color.valueOf("44aaff");
                        }};
                        lightningColor = Color.valueOf("88eeff");
                    }};
                }}, 7, 3),
                copyWeapon(new Weapon("nothing") {{
                    reload = 45; shootSound = Sounds.shoot; rotate = true; rotationLimit = 30;
                    shoot = new ShootSpread(4, 12);
                    bullet = new MissileBulletType(5f, 28) {{
                        lifetime = 120; splashDamage = 40; splashDamageRadius = 28;
                        homingPower = 0.1f; homingRange = 180;
                        trailColor = Color.valueOf("88eeff");
                        hitEffect = blastExplosion;
                    }};
                }}, 0, -5)
            );
            abilities.addAll(
                new ForceFieldAbility(16f, 3f, 500f, 600f) {{ alpha = 0.08f; }},
                new EnergyFieldAbility(20f, 12f, 100f) {{
                    color = Color.valueOf("44aaff"); healPercent = 4; hitBuildings = true; hitUnits = true;
                    maxTargets = 8; sectors = 6;
                    status = StatusEffects.shielded; statusDuration = 30;
                    targetAir = true; targetGround = true;
                }}
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
