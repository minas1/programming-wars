package greenbits.programmingwars.board.objects;

public interface BoardObject extends Comparable<BoardObject> {

    @Override
    default int compareTo(BoardObject boardObject) {

        if (this == boardObject) {
            return 0;
        }

        if (this instanceof Trail) {
            return -1;
        }

        return 1;
    }
}
