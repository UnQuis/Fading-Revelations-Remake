package fadingrevelations.content;

import mindustry.Vars;
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
        buildType = OutpostBuild::new;
    }

    public class OutpostBuild extends Building {
        @Override
        public boolean acceptItem(Building source, Item item) {
            return items.get(item) < getMaximumAccepted(item);
        }

        @Override
        public void updateTile() {
            if (items.total() > 0) {
                Building core = core();
                if (core != null) {
                    for (int i = 0; i < Vars.content.items().size; i++) {
                        Item item = Vars.content.item(i);
                        int amount = items.get(item);
                        if (amount > 0) {
                            items.remove(item, amount);
                            core.items.add(item, amount);
                        }
                    }
                }
            }
        }
    }
}
