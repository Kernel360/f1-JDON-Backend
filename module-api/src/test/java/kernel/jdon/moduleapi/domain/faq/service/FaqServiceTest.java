package kernel.jdon.moduleapi.domain.faq.service;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.util.ReflectionTestUtils.*;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import kernel.jdon.moduleapi.domain.faq.dto.request.CreateFaqRequest;
import kernel.jdon.moduleapi.domain.faq.dto.request.UpdateFaqRequest;
import kernel.jdon.moduleapi.domain.faq.dto.response.CreateFaqResponse;
import kernel.jdon.moduleapi.domain.faq.dto.response.FindFaqResponse;
import kernel.jdon.moduleapi.domain.faq.dto.response.FindListFaqResponse;
import kernel.jdon.moduleapi.domain.faq.dto.response.UpdateFaqResponse;
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

		CreateFaqRequest createFaqRequest = new CreateFaqRequest();
		setField(createFaqRequest, "title", createTitle);
		setField(createFaqRequest, "content", createContent);

		// when
		CreateFaqResponse createFaqResponse = faqService.create(createFaqRequest);
		Long faqId = createFaqResponse.getFaqId();
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

		UpdateFaqRequest updateFaqRequest = new UpdateFaqRequest();
		setField(updateFaqRequest, "faqId", faqId);
		setField(updateFaqRequest, "title", newTitle);
		setField(updateFaqRequest, "content", newContent);

		// when
		UpdateFaqResponse updateFaqResponse = faqService.update(updateFaqRequest);

		// then
		Faq modifiedFaq = faqRepository.findById(faqId).get();
		assertThat(updateFaqResponse).isNotNull();
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

	@Test
	@DisplayName("faq 목록을 조회한다.")
	public void getFaqListTest() {
		// given
		String createTitle1 = "제목1";
		String createTitle2 = "제목2";
		String createContent1 = "내용1";
		String createContent2 = "내용2";

		Faq faq1 = Faq.builder()
			.title(createTitle1)
			.content(createContent1)
			.build();
		Faq faq2 = Faq.builder()
			.title(createTitle2)
			.content(createContent2)
			.build();
		Faq savedFaq1 = faqRepository.save(faq1);
		Faq savedFaq2 = faqRepository.save(faq2);

		// when
		FindListFaqResponse findListFaqResponse = faqService.findAll();

		// then
		assertNotNull(findListFaqResponse);
		assertThat(createTitle1).isEqualTo(findListFaqResponse.getFaqList().get(0).getTitle());
		assertThat(createContent1).isEqualTo(findListFaqResponse.getFaqList().get(0).getContent());
		assertThat(createTitle2).isEqualTo(findListFaqResponse.getFaqList().get(1).getTitle());
		assertThat(createContent2).isEqualTo(findListFaqResponse.getFaqList().get(1).getContent());

	}
}