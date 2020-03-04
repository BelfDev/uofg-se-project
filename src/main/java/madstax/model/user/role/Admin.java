package madstax.model.user.role;

import madstax.model.user.Permission;

import java.util.TreeSet;

import static madstax.model.user.Permission.ASSIGN_STAFF;

class Admin extends Role {

    public Admin() {
        super("Administrator");
        TreeSet<Permission> adminPermissions = new TreeSet<Permission>() {{
            add(ASSIGN_STAFF);
        }};
        this.permissions.addAll(adminPermissions);
    }

}
