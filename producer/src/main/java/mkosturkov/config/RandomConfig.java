package mkosturkov.config;

import io.micronaut.context.annotation.Factory;
import io.micronaut.context.annotation.Primary;
import jakarta.inject.Singleton;

import java.util.Random;

@Factory
public class RandomConfig {

    @Singleton
    @Primary
    public Random random() {
        return new Random();
    }
}
