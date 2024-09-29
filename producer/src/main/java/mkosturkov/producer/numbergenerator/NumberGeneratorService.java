package mkosturkov.producer.numbergenerator;

import io.micronaut.context.annotation.Value;
import io.micronaut.scheduling.annotation.Scheduled;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import mosturkov.common.NumberData;

import java.math.BigInteger;
import java.time.LocalDateTime;
import java.util.Random;

@Singleton
public class NumberGeneratorService {
    private static final int MIN_NUMBER = 2;

    private final NumberFileWriter numberFileWriter;
    private final NumberSender numberSender;
    private final Random random;
    private final long numberGeneratorUpperBound;

    @Inject
    public NumberGeneratorService(
            NumberFileWriter numberFileWriter,
            NumberSender numberSender,
            Random random,
            @Value("${numbers.generator.max-number}") BigInteger numberGeneratorUpperBound) {

        verifyUpperBoundInRange(numberGeneratorUpperBound);

        this.numberFileWriter = numberFileWriter;
        this.numberSender = numberSender;
        this.random = random;
        this.numberGeneratorUpperBound = numberGeneratorUpperBound.longValue();
    }

    @Scheduled(fixedRate = "${numbers.generator.delay}")
    void generateNumbers() {
        var number = random.nextLong(MIN_NUMBER, numberGeneratorUpperBound);
        var numberData = new NumberData(number, LocalDateTime.now());

        numberFileWriter.writeToFile(numberData);
        numberSender.send(numberData);
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
