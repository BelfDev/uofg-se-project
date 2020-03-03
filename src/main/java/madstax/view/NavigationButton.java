package madstax.view;

import madstax.model.util.ResourceIO;

import javax.swing.*;
import java.awt.*;

import static madstax.view.NavigationBar.NAV_BAR_COLOR;

class NavigationButton extends JButton {

    private static final int DEFAULT_ICON_SIDE = 24;
    private static final int CLICK_COLOR_MODIFIER = 10;

    public NavigationButton(String iconName, String text) {
        super();
        // Loads the image resource
        ImageIcon icon = ResourceIO.getImageIcon(iconName);
        // Scales the image accordingly
        Image scaledImage = icon.getImage().getScaledInstance(DEFAULT_ICON_SIDE, DEFAULT_ICON_SIDE, Image.SCALE_SMOOTH);
        icon.setImage(scaledImage);
        this.setIcon(icon);
        // Sets the button text
        this.setText(text);

        // Customizes container appearance
        setBackground(NAV_BAR_COLOR);
        this.setOpaque(false);
        this.setContentAreaFilled(false);
        this.setBorderPainted(false);
        this.setFocusPainted(false);
    }

    @Override
    protected void paintComponent(Graphics g) {
        Color currentColor = getBackground();
        if (getModel().isPressed()) {
            Color fadedColor = new Color(currentColor.getRed() - CLICK_COLOR_MODIFIER,
                    currentColor.getGreen() - CLICK_COLOR_MODIFIER,
                    currentColor.getBlue() - CLICK_COLOR_MODIFIER);
            g.setColor(fadedColor);
        } else {
            g.setColor(currentColor);
        }
        g.fillRect(0, 0, getWidth(), getHeight());
        super.paintComponent(g);
    }
}
