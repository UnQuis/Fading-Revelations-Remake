package fadingrevelations.core.ui;

import arc.graphics.*;
import arc.graphics.g2d.*;
import arc.scene.ui.*;
import arc.scene.ui.layout.*;
import mindustry.graphics.*;
import mindustry.gen.*;
import static mindustry.Vars.*;

public class HeatImage extends Table {
    public float amount;

    public HeatImage(float amount) {
        this.amount = amount;
        Image heatImage = new Image(Icon.waves.getRegion());
        heatImage.setColor(Pal.lightOrange);
        add(heatImage).size(iconSmall);
        if (amount > 0) {
            add(new Label(String.format("%.0f", amount))).padLeft(2);
        }
    }
}
