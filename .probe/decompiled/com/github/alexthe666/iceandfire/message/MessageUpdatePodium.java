package com.github.alexthe666.iceandfire.message;

import com.github.alexthe666.citadel.server.message.PacketBufferUtils;
import com.github.alexthe666.iceandfire.IceAndFire;
import com.github.alexthe666.iceandfire.entity.tile.TileEntityPodium;
import java.util.function.Supplier;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.fml.LogicalSide;
import net.minecraftforge.network.NetworkEvent;

public class MessageUpdatePodium {

    public long blockPos;

    public ItemStack heldStack;

    public MessageUpdatePodium(long blockPos, ItemStack heldStack) {
        this.blockPos = blockPos;
        this.heldStack = heldStack;
    }

    public MessageUpdatePodium() {
    }

    public static MessageUpdatePodium read(FriendlyByteBuf buf) {
        return new MessageUpdatePodium(buf.readLong(), PacketBufferUtils.readItemStack(buf));
    }

    public static void write(MessageUpdatePodium message, FriendlyByteBuf buf) {
        buf.writeLong(message.blockPos);
        PacketBufferUtils.writeItemStack(buf, message.heldStack);
    }

    public static class Handler {

        public static void handle(MessageUpdatePodium message, Supplier<NetworkEvent.Context> contextSupplier) {
            NetworkEvent.Context context = (NetworkEvent.Context) contextSupplier.get();
            context.enqueueWork(() -> {
                Player player = context.getSender();
                if (context.getDirection().getReceptionSide() == LogicalSide.CLIENT) {
                    player = IceAndFire.PROXY.getClientSidePlayer();
                }
                if (player != null) {
                    BlockPos pos = BlockPos.of(message.blockPos);
                    if (player.m_9236_().getBlockEntity(pos) instanceof TileEntityPodium podium) {
                        podium.setItem(0, message.heldStack);
                    }
                }
            });
            context.setPacketHandled(true);
        }
    }
}