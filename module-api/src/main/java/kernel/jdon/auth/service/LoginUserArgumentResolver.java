package kernel.jdon.auth.service;

import java.util.Objects;

import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import jakarta.servlet.http.HttpSession;
import kernel.jdon.auth.dto.SessionUserInfo;
import kernel.jdon.global.annotation.LoginUser;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class LoginUserArgumentResolver implements HandlerMethodArgumentResolver {

	private final HttpSession httpSession;

	@Override
	public boolean supportsParameter(MethodParameter parameter) {

		boolean isLoginUserAnnotation = Objects.nonNull(parameter.getParameterAnnotation(LoginUser.class));
		boolean isUserClass = SessionUserInfo.class.equals(parameter.getParameterType());

		return isLoginUserAnnotation && isUserClass;
	}

	@Override
	public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer,
		NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {

		return httpSession.getAttribute("USER");
	}
}
