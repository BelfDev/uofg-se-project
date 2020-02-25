package madstax.view;

import javax.swing.*;
import java.awt.*;

public class ApplicationWindow extends JFrame {

    public static final int WINDOW_WIDTH = 1000;
    public static final int WINDOW_HEIGHT = 618;

    public ApplicationWindow() {
        setupWindow();
        this.setBackground(Color.gray);
        this.setVisible(true);
    }

    // Configures the JFrame's properties
    private void setupWindow() {
        // Sets the initial window size (golden ratio)
        this.setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
        // Sets the window minimum dimension
        Dimension minimumDimension = new Dimension(WINDOW_WIDTH / 2, WINDOW_HEIGHT / 2);
        this.setMinimumSize(minimumDimension);
        // Sets the window location to the center of the screen
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
    }

}
