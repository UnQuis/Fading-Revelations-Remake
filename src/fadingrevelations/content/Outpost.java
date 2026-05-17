package fadingrevelations.content;

import mindustry.gen.Building;
import mindustry.type.Item;
import mindustry.world.Block;

public class Outpost extends Block {
    public Outpost(String name) {
        super(name);
        update = true;
        solid = true;
        hasItems = true;
        itemCapacity = 5;
    }

    public class OutpostBuild extends Building {
        @Override
        public void updateTile() {
            if (items.total() > 0) {
                Building core = core();
                if (core != null) {
                    items.each((item, amount) -> {
                        if (amount > 0) {
                            int accepted = core.acceptStack(item, amount, this);
                            if (accepted > 0) {
                                core.handleStack(item, accepted, this);
                                items.remove(item, accepted);
                            }
                        }
                    });
                }
            }
        }
    }
}
