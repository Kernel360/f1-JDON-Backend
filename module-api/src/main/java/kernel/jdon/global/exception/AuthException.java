package kernel.jdon.moduleapi.global.exception;

import kernel.jdon.modulecommon.error.ErrorCode;
import lombok.Getter;

import org.springframework.security.core.AuthenticationException;

@Getter
public class AuthException extends AuthenticationException {
	private final transient ErrorCode errorCode;

	public AuthException(ErrorCode errorCode) {
		super(errorCode.getMessage());
		this.errorCode = errorCode;
	}
}
