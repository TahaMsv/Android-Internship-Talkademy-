import java.util.List;
import java.util.Scanner;

public class Main {
    private static final String DIRECTORY_NAME = "SampleEnglishData\\EnglishData";
    private static final String EXIT_COMMAND = "EXIT()";

    public static void main(String[] args) {

        DirectoryReader directoryReader = new DirectoryReader(DIRECTORY_NAME);
        InvertedIndex invertedIndex = new InvertedIndex();
        invertedIndex.extractWordsFromFiles(directoryReader);

        String str = "";
        Scanner input = new Scanner(System.in);
        do {
            System.out.print("What do you want to search? If you want to exit, please type \"" + EXIT_COMMAND + "\":");
            str = input.nextLine();
            if (!str.equals(EXIT_COMMAND)) {
                List<String> filteredDocsList = invertedIndex.filterDocs(str.toLowerCase());
                printList(filteredDocsList);
            }
        } while (!str.equals(EXIT_COMMAND));
    }

    private static void printList(List<String> filteredDocsList) {
        if (filteredDocsList.size() == 0) {
            System.out.println("No document found.");
        } else {
            for (int i = 0; i < filteredDocsList.size(); i++) {
                System.out.println((i + 1) + " - " + filteredDocsList.get(i));
            }
        }
    }
}
