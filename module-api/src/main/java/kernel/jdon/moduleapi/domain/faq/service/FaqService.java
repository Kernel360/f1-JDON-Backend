package kernel.jdon.moduleapi.domain.faq.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kernel.jdon.moduleapi.domain.faq.dto.request.CreateFaqRequest;
import kernel.jdon.moduleapi.domain.faq.dto.request.ModifyFaqRequest;
import kernel.jdon.moduleapi.domain.faq.dto.response.FindFaqResponse;
import kernel.jdon.moduleapi.domain.faq.dto.response.ModifyFaqResponse;
import kernel.jdon.moduleapi.domain.faq.entity.Faq;
import kernel.jdon.moduleapi.domain.faq.repository.FaqRepository;
import lombok.RequiredArgsConstructor;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class FaqService {
	private final FaqRepository faqRepository;

	public FindFaqResponse find(Long faqId) {
		Faq findFaq = findById(faqId);

		return FindFaqResponse.of(findFaq);
	}

	private Faq findById(Long faqId) {
		return faqRepository.findById(faqId)
			.orElseThrow(() -> new IllegalArgumentException("존재하지 않는 Faq 입니다."));
	}

	@Transactional
	public Long create(CreateFaqRequest createFaqRequest) {
		Faq savedFaq = faqRepository.save(CreateFaqRequest.toEntity(createFaqRequest));

		return savedFaq.getId();
	}

	@Transactional
	public ModifyFaqResponse update(ModifyFaqRequest modifyFaqRequest) {
		Faq findFaq = findById(modifyFaqRequest.getFaqId());
		findFaq.update(modifyFaqRequest.getTitle(), modifyFaqRequest.getContent());

		return ModifyFaqResponse.of(findFaq);
	}

	@Transactional
	public void delete(Long faqId) {
		Faq findFaq = findById(faqId);
		faqRepository.delete(findFaq);
	}
}
