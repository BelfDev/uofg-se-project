package madstax.application;

import madstax.controller.RoleSelectionController;
import madstax.controller.navigation.NavigationController;
import madstax.view.ApplicationWindow;

public class Application implements ApplicationLifeCycle {

    private ApplicationWindow window;
    private NavigationController navigationController;

    public Application() {
        this.navigationController = NavigationController.getInstance();
        this.window = navigationController.getAppWindow();
        this.onAppDidLaunch();
    }

    @Override
    public void onAppDidLaunch() {
        navigationController.setRoot(new RoleSelectionController());
        ApplicationRepository.getInstance().prefetchAllData();
    }

}
