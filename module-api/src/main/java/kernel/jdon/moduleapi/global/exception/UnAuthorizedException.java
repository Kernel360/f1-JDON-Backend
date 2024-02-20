package kernel.jdon.moduleapi.global.exception;

import kernel.jdon.modulecommon.error.ErrorCode;
import lombok.Getter;

import org.springframework.security.core.AuthenticationException;

@Getter
public class UnAuthorizedException extends AuthenticationException {
	private final transient ErrorCode errorCode;

	public UnAuthorizedException(ErrorCode errorCode) {
		super(errorCode.getMessage());
		this.errorCode = errorCode;
	}
}
