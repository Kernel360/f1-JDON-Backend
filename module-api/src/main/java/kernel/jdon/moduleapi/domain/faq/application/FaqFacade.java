package kernel.jdon.moduleapi.domain.faq.application;

import org.springframework.stereotype.Service;

import kernel.jdon.moduleapi.domain.faq.core.FaqCommand;
import kernel.jdon.moduleapi.domain.faq.core.FaqInfo;
import kernel.jdon.moduleapi.domain.faq.core.FaqService;
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
