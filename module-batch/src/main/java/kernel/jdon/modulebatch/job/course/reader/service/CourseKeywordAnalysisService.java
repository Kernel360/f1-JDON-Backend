package kernel.jdon.modulebatch.job.course.reader.service;

import org.springframework.stereotype.Service;

import kernel.jdon.modulebatch.job.course.reader.converter.KeywordTranslator;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class CourseKeywordAnalysisService {

    public boolean isKeywordPresentInTitleAndDescription(String title, String description, String keyword) {
        String keywordInKorean = KeywordTranslator.translateToKorean(keyword);

        return (title.toLowerCase().contains(keyword.toLowerCase()) || title.contains(keywordInKorean)) &&
            (description.toLowerCase().contains(keyword.toLowerCase()) || description.contains(keywordInKorean));
    }
}
