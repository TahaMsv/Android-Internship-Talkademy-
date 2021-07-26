import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

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

    public File getDirectory() {
        return directory;
    }
}