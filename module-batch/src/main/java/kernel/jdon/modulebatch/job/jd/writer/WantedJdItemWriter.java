package kernel.jdon.modulebatch.job.jd.writer;

import java.util.List;

import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.ItemWriter;
import org.springframework.stereotype.Component;

import kernel.jdon.modulebatch.domain.skillhistory.repository.SkillHistoryStore;
import kernel.jdon.modulebatch.domain.wantedjd.repository.WantedJdRepository;
import kernel.jdon.modulebatch.job.jd.reader.dto.WantedJobDetailListResponse;
import kernel.jdon.modulebatch.job.jd.reader.dto.WantedJobDetailResponse;
import kernel.jdon.moduledomain.jobcategory.domain.JobCategory;
import kernel.jdon.moduledomain.wantedjd.domain.WantedJd;
import lombok.RequiredArgsConstructor;

@StepScope
@Component
@RequiredArgsConstructor
public class WantedJdItemWriter implements ItemWriter<WantedJobDetailListResponse> {
    private final WantedJdRepository wantedJdRepository;
    private final WantedJdSkillStore wantedJdSkillStore;
    private final SkillHistoryStore skillHistoryStore;

    @Override
    public void write(Chunk<? extends WantedJobDetailListResponse> chunk) throws Exception {
        for (WantedJobDetailListResponse wantedJobDetailList : chunk) {
            for (WantedJobDetailResponse wantedJobDetail : wantedJobDetailList.getJobDetailList()) {
                final JobCategory jdJobCategory = wantedJobDetail.getJobCategory();
                final Long wantedJdDetailId = wantedJobDetail.getDetailJobId();
                final List<WantedJobDetailResponse.WantedSkill> wantedJdSkillList = wantedJobDetail.getJobDetailSkillList();
                final WantedJd findWantedJd = findByJobCategoryAndDetailId(jdJobCategory, wantedJdDetailId);

                if (findWantedJd != null) {
                    findWantedJd.updateWantedJd(wantedJobDetail.toWantedJdEntity());
                    changeWantedJdSkillList(wantedJobDetail, findWantedJd, wantedJdSkillList);
                    changeSkillHistory(findWantedJd, jdJobCategory, wantedJdSkillList);
                } else {
                    final WantedJd savedWantedJd = wantedJdRepository.save(wantedJobDetail.toWantedJdEntity());
                    createWantedJdSkill(wantedJobDetail, savedWantedJd, wantedJdSkillList);
                    createSkillHistory(jdJobCategory, savedWantedJd, wantedJdSkillList);
                }
            }
        }
    }

    private void changeSkillHistory(final WantedJd findWantedJd, final JobCategory jdJobCategory,
        final List<WantedJobDetailResponse.WantedSkill> wantedJdSkillList) {
        skillHistoryStore.deleteAllByWantedJdId(findWantedJd.getId());
        createSkillHistory(jdJobCategory, findWantedJd, wantedJdSkillList);
    }

    private void changeWantedJdSkillList(final WantedJobDetailResponse wantedJobDetail, final WantedJd findWantedJd,
        final List<WantedJobDetailResponse.WantedSkill> wantedJdSkillList) {
        wantedJdSkillStore.deleteAllByWantedJdId(findWantedJd.getId());
        createWantedJdSkill(wantedJobDetail, findWantedJd, wantedJdSkillList);
    }

    private WantedJd findByJobCategoryAndDetailId(final JobCategory jobCategory, final Long jobDetailId) {
        return wantedJdRepository.findByJobCategoryAndDetailId(jobCategory, jobDetailId);
    }

    /** 원본 기술스택 명 이력 저장 **/
    private void createSkillHistory(final JobCategory jobCategory, final WantedJd wantedJd,
        final List<WantedJobDetailResponse.WantedSkill> wantedDetailSkillList) {
        skillHistoryStore.saveSkillHistoryList(jobCategory.getId(), wantedJd.getId(), wantedDetailSkillList);
    }

    /** 기술스택 저장 **/
    private void createWantedJdSkill(
        final WantedJobDetailResponse wantedJobDetail,
        final WantedJd wantedJd,
        final List<WantedJobDetailResponse.WantedSkill> wantedDetailSkillList) {
        wantedJdSkillStore.saveWantedJdSkillList(wantedJobDetail, wantedJd, wantedDetailSkillList);
    }
}
