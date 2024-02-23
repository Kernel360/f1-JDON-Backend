package kernel.jdon.moduleapi.global.config.auth;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerExceptionResolver;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import kernel.jdon.auth.error.AuthErrorCode;
import kernel.jdon.moduleapi.global.exception.AuthException;

@Component
public class JdonAuthExceptionHandler
	implements AuthenticationEntryPoint, AccessDeniedHandler, AuthenticationFailureHandler {

	private final HandlerExceptionResolver resolver;
	private final LoginRedirectUrlProperties loginRedirectUrlProperties;

	public JdonAuthExceptionHandler(@Qualifier("handlerExceptionResolver") HandlerExceptionResolver resolver,
		LoginRedirectUrlProperties config) {
		this.resolver = resolver;
		this.loginRedirectUrlProperties = config;
	}

	@Override
	public void commence(HttpServletRequest request, HttpServletResponse response,
		AuthenticationException authException) {
		resolver.resolveException(request, response, null, new AuthException(AuthErrorCode.UNAUTHORIZED));
	}

	@Override
	public void handle(HttpServletRequest request, HttpServletResponse response,
		AccessDeniedException accessDeniedException) {
		resolver.resolveException(request, response, null, new AuthException(AuthErrorCode.FORBIDDEN));
	}

	@Override
	public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
		AuthenticationException exception) throws IOException {
		if (AuthErrorCode.UNAUTHORIZED_OAUTH_RETURN_NULL_EMAIL == ((AuthException)exception).getErrorCode()) {
			response.sendRedirect(loginRedirectUrlProperties.getFailureNotFoundEmail(request.getHeader("Referer")));
		}
		if (AuthErrorCode.UNAUTHORIZED_NOT_MATCH_PROVIDER_TYPE == ((AuthException)exception).getErrorCode()) {
			response.sendRedirect(loginRedirectUrlProperties.getFailureNotMatchProvider(request.getHeader("Referer")));
		}
	}
}
