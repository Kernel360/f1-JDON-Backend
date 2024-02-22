package kernel.jdon.moduleapi.domain.faq.core;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kernel.jdon.moduledomain.faq.domain.Faq;
import lombok.RequiredArgsConstructor;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class FaqServiceImpl implements FaqService {
	private final FaqStore faqStore;
	private final FaqReader faqReader;

	private Faq findById(final Long faqId) {
		return faqReader.findById(faqId);
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
		final List<Faq> findFaqList = faqReader.findAll();

		return new FaqInfo.FindListResponse(findFaqList);
	}
}
