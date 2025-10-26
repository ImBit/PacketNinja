package xyz.bitsquidd.ninja.handler;

import net.kyori.adventure.text.format.TextColor;
import org.jspecify.annotations.NullMarked;

/**
 * All possible packet types for the client to interact with.
 */
@NullMarked
public enum PacketType {
    CLIENTBOUND("\uF600", TextColor.color(0x00ff88), TextColor.color(0xafffd9)),
    SERVERBOUND("\uF601", TextColor.color(0xff3366), TextColor.color(0xffb0c4)),
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
