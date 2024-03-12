package kernel.jdon.moduleapi.domain.member.core;

import java.time.LocalDateTime;
import java.util.List;

import kernel.jdon.moduledomain.member.domain.MemberRole;
import kernel.jdon.moduledomain.member.domain.SocialProviderType;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class MemberCommand {

    @Getter
    @Builder
    public static class UpdateMemberRequest {
        private final String nickname;
        private final String birth;
        private final String gender;
        private final Long jobCategoryId;
        private final List<Long> skillList;
    }

    @Getter
    @Builder
    public static class NicknameDuplicateRequest {
        private final String nickname;
    }

    @Getter
    @Builder
    public static class RegisterRequest {
        private final String encrypted;
        private final String hmac;
        private final String nickname;
        private final String birth;
        private final String gender;
        private final Long jobCategoryId;
        private final List<Long> skillList;
    }

    @Getter
    @Builder
    public static class WithdrawRequest {
        private final Long id;
        private final String email;
        private final String oauthId;
        private final SocialProviderType socialProvider;
        private final LocalDateTime lastLoginDate;
        private final MemberRole role;
    }
}
