package dev.ftb.mods.ftbquests.net;

import dev.architectury.networking.NetworkManager;
import dev.architectury.networking.simple.BaseS2CMessage;
import dev.architectury.networking.simple.MessageType;
import dev.ftb.mods.ftbquests.client.FTBQuestsNetClient;
import java.util.UUID;
import net.minecraft.network.FriendlyByteBuf;

public class ObjectCompletedResetMessage extends BaseS2CMessage {

    private final UUID team;

    private final long id;

    public ObjectCompletedResetMessage(FriendlyByteBuf buffer) {
        this.team = buffer.readUUID();
        this.id = buffer.readLong();
    }

    public ObjectCompletedResetMessage(UUID t, long i) {
        this.team = t;
        this.id = i;
    }

    @Override
    public MessageType getType() {
        return FTBQuestsNetHandler.OBJECT_COMPLETED_RESET;
    }

    @Override
    public void write(FriendlyByteBuf buffer) {
        buffer.writeUUID(this.team);
        buffer.writeLong(this.id);
    }

    @Override
    public void handle(NetworkManager.PacketContext context) {
        FTBQuestsNetClient.objectCompleted(this.team, this.id, null);
    }
}