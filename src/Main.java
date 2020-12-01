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
        List<Move> moves = board.getPieceAt(new Position(0, 1)).getPotentialMoves(board);
//        Position position = new Position(1, 0);
//        System.out.println(position.getXPosAsInt() + ", " + position.getyPos());
//        System.out.println(position);
//        System.out.println(board.getPieceAt(position));
//        System.out.println(board);
        System.out.println(moves);

//        Board board = new Board();
//        board.fillBoard();
//        System.out.println(board);
////        board.updateBoard();
//        List<Move> moves = board.getPieceAt(new Position(0, 0)).getPotentialMoves(board);
//        System.out.println(board);
    }
}
