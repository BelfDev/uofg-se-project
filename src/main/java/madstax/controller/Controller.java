package madstax.controller;

import madstax.controller.navigation.NavigationBarListener;
import madstax.controller.navigation.Navigator;
import madstax.view.ApplicationWindow;
import madstax.view.Screen;

import javax.swing.*;
import java.awt.*;

public abstract class Controller<E extends Screen> implements NavigationBarListener {

    protected E screen;
    protected Navigator navigator;

    private Timer alphaAnimator;

    public Controller(E screen) {
        this.screen = screen;
        this.navigator = Navigator.getInstance();
    }

    public E getScreen() {
        return screen;
    }

    public void onAttached(ApplicationWindow window) {
        window.setNavigationListener(this);
        animateFadeIn();
    }

    public void onDetach(ApplicationWindow window) {
        window.clearNavigationBar();
    }

    @Override
    public void onBackButtonClicked() {
        System.out.println("Back Button Was Clicked from " + screen.getScreenTitle());
        navigator.popController();
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
