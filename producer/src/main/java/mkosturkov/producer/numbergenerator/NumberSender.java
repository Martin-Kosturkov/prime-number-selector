package mkosturkov.producer.numbergenerator;

import io.micronaut.configuration.kafka.annotation.KafkaClient;
import io.micronaut.context.annotation.Value;
import jakarta.inject.Singleton;
import mosturkov.common.NumberData;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;

@Singleton
public class NumberSender {

    private final Producer<?, NumberData> producer;
    private final String topicName;

    public NumberSender(
            @KafkaClient Producer<?, NumberData> producer,
            @Value("${numbers.kafka.topic-name}") String topicName) {

        this.producer = producer;
        this.topicName = topicName;
    }

    void send(NumberData generatedNumberData) {
        producer.send(new ProducerRecord<>(topicName, generatedNumberData));
    }
}
