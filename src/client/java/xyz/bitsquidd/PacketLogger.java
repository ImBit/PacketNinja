package xyz.bitsquidd;

import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.protocol.Packet;
import xyz.bitsquidd.packets.PacketHandler;

public class PacketLogger {

    private long lastPacketTime = 0;

    public void addPacket(boolean outgoing, Packet<?> packet) {
        PacketHandler<?> handler = PacketRegistry.getHandlerForPacket(packet);

        if (handler == null) return;

        long currentTime = System.currentTimeMillis();
        if (lastPacketTime != 0 && currentTime - lastPacketTime > 500) {
            sendChatMessage(Component.literal("...").withStyle(ChatFormatting.GRAY));
        }
        lastPacketTime = currentTime;

        MutableComponent component = handler.formatPacket(outgoing, packet);
        sendChatMessage(component);
    }

    private void sendChatMessage(MutableComponent component) {
        Minecraft minecraft = Minecraft.getInstance();
        if (minecraft.player != null) {
            minecraft.player.displayClientMessage(component, false);
        }
    }
}