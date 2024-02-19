package kernel.jdon.application.faq;

import java.util.List;

import kernel.jdon.faq.domain.Faq;

public interface FaqReader {
	Faq getFaqById(Long faqId);
	List<Faq> getFaqAllList();
}
