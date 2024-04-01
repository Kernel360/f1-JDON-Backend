package kernel.jdon.moduledomain.skillhistory.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import kernel.jdon.moduledomain.jobcategory.domain.JobCategory;
import kernel.jdon.moduledomain.wantedjd.domain.WantedJd;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "skill_history")
public class SkillHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "keyword", columnDefinition = "VARCHAR(50)", nullable = false)
    private String keyword;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "job_category_id", columnDefinition = "BIGINT", nullable = false)
    private JobCategory jobCategory;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "wanted_jd_id", columnDefinition = "BIGINT", nullable = false)
    private WantedJd wantedJd;

    public SkillHistory(String keyword, JobCategory jobCategory, WantedJd wantedJd) {
        this.keyword = keyword;
        this.jobCategory = jobCategory;
        this.wantedJd = wantedJd;
    }
}
