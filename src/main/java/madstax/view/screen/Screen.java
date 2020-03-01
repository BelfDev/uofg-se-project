package madstax.view.screen;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public abstract class Screen extends JPanel {

    protected final int BIG_FONT_SIZE = 32;

    private String screenTitle;

    public Screen(String title) {
        this.screenTitle = title;
        layoutComponents();
        this.setBackground(Color.WHITE);
        this.setVisible(true);
    }

    public String getScreenTitle() {
        return screenTitle;
    }

    abstract void layoutComponents();

    public void addContent(ContentBuilder builder) {
        ArrayList<Component> components = builder.build();
        for (Component component : components) {
            add(component);
        }
    }

    protected static class ContentBuilder {
        private ArrayList<Component> components = new ArrayList<>();

        public ContentBuilder addView(Component component) {
            components.add(component);
            return this;
        }

        public ArrayList<Component> build() {
            return this.components;
        }
    }

}
