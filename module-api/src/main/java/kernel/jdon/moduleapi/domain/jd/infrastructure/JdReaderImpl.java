package kernel.jdon.moduleapi.domain.jd.infrastructure;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import kernel.jdon.moduleapi.domain.jd.core.JdInfo;
import kernel.jdon.moduleapi.domain.jd.core.JdReader;
import kernel.jdon.moduleapi.domain.jd.error.JdErrorCode;
import kernel.jdon.moduleapi.global.page.CustomJpaPageInfo;
import kernel.jdon.moduleapi.global.page.PageInfoRequest;
import kernel.jdon.wantedjd.domain.WantedJd;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class JdReaderImpl implements JdReader {
	private final WantedJdRepository wantedJdRepository;
	private final JdReaderImplMapper jdReaderImplMapper;

	@Override
	public WantedJd findWantedJd(final Long jdId) {
		return wantedJdRepository.findById(jdId)
			.orElseThrow(JdErrorCode.NOT_FOUND_JD::throwException);
	}

	@Override
	public List<JdInfo.FindSkill> findSkillListByWantedJd(final WantedJd wantedJd) {
		return wantedJd.getSkillList().stream()
			.map(wantedJdSkill -> new JdInfo.FindSkill(
				wantedJdSkill.getSkill()))
			.toList();
	}

	@Override
	public JdInfo.FindWantedJdListResponse findWantedJdList(final PageInfoRequest pageInfoRequest,
		final String keyword) {
		final Pageable pageable = PageRequest.of(pageInfoRequest.getPage(), pageInfoRequest.getSize());

		final Page<JdReaderInfo.FindWantedJd> readerInfo = wantedJdRepository.findWantedJdList(pageable, keyword);

		final List<JdInfo.FindWantedJd> content = readerInfo.stream()
			.map(jdReaderImplMapper::of)
			.toList();

		return new JdInfo.FindWantedJdListResponse(content, new CustomJpaPageInfo(readerInfo));
	}
}