package kernel.jdon.config.auth;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerExceptionResolver;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import kernel.jdon.auth.error.AuthErrorCode;
import kernel.jdon.global.exception.AuthException;

@Component
public class JdonAuthExceptionHandler
	implements AuthenticationEntryPoint, AccessDeniedHandler, AuthenticationFailureHandler {

	private final HandlerExceptionResolver resolver;

	public JdonAuthExceptionHandler(@Qualifier("handlerExceptionResolver") HandlerExceptionResolver resolver) {
		this.resolver = resolver;
	}

	@Override
	public void commence(HttpServletRequest request, HttpServletResponse response,
		AuthenticationException authException) throws IOException, ServletException {
		resolver.resolveException(request, response, null, new AuthException(AuthErrorCode.UNAUTHORIZED));
	}

	@Override
	public void handle(HttpServletRequest request, HttpServletResponse response,
		AccessDeniedException accessDeniedException) throws IOException, ServletException {
		resolver.resolveException(request, response, null, new AuthException(AuthErrorCode.FORBIDDEN));
	}

	@Override
	public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
		AuthenticationException exception) throws IOException, ServletException {
		if (exception instanceof AuthException) {
			resolver.resolveException(request, response, null,
				new AuthException(((AuthException)exception).getErrorCode()));
		}
	}
}
