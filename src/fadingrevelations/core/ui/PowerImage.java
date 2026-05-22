package fadingrevelations.core.ui;

import arc.graphics.*;
import arc.graphics.g2d.*;
import arc.scene.ui.*;
import arc.scene.ui.layout.*;
import mindustry.graphics.*;
import mindustry.gen.*;
import static mindustry.Vars.*;

public class PowerImage extends Table {
    public float amount;

    public PowerImage(float amount) {
        this.amount = amount;
        Image powerImage = new Image(Icon.power.getRegion());
        powerImage.setColor(Pal.power);
        add(powerImage).size(iconSmall);
        if (amount > 0) {
            add(new Label(String.format("%.1f", amount))).padLeft(2);
        }
    }
}
