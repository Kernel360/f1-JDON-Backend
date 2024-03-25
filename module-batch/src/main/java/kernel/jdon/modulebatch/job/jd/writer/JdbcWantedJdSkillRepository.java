package kernel.jdon.modulebatch.job.jd.writer;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import kernel.jdon.modulebatch.job.jd.reader.condition.JobSearchJobPosition;
import kernel.jdon.modulebatch.job.jd.reader.dto.WantedJobDetailResponse;
import kernel.jdon.moduledomain.jobcategory.domain.JobCategory;
import kernel.jdon.moduledomain.skill.domain.SkillType;
import kernel.jdon.moduledomain.wantedjd.domain.WantedJd;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Repository
@RequiredArgsConstructor
public class JdbcWantedJdSkillRepository {
    private final JdbcTemplate jdbcTemplate;

    public void saveWantedJdSkillList(
        final WantedJobDetailResponse wantedJobDetail,
        final WantedJd wantedJd,
        final List<WantedJobDetailResponse.WantedSkill> wantedDetailSkillList) {

        jdbcTemplate.batchUpdate(
            "INSERT INTO wanted_jd_skill (wanted_jd_id, skill_id) VALUES (?, ?)",
            new BatchPreparedStatementSetter() {
                @Override
                public void setValues(PreparedStatement ps, int i) throws SQLException {
                    final String skillKeyword = wantedDetailSkillList.get(i).getKeyword();
                    final JobSearchJobPosition jobPosition = wantedJobDetail.getJobPosition();
                    final JobCategory jobCategory = wantedJobDetail.getJobCategory();

                    final Optional<SkillType> findSkillType = jobPosition.getSkillTypeList().stream()
                        .filter(skillType -> skillType.getKeyword().equalsIgnoreCase(skillKeyword))
                        .findFirst();

                    final Long findSkillId = findSkillType
                        .map(skillType -> findByJobCategoryIdAndKeyword(jobCategory.getId(), skillType.getKeyword()))
                        .orElseGet(
                            () -> findByJobCategoryIdAndKeyword(jobCategory.getId(), SkillType.getOrderKeyword()));

                    ps.setLong(1, wantedJd.getId());
                    ps.setLong(2, findSkillId);
                }

                @Override
                public int getBatchSize() {
                    return wantedDetailSkillList.size();
                }
            });
    }

    private Long findByJobCategoryIdAndKeyword(final Long jobCategoryId, final String keyword) {
        String sql = "SELECT id FROM skill WHERE job_category_id = ? AND keyword = ?";
        return Optional.of(jdbcTemplate.queryForObject(sql, Long.class, jobCategoryId, keyword))
            .orElse(null);
    }
}
