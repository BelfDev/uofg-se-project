package madstax.model.user.role;

import madstax.model.user.Permission;

import java.util.HashMap;

import static madstax.model.user.Permission.ASSIGN_STAFF;

class Admin extends Role {

    public Admin() {
        super("Administrator");
        HashMap<Permission, Boolean> adminPermissions = new HashMap<Permission, Boolean>() {{
            put(ASSIGN_STAFF, true);
        }};
        this.permissions.putAll(adminPermissions);
    }

}
