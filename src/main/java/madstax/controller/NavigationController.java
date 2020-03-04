package madstax.controller;

import madstax.view.ApplicationWindow;
import madstax.view.NavigationBar;

import java.util.Stack;

/**
 * Class {@code NavigationController} manages all App navigation.
 * It uses a {@code Stack} to store the screen controller hierarchy
 * and provides all methods necessary to manipulate transitions.
 */
@SuppressWarnings("rawtypes")
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

    /**
     * Returns a single instance of NavigationController.
     *
     * @return a unique instance of NavigationController
     */
    public static NavigationController getInstance() {
        if (instance == null) {
            instance = new NavigationController();
        }
        return instance;
    }

    /**
     * Returns the ApplicationWindow.
     *
     * @return the instantiated ApplicationWindow.
     */
    public ApplicationWindow getAppWindow() {
        return window;
    }

    /**
     * Resets the screen controller stack and sets the given controller
     * as the first element of the hierarchy.
     *
     * @param screenController controller to be set as the root
     */
    public void setRoot(ScreenController screenController) {
        if (!navigationStack.isEmpty()) {
            navigationStack.clear();
        }
        pushController(screenController);
    }

    /**
     * Adds a new screen controller to the navigation stack
     * and make all the arrangements for the transition.
     *
     * @param screenController controller to be pushed into the hierarchy
     */
    public void pushController(ScreenController screenController) {
        navigationStack.push(screenController);
        attachToWindow(screenController);
    }

    /**
     * Removes the last screen controller of the navigation stack
     * and make the necessary arrangements to transition back to the
     * previous controller.
     */
    public void popController() {
        if (!navigationStack.isEmpty()) {
            // Invokes 'onDetach' lifecycle method
            navigationStack.pop();
            // Clears the navigation bar
            navigationBar.setListener(null);
            navigationBar.removeBarItems();
            // Attaches the previous content to the window
            attachToWindow(navigationStack.peek());
        }
    }

    private void attachToWindow(ScreenController screenController) {
        String screenTitle = screenController.getScreen().getScreenTitle();
        boolean isRootScreen = navigationStack.size() == 1;
        // Updates the navigation bar
        updateNavigationBar(screenTitle, !isRootScreen);
        // Attaches the content to the window
        window.attachContent(screenController.getScreen());
        // Invokes ScreenController lifecycle method
        screenController.onAttached();

        navigationBar.setListener(screenController);
    }

    private void updateNavigationBar(String screenTitle, boolean backButtonVisible) {
        navigationBar.setTitleLabelText(screenTitle);
        navigationBar.setBackButtonVisibility(backButtonVisible);
    }

}
