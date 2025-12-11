package xyz.bitsquidd.ninja.handler.impl.clientbound;

import net.kyori.adventure.text.Component;
import net.minecraft.network.protocol.game.ClientboundBlockChangedAckPacket;
import org.jetbrains.annotations.NotNull;

import xyz.bitsquidd.ninja.format.PacketInfoBundle;
import xyz.bitsquidd.ninja.format.PacketInfoSegment;
import xyz.bitsquidd.ninja.handler.PacketHandler;
import xyz.bitsquidd.ninja.handler.PacketType;

import java.util.List;

public class BlockChangedAckHandler extends PacketHandler<@NotNull ClientboundBlockChangedAckPacket> {

    public BlockChangedAckHandler() {
        super(
              ClientboundBlockChangedAckPacket.class,
              "BlockChangedAck",
              "Handles ClientboundBlockChangedAckPacket.",
              PacketType.CLIENTBOUND
        );
    }

    @Override
    protected @NotNull PacketInfoBundle getPacketInfoInternal(ClientboundBlockChangedAckPacket packet) {
        return PacketInfoBundle.of(
              packetType,
              Component.text(friendlyName),
              List.of(
                    PacketInfoSegment.of(Component.text("Sequence"), Component.text(packet.sequence()))
              )
        );
    }

}
