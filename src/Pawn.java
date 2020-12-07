import java.util.ArrayList;
import java.util.List;

public class Pawn extends PieceWrapper {

    public Pawn(Position position, int color) {
        super(position, color);
    }

    @Override
    public String toString() {
        return "Pawn";
    }
}
