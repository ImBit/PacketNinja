package xyz.bitsquidd.ninja.handler.impl.clientbound;

import net.kyori.adventure.text.Component;
import net.minecraft.network.protocol.game.ClientboundPlayerInfoRemovePacket;
import org.jetbrains.annotations.NotNull;

import xyz.bitsquidd.ninja.format.PacketInfo;
import xyz.bitsquidd.ninja.format.PacketInfoBundle;
import xyz.bitsquidd.ninja.handler.PacketHandler;
import xyz.bitsquidd.ninja.handler.PacketType;

import java.util.ArrayList;
import java.util.List;

public class PlayerInfoRemoveHandler extends PacketHandler<@NotNull ClientboundPlayerInfoRemovePacket> {
    public PlayerInfoRemoveHandler() {
        super(
              ClientboundPlayerInfoRemovePacket.class,
              "PlayerInfoRemove",
              "Handles player removal updates",
              PacketType.CLIENTBOUND
        );
    }

    @Override
    protected @NotNull PacketInfoBundle getPacketInfoInternal(@NotNull ClientboundPlayerInfoRemovePacket packet) {
        List<PacketInfo> rows = new ArrayList<>();

        var removed = packet.profileIds().stream()
              .map(uuid -> PacketInfo.value(Component.text(uuid.toString())))
              .toList();
        rows.add(PacketInfo.list(Component.text("Removed UUIDs"), removed));

        return PacketInfoBundle.of(
              packetType,
              Component.text(friendlyName),
              rows
        );
    }
}
