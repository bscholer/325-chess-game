import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * This class simply stores the X and Y coordinates of a chess piece, and will be used in each of the Piece classes
 * along with the Move class.
 */

public class Position {

    private String xPos;
    private int yPos;
    private Pattern xyPattern = Pattern.compile("([a-hA-H])([1-8])");
    private Pattern yxPattern = Pattern.compile("([1-8])([a-hA-H])");

    /**
     * Default (empty) constructor. This shouldn't be used except in the piece interface.
     */
    public Position () {

    }

    /**
     * Constructor, parses the position string and sets the xPos/yPos variables.
     * @param position The xy or yx position, can either be in the 'A3' or the '3A' format.
     */
    public Position (String position) throws InvalidPositionException {
        this.setPosition(position);
    }

    public String getxPos() {
        return xPos.toUpperCase();
    }

    public int getXPosAsInt() {
        return xPos.charAt(0) - 65;
    }

    public int getyPos() {
        return yPos;
    }

    public void setxPos(String xPos) throws InvalidPositionException {
        if (xPos.length() == 1 && "abcdefghABCDEFGH".contains("" + xPos)) {
            this.xPos = xPos;
        }
        else {
            throw new InvalidPositionException(xPos + " is not a valid x position");
        }
    }

    public void setyPos(int yPos) throws InvalidPositionException {
        if (yPos >= 1 && yPos <= 8) {
            this.yPos = yPos;
        }
        else {
            throw new InvalidPositionException(yPos + " is not a valid y position");
        }
    }

    /**
     * @param position The xy or yx position, can either be in the 'A3' or the '3A' format.
     * @throws InvalidPositionException Gets thrown if the position is invalid
     */
    public void setPosition (String position) throws InvalidPositionException {
        // The position must be only two characters long
        if (position.length() != 2) throw new InvalidPositionException(position + " is not a valid position");

        Matcher xyMatch = xyPattern.matcher(position);
        // Try the xy match first
        if (xyMatch.find()) {
            xPos = xyMatch.group(1).toUpperCase();
            yPos = Integer.parseInt(xyMatch.group(2));
        }
        // xy match didn't work, so try yxMatch
        else {
            Matcher yxMatch = yxPattern.matcher(position);
            if (yxMatch.find()) {
                xPos = yxMatch.group(2).toUpperCase();
                yPos = Integer.parseInt(yxMatch.group(1));
            }
            else {
                // Neither match worked, so throw an exception
                throw new InvalidPositionException(position + " is not a valid position");
            }
        }
    }

    @Override
    public String toString() {
        return xPos + (yPos + "");
    }
}
