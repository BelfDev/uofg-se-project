package madstax.application;

import madstax.controller.RoleSelectionController;
import madstax.controller.NavigationController;

/**
 * Class {@code Application} is the application controller.
 * This class instantiates the {@code NavigationController}
 * and pre-fetches all data required for the application.
 */
public class Application implements ApplicationLifeCycle {

    private NavigationController navigationController;

    public Application() {
        this.navigationController = NavigationController.getInstance();
        this.onAppDidLaunch();
    }

    /**
     * Application lifecycle method. Invoked after the application has
     * been launched.
     */
    @Override
    public void onAppDidLaunch() {
        // Sets the RoleSelectionController as the root Controller
        navigationController.setRoot(new RoleSelectionController());
        // Pre-fetches all data required for displaying content
        ApplicationRepository.getInstance().prefetchAllData();
    }

}
