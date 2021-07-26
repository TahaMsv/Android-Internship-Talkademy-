import java.util.List;
import java.util.Scanner;

public class Main {
    private static final String DIRECTORY_NAME = "SampleEnglishData\\EnglishData";
    private static final String EXIT_COMMAND = "EXIT()";

    public static void main(String[] args) {
        Log log = new Log();
        DirectoryReader directoryReader = new DirectoryReader(DIRECTORY_NAME);
        InvertedIndex invertedIndex = new InvertedIndex();
        invertedIndex.extractWordsFromFiles(directoryReader);

        String str = "";
        Scanner input = new Scanner(System.in);
        do {
            log.log("What do you want to search? If you want to exit, please type \"" + EXIT_COMMAND + "\":");
            str = input.nextLine();
            if (!str.equals(EXIT_COMMAND)) {
                List<String> filteredDocsList = invertedIndex.filterDocs(str.toLowerCase());
                invertedIndex.printList(filteredDocsList);
            }
        } while (!str.equals(EXIT_COMMAND));
    }


}
