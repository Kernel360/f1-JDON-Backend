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
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "wanted_jd")
public class WantedJd {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "company_name", columnDefinition = "VARCHAR(50)", nullable = false)
	private String companyName;

	@Column(name = "detail_id", columnDefinition = "BIGINT", nullable = false, unique = true)
	private Long detailId;

	@Column(name = "detail_url", columnDefinition = "VARCHAR(255)", nullable = false)
	private String detailUrl;

	@Column(name = "image_url", columnDefinition = "TEXT", nullable = false)
	private String imageUrl;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "job_category_id", columnDefinition = "BIGINT")
	private JobCategory jobCategory;

	@Builder
	public WantedJd(String companyName, Long detailId, String detailUrl, String imageUrl, JobCategory jobCategory) {
		this.companyName = companyName;
		this.detailId = detailId;
		this.detailUrl = detailUrl;
		this.imageUrl = imageUrl;
		this.jobCategory = jobCategory;
	}
}
