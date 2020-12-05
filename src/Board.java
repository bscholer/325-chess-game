import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

/**
 * The chess board. Contains a 2D array of pieces.
 */
public class Board extends JPanel {

    private Piece[][] pieces;
    private JButton[][] boardButtons;
    private Board board;
    private int state;
    private final int READY_TO_MOVE = 0;
    private final int MOVES_SHOWN = 1;
    private List<Move> possibleMoves;
    private int myColor = Piece.SILVER;

    /**
     * Default constructor, just instantiates the array.
     * Call fillBoard() to fill the board with pieces in their starting positions
     * This takes heavy inspiration from https://stackoverflow.com/a/21096455
     */
    public Board() {
        super(new GridLayout(0, 9));
        board = this;
        setBorder(new LineBorder(Color.BLACK));

        state = READY_TO_MOVE;

        Insets buttonMargin = new Insets(0, 0, 0, 0);
        pieces = new Piece[8][8];
        boardButtons = new JButton[8][8];

        ActionListener boardButtonListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JButton clickedButton;
                Piece piece = null;
                Position position = null;
                for (int x = 0; x < 8; x++) {
                    for (int y = 0; y < 8; y++) {
                        if (e.getSource() == boardButtons[x][y]) {
                            clickedButton = (JButton) e.getSource();
                            position = new Position(y, x);
                            piece = getPieceAt(position);
                        }
                    }
                }
                if (state == READY_TO_MOVE) {
                    if (piece != null) {
                        if (piece.getColor() == myColor) {
                            possibleMoves = piece.getPotentialMoves(board);

                            // Highlight the possible move locations
                            for (Move move : possibleMoves) {
                                boardButtons[move.getFuturePosition().getyPos()][move.getFuturePosition().getXPosAsInt()].setBackground(Color.BLUE);
                            }
                            state = MOVES_SHOWN;
                        }
                    }
                }
                else if (state == MOVES_SHOWN) {
                    // Check that the clicked box is a possible move
                    boolean isValidMove = false;
                    Move currentMove = null;
                    for (Move move : possibleMoves) {
                        if (move.getFuturePosition().equals(position)) {
                            isValidMove = true;
                            currentMove = move;
                        }
                    }
                    // Make the move
                    if (isValidMove) {
                        movePiece(currentMove);
                        System.out.println(board);
                        drawBoard();
                        recolorButtons();
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
                    button.setBackground(Color.GRAY);
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

    private void recolorButtons() {
        for (int x = 0; x < 8; x++) {
            for (int y = 0; y < 8; y++) {
                JButton button = boardButtons[x][y];

                // Color every other button black
                if ((x % 2 == 1 && y % 2 == 1) || (y % 2 == 0 && x % 2 == 0)) {
                    button.setBackground(Color.GRAY);
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
        // Make sure that it's valid
        if (move.getPiece().isMoveValid(this, move)) {
            // Check if there is already a Piece where we are trying to move
            if (getPieceAt(move.getFuturePosition()) != null) {
                removePiece(move.getFuturePosition());
            }
            System.out.println("It's valid!");
            System.out.println(move.getPiece().getPosition());
            Position originalPosition = new Position(move.getPiece().getPosition().toString());
            System.out.println(originalPosition);
            move.getPiece().setPosition(move.getFuturePosition());
            pieces[move.getFuturePosition().getyPos()][move.getFuturePosition().getXPosAsInt()] = move.getPiece();
            pieces[originalPosition.getyPos()][originalPosition.getXPosAsInt()] = null;
        }
    }

    /**
     * This will look through the current board, find all the pieces, and reset their positions to
     * whatever they have as their position variable.
     * Call this method AFTER changing a Piece object's position.
     *
     * @param piecesList The list of pieces generated by getListOfPieces()
     */
    public void updateBoard(List<Piece> piecesList) {
        // Nuke the (2d) pieces array
        pieces = new Piece[8][8];

        // Add the pieces back into the pieces 2d array
        for (Piece piece : piecesList) {
            pieces[piece.getPosition().getXPosAsInt()][piece.getPosition().getyPos()] = piece;
        }
    }

    private List<Piece> getListOfPieces() {
        // Get all the pieces and put them in a temporary (Array)List
        List<Piece> piecesList = new ArrayList<>();
        for (int x = 0; x < 8; x++) {
            for (int y = 0; y < 8; y++) {
                if (pieces[x][y] != null) {
                    piecesList.add(pieces[x][y]);
                }
            }
        }
        return piecesList;
    }

    /**
     * Removes a Piece, given the Piece object.
     * @param piece The Piece to remove from the Board
     */
    public void removePiece(Piece piece) {
        pieces[piece.getPosition().getyPos()][piece.getPosition().getXPosAsInt()] = null;
    }

    /**
     * Remove the piece at the given Position
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
     * Returns the 2D array of pieces. I can't think of when this would need to be used, but it's here anyway.
     *
     * @return The 2D array of pieces
     */
    public Piece[][] getPieces() {
        return pieces;
    }

    /**
     * Gets the piece at a given position
     *
     * @param position The position to get the piece from
     * @return The piece at position
     */
    public Piece getPieceAt(Position position) {
        return pieces[position.getyPos()][position.getXPosAsInt()];
    }

    @Override
    public String toString() {
        String ret = "";
        ret += "Board:\n";
        for (int x = 0; x < 8; x++) {
            ret += "-----------------\n";
            for (int y = 0; y < 8; y++) {
                ret += "|";
                if (pieces[x][y] instanceof Pawn) {
                    ret += "P";
                } else {
                    ret += " ";
                }
            }
            ret += "|\n";
        }
        return ret;
    }
}
