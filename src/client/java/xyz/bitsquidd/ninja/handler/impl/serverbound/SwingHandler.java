package xyz.bitsquidd.ninja.handler.impl.serverbound;

import net.kyori.adventure.text.Component;
import net.minecraft.network.protocol.game.ServerboundSwingPacket;
import org.jetbrains.annotations.NotNull;

import xyz.bitsquidd.ninja.format.PacketInfoBundle;
import xyz.bitsquidd.ninja.handler.PacketHandler;
import xyz.bitsquidd.ninja.handler.PacketType;

import java.util.List;

public class SwingHandler extends PacketHandler<@NotNull ServerboundSwingPacket> {

    public SwingHandler() {
        super(
              ServerboundSwingPacket.class,
              "Swing",
              "Handles when the player swings their hand.",
              PacketType.SERVERBOUND
        );
    }

    @Override
    protected @NotNull PacketInfoBundle getPacketInfoInternal(ServerboundSwingPacket packet) {
        return PacketInfoBundle.of(
              packetType,
              Component.text(friendlyName),
              List.of()
        );
    }

}