import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class InvertedIndex {
    static Map<String, Set<String>> tokenWords = tokenWords = new HashMap<>();
    public static void main(String[] args) {
        String DIRECTORY_NAME = "SampleEnglishData\\EnglishData";
        DirectoryReader directoryReader = new DirectoryReader(DIRECTORY_NAME);
        extractWordsFromFiles(directoryReader);
        for (Map.Entry<String,Set<String>> entry : tokenWords.entrySet())
            System.out.println("Key = " + entry.getKey() +
                    ", Value = " + entry.getValue());
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
