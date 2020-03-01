package madstax.view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

import static madstax.view.ApplicationWindow.INITIAL_WINDOW_HEIGHT;
import static madstax.view.ApplicationWindow.INITIAL_WINDOW_WIDTH;

public class ConfirmationModal extends JDialog {

    private static final String WARNING_MESSAGE = "Do you want to go back without saving?";

    public ConfirmationModal(String title, ActionListener onConfirm) {
        super(new JFrame(), title, true);
        setupFrameContainer();

        JLabel warningLabel = new JLabel(WARNING_MESSAGE);
        warningLabel.setHorizontalAlignment(JLabel.CENTER);

        JButton confirmButton = new JButton("YES");
        confirmButton.addActionListener(e -> {
                    setVisible(false);
                    onConfirm.actionPerformed(e);
                }
        );
        JButton cancelButton = new JButton("CANCEL");
        cancelButton.addActionListener(e -> setVisible(false));

        JPanel buttonPanel = new JPanel(new GridLayout(0, 2));
        buttonPanel.add(cancelButton);
        buttonPanel.add(confirmButton);

        this.add(warningLabel, BorderLayout.CENTER);
        this.add(buttonPanel, BorderLayout.SOUTH);
        this.setVisible(true);
    }

    private void setupFrameContainer() {
        Dimension minimumDimension = new Dimension(INITIAL_WINDOW_WIDTH / 3, INITIAL_WINDOW_HEIGHT / 8);
        this.setSize(minimumDimension);
        this.setMinimumSize(minimumDimension);

        // Sets the window location to the center of the screen
        this.setLocationRelativeTo(null);
        setLayout(new BorderLayout());
        setResizable(false);
    }

}
