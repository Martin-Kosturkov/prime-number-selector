package mkosturkov.consumer.number;

import io.micronaut.configuration.kafka.annotation.KafkaListener;
import io.micronaut.configuration.kafka.annotation.OffsetReset;
import io.micronaut.configuration.kafka.annotation.Topic;
import mosturkov.common.NumberData;

@KafkaListener(offsetReset = OffsetReset.EARLIEST)
public class NumberConsumer {

    private final NumberService numberService;
    private final NumberFileWriter numberFileWriter;

    public NumberConsumer(
            NumberService numberService,
            NumberFileWriter numberFileWriter) {

        this.numberService = numberService;
        this.numberFileWriter = numberFileWriter;
    }

    @Topic("${numbers.kafka.topic-name}")
    public void consumerNumberData(NumberData numberData) {

        if (numberService.isPrime(numberData.getNumber())) {
            numberFileWriter.writeToFile(numberData);
        }
    }
}
