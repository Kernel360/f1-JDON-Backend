package kernel.jdon.global.exception;

import org.springframework.security.core.AuthenticationException;

import kernel.jdon.error.ErrorCode;
import lombok.Getter;

@Getter
public class AuthException extends AuthenticationException {
	private final transient ErrorCode errorCode;

	public AuthException(ErrorCode errorCode) {
		super(errorCode.getMessage());
		this.errorCode = errorCode;
	}
}
