import java.util.ArrayList;
import java.util.List;

public class Pawn extends PieceWrapper {

    public Pawn(Position position, int color) {
        super(position, color);
    }

    @Override
    public boolean isMoveValid(Board board, Move move) {
        // Make sure there isn't already a piece at the future position
        if (board.getPieceAt(move.getFuturePosition()) != null) return false;

        int curX, curY, futureX, futureY;
        curX = move.getPiece().getPosition().getXPosAsInt();
        curY = move.getPiece().getPosition().getyPos();
        futureX = move.getFuturePosition().getXPosAsInt();
        futureY = move.getFuturePosition().getyPos();

        // Check for two forward
        if (Math.abs(futureY - curY) == 2 && futureX == curX) {
            return true;
        }
        // Check for one forward
        if (Math.abs(futureY - curY) == 1 && futureX == curX) {
            return true;
        }
        // Check for one forward and one to either side
        if (Math.abs(futureY - curY) == 1 && Math.abs(futureX - curX) == 1) {
            return true;
        }

        // If it isn't either of those, return false.
        return false;
    }

    @Override
    public List<Move> getPotentialMoves(Board board) {
        List<Move> moves = new ArrayList<>();
        for (int x = 0; x < 8; x++) {
            for (int y = 0; y < 8; y++) {
                Move move = new Move(new Position(x, y), this);
                if (isMoveValid(board, move)) {
                    moves.add(move);
                }
            }
        }
        return moves;
    }

    @Override
    public String toString() {
        return "Pawn";
    }
}
