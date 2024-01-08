package kernel.jdon.crawler.wanted.dto.object;

import kernel.jdon.jobcategory.domain.JobCategory;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CreateSkillDto {
	private JobCategory jobCategory;
	private String keyword;
}
