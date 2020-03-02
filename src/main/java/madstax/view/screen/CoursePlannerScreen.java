package madstax.view.screen;

import madstax.model.CoursePlanListModel;
import madstax.model.RequestStatus;
import madstax.view.PlannerCellRenderer;
import madstax.view.editor.EditorToolbar;

import javax.swing.*;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.TableColumn;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class CoursePlannerScreen extends Screen {

    public static final String COURSE_PLANNER_TITLE = "Course Planner";
    private static final String[] TABLE_HEADER = {"INDEX", "COURSE", "REQUIREMENTS", "TEACHER ID", "STATUS"};

    private JTable table;

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

    public void setTableModel(CoursePlanListModel model, ListSelectionListener selectionListener) {
        table.setModel(model);
        setColumnWidths();
        ListSelectionModel selectionModel = table.getSelectionModel();
        selectionModel.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        selectionModel.addListSelectionListener(selectionListener);
        table.revalidate();
        table.repaint();
    }

    private void setColumnWidths(){
        TableColumn column = null;
        for(int i=0; i<5; i++){
            column = table.getColumnModel().getColumn(i);
            switch(i){
                case 0:
                    column.setPreferredWidth(10);
                    break;
                case 1:
                    column.setPreferredWidth(150);
                    break;
                case 2:
                    column.setPreferredWidth(200);
                    break;
                default:
                    column.setPreferredWidth(100);
            }
        }
    }

    public void updateAssignedTeacher(String teacherName) {
        int selectedRow = table.getSelectedRow();
        table.setValueAt(teacherName, selectedRow, 3);
    }

    public void updateRequirements(List<String> requirements) {
        int selectedRow = table.getSelectedRow();
        table.setValueAt(requirements, selectedRow, 2);
    }

    public void updateRequestStatus(RequestStatus requestStatus) {
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

    public void addEditorToolbar(EditorToolbar editorToolbar) {
        add(editorToolbar, BorderLayout.SOUTH);
    }

}
