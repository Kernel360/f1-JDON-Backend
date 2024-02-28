package kernel.jdon.moduleapi.domain.jd.infrastructure;

import static kernel.jdon.moduledomain.jobcategory.domain.QJobCategory.*;
import static kernel.jdon.moduledomain.wantedjd.domain.QWantedJd.*;
import static org.springframework.util.StringUtils.*;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class WantedJdRepositoryImpl implements CustomWantedJdRepository {
	private final JPAQueryFactory jpaQueryFactory;

	@Override
	public Page<JdReaderInfo.FindWantedJd> findWantedJdList(final Pageable pageable, final String keyword) {

		List<JdReaderInfo.FindWantedJd> content = jpaQueryFactory
			.select(new QJdReaderInfo_FindWantedJd(
				wantedJd.id,
				wantedJd.title,
				wantedJd.companyName,
				wantedJd.imageUrl,
				jobCategory.name))
			.from(wantedJd)
			.join(jobCategory)
			.on(wantedJd.jobCategory.eq(jobCategory))
			.where(wantedJdTitleContains(keyword))
			.orderBy(wantedJd.scrapingDate.desc())
			.offset(pageable.getOffset())
			.limit(pageable.getPageSize())
			.fetch();

		Long totalCount = jpaQueryFactory
			.select(wantedJd.count())
			.from(wantedJd)
			.where(wantedJdTitleContains(keyword))
			.fetchOne();

		return new PageImpl<>(content, pageable, totalCount);
	}

	private BooleanExpression wantedJdTitleContains(String keyword) {
		return hasText(keyword) ? wantedJd.title.contains(keyword) : null;
	}
}
