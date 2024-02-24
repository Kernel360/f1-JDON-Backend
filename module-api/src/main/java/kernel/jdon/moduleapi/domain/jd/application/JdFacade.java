package kernel.jdon.moduleapi.domain.jd.application;

import org.springframework.stereotype.Service;

import kernel.jdon.moduleapi.domain.jd.core.JdInfo;
import kernel.jdon.moduleapi.domain.jd.core.JdService;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class JdFacade {
	private final JdService jdService;

	public JdInfo.FindWantedJdResponse getJd(final Long jdId) {
		return jdService.getJd(jdId);
	}
}
