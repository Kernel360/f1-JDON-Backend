package kernel.jdon.skill.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor
public class FindCompanyBySkillResponse {
	private String companyName;
	private String imageUrl;
	private String title;
	private String detailUrl;
}
