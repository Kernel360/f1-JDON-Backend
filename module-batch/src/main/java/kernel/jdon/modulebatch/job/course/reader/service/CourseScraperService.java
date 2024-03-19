package kernel.jdon.modulebatch.job.course.reader.service;

import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

import kernel.jdon.modulebatch.domain.inflearncourse.error.InflearnErrorCode;
import kernel.jdon.modulebatch.global.exception.BatchException;

@Service
public class CourseScraperService {

    public Elements scrapeCourses(String url) {
        try {
            Document doc = Jsoup.connect(url).get();
            return doc.select("div.card.course");
        } catch (IOException e) {
            throw new BatchException(InflearnErrorCode.NOT_FOUND_INFLEARN_URL);
        }
    }
}
