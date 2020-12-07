import java.io.BufferedWriter;
import java.io.FileWriter;

/**
 * Takes care of the logging.
 */
public class Logging {
    private static String LOG_FILE_NAME = "_log.txt";

    /**
     * Handles logging
     *
     * @param event  The string you'd like to log
     * @param gameID The current gameID
     */
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
