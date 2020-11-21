import java.util.List;

public class Pawn implements Piece {

    private int color;

    @Override
    public boolean isMoveValid(Board board, Move move) {
        return true;
    }

    @Override
    public List<Move> getPotentialMoves() {
        return null;
    }

    @Override
    public void setColor(int color) {
        if (color == Piece.WHITE || color == Piece.BLACK) {
            this.color = color;
        }
    }
}
