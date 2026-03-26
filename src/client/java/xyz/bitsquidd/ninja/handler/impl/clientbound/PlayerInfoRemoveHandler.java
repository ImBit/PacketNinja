package xyz.bitsquidd.ninja.handler.impl.clientbound;

import net.kyori.adventure.text.Component;
import net.minecraft.network.protocol.game.ClientboundPlayerInfoRemovePacket;
import org.jetbrains.annotations.NotNull;

import xyz.bitsquidd.ninja.format.*;
import xyz.bitsquidd.ninja.handler.PacketHandler;
import xyz.bitsquidd.ninja.handler.PacketType;

import java.util.ArrayList;

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
        var removed = packet.profileIds().stream()
              .map(uuid -> PacketInfoValue.of(Component.text(uuid.toString())))
              .toList();

        var rows = new ArrayList<PacketInfoRow>();
        rows.add(PacketInfoList.of(Component.text("Removed UUIDs"), removed));

        return PacketInfoBundle.of(
              packetType,
              Component.text(friendlyName),
              rows
        );
    }
}
