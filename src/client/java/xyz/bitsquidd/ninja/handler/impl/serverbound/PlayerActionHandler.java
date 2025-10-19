package xyz.bitsquidd.ninja.handler.impl.serverbound;

import net.kyori.adventure.text.Component;
import net.minecraft.network.protocol.game.ServerboundPlayerActionPacket;
import org.jetbrains.annotations.NotNull;
import xyz.bitsquidd.ninja.format.PacketInfoBundle;
import xyz.bitsquidd.ninja.format.PacketInfoSegment;
import xyz.bitsquidd.ninja.handler.PacketHandler;
import xyz.bitsquidd.ninja.handler.PacketType;

import java.util.List;

public class PlayerActionHandler extends PacketHandler<@NotNull ServerboundPlayerActionPacket> {

    public PlayerActionHandler() {
        super(
                ServerboundPlayerActionPacket.class,
                "PlayerAction",
                "Handles player actions.",
                PacketType.SERVERBOUND
        );
    }

    @Override
    protected @NotNull PacketInfoBundle getPacketInfoInternal(ServerboundPlayerActionPacket packet) {
        return PacketInfoBundle.of(
                packetType,
                Component.text(friendlyName),
                List.of(
                        PacketInfoSegment.of(Component.text("Action"), Component.text(packet.getAction().toString())),
                        PacketInfoSegment.of(Component.text("Pos"), Component.text("["+packet.getPos().getX()+","+packet.getPos().getY()+","+packet.getPos().getZ()+"]"))
                )
        );
    }

}