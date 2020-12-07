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
    public Position() {

    }

    /**
     * Constructor for creating a Position with numerical coordinates
     *
     * @param xPos 0-7
     * @param yPos 0-7
     */
    public Position(int xPos, int yPos) {
        if (xPos >= 0 && xPos <= 8 && yPos >= 0 && yPos <= 8) {
            this.xPos = ((char) (xPos + 65)) + "";
            this.yPos = yPos;
        }
    }

    /**
     * Constructor, parses the position string and sets the xPos/yPos variables.
     *
     * @param position The xy or yx position, can either be in the 'A3' or the '3A' format.
     */
    public Position(String position) {
        this.setPosition(position);
    }

    /**
     * Get xPos as a String
     *
     * @return the xPos
     */
    public String getxPos() {
        return xPos.toUpperCase();
    }

    /**
     * Get the xPos as an int (0-7)
     *
     * @return xPos, 0-7
     */
    public int getXPosAsInt() {
        return xPos.charAt(0) - 65;
    }

    /**
     * Get the yPos
     *
     * @return yPos as an int, 0-7
     */
    public int getyPos() {
        return yPos;
    }

    /**
     * Set the position with a xy or yx position, using letters for x.
     *
     * @param position The xy or yx position, can either be in the 'A3' or the '3A' format.
     */
    public void setPosition(String position) {
        // The position must be only two characters long
        if (position.length() != 2) return;

        Matcher xyMatch = xyPattern.matcher(position);
        // Try the xy match first
        if (xyMatch.find()) {
            xPos = xyMatch.group(1).toUpperCase();
            yPos = Integer.parseInt(xyMatch.group(2)) - 1;
        }
        // xy match didn't work, so try yxMatch
        else {
            Matcher yxMatch = yxPattern.matcher(position);
            if (yxMatch.find()) {
                xPos = yxMatch.group(2).toUpperCase();
                yPos = Integer.parseInt(yxMatch.group(1)) - 1;
            } else {
                // Neither match worked
                return;
            }
        }
    }

    @Override
    public String toString() {
        return (xPos + ((yPos + 1) + "")).toLowerCase();
    }

    /**
     * Just checks if the x and y of this and obj are equal
     *
     * @param obj The Position
     * @return true or false, depending on if the x's and y's match
     */
    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Position) {
            return (xPos.equals(((Position) obj).getxPos()) && yPos == ((Position) obj).getyPos());
        } else return false;
    }
}
