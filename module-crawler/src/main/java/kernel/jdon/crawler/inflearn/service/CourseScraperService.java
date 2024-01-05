package kernel.jdon.crawler.inflearn.service;

import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

@Service
public class CourseScraperService {

	public Elements scrapeCourses(String url) {
		// TODO: CrawlerException 커스텀 예러를 던져줘서 GlobalExceptionHandler에서 처리할 수 있도록 리팩토링 필요, 한꺼번에 리팩토링할 수 있도록 남겨뒀습니다
		try {
			Document doc = Jsoup.connect(url).get();
			return doc.select("div.card.course");
		} catch (IOException e) {
			throw new IllegalArgumentException("강의 데이터를 가져오지 못한 url: " + url, e);
		}
	}
}
