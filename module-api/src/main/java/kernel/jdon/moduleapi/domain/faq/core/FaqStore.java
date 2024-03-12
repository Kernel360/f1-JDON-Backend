package kernel.jdon.moduleapi.domain.faq.core;

import kernel.jdon.moduledomain.faq.domain.Faq;

public interface FaqStore {
    Faq save(Faq initFaq);

    void delete(Faq persistFaq);
}
