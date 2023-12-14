package kernel.jdon.moduleapi.domain.inflearn.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "inflearn")
public class Inflearn {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "title", columnDefinition = "VARCHAR(100)", nullable = false)
	private String title;

	@Column(name = "lecture_url", columnDefinition = "VARCHAR(255)", nullable = false)
	private String lectureUrl;

	@Column(name = "instructor", columnDefinition = "VARCHAR(50)", nullable = false)
	private String instructor;

	@Column(name = "student_count", columnDefinition = "BIGINT(50)", nullable = false)
	private Long studentCount;

	@Column(name = "image_url", columnDefinition = "TEXT", nullable = false)
	private String imageUrl;

	@Column(name = "price", columnDefinition = "INT", nullable = false)
	private int price;
}
