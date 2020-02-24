package madstax.model.user.role;

import madstax.model.user.Permission;

import java.util.HashMap;

import static madstax.model.user.Permission.VIEW_REQUIREMENT_LIST;

public abstract class Role {

    protected String name;
    protected HashMap<Permission, Boolean> permissions;

    public Role(String name) {
        this.name = name;
        this.permissions = getDefaultPermissions();
    }

    // Returns permission map with all values set to false
    private HashMap<Permission, Boolean> getDefaultPermissions() {
        HashMap<Permission, Boolean> defaultPermissions = new HashMap<>();
        for (Permission permission : Permission.values()) {
            defaultPermissions.put(permission, false);
        }

        defaultPermissions.put(VIEW_REQUIREMENT_LIST, true);

        return defaultPermissions;
    }

    public String getName() {
        return name;
    }

    public HashMap<Permission, Boolean> getPermissions() {
        return permissions;
    }

}
