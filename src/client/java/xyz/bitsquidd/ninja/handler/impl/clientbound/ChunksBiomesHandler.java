package xyz.bitsquidd.ninja.handler.impl.clientbound;

import net.kyori.adventure.text.Component;
import net.minecraft.network.protocol.game.ClientboundChunkBatchFinishedPacket;
import net.minecraft.network.protocol.game.ClientboundChunksBiomesPacket;
import org.jetbrains.annotations.NotNull;

import xyz.bitsquidd.ninja.format.FormatHelper;
import xyz.bitsquidd.ninja.format.PacketInfoBundle;
import xyz.bitsquidd.ninja.format.PacketInfoSegment;
import xyz.bitsquidd.ninja.handler.PacketHandler;
import xyz.bitsquidd.ninja.handler.PacketType;

import java.util.List;

public class ChunksBiomesHandler extends PacketHandler<@NotNull ClientboundChunksBiomesPacket> {

    public ChunksBiomesHandler() {
        super(
              ClientboundChunksBiomesPacket.class,
              "ChunksBiomes",
              "Handles ClientboundChunksBiomesPacket.",
              PacketType.CLIENTBOUND
        );
    }

    @Override
    protected @NotNull PacketInfoBundle getPacketInfoInternal(ClientboundChunksBiomesPacket packet) {
        String chunkBiomeDataList = FormatHelper.formatList(packet.chunkBiomeData().stream().toList(), MAX_DISPLAYED_ENTRIES);

        return PacketInfoBundle.of(
              packetType,
              Component.text(friendlyName),
              List.of(
                    PacketInfoSegment.of(Component.text("BatchSize"), Component.text(chunkBiomeDataList))
              )
        );
    }

}
