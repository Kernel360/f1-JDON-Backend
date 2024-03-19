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

@Component
@StepScope
@RequiredArgsConstructor
public class InflearnCourseItemReader implements ItemReader<String> {

    private Iterator<String> keywordIterator;
    private List<String> keywordList;

    @PostConstruct
    public void postConstruct() {
        keywordList = new ArrayList<>();
        keywordList.addAll(FrontendSkillType.getAllKeywords());
        keywordList.addAll(BackendSkillType.getAllKeywords());
        this.keywordIterator = keywordList.iterator();
    }

    @Override
    public String read() throws
        Exception,
        UnexpectedInputException,
        ParseException,
        NonTransientResourceException {
        return keywordIterator.hasNext() ? keywordIterator.next() : null;
    }
}
