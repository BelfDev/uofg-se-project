package madstax.controller.navigation;

import com.sun.istack.internal.NotNull;
import madstax.controller.ScreenController;
import madstax.view.ApplicationWindow;
import madstax.view.NavigationBar;

import java.util.Stack;

public class NavigationController {

    private static NavigationController instance = null;
    private ApplicationWindow window;
    private Stack<ScreenController> navigationStack;
    private NavigationBar navigationBar;

    private NavigationController() {
        this.navigationStack = new Stack<>();
        this.window = new ApplicationWindow();
        this.navigationBar = window.getNavigationBar();
    }

    public static NavigationController getInstance() {
        if (instance == null) {
            instance = new NavigationController();
        }
        return instance;
    }

    public ApplicationWindow getAppWindow() {
        return window;
    }

    public void setRoot(@NotNull ScreenController screenController) {
        if (!navigationStack.isEmpty()) {
            navigationStack.clear();
        }
        pushController(screenController);
    }

    public void popToRoot() {
        ScreenController rootScreenController = null;
        for (int i = 0; i < navigationStack.size(); i++) {
            rootScreenController = navigationStack.pop();
        }
        setRoot(rootScreenController);
    }

    public void pushController(@NotNull ScreenController screenController) {
        navigationStack.push(screenController);
        attachToWindow(screenController);
    }

    public void popController() {
        if (!navigationStack.isEmpty()) {
            // Invokes 'onDetach' lifecycle method
            navigationStack.pop().onDetach();
            // Clears the navigation bar
            navigationBar.setListener(null);
            navigationBar.removeBarItems();
            // Attaches the previous content to the window
            attachToWindow(navigationStack.peek());
        }
    }

    private void attachToWindow(@NotNull ScreenController screenController) {
        String screenTitle = screenController.getScreen().getScreenTitle();
        boolean isRootScreen = navigationStack.size() == 1;
        // Updates the navigation bar
        updateNavigationBar(screenTitle, !isRootScreen);
        // Attaches the content to the window
        window.attachContent(screenController.getScreen());
        screenController.onAttached();

        navigationBar.setListener(screenController);
    }

    private void updateNavigationBar(String screenTitle, boolean backButtonVisible) {
        navigationBar.setTitleLabelText(screenTitle);
        navigationBar.setBackButtonVisibility(backButtonVisible);
    }

}
