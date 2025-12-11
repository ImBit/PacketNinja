package xyz.bitsquidd.ninja.handler.impl.clientbound;

import net.kyori.adventure.text.Component;
import net.minecraft.network.protocol.game.ClientboundDebugBlockValuePacket;
import net.minecraft.network.protocol.game.ClientboundDebugChunkValuePacket;
import org.jetbrains.annotations.NotNull;

import xyz.bitsquidd.ninja.format.PacketInfoBundle;
import xyz.bitsquidd.ninja.format.PacketInfoSegment;
import xyz.bitsquidd.ninja.handler.PacketHandler;
import xyz.bitsquidd.ninja.handler.PacketType;

import java.util.List;

public class DebugChunkValuePacket extends PacketHandler<@NotNull ClientboundDebugChunkValuePacket> {

    public DebugChunkValuePacket() {
        super(
              ClientboundDebugChunkValuePacket.class,
              "DebugChunkValue",
              "Handles ClientboundDebugChunkValuePacket.",
              PacketType.CLIENTBOUND
        );
    }

    @Override
    protected @NotNull PacketInfoBundle getPacketInfoInternal(ClientboundDebugChunkValuePacket packet) {
        return PacketInfoBundle.of(
              packetType,
              Component.text(friendlyName),
              List.of(
                    PacketInfoSegment.of(Component.text("Position"), Component.text(packet.chunkPos().x + ", " + packet.chunkPos().z)),
                    PacketInfoSegment.of(Component.text("UpdateSubscription"), Component.text(packet.update().subscription().toString()))
              )
        );
    }

}
