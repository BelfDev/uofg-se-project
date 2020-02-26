package madstax.application;

import madstax.controller.RoleSelectionController;
import madstax.controller.navigation.Navigator;
import madstax.view.ApplicationWindow;

public class Application implements ApplicationLifeCycle {

    private ApplicationWindow window;
    private Navigator navigator;

    public Application() {
        this.navigator = Navigator.getInstance();
        this.window = navigator.getAppWindow();
        this.onAppDidLaunch();
    }

    @Override
    public void onAppDidLaunch() {
        navigator.setRoot(new RoleSelectionController());
        ApplicationRepository.getInstance().prefetchAllData();
    }

}
