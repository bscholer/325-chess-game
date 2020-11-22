import java.util.List;

/**
 * This is a wrapper class for the Piece interface. It only defines the basic methods, like setColor()
 * It should be inherited by all of the Piece subclasses
 */
public class PieceWrapper implements Piece {

    Position position;
    int color;

    /*
     * OVERRIDE THIS!!!!
     */
    @Override
    public boolean isMoveValid(Board board, Move move) {
        return false;
    }

    /*
     * OVERRIDE THIS!!!!
     */
    @Override
    public List<Move> getPotentialMoves(Board board) {
        return null;
    }

    /**
     * DON'T OVERRIDE THIS!!!!
     * Sets the piece's color.
     * @param color Piece.WHITE or Piece.BLACK
     */
    @Override
    public void setColor(int color) {
        if (color == Piece.WHITE || color == Piece.BLACK) {
            this.color = color;
        }
    }

    /**
     * DON'T OVERRIDE THIS!!!!
     * Get's the Piece's Position
     * @return The Piece's Position
     */
    @Override
    public Position getPosition() {
        return position;
    }

    /**
     * DON'T OVERRIDE THIS!!!!
     * Set's the Position of a Piece
     * @param position The position to set
     */
    @Override
    public void setPosition(Position position) {
        // By doing all the validation in Position, we can be sure that this is valid.
        this.position = position;
    }
}
