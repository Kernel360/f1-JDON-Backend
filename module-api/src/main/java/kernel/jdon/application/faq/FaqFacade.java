package kernel.jdon.application.faq;

import org.springframework.stereotype.Service;

import kernel.jdon.domain.faq.FaqCommand;
import kernel.jdon.domain.faq.FaqInfo;
import kernel.jdon.domain.faq.FaqService;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class FaqFacade {
	private final FaqService faqService;

	public FaqInfo.CreateResponse create(final FaqCommand.CreateRequest command) {
		return faqService.create(command);
	}

	public FaqInfo.UpdateResponse update(final FaqCommand.UpdateRequest command) {
		return faqService.update(command);
	}

	public FaqInfo.DeleteResponse delete(final Long faqId) {
		return faqService.delete(faqId);
	}

	public FaqInfo.FindListResponse findList() {
		return faqService.findList();
	}

}
