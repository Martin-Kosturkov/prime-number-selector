package mkosturkov.consumer.number;

import io.micronaut.configuration.kafka.annotation.KafkaListener;
import io.micronaut.configuration.kafka.annotation.OffsetReset;
import io.micronaut.configuration.kafka.annotation.Topic;
import mkosturkov.consumer.number.cache.PrimeNumbersCache;
import mkosturkov.consumer.number.cache.PrimeNumbersInMemoryCache;
import mosturkov.common.NumberData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@KafkaListener(groupId = "number-consumer", offsetReset = OffsetReset.EARLIEST)
public class NumberConsumer {
    private static final Logger logger = LoggerFactory.getLogger(NumberConsumer.class);

    private final PrimeNumbersCache primeNumbersCache;
    private final NumberFileWriter numberFileWriter;

    public NumberConsumer(
            PrimeNumbersCache primeNumbersCache,
            NumberFileWriter numberFileWriter) {

        this.primeNumbersCache = primeNumbersCache;
        this.numberFileWriter = numberFileWriter;
    }

    @Topic("${numbers.kafka.topic-name}")
    public void consumerNumberData(NumberData numberData) {
        try {
            if (primeNumbersCache.isPrime(numberData.getNumber())) {
                numberFileWriter.writeToFile(numberData);
            }
        } catch (Exception e) {
            logger.error("Error consuming and processing number data: {}", numberData, e);
        }
    }
}
