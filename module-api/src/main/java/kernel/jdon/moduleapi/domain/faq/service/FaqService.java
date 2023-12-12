package kernel.jdon.moduleapi.domain.faq.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;

import kernel.jdon.moduleapi.domain.faq.dto.FindFaqResponse;
import kernel.jdon.moduleapi.domain.faq.entity.Faq;
import kernel.jdon.moduleapi.domain.faq.repository.FaqRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

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
	public void delete(Long faqId) {
		findById(faqId);
		faqRepository.deleteById(faqId);
	}
}
