package kernel.jdon.member.dto.request;

import java.time.LocalDate;
import java.util.List;

import kernel.jdon.jobcategory.domain.JobCategory;
import kernel.jdon.member.domain.Gender;
import kernel.jdon.member.domain.Member;
import kernel.jdon.memberskill.domain.MemberSkill;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class UpdateMemberRequest {
	private String nickname;
	private LocalDate birth;
	private String gender;
	private Long jobCategoryId;
	private List<Long> skillList;

	public Member toUpdateEntity(JobCategory jobCategory) {
		return Member.builder()
			.nickname(nickname)
			.birth(birth.toString())
			.gender(Gender.ofType(gender))
			.jobCategory(jobCategory)
			.build();
	}
}