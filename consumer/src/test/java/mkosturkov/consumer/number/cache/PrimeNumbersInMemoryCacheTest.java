package mkosturkov.consumer.number.cache;

import io.micronaut.core.reflect.ReflectionUtils;
import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class PrimeNumbersInMemoryCacheTest {

    private final PrimeNumbersInMemoryCache inMemoryCache = new PrimeNumbersInMemoryCache();

    @Test
    @SuppressWarnings("unchecked")
    public void loadCacheAsync_shouldLoadNumbersInCache() throws InterruptedException {
        // When
        inMemoryCache.loadCacheAsync(10);
        Thread.sleep(Duration.ofSeconds(1));

        // Then
        var cacheMaxCalculatedNumber = (long) ReflectionUtils.getFieldValue(
                PrimeNumbersInMemoryCache.class, "cacheMaxCalculatedNumber", inMemoryCache).get();

        var primeNumbersCache = (Set<Long>) ReflectionUtils.getFieldValue(
                PrimeNumbersInMemoryCache.class, "primeNumbersCache", inMemoryCache).get();

        assertEquals(10, cacheMaxCalculatedNumber);
        assertEquals(primeNumbersCache, Set.of(2L, 3L, 5L, 7L));
    }

    @Test
    public void isPrime_whenNumbersInCacheAndPrime_returnTrue() {
        // Given
        ReflectionUtils.setField(PrimeNumbersInMemoryCache.class, "cacheMaxCalculatedNumber", inMemoryCache,
                10);

        addValuesInCache();

        // When
        var isPrime = inMemoryCache.isPrime(5);

        // Then
        assertTrue(isPrime);
    }

    @Test
    public void isPrime_whenNumbersInCacheButNotPrime_returnFalse() {
        // Given
        ReflectionUtils.setField(PrimeNumbersInMemoryCache.class, "cacheMaxCalculatedNumber", inMemoryCache,
                10);

        addValuesInCache();

        // When
        var isPrime = inMemoryCache.isPrime(4);

        // Then
        assertFalse(isPrime);
    }

    @Test
    public void isPrime_whenCacheEmptyAndNumberPrime_returnTrue() {
        // When
        var isPrime = inMemoryCache.isPrime(53);

        // Then
        assertTrue(isPrime);
    }

    @Test
    public void isPrime_whenCacheEmptyAndNumberNotPrime_returnFalse() {
        // When
        var isPrime = inMemoryCache.isPrime(102);

        // Then
        assertFalse(isPrime);
    }

    @SuppressWarnings("unchecked")
    private void addValuesInCache() {
        var cachedPrimeNumbers = (Set<Long>) ReflectionUtils.getFieldValue(
                PrimeNumbersInMemoryCache.class, "primeNumbersCache", inMemoryCache).get();

        cachedPrimeNumbers.addAll(Set.of(2L, 3L, 5L, 7L));
    }
}
