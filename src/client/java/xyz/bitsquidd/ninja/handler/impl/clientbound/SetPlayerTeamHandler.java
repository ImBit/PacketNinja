package xyz.bitsquidd.ninja.handler.impl.clientbound;

import net.kyori.adventure.text.Component;
import net.minecraft.network.protocol.game.ClientboundSetPlayerTeamPacket;
import org.jetbrains.annotations.NotNull;

import xyz.bitsquidd.ninja.format.FormatHelper;
import xyz.bitsquidd.ninja.format.PacketInfoBundle;
import xyz.bitsquidd.ninja.format.PacketInfoSegment;
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
        List<PacketInfoSegment> segments = new ArrayList<>();

        segments.add(PacketInfoSegment.of(Component.text("Team"), Component.text(packet.getName())));

        ClientboundSetPlayerTeamPacket.Action teamAction = packet.getTeamAction();
        ClientboundSetPlayerTeamPacket.Action playerAction = packet.getPlayerAction();

        segments.add(PacketInfoSegment.of(Component.text("TeamAction"), Component.text(teamAction != null ? teamAction.name() : "null")));
        segments.add(PacketInfoSegment.of(Component.text("PlayerAction"), Component.text(playerAction != null ? playerAction.name() : "null")));

        if (!packet.getPlayers().isEmpty()) {
            segments.add(PacketInfoSegment.of(Component.text("Players"), Component.text(FormatHelper.formatList(packet.getPlayers(), MAX_DISPLAYED_ENTRIES))));
        }

        packet.getParameters().ifPresent(params -> {
            segments.add(PacketInfoSegment.of(
                  Component.text("DisplayName"),
                  Component.text(params.getDisplayName().getString())
            ));

            segments.add(PacketInfoSegment.of(
                  Component.text("Color"),
                  Component.text(params.getColor().getName())
            ));

            segments.add(PacketInfoSegment.of(
                  Component.text("NTVisibility"),
                  Component.text(params.getNametagVisibility().name)
            ));

            segments.add(PacketInfoSegment.of(
                  Component.text("CollisionRule"),
                  Component.text(params.getCollisionRule().name)
            ));

            if (!params.getPlayerPrefix().getString().isEmpty()) {
                segments.add(PacketInfoSegment.of(
                      Component.text("Prefix"),
                      Component.text(params.getPlayerPrefix().getString())
                ));
            }

            if (!params.getPlayerSuffix().getString().isEmpty()) {
                segments.add(PacketInfoSegment.of(
                      Component.text("Suffix"),
                      Component.text(params.getPlayerSuffix().getString())
                ));
            }

            segments.add(PacketInfoSegment.of(Component.text("Options"), Component.text(String.format("0x%02X", params.getOptions()))));
        });

        return PacketInfoBundle.of(
              packetType,
              Component.text(friendlyName),
              segments
        );
    }

}