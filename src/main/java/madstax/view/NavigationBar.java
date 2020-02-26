package madstax.view;

import madstax.controller.navigation.NavigationListener;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class NavigationBar extends JPanel {

    private static final int NAV_BAR_HEIGHT = 44;
    private static final int NUMBER_OF_ITEMS = 3;
    private static final int TITLE_SIZE = 16;

    protected static final Color NAV_BAR_COLOR = new Color(129, 212, 250);

    private ArrayList<JPanel> navBarSlots;

    public NavigationBar() {
        // Sets the default navigation bar height
        Dimension size = new Dimension(0, NAV_BAR_HEIGHT);
        setPreferredSize(size);
        // Sets the layout manager to a GridLayout
        GridLayout gridLayout = new GridLayout(0, 3);
        setLayout(gridLayout);

        // Sets the inner padding
        setBorder(new EmptyBorder(4, 8, 4, 8));
        setBackground(NAV_BAR_COLOR);

        // Creates the navigation bar components
        createNavBarSlots();
        createNavBarDefaultItems();

        setVisible(true);
    }

    private void createNavBarSlots() {
        this.navBarSlots = new ArrayList<>();
        Color transparentColor = new Color(0, 0, 0, 0);
        for (int i = 0; i < NUMBER_OF_ITEMS; i++) {
            JPanel itemContainer = new JPanel(new BorderLayout());
            itemContainer.setBackground(transparentColor);
            navBarSlots.add(itemContainer);
            add(navBarSlots.get(i));
        }
    }

    private void createNavBarDefaultItems() {
        NavigationButton backButton = new NavigationButton("back-arrow", "BACK");
        backButton.setAlignmentX(SwingConstants.LEFT);
        backButton.setVisible(false);

        JLabel titleLabel = new JLabel("Role Selection");
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        // Sets the screen title label style to BOLD
        Font f = titleLabel.getFont();
        titleLabel.setFont(new Font(f.getName(), Font.BOLD, TITLE_SIZE));

        navBarSlots.get(0).add(backButton, BorderLayout.WEST);
        navBarSlots.get(1).add(titleLabel, BorderLayout.CENTER);
    }

    private JButton getBackButton() {
        return (JButton) navBarSlots.get(0).getComponent(0);
    }

    private JLabel getTitleLabel() {
        return (JLabel) navBarSlots.get(1).getComponent(0);
    }

    public void setBackButtonVisibility(boolean visible) {
        JButton backButton = getBackButton();
        backButton.setEnabled(visible);
        backButton.setVisible(visible);
    }

    public void setTitleLabelText(String text) {
        JLabel titleLabel = getTitleLabel();
        titleLabel.setText(text);
    }

    public void setListener(NavigationListener listener) {
        ActionListener[] listeners = getBackButton().getActionListeners();
        if (listeners.length > 0) {
            getBackButton().removeActionListener(listeners[0]);
        }
        if (listener != null) {
            getBackButton().addActionListener(listener::onBackButtonClicked);
        }
    }

}
