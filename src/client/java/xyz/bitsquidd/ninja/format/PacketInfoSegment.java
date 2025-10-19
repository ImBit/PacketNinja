package xyz.bitsquidd.ninja.format;

import net.kyori.adventure.text.Component;
import org.jspecify.annotations.NullMarked;

@NullMarked
public class PacketInfoSegment {
    private final Component name;
    private final Component value;

    public PacketInfoSegment(Component name, Component value) {
        this.name = name;
        this.value = value;
    }

    public Component getName() {
        return name;
    }

    public Component getValue() {
        return value;
    }
}
