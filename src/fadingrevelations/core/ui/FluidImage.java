package fadingrevelations.core.ui;

import arc.graphics.g2d.*;
import arc.scene.ui.*;

public class FluidImage extends Image {
    public float amount;

    public FluidImage(TextureRegion region, float amount) {
        super(region);
        this.amount = amount;
    }

    @Override
    public void draw() {
        setScale(Math.min(1f, amount / 60f));
        super.draw();
    }
}
