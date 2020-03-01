package madstax.controller;

import madstax.controller.listener.NavigationBarListener;
import madstax.view.NavigationBar;
import madstax.view.screen.Screen;

import javax.swing.*;
import java.awt.*;

abstract class ScreenController<T extends Screen> implements NavigationBarListener {

    protected T screen;
    protected NavigationController navigationController;
    protected NavigationBar navBar;

    private Timer alphaAnimator;

    public ScreenController(T screen) {
        this.screen = screen;
        this.navigationController = NavigationController.getInstance();
        this.navBar = navigationController.getAppWindow().getNavigationBar();
    }

    public T getScreen() {
        return screen;
    }

    public void onAttached() {
        animateFadeIn();
    }

    @Override
    public void onBackButtonClicked() {
        navigationController.popController();
    }

    @Override
    public void onRightNavigationButtonClicked() {
    }

    private void animateFadeIn() {
        JPanel screen = getScreen();
        Color bg = screen.getBackground();
        Color initialColor = new Color(bg.getRed(), bg.getGreen(), bg.getBlue(), 0);
        screen.setBackground(initialColor);
        screen.repaint();

        alphaAnimator = new Timer(0, e -> {
            int incrementer = 2;
            Color bg1 = screen.getBackground();
            Color newColor = null;
            if (bg1.getAlpha() >= 254) {
                alphaAnimator.stop();
                newColor = new Color(bg1.getRed(), bg1.getGreen(), bg1.getBlue(), 255);
            } else {
                int newAlpha = bg1.getAlpha() + incrementer;
                newColor = new Color(bg1.getRed(), bg1.getGreen(), bg1.getBlue(), newAlpha);
            }
            screen.setBackground(newColor);
            screen.repaint();
        });

        alphaAnimator.start();
    }

}
