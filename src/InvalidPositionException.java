/**
 * Throwing custom exceptions helps a lot with debugging. This way, you can know exactly why your program bit it.
 * This can be removed later, but should be left in for now.
 */

public class InvalidPositionException extends Exception {

    public InvalidPositionException() {

    }
    public InvalidPositionException(String message) {
        super(message);
    }
}
