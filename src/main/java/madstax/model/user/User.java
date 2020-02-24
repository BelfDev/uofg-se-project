package madstax.model.user;

import madstax.model.user.role.Role;

public class User {

    Role role;

    public User() {
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

}
