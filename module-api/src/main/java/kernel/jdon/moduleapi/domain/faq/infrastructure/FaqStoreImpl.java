package kernel.jdon.moduleapi.domain.faq.infrastructure;

import org.springframework.stereotype.Component;

import kernel.jdon.moduledomain.faq.domain.Faq;
import kernel.jdon.moduleapi.domain.faq.application.FaqStore;
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
