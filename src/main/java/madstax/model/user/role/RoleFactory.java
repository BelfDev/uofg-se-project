package madstax.model.user.role;

public class RoleFactory {

    public static Role getRole(RoleType type) {
        switch (type) {
            case ADMIN:
                return new Admin();
            case CLASS_DIRECTOR:
                return new ClassDirector();
            case PTT_DIRECTOR:
                return new PTTDirector();
        }
        return null;
    }

}
