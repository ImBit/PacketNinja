package xyz.bitsquidd.ninja.handler.impl.clientbound;

import net.kyori.adventure.text.Component;
import net.minecraft.network.protocol.game.ClientboundBlockDestructionPacket;
import org.jetbrains.annotations.NotNull;

import xyz.bitsquidd.ninja.format.PacketInfoBundle;
import xyz.bitsquidd.ninja.format.PacketInfoSegment;
import xyz.bitsquidd.ninja.handler.PacketHandler;
import xyz.bitsquidd.ninja.handler.PacketType;

import java.util.List;

public class BlockDestructionHandler extends PacketHandler<@NotNull ClientboundBlockDestructionPacket> {

    public BlockDestructionHandler() {
        super(
              ClientboundBlockDestructionPacket.class,
              "BlockDestruction",
              "Handles ClientboundBlockDestructionPacket.",
              PacketType.CLIENTBOUND
        );
    }

    @Override
    protected @NotNull PacketInfoBundle getPacketInfoInternal(ClientboundBlockDestructionPacket packet) {
        return PacketInfoBundle.of(
              packetType,
              Component.text(friendlyName),
              List.of(
                    PacketInfoSegment.of(Component.text("Position"), Component.text(packet.getPos().getX() + ", " + packet.getPos().getY() + ", " + packet.getPos().getZ())),
                    PacketInfoSegment.of(Component.text("Progress"), Component.text(packet.getProgress()))
              )
        );
    }

}
