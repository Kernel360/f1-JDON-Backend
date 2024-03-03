package kernel.jdon.moduleapi.domain.faq.core;

public interface FaqService {
	FaqInfo.CreateFaqResponse create(final FaqCommand.CreateFaqRequest command);

	FaqInfo.UpdateFaqResponse modify(final FaqCommand.UpdateFaqRequest command);

	FaqInfo.DeleteFaqResponse remove(final Long faqId);

	FaqInfo.FindFaqListResponse getFaqList();
}
