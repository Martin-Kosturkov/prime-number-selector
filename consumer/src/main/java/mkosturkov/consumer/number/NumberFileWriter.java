package mkosturkov.consumer.number;

import io.micronaut.context.annotation.Value;
import jakarta.annotation.PostConstruct;
import jakarta.inject.Singleton;
import mosturkov.common.FileUtils;
import mosturkov.common.NumberData;

import java.io.File;

@Singleton
public class NumberFileWriter {

    private final FileUtils fileUtils;
    private final String fileName;

    public NumberFileWriter(
            FileUtils fileUtils,
            @Value("${numbers.output-file-name}") String fileName) {

        this.fileUtils = fileUtils;
        this.fileName = fileName;
    }

    @PostConstruct
    public void createCsvFile() {
        var file = getCsvFile();
        fileUtils.createFile(file);
        fileUtils.writeLine(file, "generated at,number");
    }

    public void writeToFile(NumberData numberData) {
        var csvFile = getCsvFile();

        var generatedAt = numberData.getGeneratedAt();
        var csvRow = "%s,%s".formatted(generatedAt, numberData.getNumber());

        fileUtils.writeLine(csvFile, csvRow);
    }

    private File getCsvFile() {
        return new File(new File(fileName).getAbsolutePath());
    }
}
