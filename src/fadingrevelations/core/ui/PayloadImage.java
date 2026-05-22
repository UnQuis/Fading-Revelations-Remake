package fadingrevelations.core.ui;

import arc.graphics.g2d.*;
import arc.scene.ui.*;
import arc.scene.ui.layout.*;
import mindustry.gen.*;
import static mindustry.Vars.*;

public class PayloadImage extends Table {
    public int amount;

    public PayloadImage(TextureRegion region, int amount) {
        this.amount = amount;
        Image img = new Image(region);
        add(img).size(iconSmall);
        if (amount > 0) {
            add(new Label("" + amount)).padLeft(2);
        }
    }
}
