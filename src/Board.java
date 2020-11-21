import java.util.Arrays;

/**
 * The chess board. Contains a 2D array of pieces.
 */
public class Board {

    private Piece[][] pieces;

    /**
     * Default constructor, just instantiates the array.
     * Call fillBoard() to fill the board with pieces in their starting positions
     */
    public Board () {
        pieces = new Piece[8][8];
    }

    /**
     * Creates the pieces on the board
     */
    public void fillBoard() {
        // Create pawns
        for (int i = 0; i < 8; i++) {
            pieces[0][i] = new Pawn(new Position( 0, i), Piece.WHITE);
            pieces[7][i] = new Pawn(new Position( 7, i), Piece.BLACK);
        }
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

    @Override
    public String toString() {
        String ret = "";
        ret += "Board:\n";
        for (int x = 0; x < 8; x++) {
            ret += "-----------------\n";
            for (int y = 0; y < 8; y++) {
                ret += "|";
                if (pieces[x][y] instanceof Pawn) {
                    ret += "P";
                }
                else {
                    ret += " ";
                }
            }
            ret += "|\n";
        }
        return ret;
    }
}
