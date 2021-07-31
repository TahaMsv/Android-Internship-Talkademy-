import SerachEngin.DirectoryReader;
import SerachEngin.DocumentModel;
import SerachEngin.InvertedIndex;
import SerachEngin.Query;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class InvertedIndexTest {
    public InvertedIndex invertedIndex;
    public DirectoryReader directoryReader = new DirectoryReader("");
    File rootDirectory;
    File file;
    File file2;
    File file3;
    @Rule
    public TemporaryFolder folder = new TemporaryFolder();


    @Before
    public void setUp() throws IOException {
        invertedIndex = new InvertedIndex();
        invertedIndex.updateTokenWords(new DocumentModel("name1", "c1"));
        invertedIndex.updateTokenWords(new DocumentModel("name1", "c2"));
        invertedIndex.updateTokenWords(new DocumentModel("name2", "c1"));
        invertedIndex.updateTokenWords(new DocumentModel("name2", "c1"));
        invertedIndex.updateTokenWords(new DocumentModel("name3", "c4"));
        invertedIndex.updateTokenWords(new DocumentModel("name3", "c3"));

        rootDirectory = folder.newFolder("root");
        file = new File(rootDirectory, "testFile1.txt");
        file2 = new File(rootDirectory, "testFile2.txt");

        if (!file2.exists()) {
            file2.createNewFile();
        }
        if (!file.exists()) {
            file.createNewFile();
        }
        FileWriter fileWriter = new FileWriter(file);
        FileWriter fileWriter2 = new FileWriter(file2);
        fileWriter.write("test1");
        fileWriter2.write("test2");
        fileWriter.close();
        fileWriter2.close();

    }

    @Test
    public void extractWordsFromFilesTest(){
        directoryReader.setDirectory(rootDirectory);
        invertedIndex.extractWordsFromFiles(directoryReader);
        Map<String,Set<String>> result=invertedIndex.getTokenWords();
        Map<String, Set<String>> expected = new HashMap<>();
        expected.put("c1", new HashSet<>(Arrays.asList("name1","name2")));
        expected.put("c2", new HashSet<>(Arrays.asList("name1")));
        expected.put("c3", new HashSet<>(Arrays.asList("name3")));
        expected.put("c4", new HashSet<>(Arrays.asList("name3")));
        expected.put("test1", new HashSet<>(Arrays.asList("testFile1.txt")));
        expected.put("test2", new HashSet<>(Arrays.asList("testFile2.txt")));
        assertEquals(expected, result);
    }
    @Test
    public void getTokenWordsTest() {
        Map<String, Set<String>> expected = new HashMap<>();
        expected.put("c1", new HashSet<>(Arrays.asList("name1","name2")));
        expected.put("c2", new HashSet<>(Arrays.asList("name1")));
        expected.put("c3", new HashSet<>(Arrays.asList("name3")));
        expected.put("c4", new HashSet<>(Arrays.asList("name3")));
        expected.put("c5", new HashSet<>(Arrays.asList("name4")));
        expected.put("test1", new HashSet<>(Arrays.asList("testFile1.txt")));
        expected.put("test2", new HashSet<>(Arrays.asList("testFile2.txt")));
        assertEquals(expected, invertedIndex.getTokenWords());
    }

    @Test
    public void getStopWordsTest() {
        boolean result = invertedIndex.getStopWords().contains("to");
        assertTrue(result);
    }

    @Test
    public void getAllValidDocsTest() {
        Set<String> set = new HashSet<>(Arrays.asList("c1", "c2", "c3", "c4", "to"));
        Set<String> result = invertedIndex.getAllValidDocs(set);
        Set<String> expected = new HashSet<>(Arrays.asList("name3", "name2", "name1"));
        assertEquals(result, expected);
    }

    @Test
    public void getOutputTest() {
        Set<String> plusSet = new HashSet<>(Arrays.asList("c2"));
        Set<String> minusSet = new HashSet<>(Arrays.asList("c1"));
        Set<String> normalSet = new HashSet<>(Arrays.asList("c3"));

        Query query = new Query(plusSet, minusSet, normalSet);
        List<String> result = invertedIndex.getOutput(query);
        List<String> expected = new ArrayList<>(Arrays.asList("name3"));

        assertEquals(expected, result);

    }

    @Test
    public void filterDocsTest() {
        String query = "-c1 +c2 c3";
        List<String> result = invertedIndex.filterDocs(query);
        List<String> expected = new ArrayList<>(Arrays.asList("name3"));
        assertEquals(expected, result);
    }

    @Test
    public void updateTokenWordsTest() {
        DocumentModel documentModel = new DocumentModel("name4", "c5");
        invertedIndex.updateTokenWords(documentModel);
        assertEquals(invertedIndex.getTokenWords().size(), 7);
    }


    @After
    public void tearDown() {
        invertedIndex = null;
    }
}
