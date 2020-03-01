package madstax.model.util;

import com.opencsv.bean.CsvToBeanBuilder;
import com.opencsv.bean.HeaderColumnNameMappingStrategy;
import com.opencsv.bean.StatefulBeanToCsv;
import com.opencsv.bean.StatefulBeanToCsvBuilder;
import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class ResourceIO {

    private ResourceIO() {
    }

    public static ImageIcon getImageIcon(String imageName) {
        try {
            String imageRelativePath = String.format("icons/%s.png", imageName);
            InputStream resource = getResourceStream(imageRelativePath);
            BufferedImage image = ImageIO.read(resource);
            return new ImageIcon(image);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new ImageIcon();
    }

    public static <T> List<T> parseDataFromCSV(String fileName, Class<? extends T> type) {
        String csvFileRelativePath = String.format("csv/%s.csv", fileName);
        InputStream csvStream = getResourceStream(csvFileRelativePath);

        try {
            Reader reader = new InputStreamReader(csvStream, StandardCharsets.UTF_8);

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

        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static <T> void writeDataToCSV(String fileName, List<T> data, Class<? extends T> type) {
        String csvFileRelativePath = String.format("csv/%s.csv", fileName);
        ClassLoader classLoader = ResourceIO.class.getClassLoader();
        URL resourceURL = classLoader.getResource(csvFileRelativePath);

        try {
            assert resourceURL != null;
            Path filePath = Paths.get(resourceURL.toURI());
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
        } catch (CsvRequiredFieldEmptyException | CsvDataTypeMismatchException | IOException | URISyntaxException e) {
            e.printStackTrace();
        }
    }

    private static InputStream getResourceStream(String fileName) {
        ClassLoader classLoader = ResourceIO.class.getClassLoader();
        InputStream resource = classLoader.getResourceAsStream(fileName);

        if (resource == null) {
            throw new IllegalArgumentException("File not found");
        } else {
            return resource;
        }
    }

}
