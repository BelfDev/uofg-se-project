package madstax.model.util;

import com.opencsv.bean.CsvToBeanBuilder;

import javax.swing.*;
import java.io.IOException;
import java.io.Reader;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class ResourceLoader {

    private ResourceLoader() {
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

            return new CsvToBeanBuilder<T>(reader)
                    .withType(type)
                    .withIgnoreLeadingWhiteSpace(true)
                    .build()
                    .parse();

        } catch (URISyntaxException | IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private static URL getResourceURL(String fileName) {
        ClassLoader classLoader = ResourceLoader.class.getClassLoader();
        URL resourceURL = classLoader.getResource(fileName);
        if (resourceURL == null) {
            throw new IllegalArgumentException("File not found");
        } else {
            return classLoader.getResource(fileName);
        }
    }

}
