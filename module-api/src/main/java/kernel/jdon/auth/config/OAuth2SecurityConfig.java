package kernel.jdon.auth.config;

import static com.nimbusds.jose.util.StandardCharset.*;
import static org.springframework.http.MediaType.*;

import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import com.fasterxml.jackson.databind.ObjectMapper;

import kernel.jdon.auth.service.JdonOAuth2UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@EnableWebSecurity
// TODO: 이거하면 왜 HttpSecurity에서 Could not autowire. No beans of 'HttpSecurity' type found. 에러가 사라지는지 모르곘음.
@Configuration
public class OAuth2SecurityConfig {
	private final JdonOAuth2UserService jdonOAuth2UserService;

	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		http.csrf().disable();
		http.authorizeHttpRequests(config -> config
			.requestMatchers("/oauth/**").authenticated()
			.anyRequest().permitAll()); // .anyRequest().authenticated());
		http.oauth2Login(oauth2Configurer -> oauth2Configurer
			.successHandler(oAuth2AuthenticationSuccessHandler())
			.userInfoEndpoint()
			.userService(jdonOAuth2UserService));

		return http.build();
	}

	@Bean
	public AuthenticationSuccessHandler oAuth2AuthenticationSuccessHandler() {
		return ((request, response, authentication) -> {
			DefaultOAuth2User defaultOAuth2User = (DefaultOAuth2User)authentication.getPrincipal();
			Map<String, Object> attributes = defaultOAuth2User.getAttributes();
			String email = ((Map<String, String>)attributes.get("kakao_account")).get("email");

			log.info(defaultOAuth2User.getAttributes().toString());
			log.info("successhandler | email : " + email);

			Map<String, String> userInfo = new HashMap<>();
			userInfo.put("email", email);

			String encodedEmail = Base64.getEncoder().encodeToString(email.getBytes(StandardCharsets.UTF_8));
			String redirectUrl = "http://localhost:3000/oauth/kakao/info?email=" + encodedEmail;
			log.info("redirectUrl: " + redirectUrl);
			if (redirectUrl != null && !redirectUrl.isEmpty()) {
				response.sendRedirect(redirectUrl);
			} else {
				// JSON 응답 생성 및 전송
				ObjectMapper objectMapper = new ObjectMapper();
				String jsonUserInfo = objectMapper.writeValueAsString(userInfo);

				response.setContentType(APPLICATION_JSON_VALUE);
				response.setCharacterEncoding(UTF_8.name());
				response.getWriter().write(jsonUserInfo);
			}
		});
	}
}
