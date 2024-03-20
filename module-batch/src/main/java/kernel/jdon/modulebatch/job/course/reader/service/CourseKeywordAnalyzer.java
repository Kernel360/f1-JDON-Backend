package kernel.jdon.modulebatch.job.course.reader.service;

import org.springframework.stereotype.Component;

import kernel.jdon.modulebatch.job.course.reader.service.converter.KeywordTranslator;

@Component
public class CourseKeywordAnalyzer {

    public boolean isKeywordPresentInTitleAndDescription(String title, String description, String keyword) {
        String keywordInKorean = KeywordTranslator.translateToKorean(keyword);

        return (title.toLowerCase().contains(keyword.toLowerCase()) || title.contains(keywordInKorean)) &&
            (description.toLowerCase().contains(keyword.toLowerCase()) || description.contains(keywordInKorean));
    }
}
