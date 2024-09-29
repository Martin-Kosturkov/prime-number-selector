package mkosturkov.number;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class NumberData {
    private long number;
    private LocalDateTime generatedAt;
}
