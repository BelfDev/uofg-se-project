package madstax.controller;

import madstax.controller.navigation.NavigationBarListener;
import madstax.controller.navigation.NavigationController;
import madstax.view.ApplicationWindow;
import madstax.view.NavigationBar;
import madstax.view.Screen;

import javax.swing.*;
import java.awt.*;

public abstract class ScreenController<T extends Screen> implements NavigationBarListener {

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

    public void onDetach() {
    }

    @Override
    public void onBackButtonClicked() {
        System.out.println("Back Button Was Clicked from " + screen.getScreenTitle());
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
