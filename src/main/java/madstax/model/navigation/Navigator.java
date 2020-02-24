package madstax.model.navigation;

import com.sun.istack.internal.NotNull;
import madstax.controller.Controller;
import madstax.view.ApplicationWindow;

import java.util.Stack;

public class Navigator {

    private static Navigator instance = null;
    private ApplicationWindow window;
    private Stack<Controller> navigationStack;

    private Navigator() {
        this.navigationStack = new Stack<>();
        this.window = new ApplicationWindow();
    }

    public static Navigator getInstance() {
        if (instance == null) {
            instance = new Navigator();
        }
        return instance;
    }

    public ApplicationWindow getAppWindow() {
        return window;
    }

    public void setRoot(@NotNull Controller controller) {
        if (!navigationStack.isEmpty()) {
            navigationStack.clear();
        }
        pushController(controller);
    }

    public void popToRoot() {
        Controller rootController = null;
        for (int i = 0; i < navigationStack.size(); i++) {
            rootController = navigationStack.pop();
        }
        setRoot(rootController);
    }

    public void pushController(@NotNull Controller controller) {
        navigationStack.push(controller);
        attachToWindow(controller);
    }

    public void popController() {
        if (!navigationStack.isEmpty()) {
            Controller previousController = navigationStack.pop();
            attachToWindow(previousController);
        }
    }

    private void attachToWindow(@NotNull Controller controller) {
        window.add(controller.getScreen());
        window.setVisible(true);
    }

}
