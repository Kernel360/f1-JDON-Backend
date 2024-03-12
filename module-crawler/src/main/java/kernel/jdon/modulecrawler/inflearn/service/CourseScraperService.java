package kernel.jdon.modulecrawler.inflearn.service;

import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

import kernel.jdon.modulecrawler.global.error.code.InflearnErrorCode;
import kernel.jdon.modulecrawler.global.error.exception.CrawlerException;

@Service
public class CourseScraperService {

    protected Elements scrapeCourses(String url) {
        try {
            Document doc = Jsoup.connect(url).get();
            return doc.select("div.card.course");
        } catch (IOException e) {
            throw new CrawlerException(InflearnErrorCode.NOT_FOUND_INFLEARN_URL);
        }
    }
}
