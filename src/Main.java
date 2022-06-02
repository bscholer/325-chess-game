import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.Scanner;

/**
 * This class just starts the game, and handles everything outside of gameplay.
 */
public class Main {

    // The board
    static Board board;
    // Internationalization stuff
    static Locale englishLocale = new Locale("en", "US");
    static Locale spanishLocale = new Locale("es", "ES");
    static String[] languages = {"English", "Spanish"};
    static ResourceBundle resourceBundle = ResourceBundle.getBundle("resources", englishLocale);
    // The JFrame to stick the stuff in.
    static JFrame frame;

    public static void main (String [] args) {
        frame = new JFrame();
        frame.setSize(1000,1000);
        frame.setVisible(true);
        frame.setTitle(resourceBundle.getString("title"));

        // Buttons/language selector
        JButton resetGame = new JButton(resourceBundle.getString("reset"));
        resetGame.setBounds(0, 0, 150, 30);
        JComboBox languageSelector = new JComboBox(languages);
        languageSelector.setSelectedIndex(0);
        languageSelector.setBounds(155, 0, 150, 30);
        frame.add(resetGame);
        frame.add(languageSelector);

        // Make the Board and start the game
        board = new Board();
        board.setBounds(0, 60, 1000,940);
        board.fillBoard();
        board.drawBoard();

        // Add the Board to the frame.
        frame.add(board);
        frame.pack();
        frame.repaint();

        // An ActionListener for the reset button.
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
    }
}
