package kernel.jdon.modulecrawler.wanted.dto.response;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

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

    public void addDetailInfo(String detailUrl, JobCategory jobCategory) {
        this.detailUrl = detailUrl;
        this.jobCategory = jobCategory;
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
