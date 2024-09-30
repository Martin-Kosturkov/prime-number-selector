package mkosturkov.producer.config;

import io.micronaut.context.annotation.Factory;
import io.micronaut.context.annotation.Value;
import jakarta.inject.Singleton;
import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;

@Factory
public class DistributedLockingConfig {

    @Singleton
    public RedissonClient redissonClient(@Value("${redis.server}") String redisServer) {
        var config = new Config();
        config.useSingleServer().setAddress(redisServer);

        return Redisson.create(config);
    }
}
