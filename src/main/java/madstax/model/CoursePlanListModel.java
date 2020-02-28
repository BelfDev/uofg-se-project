package madstax.model;

import javax.swing.table.AbstractTableModel;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class CoursePlanListModel extends AbstractTableModel {

    private List<Field> headers;
    private Object[][] data;

    public CoursePlanListModel(List<CoursePlanListItem> rows) {
        this.headers = Arrays.stream(CoursePlanListItem.class.getDeclaredFields())
                .collect(Collectors.toCollection(ArrayList::new));
        this.data = rows.stream()
                .map(CoursePlanListItem::toArray)
                .toArray(Object[][]::new);
    }

    @Override
    public int getRowCount() {
        return data.length;
    }

    @Override
    public int getColumnCount() {
        return headers.size();
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        return data[rowIndex][columnIndex];
    }

    @Override
    public String getColumnName(int column) {
        return headers.get(column).getName().toUpperCase();
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        if (columnIndex == 0) {
            return true;
        }
        return super.isCellEditable(rowIndex, columnIndex);
    }

    @Override
    public Class<?> getColumnClass(int columnIndex) {
        return getValueAt(0, columnIndex).getClass();
    }

}