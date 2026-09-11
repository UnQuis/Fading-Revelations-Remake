package fadingrevelations.worlds.blocks.campaign;

import arc.Core;
import arc.graphics.Color;
import arc.graphics.g2d.Draw;
import arc.graphics.g2d.TextureRegion;
import arc.math.Mathf;
import arc.util.Time;
import mindustry.Vars;
import mindustry.gen.Sounds;
import mindustry.graphics.Drawf;
import mindustry.ui.Styles;
import mindustry.ui.dialogs.BaseDialog;
import mindustry.world.Block;
import mindustry.world.meta.BuildVisibility;
import mindustry.world.meta.Env;

import fadingrevelations.content.FRLore;
import fadingrevelations.content.FRPlanets;
import fadingrevelations.content.FRSettings;

/**
 * An ancient data terminal of the Precursors, found in campaign sectors of
 * Cangirus, Hathor and Cerbero (placed automatically, see FRLore).
 *
 * Tapping it restores one more fragment of that planet's records; recovered
 * fragments are kept in the terminal's archive, which can be re-read at any
 * terminal. The finale chain only opens once all three planet chains are
 * complete.
 *
 * Concept inspired by the Asthosus mod's lore tablets (own implementation).
 */
public class PrecursorTerminal extends Block {
    public Color glowColor = Color.valueOf("9d7bff");
    public float glowRadius = 46f;

    public TextureRegion glowRegion;

    public PrecursorTerminal(String name) {
        super(name);
        update = false;
        solid = true;
        destructible = false; //ruins that nobody can destroy anymore
        consumesTap = true;
        drawTeamOverlay = false;
        buildVisibility = BuildVisibility.hidden;
        envEnabled = Env.any;
        emitLight = true;
        lightRadius = glowRadius;
        lightColor = glowColor.cpy().a(0.55f);
    }

    @Override
    public void load() {
        super.load();
        //mods cannot use the @Load annotation, so regions are resolved manually
        glowRegion = Core.atlas.find(name + "-glow");
    }

    public class PrecursorTerminalBuild extends Building {
        @Override
        public void tapped() {
            super.tapped();
            if (Vars.headless || Vars.ui == null) return;

            FRLore.LoreEntry next = nextEntry();
            if (next != null) {
                FRLore.discover(next);
                Sounds.unlock.at(x, y);
                showEntry(next, true);
            } else {
                showArchive();
            }
        }

        /** The next entry to recover on this terminal: its planet's chain, or the finale when everything else is done. */
        public FRLore.LoreEntry nextEntry() {
            String planet = chainHere();
            FRLore.LoreEntry next = FRLore.nextEntry(planet);
            if (next == null && FRLore.allChainsComplete()) {
                next = FRLore.nextEntry(FRLore.FINALE);
            }
            return next;
        }

        private String chainHere() {
            if (Vars.state.rules.sector == null || Vars.state.rules.sector.planet == null) return FRLore.CERBERO;
            var planet = Vars.state.rules.sector.planet;
            if (planet == FRPlanets.cangirus) return FRLore.CANGIRUS;
            if (planet == FRPlanets.hathor) return FRLore.HATHOR;
            return FRLore.CERBERO;
        }

        @Override
        public void draw() {
            super.draw();
            Draw.alpha(0.55f + 0.45f * Mathf.absin(Time.time, 3f, 1f));
            Draw.rect(glowRegion, x, y);
            Draw.reset();
        }

        @Override
        public void drawLight() {
            super.drawLight();
            Drawf.light(x, y, glowRadius * (0.85f + 0.15f * Mathf.absin(Time.time, 3f, 1f)), glowColor, 0.65f);
        }

        //region dialogs

        /** Shows a recovered entry. */
        public void showEntry(FRLore.LoreEntry entry, boolean fresh) {
            BaseDialog dialog = new BaseDialog(entry.title());
            dialog.addCloseButton();

            if (fresh) {
                dialog.cont.add("[accent]" + FRSettings.bundle("fr.lore.recovered", "Archive entry recovered") + "[]").pad(4f).row();
            }
            dialog.cont.add(chainName(entry.chain) + " [gray]- " +
                FRSettings.bundle("fr.lore.recovered-count", "Entries") + " " +
                FRLore.chainProgress(entry.chain) + "/" + FRLore.chainTotal(entry.chain) + "[]").pad(2f).row();

            dialog.cont.table(Styles.black6, t -> {
                t.add(entry.text()).width(Vars.mobile ? 400f : 520f).wrap().growX().pad(10f).left().labelAlign(0);
            }).growX().pad(6f).row();

            dialog.cont.button(FRSettings.bundle("fr.lore.archive.title", "Precursor Archive"), () -> {
                dialog.hide();
                showArchive();
            }).size(240f, 46f).padTop(8f);

            dialog.show();
        }

        /** The list of every recovered entry, grouped by planet. */
        public void showArchive() {
            BaseDialog dialog = new BaseDialog(FRSettings.bundle("fr.lore.archive.title", "Precursor Archive"));
            dialog.addCloseButton();

            dialog.cont.pane(list -> {
                for (String chain : new String[]{FRLore.CANGIRUS, FRLore.HATHOR, FRLore.CERBERO, FRLore.FINALE}) {
                    if (chain.equals(FRLore.FINALE) && !FRLore.allChainsComplete()) {
                        list.add("[gray]" + FRSettings.bundle("fr.lore.finale-locked",
                            "The last records unlock when all planet archives are recovered.") + "[]")
                            .growX().left().pad(8f).wrap().row();
                        continue;
                    }

                    list.add("[accent]" + chainName(chain) + "[] " +
                        "[gray](" + FRLore.chainProgress(chain) + "/" + FRLore.chainTotal(chain) + ")[]")
                        .growX().left().padTop(10f).padLeft(6f).row();

                    for (FRLore.LoreEntry entry : FRLore.entries) {
                        if (!entry.chain.equals(chain)) continue;

                        if (FRLore.isDiscovered(entry)) {
                            list.button("[light]" + entry.title() + "[]", Styles.cleart, () ->
                                showEntry(entry, false)
                            ).growX().height(40f).padLeft(14f).padRight(6f).padTop(3f).row();
                        } else {
                            list.add("[gray]???[]").growX().left().padLeft(20f).padTop(8f).row();
                        }
                    }
                }
            }).grow().maxHeight(Vars.mobile ? 500f : 640f).pad(6f);

            dialog.show();
        }

        private String chainName(String chain) {
            switch (chain) {
                case FRLore.CANGIRUS: return FRSettings.bundle("fr.lore.chain.cangirus", "Cangirus Records");
                case FRLore.HATHOR: return FRSettings.bundle("fr.lore.chain.hathor", "Hathor Records");
                case FRLore.CERBERO: return FRSettings.bundle("fr.lore.chain.cerbero", "Cerbero Records");
                default: return FRSettings.bundle("fr.lore.chain.finale", "The Convergence");
            }
        }

        //endregion
    }
}
