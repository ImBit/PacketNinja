package xyz.bitsquidd.ninja.format;

import net.kyori.adventure.text.Component;
import org.jspecify.annotations.NullMarked;

import xyz.bitsquidd.ninja.handler.PacketType;

import java.util.ArrayList;
import java.util.List;

/**
 * Displays a titled list of entries inside a packet info bundle.
 */
@NullMarked
public final class PacketInfoList implements PacketInfoRow {
    private final Component title;
    private final List<? extends PacketInfoListElement> entries;

    private PacketInfoList(Component title, List<? extends PacketInfoListElement> entries) {
        this.title = title;
        this.entries = entries;
    }

    public static PacketInfoList of(Component title, List<? extends PacketInfoListElement> entries) {
        return new PacketInfoList(title, entries);
    }

    @Override
    public List<Component> format(PacketType type) {
        List<Component> lines = new ArrayList<>();
        lines.add(
              Component.empty()
                    .color(type.secondaryColor)
                    .append(Component.text("    ↪ "))
                    .append(title)
                    .append(Component.text(":"))
        );

        for (int i = 0; i < entries.size(); i++) {
            var entry = entries.get(i);
            lines.add(entry.formatLine(type, "      " + (i + 1) + ". "));
        }

        return lines;
    }
}
