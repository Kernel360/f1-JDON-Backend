package kernel.jdon.skill.repository;

import static kernel.jdon.favorite.domain.QFavorite.*;
import static kernel.jdon.inflearncourse.domain.QInflearnCourse.*;
import static kernel.jdon.inflearnjdskill.domain.QInflearnJdSkill.*;
import static kernel.jdon.memberskill.domain.QMemberSkill.*;
import static kernel.jdon.skill.domain.QSkill.*;
import static kernel.jdon.skillhistory.domain.QSkillHistory.*;
import static kernel.jdon.wantedjd.domain.QWantedJd.*;
import static kernel.jdon.wantedjdskill.domain.QWantedJdSkill.*;

import java.util.List;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;

import kernel.jdon.skill.dto.object.FindHotSkillDto;
import kernel.jdon.skill.dto.object.FindLectureDto;
import kernel.jdon.skill.dto.object.FindMemberSkillDto;
import kernel.jdon.skill.dto.object.FindWantedJdDto;
import kernel.jdon.skill.dto.object.QFindHotSkillDto;
import kernel.jdon.skill.dto.object.QFindLectureDto;
import kernel.jdon.skill.dto.object.QFindMemberSkillDto;
import kernel.jdon.skill.dto.object.QFindWantedJdDto;
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
	public List<FindMemberSkillDto> findMemberSkillList(final Long memberId) {
		return jpaQueryFactory
			.select(new QFindMemberSkillDto(skill.id, skill.keyword))
			.from(memberSkill)
			.leftJoin(skill)
			.on(memberSkill.skill.id.eq(skill.id))
			.where(memberSkill.member.id.eq(memberId))
			.fetch();
	}

	@Override
	public List<FindWantedJdDto> findWantedJdListBySkill(final String keyword) {
		final int wantedJdCount = 6;

		return jpaQueryFactory
			.select(new QFindWantedJdDto(wantedJd.companyName, wantedJd.title, wantedJd.imageUrl, wantedJd.detailUrl))
			.from(wantedJdSkill)
			.innerJoin(wantedJd)
			.on(wantedJdSkill.wantedJd.id.eq(wantedJd.id))
			.where(
				wantedJdSkill.skill.id.in(
					JPAExpressions
						.select(skill.id)
						.from(skill)
						.where(skill.keyword.eq(keyword))))
			.orderBy(Expressions.numberTemplate(Double.class, "function('rand')").asc())
			.limit(wantedJdCount)
			.fetch();
	}

	@Override
	public List<FindLectureDto> findInflearnLectureListBySkill(final String keyword, final Long userId) {
		final int inflearnLectureCount = 3;

		return jpaQueryFactory
			.select(new QFindLectureDto(inflearnCourse.id, inflearnCourse.title, inflearnCourse.lectureUrl,
				inflearnCourse.imageUrl, inflearnCourse.instructor, inflearnCourse.studentCount, inflearnCourse.price,
				isFavoriteLecture(userId)
			))
			.from(inflearnJdSkill)
			.innerJoin(inflearnCourse)
			.on(inflearnJdSkill.inflearnCourse.id.eq(inflearnCourse.id))
			.where(
				inflearnJdSkill.skill.id.in(
					JPAExpressions
						.select(skill.id)
						.from(skill)
						.where(skill.keyword.eq(keyword))))
			.limit(inflearnLectureCount)
			.fetch();
	}

	private BooleanExpression isFavoriteLecture(Long userId) {
		return userId == null ? Expressions.asBoolean(false) : isFavoriteLectureByMember(userId);
	}

	private BooleanExpression isFavoriteLectureByMember(Long userId) {
		return Expressions.cases()
			.when(
				JPAExpressions
					.select(favorite)
					.from(favorite)
					.where(favorite.inflearnCourse.id.eq(inflearnCourse.id)
						.and(favorite.member.id.eq(userId)))
					.exists()
			)
			.then(true)
			.otherwise(false);
	}
}
