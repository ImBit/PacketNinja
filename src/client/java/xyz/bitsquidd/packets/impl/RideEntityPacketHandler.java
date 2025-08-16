package xyz.bitsquidd.packets.impl;

import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.protocol.game.ClientboundSetPassengersPacket;
import xyz.bitsquidd.packets.PacketHandler;

public class RideEntityPacketHandler extends PacketHandler<ClientboundSetPassengersPacket> {

    public RideEntityPacketHandler() {
        super(ClientboundSetPassengersPacket.class, "RideEntity", "Handles entity riding/dismounting");
    }

    @Override
    protected MutableComponent formatPacketInternal(boolean outgoing, ClientboundSetPassengersPacket packet) {
        StringBuilder builder = new StringBuilder();
        builder.append("RideEntity\n  ");
        builder.append("VehicleId=").append(packet.getVehicle()).append("  ");
        builder.append("PassengerCount=").append(packet.getPassengers().length).append("  ");

        if (packet.getPassengers().length > 0) {
            builder.append("Passengers=[");
            for (int i = 0; i < packet.getPassengers().length; i++) {
                builder.append(packet.getPassengers()[i]);
                if (i < packet.getPassengers().length - 1) {
                    builder.append(",");
                }
            }
            builder.append("]");
        }

        MutableComponent component = Component.literal(builder.toString());

        if (outgoing) {
            component.withColor(0x99ff99);
        } else {
            component.withColor(0xff9999);
        }

        return component;
    }
}