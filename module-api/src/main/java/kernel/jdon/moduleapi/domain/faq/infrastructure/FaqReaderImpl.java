package kernel.jdon.moduleapi.domain.faq.infrastructure;

import java.util.List;

import org.springframework.stereotype.Component;

import kernel.jdon.moduledomain.faq.domain.Faq;
import kernel.jdon.moduleapi.domain.faq.application.FaqReader;
import kernel.jdon.moduleapi.domain.faq.error.FaqErrorCode;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class FaqReaderImpl implements FaqReader {
	private final FaqRepository faqRepository;

	@Override
	public Faq findById(Long faqId) {
		return faqRepository.findById(faqId)
			.orElseThrow(FaqErrorCode.NOT_FOUND_FAQ::throwException);
	}

	@Override
	public List<Faq> findAll() {
		return faqRepository.findAll();
	}
}
