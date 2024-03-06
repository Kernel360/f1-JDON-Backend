package kernel.jdon.moduleapi.domain.review.application;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.BDDMockito.*;

import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import kernel.jdon.moduleapi.domain.review.core.ReviewCommand;
import kernel.jdon.moduleapi.domain.review.core.ReviewInfo;
import kernel.jdon.moduleapi.domain.review.core.ReviewService;
import kernel.jdon.moduleapi.global.page.CustomPageInfo;
import kernel.jdon.moduleapi.global.page.PageInfoRequest;

@DisplayName("Review Facade 테스트")
@ExtendWith(MockitoExtension.class)
class ReviewFacadeTest {
	@InjectMocks
	private ReviewFacade reviewFacade;
	@Mock
	private ReviewService reviewService;

	@Test
	@DisplayName("1: 리뷰 생성 정보가 주어졌을 때, createReview 메서드가 응답값을 반환한다.")
	void givenCreateReviewCommand_whenCreateReview_thenCreateReviewInfo() throws Exception {
		//given
		final var mockRequestCommand = mockRequestCommand();
		final var mockRequestInfo = mock(ReviewInfo.CreateReviewResponse.class);
		given(reviewService.createReview(mockRequestCommand)).willReturn(mockRequestInfo);

		//when
		final var response = reviewFacade.createReview(mockRequestCommand);

		//then
		assertThat(response).isEqualTo(mockRequestInfo);
		then(reviewService).should(times(1)).createReview(mockRequestCommand);
	}

	private ReviewCommand.CreateReviewRequest mockRequestCommand() {
		return ReviewCommand.CreateReviewRequest.builder().build();
	}

	@Test
	@DisplayName("2: JD id와 pageinfoRequest가 주어졌을 때, getReviewList 메서드가 리뷰 목록을 반환한다.")
	void givenJdIdAndPageInfoRequest_whenGetReviewList_thenCollectFindReviewListInfo() throws Exception {
		//given
		final var jdId = 1L;
		final var mockPageInfoRequest = mock(PageInfoRequest.class);
		final var mockFindReviewListInfo = new ReviewInfo.FindReviewListResponse(
			List.of(
				mock(ReviewInfo.FindReview.class),
				mock(ReviewInfo.FindReview.class)),
			mock(CustomPageInfo.class));
		given(reviewService.getReviewList(jdId, mockPageInfoRequest)).willReturn(mockFindReviewListInfo);

		//when
		var response = reviewFacade.getReviewList(jdId, mockPageInfoRequest);

		//then
		assertThat(response.getContent()).hasSize(2);
		then(reviewService).should(times(1)).getReviewList(jdId, mockPageInfoRequest);
	}

	@Test
	@DisplayName("3: 리뷰 삭제 정보가 주어졌을 때, removeReview 메서드가 reviewStore의 delete를 실행한다.")
	void givenValidWriter_whenRemoveReview_thenExecuteDeleteReview() throws Exception {
		//given
		final var mockDeleteCommand = mockDeleteCommand();
		willDoNothing().given(reviewService).removeReview(mockDeleteCommand);

		//when
		reviewFacade.removeReview(mockDeleteCommand);

		//then
		then(reviewService).should(times(1)).removeReview(mockDeleteCommand);
	}

	private ReviewCommand.DeleteReviewRequest mockDeleteCommand() {
		return ReviewCommand.DeleteReviewRequest.builder()
			.reviewId(1L)
			.memberId(1L)
			.build();
	}
}