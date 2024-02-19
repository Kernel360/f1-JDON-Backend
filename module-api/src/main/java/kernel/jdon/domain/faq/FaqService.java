package kernel.jdon.domain.faq;

public interface FaqService {
	FaqInfo.CreateResponse create(final FaqCommand.CreateRequest command);
	FaqInfo.UpdateResponse update(final FaqCommand.UpdateRequest command);
	FaqInfo.DeleteResponse delete(final Long faqId);
	FaqInfo.FindListResponse findList();
}
