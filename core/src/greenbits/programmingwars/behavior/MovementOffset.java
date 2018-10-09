package greenbits.programmingwars.behavior;

import java.util.Locale;

/**
 * The amount of units to move in X and Y axis respectively.
 */
public final class MovementOffset {

    private final int xOffset;
    private final int yOffset;

    /**
     * @param xOffset Amount of units to move in the X axis. Can be in the range {@code [-1, 1]}.
     *                If the given value is outside the range, it is changed to the closest valid value.
     *                E.g. -100 becomes -1, 2 becomes 1.
     * @param yOffset Amount of units to move in the Y axis. Can be in the range {@code [-1, 1]}.
     *                If the given value is outside the range, it is changed to the closest valid value.
     *                E.g. -100 becomes -1, 2 becomes 1.
     */
    MovementOffset(int xOffset, int yOffset) {

        this.xOffset = Math.max(Math.min(xOffset, 1), -1);
        this.yOffset = Math.max(Math.min(yOffset, 1), -1);

        validateOffset(this.xOffset);
        validateOffset(this.yOffset);
    }

    private static void validateOffset(int offset) {

        if (offset < -1 || offset > 1) {
            throw new IllegalArgumentException("Offset can [-1, 1] but was " + offset);
        }
    }

    public int getXOffset() {

        return xOffset;
    }

    public int getYOffset() {

        return yOffset;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        MovementOffset offset = (MovementOffset) o;

        if (xOffset != offset.xOffset) return false;
        return yOffset == offset.yOffset;
    }

    @Override
    public int hashCode() {
        int result = xOffset;
        result = 31 * result + yOffset;
        return result;
    }

    @Override
    public String toString() {

        return String.format(Locale.getDefault(), "(%d, %d)", xOffset, yOffset);
    }
}
