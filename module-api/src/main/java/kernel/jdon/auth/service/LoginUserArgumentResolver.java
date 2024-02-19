package kernel.jdon.auth.service;

import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import jakarta.servlet.http.HttpSession;
import kernel.jdon.moduleapi.global.annotation.LoginUser;
import kernel.jdon.auth.dto.SessionUserInfo;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class LoginUserArgumentResolver implements HandlerMethodArgumentResolver {

	private final HttpSession httpSession;

	@Override
	public boolean supportsParameter(MethodParameter parameter) {
		boolean isLoginUserAnnotation = parameter.getParameterAnnotation(LoginUser.class) != null;
		boolean isUserClass = SessionUserInfo.class.equals(parameter.getParameterType());

		return isLoginUserAnnotation && isUserClass;
	}

	@Override
	public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer,
		NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {

		return  httpSession.getAttribute("USER");
		// SessionUserInfo sessionUserInfo = (SessionUserInfo) httpSession.getAttribute("USER");
		// if (sessionUserInfo == null) {
		// 	return SessionUserInfo.of(Member.builder().id(30L).build(), UserInfoFromOAuth2.of(null, null, null));
		// } else {
		// 	return sessionUserInfo;
		// }
	}
}
