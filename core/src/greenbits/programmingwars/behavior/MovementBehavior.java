package greenbits.programmingwars.behavior;

import greenbits.programmingwars.board.Board;
import greenbits.programmingwars.board.BoardPosition;

public interface MovementBehavior {

    MovementOffset getMove(Board board, BoardPosition currentPosition);
}
