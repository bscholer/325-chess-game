import org.json.JSONArray;
import org.json.JSONObject;

import java.io.*;
import java.net.*;
import java.util.*;

public class HTTPClient {
    static String url = "http://chess-api-chess.herokuapp.com/api/v1/chess/one";
    static URL baseUrl;

    public static void main(String[] args) {
        String game_id = createNewGame();

        System.out.println("game_id: " + game_id);
        Piece piece = new Pawn(new Position("a2"), 0);

//        listPossibleMoves(piece, game_id);
    }

    public static String createNewGame() {
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
            return game_id;
        } catch (Exception e) {
            e.printStackTrace();
        }

        //java sucks
        return "";
    }

//
//    public static List<Move> listPossibleMoves(Piece piece, String game_id) {
//        List<Move> move = new ArrayList<Move>();
//
//        try {
//            String endpoint = "/moves";
//
//            Map<String, String> parameters = new HashMap<>();
//            parameters.put("game_id", game_id);
//            parameters.put("position", piece.getPosition().toString());
//
//            String content = ParameterStringBuilder.getParamsString(parameters);
//
//            System.out.println(content);
//
//            String response = doPostSync(url + endpoint, content);
//
//            System.out.println(response);
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//        //java still sucks
//        return move;
//
//    }


}
