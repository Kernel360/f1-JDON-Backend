package kernel.jdon.skill.domain;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import kernel.jdon.jobcategory.domain.JobCategory;
import kernel.jdon.wantedskill.domain.WantedJdSkill;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Table(name = "skill")
public class Skill {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "keyword", columnDefinition = "VARCHAR(50)", nullable = false)
	private String keyword;

	@Column(name = "count", columnDefinition = "BIGINT", nullable = false)
	private Long count;

	@OneToMany(mappedBy = "skill")
	private List<WantedJdSkill> wantedJdSkillList = new ArrayList<>();

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "job_category_id", columnDefinition = "BIGINT")
	private JobCategory jobCategory;

	public void countPlus(Long plus) {
		this.count += plus;
	}
	@Builder
	public Skill(Long id, String keyword, Long count, JobCategory jobCategory) {
		this.id = id;
		this.keyword = keyword;
		this.count = count;
		this.jobCategory = jobCategory;
	}

}
