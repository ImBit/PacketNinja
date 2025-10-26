package xyz.bitsquidd.ninja.command;

import net.kyori.adventure.text.format.TextColor;
import org.jspecify.annotations.NullMarked;

@NullMarked
public enum ResponseType {
    SUCCESS("✔", TextColor.color(0x00ff88)),
    ERROR("❌", TextColor.color(0xff3366)),
    INFO("", TextColor.color(0x0088ff)),
    ;

    public final String icon;
    public final TextColor color;

    ResponseType(String icon, TextColor color) {
        this.icon = icon;
        this.color = color;
    }

}
