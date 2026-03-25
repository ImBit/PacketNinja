package xyz.bitsquidd.ninja;

import net.fabricmc.api.ModInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

// Mester: is this really necessary, considering the mod is client-only and will only ever call the client entry point
public class PacketInterceptor implements ModInitializer {
    public static final String MOD_ID = "packet-ninja";

    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    @Override
    public void onInitialize() {}
}