package madstax.view.screen;

import madstax.model.user.role.RoleType;

import javax.swing.*;
import java.awt.*;

import static madstax.view.ApplicationWindow.INITIAL_WINDOW_WIDTH;


public class RoleSelectionScreen extends Screen {

    private static final String ROLE_SELECTION_TITLE = "Role Selection";
    private static final String INSTRUCTION_TEXT = "Please select a role";
    private static final String LOGIN_BUTTON_TITLE = "LOGIN";
    private static final int PREFERRED_BUTTON_WIDTH = 200;
    private static final int PREFERRED_BUTTON_HEIGHT = 50;

    private JButton loginButton;
    private JComboBox<RoleType> dropdownMenu;

    public RoleSelectionScreen() {
        super(ROLE_SELECTION_TITLE);
    }

    public JButton getLoginButton() {
        return loginButton;
    }

    public JComboBox<RoleType> getDropdownMenu() {
        return dropdownMenu;
    }

    public void setDropdownData(RoleType[] data) {
        DefaultComboBoxModel<RoleType> model = new DefaultComboBoxModel<>(data);
        dropdownMenu.setModel(model);
    }

    @Override
    void layoutComponents() {
        // Creates vertical box layout manager
        BoxLayout boxLayout = new BoxLayout(this, BoxLayout.Y_AXIS);
        this.setLayout(boxLayout);

        // Creates subviews
        JLabel instructionLabel = createInstructionLabel();
        dropdownMenu = createDropdownMenu();
        loginButton = createLoginButton();

        // Assembles the layout components declaratively
        ContentBuilder builder = new ContentBuilder()
                .addView(Box.createVerticalGlue())
                .addView(instructionLabel)
                .addView(Box.createVerticalGlue())
                .addView(dropdownMenu)
                .addView(Box.createVerticalGlue())
                .addView(loginButton)
                .addView(Box.createVerticalGlue());

        // Adds the layout components to the screen
        addContent(builder);
    }

    // Customizes the instruction label
    private JLabel createInstructionLabel() {
        JLabel label = new JLabel(INSTRUCTION_TEXT);
        Font customizedFont = new Font(label.getFont().getName(), Font.PLAIN, BIG_FONT_SIZE);
        label.setFont(customizedFont);
        label.setAlignmentX(CENTER_ALIGNMENT);
        return label;
    }

    // Customizes the dropdownMenu JComboBox
    private JComboBox<RoleType> createDropdownMenu() {
        JComboBox<RoleType> comboBox = new JComboBox<>();
        comboBox.setAlignmentX(CENTER_ALIGNMENT);
        Dimension dropdownSize = new Dimension(INITIAL_WINDOW_WIDTH / 4, comboBox.getHeight());
        comboBox.setMaximumSize(dropdownSize);
        return comboBox;
    }

    // Customizes the login button
    private JButton createLoginButton() {
        JButton button = new JButton(LOGIN_BUTTON_TITLE);
        button.setAlignmentX(CENTER_ALIGNMENT);
        // Sets the login button size
        // All methods below are necessary to ensure the desired size
        Dimension buttonSize = new Dimension(PREFERRED_BUTTON_WIDTH, PREFERRED_BUTTON_HEIGHT);
        button.setMaximumSize(buttonSize);
        button.setPreferredSize(buttonSize);
        return button;
    }

}
