package madstax.controller;

import madstax.model.navigation.Navigator;
import madstax.view.Screen;

public abstract class Controller<E extends Screen> {

    protected E screen;
    protected Navigator navigator;

    public Controller(E screen) {
        this.screen = screen;
        this.navigator = Navigator.getInstance();
    }

    public E getScreen() {
        return screen;
    }

}
