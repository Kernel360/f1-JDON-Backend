package kernel.jdon.modulebatch.domain.skillhistory.repository;

import static kernel.jdon.modulecommon.util.StringUtil.*;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import kernel.jdon.modulebatch.job.jd.reader.dto.WantedJobDetailResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Repository
@RequiredArgsConstructor
public class JdbcSkillHistoryRepository {

    private final JdbcTemplate jdbcTemplate;

    public void saveSkillHistoryList(final Long jobCategoryId, final Long wantedJdId,
        final List<WantedJobDetailResponse.WantedSkill> wantedDetailSkillList) {
        final List<String> expectValues = new ArrayList<>();

        jdbcTemplate.batchUpdate(
            "INSERT INTO skill_history (wanted_jd_id, job_category_id, keyword) VALUES (?, ?, ?)"
            , new BatchPreparedStatementSetter() {
                @Override
                public void setValues(PreparedStatement ps, int i) throws SQLException {
                    String keyword = wantedDetailSkillList.get(i).getKeyword();
                    ps.setLong(1, wantedJdId);
                    ps.setLong(2, jobCategoryId);
                    ps.setString(3, keyword);

                    expectValues.add(joinToString("(", wantedJdId, ", ", jobCategoryId, ", ", keyword, ")"));
                }

                @Override
                public int getBatchSize() {
                    return wantedDetailSkillList.size();
                }
            });

        writeInsertLog(expectValues);
    }

    private void writeInsertLog(List<String> valus) {
        if (!valus.isEmpty()) {
            StringBuilder insertLog = new StringBuilder(
                "[skill_history batchUpdate 실행 예상 쿼리] INSERT INTO skill_history (wanted_jd_id, job_category_id, keyword) VALUES ");
            valus.forEach(value -> insertLog.append(joinToString(value, ", ")));
            log.info(insertLog.deleteCharAt(insertLog.length() - 2).toString());
        }
    }
}
