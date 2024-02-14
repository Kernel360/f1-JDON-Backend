package kernel.jdon.domain.faq;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kernel.jdon.application.faq.FaqReader;
import kernel.jdon.application.faq.FaqStore;
import kernel.jdon.faq.domain.Faq;
import lombok.RequiredArgsConstructor;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class FaqServiceImpl implements FaqService {
	private final FaqStore faqStore;
	private final FaqReader faqReader;

	private Faq findById(final Long faqId) {
		return faqReader.getFaqById(faqId);
	}

	@Override
	@Transactional
	public FaqInfo.CreateResponse create(final FaqCommand.CreateRequest command) {
		final Faq savedFaq = faqStore.save(command.toEntity());

		return new FaqInfo.CreateResponse(savedFaq);
	}

	@Override
	@Transactional
	public FaqInfo.UpdateResponse update(final FaqCommand.UpdateRequest command) {
		final Faq findFaq = findById(command.getFaqId());
		findFaq.update(command.getTitle(), command.getContent());

		return new FaqInfo.UpdateResponse(findFaq);
	}

	@Override
	@Transactional
	public FaqInfo.DeleteResponse delete(final Long faqId) {
		final Faq findFaq = findById(faqId);
		faqStore.delete(findFaq);

		return new FaqInfo.DeleteResponse(findFaq);
	}

	@Override
	public FaqInfo.FindListResponse findList() {
		final List<Faq> findFaqList = faqReader.getFaqAllList();

		return new FaqInfo.FindListResponse(findFaqList);
	}
}
