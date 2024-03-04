package kernel.jdon.moduleapi.domain.faq.application;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Collections;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import kernel.jdon.moduleapi.domain.faq.core.FaqCommand;
import kernel.jdon.moduleapi.domain.faq.core.FaqInfo;
import kernel.jdon.moduleapi.domain.faq.core.FaqService;
import kernel.jdon.moduledomain.faq.domain.Faq;

@DisplayName("Faq Facade 테스트")
@ExtendWith(MockitoExtension.class)
class FaqFacadeTest {
	@Mock
	private FaqService faqService;
	@InjectMocks
	private FaqFacade faqFacade;

	@Test
	@DisplayName("1: getFaqList 메서드가 존재하는 faq 개수 만큼 데이터를 응답한다.")
	void givenValidFaqList_whenFindList_thenReturn_whenCorrectFaqList() throws Exception {
		//given
		final var faqList = Collections.singletonList(new Faq(1L, "title", "content"));
		final var faqListResponse = new FaqInfo.FindFaqListResponse(faqList);

		//when
		when(faqService.getFaqList()).thenReturn(faqListResponse);
		final var response = faqFacade.getFaqList();

		//then
		assertThat(response.getFaqList()).hasSize(1);
		verify(faqService, times(1)).getFaqList();
	}

	@Test
	@DisplayName("2: FAQ 생성 정보가 주어졌을 때 create 메서드가 등록한 FAQ의 ID를 반환한다.")
	void givenCreateRequest_whenCreate_thenReturnCreatedFaqId() throws Exception {
		// given
		final var createFaqResponse = FaqInfo.CreateFaqResponse.builder().faqId(1L).build();
		final var request = FaqCommand.CreateFaqRequest.builder().title("FAQ 제목").content("FAQ 내용").build();

		// when
		when(faqService.create(request)).thenReturn(createFaqResponse);
		final var response = faqFacade.create(request);

		// then
		assertThat(response.getFaqId()).isEqualTo(createFaqResponse.getFaqId());
		verify(faqService, times(1)).create(request);
	}

	@Test
	@DisplayName("3: FAQ ID가 주어졌을 때 remove 메서드에서 삭제한 FAQ의 ID를 반환한다.")
	void givenValidFaqId_whenRemove_thenReturnRemovedFaqId() throws Exception {
		// given
		final Long removeFaqId = 1L;
		final var deleteFaqResponse = FaqInfo.DeleteFaqResponse.builder().faqId(removeFaqId).build();

		// when
		when(faqService.remove(removeFaqId)).thenReturn(deleteFaqResponse);
		final var response = faqFacade.remove(removeFaqId);

		// then
		assertThat(response.getFaqId()).isEqualTo(removeFaqId);
		verify(faqService, times(1)).remove(removeFaqId);
	}

	@Test
	@DisplayName("4: FAQ ID가 주어졌을 때 modify 메서드에서 수정한 FAQ의 ID를 반환한다.")
	void givenValidFaqId_whenModify_thenModifiedFaqId() throws Exception {
		// given
		final var modifyFaqCommand = FaqCommand.UpdateFaqRequest.builder()
			.faqId(1L)
			.title("수정 제목")
			.content("수정 내용")
			.build();
		final var modifyFaqResponse = FaqInfo.UpdateFaqResponse.builder().faqId(modifyFaqCommand.getFaqId()).build();

		// when
		when(faqService.modify(modifyFaqCommand)).thenReturn(modifyFaqResponse);
		final var response = faqFacade.modify(modifyFaqCommand);

		// then
		assertThat(response.getFaqId()).isEqualTo(modifyFaqCommand.getFaqId());
		verify(faqService, times(1)).modify(modifyFaqCommand);
	}
}