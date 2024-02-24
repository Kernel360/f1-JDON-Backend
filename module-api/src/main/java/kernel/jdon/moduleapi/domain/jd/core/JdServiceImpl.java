package kernel.jdon.moduleapi.domain.jd.core;

import org.springframework.stereotype.Service;

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
		return jdInfoMapper.of(findWantedJd);
	}
}
