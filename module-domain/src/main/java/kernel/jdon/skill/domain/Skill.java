package kernel.jdon.skill.domain;

import java.util.ArrayList;

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

@Entity
@Table(name = "skill")
public class Skill {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "keyword", columnDefinition = "VARCHAR(50)", nullable = false)
	private String keyword;

	@OneToMany(mappedBy = "skill")
	private ArrayList<WantedJdSkill> wantedJdSkillList = new ArrayList<>();

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "job_category_id", columnDefinition = "BIGINT(50)")
	private JobCategory jobCategory;
}
