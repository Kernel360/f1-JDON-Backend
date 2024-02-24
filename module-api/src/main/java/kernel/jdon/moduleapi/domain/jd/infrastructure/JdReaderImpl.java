package kernel.jdon.moduleapi.domain.jd.infrastructure;

import org.springframework.stereotype.Component;

import kernel.jdon.moduleapi.domain.jd.core.JdReader;
import kernel.jdon.moduleapi.domain.jd.error.JdErrorCode;
import kernel.jdon.wantedjd.domain.WantedJd;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class JdReaderImpl implements JdReader {
	private final WantedJdRepository wantedJdRepository;

	@Override
	public WantedJd findWantedJd(final Long jdId) {
		return wantedJdRepository.findById(jdId)
			.orElseThrow(JdErrorCode.NOT_FOUND_JD::throwException);
	}
}
