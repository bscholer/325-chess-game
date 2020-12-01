public class Rook extends PieceWrapper {

    public Rook(Position position, int color) {
        super(position, color);
    }

    @Override
    public String toString() {
        return "Rook at " + position;
    }
}
