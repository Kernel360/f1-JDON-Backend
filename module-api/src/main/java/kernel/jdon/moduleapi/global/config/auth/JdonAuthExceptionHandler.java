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
import kernel.jdon.moduleapi.domain.auth.error.AuthErrorCode;
import kernel.jdon.moduleapi.global.config.auth.properties.AllowOriginProperties;
import kernel.jdon.moduleapi.global.config.auth.properties.LoginRedirectUrlProperties;
import kernel.jdon.moduleapi.global.exception.AuthException;
import kernel.jdon.modulecommon.error.ErrorCode;

@Component
public class JdonAuthExceptionHandler
    implements AuthenticationEntryPoint, AccessDeniedHandler, AuthenticationFailureHandler {
    private final HandlerExceptionResolver resolver;
    private final LoginRedirectUrlProperties loginRedirectUrlProperties;
    private final AllowOriginProperties allowOriginProperties;

    public JdonAuthExceptionHandler(@Qualifier("handlerExceptionResolver") HandlerExceptionResolver resolver,
        LoginRedirectUrlProperties loginRedirectUrlProperties, AllowOriginProperties allowOriginProperties) {
        this.resolver = resolver;
        this.loginRedirectUrlProperties = loginRedirectUrlProperties;
        this.allowOriginProperties = allowOriginProperties;
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
        ErrorCode errorCode = ((AuthException)exception).getErrorCode();
        if (AuthErrorCode.UNAUTHORIZED_OAUTH_RETURN_NULL_EMAIL == errorCode) {
            response.sendRedirect(loginRedirectUrlProperties.getFailureNotFoundEmail());
            return;
        }
        if (AuthErrorCode.UNAUTHORIZED_NOT_MATCH_PROVIDER_TYPE == errorCode) {
            response.sendRedirect(loginRedirectUrlProperties.getFailureNotMatchProvider());
            return;
        }
        if (AuthErrorCode.CONFLICT_WITHDRAW_BY_OTHER_SOCIAL_PROVIDER == errorCode) {
            response.sendRedirect(loginRedirectUrlProperties.getFailureAnotherWithdrawAccount());
            return;
        }
        if (AuthErrorCode.CONFLICT_WITHDRAW_ACCOUNT == errorCode) {
            response.sendRedirect(loginRedirectUrlProperties.getFailureAlreadyWithdrawAccount());
            return;
        }
        response.sendRedirect(allowOriginProperties.getOrigin());
    }
}
