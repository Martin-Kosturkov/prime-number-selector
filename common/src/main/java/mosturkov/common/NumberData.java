package mosturkov.common;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

@AllArgsConstructor
@NoArgsConstructor
public class NumberData {

    @Getter
    private long number;

    private LocalDateTime generatedAt;

    public LocalDateTime getGeneratedAt() {
        return generatedAt.truncatedTo(ChronoUnit.MILLIS);
    }
}
