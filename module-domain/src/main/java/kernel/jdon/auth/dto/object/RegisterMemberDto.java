package kernel.jdon.auth.dto.object;

import kernel.jdon.jobcategory.domain.JobCategory;
import kernel.jdon.member.domain.MemberRole;
import kernel.jdon.member.domain.SocialProviderType;
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
