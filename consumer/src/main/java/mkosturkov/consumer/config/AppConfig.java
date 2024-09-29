package mkosturkov.consumer.config;

import io.micronaut.context.annotation.Factory;
import jakarta.inject.Singleton;
import mosturkov.common.FileUtils;

@Factory
public class AppConfig {

    @Singleton
    public FileUtils fileUtils() {
        return new FileUtils();
    }
}
