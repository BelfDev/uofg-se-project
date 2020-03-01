package madstax.view;

import com.sun.istack.internal.NotNull;
import madstax.controller.listener.NavigationBarListener;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class NavigationBar extends JPanel {

    private static final int NAV_BAR_HEIGHT = 44;
    private static final int NUMBER_OF_ITEMS = 3;
    private static final int TITLE_SIZE = 16;
    private static final int SUBTITLE_SIZE = 12;

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
        for (int i = 0; i < NUMBER_OF_ITEMS; i++) {
            JPanel itemContainer = new JPanel(new BorderLayout());
            itemContainer.setBackground(NAV_BAR_COLOR);
            navBarSlots.add(itemContainer);
            add(navBarSlots.get(i));
        }
    }

    private void createNavBarDefaultItems() {
        NavigationButton backButton = new NavigationButton("back-arrow", "BACK");
        backButton.setAlignmentX(SwingConstants.LEFT);
        backButton.setVisible(false);

        JLabel titleLabel = new JLabel();
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

    private JLabel getSubtitleLabel() {
        JPanel centralSlot = navBarSlots.get(1);
        JLabel subtitleLabel = null;
        if (centralSlot.getComponents().length >= 2) {
            subtitleLabel = (JLabel) centralSlot.getComponent(1);
        }
        return subtitleLabel;
    }

    private JButton getRightButton() {
        JPanel easternSlot = navBarSlots.get(NUMBER_OF_ITEMS - 1);
        JButton button = null;
        if (easternSlot.getComponents().length >= 1) {
            button = (JButton) easternSlot.getComponent(0);
        }
        return button;
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

    public void setRightButtonText(String text) {
        JButton rightButton = getRightButton();
        if (rightButton != null) {
            rightButton.setText(text);
        }
    }

    public void createSubtitleItem(@NotNull String text) {
        JLabel subtitleLabel = getSubtitleLabel();
        if (subtitleLabel != null) {
            subtitleLabel.setText(text);
        }
        subtitleLabel = new JLabel();
        subtitleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        // Sets the screen subtitle style
        Font f = subtitleLabel.getFont();
        subtitleLabel.setFont(new Font(f.getName(), Font.PLAIN, SUBTITLE_SIZE));
        subtitleLabel.setText(text);
        navBarSlots.get(1).add(subtitleLabel, BorderLayout.SOUTH);
    }

    public void createRightButtonItem(@NotNull String title) {
        JButton rightButton = getRightButton();
        if (rightButton != null) {
            rightButton.setText(title);
        } else {
            rightButton = new JButton("EDIT");
            navBarSlots.get(NUMBER_OF_ITEMS - 1).add(rightButton, BorderLayout.EAST);
        }
    }

    public void setListener(NavigationBarListener listener) {
        JButton backButton = getBackButton();
        JButton rightButton = getRightButton();

        addButtonActionListener(backButton, listener != null ? e -> listener.onBackButtonClicked() : null);
        addButtonActionListener(rightButton, listener != null ? e -> listener.onRightNavigationButtonClicked() : null);
    }

    public void removeBarItems() {
        JLabel subtitleLabel = getSubtitleLabel();
        JButton rightButton = getRightButton();
        if (subtitleLabel != null) {
            navBarSlots.get(1).remove(subtitleLabel);
        }
        if (rightButton != null) {
            navBarSlots.get(NUMBER_OF_ITEMS - 1).remove(rightButton);
        }
    }

    private void addButtonActionListener(JButton button, ActionListener actionListener) {
        if (button != null) {
            ActionListener[] currentListeners = button.getActionListeners();
            if (currentListeners.length > 0) {
                button.removeActionListener(currentListeners[0]);
            }
            if (actionListener != null) {
                button.addActionListener(actionListener);
            }
        }

    }

}
