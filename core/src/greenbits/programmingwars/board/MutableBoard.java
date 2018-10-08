package greenbits.programmingwars.board;

import java.lang.reflect.Array;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.TreeSet;

import greenbits.programmingwars.board.objects.BoardObject;
import greenbits.programmingwars.board.objects.Pawn;
import greenbits.programmingwars.board.objects.Trail;

public class MutableBoard implements Board {

    private final int boardSize;
    private final Set<BoardObject>[] board;

    @SuppressWarnings("unchecked")
    public MutableBoard(int boardSize) {

        this.boardSize = boardSize;

        board = (Set<BoardObject>[]) Array.newInstance(Set.class, boardSize * boardSize);
        for (int i = 0; i < board.length; ++i) {
            board[i] = new TreeSet<>();
        }
    }

    public Set<BoardObject> getElementsAt(int x, int y) {

        if (!isInsideBoard(x, y)) {
            return Collections.emptySet();
        }

        int index = y * boardSize + x;
        return board[index];
    }

    /**
     * @return The pawn at the given position or {@code null}. Remember that at each cell at most one pawn can exist.
     */
    public Pawn getPawnAt(int x, int y) {

        Set<BoardObject> boardObjects = getElementsAt(x, y);

        for (BoardObject boardObject : boardObjects) {
            if (boardObject instanceof Pawn) {
                return (Pawn) boardObject;
            }
        }

        return null;
    }

    public void moveTo(int x, int y, BoardObject element) {

        validateDimension("x", x);
        validateDimension("y", y);

        removeFromBoard(element);

        int index = y * boardSize + x;
        board[index].add(element);

        if (element instanceof Pawn) {

            removeTrails(board[index]);
            board[index].add(((Pawn) element).getTrail());
        }
    }

    private void removeFromBoard(BoardObject element) {

        for (Set<BoardObject> boardObjects : board) {
            boardObjects.remove(element);
        }
    }

    private void removeTrails(Set<BoardObject> boardObjects) {

        Set<BoardObject> toRemove = new HashSet<>();

        for (BoardObject boardObject : boardObjects) {
            if (boardObject instanceof Trail) {
                toRemove.add(boardObject);
            }
        }

        boardObjects.removeAll(toRemove);
    }

    private void validateDimension(String str, int dimension) {

        if (dimension < 0 || dimension >= boardSize) {
            throw new RuntimeException(String.format("%s must be in the range [0, %d) but was %d.", str, boardSize, dimension));
        }
    }

    @Override
    public int getBoardSize() {

        return boardSize;
    }

    public Set<Pawn> getPawns() {

        Set<Pawn> pawns = new HashSet<>();

        for (Set<BoardObject> boardObjects : board) {

            for (BoardObject boardObject : boardObjects) {

                if (boardObject instanceof Pawn) {
                    pawns.add(((Pawn) boardObject));
                }
            }
        }

        return pawns;
    }

    BoardPosition getPositionOf(Pawn pawn) {

        for (int row = 0; row < boardSize; ++row) {

            for (int column = 0; column < boardSize; ++column) {

                int index = row * boardSize + column;
                if (board[index].contains(pawn)) {
                    return new BoardPosition(column, row);
                }
            }
        }

        throw new RuntimeException("Could not find pawn " + pawn);
    }

    @Override
    public boolean isInsideBoard(int x, int y) {

        return x >= 0 && x < boardSize && y >= 0 && y < boardSize;
    }
}