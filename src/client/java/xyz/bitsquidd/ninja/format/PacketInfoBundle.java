package xyz.bitsquidd.ninja.format;

import net.kyori.adventure.platform.modcommon.MinecraftClientAudiences;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.format.TextDecoration;
import org.jspecify.annotations.NullMarked;

import xyz.bitsquidd.ninja.handler.PacketType;

import java.util.List;

/**
 * A bundle of information about a packet sent to a client. This structure is used for pretty formatting.
 */
@NullMarked
public final class PacketInfoBundle {
    private final PacketType type;

    private final Component name;
    private final List<PacketInfo> info;

    private PacketInfoBundle(PacketType type, Component name, List<PacketInfo> info) {
        this.type = type;
        this.name = name;
        this.info = info;
    }

    public static PacketInfoBundle of(PacketType type, Component name, List<? extends PacketInfo> info) {
        return new PacketInfoBundle(type, name, List.copyOf(info));
    }

    public PacketType getType() {
        return type;
    }

    public net.minecraft.network.chat.Component formatNative() {
        return MinecraftClientAudiences.of().asNative(format());
    }

    public Component format() {
        TextComponent.Builder infoBuilder = Component.text().color(type.secondaryColor);

        infoBuilder.append(Component.text(type.icon + " "));
        infoBuilder.append(PacketInfo.list(name.decorate(TextDecoration.BOLD), info).format(type));
        infoBuilder.appendNewline();

        return infoBuilder.build();
    }

}
