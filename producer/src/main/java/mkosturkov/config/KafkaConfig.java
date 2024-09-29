package mkosturkov.config;

import io.micronaut.context.annotation.Bean;
import io.micronaut.context.annotation.Factory;
import io.micronaut.context.annotation.Value;
import org.apache.kafka.clients.admin.NewTopic;

@Factory
public class KafkaConfig {

    @Bean
    public NewTopic numbersTopic(@Value("${numbers.kafka.topic-name}") String topicName) {
        return new NewTopic(topicName, 5, (short) 1);
    }
}
