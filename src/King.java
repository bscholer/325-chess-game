/**
 * The King class. Really just overrides toString().
 */
public class King extends PieceWrapper {

    /**
     * Constructor
     *
     * @param position A Position object for where the piece is at on the board
     * @param color    The color of the piece, either Piece.SILVER or Piece.GOLD.
     */
    public King(Position position, int color) {
        super(position, color);
    }

    @Override
    public String toString() {
        return "King at " + position;
    }
}
