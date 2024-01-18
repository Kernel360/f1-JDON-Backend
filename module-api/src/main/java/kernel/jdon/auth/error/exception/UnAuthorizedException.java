package kernel.jdon.auth.error.exception;

import org.springframework.security.core.AuthenticationException;

import kernel.jdon.error.ErrorCode;
import lombok.Getter;

@Getter
public class UnAuthorizedException extends AuthenticationException {
	private final transient ErrorCode errorCode;

	public UnAuthorizedException(ErrorCode errorCode) {
		super(errorCode.getMessage());
		this.errorCode = errorCode;
	}
}
