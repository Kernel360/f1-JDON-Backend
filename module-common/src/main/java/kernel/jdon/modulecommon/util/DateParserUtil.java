package kernel.jdon.modulecommon.util;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class DateParserUtil {

    public static String localDateTimeToDateString(final LocalDateTime localDateTime) {
        return Optional.ofNullable(localDateTime)
            .map(item -> {
                String localDateTimeToString = item.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
                return localDateTimeToString.substring(0, 10);
            })
            .orElse("");
    }
}