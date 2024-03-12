package kernel.jdon.moduleapi.domain.faq.infrastructure;

import org.springframework.stereotype.Component;

import kernel.jdon.moduleapi.domain.faq.core.FaqStore;
import kernel.jdon.moduledomain.faq.domain.Faq;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class FaqStoreImpl implements FaqStore {
    private final FaqRepository faqRepository;

    @Override
    public Faq save(final Faq initFaq) {
        return faqRepository.save(initFaq);
    }

    @Override
    public void delete(final Faq persistFaq) {
        faqRepository.delete(persistFaq);
    }
}
