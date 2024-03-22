package kernel.jdon.modulebatch.job.course.processor.service;

import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Component;

import kernel.jdon.modulebatch.domain.inflearncourse.error.InflearnErrorCode;
import kernel.jdon.modulebatch.global.exception.BatchException;

@Component
public class CourseScraper {

    public Elements scrapeCourses(String url) {
        try {
            Document doc = Jsoup.connect(url).get();
            return doc.select("div.card.course");
        } catch (IOException e) {
            throw new BatchException(InflearnErrorCode.NOT_FOUND_INFLEARN_URL);
        }
    }
}
