package mkosturkov.producer.numbergenerator;

import jakarta.annotation.PostConstruct;
import jakarta.inject.Singleton;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.temporal.ChronoUnit;

@Singleton
public class NumberFileWriter {
    private static final String FILE_NAME = "generated_numbers.csv";

    public void writeToFile(GeneratedNumberData numberData) {
        var csvFile = getCsvFile();

        var generatedAt = numberData.generatedAt().truncatedTo(ChronoUnit.MILLIS);
        var csvRow = "%s,%s".formatted(generatedAt, numberData.number());
        writeToFile(csvFile, csvRow);
    }

    @PostConstruct
    public void createCsvFile() {
        var file = getCsvFile();

        file.deleteOnExit();
        try {
            file.createNewFile();
        } catch (IOException e) {
            throw new RuntimeException("File with name %s could not be created".formatted(FILE_NAME), e);
        }

        writeToFile(file, "generated at,number");
    }

    private static File getCsvFile() {
        return new File(new File(FILE_NAME).getAbsolutePath());
    }

    private static void writeToFile(File file, String row) {
        try (var fileWriter = new BufferedWriter(new FileWriter(file, true))) {

            fileWriter.write(row);
            fileWriter.newLine();
            fileWriter.flush();

        } catch (Exception e) {
            throw new RuntimeException("Error writing to file %s".formatted(FILE_NAME), e);
        }
    }
}
