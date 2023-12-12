package kernel.jdon.moduleapi.domain.faq.service;

import org.springframework.stereotype.Service;

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
}
