package kernel.jdon.moduledomain.wantedjd.domain;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import kernel.jdon.moduledomain.jobcategory.domain.JobCategory;
import kernel.jdon.moduledomain.review.domain.Review;
import kernel.jdon.moduledomain.wantedjdskill.domain.WantedJdSkill;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EntityListeners(AuditingEntityListener.class)
@Table(name = "wanted_jd", uniqueConstraints = @UniqueConstraint(columnNames = {"detail_id", "job_category_id"}))
public class WantedJd {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "company_name", columnDefinition = "VARCHAR(50)", nullable = false)
    private String companyName;

    @Column(name = "title", columnDefinition = "VARCHAR(255)", nullable = false)
    private String title;

    @Column(name = "detail_id", columnDefinition = "BIGINT", nullable = false)
    private Long detailId;

    @Column(name = "detail_url", columnDefinition = "VARCHAR(255)", nullable = false)
    private String detailUrl;

    @Column(name = "image_url", columnDefinition = "TEXT", nullable = false)
    private String imageUrl;

    @Column(name = "requirements", columnDefinition = "TEXT")
    private String requirements;

    @Column(name = "main_tasks", columnDefinition = "TEXT")
    private String mainTasks;

    @Column(name = "intro", columnDefinition = "TEXT")
    private String intro;

    @Column(name = "benefits", columnDefinition = "TEXT")
    private String benefits;

    @Column(name = "preferredPoints", columnDefinition = "TEXT")
    private String preferredPoints;

    @CreatedDate
    @Column(name = "scraping_date", columnDefinition = "DATETIME", nullable = false)
    private LocalDateTime scrapingDate;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "job_category_id", columnDefinition = "BIGINT")
    private JobCategory jobCategory;

    @OneToMany(mappedBy = "wantedJd")
    private List<WantedJdSkill> skillList = new ArrayList<>();

    @OneToMany(mappedBy = "wantedJd")
    private List<Review> reviewList = new ArrayList<>();

    @Builder
    public WantedJd(String companyName, String title, Long detailId, String detailUrl, String imageUrl,
        String requirements, String mainTasks, String intro, String benefits, String preferredPoints,
        LocalDateTime scrapingDate, JobCategory jobCategory) {
        this.companyName = companyName;
        this.title = title;
        this.detailId = detailId;
        this.detailUrl = detailUrl;
        this.imageUrl = imageUrl;
        this.requirements = requirements;
        this.mainTasks = mainTasks;
        this.intro = intro;
        this.benefits = benefits;
        this.preferredPoints = preferredPoints;
        this.scrapingDate = scrapingDate;
        this.jobCategory = jobCategory;
    }
}
