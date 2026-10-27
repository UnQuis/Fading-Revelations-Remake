package fadingrevelations.content;

import arc.util.io.Reads;
import arc.util.io.Writes;
import mindustry.Vars;
import mindustry.ctype.Content;
import mindustry.ctype.ContentType;
import mindustry.ctype.UnlockableContent;
import mindustry.net.NetConnection;
import mindustry.net.Packet;

/**
 * Client -> host request for campaign progress that the client cannot write
 * authoritatively: global research and lore discoveries.
 */
public class FRCampaignPacket extends Packet {
    public static final byte RESEARCH = 0;
    public static final byte LORE = 1;

    public byte action;
    public byte contentType;
    public short contentId;
    public String entryId;

    public FRCampaignPacket() {}

    public FRCampaignPacket(UnlockableContent content) {
        action = RESEARCH;
        contentType = (byte) content.getContentType().ordinal();
        contentId = content.id;
    }

    public FRCampaignPacket(byte action, String entryId) {
        this.action = action;
        this.entryId = entryId;
    }

    @Override
    public void write(Writes write) {
        write.b(action);
        if (action == RESEARCH) {
            write.b(contentType);
            write.s(contentId);
        } else {
            write.str(entryId == null ? "" : entryId);
        }
    }

    @Override
    public void read(Reads read) {
        action = read.b();
        if (action == RESEARCH) {
            contentType = read.b();
            contentId = read.s();
        } else {
            entryId = read.str();
        }
    }

    @Override
    public void handleServer(NetConnection connection) {
        if (action == RESEARCH) {
            if (contentType < 0 || contentType >= ContentType.all.length) return;

            ContentType type = ContentType.all[contentType];
            if (type == null) return;

            Content content = Vars.content.getByID(type, contentId);
            if (content instanceof UnlockableContent unlockable) {
                FRTechTree.researchFromNetwork(unlockable, connection);
            }
        } else if (action == LORE) {
            FRLore.discoverFromNetwork(entryId, connection);
        }
    }
}
