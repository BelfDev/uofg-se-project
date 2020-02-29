package madstax.controller.navigation;

import java.awt.event.ActionEvent;
import java.util.EventListener;

public interface NavigationBarListener extends EventListener {

    /**
     * Invoked when an action occurs.
     */
    public void onBackButtonClicked();

    public void onRightNavigationButtonClicked();

}
