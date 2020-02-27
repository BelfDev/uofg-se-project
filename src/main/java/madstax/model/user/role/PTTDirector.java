package madstax.model.user.role;

import madstax.model.user.Permission;

import java.util.HashMap;

import static madstax.model.user.Permission.*;

class PTTDirector extends Role {

    public PTTDirector() {
        super("PTT Director");
        HashMap<Permission, Boolean> adminPermissions = new HashMap<Permission, Boolean>() {{
            put(VIEW_ASSIGNED_STAFF, true);
            put(VIEW_REQUEST_STATUS, true);
            put(APPROVE_TEACHING_REQUEST, true);
        }};
        this.permissions.putAll(adminPermissions);
    }

}
