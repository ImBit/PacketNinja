package xyz.bitsquidd.packets.impl;

import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.protocol.game.ServerboundInteractPacket;
import xyz.bitsquidd.packets.PacketHandler;

public class InteractPacketHandler extends PacketHandler<ServerboundInteractPacket> {

    public InteractPacketHandler() {
        super(ServerboundInteractPacket.class, "InteractPacket", "Handles player interactions with things.");
    }

    @Override
    protected MutableComponent formatPacketInternal(boolean outgoing, ServerboundInteractPacket packet) {
        StringBuilder builder = new StringBuilder();
        builder.append("Interact\n  ");
        builder.append("Action=").append(packet.isUsingSecondaryAction()).append("  ");

        MutableComponent component = Component.literal(builder.toString());

        if (outgoing) {
            component.withColor(0x99ff99);
        } else {
            component.withColor(0xff9999);
        }

        return component;

    }
}