package mkosturkov.numbergenerator;

import io.micronaut.context.annotation.Value;
import io.micronaut.scheduling.annotation.Scheduled;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;

import java.math.BigInteger;
import java.util.Random;

@Singleton
public class NumberGeneratorService {
    private static final int MIN_NUMBER = 2;

    private final Random random;
    private final long numberGeneratorUpperBound;

    @Inject
    public NumberGeneratorService(
            Random random,
            @Value("${number.generator.max-number}") BigInteger numberGeneratorUpperBound) {

        verifyUpperBoundInRange(numberGeneratorUpperBound);

        this.random = random;
        this.numberGeneratorUpperBound = numberGeneratorUpperBound.longValue();
    }

    @Scheduled(fixedRate = "${number.generator.delay}")
    void generateNumbers() {
        var number = random.nextLong(MIN_NUMBER, numberGeneratorUpperBound);
    }

    private void verifyUpperBoundInRange(BigInteger numberGeneratorUpperBound) {
        // TODO: make boundaries configurable
        var upperBound = BigInteger.valueOf(Long.MAX_VALUE);
        var lowerBound = BigInteger.valueOf(MIN_NUMBER);

        if (numberGeneratorUpperBound.compareTo(upperBound) > 0 ||
                numberGeneratorUpperBound.compareTo(lowerBound) <= 0) {

            throw new IllegalArgumentException("Number generator upper bound must be bigger than %s and smaller than %s"
                    .formatted(MIN_NUMBER, Long.MAX_VALUE));
        }
    }
}
