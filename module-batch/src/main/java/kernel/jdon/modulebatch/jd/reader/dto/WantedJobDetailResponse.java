package kernel.jdon.modulebatch.jd.reader.dto;

import static kernel.jdon.modulecommon.util.StringUtil.*;

import java.time.LocalDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import kernel.jdon.modulebatch.jd.search.JobSearchJobPosition;
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

    public WantedJobDetailResponse(String detailUrl) {
        this.detailUrl = detailUrl;
    }

    public void addDetailInfo(String detailUrl, JobCategory jobCategory, JobSearchJobPosition jobPosition) {
        this.detailUrl = joinToString(detailUrl, this.job.id);
        this.jobCategory = jobCategory;
        this.jobPosition = jobPosition;
    }

    public WantedJd toWantedJdEntity() {
        return WantedJd.builder()
            .jobCategory(this.getJobCategory())
            .companyName(this.getJob().getCompany().getName())
            .title(this.getJob().getTitle())
            .detailId(this.getJob().getId())
            .detailUrl(this.getDetailUrl())
            .imageUrl(this.getJob().getCompanyImages())
            .requirements(this.getJob().getDetail().getRequirements())
            .mainTasks(this.getJob().getDetail().getMainTasks())
            .intro(this.getJob().getDetail().getIntro())
            .benefits(this.getJob().getDetail().getBenefits())
            .preferredPoints(this.getJob().getDetail().getPreferredPoints())
            .scrapingDate(LocalDateTime.now())
            .build();
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

        public String getCompanyImages() {
            return String.valueOf(companyImages.get(0).url);
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
