package greenbits.programmingwars.board;

import greenbits.programmingwars.board.objects.BoardObject;

public class Board {

    private final int boardSize;
    private final BoardObject[] board;

    public Board(int boardSize) {

        this.boardSize = boardSize;
        board = new BoardObject[boardSize * boardSize];
    }

    public BoardObject getElement(int x, int y) {

        validateDimension("x", x);
        validateDimension("y", y);

        int index = y * boardSize + x;
        return board[index];
    }

    public void setElement(int x, int y, BoardObject element) {

        validateDimension("x", x);
        validateDimension("y", y);

        int index = y * boardSize + x;
        board[index] = element;
    }

    private void validateDimension(String str, int dimension) {

        if (dimension < 0 || dimension >= boardSize) {
            throw new RuntimeException(String.format("%s must be in the range [0, %d) but was %d.", str, boardSize, dimension));
        }
    }

    public int getBoardSize() {

        return boardSize;
    }
}

// TODO convert from index to (row, col):
/*
row = index / columns
column = index % column
 */
