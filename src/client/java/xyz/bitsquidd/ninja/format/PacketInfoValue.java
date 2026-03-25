package xyz.bitsquidd.ninja.format;


import net.kyori.adventure.text.Component;
import org.jspecify.annotations.NullMarked;

import xyz.bitsquidd.ninja.handler.PacketType;

import java.util.List;

/**
 * A packet info row that contains a single value.
 */
@NullMarked
public final class PacketInfoValue implements PacketInfoRow, PacketInfoListElement {
    private final Component value;

    private PacketInfoValue(Component value) {
        this.value = value;
    }

    public static PacketInfoValue of(Component value) {
        return new PacketInfoValue(value);
    }

    @Override
    public Component formatLine(PacketType type, String prefix) {
        return Component.empty()
              .color(type.secondaryColor)
              .append(Component.text(prefix))
              .append(value);
    }

    @Override
    public List<Component> format(PacketType type) {
        return List.of(formatLine(type, "    ↪ "));
    }
}
