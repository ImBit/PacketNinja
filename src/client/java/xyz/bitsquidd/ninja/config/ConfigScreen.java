package xyz.bitsquidd.ninja.config;

import me.shedaniel.clothconfig2.api.ConfigBuilder;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;

public class ConfigScreen {
    public static Screen create(Screen parent) {
        var builder = ConfigBuilder.create()
              .setParentScreen(parent)
              .setTitle(Component.literal("PacketNinja Settings"));

        var general = builder.getOrCreateCategory(Component.literal("General"));
        var entryBuilder = builder.entryBuilder();

        general.addEntry(entryBuilder
              .startIntSlider(Component.literal("Packet Delay (ms)"),
                    Config.packetDelayMs, 0, 2000)
              .setDefaultValue(500)
              .setTextGetter(value -> {
                  int step = 50;
                  int snapped = (value / step) * step;
                  return Component.literal(snapped + " ms");
              })
              .setTooltip(Component.literal(
                    "The minimum delay in milliseconds between two packets required " +
                          "to actually log a packet, otherwise it'll be replaced with \"...\""
              ))
              .setSaveConsumer(newValue -> {
                  int step = 50; // example
                  Config.packetDelayMs = (newValue / step) * step;
                  Config.save();
              })
              .build()
        );

        return builder.build();
    }
}
