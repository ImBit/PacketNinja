package xyz.bitsquidd.ninja.handler.impl.clientbound;

import net.kyori.adventure.text.Component;
import net.minecraft.network.protocol.game.ClientboundBundleDelimiterPacket;
import org.jetbrains.annotations.NotNull;

import xyz.bitsquidd.ninja.format.PacketInfoBundle;
import xyz.bitsquidd.ninja.handler.PacketHandler;
import xyz.bitsquidd.ninja.handler.PacketType;

import java.util.List;

public class BundleDelimiterHandler extends PacketHandler<@NotNull ClientboundBundleDelimiterPacket> {

    public BundleDelimiterHandler() {
        super(
              ClientboundBundleDelimiterPacket.class,
              "BundleDelimiter",
              "Handles ClientboundBundleDelimiterPacket.",
              PacketType.CLIENTBOUND
        );
    }

    @Override
    protected @NotNull PacketInfoBundle getPacketInfoInternal(ClientboundBundleDelimiterPacket packet) {
        return PacketInfoBundle.of(
              packetType,
              Component.text(friendlyName),
              List.of()
        );
    }

}
