package kernel.jdon.moduleapi.domain.skill.infrastructure;

import static kernel.jdon.memberskill.domain.QMemberSkill.*;
import static kernel.jdon.skill.domain.QSkill.*;
import static kernel.jdon.skillhistory.domain.QSkillHistory.*;

import java.util.List;

import com.querydsl.jpa.impl.JPAQueryFactory;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class SkillRepositoryImpl implements SkillRepositoryCustom {

	private final JPAQueryFactory jpaQueryFactory;

	@Override
	public List<SkillReaderInfo.FindHotSkill> findHotSkillList() {
		final int hotSkillKeywordCount = 10;

		return jpaQueryFactory
			.select(new QSkillReaderInfo_FindHotSkill(skill.id, skill.keyword))
			.from(skillHistory)
			.innerJoin(skill)
			.on(skillHistory.jobCategory.id.eq(skill.jobCategory.id)
				.and(skillHistory.keyword.eq(skill.keyword)))
			.groupBy(skill.id, skill.keyword)
			.orderBy(skill.keyword.count().desc())
			.limit(hotSkillKeywordCount)
			.fetch();
	}

	@Override
	public List<SkillReaderInfo.FindMemberSkill> findMemberSkillList(final Long memberId) {
		return jpaQueryFactory
			.select(new QSkillReaderInfo_FindMemberSkill(skill.id, skill.keyword))
			.from(memberSkill)
			.leftJoin(skill)
			.on(memberSkill.skill.id.eq(skill.id))
			.where(memberSkill.member.id.eq(memberId))
			.fetch();
	}
}
