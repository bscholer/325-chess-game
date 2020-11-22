import javax.swing.*;

/**
 * Assigned to: Isabelle
 *
 * This takes care of all the GUI stuff, and is the main running thread of the program.
 * The program will start by creating a new GUI object w/ the constructor.
 */
public class GUI extends JFrame {

    private Board board;
    private ChessAPI chessAPI;
    // Whatever other variables you need

    /**
     * Default constructor, please add stuff to it as needed.
     * The bulk of the program will likely be in here.
     * Feel free to chunk it out into separate methods of readability!
     */
    public GUI() {
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