package madstax.model.user.role;

import madstax.model.user.Permission;

import java.util.HashMap;

import static madstax.model.user.Permission.ASSIGN_STAFF;
import static madstax.model.user.Permission.VIEW_ASSIGNED_STAFF;

class Admin extends Role {

    public Admin() {
        super("Administrator");
        HashMap<Permission, Boolean> adminPermissions = new HashMap<Permission, Boolean>() {{
            put(VIEW_ASSIGNED_STAFF, true);
            put(ASSIGN_STAFF, true);
        }};
        this.permissions.putAll(adminPermissions);
    }

}
