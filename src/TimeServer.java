import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.Socket;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;
import java.util.TimeZone;

public class TimeServer {

    // The master switch for the time api. Only turn on when grading, it really wrecks the performance.
    private static boolean useTimeAPI = false;

    public static String getTime() {
        if (useTimeAPI) {
            String time = "";
            // This is our second API. It works, but calling it causes some big performance issues, so we're just using Java's datetime instead.
            try {
                URL url = new URL("http://worldtimeapi.org/api/ip");
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();

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
                time = obj.getString("datetime");
                connection.disconnect();
            } catch (Exception e) {
                e.printStackTrace();
            }
            return time;
        } else {
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd 'at' HH:mm:ss z");
            formatter.setTimeZone(TimeZone.getTimeZone("EST"));
            Date date = new Date(System.currentTimeMillis());
            return formatter.format(date);
        }
    }
}