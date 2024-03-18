package kernel.jdon.moduledomain.wantedjd.domain;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

public enum WantedJdActiveStatus {
    OPEN("모집중"),
    CLOSE("모집종료"),
    ALWAYS("상시채용");

    private final String activeStatus;

    WantedJdActiveStatus(String activeStatus) {
        this.activeStatus = activeStatus;
    }

    public static WantedJdActiveStatus getWantedJdActiveStatus(String deadLineDateToString) {
        return Optional.ofNullable(deadLineDateToString)
            .map(str -> {
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                LocalDateTime deadLineDate = LocalDate.parse(deadLineDateToString, formatter).atStartOfDay();
                LocalDateTime today = LocalDateTime.now();
                return today.isBefore(deadLineDate) || today.isEqual(deadLineDate) ? OPEN : CLOSE;
            })
            .orElse(ALWAYS);
    }

    public String getActiveStatus() {
        return activeStatus;
    }
}
