package madstax.view;

import madstax.model.RequestStatus;
import org.apache.commons.lang3.StringUtils;

import javax.swing.*;
import javax.swing.border.MatteBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableCellRenderer;
import java.awt.*;
import java.util.ArrayList;

@SuppressWarnings("unchecked")
public class PlannerCellRenderer extends JLabel implements TableCellRenderer {

    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int col) {
        DefaultTableCellRenderer renderer = new DefaultTableCellRenderer();
        int selectedRow = table.getSelectedRow();

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
                isSelected, false, row, col);
        ((JLabel) c).setHorizontalAlignment(SwingConstants.CENTER);

        if(row == selectedRow){
            JComponent jc = (JComponent)c;
            jc.setBorder(new MatteBorder(1,0,1,0, Color.BLUE));
        }

        if(col == 4){
            switch((RequestStatus)value){
                case APPROVED:
                    c.setForeground(new Color(15, 194,0));
                    break;
                case DENIED:
                    c.setForeground(Color.red);
                    break;
                case AWAITING_APPROVAL:
                    c.setForeground(new Color(255, 127, 2));
                    break;
            }
        }

        if(row%2 == 0){
            c.setBackground(new Color(240,240,240));
        }else{
            c.setBackground(new Color(210,210,210));
        }

        return c;
    }

}
