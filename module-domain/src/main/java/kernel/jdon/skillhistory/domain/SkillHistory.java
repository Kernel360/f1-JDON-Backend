package kernel.jdon.skillhistory.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import kernel.jdon.jobcategory.domain.JobCategory;
import kernel.jdon.wantedjd.domain.WantedJd;
import lombok.AccessLevel;
import lombok.Builder;
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
	@JoinColumn(name = "job_category_id", columnDefinition = "BIGINT")
	private JobCategory jobCategory;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "wanted_jd_id", columnDefinition = "BIGINT")
	private WantedJd wantedJd;

	@Builder
	public SkillHistory(Long id, String keyword, JobCategory jobCategory, WantedJd wantedJd) {
		this.id = id;
		this.keyword = keyword;
		this.jobCategory = jobCategory;
		this.wantedJd = wantedJd;
	}
}
