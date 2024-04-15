package kernel.jdon.modulecrawler.domain.jd.core.dto;

import static kernel.jdon.modulecommon.util.StringUtil.*;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import kernel.jdon.modulecrawler.domain.jd.core.condition.JobSearchJobPosition;
import kernel.jdon.moduledomain.jobcategory.domain.JobCategory;
import kernel.jdon.moduledomain.wantedjd.domain.WantedJd;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class WantedJobDetailResponse {

    private JobDetail job;
    private String detailUrl;
    private JobCategory jobCategory;
    private JobSearchJobPosition jobPosition;

    public void addDetailInfo(String detailUrl, JobCategory jobCategory, JobSearchJobPosition jobPosition) {
        this.detailUrl = joinToString(detailUrl, this.job.id);
        this.jobCategory = jobCategory;
        this.jobPosition = jobPosition;
    }

    public WantedJd toWantedJdEntity() {
        return new WantedJd(
            this.job.company.name,
            this.job.title,
            this.job.id,
            this.detailUrl,
            this.job.getFirstCompanyImage(),
            this.job.detail.requirements,
            this.job.detail.mainTasks,
            this.job.detail.intro,
            this.job.detail.benefits,
            this.job.detail.preferredPoints,
            this.job.deadlineDate,
            this.jobCategory
        );
    }

    public Long getDetailJobId() {
        return this.job.id;
    }

    public List<WantedSkill> getJobDetailSkillList() {
        return this.job.skill;
    }

    @Getter
    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    public static class JobDetail {
        private Long id;
        @JsonProperty("position")
        private String title;
        private Detail detail;
        private Company company;
        @JsonProperty("skill_tags")
        private List<WantedSkill> skill;
        @JsonProperty("company_images")
        private List<CompanyImages> companyImages;
        @JsonProperty("due_time")
        private String deadlineDate;

        public String getFirstCompanyImage() {
            return !companyImages.isEmpty() ? companyImages.get(0).url : null;
        }
    }

    @Getter
    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    public static class Detail {
        private String requirements;
        @JsonProperty("main_tasks")
        private String mainTasks;
        private String intro;
        private String benefits;
        @JsonProperty("preferred_points")
        private String preferredPoints;
    }

    @Getter
    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    public static class WantedSkill {
        @JsonProperty("title")
        private String keyword;
    }

    @Getter
    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    public static class Company {
        private String id;
        private String name;
    }

    @Getter
    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    public static class CompanyImages {
        private String url;
    }
}
