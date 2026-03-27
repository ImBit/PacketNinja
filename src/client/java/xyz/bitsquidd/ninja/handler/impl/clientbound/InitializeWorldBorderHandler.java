package xyz.bitsquidd.ninja.handler.impl.clientbound;

import net.kyori.adventure.text.Component;
import net.minecraft.network.protocol.game.ClientboundInitializeBorderPacket;

import org.jetbrains.annotations.NotNull;

import xyz.bitsquidd.ninja.format.PacketInfoBundle;
import xyz.bitsquidd.ninja.format.PacketInfoSegment;
import xyz.bitsquidd.ninja.handler.PacketHandler;
import xyz.bitsquidd.ninja.handler.PacketType;

import java.util.List;

public class InitializeWorldBorderHandler extends PacketHandler<ClientboundInitializeBorderPacket> {
    public InitializeWorldBorderHandler() {
        super(
              ClientboundInitializeBorderPacket.class,
              "InitializeWorldBorder",
              "Handles world border initialization",
              PacketType.CLIENTBOUND
        );
    }

    @Override
    protected @NotNull PacketInfoBundle getPacketInfoInternal(ClientboundInitializeBorderPacket packet) {
        return PacketInfoBundle.of(
              packetType,
              Component.text(friendlyName),
              List.of(
                    PacketInfoSegment.of(Component.text("Location"), Component.text(String.format("X=%s, Z=%s", packet.getNewCenterX(), packet.getNewCenterZ()))),
                    PacketInfoSegment.of(Component.text("Size"), Component.text(String.format("%s -> %s", packet.getOldSize(), packet.getNewSize()))),
                    PacketInfoSegment.of(Component.text("Speed"), Component.text(packet.getLerpTime() + " ticks")),
                    PacketInfoSegment.of(Component.text("Portal boundary"), Component.text(packet.getNewAbsoluteMaxSize())),
                    PacketInfoSegment.of(Component.text("Warning"), Component.text(String.format("Blocks=%s, time=%s ticks", packet.getWarningBlocks(), packet.getWarningTime())))
              )
        );
    }
}
