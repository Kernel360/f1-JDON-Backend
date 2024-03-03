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

	@Override
	@Transactional
	public FaqInfo.CreateFaqResponse create(final FaqCommand.CreateFaqRequest command) {
		final Faq savedFaq = faqStore.save(command.toEntity());

		return new FaqInfo.CreateFaqResponse(savedFaq.getId());
	}

	@Override
	@Transactional
	public FaqInfo.UpdateFaqResponse modify(final FaqCommand.UpdateFaqRequest command) {
		final Faq findFaq = findById(command.getFaqId());
		findFaq.update(command.getTitle(), command.getContent());

		return new FaqInfo.UpdateFaqResponse(findFaq.getId());
	}

	@Override
	@Transactional
	public FaqInfo.DeleteFaqResponse remove(final Long faqId) {
		final Faq findFaq = findById(faqId);
		faqStore.delete(findFaq);

		return new FaqInfo.DeleteFaqResponse(findFaq.getId());
	}

	@Override
	public FaqInfo.FindFaqListResponse getFaqList() {
		final List<Faq> findFaqList = faqReader.findAllFaqList();

		return new FaqInfo.FindFaqListResponse(findFaqList);
	}

	private Faq findById(final Long faqId) {
		return faqReader.findById(faqId);
	}
}
