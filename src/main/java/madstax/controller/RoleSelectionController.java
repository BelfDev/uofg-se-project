package madstax.controller;

import madstax.model.user.User;
import madstax.model.user.role.Role;
import madstax.model.user.role.RoleFactory;
import madstax.model.user.role.RoleType;
import madstax.view.RoleSelectionScreen;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class RoleSelectionController extends Controller<RoleSelectionScreen> {

    private User user;

    public RoleSelectionController() {
        super(new RoleSelectionScreen());
        user = new User();
        loadData();
        setListeners();
    }

    private void loadData() {
        screen.setDropdownData(RoleType.values());
    }

    private void setListeners() {
        screen.getLoginButton().addActionListener(onLoginButtonClick());
    }

    private ActionListener onLoginButtonClick() {
        return actionEvent -> {
            user.setRole(getSelectedRow());
            navigator.pushController(new CoursePlannerController(user));
        };
    }

    private Role getSelectedRow() {
        RoleType selectedItem = (RoleType) screen.getDropdownMenu().getSelectedItem();
        return RoleFactory.getRole(selectedItem);
    }

}
