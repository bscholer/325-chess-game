import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main (String [] args) {
        Board board = new Board();
        board.fillBoard();
        System.out.println(board);
        List<Move> moves = board.getPieceAt(new Position(0, 0)).getPotentialMoves(board);
        System.out.println(moves);
    }
}
