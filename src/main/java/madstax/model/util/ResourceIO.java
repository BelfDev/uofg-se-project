package madstax.model.util;

import com.opencsv.bean.CsvToBeanBuilder;
import com.opencsv.bean.HeaderColumnNameMappingStrategy;
import com.opencsv.bean.StatefulBeanToCsv;
import com.opencsv.bean.StatefulBeanToCsvBuilder;
import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;

import javax.swing.*;
import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Comparator;
import java.util.List;

public class ResourceIO {

    private ResourceIO() {
    }

    public static ImageIcon getImageIcon(String imageName) {
        String imageRelativePath = String.format("icons/%s.png", imageName);
        URL imageURL = getResourceURL(imageRelativePath);
        return new ImageIcon(imageURL);
    }

    public static <T> List<T> parseDataFromCSV(String fileName, Class<? extends T> type) {
        String csvFileRelativePath = String.format("csv/%s.csv", fileName);
        URL csvURL = getResourceURL(csvFileRelativePath);

        try {
            Path filePath = Paths.get(csvURL.toURI());
            Reader reader = Files.newBufferedReader(filePath);

            HeaderColumnNameMappingStrategy<T> strategy = new HeaderColumnNameMappingStrategy<T>();
            strategy.setType(type);

            List<T> data = new CsvToBeanBuilder<T>(reader)
                    .withType(type)
                    .withIgnoreLeadingWhiteSpace(true)
                    .withMappingStrategy(strategy)
                    .build()
                    .parse();

            reader.close();
            return data;

        } catch (URISyntaxException | IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static <T> void writeDataToCSV(String fileName, List<T> data, Class<? extends T> type) {
        String csvFileRelativePath = String.format("csv/%s.csv", fileName);
        URL csvURL = getResourceURL(csvFileRelativePath);

        try {
            Path filePath = Paths.get(csvURL.toURI());
            Writer writer = Files.newBufferedWriter(filePath);

            HeaderColumnNameMappingStrategy<T> strategy = new HeaderColumnNameMappingStrategy<>();
            strategy.setType(type);
            strategy.setColumnOrderOnWrite(new ClassFieldOrderComparator(type));

            StatefulBeanToCsv<T> beanToCsv = new StatefulBeanToCsvBuilder<T>(writer)
                    .withApplyQuotesToAll(false)
                    .withMappingStrategy(strategy)
                    .build();
            beanToCsv.write(data);

            writer.flush();
            writer.close();
        } catch (CsvRequiredFieldEmptyException | CsvDataTypeMismatchException | URISyntaxException | IOException e) {
            e.printStackTrace();
        }
    }

    private static URL getResourceURL(String fileName) {
        ClassLoader classLoader = ResourceIO.class.getClassLoader();
        URL resourceURL = classLoader.getResource(fileName);
        if (resourceURL == null) {
            throw new IllegalArgumentException("File not found");
        } else {
            return classLoader.getResource(fileName);
        }
    }

}
