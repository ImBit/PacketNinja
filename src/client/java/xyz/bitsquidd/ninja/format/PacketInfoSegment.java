package xyz.bitsquidd.ninja.format;

import net.kyori.adventure.text.Component;
import org.jspecify.annotations.NullMarked;

@NullMarked
public final class PacketInfoSegment {
    private final Component name;
    private final Component value;

    private PacketInfoSegment(Component name, Component value) {
        this.name = name;
        this.value = value;
    }

    public static PacketInfoSegment of(Component name, Component value) {
        return new PacketInfoSegment(name, value);
    }

    public Component getName() {
        return name;
    }

    public Component getValue() {
        return value;
    }
}
