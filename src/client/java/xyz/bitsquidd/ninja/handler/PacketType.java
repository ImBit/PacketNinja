package xyz.bitsquidd.ninja.handler;

import net.kyori.adventure.text.format.TextColor;
import org.jspecify.annotations.NullMarked;

@NullMarked
public enum PacketType {
    CLIENTBOUND("↓", TextColor.color(0x00ff88), TextColor.color(0xafffd9)),
    SERVERBOUND("↑", TextColor.color(0xff3366), TextColor.color(0xffb0c4)),
    ;

    public final String icon;
    public final TextColor primaryColor;
    public final TextColor secondaryColor;

    PacketType(String icon, TextColor primaryColor, TextColor secondaryColor) {
        this.icon = icon;
        this.primaryColor = primaryColor;
        this.secondaryColor = secondaryColor;
    }

}
