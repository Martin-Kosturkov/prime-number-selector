package mkosturkov.consumer.number;

import mosturkov.common.FileUtils;
import mosturkov.common.NumberData;
import org.junit.jupiter.api.Test;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class NumberFileWriterTest {
    private static final String TEST_FILE_NAME = "test_file.csv";

    private final FileUtils fileUtils = mock(FileUtils.class);
    private final RedissonClient redissonClient = mock(RedissonClient.class);
    private final NumberFileWriter fileWriter = new NumberFileWriter(fileUtils, redissonClient, TEST_FILE_NAME);

    @Test
    public void createCsvFile_whenNewFileCreated_writeInitialLine() {
        // Given
        when(fileUtils.createFile(argThat(file -> TEST_FILE_NAME.equals(file.getName()))))
                .thenReturn(true);

        // When
        fileWriter.createCsvFile();

        // Then
        verify(fileUtils).createFile(argThat(file -> TEST_FILE_NAME.equals(file.getName())));
        verify(fileUtils).writeLine(
                argThat(file -> TEST_FILE_NAME.equals(file.getName())),
                eq("generated at,number"));
    }

    @Test
    public void createCsvFile_whenFileAlreadyExist_skipWritingInitialLine() {
        // Given
        when(fileUtils.createFile(argThat(file -> TEST_FILE_NAME.equals(file.getName()))))
                .thenReturn(false);

        // When
        fileWriter.createCsvFile();

        // Then
        verify(fileUtils).createFile(argThat(file -> TEST_FILE_NAME.equals(file.getName())));
        verify(fileUtils, never()).writeLine(any(), any());
    }

    @Test
    public void writeToFile_shouldCallFileUtils_andEventuallyUnlockSynchronization() {
        // Given
        var generatedAt = LocalDateTime.of(
                LocalDate.of(2024, 1,1),
                LocalTime.of(10, 15, 16));

        var numberData = new NumberData(10, generatedAt);

        var mockedLock = mock(RLock.class);
        when(redissonClient.getFairLock(TEST_FILE_NAME)).thenReturn(mockedLock);

        // When
        fileWriter.writeToFile(numberData);

        // Then
        verify(fileUtils).writeLine(
                argThat(file -> TEST_FILE_NAME.equals(file.getName())),
                eq("2024-01-01T10:15:16,10"));

        verify(mockedLock).lock();
        verify(mockedLock).unlock();
    }

    @Test
    public void writeToFile_whenWritingToFileThrowException_unlockSynchronization() {
        // Given
        var numberData = new NumberData(10, LocalDateTime.now());

        var mockedLock = mock(RLock.class);
        when(redissonClient.getFairLock(TEST_FILE_NAME)).thenReturn(mockedLock);
        doThrow(new RuntimeException())
                .when(fileUtils).writeLine(any(), any());

        // When
        assertThrows(
                RuntimeException.class,
                () -> fileWriter.writeToFile(numberData));

        // Then
        verify(mockedLock).lock();
        verify(mockedLock).unlock();
    }
}
