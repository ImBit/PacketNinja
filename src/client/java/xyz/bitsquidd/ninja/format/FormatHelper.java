package xyz.bitsquidd.ninja.format;

import net.minecraft.core.BlockPos;
import net.minecraft.network.protocol.game.ClientboundUpdateAttributesPacket;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.Vec3;
import org.jspecify.annotations.NullMarked;
import org.jspecify.annotations.Nullable;

import java.util.Collection;
import java.util.List;

/**
 * Helper class for formatting special data.
 */
@NullMarked
public final class FormatHelper {
    private FormatHelper() {}

    public static String formatPosition(final double x, final double y, final double z) {
        return String.format("[X;%.2f Y;%.2f Z;%.2f]", x, y, z);
    }

    public static String formatPosition(final BlockPos pos) {
        return formatPosition(pos.getX(), pos.getY(), pos.getZ());
    }

    public static String formatPosition(final Vec3 pos) {
        return formatPosition(pos.x, pos.y, pos.z);
    }


    public static String formatRotation(final float pitch, final float yaw) {
        return String.format("[Pitch;%.1f Yaw;%.1f]", pitch, yaw);
    }

    public static String formatList(Collection<?> entries, int maxEntries) {
        StringBuilder sb = new StringBuilder();
        sb.append("[");

        List<String> entriesList = entries.stream().map(Object::toString).toList();
        int size = entriesList.size();

        boolean tooMany = size > maxEntries;
        int displayed = Math.min(size, maxEntries);

        for (int i = 0; i < displayed; i++) {
            if (i > 0) sb.append(",");
            sb.append(entriesList.get(i));
        }

        if (tooMany) {
            sb.append("...+").append(size - maxEntries).append(" more]");
        } else {
            sb.append("]");
        }

        return sb.toString();
    }

    public static String formatItemStack(@Nullable ItemStack itemStack) {
        if (itemStack == null || itemStack.isEmpty()) return "Empty";
        String itemName = itemStack.getItem().toString();
        int count = itemStack.getCount();
        return String.format("%s x%d", itemName, count);
    }

    public static String formatAttribute(ClientboundUpdateAttributesPacket.AttributeSnapshot snapshot) {
        String attributeName = snapshot.attribute().value().getDescriptionId();
        StringBuilder modifiers = new StringBuilder();

        snapshot.modifiers().forEach(modifier -> {
            if (!modifiers.isEmpty()) modifiers.append(", ");
            modifiers.append(String.format("%s %.2f", modifier.id(), modifier.amount()));
        });

        return String.format("%s=[%s]", attributeName, modifiers);
    }

}
