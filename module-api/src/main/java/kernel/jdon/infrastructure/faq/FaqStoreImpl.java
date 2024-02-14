package kernel.jdon.infrastructure.faq;

import org.springframework.stereotype.Component;

import kernel.jdon.application.faq.FaqStore;
import kernel.jdon.faq.domain.Faq;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class FaqStoreImpl implements FaqStore {
	private final FaqRepository faqRepository;

	@Override
	public Faq save(Faq initFaq) {
		return faqRepository.save(initFaq);
	}

	@Override
	public void delete(Faq persistFaq) {
		faqRepository.delete(persistFaq);
	}
}
