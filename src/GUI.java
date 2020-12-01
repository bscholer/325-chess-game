import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Locale;
import java.util.ResourceBundle;

/**
 * Assigned to: Isabelle
 *
 * This takes care of all the GUI stuff, and is the main running thread of the program.
 * The program will start by creating a new GUI object w/ the constructor.
 */
public class GUI extends JFrame {

    private Board board;
    private ChessAPI chessAPI;
    private boolean isCreatingGame;
    Locale englishLocale = new Locale("en", "US");
    Locale frenchLocale = new Locale("es", "ES");
    ResourceBundle resourceBundle = ResourceBundle.getBundle("resources", englishLocale);

    /**
     * Default constructor, please add stuff to it as needed.
     * The bulk of the program will likely be in here.
     * Feel free to chunk it out into separate methods of readability!
     */
    public GUI() {
        setSize(300, 400);
        setLayout(null);
        setVisible(true);
        setTitle(resourceBundle.getString("title"));

        JPanel startPanel = new JPanel();
        startPanel.setBounds(0, 0, 250, 350);
        JButton createGame = new JButton(resourceBundle.getString("startNewGame"));
        JButton joinGame = new JButton(resourceBundle.getString("joinGame"));
        JTextField gameID = new JTextField(15);
        JLabel gameIDLabel = new JLabel(resourceBundle.getString("gameID"));
        JButton begin = new JButton(resourceBundle.getString("begin"));

        startPanel.add(createGame);
        startPanel.add(joinGame);
        add(startPanel);
        repaint();

        ActionListener startingListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("here");
                // Remove the buttons and add the game ID label and TextField
                startPanel.remove(createGame);
                startPanel.remove(joinGame);
                startPanel.add(gameIDLabel);
                startPanel.add(gameID);
                startPanel.add(begin);
                startPanel.repaint();
                if (e.getSource() == createGame) {
                    isCreatingGame = true;
                    chessAPI = new ChessAPI(false);
                    gameID.setText(chessAPI.getGameID());
                }
                else if (e.getSource() == joinGame) {
                    isCreatingGame = false;
                }
            }
        };
        createGame.addActionListener(startingListener);
        joinGame.addActionListener(startingListener);

        ActionListener gameBeginListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (isCreatingGame) {
                    // Launch the game
                }
                else {
                    chessAPI = new ChessAPI(gameID.getText(), false);
                    // Launch the game
                }
            }
        };


        // Ask the user if it should be multi-player or single-player
        // Create the chessAPI and board
        // Go through the flow of the gameplay
    }

    /**
     * Re-render the GUI's board. This will be called every time a move is made
     */
    public void updateDisplay() {

    }

    /**
     * This will somehow (up to you) show the user where they can move a specific piece.
     * This will need to be woven in with a click listener of some sort.
     * This will be run after a user clicks on a piece, and will call Piece.getPotentialMoves()
     */
    public void displayPossibleMoves() {
        // TODO Figure out what moves the Piece can make (Piece.getPotentialMoves())
        // TODO Display those moves in a creative way on the board
    }
}