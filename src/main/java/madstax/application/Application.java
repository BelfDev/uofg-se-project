package madstax.application;

import madstax.controller.RoleSelectionController;
import madstax.controller.NavigationController;

public class Application implements ApplicationLifeCycle {

    private NavigationController navigationController;

    public Application() {
        this.navigationController = NavigationController.getInstance();
        this.onAppDidLaunch();
    }

    @Override
    public void onAppDidLaunch() {
        navigationController.setRoot(new RoleSelectionController());
        ApplicationRepository.getInstance().prefetchAllData();
    }

}
