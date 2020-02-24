package madstax.view;

import javax.swing.*;

public abstract class Screen extends JPanel {

    private String screenTitle;

    public Screen(String title) {
        this.screenTitle = title;
    }

    public String getScreenTitle() {
        return screenTitle;
    }

}
