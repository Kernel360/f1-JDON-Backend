package kernel.jdon.moduleapi.domain.review.infrastructure;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import kernel.jdon.moduleapi.domain.jd.core.JdReader;
import kernel.jdon.moduleapi.domain.member.core.MemberReader;
import kernel.jdon.moduleapi.domain.review.core.ReviewCommand;
import kernel.jdon.moduleapi.domain.review.core.ReviewStore;
import kernel.jdon.moduledomain.member.domain.Member;
import kernel.jdon.moduledomain.review.domain.Review;
import kernel.jdon.moduledomain.wantedjd.domain.WantedJd;

@DisplayName("Review Factory Impl 테스트")
@ExtendWith(MockitoExtension.class)
class ReviewFactoryImplTest {
	@InjectMocks
	private ReviewFactoryImpl reviewFactoryImpl;
	@Mock
	private ReviewStore reviewStore;
	@Mock
	private JdReader jdReader;
	@Mock
	private MemberReader memberReader;

	@Test
	@DisplayName("1: 리뷰 등록 정보가 주어지면, saveReview 메서드가 생성된 Review 엔티티를 반환한다.")
	void givenCreateReviewCommand_whenSaveReview_thenReturnSavedReview() throws Exception {
		//given
		final var request = mockCreateCommand();
		final var mockMember = mock(Member.class);
		final var mockWantedJd = mock(WantedJd.class);
		final var mockSavedReview = mockSaveReview(request, mockMember, mockWantedJd);
		given(memberReader.findById(request.getMemberId())).willReturn(mockMember);
		given(jdReader.findWantedJd(request.getJdId())).willReturn(mockWantedJd);
		given(reviewStore.save(any(Review.class))).willReturn(mockSavedReview);

		//when
		final var response = reviewFactoryImpl.saveReview(request);

		//then
		assertAll(
			() -> assertThat(response).isEqualTo(mockSavedReview),
			() -> assertThat(response.getContent()).isEqualTo(request.getContent())
		);
		then(memberReader).should(times(1)).findById(request.getMemberId());
		then(jdReader).should(times(1)).findWantedJd(request.getJdId());
		then(reviewStore).should(times(1)).save(any(Review.class));
	}

	private ReviewCommand.CreateReviewRequest mockCreateCommand() {
		return ReviewCommand.CreateReviewRequest.builder()
			.content("content")
			.build();
	}

	private Review mockSaveReview(final ReviewCommand.CreateReviewRequest request,
		final Member mockMember, final WantedJd mockWantedJd) {
		return Review.builder()
			.content(request.getContent())
			.member(mockMember)
			.wantedJd(mockWantedJd)
			.build();
	}
}