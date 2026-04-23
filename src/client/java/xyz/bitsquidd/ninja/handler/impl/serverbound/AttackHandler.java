package xyz.bitsquidd.ninja.handler.impl.serverbound;

import net.kyori.adventure.text.Component;
import net.minecraft.network.protocol.game.ServerboundAttackPacket;
import org.jetbrains.annotations.NotNull;

import xyz.bitsquidd.ninja.format.PacketInfoBundle;
import xyz.bitsquidd.ninja.handler.PacketHandler;
import xyz.bitsquidd.ninja.handler.PacketType;

import java.util.List;


public class AttackHandler extends PacketHandler<@NotNull ServerboundAttackPacket> {

    public AttackHandler() {
        super(
          ServerboundAttackPacket.class,
          "Attack",
          "Handles player attacks.",
          PacketType.SERVERBOUND
        );
    }

    @Override
    protected @NotNull PacketInfoBundle getPacketInfoInternal(ServerboundAttackPacket packet) {
        return PacketInfoBundle.of(
          packetType,
          Component.text(friendlyName),
          List.of()
        );
    }

}