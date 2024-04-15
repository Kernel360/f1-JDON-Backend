package kernel.jdon.modulecrawler.domain.jd.core.skillhistory;

import java.util.List;

import kernel.jdon.modulecrawler.domain.jd.core.dto.WantedJobDetailResponse;

public interface SkillHistoryStore {
    void saveSkillHistoryList(Long jobCategoryId, Long wantedJdId,
        List<WantedJobDetailResponse.WantedSkill> wantedDetailSkillList);

}
