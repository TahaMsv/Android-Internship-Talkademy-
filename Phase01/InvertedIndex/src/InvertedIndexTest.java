import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.*;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class InvertedIndexTest {
    public InvertedIndex invertedIndex;

    @Before
    public void setUp() {
        invertedIndex = new InvertedIndex();
        invertedIndex.updateTokenWords(new DocumentModel("name1", "c1"));
        invertedIndex.updateTokenWords(new DocumentModel("name1", "c2"));
        invertedIndex.updateTokenWords(new DocumentModel("name2", "c1"));
        invertedIndex.updateTokenWords(new DocumentModel("name2", "c1"));
        invertedIndex.updateTokenWords(new DocumentModel("name3", "c4"));
        invertedIndex.updateTokenWords(new DocumentModel("name3", "c3"));


    }

    @Test
    public void getTokenWordsTest() {
        Map<String, Set<String>> expected = new HashMap<>();
        expected.put("c1", new HashSet<>(Arrays.asList("name1","name2")));
        expected.put("c2", new HashSet<>(Arrays.asList("name1")));
        expected.put("c3", new HashSet<>(Arrays.asList("name3")));
        expected.put("c4", new HashSet<>(Arrays.asList("name3")));
        expected.put("c5", new HashSet<>(Arrays.asList("name4")));
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
        assertEquals(invertedIndex.getTokenWords().size(), 5);
    }


    @After
    public void tearDown() {
        invertedIndex = null;
    }
}
