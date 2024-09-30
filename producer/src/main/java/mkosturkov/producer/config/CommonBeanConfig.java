package mkosturkov.producer.config;

import io.micronaut.context.annotation.Factory;
import jakarta.inject.Singleton;
import mosturkov.common.FileUtils;

import java.util.Random;

@Factory
public class CommonBeanConfig {

    @Singleton
    public Random random() {
        return new Random();
    }

    @Singleton
    public FileUtils fileUtils() {
        return new FileUtils();
    }
}
