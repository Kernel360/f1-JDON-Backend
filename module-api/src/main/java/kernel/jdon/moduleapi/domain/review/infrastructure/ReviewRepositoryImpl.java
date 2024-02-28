package kernel.jdon.moduleapi.domain.review.infrastructure;

import static kernel.jdon.moduledomain.member.domain.QMember.*;
import static kernel.jdon.moduledomain.review.domain.QReview.*;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import com.querydsl.jpa.impl.JPAQueryFactory;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class ReviewRepositoryImpl implements CustomReviewRepository {
	private final JPAQueryFactory jpaQueryFactory;

	@Override
	public Page<ReviewReaderInfo.FindReview> findReviewList(final Long jdId, final Pageable pageable) {
		final List<ReviewReaderInfo.FindReview> content = jpaQueryFactory
			.select(new QReviewReaderInfo_FindReview(
				review.id,
				review.content,
				member.nickname,
				review.createdDate,
				member.id
			))
			.from(review)
			.join(member)
			.on(review.member.eq(member))
			.where(review.wantedJd.id.eq(jdId))
			.offset(pageable.getOffset())
			.limit(pageable.getPageSize())
			.fetch();

		final Long totalCount = jpaQueryFactory
			.select(review.count())
			.from(review)
			.where(review.wantedJd.id.eq(jdId))
			.fetchOne();

		return new PageImpl<>(content, pageable, totalCount);
	}
}
