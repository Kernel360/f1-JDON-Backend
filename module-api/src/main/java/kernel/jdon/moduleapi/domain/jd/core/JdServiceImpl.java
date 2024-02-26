package kernel.jdon.moduleapi.domain.jd.core;

import java.util.List;

import org.springframework.stereotype.Service;

import kernel.jdon.moduleapi.global.page.PageInfoRequest;
import kernel.jdon.wantedjd.domain.WantedJd;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class JdServiceImpl implements JdService {
	private final JdReader jdReader;
	private final JdInfoMapper jdInfoMapper;

	@Override
	public JdInfo.FindWantedJdResponse getJd(final Long jdId) {
		final WantedJd findWantedJd = jdReader.findWantedJd(jdId);
		final List<JdInfo.FindSkill> findSkillList = jdReader.findSkillListByWantedJd(findWantedJd);

		return jdInfoMapper.of(findWantedJd, findSkillList);
	}

	@Override
	public JdInfo.FindWantedJdListResponse getJdList(final PageInfoRequest pageInfoRequest, final String keyword) {
		return jdReader.findWantedJdList(pageInfoRequest, keyword);
	}

}
