public class Queen extends PieceWrapper {

    public Queen(Position position, int color) {
        super(position, color);
    }

    @Override
    public String toString() {
        return "Queen at " + position;
    }
}
