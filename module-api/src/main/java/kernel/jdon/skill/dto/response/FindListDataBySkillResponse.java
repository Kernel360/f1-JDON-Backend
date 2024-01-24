package kernel.jdon.skill.dto.response;

import java.util.List;

import kernel.jdon.skill.dto.object.FindLectureDto;
import kernel.jdon.skill.dto.object.FindWantedJdDto;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor
public class FindListDataBySkillResponse {
	private String keyword;
	private List<FindLectureDto> lectureList;
	private List<FindWantedJdDto> jdList;

	public static FindListDataBySkillResponse of (String keyword, List<FindLectureDto> lectureList, List<FindWantedJdDto> jdList) {
		return FindListDataBySkillResponse.builder()
			.keyword(keyword)
			.lectureList(lectureList)
			.jdList(jdList)
			.build();
	}
}
