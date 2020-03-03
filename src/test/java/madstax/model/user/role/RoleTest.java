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
    @DisplayName("Permission should not be empty")
    void shouldNotReturnEmptyPermissions() {
        assertFalse(userPermissions.isEmpty());
    }

    @Test
    @DisplayName("Should have required permissions")
    void shouldHaveRequiredPermissions() {
        assertTrue(userPermissions.contains(Permission.ADD_REQUIREMENT));
    }

    @Test
    @DisplayName("Should not have unnecessary permissions")
    void shouldHaveUnnecessaryPermissions() {
        assertFalse(userPermissions.contains(Permission.APPROVE_TEACHING_REQUEST));
    }

}
