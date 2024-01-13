package kernel.jdon.skill.repository;

import static kernel.jdon.skill.domain.QSkill.*;

import java.util.List;

import com.querydsl.jpa.impl.JPAQueryFactory;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class SkillRepositoryImpl implements SkillRepositoryCustom {

	private final JPAQueryFactory jpaQueryFactory;

	@Override
	public List<String> findHotSkillList() {
		final int hotSkillKeywordCount = 10;

		return jpaQueryFactory
			.select(skill.keyword)
			.from(skill)
			.groupBy(skill.keyword)
			.orderBy(skill.keyword.count().desc())
			.limit(hotSkillKeywordCount)
			.fetch();
	}
}
