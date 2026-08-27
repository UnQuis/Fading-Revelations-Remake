package fadingrevelations.worlds.blocks.storage;

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

            // Accelerate queued construction within range.
            // Uses ConstructBuild.construct(), which pays items from the core
            // progressively and finishes the block properly once complete.
            arc.struct.Queue<mindustry.game.Teams.BlockPlan> plans = team.data().plans;
            CoreBuild core = team.core();
            for (int i = 0; i < plans.size; i++) {
                mindustry.game.Teams.BlockPlan plan = plans.get(i);
                if (plan == null || plan.removed) continue;

                float dist = Mathf.dst(x, y, plan.x * tilesize, plan.y * tilesize);
                if (dist > buildRange) continue;

                Building building = world.build(plan.x, plan.y);
                if (building instanceof ConstructBuild && ((ConstructBuild) building).team == team) {
                    ConstructBuild cons = (ConstructBuild) building;
                    if (core != null && cons.buildCost > 0f) {
                        cons.construct(null, core, edelta() * buildSpeedMultiplier / cons.buildCost, plan.config);
                    }

                    if (Mathf.chance(0.05)) {
                        Fx.pointBeam.at(x, y, angleTo(cons.x, cons.y), Color.valueOf("f8ad42"));
                    }

                    if (cons.progress >= 1f || !(world.build(plan.x, plan.y) instanceof ConstructBuild)) {
                        plan.removed = true;
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
