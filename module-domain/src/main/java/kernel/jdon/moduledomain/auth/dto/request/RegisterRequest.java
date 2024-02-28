package kernel.jdon.moduledomain.auth.dto.request;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import kernel.jdon.moduledomain.auth.dto.object.RegisterMemberDto;
import kernel.jdon.moduledomain.member.domain.Gender;
import kernel.jdon.moduledomain.member.domain.Member;
import kernel.jdon.moduledomain.member.domain.MemberAccountStatus;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class RegisterRequest {
	private String encrypted;
	private String hmac;
	private String nickname;
	private LocalDate birth;
	private String gender;
	private Long jobCategoryId;
	private List<Long> skillList;

	public Member toEntity(RegisterMemberDto registerMemberDto) {
		return Member.builder()
			.email(registerMemberDto.getEmail())
			.nickname(nickname)
			.birth(birth.toString())
			.gender(Gender.ofType(gender))
			.role(registerMemberDto.getMemberRole())
			.accountStatus(MemberAccountStatus.ACTIVE)
			.socialProvider(registerMemberDto.getSocialProvider())
			.jobCategory(registerMemberDto.getJobCategory())
			.joinDate(LocalDateTime.now())
			.build();
	}
}
