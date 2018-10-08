package greenbits.programmingwars.behavior;

public final class MovementOffset {

    private final int xOffset;
    private final int yOffset;

    public MovementOffset(int xOffset, int yOffset) {

        this.xOffset = xOffset;
        this.yOffset = yOffset;

        validateOffset(xOffset);
        validateOffset(yOffset);
    }

    private static void validateOffset(int offset) {

        if (offset < -1 || offset > 1) {
            throw new IllegalArgumentException("Offset can [-1, 1] but was" + offset);
        }
    }

    public int getXOffset() {

        return xOffset;
    }

    public int getYOffset() {

        return yOffset;
    }
}
