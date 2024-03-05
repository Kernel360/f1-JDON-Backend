package kernel.jdon.moduleapi.domain.review.core;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.io.IOException;
import java.util.List;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import kernel.jdon.moduleapi.domain.review.error.ReviewErrorCode;
import kernel.jdon.moduleapi.global.exception.ApiException;
import kernel.jdon.moduleapi.global.page.CustomPageInfo;
import kernel.jdon.moduleapi.global.page.PageInfoRequest;
import kernel.jdon.moduledomain.review.domain.Review;
import kernel.jdon.util.JsonFileReader;

@DisplayName("Review Service Impl 테스트")
@ExtendWith(MockitoExtension.class)
class ReviewServiceImplTest {
	@InjectMocks
	private ReviewServiceImpl reviewServiceImpl;
	@Mock
	private ReviewStore reviewStore;
	@Mock
	private ReviewReader reviewReader;
	@Mock
	private ReviewFactory reviewFactory;

	@Test
	@DisplayName("1: 리뷰 생성 정보가 주어졌을 때 createReview 메서드가 생성된 Review의 id를 반환한다.")
	void givenCreateReviewCommand_whenCreateReview_thenCreateReviewInfo() throws Exception {
		//given
		final var mockCreateCommand = mockCreateCommand();
		final var mockSavedReview = mockSavedReview();

		//when
		when(reviewFactory.saveReview(mockCreateCommand)).thenReturn(mockSavedReview);
		final var response = reviewServiceImpl.createReview(mockCreateCommand);

		//then
		assertThat(response.getReviewId()).isEqualTo(mockSavedReview().getId());
		verify(reviewFactory, times(1)).saveReview(mockCreateCommand);
	}

	private ReviewCommand.CreateReviewRequest mockCreateCommand() {
		return ReviewCommand.CreateReviewRequest.builder().content("content").build();
	}

	private Review mockSavedReview() throws IOException {
		String filePath = "giventest/review/serviceimpl/1_review.json";
		return JsonFileReader.readJsonFileToObject(filePath, Review.class);
	}

	@Test
	@DisplayName("2: JD id와 pageinfoRequest가 주어졌을 때, getReviewList 메서드가 리뷰 목록을 반환한다.")
	void givenJdIdAndPageInfoRequest_whenGetReviewList_thenCollectFindReviewListInfo() throws Exception {
		//given
		final var jdId = 1L;
		final var mockPageInfoRequest = mock(PageInfoRequest.class);
		final var mockFindReviewList = new ReviewInfo.FindReviewListResponse(
			List.of(
				mock(ReviewInfo.FindReview.class),
				mock(ReviewInfo.FindReview.class)),
			mock(CustomPageInfo.class));

		//when
		when(reviewReader.findReviewList(jdId, mockPageInfoRequest)).thenReturn(mockFindReviewList);
		var response = reviewServiceImpl.getReviewList(jdId, mockPageInfoRequest);

		//then
		Assertions.assertThat(response.getContent()).hasSize(2);
		verify(reviewReader, times(1)).findReviewList(jdId, mockPageInfoRequest);
	}

	@Test
	@DisplayName("3: 리뷰 작성자가 리뷰 삭제를 요청할 때 removeReview 메서드가 reviewStore의 delete를 실행한다.")
	void givenValidWriter_whenRemoveReview_thenExecuteDeleteReview() throws Exception {
		//given
		final var memberId = 1L;
		final var mockDeleteCommand = mockDeleteCommand(memberId);
		final var mockFindReview = mockFindReview();

		//when
		when(reviewReader.findById(mockDeleteCommand.getReviewId())).thenReturn(mockFindReview);
		doNothing().when(reviewStore).delete(mockFindReview);
		reviewServiceImpl.removeReview(mockDeleteCommand);

		//then
		verify(reviewReader, times(1)).findById(mockDeleteCommand.getReviewId());
		verify(reviewStore, times(1)).delete(mockFindReview);
	}

	private ReviewCommand.DeleteReviewRequest mockDeleteCommand(final Long memberId) {
		return ReviewCommand.DeleteReviewRequest.builder()
			.reviewId(1L)
			.memberId(memberId)
			.build();
	}

	private Review mockFindReview() throws IOException {
		String filePath = "giventest/review/serviceimpl/1_review.json";
		return JsonFileReader.readJsonFileToObject(filePath, Review.class);
	}

	@Test
	@DisplayName("4: 리뷰 작성자 아닌 사용자가 리뷰 삭제를 요청할 때 removeReview 메서드가 FORBIDDEN_DELETE_REVIEW 예외를 발생시킨다.")
	void givenInValidWriter_whenRemoveReview_thenThrowException() throws Exception {
		//given
		final var notWriterMemberId = 2L;
		final var mockDeleteCommand = mockDeleteCommand(notWriterMemberId);
		final var mockFindReview = mockFindReview();

		//when
		when(reviewReader.findById(mockDeleteCommand.getReviewId())).thenReturn(mockFindReview);

		//then
		ApiException exception = assertThrows(ApiException.class,
			() -> reviewServiceImpl.removeReview(mockDeleteCommand));
		assertThat(exception.getErrorCode()).isEqualTo(ReviewErrorCode.FORBIDDEN_DELETE_REVIEW);
		verify(reviewReader, times(1)).findById(mockDeleteCommand.getReviewId());
	}
}