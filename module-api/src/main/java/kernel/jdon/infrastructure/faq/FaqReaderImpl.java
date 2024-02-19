package kernel.jdon.infrastructure.faq;

import java.util.List;

import org.springframework.stereotype.Component;

import kernel.jdon.application.faq.FaqReader;
import kernel.jdon.faq.domain.Faq;
import kernel.jdon.faq.error.FaqErrorCode;
import kernel.jdon.faq.repository.FaqRepository;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class FaqReaderImpl implements FaqReader {
	private final FaqRepository faqRepository;

	@Override
	public Faq getFaqById(Long faqId) {
		return faqRepository.findById(faqId)
			.orElseThrow(FaqErrorCode.NOT_FOUND_FAQ::throwException);
	}

	@Override
	public List<Faq> getFaqAllList() {
		return faqRepository.findAll();
	}
}
