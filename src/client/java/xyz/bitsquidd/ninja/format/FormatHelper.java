package xyz.bitsquidd.ninja.format;

import net.minecraft.core.BlockPos;
import org.jspecify.annotations.NullMarked;

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
}
