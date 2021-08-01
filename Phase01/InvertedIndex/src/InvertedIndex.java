import java.io.File;
import java.util.*;

public class InvertedIndex {
    private static Map<String, Set<String>> tokenWords = new HashMap<>();
    private final static char PLUS_SIGN = '+';
    private final static char MINUS_SIGN = '-';
    private final static Set<String> stopWords = new HashSet<>(Arrays.asList("their", "too", "only", "myself", "which", "those", "i", "after",
            "few", "whom", "t", "being", "if", "theirs", "my", "against", "a", "by", "doing", "it", "how"
            , "further", "while", "above", "both", "up", "to", "ours", "had", "she", "all", "no", "when", "at",
            "any", "before", "them", "same", "and", "been", "have", "in", "will", "on", "does", "yourselves",
            "then", "ourselves", "hers", "between", "yourself", "but", "again", "of", "most", "itself", "other",
            "off", "is", "s", "am", "or", "who", "as", "from", "him", "each", "the", "themselves", "until", "below",
            "your", "his", "through", "don", "nor", "me", "were", "her", "more", "himself", "this", "down", "should",
            "our", "there", "about", "once", "during", "out", "very", "having", "with", "they", "own", "an", "be",
            "some", "for", "do", "its", "yours", "such", "into", "are", "we", "these", "has", "just", "where", "was", "here",
            "that", "because", "what", "over", "why", "so", "can", "did", "not", "now", "under", "he", "you", "herself", "than"));


    void extractWordsFromFiles(DirectoryReader directoryReader) {
        if (directoryReader != null) {
            File[] files = directoryReader.getFilesList();
            if (files != null) {
                for (File file : files) {
                    String fileName = file.getName();
                    String fileContent = directoryReader.readFileContent(file);
                    DocumentModel document = new DocumentModel(fileName, fileContent);
                    updateTokenWords(document);
                }
            }
        }
    }

    private void updateTokenWords(DocumentModel document) {
        String[] words;
        if (document.getContent() != null) {
            words = document.getContent().split("\\s+");
            for (String word : words) {
                if (!getStopWords().contains(word)) {
                    if (tokenWords.containsKey(word)) {
                        tokenWords.get(word).add(document.getName());
                    } else {
                        tokenWords.put(word, new HashSet<>() {{
                            add(document.getName());
                        }});
                    }
                }
            }
        }
    }

    List<String> filterDocs(String queryString) {
        String[] queryWords = queryString.split("\\s+");
        Set<String> plusSet = new HashSet<>();
        Set<String> minusSet = new HashSet<>();
        Set<String> normalSet = new HashSet<>();
        for (String queryWord : queryWords) {
            switch (queryWord.charAt(0)) {
                case PLUS_SIGN:
                    plusSet.add(queryWord.substring(1));
                    break;
                case MINUS_SIGN:
                    minusSet.add(queryWord.substring(1));
                    break;
                default:
                    normalSet.add(queryWord);
            }

        }
        Query query = new Query(plusSet, minusSet, normalSet);
        return getOutput(query);
    }

    private List<String> getOutput(Query query) {
        List<String> output = new ArrayList<>();
        output.addAll(getAllValidDocs(query.getMustBeDocsList()));
        output.addAll(getAllValidDocs(query.getShouldBeDocsList()));
        output.removeAll(getAllValidDocs(query.getMustNotBeDocsList()));
        return output;
    }

    private List<String> getAllValidDocs(Set<String> normalSet) {
        List<String> result = new ArrayList<>();
        for (String word : normalSet) {
            if (tokenWords.containsKey(word)) {
                result.addAll(tokenWords.get(word));
            }
        }
        return result;
    }

    public static Set<String> getStopWords() {
        return stopWords;
    }
}



