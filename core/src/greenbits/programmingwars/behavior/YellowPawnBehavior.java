package greenbits.programmingwars.behavior;

import greenbits.programmingwars.board.Board;
import greenbits.programmingwars.board.BoardPosition;

public class YellowPawnBehavior implements MovementBehavior {

    @Override
    public MovementOffset getMove(Board board, BoardPosition currentPosition) {

        int x = -1 + (int) (Math.random() * 3);
        int y = -1 + (int) (Math.random() * 3);

        return new MovementOffset(x, y);    }
}
