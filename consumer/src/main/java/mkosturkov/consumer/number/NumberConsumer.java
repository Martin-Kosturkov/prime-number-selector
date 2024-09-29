package mkosturkov.consumer.number;

import io.micronaut.configuration.kafka.annotation.KafkaListener;
import io.micronaut.configuration.kafka.annotation.OffsetReset;
import io.micronaut.configuration.kafka.annotation.Topic;
import mkosturkov.consumer.number.cache.PrimeNumbersCache;
import mosturkov.common.NumberData;

@KafkaListener(offsetReset = OffsetReset.EARLIEST)
public class NumberConsumer {

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
        if (primeNumbersCache.isPrime(numberData.getNumber())) {
            numberFileWriter.writeToFile(numberData);
        }
    }
}
