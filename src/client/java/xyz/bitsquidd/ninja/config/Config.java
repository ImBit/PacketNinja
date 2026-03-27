package xyz.bitsquidd.ninja.config;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import net.fabricmc.loader.api.FabricLoader;

import xyz.bitsquidd.ninja.PacketInterceptorMod;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.Duration;

public class Config {
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
    private static final Path FILE = FabricLoader.getInstance().getConfigDir().resolve("packetninja.json");

    public static Duration packetDelay = Duration.ofMillis(500);

    public static void load() {
        if (Files.exists(FILE)) {
            try {
                String json = Files.readString(FILE);
                ConfigData data = GSON.fromJson(json, ConfigData.class);
                if (data != null) {
                    packetDelay = Duration.ofMillis(data.packetDelayMs);
                }
            } catch (Exception e) {
                PacketInterceptorMod.LOGGER.error("Failed to load config, using defaults");
            }
        } else {
            save(); // generate file on first run
        }
    }

    public static void save() {
        try {
            ConfigData data = new ConfigData();
            data.packetDelayMs = packetDelay.toMillis();

            Files.writeString(FILE, GSON.toJson(data));
        } catch (IOException e) {
            PacketInterceptorMod.LOGGER.error("Failed to save config");
        }
    }

    private static class ConfigData {
        long packetDelayMs = 500;
    }
}
