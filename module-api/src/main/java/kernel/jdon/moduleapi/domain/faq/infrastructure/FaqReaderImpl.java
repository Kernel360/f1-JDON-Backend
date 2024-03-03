package kernel.jdon.moduleapi.domain.faq.infrastructure;

import java.util.List;

import org.springframework.stereotype.Component;

import kernel.jdon.moduleapi.domain.faq.core.FaqReader;
import kernel.jdon.moduleapi.domain.faq.error.FaqErrorCode;
import kernel.jdon.moduledomain.faq.domain.Faq;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class FaqReaderImpl implements FaqReader {
	private final FaqRepository faqRepository;

	@Override
	public Faq findById(final Long faqId) {
		return faqRepository.findById(faqId)
			.orElseThrow(FaqErrorCode.NOT_FOUND_FAQ::throwException);
	}

	@Override
	public List<Faq> findAllFaqList() {
		return faqRepository.findAll();
	}
}
