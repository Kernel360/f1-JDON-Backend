package kernel.jdon.auth.error.exception;

import org.springframework.security.access.AccessDeniedException;

import kernel.jdon.error.ErrorCode;
import lombok.Getter;

@Getter
public class ForbiddenException extends AccessDeniedException {
	private final transient ErrorCode errorCode;

	public ForbiddenException(ErrorCode errorCode) {
		super(errorCode.getMessage());
		this.errorCode = errorCode;
	}
}
