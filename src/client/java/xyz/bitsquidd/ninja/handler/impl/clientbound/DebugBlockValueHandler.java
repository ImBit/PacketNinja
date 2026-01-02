package xyz.bitsquidd.ninja.handler.impl.clientbound;

import net.kyori.adventure.text.Component;
import net.minecraft.network.protocol.game.ClientboundDamageEventPacket;
import net.minecraft.network.protocol.game.ClientboundDebugBlockValuePacket;
import org.jetbrains.annotations.NotNull;

import xyz.bitsquidd.ninja.format.PacketInfoBundle;
import xyz.bitsquidd.ninja.format.PacketInfoSegment;
import xyz.bitsquidd.ninja.handler.PacketHandler;
import xyz.bitsquidd.ninja.handler.PacketType;

import java.util.List;

public class DebugBlockValueHandler extends PacketHandler<@NotNull ClientboundDebugBlockValuePacket> {

    public DebugBlockValueHandler() {
        super(
              ClientboundDebugBlockValuePacket.class,
              "DebugBlockValue",
              "Handles ClientboundDebugBlockValuePacket.",
              PacketType.CLIENTBOUND
        );
    }

    @Override
    protected @NotNull PacketInfoBundle getPacketInfoInternal(ClientboundDebugBlockValuePacket packet) {
        return PacketInfoBundle.of(
              packetType,
              Component.text(friendlyName),
              List.of(
                    PacketInfoSegment.of(Component.text("Position"), Component.text(packet.blockPos().getX() + ", " + packet.blockPos().getY() + ", " + packet.blockPos().getZ())),
                    PacketInfoSegment.of(Component.text("UpdateSubscription"), Component.text(packet.update().subscription().toString()))
              )
        );
    }

}
