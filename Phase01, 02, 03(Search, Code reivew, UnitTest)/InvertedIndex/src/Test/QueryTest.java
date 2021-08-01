package Test;

import SerachEngin.Query;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.assertEquals;

public class QueryTest {
    Query query;

    @Before
    public void setUp() {
        Set<String> plusSet = new HashSet<>(Arrays.asList("c2", "c4"));
        Set<String> minusSet = new HashSet<>(Arrays.asList("c1"));
        Set<String> normalSet = new HashSet<>(Arrays.asList("c3"));
        query = new Query(plusSet, minusSet, normalSet);
    }

    @Test
    public void getMustBeDocsListTest() {
        Set<String> expected = new HashSet<>(Arrays.asList("c2", "c4"));
        Set<String> result = query.getMustBeDocsList();
        assertEquals(expected, result);
    }

    @Test
    public void getMustNotBeDocsListTest() {
        Set<String> expected = new HashSet<>(Arrays.asList("c1"));
        Set<String> result = query.getMustNotBeDocsList();
        assertEquals(expected, result);
    }

    @Test
    public void getShouldBeDocsListTest() {
        Set<String> expected = new HashSet<>(Arrays.asList("c3"));
        Set<String> result = query.getShouldBeDocsList();
        assertEquals(expected, result);
    }

    @Test
    public void setMustBeDocsListTest() {
        Set<String> expected = new HashSet<>(Arrays.asList("new c4"));
        query.setMustBeDocsList(expected);
        Set<String> result = query.getMustBeDocsList();
        assertEquals(expected, result);
    }

    @Test
    public void setMustNotBeDocsListTest() {
        Set<String> expected = new HashSet<>(Arrays.asList("new c1"));
        query.setMustNotBeDocsList(expected);
        Set<String> result = query.getMustNotBeDocsList();
        assertEquals(expected, result);
    }

    @Test
    public void setShouldBeDocsListTest() {
        Set<String> expected = new HashSet<>(Arrays.asList("new c3"));
        query.setShouldBeDocsList(expected);
        Set<String> result = query.getShouldBeDocsList();
        assertEquals(expected, result);
    }


}
