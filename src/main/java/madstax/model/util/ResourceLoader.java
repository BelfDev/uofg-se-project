package madstax.model.util;

import javax.swing.*;
import java.awt.*;
import java.net.URL;

public class ResourceLoader {

    private ResourceLoader() {
    }

    public static ImageIcon getImageIcon(String imageName) {
        String imageRelativePath = String.format("icons/%s.png", imageName);
        URL imageURL = getResourceURL(imageRelativePath);
        return new ImageIcon(imageURL);
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
