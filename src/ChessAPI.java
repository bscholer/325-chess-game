import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Assigned to: Kenny
 * <p>
 * The helper class that handles all interaction with the Heroku Chess API.
 * <p>
 * All API endpoints specified in comments assume a prefix of:
 * http://chess-api-chess.herokuapp.com/api/v1/chess
 * One/Two player must be specified. This is used to determine which endpoint to call.
 * <p>
 * All of these functions should probably throw an exception for error-handling and debugging purposes.
 * Fell free to make an exception accordingly
 */
public class ChessAPI {
    private String gameID;
    static String url = "http://chess-api-chess.herokuapp.com/api/v1/chess/one";
    static URL baseUrl;

    /**
     * PRIORITY: VERY HIGH
     * Creates a new ChessAPI object.
     * This needs to call the Heroku Chess API's Create New Game endpoint /one and set the gameID accordingly.
     */
    public ChessAPI() {
        try {
            baseUrl = new URL(url);
            HttpURLConnection connection = (HttpURLConnection) baseUrl.openConnection();

            // Now it's "open", we can set the request method, headers etc.
            connection.setRequestMethod("GET");
            int status = connection.getResponseCode();


            //Read the response from the create new game request.
            BufferedReader br = new BufferedReader(
                    new InputStreamReader(connection.getInputStream()));
            String inputLine;
            StringBuffer content = new StringBuffer();
            while ((inputLine = br.readLine()) != null) {
                content.append(inputLine);
                System.out.println("Content: " + content);
            }
            br.close();

            //Parse the response to get game ID
            String jsonString = content.toString();
            JSONObject obj = new JSONObject(jsonString);
            String game_id = obj.getString("game_id");
            connection.disconnect();
            //final game id
            Logging.logEvent("Created new game_id: " + game_id, game_id);
            this.gameID = game_id;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * PRIORITY: HIGH
     * Use the Heroku API to figure out the possible moves for a given Piece.
     * Should use /one/moves
     *
     * @param piece the piece to check movements of
     * @return An (Array)List of the possible moves
     */
    public List<Move> getPossibleMoves(Piece piece) {
        System.out.println("GAMEID: " + gameID);
        List<Move> moves = new ArrayList<Move>();

        try {
            String endpoint = "/moves";

            Map<String, String> parameters = new HashMap<>();
            parameters.put("game_id", gameID);
            parameters.put("position", piece.getPosition().toString());

            String content = ParameterStringBuilder.getParamsString(parameters);

            System.out.println(content);

            String response = doPostSync(url + endpoint, content);

            System.out.println(response);

            String jsonString = response.toString();
            JSONObject obj = new JSONObject(jsonString);
            JSONArray JSONMovesArray = obj.getJSONArray("moves");

            for (Object moveObj : JSONMovesArray) {
                String move = (String) moveObj;
                System.out.println(move);
                moves.add(new Move(new Position(move), piece));
            }

        } catch (JSONException jsonException) {

        } catch (Exception e) {
            e.printStackTrace();
        }

        return moves;
    }

    /**
     * PRIORITY: HIGH
     * Execute Move, and tell the Heroku API about it.
     * Endpoint is /one/move/player
     *
     * @param move  The move to execute
     */
    public void movePlayer(Move move) {

        try {
            String endpoint = "/move/player";

            Map<String, String> parameters = new HashMap<>();
            parameters.put("from", move.getPiece().getPosition().toString());
            parameters.put("to", move.getFuturePosition().toString());
            parameters.put("game_id", gameID);

            String content = ParameterStringBuilder.getParamsString(parameters);

            System.out.println(content);

            String response = doPostSync(url + endpoint, content);


        } catch (JSONException jsonException) {

        } catch (Exception e) {
            e.printStackTrace();
        }



    }

    /**
     * PRIORITY: HIGH
     * Check's if the game is over.
     * Endpoint is /one/check
     * This should be run after every single move.
     *
     * @return True if the game IS over, false if it isn't.
     */
    public boolean checkGameOver() {

        try {
            String endpoint = "/check";

            Map<String, String> parameters = new HashMap<>();
            parameters.put("game_id", gameID);

            String content = ParameterStringBuilder.getParamsString(parameters);

            System.out.println(content);

            String response = doPostSync(url + endpoint, content);

            System.out.println(response);

            String jsonString = response.toString();
            JSONObject obj = new JSONObject(jsonString);

            return !obj.getString("status").equals("game continues");

        } catch (JSONException jsonException) {

        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * PRIORITY: MEDIUM
     * In the case of a single-player game, lets the AI take it's turn.
     * Endpoint is /one/move/ai
     *
     * @param board The current game's Board. Objects are pass-by-ref, so just update that object, no need to return anything.
     */
    public Move moveAI(Board board) {
        // This will only work for one player games!
        // TODO API code
        Move move = new Move();
        try {
            String endpoint = "/move/ai";

            Map<String, String> parameters = new HashMap<>();
            parameters.put("game_id", gameID);

            String content = ParameterStringBuilder.getParamsString(parameters);

            System.out.println(content);

            String response = doPostSync(url + endpoint, content);

            System.out.println(response);

            String jsonString = response.toString();
            JSONObject obj = new JSONObject(jsonString);
            move = new Move(new Position(obj.getString("to")), board.getPieceAt(new Position(obj.getString("from"))));

        } catch (JSONException jsonException) {

        } catch (Exception e) {
            e.printStackTrace();
        }
        return move;
    }

    /**
     * PRIORITY: LOW
     * Get's Heroku's version of the board. This will really only be used to confirm that our Board matches Heroku's board.
     * This would likely use either /one/fen OR /two/fen
     *
     * @return The 2d pieces array
     */
    public Piece[][] getBoard() {
        // TODO API code
        return new Piece[8][8];
    }

    public String getGameID() {
        return gameID;
    }

    private String doPostSync(final String urlToRead, final String content) throws IOException {
        final String charset = "UTF-8";
        // Create the connection
        HttpURLConnection connection = (HttpURLConnection) new URL(urlToRead).openConnection();
        // setDoOutput(true) implicitly set's the request type to POST
        connection.setDoOutput(true);
        connection.setRequestProperty("Accept-Charset", charset);
        connection.setRequestProperty("Content-type", "application/x-www-form-urlencoded");

        // Write to the connection
        OutputStream output = connection.getOutputStream();
        output.write(content.getBytes(charset));
        output.close();

        // Check the error stream first, if this is null then there have been no issues with the request
        InputStream inputStream = connection.getErrorStream();
        if (inputStream == null)
            inputStream = connection.getInputStream();

        // Read everything from our stream
        BufferedReader responseReader = new BufferedReader(new InputStreamReader(inputStream, charset));

        String inputLine;
        StringBuffer response = new StringBuffer();

        while ((inputLine = responseReader.readLine()) != null) {
            response.append(inputLine);
        }
        responseReader.close();

        return response.toString();
    }
}
