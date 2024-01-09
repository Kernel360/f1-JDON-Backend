package kernel.jdon.crawler.common.repository;

import java.util.List;

import com.querydsl.jpa.impl.JPAQueryFactory;

import kernel.jdon.skill.domain.Skill;
import kernel.jdon.wantedskill.domain.QWantedJdSkill;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class WantedJdSkillRepositoryCustomImpl implements WantedJdSkillRepositoryCustom {

	public final JPAQueryFactory queryFactory;

	@Override
	public List<Skill> findAllSkills() {
		QWantedJdSkill wantedJdSkill = QWantedJdSkill.wantedJdSkill;

		return null;
		// return queryFactory
		// 	.selectDistinct(wantedJdSkill);
	}
}
