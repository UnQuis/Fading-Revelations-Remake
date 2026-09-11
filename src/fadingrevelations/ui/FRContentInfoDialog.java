package fadingrevelations.ui;

import mindustry.Vars;
import mindustry.ctype.UnlockableContent;
import mindustry.gen.Building;
import mindustry.ui.dialogs.ContentInfoDialog;

import fadingrevelations.content.FROrbitalRing;
import fadingrevelations.content.FRSettings;

/**
 * Vanilla content info dialog with one extra row in every block's stats:
 * the current state of the Orbital Ring's planet-wide boost. Once the ring
 * around Cangirus is finished, every block shows "Orbital Boost: +200%".
 *
 * Replaces Vars.ui.content in FadingRevelationsMod.init().
 */
public class FRContentInfoDialog extends ContentInfoDialog {

    @Override
    public void show(UnlockableContent content) {
        super.show(content);

        if (content instanceof mindustry.world.Block) {
            boolean active = FROrbitalRing.boostActive();

            cont.row();
            cont.table(t -> {
                if (active) {
                    t.add("[lightgray]" + FRSettings.bundle("fr.ring.orbital-boost", "Orbital Boost") + ":[] " +
                        "[green]+200%[] " +
                        "[gray]" + FRSettings.bundle("fr.ring.orbital-boost-planet", "(planet-wide, Cangirus)") + "[]"
                    ).padTop(8f);
                } else {
                    t.add("[lightgray]" + FRSettings.bundle("fr.ring.orbital-boost", "Orbital Boost") + ":[] " +
                        "[gray]" + FRSettings.bundle("fr.ring.orbital-boost-locked", "locked - complete the Orbital Ring on Cangirus") + "[]"
                    ).padTop(8f);
                }
            });
        }
    }
}
