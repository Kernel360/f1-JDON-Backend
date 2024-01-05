package kernel.jdon.wanted.domain;

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
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import kernel.jdon.jobcategory.domain.JobCategory;
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

	@CreatedDate
	@Column(name = "scraping_date", columnDefinition = "TEXT", nullable = false)
	private String scrapingDate;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "job_category_id", columnDefinition = "BIGINT")
	private JobCategory jobCategory;

	@Builder
	public WantedJd(String companyName, String title, Long detailId, String detailUrl, String imageUrl,
		String scrapingDate,
		JobCategory jobCategory) {
		this.companyName = companyName;
		this.title = title;
		this.detailId = detailId;
		this.detailUrl = detailUrl;
		this.imageUrl = imageUrl;
		this.scrapingDate = scrapingDate;
		this.jobCategory = jobCategory;
	}
}
