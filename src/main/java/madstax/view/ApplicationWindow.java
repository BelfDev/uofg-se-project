package madstax.view;

import madstax.controller.navigation.NavigationBarListener;

import javax.swing.*;
import java.awt.*;

public class ApplicationWindow extends JFrame {

    public static final int INITIAL_WINDOW_WIDTH = 1000;
    public static final int INITIAL_WINDOW_HEIGHT = 618;

    private NavigationBar navigationBar;
    private JPanel contentContainer;

    public ApplicationWindow() {
        setupWindow();
        setupContainers();
        this.setBackground(Color.gray);
        this.setVisible(true);
    }

    // Configures the JFrame's properties
    private void setupWindow() {
        // Sets the initial window size (golden ratio)
        this.setSize(INITIAL_WINDOW_WIDTH, INITIAL_WINDOW_HEIGHT);
        // Sets the window minimum dimension
        Dimension minimumDimension = new Dimension(INITIAL_WINDOW_WIDTH / 2, INITIAL_WINDOW_HEIGHT / 2);
        this.setMinimumSize(minimumDimension);
        // Sets the window location to the center of the screen
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
    }

    private void setupContainers() {
        navigationBar = new NavigationBar();
        contentContainer = new JPanel();
        add(navigationBar, BorderLayout.PAGE_START);
        add(contentContainer, BorderLayout.CENTER);
    }

    public void attachContent(Screen screen) {
        remove(contentContainer);
        contentContainer = screen;
        add(contentContainer, BorderLayout.CENTER);
        revalidate();
        repaint();
    }

    public void updateNavigationBar(String screenTitle, boolean isRootScreen) {
        navigationBar.setTitleLabelText(screenTitle);
        navigationBar.setBackButtonVisibility(!isRootScreen);
        revalidate();
        repaint();
    }

    public void setNavigationListener(NavigationBarListener listener) {
        navigationBar.setListener(listener);
    }

    public void setNavigationBarSubtitle(String text) {
        navigationBar.addSubtitleItem(text);
    }

    public void setNavigationBarRightButtonTitle(String title) {
        navigationBar.addRightButtonItem(title);
    }

    public void clearNavigationBar() {
        navigationBar.setListener(null);
        navigationBar.removeBarItems();
    }

}
