package xyz.bitsquidd.ninja.handler.impl.clientbound;

import com.google.gson.JsonObject;
import com.mojang.serialization.JsonOps;
import net.kyori.adventure.text.Component;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.protocol.game.ClientboundSetCursorItemPacket;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;

import xyz.bitsquidd.ninja.PacketInterceptorMod;
import xyz.bitsquidd.ninja.format.PacketInfoBundle;
import xyz.bitsquidd.ninja.format.PacketInfoSegment;
import xyz.bitsquidd.ninja.handler.PacketHandler;
import xyz.bitsquidd.ninja.handler.PacketType;

import java.util.List;

public class SetCursorItemHandler extends PacketHandler<@NotNull ClientboundSetCursorItemPacket> {
    public SetCursorItemHandler() {
        super(
              ClientboundSetCursorItemPacket.class,
              "SetCursorItem",
              "Handles cursor item updates",
              PacketType.CLIENTBOUND
        );
    }

    @Override
    protected @NotNull PacketInfoBundle getPacketInfoInternal(@NotNull ClientboundSetCursorItemPacket packet) {
        var stack = packet.contents();
        var itemId = BuiltInRegistries.ITEM.getKey(stack.getItem());
        var itemCount = stack.getCount();

        // serialize nbt as snbt
        var encodeResult = ItemStack.CODEC.encodeStart(JsonOps.INSTANCE, stack);
        var json = encodeResult.resultOrPartial(PacketInterceptorMod.LOGGER::error).orElse(new JsonObject()); // return empty on error

        return PacketInfoBundle.of(
              packetType,
              Component.text(friendlyName),
              List.of(
                    PacketInfoSegment.of(Component.text("ID"), Component.text(itemId.toString())),
                    PacketInfoSegment.of(Component.text("Count"), Component.text(itemCount)),
                    PacketInfoSegment.of(Component.text("NBT"), Component.text(json.toString()))
              )
        );
    }
}
