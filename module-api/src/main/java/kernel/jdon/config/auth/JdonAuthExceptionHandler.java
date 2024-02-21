package kernel.jdon.config.auth;

import java.io.IOException;
import java.io.PrintWriter;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.MediaType;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerExceptionResolver;

import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import kernel.jdon.auth.error.AuthErrorCode;
import kernel.jdon.dto.response.ErrorResponse;
import kernel.jdon.error.ErrorCode;
import kernel.jdon.global.exception.AuthException;

@Component
public class JdonAuthExceptionHandler
	implements AuthenticationEntryPoint, AccessDeniedHandler, AuthenticationFailureHandler {

	private final HandlerExceptionResolver resolver;
	private final LoginRedirectUrlConfig loginRedirectUrlConfig;

	public JdonAuthExceptionHandler(@Qualifier("handlerExceptionResolver") HandlerExceptionResolver resolver,
		LoginRedirectUrlConfig config) {
		this.resolver = resolver;
		this.loginRedirectUrlConfig = config;
	}

	private void throwAuthException(HttpServletRequest request, HttpServletResponse response,
		ErrorCode authErrorCode) throws
		IOException {
		AuthException e = new AuthException(authErrorCode);
		ErrorResponse errorResponse = ErrorResponse.of(e.getErrorCode(), request);

		String exceptionResponseJson = new ObjectMapper().writeValueAsString(errorResponse);
		response.setStatus(authErrorCode.getHttpStatus().value());
		response.setContentType(MediaType.APPLICATION_JSON_VALUE);
		response.setCharacterEncoding("utf-8");
		PrintWriter write = response.getWriter();
		write.write(exceptionResponseJson);
		write.flush();
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
		if (AuthErrorCode.UNAUTHORIZED_OAUTH_RETURN_NULL_EMAIL == ((AuthException)exception).getErrorCode()) {
			response.sendRedirect(loginRedirectUrlConfig.getFailureNotFoundEmail());
		}
		if (AuthErrorCode.UNAUTHORIZED_NOT_MATCH_PROVIDER_TYPE == ((AuthException)exception).getErrorCode()) {
			response.sendRedirect(loginRedirectUrlConfig.getFailureNotMatchProvider());
		}
	}
}
