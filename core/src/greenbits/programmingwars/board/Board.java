package greenbits.programmingwars.board;

import greenbits.programmingwars.board.objects.BoardObject;

public class Board {

    private final int boardSize;

    private final BoardObject[] board;

    public Board(int boardSize) {

        this.boardSize = boardSize;
        board = new BoardObject[boardSize * boardSize];
    }

    public BoardObject getElement(int row, int column) {

        validateDimension("row", row);
        validateDimension("column", column);

        int index = row * boardSize + column;
        return board[index];
    }

    public void setElement(int row, int column, BoardObject element) {

        validateDimension("row", row);
        validateDimension("column", column);

        int index = row * boardSize + column;
        board[index] = element;
    }

    private void validateDimension(String str, int dimension) {

        if (dimension < 0 || dimension >= boardSize) {
            throw new RuntimeException(String.format("%s must be in the range [0, %d) but was %d.", str, boardSize, dimension));
        }
    }
}

// TODO convert from index to (row, col):
/*
row = index / columns
column = index % column
 */
