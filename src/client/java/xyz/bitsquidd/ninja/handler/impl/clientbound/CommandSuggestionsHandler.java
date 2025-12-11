package xyz.bitsquidd.ninja.handler.impl.clientbound;

import com.mojang.brigadier.suggestion.Suggestion;
import net.kyori.adventure.text.Component;
import net.minecraft.network.protocol.game.ClientboundClearTitlesPacket;
import net.minecraft.network.protocol.game.ClientboundCommandSuggestionsPacket;
import org.jetbrains.annotations.NotNull;

import xyz.bitsquidd.ninja.format.FormatHelper;
import xyz.bitsquidd.ninja.format.PacketInfoBundle;
import xyz.bitsquidd.ninja.format.PacketInfoSegment;
import xyz.bitsquidd.ninja.handler.PacketHandler;
import xyz.bitsquidd.ninja.handler.PacketType;

import java.util.ArrayList;
import java.util.List;

public class CommandSuggestionsHandler extends PacketHandler<@NotNull ClientboundCommandSuggestionsPacket> {

    public CommandSuggestionsHandler() {
        super(
              ClientboundCommandSuggestionsPacket.class,
              "CommandSuggestions",
              "Handles ClientboundCommandSuggestionsPacket.",
              PacketType.CLIENTBOUND
        );
    }

    @Override
    protected @NotNull PacketInfoBundle getPacketInfoInternal(ClientboundCommandSuggestionsPacket packet) {
        List<Suggestion> suggestions = packet.toSuggestions().getList();
        String suggestionsList = FormatHelper.formatList(suggestions.stream().map(Suggestion::getText).toList(), MAX_DISPLAYED_ENTRIES);

        return PacketInfoBundle.of(
              packetType,
              Component.text(friendlyName),
              List.of(
                    PacketInfoSegment.of(Component.text("ResetTimes"), Component.text(suggestionsList))
              )
        );
    }

}
