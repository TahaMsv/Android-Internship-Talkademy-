import java.util.Scanner;

public class Log {
    Scanner input = new Scanner(System.in);

    public void log(String message) {
        System.out.println(message);
    }

    public void logError(String message) {
        System.out.println("Error: " + message);
    }
}
