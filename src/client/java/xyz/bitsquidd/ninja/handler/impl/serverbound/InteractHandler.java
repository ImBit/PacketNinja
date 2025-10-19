package xyz.bitsquidd.ninja.handler.impl.serverbound;

import net.kyori.adventure.text.Component;
import net.minecraft.network.protocol.game.ServerboundInteractPacket;
import org.jetbrains.annotations.NotNull;

import xyz.bitsquidd.ninja.format.PacketInfoBundle;
import xyz.bitsquidd.ninja.format.PacketInfoSegment;
import xyz.bitsquidd.ninja.handler.PacketHandler;
import xyz.bitsquidd.ninja.handler.PacketType;

import java.util.List;

public class InteractHandler extends PacketHandler<@NotNull ServerboundInteractPacket> {

    public InteractHandler() {
        super(
              ServerboundInteractPacket.class,
              "Interact",
              "Handles player interactions with things.",
              PacketType.SERVERBOUND
        );
    }

    @Override
    protected @NotNull PacketInfoBundle getPacketInfoInternal(ServerboundInteractPacket packet) {
        return PacketInfoBundle.of(
              packetType,
              Component.text(friendlyName),
              List.of(
                    PacketInfoSegment.of(Component.text("IsSecondary"), Component.text(packet.isUsingSecondaryAction()))
              )
        );
    }

}