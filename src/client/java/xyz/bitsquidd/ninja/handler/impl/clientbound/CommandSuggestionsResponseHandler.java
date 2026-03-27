package xyz.bitsquidd.ninja.handler.impl.clientbound;

import net.kyori.adventure.text.Component;
import net.minecraft.network.protocol.game.ClientboundCommandSuggestionsPacket;
import org.jetbrains.annotations.NotNull;

import xyz.bitsquidd.ninja.format.PacketInfo;
import xyz.bitsquidd.ninja.format.PacketInfoBundle;
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

        List<PacketInfo> rows = new ArrayList<>();
        rows.add(PacketInfo.data(Component.text("Transaction ID"), Component.text(id)));
        rows.add(PacketInfo.data(Component.text("Suggestion Count"), Component.text(suggestionCount)));

        if (!suggestions.isEmpty()) {
            var suggestionSegments = suggestions.stream()
                  .limit(MAX_DISPLAYED_ENTRIES)
                  .map(entry -> PacketInfo.value(Component.text(entry.text())))
                  .toList();
            rows.add(PacketInfo.list(Component.text("Suggestions"), suggestionSegments));

            int hiddenSuggestions = suggestions.size() - suggestionSegments.size();
            if (hiddenSuggestions > 0) {
                rows.add(PacketInfo.data(Component.text("SuggestionsHidden"), Component.text(hiddenSuggestions + " more")));
            }
        }

        return PacketInfoBundle.of(
              packetType,
              Component.text(friendlyName),
              rows
        );
    }
}
