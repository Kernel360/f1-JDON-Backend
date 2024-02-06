package kernel.jdon.faq.service;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.*;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import kernel.jdon.faq.domain.Faq;
import kernel.jdon.faq.dto.request.CreateFaqRequest;
import kernel.jdon.faq.dto.request.UpdateFaqRequest;
import kernel.jdon.faq.dto.response.CreateFaqResponse;
import kernel.jdon.faq.dto.response.DeleteFaqResponse;
import kernel.jdon.faq.dto.response.FindListFaqResponse;
import kernel.jdon.faq.dto.response.UpdateFaqResponse;
import kernel.jdon.faq.error.FaqErrorCode;
import kernel.jdon.faq.repository.FaqRepository;
import kernel.jdon.global.exception.ApiException;

@DisplayName("FAQ 서비스 테스트")
@ExtendWith(MockitoExtension.class)
public class FaqServiceTest {
	@Mock
	private FaqRepository faqRepository;
	@InjectMocks
	private FaqService faqService;

	@Test
	@DisplayName("findList 메서드가 존재하는 faq 개수 만큼 데이터를 응답한다.")
	void givenValidFaqList_whenFindList_thenReturn_whenCorrectFaqList() {
		// given
		List<Faq> faqs = Collections.singletonList(Faq.builder().id(1L).title("title1").content("content1").build());

		// when
		when(faqRepository.findAll()).thenReturn(faqs);
		FindListFaqResponse response = faqService.findList();

		// then
		assertThat(response.getFaqList()).hasSize(1);
		verify(faqRepository, times(1)).findAll();
	}

	@Test
	@DisplayName("유효한 FAQ 정보가 주어졌을 때 create 메서드가 등록한 FAQ의 ID를 반환한다.")
	void givenValidCreateRequest_whenCreate_thenReturnCreatedFaqId() throws Exception {
		// given
		final String title = "FAQ 제목";
		final String content = "FAQ 내용";
		CreateFaqRequest createFaqRequest =  new CreateFaqRequest(title, content);
		Faq faq = createFaqRequest.toEntity();

		// when
		when(faqRepository.save(any(Faq.class))).thenReturn(faq);
		CreateFaqResponse createFaqResponse = faqService.create(createFaqRequest);

		// then
		assertAll(
			() -> assertThat(createFaqResponse).isNotNull(),
			() -> assertThat(createFaqResponse.getFaqId()).isEqualTo(faq.getId())
		);
		verify(faqRepository, times(1)).save(any(Faq.class));
	}

	@Test
	@DisplayName("유효한 FAQ ID가 주어졌을 때 delete 메서드에서 삭제한 FAQ의 ID를 반환한다.")
	void givenValidFaqId_whenDelete_thenReturnDeletedFaqId() throws Exception {
		//given
		final Long faqId = 1L;
		Faq faq = Faq.builder().id(1L).title("제목").content("내용").build();

		// when
		when(faqRepository.findById(faqId)).thenReturn(Optional.of(faq));
		doNothing().when(faqRepository).delete(faq);
		DeleteFaqResponse response = faqService.delete(faqId);

		// then
		assertThat(faqId).isEqualTo(response.getFaqId());
		verify(faqRepository, times(1)).findById(faqId);
		verify(faqRepository, times(1)).delete(faq);
	}

	@Test
	@DisplayName("유효하지 않은 FAQ ID가 주어졌을 때 delete 메서드에서 NOT_FOUND_FAQ 예외를 던진다.")
	void givenInvalidFaqId_whenDelete_thenThrowException() throws Exception {
	    //given
		final Long faqId = 1L;

	    // when
		when(faqRepository.findById(faqId)).thenReturn(Optional.empty());

		// then
		ApiException exception = assertThrows(ApiException.class,
			() -> faqService.delete(faqId));
		assertThat(exception.getErrorCode()).isEqualTo(FaqErrorCode.NOT_FOUND_FAQ);
	}

	@Test
	@DisplayName("유효한 FAQ ID가 주어졌을 때 update 메서드에서 수정한 FAQ의 ID를 반환한다.")
	void givenValidFaqId_whenUpdate_thenUpdatedFaqId() throws Exception {
	    //given
	    final Long faqId = 1L;
		final String title = "FAQ 제목";
		final String content = "FAQ 내용";
		UpdateFaqRequest request = new UpdateFaqRequest(faqId, title, content);
		Faq faq = Faq.builder().id(faqId).title(title).content(content).build();

	    //when
		when(faqRepository.findById(faqId)).thenReturn(Optional.of(faq));
		UpdateFaqResponse update = faqService.update(request);

		//then
		assertThat(update.getFaqId()).isEqualTo(faqId);
		verify(faqRepository, times(1)).findById(faqId);
	}

	@Test
	@DisplayName("유효하지 않은 FAQ ID가 주어졌을 때 update 메서드에서 NOT_FOUND_FAQ 예외를 던진다.")
	void givenInvalidFaqId_whenUpdate_thenThrowException() throws Exception {
		// given
		final Long faqId = 1L;
		final String title = "FAQ 제목";
		final String content = "FAQ 내용";
		UpdateFaqRequest request = new UpdateFaqRequest(faqId, title, content);

		// when
		when(faqRepository.findById(faqId)).thenReturn(Optional.empty());

		// then
		ApiException exception = assertThrows(ApiException.class,
			() -> faqService.update(request));
		assertThat(exception.getErrorCode()).isEqualTo(FaqErrorCode.NOT_FOUND_FAQ);
	}
}