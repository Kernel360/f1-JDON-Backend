package kernel.jdon.moduleapi.global.common.dto.response;

import lombok.Getter;

@Getter
public class CommonResponse<T> {

	private T data;

	public CommonResponse(T data) {
		this.data = data;
	}

	public static <T> CommonResponse of(T data){
		return new CommonResponse(data);
	}
}
