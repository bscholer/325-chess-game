/**
 * This is a wrapper class for the Piece interface. It only defines the basic methods, like setColor()
 * It should be inherited by all of the Piece subclasses
 * Decided to make this because Interfaces are dumb, and this covers a whole bunch of reused code.
 */
public class PieceWrapper implements Piece {

    Position position;
    int color;

    public PieceWrapper(Position position, int color) {
        this.position = position;
        this.color = color;
    }

    /**
     * Sets the piece's color.
     *
     * @param color Piece.SILVER or Piece.GOLD
     */
    @Override
    public void setColor(int color) {
        if (color == Piece.SILVER || color == Piece.GOLD) {
            this.color = color;
        }
    }

    /**
     * Gets the Piece's color
     *
     * @return Piece.SILVER or Piece.GOLD
     */
    @Override
    public int getColor() {
        return color;
    }

    /**
     * Get's the Piece's Position
     *
     * @return The Piece's Position
     */
    @Override
    public Position getPosition() {
        return position;
    }

    /**
     * Set's the Position of a Piece
     *
     * @param position The position to set
     */
    @Override
    public void setPosition(Position position) {
        // By doing all the validation in Position, we can be sure that this is valid.
        this.position = position;
    }
}
