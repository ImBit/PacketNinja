package xyz.bitsquidd.ninja.handler.impl.clientbound;

import net.kyori.adventure.text.Component;
import net.minecraft.network.protocol.game.ClientboundSetPlayerTeamPacket;
import org.jetbrains.annotations.NotNull;

import xyz.bitsquidd.ninja.format.PacketInfo;
import xyz.bitsquidd.ninja.format.PacketInfoBundle;
import xyz.bitsquidd.ninja.handler.PacketHandler;
import xyz.bitsquidd.ninja.handler.PacketType;

import java.util.ArrayList;
import java.util.List;

public class SetPlayerTeamHandler extends PacketHandler<@NotNull ClientboundSetPlayerTeamPacket> {

    public SetPlayerTeamHandler() {
        super(
              ClientboundSetPlayerTeamPacket.class,
              "SetPlayerTeam",
              "Handles all team functions: create, addTo, removeFrom, update teams.",
              PacketType.CLIENTBOUND
        );
    }

    @Override
    protected @NotNull PacketInfoBundle getPacketInfoInternal(ClientboundSetPlayerTeamPacket packet) {
        List<PacketInfo> rows = new ArrayList<>();

        rows.add(PacketInfo.data(Component.text("Team"), Component.text(packet.getName())));

        ClientboundSetPlayerTeamPacket.Action teamAction = packet.getTeamAction();
        ClientboundSetPlayerTeamPacket.Action playerAction = packet.getPlayerAction();

        rows.add(PacketInfo.data(Component.text("TeamAction"), Component.text(teamAction != null ? teamAction.name() : "null")));
        rows.add(PacketInfo.data(Component.text("PlayerAction"), Component.text(playerAction != null ? playerAction.name() : "null")));

        if (!packet.getPlayers().isEmpty()) {
            List<String> playerList = packet.getPlayers().stream()
                  .limit(MAX_DISPLAYED_ENTRIES)
                  .toList();
            List<PacketInfo> playerSegments = playerList.stream()
                  .map(name -> PacketInfo.data(Component.text("Player"), Component.text(name)))
                  .toList();
            rows.add(PacketInfo.list(Component.text("Players"), new ArrayList<>(playerSegments)));

            int hiddenPlayers = packet.getPlayers().size() - playerList.size();
            if (hiddenPlayers > 0) {
                rows.add(PacketInfo.data(Component.text("PlayersHidden"), Component.text(hiddenPlayers + " more")));
            }
        }

        packet.getParameters().ifPresent(params -> {
            rows.add(PacketInfo.data(
                  Component.text("DisplayName"),
                  Component.text(params.getDisplayName().getString())
            ));

            rows.add(PacketInfo.data(
                  Component.text("Color"),
                  Component.text(params.getColor().getName())
            ));

            rows.add(PacketInfo.data(
                  Component.text("NTVisibility"),
                  Component.text(params.getNametagVisibility().name)
            ));

            rows.add(PacketInfo.data(
                  Component.text("CollisionRule"),
                  Component.text(params.getCollisionRule().name)
            ));

            if (!params.getPlayerPrefix().getString().isEmpty()) {
                rows.add(PacketInfo.data(
                      Component.text("Prefix"),
                      Component.text(params.getPlayerPrefix().getString())
                ));
            }

            if (!params.getPlayerSuffix().getString().isEmpty()) {
                rows.add(PacketInfo.data(
                      Component.text("Suffix"),
                      Component.text(params.getPlayerSuffix().getString())
                ));
            }

            rows.add(PacketInfo.data(Component.text("Options"), Component.text(String.format("0x%02X", params.getOptions()))));
        });

        return PacketInfoBundle.of(
              packetType,
              Component.text(friendlyName),
              rows
        );
    }

}