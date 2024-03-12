package kernel.jdon.moduleapi.domain.faq.core;

import java.util.List;

import kernel.jdon.moduledomain.faq.domain.Faq;

public interface FaqReader {
    Faq findById(Long faqId);

    List<Faq> findAllFaqList();
}
