package xyz.bitsquidd.ninja.handler.impl.clientbound;

import net.kyori.adventure.text.Component;
import net.minecraft.network.protocol.game.ClientboundSetBorderWarningDelayPacket;
import org.jetbrains.annotations.NotNull;

import xyz.bitsquidd.ninja.format.PacketInfoBundle;
import xyz.bitsquidd.ninja.format.PacketInfoSegment;
import xyz.bitsquidd.ninja.handler.PacketHandler;
import xyz.bitsquidd.ninja.handler.PacketType;

import java.util.List;

public class SetBorderWarningDelayHandler extends PacketHandler<ClientboundSetBorderWarningDelayPacket> {

    public SetBorderWarningDelayHandler() {
        super(
              ClientboundSetBorderWarningDelayPacket.class,
              "SetBorderWarningDelay",
              "Handles world border warning delay updates",
              PacketType.CLIENTBOUND
        );
    }

    @Override
    protected @NotNull PacketInfoBundle getPacketInfoInternal(ClientboundSetBorderWarningDelayPacket packet) {
        return PacketInfoBundle.of(
              packetType,
              Component.text(friendlyName),
              List.of(
                    PacketInfoSegment.of(Component.text("Warning Time"), Component.text(packet.getWarningDelay() + " ticks"))
              )
        );
    }
}

