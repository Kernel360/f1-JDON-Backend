package kernel.jdon.modulecommon.util;

import java.util.Arrays;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class StringUtil {
    public static String createQueryString(String key, String value) {
        return joinToString(key, "=", value, "&");
    }

    public static String createPathString(String value) {
        return joinToString("/", value);
    }

    public static String joinToString(Object... args) {
        StringBuilder sb = new StringBuilder();
        Arrays.stream(args).forEach(sb::append);

        return sb.toString();
    }

}
