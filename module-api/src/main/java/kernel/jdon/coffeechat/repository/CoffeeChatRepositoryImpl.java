package kernel.jdon.coffeechat.repository;

import static kernel.jdon.coffeechat.domain.QCoffeeChat.*;
import static kernel.jdon.coffeechat.dto.request.CoffeeChatSortCondition.*;
import static kernel.jdon.member.domain.QMember.*;
import static org.springframework.util.StringUtils.*;

import java.util.ArrayList;

import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;

import kernel.jdon.coffeechat.dto.request.CoffeeChatSortCondition;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class CoffeeChatRepositoryImpl implements CustomCoffeeChatRepository {
	private final JPAQueryFactory jpaQueryFactory;

	// @Override
	// public Page<FindCoffeeChatListResponse> findCoffeeChatList(Pageable pageable,
	// 	CoffeeChatCondition coffeeChatCondition) {
	// 	List<FindCoffeeChatListResponse> content = jpaQueryFactory
	// 		.select(new QFindCoffeeChatListResponse(
	// 			coffeeChat.id,
	// 			member.nickname,
	// 			jobCategory.name,
	// 			coffeeChat.title,
	// 			coffeeChat.coffeeChatStatus,
	// 			coffeeChat.meetDate,
	// 			coffeeChat.createdDate,
	// 			coffeeChat.totalRecruitCount,
	// 			coffeeChat.currentRecruitCount))
	// 		.from(coffeeChat)
	// 		.join(member)
	// 		.on(coffeeChat.member.eq(member))
	// 		.join(jobCategory)
	// 		.on(member.jobCategory.eq(jobCategory))
	// 		.where(
	// 			excludeDeleteCoffeeChat(),
	// 			coffeeChatTitleContains(coffeeChatCondition.getKeyword()),
	// 			memberJobCategoryEq(coffeeChatCondition.getJobCategory())
	// 		)
	// 		.orderBy(
	// 			coffeeChatSort(coffeeChatCondition.getSort())
	// 		)
	// 		.offset(pageable.getOffset())
	// 		.limit(pageable.getPageSize())
	// 		.fetch();
	//
	// 	Long totalCount = jpaQueryFactory
	// 		.select(coffeeChat.count())
	// 		.from(coffeeChat)
	// 		.join(member)
	// 		.on(coffeeChat.member.eq(member))
	// 		.where(
	// 			excludeDeleteCoffeeChat(),
	// 			coffeeChatTitleContains(coffeeChatCondition.getKeyword()),
	// 			memberJobCategoryEq(coffeeChatCondition.getJobCategory())
	// 		)
	// 		.fetchOne();
	//
	// 	return new PageImpl<>(content, pageable, totalCount);
	//
	// }

	private OrderSpecifier[] coffeeChatSort(CoffeeChatSortCondition sort) {
		ArrayList<Object> orderSpecifiers = new ArrayList<>();
		if (VIEW_COUNT == sort)
			orderSpecifiers.add(new OrderSpecifier<>(Order.DESC, coffeeChat.viewCount));
		else
			orderSpecifiers.add(new OrderSpecifier<>(Order.DESC, coffeeChat.createdDate));

		return orderSpecifiers.toArray(new OrderSpecifier[orderSpecifiers.size()]);
	}

	private BooleanExpression memberJobCategoryEq(Long jobCategoryId) {
		return jobCategoryId != null ? member.jobCategory.id.eq(jobCategoryId) : null;
	}

	private BooleanExpression coffeeChatTitleContains(String keyword) {
		return hasText(keyword) ? coffeeChat.title.contains(keyword) : null;
	}

	private BooleanExpression excludeDeleteCoffeeChat() {
		return coffeeChat.isDeleted.eq(false);
	}

}
