package greenbits.programmingwars.board;

import greenbits.programmingwars.behavior.MovementOffset;

public final class BoardPosition {

    private final int x;
    private final int y;

    public BoardPosition(int x, int y) {

        this.x = x;
        this.y = y;
    }

    public int getX() {

        return x;
    }

    public int getY() {

        return y;
    }

    @Override
    public boolean equals(Object o) {

        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        BoardPosition that = (BoardPosition) o;

        if (getX() != that.getX()) return false;
        return getY() == that.getY();
    }

    @Override
    public int hashCode() {

        int result = getX();
        result = 31 * result + getY();
        return result;
    }

    public BoardPosition plus(MovementOffset offset) {

        return new BoardPosition(x + offset.getXOffset(), y + offset.getYOffset());
    }
}
