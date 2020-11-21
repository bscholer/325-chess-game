import java.util.Scanner;

public class Main {
    public static void main (String [] args) {
        while (true) {
            Scanner scanner = new Scanner(System.in);
            System.out.print("Enter position: ");
            String pos = scanner.nextLine();
            try {
                Position position = new Position(pos);
                System.out.println(position);
            } catch (InvalidPositionException e) {
                e.printStackTrace();
            }
        }
    }
}
