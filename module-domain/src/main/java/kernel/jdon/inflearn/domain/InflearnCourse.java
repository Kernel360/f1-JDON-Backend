package kernel.jdon.inflearn.domain;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EntityListeners(AuditingEntityListener.class)
@Table(name = "inflearn_course")
public class InflearnCourse {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "course_id", columnDefinition = "BIGINT", nullable = false, unique = true)
	private Long courseId;

	@Column(name = "title", columnDefinition = "VARCHAR(100)", nullable = false)
	private String title;

	@Column(name = "lecture_url", columnDefinition = "VARCHAR(255)", nullable = false)
	private String lectureUrl;

	@Column(name = "instructor", columnDefinition = "VARCHAR(50)", nullable = false)
	private String instructor;

	@Column(name = "student_count", columnDefinition = "BIGINT", nullable = false)
	private Long studentCount;

	@Column(name = "image_url", columnDefinition = "TEXT", nullable = false)
	private String imageUrl;

	@Column(name = "price", columnDefinition = "INT", nullable = false)
	private int price;

	@CreatedDate
	@Column(name = "scraping_date", columnDefinition = "TEXT", nullable = false)
	private String scrapingDate;

	@Builder
	public InflearnCourse(Long courseId, String title, String lectureUrl, String instructor, Long studentCount,
		String imageUrl, int price, String scrapingDate) {
		this.courseId = courseId;
		this.title = title;
		this.lectureUrl = lectureUrl;
		this.instructor = instructor;
		this.studentCount = studentCount;
		this.imageUrl = imageUrl;
		this.price = price;
		this.scrapingDate = scrapingDate;
	}
}
