package kernel.jdon.domain.jobcategory.entity;

import java.util.ArrayList;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import kernel.jdon.global.common.entity.BaseEntity;
import kernel.jdon.domain.skill.entity.Skill;

@Entity
@Table(name = "job_category")
public class JobCategory extends BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "name", columnDefinition = "VARCHAR(255)", nullable = false)
	private String name;

	@Column(name = "parent_id", columnDefinition = "BIGINT(50)")
	private Long parentId;

	@OneToMany(mappedBy = "jobCategory")
	private ArrayList<Skill> skillList = new ArrayList<>();
}
