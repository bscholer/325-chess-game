import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.net.URL;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * This class handles the sprite sheet of the chess pieces.
 * This takes *very* heavy inspiration from https://stackoverflow.com/questions/19209650/example-images-for-code-and-mark-up-qas
 */
public class ChessSprites {
    private ChessSprites() {}
    public static final int SIZE = 64;
    public static final BufferedImage SHEET;
    static {
        try {
            // see https://stackoverflow.com/a/19209651/2891664
            SHEET = ImageIO.read(new URL("https://i.stack.imgur.com/memI0.png"));
        } catch (IOException x) {
            throw new UncheckedIOException(x);
        }
    }
    public static final BufferedImage GOLD_QUEEN    = SHEET.getSubimage(0 * SIZE, 0,    SIZE, SIZE);
    public static final BufferedImage SILVER_QUEEN  = SHEET.getSubimage(0 * SIZE, SIZE, SIZE, SIZE);
    public static final BufferedImage GOLD_KING     = SHEET.getSubimage(1 * SIZE, 0,    SIZE, SIZE);
    public static final BufferedImage SILVER_KING   = SHEET.getSubimage(1 * SIZE, SIZE, SIZE, SIZE);
    public static final BufferedImage GOLD_ROOK     = SHEET.getSubimage(2 * SIZE, 0,    SIZE, SIZE);
    public static final BufferedImage SILVER_ROOK   = SHEET.getSubimage(2 * SIZE, SIZE, SIZE, SIZE);
    public static final BufferedImage GOLD_KNIGHT   = SHEET.getSubimage(3 * SIZE, 0,    SIZE, SIZE);
    public static final BufferedImage SILVER_KNIGHT = SHEET.getSubimage(3 * SIZE, SIZE, SIZE, SIZE);
    public static final BufferedImage GOLD_BISHOP   = SHEET.getSubimage(4 * SIZE, 0,    SIZE, SIZE);
    public static final BufferedImage SILVER_BISHOP = SHEET.getSubimage(4 * SIZE, SIZE, SIZE, SIZE);
    public static final BufferedImage GOLD_PAWN     = SHEET.getSubimage(5 * SIZE, 0,    SIZE, SIZE);
    public static final BufferedImage SILVER_PAWN   = SHEET.getSubimage(5 * SIZE, SIZE, SIZE, SIZE);
    public static final List<BufferedImage> SPRITES =
            Collections.unmodifiableList(Arrays.asList(GOLD_QUEEN,  SILVER_QUEEN,
                    GOLD_KING,   SILVER_KING,
                    GOLD_ROOK,   SILVER_ROOK,
                    GOLD_KNIGHT, SILVER_KNIGHT,
                    GOLD_BISHOP, SILVER_BISHOP,
                    GOLD_PAWN,   SILVER_PAWN));
}
