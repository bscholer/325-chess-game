public class King extends PieceWrapper {

    public King(Position position, int color) {
        super(position, color);
    }

    @Override
    public String toString() {
        return "King at " + position;
    }
}
