package kernel.jdon.moduleapi.domain.faq.service;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import kernel.jdon.moduleapi.domain.faq.dto.request.CreateFaqRequest;
import kernel.jdon.moduleapi.domain.faq.dto.request.ModifyFaqRequest;
import kernel.jdon.moduleapi.domain.faq.dto.response.CreateFaqResponse;
import kernel.jdon.moduleapi.domain.faq.dto.response.FindFaqResponse;
import kernel.jdon.moduleapi.domain.faq.dto.response.ModifyFaqResponse;
import kernel.jdon.moduleapi.domain.faq.entity.Faq;
import kernel.jdon.moduleapi.domain.faq.repository.FaqRepository;

@SpringBootTest
public class FaqServiceTest {

	@Autowired
	private FaqService faqService;
	@Autowired
	private FaqRepository faqRepository;

	@Test
	@DisplayName("faq를 상세조회 한다.")
	void getFaqDetailTest() {
		// given
		Faq faq = Faq.builder()
			.title("제목")
			.content("내용")
			.build();
		Faq savedFaq = faqRepository.save(faq);

		// when
		FindFaqResponse findFaqResponse = faqService.find(savedFaq.getId());

		// then
		assertThat(findFaqResponse).isNotNull();
	}

	@Test
	@DisplayName("faq를 등록한다.")
	void createFaqTest() {
		// given
		String createTitle = "FAQ 제목 생성 테스트";
		String createContent = "FAQ content 생성 테스트";

		CreateFaqRequest createFaqRequest = CreateFaqRequest.builder()
			.title(createTitle)
			.content(createContent)
			.build();

		// when
		CreateFaqResponse createFaqResponse = faqService.create(createFaqRequest);
		Long faqId = createFaqResponse.getId();
		FindFaqResponse findFaqResponse = faqService.find(faqId);

		// then
		assertNotNull(findFaqResponse);
		assertThat(createTitle).isEqualTo(findFaqResponse.getTitle());
		assertThat(createContent).isEqualTo(findFaqResponse.getContent());

	}

	@Test
	@DisplayName("faq를 수정한다.")
	void updateFaqTest() {
		// given
		Faq faq = Faq.builder()
			.title("제목")
			.content("내용")
			.build();
		Faq savedFaq = faqRepository.save(faq);
		Long faqId = savedFaq.getId();

		String newTitle = "수정된 제목";
		String newContent = "수정된 내용";
		ModifyFaqRequest modifyFaqRequest = ModifyFaqRequest.builder()
			.faqId(faqId)
			.title(newTitle)
			.content(newContent)
			.build();

		// when
		ModifyFaqResponse modifyFaqResponse = faqService.update(modifyFaqRequest);

		// then
		Faq modifiedFaq = faqRepository.findById(faqId).get();
		assertThat(modifyFaqResponse).isNotNull();
		assertThat(modifiedFaq.getTitle()).isEqualTo(newTitle);
		assertThat(modifiedFaq.getContent()).isEqualTo(newContent);
	}

	@Test
	@DisplayName("faq 삭제를 확인한다.")
	public void removeFaqTest() {

		//given
		Faq faq = Faq.builder()
			.title("title")
			.content("content")
			.build();
		Faq savedFaq = faqRepository.save(faq);

		//when
		faqService.delete(savedFaq.getId());

		//then
		Assertions.assertThrows(IllegalArgumentException.class,
			() -> faqService.find(savedFaq.getId()));

	}
}