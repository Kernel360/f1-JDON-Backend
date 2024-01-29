package kernel.jdon.favorite.dto.response;

import kernel.jdon.inflearncourse.domain.InflearnCourse;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor
public class FindFavoriteResponse {
	private Long lectureId;
	private String title;
	private String lectureUrl;
	private String imageUrl;
	private String instructor;
	private Long studentCount;
	private Integer price;

	public static FindFavoriteResponse of(InflearnCourse inflearnCourse) {
		return FindFavoriteResponse.builder()
			.lectureId(inflearnCourse.getId())
			.title(inflearnCourse.getTitle())
			.lectureUrl(inflearnCourse.getLectureUrl())
			.imageUrl(inflearnCourse.getImageUrl())
			.instructor(inflearnCourse.getInstructor())
			.studentCount(inflearnCourse.getStudentCount())
			.price(inflearnCourse.getPrice())
			.build();
	}
}
