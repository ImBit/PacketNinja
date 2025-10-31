package xyz.bitsquidd.ninja.format;

import net.minecraft.core.BlockPos;
import org.jspecify.annotations.NullMarked;

import java.util.Collection;
import java.util.List;

/**
 * Helper class for formatting special data.
 */
@NullMarked
public final class FormatHelper {

    public static String formatPosition(final List<Double> position) {
        if (position.size() != 3) return "Invalid Position";
        return String.format("[X;%.2f Y;%.2f Z;%.2f]", position.get(0), position.get(1), position.get(2));
    }

    public static String formatPosition(final BlockPos pos) {
        return String.format("[X;%d Y;%d Z;%d]", pos.getX(), pos.getY(), pos.getZ());
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
}
