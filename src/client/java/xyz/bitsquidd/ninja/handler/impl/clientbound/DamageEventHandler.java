package xyz.bitsquidd.ninja.handler.impl.clientbound;

import net.kyori.adventure.text.Component;
import net.minecraft.network.protocol.game.ClientboundDamageEventPacket;
import org.jetbrains.annotations.NotNull;

import xyz.bitsquidd.ninja.format.PacketInfoBundle;
import xyz.bitsquidd.ninja.format.PacketInfoSegment;
import xyz.bitsquidd.ninja.handler.PacketHandler;
import xyz.bitsquidd.ninja.handler.PacketType;

import java.util.List;

public class DamageEventHandler extends PacketHandler<@NotNull ClientboundDamageEventPacket> {

    public DamageEventHandler() {
        super(
              ClientboundDamageEventPacket.class,
              "DamageEvent",
              "Handles ClientboundCustomChatCompletionsPacket.",
              PacketType.CLIENTBOUND
        );
    }

    @Override
    protected @NotNull PacketInfoBundle getPacketInfoInternal(ClientboundDamageEventPacket packet) {
        return PacketInfoBundle.of(
              packetType,
              Component.text(friendlyName),
              List.of(
                    PacketInfoSegment.of(Component.text("EntityId"), Component.text(packet.entityId())),
                    PacketInfoSegment.of(Component.text("SourceCauseId"), Component.text(packet.sourceCauseId())),
                    PacketInfoSegment.of(Component.text("SourceType"), Component.text(packet.sourceType().getRegisteredName()))
              )
        );
    }

}
