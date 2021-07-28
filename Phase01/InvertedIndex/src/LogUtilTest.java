import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.CoreMatchers.containsString;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

public class LogUtilTest {

    private final PrintStream standardOut = System.out;
    private final ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();

    @Before
    public void setUp() {
        System.setOut(new PrintStream(outputStreamCaptor));
    }

    @Test
    public void logStringTest() {
        String s = "This is a test";
        ByteArrayOutputStream sink = new ByteArrayOutputStream();
        System.setOut(new PrintStream(sink, true));
        LogUtil.logString(s);
        assertThat(new String(sink.toByteArray()), containsString(s));
    }

    @Test
    public void logErrorTest() {
        String expected = "Error: This is a test";
        ByteArrayOutputStream sink = new ByteArrayOutputStream();
        System.setOut(new PrintStream(sink, true));
        LogUtil.logError("This is a test");
        assertThat(new String(sink.toByteArray()), containsString(expected));
    }

    @Test
    public void logIntTest() {
        LogUtil.logInt(2);

        assertEquals(String.valueOf(2), outputStreamCaptor.toString()
                .trim());
    }

    @Test
    public  void logDoubleTest() {
        LogUtil.logDouble(2.6);
        assertEquals(String.valueOf(2.6), outputStreamCaptor.toString()
                .trim());
    }

    @Test
    public void logDocListTest(){
        List<String> list1=new ArrayList<>();
        String s = "No document found.";
        ByteArrayOutputStream sink = new ByteArrayOutputStream();
        System.setOut(new PrintStream(sink, true));
        LogUtil.logDocList(list1);
        assertThat(new String(sink.toByteArray()), containsString(s));

        List<String> list2=new ArrayList<>(Arrays.asList("test"));
         s = "1 - test";
        sink = new ByteArrayOutputStream();
        System.setOut(new PrintStream(sink, true));
        LogUtil.logDocList(list2);
        assertThat(new String(sink.toByteArray()), containsString(s));
    }



    @After
    public void tearDown() {
        System.setOut(standardOut);
    }
}
