package kernel.jdon.moduledomain.auth.dto.object;

import kernel.jdon.moduledomain.jobcategory.domain.JobCategory;
import kernel.jdon.moduledomain.member.domain.MemberRole;
import kernel.jdon.moduledomain.member.domain.SocialProviderType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class RegisterMemberDto {
    private String email;
    private MemberRole memberRole;
    private SocialProviderType socialProvider;
    private JobCategory jobCategory;
}
