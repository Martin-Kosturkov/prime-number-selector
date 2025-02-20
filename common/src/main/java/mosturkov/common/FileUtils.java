package mosturkov.common;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class FileUtils {

    /**
     * @return true if a new file was created. False if the file has already exists.
     */
    public boolean createFile(File file) {
        try {
            return file.createNewFile();
        } catch (IOException e) {
            throw new RuntimeException("File with name %s could not be created".formatted(file.getName()), e);
        }
    }

    public void writeLine(File file, String line) {
        try (var fileWriter = new BufferedWriter(new FileWriter(file, true))) {

            fileWriter.write(line);
            fileWriter.newLine();
            fileWriter.flush();

        } catch (Exception e) {
            throw new RuntimeException("Error writing to file %s".formatted(file.getName()), e);
        }
    }
}
