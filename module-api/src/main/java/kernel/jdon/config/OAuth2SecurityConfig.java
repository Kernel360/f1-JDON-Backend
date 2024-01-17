package kernel.jdon.config;

import static kernel.jdon.auth.encrypt.AesUtil.*;
import static kernel.jdon.auth.encrypt.HmacUtil.*;
import static kernel.jdon.util.StringUtil.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletResponse;
import kernel.jdon.auth.dto.SecurityExceptionResponse;
import kernel.jdon.error.code.api.AuthErrorCode;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import kernel.jdon.auth.dto.JdonOAuth2User;
import kernel.jdon.auth.service.JdonOAuth2UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.io.PrintWriter;

@Slf4j
@RequiredArgsConstructor
@EnableWebSecurity
// TODO: 이거하면 왜 HttpSecurity에서 Could not autowire. No beans of 'HttpSecurity' type found. 에러가 사라지는지 모르곘음.
@Configuration
public class OAuth2SecurityConfig {
	private final JdonOAuth2UserService jdonOAuth2UserService;

	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		http.csrf(csrfConfigurer -> csrfConfigurer.disable());
		http.authorizeHttpRequests(requestConfig -> requestConfig
				.requestMatchers("/login").permitAll()
				.requestMatchers("/oauth2/authorization/**", "/api/v1/register", "/api/v1/nickname/duplicate").permitAll()
				.requestMatchers("/api/v1/member/**", "/api/v1/withdraw", "/api/v1/logout").hasAnyRole("USER")
				.requestMatchers("/api/v1/**").permitAll()
				.anyRequest().permitAll());
		http.exceptionHandling(exceptionConfig -> exceptionConfig
				.authenticationEntryPoint(jdonUnAuthorizedEntryPoint)
				.accessDeniedHandler(jdonAccessDeniedHandler));
		http.oauth2Login(oauth2Configurer -> oauth2Configurer
				.successHandler(oAuth2AuthenticationSuccessHandler())
				.userInfoEndpoint(userInfoEndpointConfigurer -> userInfoEndpointConfigurer
						.userService(jdonOAuth2UserService)));
		http.logout(logoutConfigurer -> logoutConfigurer
				.logoutUrl("/api/v1/logout")
				.logoutSuccessUrl("http://localhost:3000/main") // TODO: 프론트와 상의 후 메인페이지 url로 변경
				.invalidateHttpSession(true)
				.deleteCookies("JSESSIONID"));

		return http.build();
	}

	private final AuthenticationEntryPoint jdonUnAuthorizedEntryPoint = (request, response, authException) -> {
		throwAuthException(response, AuthErrorCode.UNAUTHORIZED, "/");
	};

	private final AccessDeniedHandler jdonAccessDeniedHandler = (request, response, accessDeniedException) -> {
		throwAuthException(response, AuthErrorCode.FORBIDDEN, "/");
	};

	private void throwAuthException(HttpServletResponse response, AuthErrorCode authErrorCode, String redirectUri) throws IOException {
		SecurityExceptionResponse exception = new SecurityExceptionResponse(
				authErrorCode.getHttpStatus().value(), authErrorCode.getMessage(), redirectUri);
		String json = new ObjectMapper().writeValueAsString(exception);
		response.setStatus(authErrorCode.getHttpStatus().value());
		response.setContentType(MediaType.APPLICATION_JSON_VALUE);
		response.setCharacterEncoding("utf-8");
		PrintWriter write = response.getWriter();
		write.write(json);
		write.flush();
	}

	@Bean
	public AuthenticationSuccessHandler oAuth2AuthenticationSuccessHandler() {
		return ((request, response, authentication) -> {
			JdonOAuth2User jdonOAuth2User = (JdonOAuth2User)authentication.getPrincipal();
			if (isTemporaryUser(jdonOAuth2User)) {
				String query = createUserInfoString(jdonOAuth2User.getEmail(), jdonOAuth2User.getSocialProviderType());
				String encodedQueryString = createEncryptQueryString(query);
				response.sendRedirect(joinToString("http://localhost:3000/oauth/info?", encodedQueryString));
			}
		});
	}

	private boolean isTemporaryUser(JdonOAuth2User jdonOAuth2User) {
		return jdonOAuth2User.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_TEMPORARY_USER"));
	}

	private String createUserInfoString(String email, String provider) {
		return joinToString(createQueryString("email", email), createQueryString("provider", provider));
	}

	private String createEncryptQueryString(String info) {
		String encoded = null;
		try {
			encoded = encryptAESCBC(info);
			encoded = joinToString(createQueryString("value", encoded),
				createQueryString("hmac", generateHMAC(encoded)));
		} catch (Exception e) {
			log.warn(e.getMessage(), e);
		}
		return encoded;
	}
}
