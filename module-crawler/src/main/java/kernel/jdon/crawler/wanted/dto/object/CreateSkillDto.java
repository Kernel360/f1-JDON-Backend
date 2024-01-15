package kernel.jdon.crawler.wanted.dto.object;

import kernel.jdon.jobcategory.domain.JobCategory;
import kernel.jdon.wantedjd.domain.WantedJd;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CreateSkillDto {
	private JobCategory jobCategory;
	private WantedJd wantedJd;
	private String keyword;
}
