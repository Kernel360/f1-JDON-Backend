package kernel.jdon.faq.service;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import kernel.jdon.faq.domain.Faq;
import kernel.jdon.faq.dto.request.CreateFaqRequest;
import kernel.jdon.faq.dto.request.UpdateFaqRequest;
import kernel.jdon.faq.dto.response.CreateFaqResponse;
import kernel.jdon.faq.dto.response.DeleteFaqResponse;
import kernel.jdon.faq.dto.response.FindListFaqResponse;
import kernel.jdon.faq.dto.response.UpdateFaqResponse;
import kernel.jdon.faq.error.FaqErrorCode;
import kernel.jdon.faq.repository.FaqRepository;
import lombok.RequiredArgsConstructor;

// @Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class FaqService {
	private final FaqRepository faqRepository;

	private Faq findById(final Long faqId) {
		return faqRepository.findById(faqId)
			.orElseThrow(FaqErrorCode.NOT_FOUND_FAQ::throwException);
	}

	@Transactional
	public CreateFaqResponse create(final CreateFaqRequest createFaqRequest) {
		final Faq savedFaq = faqRepository.save(createFaqRequest.toEntity());

		return CreateFaqResponse.of(savedFaq.getId());
	}

	@Transactional
	public UpdateFaqResponse update(final UpdateFaqRequest updateFaqRequest) {
		final Faq findFaq = findById(updateFaqRequest.getFaqId());
		findFaq.update(updateFaqRequest.getTitle(), updateFaqRequest.getContent());

		return UpdateFaqResponse.of(findFaq.getId());
	}

	@Transactional
	public DeleteFaqResponse delete(final Long faqId) {
		final Faq findFaq = findById(faqId);
		faqRepository.delete(findFaq);

		return DeleteFaqResponse.of(findFaq.getId());
	}

	public FindListFaqResponse findList() {
		final List<Faq> findFaqList = faqRepository.findAll();

		return FindListFaqResponse.of(findFaqList);
	}
}
