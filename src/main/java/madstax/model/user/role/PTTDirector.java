package madstax.model.user.role;

import madstax.model.user.Permission;

import java.util.TreeSet;

import static madstax.model.user.Permission.APPROVE_TEACHING_REQUEST;

class PTTDirector extends Role {

    public PTTDirector() {
        super("PTT Director");
        TreeSet<Permission> pttDirectorPermissions = new TreeSet<Permission>() {{
            add(APPROVE_TEACHING_REQUEST);
        }};
        this.permissions.addAll(pttDirectorPermissions);
    }

}
