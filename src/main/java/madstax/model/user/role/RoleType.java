package madstax.model.user.role;

public enum RoleType {
    ADMIN("Administrator"),
    CLASS_DIRECTOR("Class Director"),
    PTT_DIRECTOR("PTT Director");

    private String name;

    RoleType(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }
}
