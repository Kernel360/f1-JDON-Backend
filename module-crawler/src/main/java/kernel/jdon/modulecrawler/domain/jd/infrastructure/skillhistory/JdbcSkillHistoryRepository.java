package kernel.jdon.modulecrawler.domain.jd.infrastructure.skillhistory;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import kernel.jdon.modulecrawler.domain.jd.core.dto.WantedJobDetailResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Repository
@RequiredArgsConstructor
public class JdbcSkillHistoryRepository {

    private final JdbcTemplate jdbcTemplate;

    public void saveSkillHistoryList(final Long jobCategoryId, final Long wantedJdId,
        final List<WantedJobDetailResponse.WantedSkill> wantedDetailSkillList) {

        jdbcTemplate.batchUpdate(
            "INSERT INTO skill_history (wanted_jd_id, job_category_id, keyword) VALUES (?, ?, ?)"
            , new BatchPreparedStatementSetter() {
                @Override
                public void setValues(PreparedStatement ps, int i) throws SQLException {
                    String keyword = wantedDetailSkillList.get(i).getKeyword();
                    ps.setLong(1, wantedJdId);
                    ps.setLong(2, jobCategoryId);
                    ps.setString(3, keyword);
                }

                @Override
                public int getBatchSize() {
                    return wantedDetailSkillList.size();
                }
            });
    }
}
