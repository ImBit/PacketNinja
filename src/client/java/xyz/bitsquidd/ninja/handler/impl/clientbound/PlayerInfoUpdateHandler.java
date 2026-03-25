package xyz.bitsquidd.ninja.handler.impl.clientbound;

import com.mojang.serialization.JsonOps;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.kyori.adventure.text.serializer.gson.GsonComponentSerializer;
import net.minecraft.network.chat.ComponentSerialization;
import net.minecraft.network.protocol.game.ClientboundPlayerInfoUpdatePacket;
import org.jetbrains.annotations.NotNull;

import xyz.bitsquidd.ninja.PacketInterceptorMod;
import xyz.bitsquidd.ninja.format.PacketInfoBundle;
import xyz.bitsquidd.ninja.format.PacketInfoList;
import xyz.bitsquidd.ninja.format.PacketInfoRow;
import xyz.bitsquidd.ninja.format.PacketInfoSegment;
import xyz.bitsquidd.ninja.handler.PacketHandler;
import xyz.bitsquidd.ninja.handler.PacketType;

import java.util.ArrayList;
import java.util.List;

public class PlayerInfoUpdateHandler extends PacketHandler<@NotNull ClientboundPlayerInfoUpdatePacket> {
    public PlayerInfoUpdateHandler() {
        super(
              ClientboundPlayerInfoUpdatePacket.class,
              "PlayerInfoUpdate",
              "Handles player info updates",
              PacketType.CLIENTBOUND
        );
    }

    private String extractDisplayName(@NotNull ClientboundPlayerInfoUpdatePacket.Entry entry) {
        var displayName = entry.displayName();
        if(displayName == null) {
            return "<none>";
        }

        // here is the fun part: displayName is Minecraft's Component, we want to convert it to MiniMessage
        var jsonElement = ComponentSerialization.CODEC.encodeStart(JsonOps.COMPRESSED, displayName)
              .resultOrPartial(error ->
                  PacketInterceptorMod.LOGGER.error("Failed to serialize displayName for player {}: {}", entry.profileId(), error)
              )
              .orElse(null);
        if(jsonElement == null) {
            return "<invalid displayName>";
        }

        var adventure = GsonComponentSerializer.gson().deserialize(jsonElement.toString());
        return MiniMessage.miniMessage().serialize(adventure);
    }

    @Override
    protected @NotNull PacketInfoBundle getPacketInfoInternal(@NotNull ClientboundPlayerInfoUpdatePacket packet) {
        List<ClientboundPlayerInfoUpdatePacket.Entry> displayedEntries = packet.entries().stream()
              .limit(MAX_DISPLAYED_ENTRIES)
              .toList();

        List<PacketInfoRow> rows = new ArrayList<>();
        rows.add(PacketInfoSegment.of(Component.text("EntryCount"), Component.text(displayedEntries.size())));

        var entryLists = displayedEntries.stream().map(entry -> {
            // identity
            var displayName = extractDisplayName(entry);
            var identity = String.format("displayName=%s, uuid=%s", displayName, entry.profileId());
            var identitySegment = PacketInfoSegment.of(Component.text("Identity"), Component.text(identity));

            // state
            String state = String.format(
                  "listed=%s, listOrder=%d, showHat=%s, latency=%dms",
                  entry.listed(),
                  entry.listOrder(),
                  entry.showHat(),
                  entry.latency()
            );
            var stateSegment = PacketInfoSegment.of(Component.text("State"), MiniMessage.miniMessage().deserialize(state));

            String name = entry.profile() != null ? entry.profile().name() : "<none>";
            return PacketInfoList.of(Component.text(name), List.of(identitySegment, stateSegment));
        }).toList();

        rows.addAll(entryLists);

        return PacketInfoBundle.ofRows(
              packetType,
              Component.text(friendlyName),
              rows
        );
    }
}
