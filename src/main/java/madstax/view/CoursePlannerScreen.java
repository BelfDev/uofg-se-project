package madstax.view;

import javax.swing.*;
import java.awt.*;

public class CoursePlannerScreen extends Screen {

    public static final String COURSE_PLANNER_TITLE = "Course Planner";
    private static final String[] TABLE_HEADER = {"INDEX", "COURSE", "REQUIREMENTS", "TEACHER ID", "STATUS"};

    private JTable table;
    private JScrollPane scrollablePane;

    public CoursePlannerScreen() {
        super(COURSE_PLANNER_TITLE);
    }

    @Override
    void layoutComponents() {
        setLayout(new BorderLayout());
    }

    public void setTable(String[][] rows) {
        cleanPreviousTable();
        table = new JTable(rows, getHeader(rows));
        scrollablePane = new JScrollPane(table);
        add(scrollablePane, BorderLayout.CENTER);
    }

    private void cleanPreviousTable() {
        if (getComponents().length > 0 && getComponent(0) instanceof JScrollPane) {
            remove(0);
        }
    }

    private String[] getHeader(String[][] rows) {
        if (rows.length != 0) {
            String[] headerValues = new String[rows[0].length];
            System.arraycopy(TABLE_HEADER, 0, headerValues, 0, headerValues.length);
            return headerValues;
        } else {
            return TABLE_HEADER;
        }
    }


}
