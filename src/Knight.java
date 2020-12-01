public class Knight extends PieceWrapper {

    public Knight(Position position, int color) {
        super(position, color);
    }

    @Override
    public String toString() {
        return "Knight at " + position;
    }
}
