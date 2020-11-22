import java.util.ArrayList;
import java.util.List;

/**
 * The helper class that handles all interaction with the Heroku Chess API.
 *
 * All API endpoints specified in comments assume a prefix of:
 * http://chess-api-chess.herokuapp.com/api/v1/chess
 * One/Two player must be specified. This is used to determine which endpoint to call.
 *
 * All of these functions should probably throw an exception for error-handling and debugging purposes.
 * Fell free to make an exception accordingly
 */
public class ChessAPI {
    private String gameID;
    private boolean isOnePlayer;

    /**
     * PRIORITY: VERY HIGH
     * Creates a new ChessAPI object.
     * This needs to call the Heroku Chess API's Create New Game endpoint (/one OR /two) and set the gameID accordingly.
     */
    public ChessAPI(boolean isOnePlayer) {
        this.isOnePlayer = isOnePlayer;
        // TODO API code
    }

    /**
     * This should be used for multiplayer for the player joining the server.
     * @param gameID The Chess API's gameID
     * @param isOnePlayer Is the game one player?
     */
    public ChessAPI(String gameID, boolean isOnePlayer) {
        this.gameID = gameID;
        this.isOnePlayer = isOnePlayer;
    }

    /**
     * PRIORITY: HIGH
     * Use the Heroku API to figure out the possible moves for a given Piece.
     * Should use either /one/moves OR /two/moves
     * @param piece the piece to check movements of
     * @return An (Array)List of the possible moves
     */
    public List<Move> getPossibleMoves(Piece piece) {
        // TODO API code
        return new ArrayList<>();
    }

    /**
     * PRIORITY: HIGH
     * Execute Move, and tell the Heroku API about it.
     * Endpoint is either /one/move/player OR /two/move/player
     * @param move The move to execute
     * @param board The current game's Board. Objects are pass-by-ref, so just update that object, no need to return anything.
     */
    public void movePlayer(Board board, Move move) {
        board.movePiece(move);
        // TODO API code
    }

    /**
     * PRIORITY: HIGH
     * Check's if the game is over.
     * Endpoint is either /one/check OR /two/check
     * This should be run after every single move.
     * @return True if the game IS over, false if it isn't.
     */
    public boolean checkGameOver() {
        // TODO API code
        return false;
    }

    /**
     * PRIORITY: MEDIUM
     * In the case of a single-player game, lets the AI take it's turn.
     * Endpoint is /one/move/ai
     * @param board The current game's Board. Objects are pass-by-ref, so just update that object, no need to return anything.
     */
    public void moveAI(Board board) {
        // This will only work for one player games!
        if (!isOnePlayer) return;
        // TODO API code
    }

    /**
     * PRIORITY: LOW
     * Get's Heroku's version of the board. This will really only be used to confirm that our Board matches Heroku's board.
     * This would likely use either /one/fen OR /two/fen
     * @return The 2d pieces array
     */
    public Piece[][] getBoard() {
        // TODO API code
        return new Piece[8][8];
    }
}
