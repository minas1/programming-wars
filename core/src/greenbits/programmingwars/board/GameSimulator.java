package greenbits.programmingwars.board;

import java.util.ArrayList;
import java.util.List;

import greenbits.programmingwars.behavior.MovementBehavior;
import greenbits.programmingwars.behavior.MovementOffset;
import greenbits.programmingwars.board.objects.Pawn;

public class GameSimulator {

    /**
     * Time for each pawn to move, in seconds.
     */
    private static final float TIME_TO_MOVE_PAWN = 0.1f;

    private static final int TOTAL_ROUNDS = 100;

    private final List<Pawn> players = new ArrayList<>();
    private final List<MovementBehavior> movementBehaviors = new ArrayList<>();
    private final MutableBoard board;

    private int nextPlayerToMove = 0;
    private float remainingTimeToMove = TIME_TO_MOVE_PAWN;

    private int remainingRounds = TOTAL_ROUNDS;

    public GameSimulator(MutableBoard board) {

        this.board = board;
    }

    public void addPlayer(Pawn pawn, MovementBehavior movementBehavior) {

        players.add(pawn);
        movementBehaviors.add(movementBehavior);
    }

    public void update(float dt) {

        if (!isGameInProgress()) {
            return;
        }

        remainingTimeToMove -= dt;
        if (remainingTimeToMove <= 0f) {

            remainingTimeToMove = TIME_TO_MOVE_PAWN;

            Pawn pawn = players.get(nextPlayerToMove);
            BoardPosition currentPosition = board.getPositionOf(pawn);
            MovementOffset movementOffset = movementBehaviors.get(nextPlayerToMove).getMove(board, currentPosition);

            applyMovement(pawn, currentPosition, movementOffset);
            nextPlayerToMove = (nextPlayerToMove + 1) % players.size();

            if (nextPlayerToMove == 0) {
                --remainingRounds;
            }
        }
    }

    private void applyMovement(Pawn pawn, BoardPosition currentPosition, MovementOffset movementOffset) {

        int newX = currentPosition.getX() + movementOffset.getXOffset();
        int newY = currentPosition.getY() + movementOffset.getYOffset();

        if (newX >= 0 && newX < board.getBoardSize() && newY >= 0 && newY < board.getBoardSize()) {

            Pawn alreadyExistingPawn = board.getPawnAt(newX, newY);
            if (alreadyExistingPawn == null) {
                board.moveTo(newX, newY, pawn);
            }
        }
    }

    private boolean isGameInProgress() {

        return remainingRounds > 0;
    }

    public int getRemainingRounds() {

        return remainingRounds;
    }
}
