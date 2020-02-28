package madstax.model.user.role;

import madstax.model.user.Permission;

import java.util.HashMap;

import static madstax.model.user.Permission.*;

class ClassDirector extends Role {

    public ClassDirector() {
        super("Class Director");
        HashMap<Permission, Boolean> adminPermissions = new HashMap<Permission, Boolean>() {{
            put(VIEW_ASSIGNED_STAFF, true);
            put(VIEW_REQUEST_STATUS, true);
            put(ADD_REQUIREMENT, true);
        }};
        this.permissions.putAll(adminPermissions);
    }

}
