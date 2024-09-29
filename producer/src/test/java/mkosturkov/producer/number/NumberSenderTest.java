package mkosturkov.producer.number;

import mosturkov.common.NumberData;
import org.apache.kafka.clients.producer.Producer;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class NumberSenderTest {
    private static final String TOPIC_NAME = "test-topic";

    private final Producer<?, NumberData> producer = mock(Producer.class);
    private final NumberSender numberSender = new NumberSender(producer, TOPIC_NAME);

    @Test
    public void send_shouldCallProducer() {
        // Given
        var numberData = new NumberData(13L, LocalDateTime.now());

        // When
        numberSender.send(numberData);

        // Then
        verify(producer.send(argThat(producerRecord ->
                TOPIC_NAME.equals(producerRecord.topic())
                        && numberData.equals(producerRecord.value()))));
    }
}
