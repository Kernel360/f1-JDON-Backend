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

	public FaqInfo.CreateFaqResponse create(final FaqCommand.CreateFaqRequest command) {
		return faqService.create(command);
	}

	public FaqInfo.UpdateFaqResponse modify(final FaqCommand.UpdateFaqRequest command) {
		return faqService.modify(command);
	}

	public FaqInfo.DeleteFaqResponse delete(final Long faqId) {
		return faqService.remove(faqId);
	}

	public FaqInfo.FindFaqListResponse getFaqList() {
		return faqService.getFaqList();
	}

}
