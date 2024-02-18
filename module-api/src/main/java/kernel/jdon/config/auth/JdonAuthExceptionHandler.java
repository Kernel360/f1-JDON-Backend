package kernel.jdon.config.auth;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import kernel.jdon.auth.dto.AuthExceptionResponse;
import kernel.jdon.auth.error.AuthErrorCode;
import kernel.jdon.error.ErrorCode;
import kernel.jdon.global.exception.UnAuthorizedException;
import org.springframework.http.MediaType;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.PrintWriter;

@Component
public class JdonAuthExceptionHandler implements AuthenticationEntryPoint, AccessDeniedHandler, AuthenticationFailureHandler {

    private void throwAuthException(HttpServletResponse response, ErrorCode authErrorCode, String redirectUri) throws
            IOException {
        AuthExceptionResponse exceptionResponse = new AuthExceptionResponse(
                authErrorCode.getHttpStatus().value(), authErrorCode.getMessage(), redirectUri);
        String exceptionResponseJson = new ObjectMapper().writeValueAsString(exceptionResponse);
        response.setStatus(authErrorCode.getHttpStatus().value());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setCharacterEncoding("utf-8");
        PrintWriter write = response.getWriter();
        write.write(exceptionResponseJson);
        write.flush();
    }

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {

        // 상수
        throwAuthException(response, AuthErrorCode.UNAUTHORIZED, "/");
    }

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {
        throwAuthException(response, AuthErrorCode.FORBIDDEN, "/");
    }

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
        if (exception instanceof UnAuthorizedException) {
            throwAuthException(response, ((UnAuthorizedException) exception).getErrorCode(), "/");
        }
    }
}
