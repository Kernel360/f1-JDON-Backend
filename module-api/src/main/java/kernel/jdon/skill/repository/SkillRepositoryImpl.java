package kernel.jdon.skill.repository;

import static kernel.jdon.memberskill.domain.QMemberSkill.*;
import static kernel.jdon.skill.domain.QSkill.*;
import static kernel.jdon.skillhistory.domain.QSkillHistory.*;

import java.util.List;

import com.querydsl.jpa.impl.JPAQueryFactory;

import kernel.jdon.skill.dto.object.FindHotSkillDto;
import kernel.jdon.skill.dto.object.FindMemberSkillDto;
import kernel.jdon.skill.dto.object.QFindHotSkillDto;
import kernel.jdon.skill.dto.object.QFindMemberSkillDto;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class SkillRepositoryImpl implements SkillRepositoryCustom {

	private final JPAQueryFactory jpaQueryFactory;

	@Override
	public List<FindHotSkillDto> findHotSkillList() {
		final int hotSkillKeywordCount = 10;

		return jpaQueryFactory
				.select(new QFindHotSkillDto(skill.id, skill.keyword))
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
	public List<FindMemberSkillDto> findMemberSkillList(Long memberId) {
		return jpaQueryFactory
				.select(new QFindMemberSkillDto(skill.id, skill.keyword))
				.from(memberSkill)
				.leftJoin(skill)
				.on(memberSkill.skill.id.eq(skill.id))
				.where(memberSkill.member.id.eq(memberId))
				.fetch();
	}
}
