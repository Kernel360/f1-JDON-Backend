package kernel.jdon.moduleapi.domain.jd.infrastructure;

import static kernel.jdon.moduledomain.jobcategory.domain.QJobCategory.*;
import static kernel.jdon.moduledomain.wantedjd.domain.QWantedJd.*;
import static kernel.jdon.moduledomain.wantedjdskill.domain.QWantedJdSkill.*;
import static org.springframework.util.StringUtils.*;

import java.util.List;
import java.util.Objects;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;

import kernel.jdon.moduleapi.domain.jd.core.JdSearchTypeCondition;
import kernel.jdon.moduleapi.domain.jd.core.JdSortTypeCondition;
import kernel.jdon.moduleapi.domain.jd.presentation.JdCondition;
import kernel.jdon.moduleapi.domain.skill.infrastructure.SkillKeywordManager;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class WantedJdRepositoryImpl implements CustomWantedJdRepository {
	private final JPAQueryFactory jpaQueryFactory;
	private final SkillKeywordManager skillKeywordManager;

	public Page<JdReaderInfo.FindWantedJd> findWantedJdList(final Pageable pageable, final JdCondition jdCondition) {
		List<JdReaderInfo.FindWantedJd> content = jpaQueryFactory
			.select(new QJdReaderInfo_FindWantedJd(
				wantedJd.id,
				wantedJd.title,
				wantedJd.companyName,
				wantedJd.imageUrl,
				jobCategory.name))
			.from(wantedJd)
			.join(jobCategory).on(wantedJd.jobCategory.eq(jobCategory))
			.where(searchWantedJdList(jdCondition))
			.orderBy(createOrderSpecifier(jdCondition.getSort()))
			.offset(pageable.getOffset())
			.limit(pageable.getPageSize())
			.fetch();

		Long totalCount = jpaQueryFactory
			.select(wantedJd.count())
			.from(wantedJd)
			.where(searchWantedJdList(jdCondition))
			.fetchOne();

		return new PageImpl<>(content, pageable, totalCount);
	}

	private BooleanBuilder searchWantedJdList(JdCondition jdCondition) {
		BooleanBuilder searchCondition = new BooleanBuilder();

		if (jdCondition != null) {
			searchCondition.and(wantedJdSkillContains(jdCondition.getSkill()));
			searchCondition.and(wantedJdJobCategoryContains(jdCondition.getJobCategory()));
			searchCondition.and(wantedJdKeywordContains(jdCondition.getKeywordType(), jdCondition.getKeyword()));
		}

		return searchCondition;
	}

	private BooleanExpression wantedJdKeywordContains(final JdSearchTypeCondition keywordType, final String keyword) {
		return switch (keywordType) {
			case COMPANY -> wantedJdCompanyContains(keyword);
			default -> wantedJdTitleContains(keyword);
		};
	}

	private BooleanExpression wantedJdCompanyContains(final String keyword) {
		return hasText(keyword) ?
			wantedJd.companyName.contains(keyword)
			: null;
	}

	private BooleanExpression wantedJdTitleContains(final String keyword) {
		return hasText(keyword) ? wantedJd.title.contains(keyword) : null;
	}

	private BooleanExpression wantedJdJobCategoryContains(final Long jobCategory) {
		return Objects.nonNull(jobCategory) ?
			wantedJd.jobCategory.id.eq(jobCategory)
			: null;
	}

	private BooleanExpression wantedJdSkillContains(final String skill) {
		final List<String> allKeywordAssociatedTermList = skillKeywordManager
			.getAllKeywordAssociatedTermsByKeyword(skill);

		return hasText(skill) ?
			wantedJd.id.in(JPAExpressions.select(wantedJdSkill.wantedJd.id)
				.from(wantedJdSkill)
				.where(wantedJdSkill.skill.keyword.in(allKeywordAssociatedTermList)))
			: null;
	}

	private OrderSpecifier createOrderSpecifier(final JdSortTypeCondition sort) {
		return switch (sort) {
			case REVIEW -> new OrderSpecifier<>(Order.DESC, wantedJd.reviewList.size());
			default -> new OrderSpecifier<>(Order.DESC, wantedJd.scrapingDate);
		};
	}
}
