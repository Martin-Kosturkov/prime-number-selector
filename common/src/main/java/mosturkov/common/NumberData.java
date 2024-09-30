package mosturkov.common;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

@AllArgsConstructor
@NoArgsConstructor
@ToString
public class NumberData {

    @Getter
    private long number;

    private LocalDateTime generatedAt;

    public LocalDateTime getGeneratedAt() {
        return generatedAt.truncatedTo(ChronoUnit.MILLIS);
    }
}
