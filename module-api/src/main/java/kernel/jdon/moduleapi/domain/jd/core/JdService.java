package kernel.jdon.moduleapi.domain.jd.core;

import kernel.jdon.moduleapi.global.page.PageInfoRequest;

public interface JdService {
	JdInfo.FindWantedJdResponse getJd(Long jdId);

	JdInfo.FindWantedJdListResponse getJdList(PageInfoRequest pageInfoRequest, String keyword);
}
