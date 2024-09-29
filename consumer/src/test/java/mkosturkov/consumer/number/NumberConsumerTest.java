package mkosturkov.consumer.number;

import mkosturkov.consumer.number.cache.PrimeNumbersCache;
import mosturkov.common.NumberData;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class NumberConsumerTest {

    private final PrimeNumbersCache primeNumbersCache = mock(PrimeNumbersCache.class);
    private final NumberFileWriter numberFileWriter = mock(NumberFileWriter.class);
    private final NumberConsumer numberConsumer = new NumberConsumer(primeNumbersCache, numberFileWriter);

    @Test
    public void consumerNumberData_whenNumberNotPrime_doNothing() {
        // Given
        var numberData = new NumberData(8, LocalDateTime.now());

        when(primeNumbersCache.isPrime(8)).thenReturn(false);

        // When
        numberConsumer.consumerNumberData(numberData);

        // Then
        verify(primeNumbersCache).isPrime(8);
        verify(numberFileWriter, never()).writeToFile(any());
    }

    @Test
    public void consumerNumberData_whenNumberPrime_callFileWriter() {
        // Given
        var numberData = new NumberData(13, LocalDateTime.now());

        when(primeNumbersCache.isPrime(13)).thenReturn(true);

        // When
        numberConsumer.consumerNumberData(numberData);

        // Then
        verify(numberFileWriter).writeToFile(numberData);
    }
}
