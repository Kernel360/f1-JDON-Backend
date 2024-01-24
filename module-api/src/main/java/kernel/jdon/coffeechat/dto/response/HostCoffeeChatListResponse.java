package kernel.jdon.coffeechat.dto.response;

import java.util.List;

import kernel.jdon.global.page.CustomPageInfo;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class HostCoffeeChatListResponse {
	private List content;
	private CustomPageInfo.PageInfo pageInfo;

	public static HostCoffeeChatListResponse of(CustomPageInfo customPageInfo) {
		return HostCoffeeChatListResponse.builder()
			.content(customPageInfo.getContent())
			.pageInfo(customPageInfo.getPageInfo())
			.build();
	}

}
