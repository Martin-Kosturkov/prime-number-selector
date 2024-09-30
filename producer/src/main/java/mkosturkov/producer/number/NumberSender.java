package mkosturkov.producer.number;

import io.micronaut.configuration.kafka.annotation.KafkaClient;
import io.micronaut.configuration.kafka.annotation.Topic;
import mosturkov.common.NumberData;

@KafkaClient
public interface NumberSender {

    @Topic("${numbers.kafka.topic-name}")
    void send(NumberData generatedNumberData);
}
