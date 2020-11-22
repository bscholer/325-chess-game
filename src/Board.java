import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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
     * Create a board with a 2d array of Pieces
     * @param pieces the 2d array of Pieces
     */
    public Board(Piece[][] pieces) {
        this.pieces = pieces;
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
        // Make sure the move is valid first
        if (move.getPiece().isMoveValid(this, move)) {
            // Change the Piece object's position
            move.getPiece().setPosition(move.getFuturePosition());
        }
    }

    private List<Piece> getListOfPieces() {
        // Get all the pieces and put them in a temporary (Array)List
        List<Piece> piecesList = new ArrayList<>();
        for (int x = 0; x < 8; x++) {
            for (int y = 0; y < 8; y++) {
                if (pieces[x][y] != null) {
                    piecesList.add(pieces[x][y]);
                }
            }
        }
        return piecesList;
    }

    /**
     * This will look through the current board, find all the pieces, and reset their positions to
     * whatever they have as their position variable.
     * Call this method AFTER changing a Piece object's position
     * @param piecesList The list of pieces generated by getListOfPieces()
     */
    public void updateBoard(List<Piece> piecesList) {
        // Nuke the (2d) pieces array
        pieces = new Piece[8][8];

        // Add the pieces back into the pieces 2d array
        for (Piece piece : piecesList) {
            pieces[piece.getPosition().getXPosAsInt()][piece.getPosition().getyPos()] = piece;
        }
    }

    /**
     * Returns the 2D array of pieces. I can't think of when this would need to be used, but it's here anyway.
     * @return The 2D array of pieces
     */
    public Piece[][] getPieces() {
        return pieces;
    }

    /**
     * Adds a Piece to the Board and updates the Board
     * @param piece The Piece to add
     * @return Was the piece successfully added?
     */
    public boolean addPiece(Piece piece) {
        List<Piece> pieceList = getListOfPieces();
        boolean pieceAdded = pieceList.add(piece);
        updateBoard(pieceList);
        return pieceAdded;
    }

    /**
     * Removes a Piece, given the Piece object.
     * @param piece The Piece to remove from the Board
     * @return Was the piece successfully removed?
     */
    public boolean removePiece(Piece piece) {
        List<Piece> pieceList = getListOfPieces();
        boolean pieceRemoved = pieceList.remove(piece);
        updateBoard(pieceList);
        return pieceRemoved;
    }

    /**
     * Remove the piece at the given Position
     * @param position The position of the Piece that should be removed
     * @return Was the piece successfully removed?
     */
    public boolean removePiece(Position position) {
        // Get the piece at the position
        Piece piece = getPieceAt(position);

        // If there isn't a piece at that position, return false
        if (piece == null) return false;

        List<Piece> pieceList = getListOfPieces();
        boolean pieceRemoved = pieceList.remove(piece);
        updateBoard(pieceList);
        return pieceRemoved;
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
