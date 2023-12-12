package kernel.jdon.moduleapi.domain.faq.service;

import kernel.jdon.moduleapi.domain.faq.dto.request.ModifyFaqRequest;
import kernel.jdon.moduleapi.domain.faq.dto.response.ModifyFaqResponse;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kernel.jdon.moduleapi.domain.faq.dto.FindFaqResponse;
import kernel.jdon.moduleapi.domain.faq.entity.Faq;
import kernel.jdon.moduleapi.domain.faq.repository.FaqRepository;
import lombok.RequiredArgsConstructor;

@Service
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
	public ModifyFaqResponse update(ModifyFaqRequest modifyFaqRequest) {
		Faq faq = findById(modifyFaqRequest.getFaqId());
		faq.update(modifyFaqRequest.getTitle(), modifyFaqRequest.getContent());

		return ModifyFaqResponse.of(faq);
	}

	@Transactional
	public void delete(Long faqId) {
		Faq findFaq = findById(faqId);
		faqRepository.delete(findFaq);
	}
}
