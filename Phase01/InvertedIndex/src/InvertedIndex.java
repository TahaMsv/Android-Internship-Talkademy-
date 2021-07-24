import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class InvertedIndex {
    static Set<String> plusSet;
    static Set<String> minusSet;
    static Set<String> normalSet;
    static Map<String, Set<String>> tokenWords = tokenWords = new HashMap<>();
    final static Set<String> stopWords = new HashSet<>(Arrays.asList("their", "too", "only", "myself", "which", "those", "i", "after",
            "few", "whom", "t", "being", "if", "theirs", "my", "against", "a", "by", "doing", "it", "how"
            , "further", "while", "above", "both", "up", "to", "ours", "had", "she", "all", "no", "when", "at",
            "any", "before", "them", "same", "and", "been", "have", "in", "will", "on", "does", "yourselves",
            "then", "ourselves", "hers", "between", "yourself", "but", "again", "of", "most", "itself", "other",
            "off", "is", "s", "am", "or", "who", "as", "from", "him", "each", "the", "themselves", "until", "below",
            "your", "his", "through", "don", "nor", "me", "were", "her", "more", "himself", "this", "down", "should",
            "our", "there", "about", "once", "during", "out", "very", "having", "with", "they", "own", "an", "be",
            "some", "for", "do", "its", "yours", "such", "into", "are", "we", "these", "has", "just", "where", "was", "here",
            "that", "because", "what", "over", "why", "so", "can", "did", "not", "now", "under", "he", "you", "herself", "than"));

    public static void main(String[] args) {
        String DIRECTORY_NAME = "SampleEnglishData\\EnglishData";
        DirectoryReader directoryReader = new DirectoryReader(DIRECTORY_NAME);
        extractWordsFromFiles(directoryReader);

        String str = "";
        Scanner input = new Scanner(System.in);
        do {
            System.out.print("What do you want to search? If you want to exit, please type \"EXIT()\":");
            str = input.nextLine();

            if (!str.equals("EXIT()")) {
                List<String> filteredDocsList = filterDocs(str.toLowerCase());
                if (filteredDocsList.size() == 0) {
                    System.out.println("No document found.");
                } else {
                    for (int i = 0; i < filteredDocsList.size(); i++) {
                        System.out.println((i + 1) + "- " + filteredDocsList.get(i));
                    }
                }
            }
        } while (!str.equals("EXIT()"));
    }


    private static void extractWordsFromFiles(DirectoryReader directoryReader) {
        File[] files = directoryReader.getFilesList();
        for (File file : files) {
            String fileName = file.getName();
            String fileContent = directoryReader.readFileContent(file);
            updateTokenWords(fileName, fileContent);
        }
    }

    private static void updateTokenWords(String fileName, String fileContent) {
        String[] words = fileContent.split("\\s+");
        for (String word : words) {
            if (!InvertedIndex.stopWords.contains(word)) {
                if (tokenWords.containsKey(word)) {
                    tokenWords.get(word).add(fileName);
                } else {
                    tokenWords.put(word, new HashSet<String>() {{
                        add(fileName);
                    }});
                }
            }
        }
    }

    private static List<String> filterDocs(String query) {
        String[] queryWords = query.split("\\s+");
        plusSet = new HashSet<>();
        minusSet = new HashSet<>();
        normalSet = new HashSet<>();
        for (int i = 0; i < queryWords.length; i++) {
            if (queryWords[i].startsWith("+")) {
                plusSet.add(queryWords[i].substring(1));
            } else if (queryWords[i].startsWith("-")) {
                minusSet.add(queryWords[i].substring(1));
            } else {
                normalSet.add(queryWords[i]);
            }
        }

        List<String> mustBeDocsList = new ArrayList<>();
        List<String> mustNotBeDocsList = new ArrayList<>();
        List<String> shouldBeDocsList = new ArrayList<>();

        for (String word : plusSet) {
            if (tokenWords.containsKey(word)) {
                mustBeDocsList.addAll(tokenWords.get(word));
            }
        }

        for (String word : minusSet) {
            if (tokenWords.containsKey(word)) {
                mustNotBeDocsList.addAll(tokenWords.get(word));
            }
        }

        for (String word : normalSet) {
            if (tokenWords.containsKey(word)) {
                shouldBeDocsList.addAll(tokenWords.get(word));
            }
        }

        List<String> output = new ArrayList<>();
        output.addAll(mustBeDocsList);
        output.addAll(shouldBeDocsList);
        output.removeAll(mustNotBeDocsList);
        return output;
    }

}


class DirectoryReader {
    private File directory;

    public DirectoryReader(String directoryName) {
        this.directory = new File(directoryName);
    }

    String readFileContent(File file) {
        StringBuilder fileContent = new StringBuilder();
        try {
            FileReader reader = new FileReader(file);
            BufferedReader bufferedReader = new BufferedReader((reader));
            int ch;
            while ((ch = bufferedReader.read()) != -1) {
                fileContent.append((char) ch);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return fileContent.toString().toLowerCase();

    }

    File[] getFilesList() {
        return directory.listFiles();
    }


}
