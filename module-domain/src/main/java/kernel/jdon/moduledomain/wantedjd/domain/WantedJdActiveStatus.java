package kernel.jdon.moduledomain.wantedjd.domain;

public enum WantedJdActiveStatus {
    OPEN("모집중"),
    CLOSE("모집종료");

    private final String activeStatus;

    WantedJdActiveStatus(String activeStatus) {
        this.activeStatus = activeStatus;
    }

    public String getActiveStatus() {
        return activeStatus;
    }
}
