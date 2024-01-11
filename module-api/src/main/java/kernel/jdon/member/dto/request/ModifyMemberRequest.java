package kernel.jdon.member.dto.request;

import java.time.LocalDate;
import java.util.List;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ModifyMemberRequest {
	private String email;
	private String nickname;
	private LocalDate birth;
	private String gender;
	private Long jobCategoryId;
	private List<Long> skillList;
}
