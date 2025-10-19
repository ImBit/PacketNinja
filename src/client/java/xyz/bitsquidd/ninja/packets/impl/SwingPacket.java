package xyz.bitsquidd.ninja.packets.impl;

import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.protocol.game.ServerboundSwingPacket;
import xyz.bitsquidd.ninja.packets.PacketHandler;

public class SwingPacket extends PacketHandler<ServerboundSwingPacket> {

    public SwingPacket() {
        super(ServerboundSwingPacket.class, "ServerboundSwingPacket", "Handles player swings their hand.");
    }


    @Override
    protected MutableComponent formatPacketInternal(boolean outgoing, ServerboundSwingPacket packet) {
        StringBuilder builder = new StringBuilder();
        builder.append("SWING SWING\n  ");

        MutableComponent component = Component.literal(builder.toString());

        if (outgoing) {
            component.withColor(0x99ff99);
        } else {
            component.withColor(0xff9999);
        }

        return component;
    }
}