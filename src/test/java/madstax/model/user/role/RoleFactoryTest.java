package madstax.model.user.role;

import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

public class RoleFactoryTest {

    @Test
    @DisplayName("Returns correct role class based on role type")
    void getRole_RoleType_Role() {
        Role newRole = RoleFactory.getRole(RoleType.CLASS_DIRECTOR);
        assertTrue(newRole instanceof ClassDirector);
    }
}