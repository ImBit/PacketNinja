package xyz.bitsquidd.ninja.handler.impl.clientbound;

import net.kyori.adventure.text.Component;
import net.minecraft.network.protocol.game.ClientboundAwardStatsPacket;
import org.jetbrains.annotations.NotNull;

import xyz.bitsquidd.ninja.format.FormatHelper;
import xyz.bitsquidd.ninja.format.PacketInfoBundle;
import xyz.bitsquidd.ninja.format.PacketInfoSegment;
import xyz.bitsquidd.ninja.handler.PacketHandler;
import xyz.bitsquidd.ninja.handler.PacketType;

import java.util.List;
import java.util.Map;

public class AwardStatsHandler extends PacketHandler<@NotNull ClientboundAwardStatsPacket> {

    public AwardStatsHandler() {
        super(
              ClientboundAwardStatsPacket.class,
              "AwardStats",
              "Handles ClientboundAwardStatsPacket.",
              PacketType.CLIENTBOUND
        );
    }

    @Override
    protected @NotNull PacketInfoBundle getPacketInfoInternal(ClientboundAwardStatsPacket packet) {

        String statsList = FormatHelper.formatList(
              !packet.stats().isEmpty()
              ? packet.stats().object2IntEntrySet().stream()
                    .map(Map.Entry::getKey)
                    .toList()
              : List.of(),
              MAX_DISPLAYED_ENTRIES
        );
        return PacketInfoBundle.of(
              packetType,
              Component.text(friendlyName),
              List.of(
                    PacketInfoSegment.of(Component.text("StatsCount"), Component.text(packet.stats().size())),
                    PacketInfoSegment.of(Component.text("Stats"), Component.text(statsList))
              )
        );
    }

}
