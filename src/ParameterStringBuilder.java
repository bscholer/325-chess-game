import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Map;

/**
 * This class is used for building a string of URL parameters to pass stuff to the API.
 * This is directly from StackOverflow, but I can't seem to find the link.
 */
public class ParameterStringBuilder {

    /**
     * Get the String of parameters based on a Map.
     *
     * @param params The Map<String, String> of parameters you want to add.
     * @return The String of parameters
     * @throws UnsupportedEncodingException Just in case things go wrong
     */
    public static String getParamsString(Map<String, String> params)
            throws UnsupportedEncodingException {
        StringBuilder result = new StringBuilder();

        for (Map.Entry<String, String> entry : params.entrySet()) {
            result.append(URLEncoder.encode(entry.getKey(), "UTF-8"));
            result.append("=");
            result.append(URLEncoder.encode(entry.getValue(), "UTF-8"));
            result.append("&");
        }

        String resultString = result.toString();
        return resultString.length() > 0
                ? resultString.substring(0, resultString.length() - 1)
                : resultString;
    }
}