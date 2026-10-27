package fadingrevelations.worlds.blocks.defense;

import arc.graphics.g2d.Draw;
import arc.graphics.g2d.Fill;
import arc.math.Mathf;
import arc.struct.Queue;
import arc.struct.Seq;
import mindustry.gen.Building;
import mindustry.world.blocks.defense.Wall;

public class NanoOpticWall extends Wall {
    public float damageReduction = 0.1f;
    public float maxShareStep = 2;

    public NanoOpticWall(String name) {
        super(name);
        // Wall's constructor sets buildType = WallBuild::new. Without this the subclass build is
        // never instantiated, so handleDamage/drawSelect and every Wall mechanic were dead code.
        buildType = NanoOpticWallBuild::new;
    }

    private boolean linkValid(Building root, Building build) {
        return build instanceof NanoOpticWallBuild
            && !build.dead()
            && build.team == root.team
            && Mathf.dstm(root.tileX(), root.tileY(), build.tileX(), build.tileY()) <= maxShareStep;
    }

    // must extend Wall.WallBuild: flashHit / lightningChance / lightningDamage / lightningLength /
    // chanceDeflect are only implemented in WallBuild.collision(Bullet). Extending plain Building
    // silently disabled all of them.
    public class NanoOpticWallBuild extends WallBuild {
        private final Seq<Building> toDamage = new Seq<>();
        private final Queue<Building> queue = new Queue<>();
        private transient boolean redirectingDamage;

        private void findLinkWalls() {
            toDamage.clear();
            queue.clear();
            queue.addLast(self());
            while (queue.size > 0) {
                Building wall = queue.removeFirst();
                toDamage.addUnique(wall);
                for (Building next : wall.proximity) {
                    if (linkValid(self(), next) && !toDamage.contains(next)) {
                        toDamage.add(next);
                        queue.addLast(next);
                    }
                }
            }
        }

        @Override
        public void drawSelect() {
            findLinkWalls();
            for (Building wall : toDamage) {
                Draw.color(wall.team.color);
                Draw.alpha(0.35f);
                Fill.square(wall.x, wall.y, wall.block.size * 4f);
            }
            Draw.reset();
        }

        @Override
        public float handleDamage(float amount) {
            // Damage redirected from another wall in the link must not be shared again, or every
            // linked wall would recursively redistribute it and take far less damage than intended.
            if (redirectingDamage) return amount;

            findLinkWalls();
            if (toDamage.size <= 1) return amount;

            float shareDamage = (amount / toDamage.size) * (1 - damageReduction);
            for (Building b : toDamage) {
                if (b == self() || b.dead()) continue;

                NanoOpticWallBuild wall = (NanoOpticWallBuild) b;
                wall.redirectingDamage = true;
                try {
                    // Go through Building.damage so armor, events and destruction are handled normally.
                    wall.damage(shareDamage);
                } finally {
                    wall.redirectingDamage = false;
                }
            }
            return shareDamage;
        }
    }
}
