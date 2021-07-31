package SerachEngin;

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
            LogUtil.logString("What do you want to search? If you want to exit, please type \"" + EXIT_COMMAND + "\":");
            str = input.nextLine();
            if (str != null && !EXIT_COMMAND.equals(str)) {
                List<String> filteredDocsList = invertedIndex.filterDocs(str.toLowerCase());
                LogUtil.logDocList(filteredDocsList);
            }
        } while (!EXIT_COMMAND.equals(str));
    }
}
