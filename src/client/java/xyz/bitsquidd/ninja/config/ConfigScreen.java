package xyz.bitsquidd.ninja.config;

import me.shedaniel.clothconfig2.api.ConfigBuilder;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;

import java.time.Duration;

public class ConfigScreen {
    public static Screen create(Screen parent) {
        var builder = ConfigBuilder.create()
              .setParentScreen(parent)
              .setTitle(Component.literal("PacketNinja Settings"));

        var general = builder.getOrCreateCategory(Component.literal("General"));
        var entryBuilder = builder.entryBuilder();

        general.addEntry(entryBuilder
              .startLongSlider(
                    Component.literal("Packet Delay (ms)"),
                    Config.packetDelay.toMillis(),
                    0,
                    2000L
              )
              .setDefaultValue(500L)
              .setTextGetter(value -> {
                  long step = 50L;
                  long snapped = (value / step) * step;
                  return Component.literal(snapped + " ms");
              })
              .setTooltip(Component.literal(
                    "The minimum delay in milliseconds between two packets required " +
                          "to actually log a packet, otherwise it'll be replaced with \"...\""
              ))
              .setSaveConsumer(newValue -> {
                  long step = 50L;
                  Config.packetDelay = Duration.ofMillis((newValue / step) * step);
                  Config.save();
              })
              .build()
        );

        return builder.build();
    }
}
