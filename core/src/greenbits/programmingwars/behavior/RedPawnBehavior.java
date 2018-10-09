package greenbits.programmingwars.behavior;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import greenbits.programmingwars.board.Board;
import greenbits.programmingwars.board.BoardPosition;

public class RedPawnBehavior implements MovementBehavior {

    @Override
    public MovementOffset getMove(Board board, BoardPosition currentPosition) {

        List<MovementOffset> possible = new ArrayList<>();

        possible.add(new MovementOffset(-1, 1));
        possible.add(new MovementOffset(-1, 0));
        possible.add(new MovementOffset(-1, 1));
        possible.add(new MovementOffset(0, -1));
        possible.add(new MovementOffset(0, 0));
        possible.add(new MovementOffset(0, 1));
        possible.add(new MovementOffset(1, -1));
        possible.add(new MovementOffset(1, 0));
        possible.add(new MovementOffset(1, 1));

        Collections.shuffle(possible);

        while (!possible.isEmpty()) {

            MovementOffset offset = possible.remove(0);

            BoardPosition newPosition = currentPosition.plus(offset);
            if (currentPosition.equals(newPosition)) {
                continue;
            }

            if (!board.isInsideBoard(newPosition)) {
                continue;
            }

            if (board.getTrailAt(newPosition) == getOwnTrail(board, currentPosition)) {
                continue;
            }

            System.out.println("Returning " + offset);
            return offset;
        }

        return new MovementOffset(0, 0);
    }
}
