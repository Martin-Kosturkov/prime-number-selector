package mkosturkov.producer.number;

import org.junit.jupiter.api.Test;

import java.math.BigInteger;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class NumberGeneratorServiceTest {

    private final NumberFileWriter numberFileWriter = mock(NumberFileWriter.class);
    private final NumberSender numberSender = mock(NumberSender.class);
    private final Random random = mock(Random.class);

    private final NumberGeneratorService numberGeneratorService = new NumberGeneratorService(
            numberFileWriter,
            numberSender,
            random,
            BigInteger.TEN);

    @Test
    public void initializingService_whenUpperBoundLessThan3_throwException() {
        // When & Then
        assertThrows(
                IllegalArgumentException.class,
                () -> new NumberGeneratorService(numberFileWriter, numberSender, random, BigInteger.TWO));
    }

    @Test
    public void initializingService_whenUpperBoundBiggerThanLongMaxValue_throwException() {
        // When & Then
        assertThrows(
                IllegalArgumentException.class,
                () -> new NumberGeneratorService(
                        numberFileWriter, numberSender, random, new BigInteger("9223372036854775808")));
    }

    @Test
    public void generateNumber_shouldWriteToFileAndSendGeneratedNumber() {
        // Given
        when(random.nextLong(2, 10)).thenReturn(5L);

        // When
        numberGeneratorService.generateNumber();

        // Then
        verify(numberFileWriter).writeToFile(argThat(numberData -> numberData.getNumber() == 5L));
        verify(numberSender).send(argThat(numberData -> numberData.getNumber() == 5L));
    }
}
