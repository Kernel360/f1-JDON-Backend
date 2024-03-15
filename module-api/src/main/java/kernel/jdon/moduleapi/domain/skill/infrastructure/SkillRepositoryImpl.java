package kernel.jdon.moduleapi.domain.skill.infrastructure;

import static kernel.jdon.moduledomain.favorite.domain.QFavorite.*;
import static kernel.jdon.moduledomain.inflearncourse.domain.QInflearnCourse.*;
import static kernel.jdon.moduledomain.inflearnjdskill.domain.QInflearnJdSkill.*;
import static kernel.jdon.moduledomain.memberskill.domain.QMemberSkill.*;
import static kernel.jdon.moduledomain.skill.domain.QSkill.*;
import static kernel.jdon.moduledomain.skillhistory.domain.QSkillHistory.*;
import static kernel.jdon.moduledomain.wantedjd.domain.QWantedJd.*;
import static kernel.jdon.moduledomain.wantedjdskill.domain.QWantedJdSkill.*;

import java.util.List;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class SkillRepositoryImpl implements CustomSkillRepository {

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

    @Override
    public List<SkillReaderInfo.FindWantedJd> findWantedJdListBySkill(final List<String> originSkillKeywordList) {
        final int wantedJdCount = 6;

        return jpaQueryFactory
            .select(
                new QSkillReaderInfo_FindWantedJd(wantedJd.id, wantedJd.companyName, wantedJd.title, wantedJd.imageUrl,
                    wantedJd.detailUrl))
            .from(wantedJdSkill)
            .innerJoin(wantedJd)
            .on(wantedJdSkill.wantedJd.id.eq(wantedJd.id))
            .where(
                wantedJdSkill.skill.id.in(
                    JPAExpressions
                        .select(skill.id)
                        .from(skill)
                        .where(skill.keyword.in(originSkillKeywordList))))
            .orderBy(Expressions.numberTemplate(Double.class, "function('rand')").asc())
            .limit(wantedJdCount)
            .fetch();
    }

    @Override
    public List<SkillReaderInfo.FindInflearnLecture> findInflearnLectureListBySkill(
        final List<String> originSkillKeywordList, Long memberId) {
        final int inflearnLectureCount = 3;

        return jpaQueryFactory
            .select(new QSkillReaderInfo_FindInflearnLecture(inflearnCourse.id, inflearnCourse.title,
                inflearnCourse.lectureUrl,
                inflearnCourse.imageUrl, inflearnCourse.instructor, inflearnCourse.studentCount, inflearnCourse.price,
                isFavoriteLecture(memberId)
            ))
            .from(inflearnJdSkill)
            .innerJoin(inflearnCourse)
            .on(inflearnJdSkill.inflearnCourse.id.eq(inflearnCourse.id))
            .where(
                inflearnJdSkill.skill.id.in(
                    JPAExpressions
                        .select(skill.id)
                        .from(skill)
                        .where(skill.keyword.in(originSkillKeywordList))))
            .orderBy(Expressions.numberTemplate(Double.class, "function('rand')").asc())
            .limit(inflearnLectureCount)
            .fetch();
    }

    private BooleanExpression isFavoriteLecture(Long memberId) {
        return memberId == null ? Expressions.asBoolean(false) : isFavoriteLectureByMember(memberId);
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
