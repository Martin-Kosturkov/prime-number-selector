package mkosturkov.consumer.number.cache;

import io.micronaut.context.annotation.Value;
import jakarta.annotation.PostConstruct;
import jakarta.inject.Singleton;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashSet;
import java.util.Set;

@Singleton
public class PrimeNumbersInMemoryCache implements PrimeNumbersCache {
    private static final Logger logger = LoggerFactory.getLogger(PrimeNumbersInMemoryCache.class);
    private static final Set<Long> primeNumbersCache = new HashSet<>();
    private static long cacheMaxCalculatedNumber = 1;

    @PostConstruct
    public void loadCacheAsync(@Value("${numbers.max-number-allowed}") long maxAllowedNumber) {
        new Thread(() -> {
            try {
                for (long number = 2; number <= maxAllowedNumber; number++) {
                    if (isPrimeInternal(number)) {
                        primeNumbersCache.add(number);
                    }

                    cacheMaxCalculatedNumber = number;
                }

                cacheMaxCalculatedNumber = maxAllowedNumber;
            } catch (Exception e) {
                logger.error("Error loading in memory cache for prime numbers {}", e.getMessage(), e);
            }
        }).start();
    }

    public boolean isPrime(long number) {
        if (number <= cacheMaxCalculatedNumber) {
            return primeNumbersCache.contains(number);
        }

        return isPrimeInternal(number);
    }

    private static boolean isPrimeInternal(long number) {
        for (var i = 2; i <= number / 2; i++) {

            if (number % i == 0) {
                return false;
            }
        }

        return true;
    }
}
