import java.util.List;

public class LogUtil {

    public static void logString(String message) {
        System.out.println(message);
    }

    public static void logInt(int number) {
        System.out.println(number);
    }

    public static void logDouble(double number){
        System.out.println(number);
    }

    public static void logError(String message) {
        System.out.println("Error: " + message);
    }

    public static void logDocList(List<String> filteredDocsList) {
        if (filteredDocsList.size() == 0) {
            LogUtil.logString("No document found.");
        } else {
            for (int i = 0; i < filteredDocsList.size(); i++) {
                LogUtil.logString((i + 1) + " - " + filteredDocsList.get(i));
            }
        }
    }
}
