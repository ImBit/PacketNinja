package xyz.bitsquidd.ninja.handler.impl.clientbound;

import net.kyori.adventure.text.Component;
import net.minecraft.network.protocol.game.ClientboundUpdateAttributesPacket;
import org.jetbrains.annotations.NotNull;

import xyz.bitsquidd.ninja.format.FormatHelper;
import xyz.bitsquidd.ninja.format.PacketInfo;
import xyz.bitsquidd.ninja.format.PacketInfoBundle;
import xyz.bitsquidd.ninja.handler.PacketHandler;
import xyz.bitsquidd.ninja.handler.PacketType;

import java.util.List;


public class UpdateAttributesHandler extends PacketHandler<@NotNull ClientboundUpdateAttributesPacket> {

    public UpdateAttributesHandler() {
        super(
              ClientboundUpdateAttributesPacket.class,
              "UpdateAttributes",
              "Changes a LivingEntity's attributes",
              PacketType.CLIENTBOUND
        );
    }

    @Override
    protected @NotNull PacketInfoBundle getPacketInfoInternal(ClientboundUpdateAttributesPacket packet) {
        return PacketInfoBundle.of(
              packetType,
              Component.text(friendlyName),
              List.of(
                    PacketInfo.data(Component.text("EntityID"), Component.text(packet.getEntityId())),
                    PacketInfo.data(Component.text("Attributes"), Component.text(FormatHelper.formatList(packet.getValues().stream().map(FormatHelper::formatAttribute).toList(), 10)))
              )
        );
    }

}