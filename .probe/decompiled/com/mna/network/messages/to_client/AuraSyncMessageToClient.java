package com.mna.network.messages.to_client;

import com.mna.ManaAndArtifice;
import com.mna.blocks.tileentities.ParticleEmitterTile;
import com.mna.capabilities.particles.ParticleAura;
import com.mna.capabilities.particles.ParticleAuraProvider;
import com.mna.network.messages.BaseMessage;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.common.util.LazyOptional;

public class AuraSyncMessageToClient extends BaseMessage {

    private int entityID;

    private CompoundTag tag;

    private BlockPos pos;

    private AuraSyncMessageToClient() {
        this.messageIsValid = false;
    }

    private AuraSyncMessageToClient(int entityID, CompoundTag tag) {
        this();
        this.entityID = entityID;
        this.tag = tag;
        this.messageIsValid = true;
    }

    public AuraSyncMessageToClient(BlockPos pos, CompoundTag tag) {
        this();
        this.tag = tag;
        this.pos = pos;
        this.messageIsValid = true;
    }

    public int getEntityID() {
        return this.entityID;
    }

    public CompoundTag getTag() {
        return this.tag;
    }

    @Nullable
    public BlockPos getBlockPos() {
        return this.pos;
    }

    public boolean isBlock() {
        return this.pos != null;
    }

    public static AuraSyncMessageToClient decode(FriendlyByteBuf buf) {
        AuraSyncMessageToClient msg = new AuraSyncMessageToClient();
        try {
            if (buf.readBoolean()) {
                msg.entityID = buf.readInt();
            } else {
                msg.pos = BlockPos.of(buf.readLong());
            }
            msg.tag = buf.readAnySizeNbt();
        } catch (IndexOutOfBoundsException | IllegalArgumentException var3) {
            ManaAndArtifice.LOGGER.error("Exception while reading MAPFXSyncRequestMessage: " + var3);
            return msg;
        }
        msg.messageIsValid = true;
        return msg;
    }

    public static void encode(AuraSyncMessageToClient msg, FriendlyByteBuf buf) {
        if (msg.pos == null) {
            buf.writeBoolean(true);
            buf.writeInt(msg.getEntityID());
        } else {
            buf.writeBoolean(false);
            buf.writeLong(msg.pos.asLong());
        }
        buf.writeNbt(msg.tag);
    }

    public static AuraSyncMessageToClient fromPlayer(Player player) {
        LazyOptional<ParticleAura> aura = player.getCapability(ParticleAuraProvider.AURA);
        CompoundTag tag = aura.isPresent() ? ((ParticleAura) aura.resolve().get()).save() : new CompoundTag();
        return new AuraSyncMessageToClient(player.m_19879_(), tag);
    }

    public static AuraSyncMessageToClient fromTile(ParticleEmitterTile tile) {
        return new AuraSyncMessageToClient(tile.m_58899_(), tile.getData().getTag());
    }
}