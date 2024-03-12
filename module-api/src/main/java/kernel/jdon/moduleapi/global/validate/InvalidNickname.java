package kernel.jdon.moduleapi.global.validate;

import java.util.List;
import java.util.Objects;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class InvalidNickname {
    private static List<String> invalidNicknames = List.of("admin", "administer", "어드민", "관리자");

    public static boolean isInvalid(String keyword) {
        String findInvalidKeyword = invalidNicknames.stream()
            .filter(e -> keyword.toUpperCase().contains(e.toUpperCase()))
            .findAny()
            .orElse(null);

        return Objects.isNull(findInvalidKeyword);
    }
}
