package greenbits.programmingwars.behavior;

import greenbits.programmingwars.board.Board;
import greenbits.programmingwars.board.BoardPosition;
import greenbits.programmingwars.board.objects.Trail;

public interface MovementBehavior {

    MovementOffset getMove(Board board, BoardPosition currentPosition);

    default Trail getOwnTrail(Board board, BoardPosition currentPosition) {

        return board.getTrailAt(currentPosition);
    }
}
