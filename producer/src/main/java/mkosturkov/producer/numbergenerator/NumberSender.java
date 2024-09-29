package mkosturkov.producer.numbergenerator;

import io.micronaut.configuration.kafka.annotation.KafkaClient;
import io.micronaut.context.annotation.Value;
import jakarta.inject.Singleton;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;

@Singleton
public class NumberSender {

    private final Producer<?, GeneratedNumberData> producer;
    private final String topicName;

    public NumberSender(
            @KafkaClient Producer<?, GeneratedNumberData> producer,
            @Value("${numbers.kafka.topic-name}") String topicName) {

        this.producer = producer;
        this.topicName = topicName;
    }

    void send(GeneratedNumberData generatedNumberData) {
        producer.send(new ProducerRecord<>(topicName, generatedNumberData));
    }
}
