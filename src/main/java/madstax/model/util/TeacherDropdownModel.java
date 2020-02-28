package madstax.model.util;

import madstax.model.Teacher;

import javax.swing.*;

public class TeacherDropdownModel extends DefaultComboBoxModel<Teacher> {

    private boolean introSelectionAllowed;

    public TeacherDropdownModel(Teacher[] items) {
        super(items);
        this.introSelectionAllowed = true;
    }

    @Override
    public void setSelectedItem(Object anObject) {
        Teacher teacher = (Teacher) anObject;
        if (teacher.getId() == -1 && introSelectionAllowed) {
            super.setSelectedItem(anObject);
        } else if (teacher.getId() != -1) {
            introSelectionAllowed = false;
            super.setSelectedItem(anObject);
        }
    }

}
