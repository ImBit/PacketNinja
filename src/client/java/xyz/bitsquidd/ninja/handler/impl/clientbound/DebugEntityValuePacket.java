package xyz.bitsquidd.ninja.handler.impl.clientbound;

import net.kyori.adventure.text.Component;
import net.minecraft.network.protocol.game.ClientboundDebugChunkValuePacket;
import net.minecraft.network.protocol.game.ClientboundDebugEntityValuePacket;
import org.jetbrains.annotations.NotNull;

import xyz.bitsquidd.ninja.format.PacketInfoBundle;
import xyz.bitsquidd.ninja.format.PacketInfoSegment;
import xyz.bitsquidd.ninja.handler.PacketHandler;
import xyz.bitsquidd.ninja.handler.PacketType;

import java.util.List;

public class DebugEntityValuePacket extends PacketHandler<@NotNull ClientboundDebugEntityValuePacket> {

    public DebugEntityValuePacket() {
        super(
              ClientboundDebugEntityValuePacket.class,
              "DebugEntityValue",
              "Handles ClientboundDebugEntityValuePacket.",
              PacketType.CLIENTBOUND
        );
    }

    @Override
    protected @NotNull PacketInfoBundle getPacketInfoInternal(ClientboundDebugEntityValuePacket packet) {
        return PacketInfoBundle.of(
              packetType,
              Component.text(friendlyName),
              List.of(
                    PacketInfoSegment.of(Component.text("EntityId"), Component.text(packet.entityId())),
                    PacketInfoSegment.of(Component.text("UpdateSubscription"), Component.text(packet.update().subscription().toString()))
              )
        );
    }

}
