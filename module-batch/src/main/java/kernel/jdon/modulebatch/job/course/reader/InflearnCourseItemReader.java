package kernel.jdon.modulebatch.job.course.reader;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.NonTransientResourceException;
import org.springframework.batch.item.ParseException;
import org.springframework.batch.item.UnexpectedInputException;
import org.springframework.stereotype.Component;

import jakarta.annotation.PostConstruct;
import kernel.jdon.modulebatch.domain.skill.BackendSkillType;
import kernel.jdon.modulebatch.domain.skill.FrontendSkillType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@StepScope
@RequiredArgsConstructor
public class InflearnCourseItemReader implements ItemReader<String> {

    private Iterator<String> keywordIterator;

    @PostConstruct
    public void postConstruct() {
        List<String> keywordList = new ArrayList<>();
        keywordList.addAll(FrontendSkillType.getAllKeywords());
        keywordList.addAll(BackendSkillType.getAllKeywords());
        this.keywordIterator = keywordList.iterator();
        log.info("강의 수집할 기술스택 목록: {}", keywordList);
    }

    @Override
    public String read() throws
        Exception,
        UnexpectedInputException,
        ParseException,
        NonTransientResourceException {
        log.info(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> 읽기 작업 시작");
        return keywordIterator.hasNext() ? keywordIterator.next() : null;
    }
}
