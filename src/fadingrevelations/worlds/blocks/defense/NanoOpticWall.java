package fadingrevelations.worlds.blocks.defense;

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
    }

    private boolean linkValid(Building root, Building build) {
        return build instanceof NanoOpticWallBuild && Mathf.dstm(root.tileX(), root.tileY(), build.tileX(), build.tileY()) <= maxShareStep;
    }

    public class NanoOpticWallBuild extends Building {
        private final Seq<Building> toDamage = new Seq<>();
        private final Queue<Building> queue = new Queue<>();

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
        public float handleDamage(float amount) {
            findLinkWalls();
            float shareDamage = (amount / toDamage.size) * (1 - damageReduction);
            for (Building b : toDamage) {
                if (b == self()) continue;
                b.health -= shareDamage;
                b.healthChanged();
                if (b.health <= 0 && !b.dead()) {
                    b.kill();
                }
            }
            return shareDamage;
        }
    }
}
