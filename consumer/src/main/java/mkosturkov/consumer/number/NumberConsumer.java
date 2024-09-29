package mkosturkov.consumer.number;

import io.micronaut.configuration.kafka.annotation.KafkaListener;
import io.micronaut.configuration.kafka.annotation.OffsetReset;
import io.micronaut.configuration.kafka.annotation.Topic;

@KafkaListener(offsetReset = OffsetReset.EARLIEST)
public class NumberConsumer {

    private final NumberService numberService;

    public NumberConsumer(NumberService numberService) {
        this.numberService = numberService;
    }

    @Topic("${numbers.kafka.topic-name}")
    public void consumerNumberData(NumberData numberData) {

        if (numberService.isPrime(numberData.getNumber())) {

        }
    }
}
