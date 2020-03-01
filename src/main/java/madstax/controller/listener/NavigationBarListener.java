package madstax.controller.listener;

import java.util.EventListener;

public interface NavigationBarListener extends EventListener {

    /**
     * Invoked when an action occurs.
     */
    void onBackButtonClicked();

    void onRightNavigationButtonClicked();

}
