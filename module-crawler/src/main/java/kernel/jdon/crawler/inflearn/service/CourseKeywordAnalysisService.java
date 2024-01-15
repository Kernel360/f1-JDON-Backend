package kernel.jdon.crawler.inflearn.service;

import org.springframework.stereotype.Service;

import kernel.jdon.crawler.inflearn.converter.KeywordTranslator;

@Service
public class CourseKeywordAnalysisService {

	public boolean isKeywordPresentInTitleAndDescription(String title, String description, String keyword) {
		String keywordInKorean = KeywordTranslator.translateToKorean(keyword);

		return (title.toLowerCase().contains(keyword.toLowerCase()) || title.contains(keywordInKorean)) &&
			(description.toLowerCase().contains(keyword.toLowerCase()) || description.contains(keywordInKorean));
	}
}
