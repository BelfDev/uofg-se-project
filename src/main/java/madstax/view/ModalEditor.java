package madstax.view;

import madstax.controller.ModalEditorListener;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

import static madstax.view.ApplicationWindow.INITIAL_WINDOW_WIDTH;

public class ModalEditor extends JDialog {

    private final static String MODAL_TITLE = "Requirement Editor";
    private final static int COMPONENT_MARGIN = 4;

    private JTextField textField;
    private DefaultListModel<String> listModel;

    private ModalEditorListener listener;

    public ModalEditor() {
        super(new JFrame(), MODAL_TITLE, true);
        setupFrameContainer();

        // TopBar
        JPanel topBar = createTopBar();

        // Requirement List
        JList<String> requirementList = new JList<>();
        requirementList.setSize(200, 200);

        // List model
        listModel = new DefaultListModel<>();
        requirementList.setModel(listModel);

        JPanel additionToolbar = createAdditionToolbar();

        JScrollPane sp = new JScrollPane(requirementList);
        sp.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        sp.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

        this.add(topBar, BorderLayout.NORTH);
        this.add(sp, BorderLayout.CENTER);
        this.add(additionToolbar, BorderLayout.SOUTH);
    }

    private void setupFrameContainer() {
        Dimension minimumDimension = new Dimension(INITIAL_WINDOW_WIDTH / 4, INITIAL_WINDOW_WIDTH / 4);
        this.setSize(minimumDimension);
        this.setMinimumSize(minimumDimension);

        // Sets the window location to the center of the screen
        this.setLocationRelativeTo(null);
        setLayout(new BorderLayout());
    }

    private JPanel createTopBar() {
        // Components
        JLabel instructionLabel = new JLabel("Add a new requirement");
        instructionLabel.setVerticalAlignment(JLabel.CENTER);
        JButton saveButton = new JButton("DONE");
        saveButton.setVerticalAlignment(JButton.CENTER);
        saveButton.addActionListener(e -> listener.onSavedRequirements(getAllElementsFromList()));

        // TopBar
        JPanel topBar = new JPanel();
        topBar.setLayout(new BoxLayout(topBar, BoxLayout.X_AXIS));

        topBar.add(Box.createHorizontalStrut(COMPONENT_MARGIN));
        topBar.add(instructionLabel);
        topBar.add(Box.createHorizontalGlue());
        topBar.add(saveButton);
        topBar.add(Box.createHorizontalStrut(COMPONENT_MARGIN));

        return topBar;
    }

    private JPanel createAdditionToolbar() {
        // Components
        textField = new JTextField();
        JButton addButton = new JButton("ADD");
        addButton.setVerticalAlignment(JButton.CENTER);

        addButton.addActionListener(e -> {
            String input = textField.getText();
            listModel.addElement(input);
            textField.setText("");
        });

        // Toolbar
        JPanel additionToolbar = new JPanel();
        additionToolbar.setLayout(new BoxLayout(additionToolbar, BoxLayout.X_AXIS));

        additionToolbar.add(Box.createHorizontalStrut(COMPONENT_MARGIN));
        additionToolbar.add(textField);
        additionToolbar.add(addButton);
        additionToolbar.add(Box.createHorizontalStrut(COMPONENT_MARGIN));

        return additionToolbar;
    }

    public void setRequirements(List<String> requirements) {
        listModel.clear();
        requirements.forEach(r -> listModel.addElement(r));
    }

    public void setListener(ModalEditorListener listener) {
        this.listener = listener;
    }

    private List<String> getAllElementsFromList() {
        List<String> elements = new ArrayList<>();
        for (int i = 0; i < listModel.getSize(); i++) {
            elements.add(listModel.getElementAt(i));
        }
        return elements;
    }

}
