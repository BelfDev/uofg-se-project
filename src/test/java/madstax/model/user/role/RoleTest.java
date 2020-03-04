package madstax.model.user.role;

import madstax.controller.NavigationController;
import madstax.model.user.Permission;
import madstax.model.user.User;
import org.junit.jupiter.api.*;

import java.util.TreeSet;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.TestInstance.Lifecycle.PER_CLASS;

@TestInstance(PER_CLASS)
public class RoleTest {
    private User user;
    private TreeSet<Permission> userPermissions;

    @BeforeAll
    void setupTests() {
        user = new User();
        user.setRole(RoleFactory.getRole(RoleType.CLASS_DIRECTOR));
        userPermissions = user.getRole().getPermissions();
    }

    @Test
    @DisplayName("Role permissions should not be empty")
    void userPermissions_notEmpty() {
        assertFalse(userPermissions.isEmpty());
    }

    @Test
    @DisplayName("Role should have required permissions")
    void userPermissions_containRequiredPermissions() {
        assertTrue(userPermissions.contains(Permission.ADD_REQUIREMENT));
    }

    @Test
    @DisplayName("Role should not have unnecessary permissions")
    void userPermission_notContainExcessivePermissions() {
        assertFalse(userPermissions.contains(Permission.APPROVE_TEACHING_REQUEST));
    }

}
