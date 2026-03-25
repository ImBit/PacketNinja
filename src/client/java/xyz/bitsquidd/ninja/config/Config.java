package xyz.bitsquidd.ninja.config;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import net.fabricmc.loader.api.FabricLoader;

import xyz.bitsquidd.ninja.PacketInterceptorMod;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class Config {
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
    private static final Path FILE = FabricLoader.getInstance().getConfigDir().resolve("packetninja.json");

    public static int packetDelayMs = 500;

    public static void load() {
        if (Files.exists(FILE)) {
            try {
                String json = Files.readString(FILE);
                ConfigData data = GSON.fromJson(json, ConfigData.class);
                if (data != null) {
                    packetDelayMs = data.packetDelayMs;
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
            data.packetDelayMs = packetDelayMs;

            Files.writeString(FILE, GSON.toJson(data));
        } catch (IOException e) {
            PacketInterceptorMod.LOGGER.error("Failed to save config");
        }
    }

    private static class ConfigData {
        int packetDelayMs = 500;
    }
}
