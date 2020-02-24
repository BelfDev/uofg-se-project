package madstax.controller;

import madstax.view.Screen;

public abstract class Controller {

    protected Screen screen;

    public Controller(Screen screen) {
        this.screen = screen;
    }

    public Screen getScreen() {
        return screen;
    }

}
