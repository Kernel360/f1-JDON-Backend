package kernel.jdon.auth.dto;

import java.time.LocalDateTime;

import kernel.jdon.util.DateParserUtil;
import lombok.Getter;

@Getter
public class AuthExceptionResponse {

	private String timestamp;
	private int status;
	private String message;
	private String path;

	public AuthExceptionResponse(int status, String message, String path) {
		this.timestamp = DateParserUtil.dateTimeToString(LocalDateTime.now());
		this.status = status;
		this.message = message;
		this.path = path;
	}
}
