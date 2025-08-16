package xyz.bitsquidd.mixin;

import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import net.minecraft.client.Minecraft;
import net.minecraft.network.Connection;
import net.minecraft.network.protocol.BundlePacket;
import net.minecraft.network.protocol.Packet;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import xyz.bitsquidd.PacketInterceptorMod;

@Mixin(Connection.class)
public class MixinConnection {

    @Inject(method = "doSendPacket", at = @At("HEAD"))
    public void doSendPacket(Packet<?> packet, ChannelFutureListener channelFutureListener, boolean bl, CallbackInfo ci) {
        if (PacketInterceptorMod.logPackets && Minecraft.getInstance().player != null) {
            if (packet instanceof BundlePacket<?> bundlePacket) {
                for (Packet<?> subPacket : bundlePacket.subPackets()) {
                    PacketInterceptorMod.logPacket(true, subPacket);
                }
            } else {
                PacketInterceptorMod.logPacket(true, packet);
            }
        }
    }

    @Inject(method = "channelRead0(Lio/netty/channel/ChannelHandlerContext;Lnet/minecraft/network/protocol/Packet;)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/network/Connection;genericsFtw(Lnet/minecraft/network/protocol/Packet;Lnet/minecraft/network/PacketListener;)V"))
    public void readPacket(ChannelHandlerContext context, Packet<?> packet, CallbackInfo ci) {
        if (PacketInterceptorMod.logPackets && Minecraft.getInstance().player != null) {
            if (packet instanceof BundlePacket<?> bundlePacket) {
                for (Packet<?> subPacket : bundlePacket.subPackets()) {
                    PacketInterceptorMod.logPacket(false, subPacket);
                }
            } else {
                PacketInterceptorMod.logPacket(false, packet);
            }
        }
    }
}