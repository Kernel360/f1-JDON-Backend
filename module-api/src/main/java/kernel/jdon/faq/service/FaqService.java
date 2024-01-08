package kernel.jdon.faq.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kernel.jdon.error.code.api.FaqErrorCode;
import kernel.jdon.error.exception.api.ApiException;
import kernel.jdon.faq.domain.Faq;
import kernel.jdon.faq.dto.request.CreateFaqRequest;
import kernel.jdon.faq.dto.request.UpdateFaqRequest;
import kernel.jdon.faq.dto.response.CreateFaqResponse;
import kernel.jdon.faq.dto.response.DeleteFaqResponse;
import kernel.jdon.faq.dto.response.FindListFaqResponse;
import kernel.jdon.faq.dto.response.UpdateFaqResponse;
import kernel.jdon.faq.repository.FaqRepository;
import lombok.RequiredArgsConstructor;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class FaqService {
	private final FaqRepository faqRepository;

	private Faq findById(Long faqId) {
		return faqRepository.findById(faqId)
			.orElseThrow(() -> new ApiException(FaqErrorCode.NOT_FOUND_FAQ));
	}

	@Transactional
	public CreateFaqResponse create(CreateFaqRequest createFaqRequest) {
		Faq savedFaq = faqRepository.save(CreateFaqRequest.toEntity(createFaqRequest));

		return CreateFaqResponse.of(savedFaq.getId());
	}

	@Transactional
	public UpdateFaqResponse update(UpdateFaqRequest updateFaqRequest) {
		Faq findFaq = findById(updateFaqRequest.getFaqId());
		findFaq.update(updateFaqRequest.getTitle(), updateFaqRequest.getContent());

		return UpdateFaqResponse.of(findFaq.getId());
	}

	@Transactional
	public DeleteFaqResponse delete(Long faqId) {
		Faq findFaq = findById(faqId);
		faqRepository.delete(findFaq);

		return DeleteFaqResponse.of(findFaq.getId());
	}

	public FindListFaqResponse findList() {
		List<Faq> findFaqList = faqRepository.findAll();

		return FindListFaqResponse.of(findFaqList);
	}
}
