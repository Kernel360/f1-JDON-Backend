package kernel.jdon.jobcategory.domain;

import java.util.ArrayList;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import kernel.jdon.base.BaseEntity;
import kernel.jdon.skill.domain.Skill;

@Entity
@Table(name = "job_category")
public class JobCategory extends BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "name", columnDefinition = "VARCHAR(255)", nullable = false)
	private String name;

	@Column(name = "parent_id", columnDefinition = "BIGINT")
	private Long parentId;

	@OneToMany(mappedBy = "jobCategory")
	private ArrayList<Skill> skillList = new ArrayList<>();
}
