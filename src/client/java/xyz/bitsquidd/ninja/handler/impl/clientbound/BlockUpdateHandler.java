package xyz.bitsquidd.ninja.handler.impl.clientbound;

import net.kyori.adventure.text.Component;
import net.minecraft.network.protocol.game.ClientboundBlockEventPacket;
import net.minecraft.network.protocol.game.ClientboundBlockUpdatePacket;
import org.jetbrains.annotations.NotNull;

import xyz.bitsquidd.ninja.format.PacketInfoBundle;
import xyz.bitsquidd.ninja.format.PacketInfoSegment;
import xyz.bitsquidd.ninja.handler.PacketHandler;
import xyz.bitsquidd.ninja.handler.PacketType;

import java.util.List;

public class BlockUpdateHandler extends PacketHandler<@NotNull ClientboundBlockUpdatePacket> {

    public BlockUpdateHandler() {
        super(
              ClientboundBlockUpdatePacket.class,
              "BlockUpdate",
              "Handles ClientboundBlockUpdatePacket.",
              PacketType.CLIENTBOUND
        );
    }

    @Override
    protected @NotNull PacketInfoBundle getPacketInfoInternal(ClientboundBlockUpdatePacket packet) {
        return PacketInfoBundle.of(
              packetType,
              Component.text(friendlyName),
              List.of(
                    PacketInfoSegment.of(Component.text("Position"), Component.text(packet.getPos().getX() + ", " + packet.getPos().getY() + ", " + packet.getPos().getZ())),
                    PacketInfoSegment.of(Component.text("BlockState"), Component.text(packet.getBlockState().toString()))
              )
        );
    }

}
