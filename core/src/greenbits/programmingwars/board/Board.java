package greenbits.programmingwars.board;

import greenbits.programmingwars.board.objects.Pawn;
import greenbits.programmingwars.board.objects.Trail;

public interface Board {

    /**
     * @param x X coordinate (column)
     * @param y Y coordinate (row)
     * @return The pawn at coordinate {@code (x, y)} or {@code null} if no pawn exists. Also returns {@code null}
     * when the given coordinates are outside the board.
     * @see #isInsideBoard(int, int)
     */
    Pawn getPawnAt(int x, int y);

    /**
     * @see #getPawnAt(int, int)
     */
    default Pawn getPawnAt(BoardPosition boardPosition) {

        return getPawnAt(boardPosition.getX(), boardPosition.getY());
    }

    /**
     * @param x X coordinate (column). Valid values are [0, {@link #getBoardSize()})
     * @param y Y coordinate (row). Valid values are [0, {@link #getBoardSize()})
     * @return whether the given coordinates are inside the board.
     * @see #getBoardSize()
     */
    boolean isInsideBoard(int x, int y);

    /**
     * @see #isInsideBoard(int, int)
     */
    default boolean isInsideBoard(BoardPosition boardPosition) {

        return isInsideBoard(boardPosition.getX(), boardPosition.getY());
    }

    /**
     * @return The number of rows / columns of the board.
     */
    int getBoardSize();

    /**
     *
     * @param x X coordinate (column)
     * @param y Y coordinate (row)
     * @return The trail at coordinate {@code (x, y)} or {@code null} if no pawn exists. Also returns {@code null}
     * when the given coordinates are outside the board.<br>
     * Note that if a {@link Pawn} is at position {@code (x, y)} then its {@link Trail} is also positioned there.
     */
    Trail getTrailAt(int x, int y);

    /**
     * @see #getTrailAt(int, int)
     */
    default Trail getTrailAt(BoardPosition boardPosition) {

        return getTrailAt(boardPosition.getX(), boardPosition.getY());
    }
}
