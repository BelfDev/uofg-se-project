package madstax.view;

import madstax.model.util.DropdownModel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class EditorToolbar<E> extends JPanel {

    private static final int TOOLBAR_HEIGHT = 80;
    private static final int NUMBER_OF_SLOTS = 3;
    private static final String CONFIRM_BUTTON_TITLE = "CONFIRM";

    private ArrayList<JPanel> slots;

    private JLabel instructionLabel;
    private JLabel courseLabel;
    private JComboBox<E> dropdown;
    private JButton confirmButton;
    protected Class<? extends E> elementType;

    private EditorToolbar() {
        // Sets the toolbar layout
        GridLayout gridLayout = new GridLayout(0, NUMBER_OF_SLOTS);
        this.setLayout(gridLayout);

        // Sets the toolbar size
        this.setSize(0, TOOLBAR_HEIGHT);
        this.setPreferredSize(new Dimension(0, TOOLBAR_HEIGHT));

        // Creates toolbar container slots
        createToolbarSlots();

        // Creates the toolbar components
        instructionLabel = new JLabel("Activate EDIT MODE to start editing");

        courseLabel = new JLabel();
        courseLabel.setVisible(false);

        confirmButton = new JButton(CONFIRM_BUTTON_TITLE);
        confirmButton.setPreferredSize(new Dimension(100, 40));
        confirmButton.setAlignmentY(SwingUtilities.CENTER);
        confirmButton.setVisible(false);

        // Adds the components to the slots
        slots.get(0).add(courseLabel);
        slots.get(1).add(instructionLabel);
        slots.get(2).add(confirmButton);

        this.setVisible(true);
    }

    public void setElementType(Class<? extends E> elementType) {
        this.elementType = elementType;
    }

    public E getSelectedDropdownItem() {
        if (dropdown.getSelectedItem() != null) {
            DropdownModel<E> model = (DropdownModel<E>) dropdown.getModel();
            return model.getSelectedItem();
        }
        return null;
    }

    public void setCourseLabelText(String text) {
        courseLabel.setText(text);
    }

    public void createDecisionDropDown() {
        dropdown = new JComboBox<>();
        slots.get(1).remove(instructionLabel);
        slots.get(1).add(dropdown);
    }

    public void setDropdownData(E[] data) {
        DropdownModel<E> model = new DropdownModel<>(data);
        dropdown.setModel(model);
    }

//    public void setDecisionDropdownData(Teacher[] teachers) {
//        Teacher[] dropdownData = new Teacher[]{new Teacher(-1, "Select an Option", null)};
//        Teacher[] newArray = ArrayUtils.addAll(dropdownData, teachers);
//        TeacherDropdownModel model = new TeacherDropdownModel(newArray);
//        decisionDropdown.setModel(model);
//    }

    public void setConfirmButtonListener(ActionListener listener) {
        confirmButton.addActionListener(listener);
    }

    public void setConfirmButtonTitle(String title) {
        this.confirmButton.setText(title);
    }

    public void activate() {
        slots.get(1).remove(instructionLabel);
        courseLabel.setVisible(true);
        confirmButton.setVisible(true);

        if (dropdown != null) {
            slots.get(1).add(dropdown);
            dropdown.setVisible(true);
        }
        revalidate();
        repaint();
    }

    public void deactivate() {
        if (dropdown != null) {
            slots.get(1).remove(dropdown);
        }
        slots.get(1).add(instructionLabel);
        courseLabel.setVisible(false);
        confirmButton.setVisible(false);
        revalidate();
        repaint();
    }

    private void createToolbarSlots() {
        this.slots = new ArrayList<>();
        for (int i = 0; i < NUMBER_OF_SLOTS; i++) {
            JPanel itemContainer = new JPanel(new GridBagLayout());
            slots.add(itemContainer);
            add(slots.get(i));
        }
    }

    public static class Builder {

        private JComboBox dropdown;

        public Builder withDropdown() {
            if (dropdown == null) {
                dropdown = new JComboBox();
            }
            return this;
        }

        public EditorToolbar build() {
            EditorToolbar toolbar = new EditorToolbar();
            toolbar.dropdown = this.dropdown;
            return toolbar;
        }
    }

}
