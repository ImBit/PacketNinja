package xyz.bitsquidd.ninja.handler.impl.clientbound;

import net.kyori.adventure.text.Component;
import net.minecraft.network.protocol.game.ClientboundCommandSuggestionsPacket;
import org.jetbrains.annotations.NotNull;

import xyz.bitsquidd.ninja.format.*;
import xyz.bitsquidd.ninja.handler.PacketHandler;
import xyz.bitsquidd.ninja.handler.PacketType;

import java.util.ArrayList;
import java.util.List;

public class CommandSuggestionsResponseHandler extends PacketHandler<@NotNull ClientboundCommandSuggestionsPacket> {
    public CommandSuggestionsResponseHandler() {
        super(
              ClientboundCommandSuggestionsPacket.class,
              "CommandSuggestionsResponse",
              "Handles command suggestion responses",
              PacketType.CLIENTBOUND
        );
    }


    @Override
    protected @NotNull PacketInfoBundle getPacketInfoInternal(@NotNull ClientboundCommandSuggestionsPacket packet) {
        var id = packet.id();
        var suggestions = packet.suggestions();
        var suggestionCount = suggestions.size();

        List<PacketInfoRow> rows = new ArrayList<>();
        rows.add(PacketInfoSegment.of(Component.text("Transaction ID"), Component.text(id)));
        rows.add(PacketInfoSegment.of(Component.text("Suggestion Count"), Component.text(suggestionCount)));

        // Display suggestions as a structured list
        if (!suggestions.isEmpty()) {
            var suggestionSegments = suggestions.stream()
                  .limit(MAX_DISPLAYED_ENTRIES)
                  .map(entry -> PacketInfoValue.of(
                        Component.text(entry.text())
                  ))
                  .toList();
            rows.add(PacketInfoList.of(Component.text("Suggestions"), new ArrayList<>(suggestionSegments)));

            int hiddenSuggestions = suggestions.size() - suggestionSegments.size();
            if (hiddenSuggestions > 0) {
                rows.add(PacketInfoSegment.of(Component.text("SuggestionsHidden"), Component.text(hiddenSuggestions + " more")));
            }
        }

        return PacketInfoBundle.of(
              packetType,
              Component.text(friendlyName),
              rows
        );
    }
}
