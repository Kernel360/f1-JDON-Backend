package kernel.jdon.config;

import static kernel.jdon.auth.encrypt.AesUtil.*;
import static kernel.jdon.auth.encrypt.HmacUtil.*;
import static kernel.jdon.util.StringUtil.*;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import kernel.jdon.auth.dto.JdonOAuth2User;
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
			.requestMatchers("api/**").permitAll()
			.anyRequest().permitAll());
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
