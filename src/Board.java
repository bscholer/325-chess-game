/**
 * The chess board. Contains a 2D array of pieces.
 */
public class Board {

    private Piece[][] pieces;

    public Board () {

    }

    /**
     * Moves a piece
     * @param move The move
     */
    public void movePiece(Move move) {
        move.getPiece().isMoveValid(this, move);
    }

    /**
     * Returns the 2D array of pieces. I can't think of when this would need to be used, but it's here anyway.
     * @return The 2D array of pieces
     */
    public Piece[][] getPieces() {
        return pieces;
    }

    /**
     * Gets the piece at a given position
     * @param position The position to get the piece from
     * @return The piece at position
     */
    public Piece getPieceAt(Position position) {
        return pieces[position.getXPosAsInt()][position.getyPos()];
    }
}
