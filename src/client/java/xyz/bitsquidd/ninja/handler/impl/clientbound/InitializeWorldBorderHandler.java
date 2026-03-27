package xyz.bitsquidd.ninja.handler.impl.clientbound;

import net.kyori.adventure.text.Component;
import net.minecraft.network.protocol.game.ClientboundInitializeBorderPacket;
import org.jetbrains.annotations.NotNull;

import xyz.bitsquidd.ninja.format.PacketInfo;
import xyz.bitsquidd.ninja.format.PacketInfoBundle;
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
                    PacketInfo.data(Component.text("Location"), Component.text(String.format("X=%s, Z=%s", packet.getNewCenterX(), packet.getNewCenterZ()))),
                    PacketInfo.data(Component.text("Size"), Component.text(String.format("%s -> %s", packet.getOldSize(), packet.getNewSize()))),
                    PacketInfo.data(Component.text("Speed"), Component.text(packet.getLerpTime() + " ticks")),
                    PacketInfo.data(Component.text("Portal boundary"), Component.text(packet.getNewAbsoluteMaxSize())),
                    PacketInfo.data(Component.text("Warning"), Component.text(String.format("Blocks=%s, time=%s ticks", packet.getWarningBlocks(), packet.getWarningTime())))
              )
        );
    }
}
