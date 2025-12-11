package xyz.bitsquidd.ninja.handler.impl.clientbound;

import net.kyori.adventure.text.Component;
import net.minecraft.network.protocol.game.ClientboundChangeDifficultyPacket;
import net.minecraft.network.protocol.game.ClientboundChunkBatchFinishedPacket;
import org.jetbrains.annotations.NotNull;

import xyz.bitsquidd.ninja.format.PacketInfoBundle;
import xyz.bitsquidd.ninja.format.PacketInfoSegment;
import xyz.bitsquidd.ninja.handler.PacketHandler;
import xyz.bitsquidd.ninja.handler.PacketType;

import java.util.List;

public class ChunkBatchFinishedHandler extends PacketHandler<@NotNull ClientboundChunkBatchFinishedPacket> {

    public ChunkBatchFinishedHandler() {
        super(
              ClientboundChunkBatchFinishedPacket.class,
              "ChunkBatchFinished",
              "Handles ClientboundChunkBatchFinishedPacket.",
              PacketType.CLIENTBOUND
        );
    }

    @Override
    protected @NotNull PacketInfoBundle getPacketInfoInternal(ClientboundChunkBatchFinishedPacket packet) {
        return PacketInfoBundle.of(
              packetType,
              Component.text(friendlyName),
              List.of(
                    PacketInfoSegment.of(Component.text("BatchSize"), Component.text(packet.batchSize()))
              )
        );
    }

}
