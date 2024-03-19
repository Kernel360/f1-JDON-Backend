package kernel.jdon.modulebatch.job.course.reader;

import java.util.Iterator;
import java.util.List;

import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.NonTransientResourceException;
import org.springframework.batch.item.ParseException;
import org.springframework.batch.item.UnexpectedInputException;
import org.springframework.stereotype.Component;

import jakarta.annotation.PostConstruct;
import kernel.jdon.modulebatch.job.course.reader.dto.InflearnCourseResponse;
import lombok.RequiredArgsConstructor;

@Component
@StepScope
@RequiredArgsConstructor
public class InflearnCourseItemReader implements ItemReader<InflearnCourseResponse> {

    private final InflearnCourseClient inflearnCourseClient;
    private Iterator<InflearnCourseResponse> dataIterator;

    @PostConstruct
    public void postConstruct() {
        List<InflearnCourseResponse> data = inflearnCourseClient.getInflearnData();
        this.dataIterator = data.iterator();
    }

    @Override
    public InflearnCourseResponse read() throws
        Exception,
        UnexpectedInputException,
        ParseException,
        NonTransientResourceException {
        return dataIterator.hasNext() ? dataIterator.next() : null;
    }
}
