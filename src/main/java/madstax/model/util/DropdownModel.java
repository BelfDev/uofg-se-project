package madstax.model.util;

import javax.swing.*;

public class DropdownModel<E> extends DefaultComboBoxModel<E> {

    private boolean introSelectionAllowed;

    public DropdownModel(E[] items) {
        super(items);
        this.introSelectionAllowed = true;
    }

    @Override
    public void setSelectedItem(Object anObject) {
        if (anObject == null && introSelectionAllowed) {
            String message = "Select an option";
            super.setSelectedItem(message);
        } else {
            introSelectionAllowed = false;
            super.setSelectedItem(anObject);
        }
    }

    @Override
    public Object getSelectedItem() {
        return super.getSelectedItem();
    }
}
