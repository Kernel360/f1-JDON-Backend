package kernel.jdon.crawler.wanted.converter;

import kernel.jdon.crawler.wanted.dto.object.CreateSkillDto;
import kernel.jdon.crawler.wanted.dto.response.WantedJobDetailResponse;
import kernel.jdon.skill.domain.Skill;
import kernel.jdon.wantedjd.domain.WantedJd;
import kernel.jdon.wantedjdskill.domain.WantedJdSkill;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class EntityConverter {
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
			.title(wantedJobDetailResponse.getJob().getTitle())
			.detailId(wantedJobDetailResponse.getJob().getId())
			.detailUrl(wantedJobDetailResponse.getDetailUrl())
			.imageUrl(wantedJobDetailResponse.getJob().getCompanyImages())
			.requirements(wantedJobDetailResponse.getJob().getDetail().getRequirements())
			.mainTasks(wantedJobDetailResponse.getJob().getDetail().getMainTasks())
			.intro(wantedJobDetailResponse.getJob().getDetail().getIntro())
			.benefits(wantedJobDetailResponse.getJob().getDetail().getBenefits())
			.preferredPoints(wantedJobDetailResponse.getJob().getDetail().getPreferredPoints())
			.build();
	}

	public static Skill createSkill(CreateSkillDto createSkillDto) {
		return Skill.builder()
			.keyword(createSkillDto.getKeyword())
			.jobCategory(createSkillDto.getJobCategory())
			.build();
	}
}
