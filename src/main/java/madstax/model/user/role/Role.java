package madstax.model.user.role;

import madstax.model.user.Permission;

import java.util.TreeSet;

import static madstax.model.user.Permission.VIEW_REQUIREMENT_LIST;

public abstract class Role {

    protected String name;
    protected TreeSet<Permission> permissions;

    public Role(String name) {
        this.name = name;
        this.permissions = getDefaultPermissions();
    }

    // Returns permission set with all values set to false
    private TreeSet<Permission> getDefaultPermissions() {
        return new TreeSet<Permission>() {{
            add(VIEW_REQUIREMENT_LIST);
        }};
    }

    public String getName() {
        return name;
    }

    public TreeSet<Permission> getPermissions() {
        return permissions;
    }

}
