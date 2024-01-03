package kernel.jdon.crawler.wanted.dto.object;

import kernel.jdon.jobcategory.domain.JobCategory;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CreateSkillDto {
	private JobCategory jobCategory;
	private String keyword;
	private Long count;
}
