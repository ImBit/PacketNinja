package xyz.bitsquidd.ninja.keybind;

import com.mojang.blaze3d.platform.InputConstants;
import net.fabricmc.fabric.api.client.keymapping.v1.KeyMappingHelper;
import net.minecraft.client.KeyMapping;
import net.minecraft.resources.Identifier;

import xyz.bitsquidd.ninja.ui.NinjaDialogManager;

public final class NinjaKeybind {
    private static KeyMapping OPEN_NINJA;

    private NinjaKeybind() {}

    public static void register() {
        OPEN_NINJA = KeyMappingHelper.registerKeyMapping(new KeyMapping(
            "key.packet-ninja.open",
            InputConstants.Type.KEYSYM,
            InputConstants.KEY_GRAVE,
            KeyMapping.Category.register(Identifier.fromNamespaceAndPath("packet-ninja", "key.categories.packet-ninja"))
        ));
    }

    public static void tick() {
        if (OPEN_NINJA == null) return;
        while (OPEN_NINJA.consumeClick()) {
            NinjaDialogManager.INSTANCE.toggle();
        }
    }
}
