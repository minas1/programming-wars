package greenbits.programmingwars.board.objects;

public class Pawn implements BoardObject {

    private final String id;
    private final Trail trail;

    public Pawn(String id, Trail trail) {

        this.id = id;
        this.trail = trail;
    }

    public String getId() {

        return id;
    }

    public Trail getTrail() {

        return trail;
    }
}