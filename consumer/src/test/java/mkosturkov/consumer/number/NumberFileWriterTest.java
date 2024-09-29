package mkosturkov.consumer.number;

import mosturkov.common.FileUtils;
import mosturkov.common.NumberData;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class NumberFileWriterTest {
    private static final String TEST_FILE_NAME = "test_file.csv";

    private final FileUtils fileUtils = mock(FileUtils.class);
    private final NumberFileWriter fileWriter = new NumberFileWriter(fileUtils, TEST_FILE_NAME);

    @Test
    public void createCsvFile() {
        // When
        fileWriter.createCsvFile();

        // Then
        verify(fileUtils).createFile(argThat(file -> TEST_FILE_NAME.equals(file.getName())));
        verify(fileUtils).writeLine(
                argThat(file -> TEST_FILE_NAME.equals(file.getName())),
                eq("generated at,number"));
    }

    @Test
    public void writeToFile_shouldCallFileUtils() {
        // Given
        var generatedAt = LocalDateTime.of(
                LocalDate.of(2024, 1,1),
                LocalTime.of(10, 15, 16));

        var numberData = new NumberData(10, generatedAt);

        // When
        fileWriter.writeToFile(numberData);

        // Then
        verify(fileUtils).writeLine(
                argThat(file -> TEST_FILE_NAME.equals(file.getName())),
                eq("2024-01-01T10:15:16,10"));
    }
}
