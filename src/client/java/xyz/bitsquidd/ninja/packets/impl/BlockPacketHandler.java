package xyz.bitsquidd.ninja.packets.impl;

import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.protocol.game.ServerboundPlayerActionPacket;
import xyz.bitsquidd.ninja.packets.PacketHandler;

public class BlockPacketHandler extends PacketHandler<ServerboundPlayerActionPacket> {

    public BlockPacketHandler() {
        super(ServerboundPlayerActionPacket.class, "PlayerActionPacket", "Handles player actions.");
    }


    @Override
    protected MutableComponent formatPacketInternal(boolean outgoing, ServerboundPlayerActionPacket packet) {
        StringBuilder builder = new StringBuilder();
        builder.append("Action\n  ");
        builder.append("  Action=").append(packet.getAction()).append("  \n");
        builder.append("  Pos=").append(packet.getPos()).append("  \n");

        MutableComponent component = Component.literal(builder.toString());

        if (outgoing) {
            component.withColor(0x99ff99);
        } else {
            component.withColor(0xff9999);
        }

        return component;
    }
}