package me.steinborn.krypton.mixin.shared.bugfix;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.protocol.game.ClientboundCustomPayloadPacket;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin({ ClientboundCustomPayloadPacket.class })
public class CustomPayloadS2CPacketFixMemoryLeakMixin {

    @Redirect(method = { "<init>(Lnet/minecraft/network/PacketByteBuf;)V" }, at = @At(value = "INVOKE", target = "Lnet/minecraft/network/PacketByteBuf;readBytes(I)Lio/netty/buffer/ByteBuf;"))
    private ByteBuf deserialize$shouldExplicitlyCopyAsUnpooledBufferDueToShenanigans(FriendlyByteBuf instance, int length) {
        return Unpooled.copiedBuffer(instance.readSlice(length));
    }
}