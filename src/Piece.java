import java.util.List;

/**
 * The Piece interface
 * Has a variable for the (current) position, and an int for the color of the piece.
 * Use Piece.WHITE or Piece.BLACK to set the color.
 */
public interface Piece {

    Position position = new Position();
    int color = 0;
    public static final int SILVER = 0;
    public static final int GOLD = 1;

    /**
     * Sets the color of the piece. Use Piece.SILVER or Piece.GOLD.
     * @param color Piece.SILVER or Piece.WHITE
     */
    public void setColor(int color);

    /**
     * Gets the color of the piece.
     * @return
     */
    public int getColor();

    /**
     * Gets the piece's Position
     * @return piece's current Position
     */
    public Position getPosition();

    /**
     * Sets the Piece's Position
     * @param position the Position to set
     */
    public void setPosition(Position position);
}
