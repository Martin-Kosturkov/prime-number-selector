package mosturkov.common;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Paths;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class FileUtilsTest {
    private static final String TEST_FILE_PATH = Paths.get("").toAbsolutePath()
            + File.separator
            + "test-file.txt";
    private final FileUtils fileUtils = new FileUtils();

    @BeforeClass
    public static void createFile() throws IOException {
        var file = new File(TEST_FILE_PATH);
        file.createNewFile();
    }

    @AfterClass
    public static void deleteFile() {
        var file = new File(TEST_FILE_PATH);
        file.delete();
    }

    @Test
    public void createFile_shouldDeleteFirstAndCreateNewFile() throws IOException {
        // Given
        var file = mock(File.class);

        // When
        fileUtils.createFile(file);

        // Then
        verify(file).createNewFile();
    }

    @Test
    public void writeLine_shouldWriteToFile() {
        // Given
        var testFile = new File(TEST_FILE_PATH);

        // When
        fileUtils.writeLine(testFile, "something");

        // Then
        try (var fileReader = new BufferedReader(new FileReader(testFile))) {
            var firstLine = fileReader.readLine();
            assertEquals("something", firstLine);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
