package mkosturkov.producer.numbergenerator;

import io.micronaut.serde.annotation.Serdeable;

import java.time.LocalDateTime;

@Serdeable
public record GeneratedNumberData(long number, LocalDateTime generatedAt) {
}
