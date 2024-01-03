package kernel.jdon.crawler.wanted.converter;

import kernel.jdon.crawler.wanted.dto.object.CreateSkillDto;
import kernel.jdon.crawler.wanted.dto.response.WantedJobDetailResponse;
import kernel.jdon.skill.domain.Skill;
import kernel.jdon.wanted.domain.WantedJd;
import kernel.jdon.wantedskill.domain.WantedJdSkill;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class EntityFactory {

	public static WantedJdSkill createWantedJdSkill(WantedJd wantedJd, Skill skill) {
		return WantedJdSkill.builder()
			.wantedJd(wantedJd)
			.skill(skill)
			.build();
	}

	public static WantedJd createWantedJd(WantedJobDetailResponse wantedJobDetailResponse) {
		return WantedJd.builder()
			.jobCategory(wantedJobDetailResponse.getJobCategory())
			.companyName(wantedJobDetailResponse.getJob().getCompany().getName())
			.detailId(wantedJobDetailResponse.getJob().getId())
			.detailUrl(wantedJobDetailResponse.getDetailUrl())
			.imageUrl(wantedJobDetailResponse.getJob().getCompanyImages())
			.build();
	}

	public static Skill createSkill(CreateSkillDto createSkillDto) {
		return Skill.builder()
			.keyword(createSkillDto.getKeyword())
			.count(createSkillDto.getCount())
			.jobCategory(createSkillDto.getJobCategory())
			.build();
	}
}
