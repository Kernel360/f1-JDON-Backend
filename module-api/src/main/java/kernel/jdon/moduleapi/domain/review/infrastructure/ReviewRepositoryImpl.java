package kernel.jdon.moduleapi.domain.review.infrastructure;

import static kernel.jdon.moduledomain.member.domain.QMember.*;
import static kernel.jdon.moduledomain.review.domain.QReview.*;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.SliceImpl;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class ReviewRepositoryImpl implements CustomReviewRepository {
	private final JPAQueryFactory jpaQueryFactory;

	@Override
	public Slice<ReviewReaderInfo.FindReview> findReviewList(final Long jdId, final Pageable pageable,
		final Long reviewId) {
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
			.where(
				reviewIdIt(reviewId),
				review.wantedJd.id.eq(jdId)
			)
			.limit(pageable.getPageSize())
			.orderBy(review.createdDate.desc())
			.fetch();

		return new SliceImpl<>(content, pageable, hasNextPage(content, pageable.getPageSize()));
	}

	private boolean hasNextPage(final List<ReviewReaderInfo.FindReview> content, final int pageSize) {
		return content.size() > pageSize - 1;
	}

	private BooleanExpression reviewIdIt(final Long reviewId) {
		if (reviewId == null) {
			return null;
		}
		return review.id.lt(reviewId);
	}
}
