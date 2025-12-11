package xyz.bitsquidd.ninja.handler.impl.clientbound;

import net.kyori.adventure.text.Component;
import net.minecraft.network.protocol.game.ClientboundChunkBatchFinishedPacket;
import net.minecraft.network.protocol.game.ClientboundChunkBatchStartPacket;
import org.jetbrains.annotations.NotNull;

import xyz.bitsquidd.ninja.format.PacketInfoBundle;
import xyz.bitsquidd.ninja.format.PacketInfoSegment;
import xyz.bitsquidd.ninja.handler.PacketHandler;
import xyz.bitsquidd.ninja.handler.PacketType;

import java.util.List;

public class ChunkBatchStartHandler extends PacketHandler<@NotNull ClientboundChunkBatchStartPacket> {

    public ChunkBatchStartHandler() {
        super(
              ClientboundChunkBatchStartPacket.class,
              "ChunkBatchStart",
              "Handles ClientboundChunkBatchStartPacket.",
              PacketType.CLIENTBOUND
        );
    }

    @Override
    protected @NotNull PacketInfoBundle getPacketInfoInternal(ClientboundChunkBatchStartPacket packet) {
        return PacketInfoBundle.of(
              packetType,
              Component.text(friendlyName),
              List.of()
        );
    }

}
