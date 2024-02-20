package kernel.jdon.moduleapi.domain.favorite.core;

import kernel.jdon.inflearncourse.domain.InflearnCourse;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class FavoriteInfo {

	@Getter
	public static class FindResponse {
		private Long lectureId;
		private String title;
		private String lectureUrl;
		private String imageUrl;
		private String instructor;
		private Long studentCount;
		private Integer price;

		public FindResponse(InflearnCourse inflearnCourse) {
			this.lectureId = inflearnCourse.getCourseId();
			this.title = inflearnCourse.getTitle();
			this.lectureUrl = inflearnCourse.getLectureUrl();
			this.imageUrl = inflearnCourse.getImageUrl();
			this.instructor = inflearnCourse.getInstructor();
			this.studentCount = inflearnCourse.getStudentCount();
			this.price = inflearnCourse.getPrice();
		}
	}

	@Getter
	public static class UpdateResponse {
		private Long lectureId;

		public UpdateResponse(Long lectureId) {
			this.lectureId = lectureId;
		}
	}
}
