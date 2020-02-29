package madstax.model.user.role;

import madstax.model.user.Permission;

import java.util.HashMap;
import java.util.TreeSet;

import static madstax.model.user.Permission.*;

class ClassDirector extends Role {

    public ClassDirector() {
        super("Class Director");
        TreeSet<Permission> classDirectorPermissions = new TreeSet<Permission>() {{
            add(ADD_REQUIREMENT);
        }};
        this.permissions.addAll(classDirectorPermissions);
    }

}
