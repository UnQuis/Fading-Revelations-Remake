package fadingrevelations.content;

import arc.Core;
import arc.Events;
import arc.math.Mathf;
import arc.struct.ObjectSet;
import arc.util.Log;
import mindustry.Vars;
import mindustry.content.Blocks;
import mindustry.game.EventType;
import mindustry.game.Team;
import mindustry.gen.Call;
import mindustry.world.Tile;

/**
 * The story of the Precursors (Предтечи), delivered through ancient
 * terminals placed in campaign sectors (see PrecursorTerminal).
 *
 * The narrative is told in three contradictory chains - one per planet -
 * plus a finale that only opens once every planet chain is complete:
 *
 * - Cangirus: the only world where Precursor systems still run. Its records
 *   claim the split was a treason.
 * - Hathor: ruins holding the other side's records. No treason - one side
 *   tried to prevent a catastrophe.
 * - Cerbero: the oldest records, written before the split. The conflict was
 *   caused by a technology the Precursors found or created: one side wanted
 *   it destroyed, the other believed it could be controlled.
 * - Finale: the three lines converge - the Precursors are gone, the war is
 *   over, only the automated systems never received the order to stop.
 *
 * Recovered entries are stored per player in Core.settings (same approach
 * as FROrbitalRing).
 */
public class FRLore {
    public static final String
        CANGIRUS = "cangirus", HATHOR = "hathor", CERBERO = "cerbero", FINALE = "finale";

    public static class LoreEntry {
        public final String id, chain;

        public LoreEntry(String chain, String id) {
            this.chain = chain;
            this.id = id;
        }

        public String title() {
            return FRSettings.bundle("fr.lore." + id + ".title", id);
        }

        public String text() {
            return FRSettings.bundle("fr.lore." + id + ".text", "");
        }
    }

    /** All entries, in reading order. */
    public static final LoreEntry[] entries = {
        //Cangirus - the treason version
        new LoreEntry(CANGIRUS, "c1-first-contact"),
        new LoreEntry(CANGIRUS, "c2-old-protocols"),
        new LoreEntry(CANGIRUS, "c3-betrayal"),
        new LoreEntry(CANGIRUS, "c4-culprit"),
        new LoreEntry(CANGIRUS, "c5-inconsistencies"),
        //Hathor - the other side's version
        new LoreEntry(HATHOR, "h1-silence"),
        new LoreEntry(HATHOR, "h2-other-side"),
        new LoreEntry(HATHOR, "h3-no-betrayal"),
        new LoreEntry(HATHOR, "h4-project"),
        //Cerbero - the oldest version
        new LoreEntry(CERBERO, "r1-dead-world"),
        new LoreEntry(CERBERO, "r2-before-the-split"),
        new LoreEntry(CERBERO, "r3-discovery"),
        new LoreEntry(CERBERO, "r4-weapon"),
        //the convergence
        new LoreEntry(FINALE, "f1-three-lines"),
        new LoreEntry(FINALE, "f2-last-enemy"),
    };

    private static final String SETTING_KEY = "fr-lore";

    public static void load() {
        Events.on(EventType.WorldLoadEvent.class, event -> placeTerminal());
    }

    //region discovery

    /** @return the first undiscovered entry of the chain, in order. */
    public static LoreEntry nextEntry(String chain) {
        for (LoreEntry entry : entries) {
            if (entry.chain.equals(chain) && !FRCampaignState.lore.contains(entry.id)) return entry;
        }
        return null;
    }

    /** Whether every non-finale chain is complete. */
    public static boolean allChainsComplete() {
        return nextEntry(CANGIRUS) == null && nextEntry(HATHOR) == null && nextEntry(CERBERO) == null;
    }

    /** Marks an entry as read. @return true if it was new. */
    public static boolean discover(LoreEntry entry) {
        if (entry == null) return false;

        if (Vars.net.client()) {
            // The server owns campaign progress; ask it to record the entry.
            Vars.net.send(new FRCampaignPacket(FRCampaignPacket.LORE, entry.id), true);
        }

        if (!FRCampaignState.lore.add(entry.id)) return false;
        // A lore discovery is a one-off event, so persist it immediately instead of waiting for
        // the throttled progress save.
        FRCampaignState.save(true);
        return true;
    }

    public static boolean isDiscovered(LoreEntry entry) {
        return entry != null && FRCampaignState.lore.contains(entry.id);
    }

    /** Discovered count of a chain (finale only counts when unlocked). */
    public static int chainProgress(String chain) {
        int n = 0;
        for (LoreEntry entry : entries) {
            if (entry.chain.equals(chain) && FRCampaignState.lore.contains(entry.id)) n++;
        }
        return n;
    }

    public static int chainTotal(String chain) {
        int n = 0;
        for (LoreEntry entry : entries) {
            if (entry.chain.equals(chain)) n++;
        }
        return n;
    }

    //endregion

    //region terminal placement

    /**
     * Places one Precursor Terminal in freshly loaded campaign sectors of the
     * mod's planets, if there is none yet (saved sectors already carry it).
     */
    private static void placeTerminal() {
        // World changes must be made by the host, otherwise every client would place its own
        // terminal and the world would desync.
        if (Vars.net.client()) return;

        var state = Vars.state;
        if (!state.isCampaign() || state.rules.sector == null) return;

        var planet = state.rules.sector.planet;
        if (planet != FRPlanets.cangirus && planet != FRPlanets.hathor && planet != FRPlanets.cerbero) return;

        var block = FREffectBlocks.precursorTerminal;
        if (block == null) return;

        // Saved sectors already carry the terminal; the tag avoids scanning every tile again.
        if (state.rules.tags.getBool("fr.lore.terminal")) return;

        //already placed in this world?
        for (Tile tile : Vars.world.tiles) {
            if (tile.block() == block) {
                state.rules.tags.put("fr.lore.terminal", "true");
                return;
            }
        }

        Tile at = findSpot();
        if (at == null) {
            Log.warn("FRLore: no space for a precursor terminal in @", state.rules.sector.name());
            return;
        }

        // Call.setTile updates the host and sends the placement to every client. A plain
        // setBlock() would leave the terminal invisible for connected players.
        Call.setTile(at, block, state.rules.defaultTeam, 0);
        state.rules.tags.put("fr.lore.terminal", "true");
    }

    /** Finds a free 2x2 area 12..30 tiles from the map center (near the player's landing site). */
    private static Tile findSpot() {
        var world = Vars.world;
        int cx = world.width() / 2, cy = world.height() / 2;

        for (int attempt = 0; attempt < 120; attempt++) {
            float ang = Mathf.random(360f);
            float dist = Mathf.random(12f, 30f);
            int x = cx + (int)(Mathf.cosDeg(ang) * dist);
            int y = cy + (int)(Mathf.sinDeg(ang) * dist);

            if (free(x, y)) return world.tile(x, y);
        }
        //fallback: spiral out from the center
        for (int r = 4; r < world.width() / 2; r += 2) {
            for (int i = -r; i <= r; i += 2) {
                Tile found = firstFree(
                    world.tile(cx + i, cy - r), world.tile(cx + i, cy + r),
                    world.tile(cx - r, cy + i), world.tile(cx + r, cy + i));
                if (found != null) return found;
            }
        }
        return null;
    }

    private static Tile firstFree(Tile... tiles) {
        for (Tile tile : tiles) {
            if (tile != null && free(tile.x, tile.y)) return tile;
        }
        return null;
    }

    private static boolean free(int x, int y) {
        var world = Vars.world;
        if (x < 1 || y < 1 || x + 1 >= world.width() || y + 1 >= world.height()) return false;
        for (int dx = 0; dx < 2; dx++) {
            for (int dy = 0; dy < 2; dy++) {
                Tile tile = world.tile(x + dx, y + dy);
                if (tile == null || tile.block() != Blocks.air || tile.solid()) return false;
                if (tile.floor().isDeep() || tile.floor().isLiquid || tile.floor().shallow) return false;
                if (tile.team() != Team.derelict && tile.team() != Vars.state.rules.defaultTeam) return false;
            }
        }
        return true;
    }

    //endregion

    //region persistence

    static void loadLegacyProgress() {
        try {
            ObjectSet<String> data = Core.settings.getJson(SETTING_KEY, ObjectSet.class, String.class, ObjectSet::new);
            FRCampaignState.lore.clear();
            FRCampaignState.lore.addAll(data);
            if (!FRCampaignState.lore.isEmpty()) {
                Log.info("FRLore: @ entries recovered", FRCampaignState.lore.size);
            }
        } catch (Throwable t) {
            Log.err("FRLore: error loading progress", t);
            FRCampaignState.lore.clear();
        }
    }

    public static LoreEntry findEntry(String id) {
        if (id == null) return null;
        for (LoreEntry entry : entries) {
            if (entry.id.equals(id)) return entry;
        }
        return null;
    }

    static void discoverFromNetwork(String id, mindustry.net.NetConnection connection) {
        LoreEntry entry = findEntry(id);
        if (entry == null) return;

        // Enforce the same order the terminal UI enforces: only the next entry of a chain,
        // and the finale only once all three planet chains are complete.
        if (entry.chain.equals(FINALE) && !allChainsComplete()) return;
        if (entry != nextEntry(entry.chain)) return;

        discover(entry);
    }

    //endregion
}
