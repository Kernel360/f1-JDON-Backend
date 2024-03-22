package kernel.jdon.modulecommon.util;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class DateParserUtil {

    public static String localDateTimeToDateString(final LocalDateTime localDateTime) {
        final String toString = localDateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));

        return toString.substring(0, 10);
    }
}