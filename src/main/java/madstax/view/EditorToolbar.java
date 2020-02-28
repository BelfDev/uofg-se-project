package madstax.view;

import madstax.model.Teacher;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class EditorToolbar extends JPanel {

    private static final int TOOLBAR_HEIGHT = 80;
    private static final int NUMBER_OF_SLOTS = 3;
    private static final String CONFIRM_BUTTON_TITLE = "CONFIRM";

    private ArrayList<JPanel> slots;

    private JLabel courseLabel;
    private JComboBox<Teacher> decisionDropdown;
    private JButton confirmButton;

    public EditorToolbar() {
        // Sets the toolbar layout
        GridLayout gridLayout = new GridLayout(0, NUMBER_OF_SLOTS);
        this.setLayout(gridLayout);

        // Sets the toolbar size
        this.setSize(0, TOOLBAR_HEIGHT);
        this.setPreferredSize(new Dimension(0, TOOLBAR_HEIGHT));

        // Creates toolbar container slots
        createToolbarSlots();

        // Creates the toolbar components
        courseLabel = new JLabel("Course");
        decisionDropdown = new JComboBox<>();

        confirmButton = new JButton(CONFIRM_BUTTON_TITLE);
        confirmButton.setPreferredSize(new Dimension(100, 40));
        confirmButton.setAlignmentY(SwingUtilities.CENTER);

        // Adds the components to the slots
        slots.get(0).add(courseLabel);
        slots.get(1).add(decisionDropdown);
        slots.get(2).add(confirmButton);
    }

    public void setCourseLabelText(String text) {
        courseLabel.setText(text);
    }

    public void setDecisionDropdownData(Teacher[] Teacher) {
        DefaultComboBoxModel<Teacher> model = new DefaultComboBoxModel<>(Teacher);
        decisionDropdown.setModel(model);
    }

    private void createToolbarSlots() {
        this.slots = new ArrayList<>();
        for (int i = 0; i < NUMBER_OF_SLOTS; i++) {
            JPanel itemContainer = new JPanel(new GridBagLayout());
            slots.add(itemContainer);
            add(slots.get(i));
        }
    }

}
