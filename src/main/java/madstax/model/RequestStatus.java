package madstax.model;

public enum RequestStatus {
    UNASSIGNED("Unassigned"),
    AWAITING_APPROVAL("Awaiting Approval"),
    APPROVED("Approved"),
    DENIED("Denied");

    private String displayName;

    RequestStatus(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }

}
