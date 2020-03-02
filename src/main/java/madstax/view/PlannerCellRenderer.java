package madstax.view;

import madstax.model.RequestStatus;
import org.apache.commons.lang3.StringUtils;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableCellRenderer;
import java.awt.*;
import java.util.ArrayList;

@SuppressWarnings("unchecked")
public class PlannerCellRenderer extends JLabel implements TableCellRenderer {

    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int col) {
        DefaultTableCellRenderer renderer = new DefaultTableCellRenderer();

        String s;
        switch (col) {
            case 2:
                s = StringUtils.join((ArrayList<String>) value, ", ");
                break;
            case 4:
                s = ((RequestStatus) value).getDisplayName();
                break;
            default:
                s = value.toString();
        }
        Component c = renderer.getTableCellRendererComponent(table, s,
                isSelected, hasFocus, row, col);
        ((JLabel) c).setHorizontalAlignment(SwingConstants.CENTER);

        return c;
    }

}
