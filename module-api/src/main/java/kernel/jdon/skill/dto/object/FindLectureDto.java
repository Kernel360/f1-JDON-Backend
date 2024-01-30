package kernel.jdon.skill.dto.object;

import com.querydsl.core.annotations.QueryProjection;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class FindLectureDto {
	private Long lectureId;
	private String title;
	private String lectureUrl;
	private String imageUrl;
	private String instructor;
	private Long studentCount;
	private Integer price;
	private Boolean isFavorite;

	@QueryProjection
	public FindLectureDto(Long lectureId, String title, String lectureUrl, String imageUrl, String instructor,
		Long studentCount, Integer price, boolean isFavorite) {
		this.lectureId = lectureId;
		this.title = title;
		this.lectureUrl = lectureUrl;
		this.imageUrl = imageUrl;
		this.instructor = instructor;
		this.studentCount = studentCount;
		this.price = price;
		this.isFavorite = isFavorite;
	}
}
