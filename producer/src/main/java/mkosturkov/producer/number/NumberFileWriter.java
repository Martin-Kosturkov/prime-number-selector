package mkosturkov.producer.number;

import io.micronaut.context.annotation.Value;
import jakarta.annotation.PostConstruct;
import jakarta.inject.Singleton;
import mosturkov.common.FileUtils;
import mosturkov.common.NumberData;
import org.redisson.api.RedissonClient;

import java.io.File;

@Singleton
public class NumberFileWriter {
    private final FileUtils fileUtils;
    private final RedissonClient redissonClient;
    private final String fileName;

    public NumberFileWriter(
            FileUtils fileUtils,
            RedissonClient redissonClient,
            @Value("${numbers.output-file-name}") String fileName) {

        this.fileUtils = fileUtils;
        this.redissonClient = redissonClient;
        this.fileName = fileName;
    }

    @PostConstruct
    public void createCsvFile() {
        var file = getCsvFile();
        var isNewFile = fileUtils.createFile(file);

        if (isNewFile) {
            fileUtils.writeLine(file, "generated at,number");
        }
    }

    public void writeToFile(NumberData numberData) {
        var csvFile = getCsvFile();

        var generatedAt = numberData.getGeneratedAt();
        var csvRow = "%s,%s".formatted(generatedAt, numberData.getNumber());

        var synchronizationLock = redissonClient.getFairLock(fileName);

        try {
            synchronizationLock.lock();
            fileUtils.writeLine(csvFile, csvRow);
        } finally {
            synchronizationLock.unlock();
        }
    }

    private File getCsvFile() {
        return new File(new File(fileName).getAbsolutePath());
    }
}
