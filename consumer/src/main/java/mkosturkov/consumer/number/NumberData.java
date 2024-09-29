package mkosturkov.consumer.number;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

public class NumberData {
    private long number;
    private LocalDateTime generatedAt;

    public long getNumber() {
        return number;
    }

    public LocalDateTime getGeneratedAtTruncatedToMillis() {
        return generatedAt.truncatedTo(ChronoUnit.MILLIS);
    }
}
