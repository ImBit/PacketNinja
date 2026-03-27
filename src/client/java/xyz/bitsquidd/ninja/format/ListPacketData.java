package xyz.bitsquidd.ninja.format;

import com.google.common.collect.ImmutableList;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;

import xyz.bitsquidd.ninja.handler.PacketType;

import java.util.Collection;

// TODO Use MAX_DISPLAYED_ENTRIES here instead of in building packets.

/**
 * Displays a titled list of entries inside a packet info bundle.
 */
public final class ListPacketData implements PacketInfo {
    private final Component name;
    private final ImmutableList<PacketInfo> subInfo;

    ListPacketData(Component name, Collection<PacketInfo> subInfo) {
        this.name = name;
        this.subInfo = ImmutableList.copyOf(subInfo);
    }

    @Override
    public Component format(PacketType type) {
        TextComponent.Builder lineBuilder = Component.text();

        lineBuilder.append(Component.empty()
              .append(name.color(type.primaryColor))
              .append(Component.text(": "))
        );

        subInfo.forEach(info -> lineBuilder
              .appendNewline()
              .append(Component.text("  ↪ "))
              .append(info.format(type)));

        return lineBuilder.build();
    }
}
