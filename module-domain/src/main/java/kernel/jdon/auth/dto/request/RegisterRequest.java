package kernel.jdon.auth.dto.request;

import java.time.LocalDate;
import java.util.List;

import kernel.jdon.auth.dto.object.RegisterMemberDto;
import kernel.jdon.member.domain.Gender;
import kernel.jdon.member.domain.Member;
import kernel.jdon.member.domain.MemberAccountStatus;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class RegisterRequest {
	private String email;
	private String nickname;
	private LocalDate birthday;
	private String gender;
	private Long jobCategoryId;
	private List<Long> skillList;

	public Member toEntity(RegisterMemberDto registerMemberDto) {
		return Member.builder()
			.email(email)
			.nickname(nickname)
			.birth(birthday.toString())
			.gender(Gender.valueOf(gender))
			.role(registerMemberDto.getMemberRole())
			.accountStatus(MemberAccountStatus.ACTIVE)
			.socialProvider(registerMemberDto.getSocialProvider())
			.jobCategory(registerMemberDto.getJobCategory())
			.build();
	}
}
