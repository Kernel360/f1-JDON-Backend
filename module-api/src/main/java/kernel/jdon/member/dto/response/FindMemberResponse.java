package kernel.jdon.member.dto.response;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

import kernel.jdon.member.domain.Member;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class FindMemberResponse {
	private String email;
	private String nickname;
	private String birth;
	private String gender;
	private Long jobCategoryId;
	private List<Long> skillList;

	public static FindMemberResponse of(Member member) {
		List<Long> skillList = member.getMemberSkillList().stream()
			.map(memberSkill -> memberSkill.getSkill().getId())
			.collect(Collectors.toList());

		return FindMemberResponse.builder()
			.email(member.getEmail())
			.nickname(member.getNickname())
			.birth(member.getBirth())
			.gender(member.getGender().getGender())
			.jobCategoryId(member.getJobCategory().getId())
			.skillList(skillList)
			.build();
	}
}
