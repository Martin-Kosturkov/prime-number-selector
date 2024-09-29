package mkosturkov.producer.numbergenerator;

import jakarta.annotation.PostConstruct;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import mosturkov.common.FileUtils;
import mosturkov.common.NumberData;

import java.io.File;

@Singleton
public class NumberFileWriter {
    private static final String FILE_NAME = "generated_numbers.csv";
    private final FileUtils fileUtils;

    @Inject
    public NumberFileWriter(FileUtils fileUtils) {
        this.fileUtils = fileUtils;
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

    private static File getCsvFile() {
        return new File(new File(FILE_NAME).getAbsolutePath());
    }
}
