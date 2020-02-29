package madstax.view;

import madstax.model.CoursePlanListModel;
import madstax.model.RequestStatus;

import javax.swing.*;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class CoursePlannerScreen extends Screen {

    public static final String COURSE_PLANNER_TITLE = "Course Planner";
    private static final String[] TABLE_HEADER = {"INDEX", "COURSE", "REQUIREMENTS", "TEACHER ID", "STATUS"};

    private JTable table;
    private EditorToolbar editorToolbar;
    private ModalEditor modalEditor;

    public CoursePlannerScreen() {
        super(COURSE_PLANNER_TITLE);
    }

    @Override
    void layoutComponents() {
        setLayout(new BorderLayout());

        table = new JTable();
        JScrollPane scrollablePane = new JScrollPane(table);

        table.setDefaultRenderer(ArrayList.class, new PlannerCellRenderer());
        table.setDefaultRenderer(Integer.class, new PlannerCellRenderer());
        table.setDefaultRenderer(RequestStatus.class, new PlannerCellRenderer());
        table.setDefaultRenderer(String.class, new PlannerCellRenderer());

        add(scrollablePane, BorderLayout.CENTER);
    }

    public <E> void setEditorToolbarType(Class<? extends E> elementType) {
        this.editorToolbar = new EditorToolbar<E>();

        // Temporary!
        if (elementType != null) {
            editorToolbar.setElementType(elementType);
        } else {
            editorToolbar.setConfirmButtonTitle("EDIT");
        }

        add(editorToolbar, BorderLayout.SOUTH);
    }

    public <E> EditorToolbar<E> getEditorToolbar() {
        return editorToolbar;
    }

    public ModalEditor getModalEditor() {
        if (this.modalEditor == null) {
            this.modalEditor = new ModalEditor();
        }
        return modalEditor;
    }

    public void setTableModel(CoursePlanListModel model, ListSelectionListener selectionListener) {
        table.setModel(model);
        ListSelectionModel selectionModel = table.getSelectionModel();
        selectionModel.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        selectionModel.addListSelectionListener(selectionListener);
        table.revalidate();
        table.repaint();
    }

    public void updateAssignedTeacher(String teacherName) {
        int selectedRow = table.getSelectedRow();
        table.setValueAt(teacherName, selectedRow, 3);
    }

    public void updateRequirements(List<String> requirements) {
        int selectedRow = table.getSelectedRow();
        table.setValueAt(requirements, selectedRow, 2);
    }

    public void updateStatus(RequestStatus requestStatus) {
        int selectedRow = table.getSelectedRow();
        table.setValueAt(requestStatus, selectedRow, 4);
    }

    public void selectFirstRow() {
        table.requestFocus();
        table.changeSelection(0, 0, false, false);
    }

    public int getSelectedRow() {
        return table.getSelectedRow();
    }

    public void showModalEditor(List<String> requirements) {
        modalEditor.setRequirements(requirements);
        modalEditor.setVisible(true);
    }

    public void setConfirmButtonListener(ActionListener listener) {
        editorToolbar.setConfirmButtonListener(listener);
    }

    public Object getSelectedDropdownValue() {
        return editorToolbar.getSelectedDropdownItem();
    }

}
