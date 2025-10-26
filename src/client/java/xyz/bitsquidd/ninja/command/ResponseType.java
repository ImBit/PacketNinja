package xyz.bitsquidd.ninja.command;

import net.kyori.adventure.text.format.TextColor;
import org.jspecify.annotations.NullMarked;

@NullMarked
public enum ResponseType {
    SUCCESS("\uF602", TextColor.color(0x00ff88)),
    ERROR("\uF603", TextColor.color(0xff3366)),
    INFO("\uF604", TextColor.color(0x33adff)),
    ;

    public final String icon;
    public final TextColor color;

    ResponseType(String icon, TextColor color) {
        this.icon = icon;
        this.color = color;
    }

}
