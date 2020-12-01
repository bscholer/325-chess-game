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
     * This method checks if a move is valid, given a Move.
     * This method MUST be overwritten by each piece with that piece's given movement rules.
     * @param move The Move to check
     * @param board The board, used to check if a piece is already there
     * @return True or false dependent on if the move is valid.
     */
    public boolean isMoveValid(Board board, Move move);

    /**
     * Returns an (Array)List of the potential moves for the piece.
     * These should then be displayed to the user in the GUI, and then they can select which move they'd like to make.
     * After that, the selected Move generated by this function can be passed to Board.movePiece().
     * The easiest (but not the most efficient way to implement this is probably to loop through all possible
     * positions on the Board and check if they're valid.
     * This will prevent us from having to rewrite the piece movement logic in two methods.
     * @param board the current board
     * @return The (Array)List of valid moves.
     */
    public List<Move> getPotentialMoves(Board board);

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
