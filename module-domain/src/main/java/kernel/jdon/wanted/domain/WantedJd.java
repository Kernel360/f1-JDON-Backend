package kernel.jdon.wanted.domain;

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

@Entity
@Table(name = "wanted_jd")
public class WantedJd {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "company", columnDefinition = "VARCHAR(50)", nullable = false)
	private String company;

	@Column(name = "detail_url", columnDefinition = "VARCHAR(255)", nullable = false)
	private String detailUrl;

	@Column(name = "image_url", columnDefinition = "TEXT", nullable = false)
	private String imageUrl;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "job_category_id", columnDefinition = "BIGINT(50)")
	private JobCategory jobCategory;

}
