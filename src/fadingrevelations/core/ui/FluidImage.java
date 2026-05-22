package fadingrevelations.core.ui;

import arc.graphics.g2d.*;
import arc.scene.ui.*;
import arc.scene.ui.layout.*;
import static mindustry.Vars.*;

public class FluidImage extends Table {
    public float amount;

    public FluidImage(TextureRegion region, float amount) {
        this.amount = amount;
        Image img = new Image(region);
        add(img).size(iconSmall);
        if (amount > 0) {
            add(new Label(String.format("%.2f", amount / 60f))).padLeft(2);
        }
    }
}
