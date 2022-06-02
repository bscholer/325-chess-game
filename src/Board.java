import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.util.List;

/**
 * The chess board. Contains a 2D array of Pieces.
 * This is also the main 'thread' of the program, and is a JPanel which is added to the JFrame
 */
public class Board extends JPanel {

    // The 2D array of Pieces.
    private Piece[][] pieces;
    // The list of buttons, which is closely linked to pieces[][]
    private JButton[][] boardButtons;
    // I know this is terrible, but it was the only way I could figure to be able to access the 'this' context from the ActionListener
    private Board board;
    // The ChessAPI
    private ChessAPI api;
    // The state of the game.
    private int state;
    // Possible game states. Used for consistency. Could have been replaced with an enum, but there's only two states.
    private final int READY_TO_MOVE = 0;
    private final int MOVES_SHOWN = 1;
    // The List of possible Moves the user can make. Only has values in it when state=MOVES_SHOWN.
    private List<Move> possibleMoves;
    // The users Piece color.
    private int myColor = Piece.GOLD;

    /**
     * Default constructor, just instantiates the array.
     * Call fillBoard() after this to fill the board with pieces in their starting positions,
     * then call drawBoard()
     * This takes heavy inspiration from https://stackoverflow.com/a/21096455
     */
    public Board() {
        super(new GridLayout(0, 9));
        board = this;
        setBorder(new LineBorder(Color.BLACK));

        api = new ChessAPI();

        state = READY_TO_MOVE;

        Insets buttonMargin = new Insets(0, 0, 0, 0);
        pieces = new Piece[8][8];
        boardButtons = new JButton[8][8];

        // The ActionListener for the buttons on the Board.
        ActionListener boardButtonListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Piece piece = null;
                Position position = null;
                // Figure out which button was pressed
                for (int x = 0; x < 8; x++) {
                    for (int y = 0; y < 8; y++) {
                        if (e.getSource() == boardButtons[x][y]) {
                            // Having to do y,x instead of x,y still confuses me, but this makes it work.
                            position = new Position(y, x);
                            piece = getPieceAt(position);
                        }
                    }
                }
                // We're ready to move (possible moves aren't displayed yet)
                if (state == READY_TO_MOVE) {
                    // Make sure there is a Piece where we clicked
                    if (piece != null) {
                        // Make sure the color of the Piece we clicked matches our color.
                        if (piece.getColor() == myColor) {
                            // Get list of possible moves from the API
                            possibleMoves = api.getPossibleMoves(piece);
                            // Make sure there are moves that can be made, otherwise get out of here.
                            if (possibleMoves.size() == 0) return;

                            // Highlight the possible move locations
                            for (Move move : possibleMoves) {
                                boardButtons[move.getFuturePosition().getyPos()][move.getFuturePosition().getXPosAsInt()].setBackground(Color.BLUE);
                            }
                            // The user can now select a Move to make.
                            state = MOVES_SHOWN;
                        }
                    }
                } else if (state == MOVES_SHOWN) {
                    // Check that the clicked box is a possible move
                    boolean isValidMove = false;
                    Move currentMove = null;
                    // Make sure that the button clicked is within the list of possible Moves.
                    for (Move move : possibleMoves) {
                        if (move.getFuturePosition().equals(position)) {
                            isValidMove = true;
                            currentMove = move;
                        }
                    }
                    // Make the move
                    if (isValidMove) {
                        // Move the player on the API's game first
                        api.movePlayer(currentMove);
                        // Move the piece on the local Board
                        movePiece(currentMove);
                        // Redraw
                        drawBoard();
                        // Recolor buttons (get rid of the blue ones)
                        recolorButtons();
                        // Have the AI Move
                        Move aiMove = api.moveAI(board);
                        // Move the AI's Piece on the local Board
                        movePiece(aiMove);
                        // Redraw
                        drawBoard();
                        // Reset the state to READY_TO_MOVE
                        state = READY_TO_MOVE;
                    }
                }
            }
        };

        // Create all the buttons
        for (int x = 0; x < 8; x++) {
            for (int y = 0; y < 8; y++) {
                JButton button = new JButton();
                button.addActionListener(boardButtonListener);
                button.setMargin(buttonMargin);
                // Just an empty icon
                ImageIcon icon = new ImageIcon(new BufferedImage(64, 64, BufferedImage.TYPE_INT_ARGB));
                button.setIcon(icon);

                // Color every other button black
                if ((x % 2 == 1 && y % 2 == 1) || (y % 2 == 0 && x % 2 == 0)) {
                    button.setBackground(Color.WHITE);
                } else {
                    button.setBackground(Color.BLACK);
                }
                boardButtons[x][y] = button;
            }
        }

        // Just adds a blank space to align the other labels
        add(new JLabel(""));

        // Create column labels
        for (int x = 0; x < 8; x++) {
            add(new JLabel("ABCDEFGH".substring(x, x + 1), SwingConstants.CENTER));
        }

        // Create row labels
        for (int x = 0; x < 8; x++) {
            for (int y = 0; y < 8; y++) {
                if (y == 0) {
                    add(new JLabel("" + (x + 1), SwingConstants.CENTER));
                }
                add(boardButtons[x][y]);
            }
        }
    }

    /**
     * Recolors the buttons after the possible moves are displayed.
     */
    private void recolorButtons() {
        for (int x = 0; x < 8; x++) {
            for (int y = 0; y < 8; y++) {
                JButton button = boardButtons[x][y];

                // Color every other button black
                if ((x % 2 == 1 && y % 2 == 1) || (y % 2 == 0 && x % 2 == 0)) {
                    button.setBackground(Color.WHITE);
                } else {
                    button.setBackground(Color.BLACK);
                }
            }
        }

    }

    /**
     * Creates the pieces on the board
     */
    public void fillBoard() {
        // Create Pawns
        for (int i = 0; i < 8; i++) {
            pieces[1][i] = new Pawn(new Position(i, 1), Piece.GOLD);
            pieces[6][i] = new Pawn(new Position(i, 6), Piece.SILVER);
        }

        // Create Rooks
        pieces[0][0] = new Rook(new Position(0, 0), Piece.GOLD);
        pieces[0][7] = new Rook(new Position(7, 0), Piece.GOLD);
        pieces[7][7] = new Rook(new Position(7, 7), Piece.SILVER);
        pieces[7][0] = new Rook(new Position(0, 7), Piece.SILVER);

        // Create Knights
        pieces[0][1] = new Knight(new Position(1, 0), Piece.GOLD);
        pieces[0][6] = new Knight(new Position(6, 0), Piece.GOLD);
        pieces[7][6] = new Knight(new Position(6, 7), Piece.SILVER);
        pieces[7][1] = new Knight(new Position(1, 7), Piece.SILVER);

        // Create Bishops
        pieces[0][2] = new Bishop(new Position(2, 0), Piece.GOLD);
        pieces[0][5] = new Bishop(new Position(5, 0), Piece.GOLD);
        pieces[7][5] = new Bishop(new Position(5, 7), Piece.SILVER);
        pieces[7][2] = new Bishop(new Position(2, 7), Piece.SILVER);

        // Create Queens
        pieces[0][3] = new Queen(new Position(3, 0), Piece.GOLD);
        pieces[7][3] = new Queen(new Position(3, 7), Piece.SILVER);

        // Create Kings
        pieces[0][4] = new King(new Position(4, 0), Piece.GOLD);
        pieces[7][4] = new King(new Position(4, 7), Piece.SILVER);
    }

    /**
     * Moves a piece
     *
     * @param move The move
     */
    public void movePiece(Move move) {
        // Log it!
        Logging.logEvent(move.toString() + "\n" + board.toString(), api.getGameID());
        // Check if there is already a Piece where we are trying to move
        if (getPieceAt(move.getFuturePosition()) != null) {
            // If there is, remove it.
            removePiece(move.getFuturePosition());
        }
        // Move the piece in pieces[][]
        Position originalPosition = new Position(move.getPiece().getPosition().toString());
        move.getPiece().setPosition(move.getFuturePosition());
        pieces[move.getFuturePosition().getyPos()][move.getFuturePosition().getXPosAsInt()] = move.getPiece();
        pieces[originalPosition.getyPos()][originalPosition.getXPosAsInt()] = null;
    }

    /**
     * Removes a Piece, given the Piece object.
     *
     * @param piece The Piece to remove from the Board
     */
    public void removePiece(Piece piece) {
        pieces[piece.getPosition().getyPos()][piece.getPosition().getXPosAsInt()] = null;
    }

    /**
     * Remove the piece at the given Position
     *
     * @param position The position of the Piece that should be removed
     */
    public void removePiece(Position position) {
        // Get the piece at the position
        Piece piece = getPieceAt(position);

        // If there isn't a piece at that position, return false
        if (piece == null) return;

        removePiece(piece);
    }

    /**
     * Draws the board. Should be called after updateBoard().
     */
    public void drawBoard() {
        for (int x = 0; x < 8; x++) {
            for (int y = 0; y < 8; y++) {
                JButton button = boardButtons[x][y];
                Piece piece = pieces[x][y];

                // Just figure out what type of Piece it is, and give the button an ImageIcon based on that and the color.
                if (piece instanceof Pawn) {
                    if (piece.getColor() == Piece.GOLD) button.setIcon(new ImageIcon(ChessSprites.GOLD_PAWN));
                    if (piece.getColor() == Piece.SILVER) button.setIcon(new ImageIcon(ChessSprites.SILVER_PAWN));
                } else if (piece instanceof Rook) {
                    if (piece.getColor() == Piece.GOLD) button.setIcon(new ImageIcon(ChessSprites.GOLD_ROOK));
                    if (piece.getColor() == Piece.SILVER) button.setIcon(new ImageIcon(ChessSprites.SILVER_ROOK));
                } else if (piece instanceof Bishop) {
                    if (piece.getColor() == Piece.GOLD) button.setIcon(new ImageIcon(ChessSprites.GOLD_BISHOP));
                    if (piece.getColor() == Piece.SILVER) button.setIcon(new ImageIcon(ChessSprites.SILVER_BISHOP));
                } else if (piece instanceof Knight) {
                    if (piece.getColor() == Piece.GOLD) button.setIcon(new ImageIcon(ChessSprites.GOLD_KNIGHT));
                    if (piece.getColor() == Piece.SILVER) button.setIcon(new ImageIcon(ChessSprites.SILVER_KNIGHT));
                } else if (piece instanceof Queen) {
                    if (piece.getColor() == Piece.GOLD) button.setIcon(new ImageIcon(ChessSprites.GOLD_QUEEN));
                    if (piece.getColor() == Piece.SILVER) button.setIcon(new ImageIcon(ChessSprites.SILVER_QUEEN));
                } else if (piece instanceof King) {
                    if (piece.getColor() == Piece.GOLD) button.setIcon(new ImageIcon(ChessSprites.GOLD_KING));
                    if (piece.getColor() == Piece.SILVER) button.setIcon(new ImageIcon(ChessSprites.SILVER_KING));
                } else {
                    button.setIcon(null);
                }
            }
        }
        repaint();
    }

    /**
     * Returns the 2D array of Pieces. I can't think of when this would need to be used, but it's here anyway.
     *
     * @return The 2D array of Pieces
     */
    public Piece[][] getPieces() {
        return pieces;
    }

    /**
     * Gets the Piece at a given Position
     *
     * @param position The Position to get the Piece from
     * @return The Piece at the Position
     */
    public Piece getPieceAt(Position position) {
        return pieces[position.getyPos()][position.getXPosAsInt()];
    }

    /**
     * Just a quick and dirty toString to pretty-print the board to the console. Used for debugging.
     *
     * @return A string with the ASCII board.
     */
    @Override
    public String toString() {
        String ret = "";
        ret += "Board:\n";
        for (int x = 0; x < 8; x++) {
            ret += "-----------------\n";
            for (int y = 0; y < 8; y++) {
                ret += "|";
                if (pieces[x][y] instanceof Pawn) {
                    ret += "p";
                } else if (pieces[x][y] instanceof King) {
                    ret += "K";
                } else if (pieces[x][y] instanceof Queen) {
                    ret += "Q";
                } else if (pieces[x][y] instanceof Rook) {
                    ret += "r";
                } else if (pieces[x][y] instanceof Knight) {
                    ret += "k";
                } else if (pieces[x][y] instanceof Bishop) {
                    ret += "b";
                } else {
                    ret += " ";
                }
            }
            ret += "|\n";
        }
        return ret;
    }
}
