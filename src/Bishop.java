public class Bishop extends PieceWrapper {

    public Bishop(Position position, int color) {
        super(position, color);
    }

    @Override
    public String toString() {
        return "Bishop at " + position;
    }
}
