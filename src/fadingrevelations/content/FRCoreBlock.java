package fadingrevelations.content;

import arc.Core;
import arc.graphics.Color;
import arc.graphics.g2d.*;
import arc.math.*;
import arc.util.*;
import mindustry.content.Fx;
import mindustry.entities.*;
import mindustry.entities.bullet.*;
import mindustry.gen.*;
import mindustry.graphics.*;
import mindustry.world.blocks.ConstructBlock.*;
import mindustry.world.blocks.storage.CoreBlock;

import static mindustry.Vars.*;

public class FRCoreBlock extends CoreBlock {
    public float buildRange = 800f;
    public float buildSpeedMultiplier = 5f;
    public float attackRange = 400f;
    public float turretReload = 20f;
    public BulletType bulletType;

    public FRCoreBlock(String name) {
        super(name);
        update = true;
        solid = true;
        buildType = FRCoreBuild::new;

        bulletType = new FlakBulletType(4f, 40f) {{
            shootEffect = Fx.shootBig;
            smokeEffect = Fx.shootBigSmoke;
            splashDamage = 20f;
            splashDamageRadius = 24f;
            hitEffect = Fx.blastExplosion;
            despawnEffect = Fx.blastExplosion;
            lifetime = 40f;
            collidesAir = true;
            collidesGround = true;
            fragBullet = new LightningBulletType() {{
                damage = 15f;
                lightningLength = 6;
                lightningColor = Color.valueOf("f8ad42");
            }};
            fragBullets = 1;
        }};
    }

    public class FRCoreBuild extends CoreBuild {
        public float turretRotation;
        public float reloadTimer;

        @Override
        public void updateTile() {
            super.updateTile();

            if (dead || !isValid()) return;

            var plans = team.data().plans;
            for (int i = 0; i < plans.size; i++) {
                var plan = plans.get(i);
                if (plan == null || plan.removed) continue;

                float planX = plan.x * 8f;
                float planY = plan.y * 8f;
                float dist = Mathf.dst(x, y, planX, planY);
                if (dist > buildRange) continue;

                Building building = world.build(plan.x, plan.y);
                if (building instanceof ConstructBuild cons) {
                    if (team.core() != null && team.core().items.has(cons.block.requirements)) {
                        cons.progress += (edelta() / cons.buildCost) * buildSpeedMultiplier;
                        cons.progress = Math.min(cons.progress, 1f);

                        if (Mathf.chance(0.05)) {
                            Fx.pointBeam.at(x, y, angleTo(cons.x, cons.y), Color.valueOf("f8ad42"));
                        }

                        if (cons.progress >= 1f) {
                            plan.removed = true;
                        }
                    }
                }
            }

            reloadTimer += edelta();

            Teamc target = Units.closestTarget(team, x, y, attackRange, e -> !e.dead() && e.type != null, t -> true);

            if (target != null) {
                float targetAngle = angleTo(target);
                turretRotation = Mathf.slerpDelta(turretRotation, targetAngle, 0.1f);

                if (reloadTimer >= turretReload && bulletType != null) {
                    bulletType.create(this, x, y, turretRotation);
                    reloadTimer = 0f;
                }
            } else {
                turretRotation = Mathf.slerpDelta(turretRotation, 90f, 0.05f);
            }
        }

        @Override
        public void draw() {
            super.draw();

            TextureRegion turretRegion = Core.atlas.find(name + "-turret");
            if (turretRegion != null && turretRegion.found()) {
                Draw.z(Layer.turret);
                Draw.rect(turretRegion, x, y, turretRotation - 90);
                Draw.reset();
            }
        }
    }
}
