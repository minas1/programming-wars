package greenbits.programmingwars.behavior;

import greenbits.programmingwars.board.Board;
import greenbits.programmingwars.board.BoardPosition;

public class RedPawnBehavior implements MovementBehavior {

    @Override
    public MovementOffset getMove(Board board, BoardPosition currentPosition) {

        // TODO implement a better algorithm using the methods of the board

        int x = -1 + (int) (Math.random() * 3); // [-1, 1]
        int y = -1 + (int) (Math.random() * 3); // [-1, 1]

        return new MovementOffset(x, y);
    }
}
