package kernel.jdon.modulebatch.jd.writer;

import java.util.List;
import java.util.Optional;

import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.ItemWriter;
import org.springframework.stereotype.Component;

import kernel.jdon.modulebatch.jd.reader.dto.WantedJobDetailListResponse;
import kernel.jdon.modulebatch.jd.reader.dto.WantedJobDetailResponse;
import kernel.jdon.modulebatch.jd.repository.SkillHistoryRepository;
import kernel.jdon.modulebatch.jd.repository.SkillRepository;
import kernel.jdon.modulebatch.jd.repository.WantedJdRepository;
import kernel.jdon.modulebatch.jd.repository.WantedJdSkillRepository;
import kernel.jdon.modulebatch.jd.search.JobSearchJobPosition;
import kernel.jdon.moduledomain.jobcategory.domain.JobCategory;
import kernel.jdon.moduledomain.skill.domain.Skill;
import kernel.jdon.moduledomain.skill.domain.SkillType;
import kernel.jdon.moduledomain.skillhistory.domain.SkillHistory;
import kernel.jdon.moduledomain.wantedjd.domain.WantedJd;
import kernel.jdon.moduledomain.wantedjdskill.domain.WantedJdSkill;
import lombok.RequiredArgsConstructor;

@StepScope
@Component
@RequiredArgsConstructor
public class WantedJdItemWriter implements ItemWriter<WantedJobDetailListResponse> {
    private final WantedJdRepository wantedJdRepository;
    private final SkillHistoryRepository skillHistoryRepository;
    private final WantedJdSkillRepository wantedJdSkillRepository;
    private final SkillRepository skillRepository;

    @Override
    public void write(Chunk<? extends WantedJobDetailListResponse> chunk) throws Exception {
        System.out.println("WantedJdItemWriter 실행");
        // todo : 있다면 update 없으면 insert 하도록 변경해야함.
        for (WantedJobDetailListResponse wantedJobDetailList : chunk) {

            for (WantedJobDetailResponse wantedJobDetail : wantedJobDetailList.getJobDetailList()) {
                final JobCategory jdJobCategory = wantedJobDetail.getJobCategory();
                final JobSearchJobPosition jdJobPosition = wantedJobDetail.getJobPosition();

                // // todo : 있다면 update 없으면 insert 하도록 변경해야함.
                // final WantedJd savedWantedJd = wantedJdRepository.save(wantedJobDetail.toWantedJdEntity());
                //
                // final List<WantedJobDetailResponse.WantedSkill> wantedJdSkillList = wantedJobDetail.getJob().getSkill();
                //
                // // todo : 스킬 히스토리도 다 지우고 추가해야함.
                // createSkillHistory(jdJobCategory, savedWantedJd, wantedJdSkillList);
                //
                // // todo : 스킬도 다 지우고 추가해야함.
                // createWantedJdSkill(jdJobPosition, jdJobCategory, savedWantedJd, wantedJdSkillList);
            }
            System.out.println();
        }
    }

    /** 원본 기술스택 명 이력 저장 **/
    private void createSkillHistory(final JobCategory jobCategory, final WantedJd wantedJd,
        final List<WantedJobDetailResponse.WantedSkill> wantedDetailSkillList) {
        for (WantedJobDetailResponse.WantedSkill wantedJdDetailSkill : wantedDetailSkillList) {
            SkillHistory skillHistory = new SkillHistory(wantedJdDetailSkill.getKeyword(), jobCategory, wantedJd);

            skillHistoryRepository.save(skillHistory);
        }
    }

    /** 기술스택 저장 **/
    private void createWantedJdSkill(final JobSearchJobPosition jobPosition, final JobCategory jobCategory,
        WantedJd wantedJd, List<WantedJobDetailResponse.WantedSkill> wantedDetailSkillList) {
        for (WantedJobDetailResponse.WantedSkill wantedSkill : wantedDetailSkillList) {
            String skillKeyword = wantedSkill.getKeyword();

            final Optional<SkillType> findSkillType = jobPosition.getSkillTypeList().stream()
                .filter(skillType -> skillType.getKeyword().equalsIgnoreCase(skillKeyword))
                .findFirst();

            final Skill findSkill = findSkillType
                .map(skillType -> findByJobCategoryIdAndKeyword(jobCategory, skillType.getKeyword()))
                .orElseGet(() -> findByJobCategoryIdAndKeyword(jobCategory, SkillType.getOrderKeyword()));

            wantedJdSkillRepository.save(new WantedJdSkill(findSkill, wantedJd));
        }
    }

    private Skill findByJobCategoryIdAndKeyword(final JobCategory jobCategory, final String matchedSkillType) {
        return skillRepository.findByJobCategoryIdAndKeyword(jobCategory.getId(), matchedSkillType)
            .orElseThrow(() -> new IllegalArgumentException("해당하는 기술스택이 없음 -> 데이터베이스와 동기화되지 않은 키워드"));
    }
}
