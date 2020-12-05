import java.io.BufferedWriter;
import java.io.FileWriter;

public class Logging {
    private static String LOG_FILE_NAME = "_log.txt";

    // Handles all the logging
    public static void logEvent(String event, String gameID) {
        String now = TimeServer.getTime();
        event = now + "\n" + event;
        try {
            FileWriter fw = new FileWriter(gameID + LOG_FILE_NAME, true);
            BufferedWriter bw = new BufferedWriter(fw);
            bw.write(event);
            bw.newLine();
            bw.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
