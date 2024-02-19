package kernel.jdon.application.faq;

import kernel.jdon.faq.domain.Faq;

public interface FaqStore {
	Faq save(Faq initFaq);
	void delete(Faq persistFaq);
}
