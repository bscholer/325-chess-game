import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.Scanner;

public class Main {

    static Board board;
    static Locale englishLocale = new Locale("en", "US");
    static Locale spanishLocale = new Locale("es", "ES");
    static String[] languages = {"English", "Spanish"};
    static ResourceBundle resourceBundle = ResourceBundle.getBundle("resources", englishLocale);
    static JFrame frame;

    public static void main (String [] args) {
//        GUI gui = new GUI();
        System.out.println(TimeServer.getTime());

//
//        Position p = new Position("a1");
//        p.mirrorPosition();
//        System.out.println(p);

        frame = new JFrame();
        frame.setSize(1000,1000);
        frame.setVisible(true);

        JToolBar toolBar = new JToolBar();
        JButton resetGame = new JButton(resourceBundle.getString("reset"));
        resetGame.setBounds(0, 0, 150, 30);
        JComboBox languageSelector = new JComboBox(languages);
        languageSelector.setSelectedIndex(0);
        languageSelector.setBounds(155, 0, 150, 30);
        frame.add(resetGame);
        frame.add(languageSelector);
        frame.setTitle(resourceBundle.getString("title"));


        board = new Board();
        board.setBounds(0, 60, 1000,940);
        board.fillBoard();
        board.drawBoard();
        frame.add(board);
        frame.pack();
        frame.repaint();

        ActionListener resetListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("resetting");
                frame.remove(board);
                board = null;
                board = new Board();
                board.setBounds(0, 60, 1000,940);
                board.fillBoard();
                board.drawBoard();
                frame.add(board);
                frame.pack();
                frame.repaint();
            }
        };
        resetGame.addActionListener(resetListener);

        // Change Locale Listener
        ActionListener changeLocaleListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                switch (languageSelector.getSelectedIndex()) {
                    case 0:
                        // English
                        resourceBundle = ResourceBundle.getBundle("resources", englishLocale);
                        break;
                    case 1:
                        // Spanish
                        resourceBundle = ResourceBundle.getBundle("resources", spanishLocale);
                        break;
                }

                // Reset the text
                frame.setTitle(resourceBundle.getString("title"));
                resetGame.setText(resourceBundle.getString("reset"));
                frame.repaint();
            }
        };
        languageSelector.addActionListener(changeLocaleListener);

//        Board board = new Board();
//        board.fillBoard();
//        System.out.println(board);
////        board.updateBoard();
//        List<Move> moves = board.getPieceAt(new Position(0, 0)).getPotentialMoves(board);
//        System.out.println(board);
    }
}
