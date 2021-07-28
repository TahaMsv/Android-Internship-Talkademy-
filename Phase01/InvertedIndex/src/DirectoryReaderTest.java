import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import static org.junit.Assert.assertEquals;

public class DirectoryReaderTest {

    public DirectoryReader directoryReader = new DirectoryReader("");
    File rootDirectory;
    File file;
    File file2;
    File file3;
    @Rule
    public TemporaryFolder folder = new TemporaryFolder();


    @Before
    public void createFile() throws IOException {
        rootDirectory = folder.newFolder("root");
        file = new File(rootDirectory, "testFile1.txt");
        file2 = new File(rootDirectory, "testFile2.txt");
        file3 = new File(rootDirectory, "testFile3.txt");

        if (!file2.exists()) {
            file2.createNewFile();
        }
        if (!file.exists()) {
            file.createNewFile();
        }
        FileWriter fileWriter = new FileWriter(file);
        FileWriter fileWriter2 = new FileWriter(file2);
        fileWriter.write("This is a test");
        fileWriter2.write("another test");
        fileWriter.close();
        fileWriter2.close();
        file3.delete();
    }

    @Test
    public void readFileContentTest() {
        String result = directoryReader.readFileContent(file);
        String expected = "this is a test";
        assertEquals(expected, result);

        result = directoryReader.readFileContent(file3);
        expected = "";
        assertEquals(expected, result);

        result = directoryReader.readFileContent(file2);
        expected = "another test";
        assertEquals(expected, result);
    }

    @Test
    public void getFileListTest() {
        directoryReader.setDirectory(rootDirectory);
        File[] result = directoryReader.getFilesList();
        int expected = 2;
        assertEquals(expected, result.length);

    }

    @Test
    public void setFileListTest() {
        directoryReader.setDirectory(rootDirectory);
        assertEquals(rootDirectory, directoryReader.getDirectory());

    }

//    @Test
//    public void getDirectoryTest() {
//        File result = directoryReader.getDirectory();
//        File expected = rootDirectory;
//        assertEquals(expected, result);
//
//    }

    @After
    public void tearDown() throws IOException {
        directoryReader = null;
    }

}
