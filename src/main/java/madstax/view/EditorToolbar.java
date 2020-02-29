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
    private static final String INSTRUCTION_TEXT = "Activate the EDIT mode to start editing";

    private ArrayList<JPanel> slots;

    private JLabel instructionLabel;
    private JLabel courseLabel;
    private JComboBox<E> dropdown;
    private JButton actionButton;
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
        instructionLabel = new JLabel(INSTRUCTION_TEXT);

        courseLabel = new JLabel();
        courseLabel.setVisible(false);

        actionButton = new JButton(CONFIRM_BUTTON_TITLE);
        actionButton.setPreferredSize(new Dimension(100, 40));
        actionButton.setAlignmentY(SwingUtilities.CENTER);
        actionButton.setVisible(false);

        // Adds the components to the slots
        slots.get(0).add(courseLabel);
        slots.get(1).add(instructionLabel);
        slots.get(2).add(actionButton);

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
        actionButton.addActionListener(listener);
    }

    public void setConfirmButtonText(String title) {
        this.actionButton.setText(title);
    }

    public void activate() {
        slots.get(1).remove(instructionLabel);
        courseLabel.setVisible(true);
        actionButton.setVisible(true);

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
        actionButton.setVisible(false);
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
        private String actionButtonTitle;

        public Builder withDropdown() {
            if (dropdown == null) {
                dropdown = new JComboBox();
            }
            return this;
        }

        public Builder actionButtonText(String text) {
            actionButtonTitle = text;
            return this;
        }

        public EditorToolbar build() {
            EditorToolbar toolbar = new EditorToolbar();
            toolbar.dropdown = this.dropdown;

            if (actionButtonTitle != null) {
                JButton actionButton = toolbar.actionButton;
                actionButton.setText(actionButtonTitle);
                actionButton.setPreferredSize(new Dimension(156, 40));
            }

            return toolbar;
        }
    }

}
