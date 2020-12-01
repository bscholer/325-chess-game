import javax.swing.*;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main (String [] args) {
//        GUI gui = new GUI();

        JFrame frame = new JFrame();
        frame.setSize(1000,1000);
        frame.setVisible(true);
        Board board = new Board();
        board.fillBoard();
        board.drawBoard();
        frame.add(board);
        frame.pack();
        frame.repaint();

//        Board board = new Board();
//        board.fillBoard();
//        System.out.println(board);
////        board.updateBoard();
////        List<Move> moves = board.getPieceAt(new Position(0, 0)).getPotentialMoves(board);
//        System.out.println(board);
    }
}
