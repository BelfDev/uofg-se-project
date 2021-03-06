package madstax.controller;

import madstax.model.user.User;
import madstax.model.user.role.Role;
import madstax.model.user.role.RoleFactory;
import madstax.model.user.role.RoleType;
import madstax.view.screen.RoleSelectionScreen;

import java.awt.event.ActionListener;

public class RoleSelectionController extends ScreenController<RoleSelectionScreen> {

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
            navigationController.pushController(new CoursePlannerController(user));
        };
    }

    private Role getSelectedRow() {
        RoleType selectedItem = (RoleType) screen.getDropdownMenu().getSelectedItem();
        assert selectedItem != null;
        return RoleFactory.getRole(selectedItem);
    }

}
