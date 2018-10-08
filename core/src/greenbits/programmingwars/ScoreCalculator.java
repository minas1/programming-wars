package greenbits.programmingwars;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import greenbits.programmingwars.board.Board;
import greenbits.programmingwars.board.objects.BoardObject;
import greenbits.programmingwars.board.objects.Pawn;
import greenbits.programmingwars.board.objects.Trail;

public class ScoreCalculator {

    public static final class Score {

        private final Pawn pawn;
        private final int score;

        public Score(Pawn pawn, int score) {

            this.pawn = pawn;
            this.score = score;
        }

        public Pawn getPawn() {

            return pawn;
        }

        public int getScore() {

            return score;
        }
    }

    private static final Comparator<Score> COMPARATOR = (a, b) -> Integer.compare(b.score, a.score);

    /**
     * @return The score for each pawn in descending order.
     */
    public List<Score> calculate(Board board) {

        Set<Pawn> pawns = board.getPawns();
        Map<Pawn, Integer> scores = initScoreMap(board, pawns);

        for (int i = 0; i < board.getBoardSize(); ++i) {
            for (int j = 0; j < board.getBoardSize(); ++j) {
                for (BoardObject boardObject : board.getElement(i, j)) {

                    if (!(boardObject instanceof Trail)) {
                        continue;
                    }

                    Trail trail = (Trail) boardObject;
                    Pawn pawn = getOwnerOfTrail(pawns, trail);
                    scores.put(pawn, scores.get(pawn) + 1);
                }
            }
        }

        List<Score> scoreList = new ArrayList<>();
        for (Map.Entry<Pawn, Integer> score : scores.entrySet()) {
            scoreList.add(new Score(score.getKey(), score.getValue()));
        }
        Collections.sort(scoreList, COMPARATOR);

        return scoreList;
    }

    private Map<Pawn, Integer> initScoreMap(Board board, Set<Pawn> pawns) {

        Map<Pawn, Integer> scores = new HashMap<>();

        for (Pawn pawn : pawns) {
            scores.put(pawn, 0);
        }

        return scores;
    }

    private Pawn getOwnerOfTrail(Set<Pawn> pawns, Trail trail) {

        for (Pawn pawn : pawns) {

            if (pawn.getTrail() == trail) {
                return pawn;
            }
        }

        throw new RuntimeException("Pawn not found");
    }
}
