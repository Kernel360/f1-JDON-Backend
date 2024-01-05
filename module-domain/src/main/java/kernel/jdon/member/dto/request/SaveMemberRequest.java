package kernel.jdon.member.dto.request;

import java.time.LocalDate;
import java.util.List;

import lombok.Getter;

@Getter
public class SaveMemberRequest {
	private String email;
	private String nickname;
	private LocalDate birthday;
	private String gender;
	private Long jobCategoryId;
	private List<Long> skillList;
}
