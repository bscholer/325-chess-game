/**
 * This class is pretty boilerplate, and doesn't need to contain any logic.
 */
public class Move {

    private Position futurePosition;
    private Piece piece;

    /**
     * Default constructor to allow for some flexibility
     */
    public Move() {

    }

    /**
     * Main constructor. Piece should already contain it's current position.
     * @param futurePosition The future position of the piece
     * @param piece The piece
     */
    public Move(Position futurePosition, Piece piece) {
        this.futurePosition = futurePosition;
        this.piece = piece;
    }

    // Getters and Setters, nothing special here

    public Position getFuturePosition() {
        return futurePosition;
    }

    public void setFuturePosition(Position futurePosition) {
        this.futurePosition = futurePosition;
    }

    public Piece getPiece() {
        return piece;
    }

    public void setPiece(Piece piece) {
        this.piece = piece;
    }
}
